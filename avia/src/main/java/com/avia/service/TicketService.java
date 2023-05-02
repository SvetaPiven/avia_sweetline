package com.avia.service;

import com.avia.dto.TicketCreateDto;
import com.avia.dto.TicketUpdateDto;
import com.avia.model.entity.Ticket;

public interface TicketService {

    Ticket createTicket(TicketCreateDto ticketCreateDto);

    Ticket updateTicket(Long id, TicketUpdateDto ticketUpdateDto);
}
