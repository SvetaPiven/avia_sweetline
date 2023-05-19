package com.avia.service;

import com.avia.model.entity.Ticket;
import com.avia.model.request.TicketRequest;

public interface TicketService {

    Ticket createTicket(TicketRequest ticketRequest);

    Ticket updateTicket(Long id, TicketRequest ticketRequest);
}
