package com.avia.controller.rest;

import com.avia.dto.requests.TicketStatusDto;
import com.avia.exception.EntityNotFoundException;
import com.avia.model.entity.TicketStatus;
import com.avia.repository.TicketStatusRepository;
import com.avia.service.TicketStatusService;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/ticket-statuses")
public class TicketStatusRestController {

    private final TicketStatusRepository ticketStatusRepository;

    private final TicketStatusService ticketStatusService;

    private static final Logger log = Logger.getLogger(TicketStatusRestController.class);

    @GetMapping()
    public ResponseEntity<List<TicketStatus>> getAllTicketStatuses() {

        List<TicketStatus> ticketStatuses = ticketStatusRepository.findAll();

        return ResponseEntity.ok(ticketStatuses);
    }

    @PostMapping
    public ResponseEntity<TicketStatus> createTicketStatus(@RequestBody TicketStatusDto ticketStatusDto) {

        TicketStatus ticketStatus = ticketStatusService.createTicketStatus(ticketStatusDto);

        return new ResponseEntity<>(ticketStatus, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketStatus> getTicketStatusById(@PathVariable("id") Integer id) {

        Optional<TicketStatus> ticketStatusOptional = ticketStatusRepository.findById(id);

        return ticketStatusOptional.map(ResponseEntity::ok).orElseThrow(() ->
                new EntityNotFoundException("Ticket status with id " + id + " not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketStatus> updateTicketStatus(@PathVariable Integer id,
                                                           @RequestBody TicketStatusDto ticketStatusDto) {
        TicketStatus updatedTicketStatus = ticketStatusService.updateTicketStatus(id, ticketStatusDto);
        return new ResponseEntity<>(updatedTicketStatus, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicketStatus(@PathVariable("id") Integer id) {

        Optional<TicketStatus> ticketStatusOptional = ticketStatusRepository.findById(id);

        if (ticketStatusOptional.isPresent()) {
            ticketStatusRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("Ticket status with id " + id + " not found");
        }
    }
}
