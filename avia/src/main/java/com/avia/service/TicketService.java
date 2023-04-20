package com.avia.service;

import com.avia.model.entity.Ticket;

import java.util.List;

public interface TicketService {
    List<Ticket> findAll();
}
