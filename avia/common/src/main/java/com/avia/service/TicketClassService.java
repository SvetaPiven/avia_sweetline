package com.avia.service;

import com.avia.model.entity.TicketClass;
import com.avia.model.request.TicketClassRequest;

public interface TicketClassService {

    TicketClass createTicketClass(TicketClassRequest ticketClassRequest);

    TicketClass updateTicketClass(Integer id, TicketClassRequest ticketClassRequest);
}
