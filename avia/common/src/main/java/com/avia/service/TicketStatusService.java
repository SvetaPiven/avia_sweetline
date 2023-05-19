package com.avia.service;

import com.avia.model.entity.TicketStatus;
import com.avia.model.request.TicketStatusRequest;

public interface TicketStatusService {

    TicketStatus createTicketStatus(TicketStatusRequest ticketStatusRequest);

    TicketStatus updateTicketStatus(Integer id, TicketStatusRequest ticketStatusRequest);
}
