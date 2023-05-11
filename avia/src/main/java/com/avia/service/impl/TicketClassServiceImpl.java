package com.avia.service.impl;

import com.avia.model.dto.TicketClassDto;
import com.avia.exception.EntityNotFoundException;
import com.avia.mapper.TicketClassMapper;
import com.avia.model.entity.TicketClass;
import com.avia.repository.TicketClassRepository;
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
    public TicketClass createTicketClass(TicketClassDto ticketClassDto) {
        TicketClass ticketClass = ticketClassMapper.toEntity(ticketClassDto);
        return ticketClassRepository.save(ticketClass);
    }

    @Override
    @Transactional
    public TicketClass updateTicketClass(Integer id, TicketClassDto ticketClassDto) {
        TicketClass ticketClass = ticketClassRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Ticket class with id " + id + " not found"));
        ticketClassMapper.partialUpdate(ticketClassDto, ticketClass);
        return ticketClassRepository.save(ticketClass);
    }
}
