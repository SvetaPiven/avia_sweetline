package com.avia.service;

import com.avia.model.entity.Ticket;
import com.avia.model.dto.TicketDto;

public interface TicketService {

    Ticket createTicket(TicketDto ticketDto);

    Ticket updateTicket(Long id, TicketDto ticketDto);
}
