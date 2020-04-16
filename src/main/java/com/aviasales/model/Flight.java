package com.aviasales.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "flight")
@Getter @Setter
@RequiredArgsConstructor
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flightId")
    private Long id;

    @Column(name = "cost")
    private double cost;


    @Column(name = "company_name")
    private String companyName;

    @Column(name = "destination")
    private String finishPoint;

    @Column(name = "beginning")
    private String startPoint;

    @Enumerated(EnumType.STRING)
    private AirTransport transport;

    @Column(name = "departure")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date timeToStart;

    @Column(name = "arrival")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date timeToFinish;

    @Column(name = "places")
    private int countPlaces ;

    @Column(columnDefinition = "boolean default true")
    private boolean active;

    @OneToMany(mappedBy = "flight",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Ticket> tickets;


}
