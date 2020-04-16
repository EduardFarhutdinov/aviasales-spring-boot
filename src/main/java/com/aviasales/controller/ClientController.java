package com.aviasales.controller;

import com.aviasales.service.ClientService;
import com.aviasales.service.FlightService;
import com.aviasales.service.TicketService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {
    private final TicketService ticketService;
    private final ClientService clientService;
    private final FlightService flightService;

    public ClientController(TicketService ticketService, ClientService clientService, FlightService flightService) {
        this.ticketService = ticketService;
        this.clientService = clientService;
        this.flightService = flightService;
    }
}
