package com.avia.service;

import com.avia.model.entity.TicketClass;
import com.avia.model.dto.TicketClassDto;

public interface TicketClassService {

    TicketClass createTicketClass(TicketClassDto ticketClassDto);

    TicketClass updateTicketClass(Integer id, TicketClassDto ticketClassDto);
}
