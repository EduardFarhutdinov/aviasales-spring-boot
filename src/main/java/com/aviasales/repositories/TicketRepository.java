package com.aviasales.repositories;

import com.aviasales.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {

    public Ticket findTicketsById(Long id);

    public List<Ticket> findByReservedFalse();

    public List<Ticket> findByCostBetween(Double from, Double to);

    public List<Ticket> findByTimeToStartBetween(Date from, Date to);
}
