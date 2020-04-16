package com.aviasales.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ticket")
@Getter
@Setter
@RequiredArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "cost")
    private double cost;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "destination")
    private String finishPoint;

    @Column(name = "beginning")
    private String startPoint;

    @Column(name = "reserved")
    private Boolean reserved;

    @Column(name = "buy")
    private Boolean buy;

    @Column(name = "departure")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date timeToStart;

    @Column(name = "arrival")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date timeToFinish;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clientId")
    @JsonBackReference
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    @JoinColumn(name = "flightId")
    private Flight flight;


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ticket other = (Ticket) obj;
        if (companyName == null) {
            if (other.companyName != null) {
                return false;
            }
        } else if (!companyName.equals(other.companyName)) {
            return false;
        }
        return true;
    }
}
