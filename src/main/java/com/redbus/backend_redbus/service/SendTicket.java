package com.redbus.backend_redbus.service;


import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.redbus.backend_redbus.model.Ticket;
import com.redbus.backend_redbus.repository.TicketRepository;
import com.redbus.backend_redbus.request.and.responses.BookingRequest;
import com.redbus.backend_redbus.request.and.responses.CancellationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.*;

@Service
public class SendTicket {
    @Autowired
    private TicketRepository ticketRepository;

    Logger logger = LoggerFactory.getLogger(SendTicket.class);
    public void writePdf(OutputStream outputStream,String content) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();
        Paragraph paragraph = new Paragraph();
        paragraph.add(new Chunk(content));
        document.add(paragraph);
        document.close();
    }
    public Session sendSession()
    {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("", "");
            }
        });

        return session;
    }
    public void sendCancellationMail(CancellationRequest cancellationRequest)throws Exception{
        Session session =sendSession();

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("", false));

        msg.setFrom(new InternetAddress("", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(cancellationRequest.getUserEmail()));
        msg.setSubject("Redbus");
        msg.setContent("Ticket Cancelled", "text/html");
        msg.setSentDate(new Date());
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("<h1 style=\"color:red;\">REDBUS</h1><br>"+"<h2>Your ticket with ticket no.:"+cancellationRequest.getTicketId()+" is cancelled.</h2>","text/html");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        msg.setContent(multipart);
        Transport.send(msg);
    }
    public void sendmail(BookingRequest bookingRequest,LinkedList<String> seatnames,LinkedList<String> ticketId) throws Exception {

        Session session = sendSession();
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(bookingRequest.getEmailOnTicket()));
        msg.setSubject("Redbus: "+bookingRequest.getBoardingPoint()+" to "+bookingRequest.getDroppingPoint());
        msg.setContent("Ticket details.", "text/html");
        msg.setSentDate(new Date());



        MimeBodyPart messageBodyPart = new MimeBodyPart();
        List<String> passengerList = new ArrayList<>();
        for(int i=0;i<bookingRequest.getPassengers().size();i++)
        {
            String p ="        <tr>" +
                    "          <td height=\"50px\" width=\"200px\" style=\"border-bottom: 1px solid #ddd;\">"+ticketId.get(i)+"</td>" +
                    "          <th style=\"border-bottom: 1px solid #ddd;\">"+bookingRequest.getPassengers().get(i).getPassengerName()+"</th>" +
                    "          <th style=\"border-bottom: 1px solid #ddd;\">"+bookingRequest.getPassengers().get(i).getPassengerAge()+"</th>" +
                    "          <th style=\"border-bottom: 1px solid #ddd;\">"+bookingRequest.getPassengers().get(i).getPassengerGender()+"</th>" +
                    "          <th style=\"border-bottom: 1px solid #ddd;\">"+seatnames.get(i)+"</th>" +
                    "          </tr>";
            passengerList.add(p);
        }
        String allPassengers = "";
        for(int i=0;i<passengerList.size();i++)
        {
            allPassengers = allPassengers+"<br>"+passengerList.get(i);
        }
        String content ="<h1 width=\"300px\" style=\"color:red;text-align:center;\">REDBUS</h1>"+
                "<table>" +
                "      <thead>" +
                "        <tr height=\"50px\" width=\"300px\"><th colspan=\"5\"><h3>Ticket Invoice</h3></th></tr>"+
                "        <th height=\"50px\" width=\"300px\" style=\"border-bottom: 1px solid #ddd;\">Boarding Point</th>" +
                "        <th style=\"border-bottom: 1px solid #ddd;\">Boarding Time</th>" +
                "        <th style=\"border-bottom: 1px solid #ddd;\">Dropping Point</th>" +
                "        <th style=\"border-bottom: 1px solid #ddd;\">Dropping Time</th>" +
                "        <th style=\"border-bottom: 1px solid #ddd;\">Bus Name</th>" +
                "      </thead>" +
                "      <tbody style=\"text-align:center;\">" +
                "        <tr>" +
                "          <td height=\"50px\" width=\"300px\" style=\"border-bottom: 1px solid #ddd;\">"+bookingRequest.getBoardingPoint()+"</td>" +
                "          <td style=\"border-bottom: 1px solid #ddd;\">"+bookingRequest.getBoardingTime()+"</td>" +
                "          <td style=\"border-bottom: 1px solid #ddd;\">"+bookingRequest.getDroppingPoint()+"</td>" +
                "          <td style=\"border-bottom: 1px solid #ddd;\">"+bookingRequest.getDroppingTime()+"</td>" +
                "          <td style=\"border-bottom: 1px solid #ddd;\">"+bookingRequest.getBusName()+"</td>" +
                "        </tr>" +
                "        <tr>" +
                "          <th height=\"50px\" width=\"300px\"style=\"border-bottom: 1px solid #ddd;\">Date of Booking</th>" +
                "          <th style=\"border-bottom: 1px solid #ddd;\">Day of Booking</th>" +
                "          <th style=\"border-bottom: 1px solid #ddd;\">Date of Journey</th>" +
                "          <th style=\"border-bottom: 1px solid #ddd;\">Day of Journey</th>" +
                "        </tr>" +
                "        <tr>" +
                "          <td height=\"50px\" width=\"300px\" style=\"border-bottom: 1px solid #ddd;\">"+bookingRequest.getDateOfBooking()+"</td>" +
                "          <td style=\"border-bottom: 1px solid #ddd;\">"+bookingRequest.getDayOfBooking()+"</td>" +
                "          <td style=\"border-bottom: 1px solid #ddd;\">"+bookingRequest.getDateOfJourney()+"</td>" +
                "          <td style=\"border-bottom: 1px solid #ddd;\">"+bookingRequest.getDayOfJourney()+"</td>" +
                "        </tr>" +
                "        <tr>" +
                "          <th height=\"50px\" width=\"300px\" style=\"border-bottom: 1px solid #ddd;\">Ticket Fare</th>" +
                "          <th style=\"border-bottom: 1px solid #ddd;\">Email</th>" +
                "          <th style=\"border-bottom: 1px solid #ddd;\">Phone</th>" +
                "        </tr>" +
                "        <tr>" +
                "          <td height=\"50px\" width=\"300px\" style=\"border-bottom: 1px solid #ddd;\">"+bookingRequest.getFare()+"</td>" +
                "          <td style=\"border-bottom: 1px solid #ddd;\">"+bookingRequest.getEmailOnTicket()+"</td>" +
                "          <td style=\"border-bottom: 1px solid #ddd;\">"+bookingRequest.getPhoneOnTicket()+"</td>" +
                "        </tr>" +
                "        <tr>" +
                "          <th height=\"50px\" width=\"300px\"><h3>Passenger Details</h3></th>" +
                "        </tr>" +
                "        <tr>" +
                "          <td height=\"50px\" width=\"300px\" style=\"border-bottom: 1px solid #ddd;\">Ticket Id</td>" +
                "          <th style=\"border-bottom: 1px solid #ddd;\">Name</th>" +
                "          <th style=\"border-bottom: 1px solid #ddd;\">Age</th>" +
                "          <th style=\"border-bottom: 1px solid #ddd;\">Gender</th>" +
                "          <th style=\"border-bottom: 1px solid #ddd;\">Seat Number</th>" +
                "        </tr>" +
                allPassengers+
                "      </tbody>" +
                "    </table>";
       /* String content = "<h2 style=\"color:red\">RedBus</h2>"+
                "<h3>Your Ticket Information:</h3><br>" +
                "Boarding Point: "+bookingRequest.getBoardingPoint()+"<br>"+
                "Boarding Time: "+bookingRequest.getBoardingTime()+"<br>"+
                "Dropping Point: "+bookingRequest.getDroppingPoint()+"<br>"+
                "Dropping Time: "+bookingRequest.getDroppingTime()+"<br>"+
                "Bus Name: "+bookingRequest.getBusName()+"<br>"+
                "Date of Booking: "+bookingRequest.getDateOfBooking()+"<br>"+
                "Day of Booking: "+bookingRequest.getDayOfBooking()+"<br>"+
                "Date of Journey: "+bookingRequest.getDateOfJourney()+"<br>"+
                "Day of Journey: "+bookingRequest.getDayOfJourney()+"<br>"+
                "Ticket Fare: "+bookingRequest.getFare()+"<br>"+
                "Email: "+bookingRequest.getEmailOnTicket()+"<br>"+
                "Phone: "+bookingRequest.getPhoneOnTicket()+"<br>"+
                "Passenger Details:<br>"+passengerList;*/

        StringBuilder passengerList2 = new StringBuilder();
        for(int i=0;i<bookingRequest.getPassengers().size();i++)
        {
            String p ="\n-------------------------------------------------------------------\n"+
                    "Name: "+bookingRequest.getPassengers().get(i).getPassengerName()+"\n"+
                    "Age: "+bookingRequest.getPassengers().get(i).getPassengerAge()+"\n"+
                    "Gender: "+bookingRequest.getPassengers().get(i).getPassengerGender()+"\n"+
                    "Seat Number: "+seatnames.get(i)+"\n"+
                    "\n-------------------------------------------------------------------\n";
            passengerList2 = passengerList2.append(p);
        }
        String content1= "\n---------------------RedBus---------------------\n\n"+
                "Your Ticket Information:\n\n" +
                "-> Boarding Point: "+bookingRequest.getBoardingPoint()+"\n"+
                "-> Boarding Time: "+bookingRequest.getBoardingTime()+"\n"+
                "-> Dropping Point: "+bookingRequest.getDroppingPoint()+"\n"+
                "-> Dropping Time: "+bookingRequest.getDroppingTime()+"\n"+
                "-> Bus Name: "+bookingRequest.getBusName()+"\n"+
                "-> Date of Booking: "+bookingRequest.getDateOfBooking()+"\n"+
                "-> Day of Booking: "+bookingRequest.getDayOfBooking()+"\n"+
                "-> Date of Journey: "+bookingRequest.getDateOfJourney()+"\n"+
                "-> Day of Journey: "+bookingRequest.getDayOfJourney()+"\n"+
                "-> Ticket Fare: "+bookingRequest.getFare()+"\n"+
                "-> Email: "+bookingRequest.getEmailOnTicket()+"\n"+
                "-> Phone: "+bookingRequest.getPhoneOnTicket()+"\n"+
                "-> Passenger Details:\n"+passengerList2;
        messageBodyPart.setContent(content,"text/html");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        writePdf(outputStream,content1);
        byte[] bytes = outputStream.toByteArray();
        DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
        MimeBodyPart pdfBodyPart = new MimeBodyPart();
        pdfBodyPart.setDataHandler(new DataHandler(dataSource));
        pdfBodyPart.setFileName("ticket.pdf");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        multipart.addBodyPart(pdfBodyPart);
        /*MimeBodyPart attachPart = new MimeBodyPart();
        attachPart.attachFile("/var/tmp/image19.png");
        multipart.addBodyPart(attachPart);*/
        msg.setContent(multipart);
        Transport.send(msg);
    }
}
