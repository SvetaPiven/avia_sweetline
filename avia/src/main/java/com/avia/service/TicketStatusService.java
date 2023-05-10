package com.avia.service;

import com.avia.model.entity.requests.TicketStatusDto;
import com.avia.model.entity.TicketStatus;

public interface TicketStatusService {

    TicketStatus createTicketStatus(TicketStatusDto ticketStatusDto);

    TicketStatus updateTicketStatus(Integer id, TicketStatusDto ticketStatusDto);
}
