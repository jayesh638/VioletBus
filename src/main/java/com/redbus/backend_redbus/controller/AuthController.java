package com.redbus.backend_redbus.controller;


import com.redbus.backend_redbus.model.UserTable;
import com.redbus.backend_redbus.repository.UserTableRepository;
import com.redbus.backend_redbus.request.and.responses.AuthenticationRequest;
import com.redbus.backend_redbus.request.and.responses.AuthenticationResponse;
import com.redbus.backend_redbus.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@CrossOrigin
@Slf4j
@RestController
public class AuthController {
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserTableRepository userTableRepository;
    @Autowired
    JwtUtil jwtTokenUtil;

    @RequestMapping(value = "/rest/v1", method = RequestMethod.POST)
    public ModelAndView createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        UserTable userTable = userTableRepository.findByName(authenticationRequest.getUsername());
        ModelAndView modelAndView = new ModelAndView("redirect:/?" + "jwt=" + jwt + "&userName=" + userTable.getName() + "&userId=" + userTable.getId());
        return modelAndView;
    }
}