package com.avia.service;

import com.avia.model.dto.TicketClassDto;
import com.avia.model.entity.TicketClass;

public interface TicketClassService {

    TicketClass createTicketClass(TicketClassDto ticketClassDto);

    TicketClass updateTicketClass(Integer id, TicketClassDto ticketClassDto);
}
