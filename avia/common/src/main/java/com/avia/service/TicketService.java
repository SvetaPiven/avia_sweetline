package com.avia.service;

import com.avia.model.entity.Ticket;
import com.avia.model.request.TicketRequest;

import java.math.BigDecimal;

public interface TicketService {

    Ticket createTicket(TicketRequest ticketRequest);

    Ticket updateTicket(Long id, TicketRequest ticketRequest);

    BigDecimal findMostExpensiveTicketPrice(Long id);
}
