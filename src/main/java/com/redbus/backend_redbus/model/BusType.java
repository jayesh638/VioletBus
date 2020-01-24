package com.redbus.backend_redbus.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class BusType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    private String type;
    @OneToOne
    private Bus busTypeId;

}
