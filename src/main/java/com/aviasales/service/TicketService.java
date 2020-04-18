package com.aviasales.service;

import com.aviasales.model.Ticket;
import com.aviasales.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }


    public void saveTicket(Ticket ticket){
        if (checkNotNull(ticket)){
            ticketRepository.save(ticket);
        }
    }


    public List<Ticket> fndAllTickets(){
        return ticketRepository.findAll();
    }

    public Optional<Ticket> findById(Long ticketId){
        return ticketRepository.findById(ticketId);
    }

    public Ticket findTicketById(Long ticketId){
        return ticketRepository.findTicketsById(ticketId);
    }

    public List<Ticket> findAllUnreservedTickets(){
        List<Ticket> tickets = ticketRepository.findByReservedFalse();

        return tickets;
    }

    public List<Ticket> findCostBetween(Double from,Double to){

        return ticketRepository.findByCostBetween(from, to);
    }


    /**
     * Проверка метода на null
     * @param obj
     * @param <T>
     * @return
     */
    static <T> boolean checkNotNull(T obj){
        return (obj!=null);
    }
}
