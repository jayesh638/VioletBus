package com.redbus.backend_redbus.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
@Getter
@Setter
public class PointsTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String pointName;
    private String landMark;
    private String boardingTime;
    private String DepartureTime;
    private String pointType;
    private boolean isStoppingPoint;
}
