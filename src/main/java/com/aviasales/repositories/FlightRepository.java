package com.aviasales.repositories;

import com.aviasales.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight,Long> {

    public Flight findFlightById(Long flightId);
}
