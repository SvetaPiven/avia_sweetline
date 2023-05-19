package com.avia.service.impl;

import com.avia.model.entity.TicketClass;
import com.avia.model.request.TicketClassRequest;
import com.avia.repository.TicketClassRepository;
import com.avia.exception.EntityNotFoundException;
import com.avia.mapper.TicketClassMapper;
import com.avia.service.TicketClassService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketClassServiceImpl implements TicketClassService {

    private final TicketClassRepository ticketClassRepository;

    private final TicketClassMapper ticketClassMapper;

    @Override
    @Transactional
    public TicketClass createTicketClass(TicketClassRequest ticketClassRequest) {
        TicketClass ticketClass = ticketClassMapper.toEntity(ticketClassRequest);
        return ticketClassRepository.save(ticketClass);
    }

    @Override
    @Transactional
    public TicketClass updateTicketClass(Integer id, TicketClassRequest ticketClassRequest) {
        TicketClass ticketClass = ticketClassRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Ticket class with id " + id + " not found"));
        ticketClassMapper.partialUpdate(ticketClassRequest, ticketClass);
        return ticketClassRepository.save(ticketClass);
    }

    @Override
    public TicketClass findById(Integer idTicketClass) {
        return ticketClassRepository.findById(idTicketClass)
                .orElseThrow(() -> new EntityNotFoundException("Ticket class with id " + idTicketClass + " not found"));
    }
}
