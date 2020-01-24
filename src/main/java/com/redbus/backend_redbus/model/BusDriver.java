package com.redbus.backend_redbus.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class BusDriver {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    private String driverPhoneNumber;
    private String driverName;
    @OneToOne
    private Bus busId;
}
