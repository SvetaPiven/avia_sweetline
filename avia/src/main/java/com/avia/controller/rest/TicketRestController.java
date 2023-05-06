package com.avia.controller.rest;

import com.avia.dto.requests.TicketDto;
import com.avia.exception.EntityNotFoundException;
import com.avia.model.entity.Ticket;
import com.avia.repository.TicketRepository;
import com.avia.service.TicketService;
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
@RequestMapping("/rest/tickets")
@RequiredArgsConstructor
public class TicketRestController {

    private final TicketRepository ticketRepository;

    private final TicketService ticketService;

    private static final Logger log = Logger.getLogger(TicketRestController.class);

    @GetMapping()
    public ResponseEntity<List<Ticket>> getAllTickets() {

        return new ResponseEntity<>(ticketRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody TicketDto ticketDto) {

        Ticket createdTicket = ticketService.createTicket(ticketDto);

        return new ResponseEntity<>(createdTicket, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable("id") Long id) {

        Optional<Ticket> ticket = ticketRepository.findById(id);

        return ticket.map(ResponseEntity::ok).orElseThrow(() ->
                new EntityNotFoundException("Ticket with id " + id + " not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable Long id, @RequestBody TicketDto ticketDto) {

        Ticket updatedTicket = ticketService.updateTicket(id, ticketDto);

        return new ResponseEntity<>(updatedTicket, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable("id") Long id) {

        Optional<Ticket> ticketOptional = ticketRepository.findById(id);

        if (ticketOptional.isPresent()) {
            ticketRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("Ticket with id " + id + " not found!");
        }
    }
}
