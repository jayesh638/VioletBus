package com.redbus.backend_redbus.request.and.responses;

import lombok.Data;import java.io.Serializable;
@Data
public class AuthenticationRequest implements Serializable {    private String username;
    private String password;}
