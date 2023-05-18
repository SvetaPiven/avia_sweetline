package com.avia.service;

import com.avia.model.entity.TicketStatus;
import com.avia.model.dto.TicketStatusDto;

public interface TicketStatusService {

    TicketStatus createTicketStatus(TicketStatusDto ticketStatusDto);

    TicketStatus updateTicketStatus(Integer id, TicketStatusDto ticketStatusDto);
}
