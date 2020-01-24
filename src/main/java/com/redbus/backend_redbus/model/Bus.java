package com.redbus.backend_redbus.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private String name;
    @NotNull
    private String number;
    private boolean sleeperAvailable;
    private String totalSeat;
    @OneToOne(mappedBy = "busId")
    private BusDriver driver;
    @OneToOne(mappedBy = "busTypeId")
    private BusType busType;
    @OneToMany
    private List<RouteTable> routeTable = new ArrayList<>();
    @OneToOne
    private BusRating busRating;
}