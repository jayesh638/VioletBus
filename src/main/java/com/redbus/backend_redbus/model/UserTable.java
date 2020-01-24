package com.redbus.backend_redbus.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class UserTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String uniqueId;
    private String name;
    private String password;
    private String email;
    private boolean isEnable;
    private Date createdDate;
    @OneToOne
    private Role roleId;
    @OneToMany
    private List<Ticket> tickets = new ArrayList<>();
}
