package com.redbus.backend_redbus.request.and.responses;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class UserRequest {
    private String name;
    private String password;
    private String email;
    private String isEnable;
    private String createDate;
    private String role;
}
