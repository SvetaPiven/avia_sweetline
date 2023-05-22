package com.avia.service.impl;

import com.avia.exception.EntityNotFoundException;
import com.avia.mapper.TicketStatusMapper;
import com.avia.model.entity.TicketStatus;
import com.avia.model.request.TicketStatusRequest;
import com.avia.repository.TicketStatusRepository;
import com.avia.service.TicketStatusService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketStatusServiceImpl implements TicketStatusService {

    private final TicketStatusMapper ticketStatusMapper;

    private final TicketStatusRepository ticketStatusRepository;

    @Override
    @Transactional
    public TicketStatus createTicketStatus(TicketStatusRequest ticketStatusRequest) {

        TicketStatus ticketStatus = ticketStatusMapper.toEntity(ticketStatusRequest);

        return ticketStatusRepository.save(ticketStatus);
    }

    @Override
    @Transactional
    public TicketStatus updateTicketStatus(Integer id, TicketStatusRequest ticketStatusRequest) {

        TicketStatus ticketStatus = ticketStatusRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Ticket status type with id " + id + " not found"));

        ticketStatusMapper.partialUpdate(ticketStatusRequest, ticketStatus);

        return ticketStatusRepository.save(ticketStatus);
    }
}
