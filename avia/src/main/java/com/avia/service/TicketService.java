package com.avia.service;

import com.avia.model.entity.requests.TicketDto;
import com.avia.model.entity.Ticket;

public interface TicketService {

    Ticket createTicket(TicketDto ticketDto);

    Ticket updateTicket(Long id, TicketDto ticketDto);
}
