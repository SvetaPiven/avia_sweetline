package com.avia.service;

import com.avia.dto.requests.TicketClassDto;
import com.avia.model.entity.TicketClass;

public interface TicketClassService {

    TicketClass createTicketClass(TicketClassDto ticketClassDto);

    TicketClass updateTicketClass(Integer id, TicketClassDto ticketClassDto);
}
