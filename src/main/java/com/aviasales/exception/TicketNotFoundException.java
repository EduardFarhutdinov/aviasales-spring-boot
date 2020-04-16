package com.aviasales.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TicketNotFoundException extends RuntimeException{
    public TicketNotFoundException(Long ticketId) {
        super("could not find ticket '" + ticketId + "'.");
    }
}
