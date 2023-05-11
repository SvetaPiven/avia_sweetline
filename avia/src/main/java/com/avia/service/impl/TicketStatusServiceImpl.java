package com.avia.service.impl;

import com.avia.model.dto.TicketStatusDto;
import com.avia.exception.EntityNotFoundException;
import com.avia.mapper.TicketStatusMapper;
import com.avia.model.entity.TicketStatus;
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
    public TicketStatus createTicketStatus(TicketStatusDto ticketStatusDto) {

        TicketStatus ticketStatus = ticketStatusMapper.toEntity(ticketStatusDto);

        return ticketStatusRepository.save(ticketStatus);
    }

    @Override
    @Transactional
    public TicketStatus updateTicketStatus(Integer id, TicketStatusDto ticketStatusDto) {

        TicketStatus ticketStatus = ticketStatusRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Ticket status type with id " + id + " not found"));

        ticketStatusMapper.partialUpdate(ticketStatusDto, ticketStatus);

        return ticketStatusRepository.save(ticketStatus);
    }
}
