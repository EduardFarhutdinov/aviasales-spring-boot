package com.aviasales.controller;


import com.aviasales.exception.FlightNotFoundException;
import com.aviasales.exception.TicketNotFoundException;
import com.aviasales.model.Client;
import com.aviasales.model.Flight;
import com.aviasales.model.Ticket;
import com.aviasales.service.ClientService;
import com.aviasales.service.FlightService;
import com.aviasales.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/tickets")
public class ControllerTickets {

    private final TicketService ticketService;
    private final ClientService clientService;
    private final FlightService flightService;

    @Autowired
    public ControllerTickets(TicketService ticketService, ClientService clientService, FlightService flightService) {
        this.ticketService = ticketService;
        this.clientService = clientService;
        this.flightService = flightService;
    }


    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/create")
    public void createTicket(@RequestBody Ticket ticket) {
        ticketService.saveTicket(ticket);
    }


    @GetMapping(value = "/ticket/{id}")
    public Ticket getById(@PathVariable(name = "id") Long id) {
        Ticket ticket = ticketService.findTicketById(id);

        if (ticket != null) {
            return ticket;
        } else {
            throw new TicketNotFoundException(id);
        }
    }


    @GetMapping(value = "/filter/cost/{from}/{to}")
    public List<Ticket> getTicketsByCostBetween(@PathVariable(name = "from") Double from,
                                                @PathVariable(name = "to") Double to) {

        return ticketService.findCostBetween(from, to).stream().filter(el -> !el.getReserved()).collect(Collectors.toList());
    }

    @GetMapping(value = "/list")
    public List<Ticket> getAllUnreservedTickets() {

        List<Ticket> list = ticketService.fndAllTickets();
        return list.stream().filter(el -> !el.getReserved()).collect(Collectors.toList());
    }

    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping("/ticket/reserving/{clientId}/{flightId}")
    public void reservingTicket(@PathVariable(name = "flightId") Long flightId,
                                @PathVariable(name = "clientId") Long clientId) {

        Flight flight = flightService.findFlightById(flightId);
        Client client = clientService.findClientById(clientId);


        if (flight.getCountPlaces() == 0) {
            flight.setActive(false);
            flightService.saveFlight(flight);
        }
        if (flight.isActive()) {

            Ticket ticket = new Ticket();

            ticket.setReserved(true);
            ticket.setBuy(false);
            ticket.setFlight(flight);
            ticket.setClient(client);

            flight.setCountPlaces(flight.getCountPlaces() - 1);

            flightService.saveFlight(flight);
            ticketService.saveTicket(ticket);


        } else {
            throw new FlightNotFoundException(flightId);
        }

    }

    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping("/ticket/buy/{clientId}/{flightId}")
    public void buyTicketIfNoReservedTickets(@PathVariable(name = "flightId") Long flightId,
                                             @PathVariable(name = "clientId") Long clientId) {


        Flight flight = flightService.findFlightById(flightId);
        Client client = clientService.findClientById(clientId);

        double cost = flight.getCost();
        int cashBack = client.getCashBack();

        if (flight.getCountPlaces() == 0) {
            flight.setActive(false);
            flightService.saveFlight(flight);
        }
        if (flight.isActive()) {

            Ticket ticket = new Ticket();

            if (client.getCashBack() >= 0) {
                cost = cost + (cost * 0.5 - cashBack);
                cashBack += cashBack(cost, client.getTotalBuy());
            }

            ticket.setCost(cost);
            ticket.setCompanyName(flight.getCompanyName());
            ticket.setFinishPoint(flight.getFinishPoint());
            ticket.setStartPoint(flight.getStartPoint());
            ticket.setReserved(true);
            ticket.setBuy(true);
            ticket.setTimeToFinish(flight.getTimeToFinish());
            ticket.setTimeToStart(flight.getTimeToStart());
            ticket.setClient(client);
            ticket.setFlight(flight);

            client.setTotalBuy(client.getTotalBuy() + 1);
            client.setCashBack(cashBack);

            flight.setCountPlaces(flight.getCountPlaces() - 1);

            flightService.saveFlight(flight);
            clientService.saveClient(client);
            ticketService.saveTicket(ticket);

        } else {
            throw new FlightNotFoundException(flightId);
        }

    }

    private double cashBack(double cost, int totalBuy) {
        double bonuses;
        if (totalBuy < 10) {
            bonuses = cost * 0.05;
        } else if (totalBuy > 10 && totalBuy < 20) {
            bonuses = cost * 0.10;
        } else {
            bonuses = cost * 0.15;
        }
        return bonuses;
    }


    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping("/ticket/reserved/buy/{clientId}/{flightId}/{ticketId}")
    public void buyTicketIfHasReservedTickets(@PathVariable(name = "flightId") Long flightId,
                                              @PathVariable(name = "clientId") Long clientId,
                                              @PathVariable(name = "ticketId") Long ticketId) throws FlightNotFoundException {


        Flight flight = flightService.findFlightById(flightId);
        Client client = clientService.findClientById(clientId);
        Ticket ticket = ticketService.findTicketById(ticketId);

        double cost = flight.getCost();
        int cashBack = client.getCashBack();

        if (client.getCashBack() >= 0) {
            cost = cost + (cost * 0.5 - cashBack);
            cashBack += cashBack(cost, client.getTotalBuy());
        }

        if (flight.isActive()) {

            ticket.setCost(cost);
            ticket.setCompanyName(flight.getCompanyName());
            ticket.setFinishPoint(flight.getFinishPoint());
            ticket.setStartPoint(flight.getStartPoint());
            ticket.setReserved(true);
            ticket.setBuy(true);
            ticket.setTimeToFinish(flight.getTimeToFinish());
            ticket.setTimeToStart(flight.getTimeToStart());
            ticket.setClient(client);
            ticket.setFlight(flight);

            client.setTotalBuy(client.getTotalBuy() + 1);
            client.setCashBack(cashBack);

            flight.setCountPlaces(flight.getCountPlaces() - 1);

            flightService.saveFlight(flight);
            clientService.saveClient(client);
            ticketService.saveTicket(ticket);


        } else {
            throw new FlightNotFoundException(flightId);
        }


    }


}
