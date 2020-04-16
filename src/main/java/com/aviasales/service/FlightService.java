package com.aviasales.service;

import com.aviasales.model.Flight;
import com.aviasales.repositories.FlightRepository;
import org.springframework.stereotype.Service;

@Service
public class FlightService {
    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public Flight findFlightById(Long flightId){
        return flightRepository.findFlightById(flightId);
    }

    public void saveFlight(Flight flight){
        flightRepository.save(flight);
    }
}
