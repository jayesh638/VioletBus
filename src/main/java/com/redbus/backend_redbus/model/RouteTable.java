package com.redbus.backend_redbus.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class RouteTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String RouteName;
    private String startingPoint;
    private String endingPoint;
    private String routeAvailability;
    private String arrivalTime;
    private String duration;
    private int startingPrice;
    @OneToMany
    private List<PointsTable> routePoint = new ArrayList<>();
    @JsonIgnore
    @OneToMany
    private List<WeekDays> weekDays = new ArrayList<>();
    @JsonIgnore
    @OneToOne
    private Bus busID;

    private String departureTime;
}
