package com.avia.controller.rest;

import com.avia.model.entity.Ticket;
import com.avia.model.entity.TicketClass;
import com.avia.repository.TicketRepository;
import com.avia.service.TicketService;
import com.avia.model.request.TicketRequest;
import com.avia.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/tickets")
@RequiredArgsConstructor
public class TicketRestController {

    private final TicketRepository ticketRepository;

    private final TicketService ticketService;

    @Value("${ticket.page-capacity}")
    private Integer ticketPageCapacity;

    @GetMapping()
    public ResponseEntity<List<Ticket>> getAllTickets() {

        return new ResponseEntity<>(ticketRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Page<Ticket>> getAllTicketsWithPageAndSort(@PathVariable int page) {

        Pageable pageable = PageRequest.of(page, ticketPageCapacity, Sort.by("idTicket").ascending());

        Page<Ticket> tickets = ticketRepository.findAll(pageable);

        if (tickets.hasContent()) {
            return new ResponseEntity<>(tickets, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody TicketRequest ticketRequest) {

        Ticket createdTicket = ticketService.createTicket(ticketRequest);

        return new ResponseEntity<>(createdTicket, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable("id") Long id) {

        Optional<Ticket> ticket = ticketRepository.findById(id);

        return ticket.map(ResponseEntity::ok).orElseThrow(() ->
                new EntityNotFoundException("Ticket with id " + id + " not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable Long id, @RequestBody TicketRequest ticketRequest) {

        Ticket updatedTicket = ticketService.updateTicket(id, ticketRequest);

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

    @PutMapping("/{id}/status")
    public String changeStatus(@PathVariable("id") Long id, @RequestParam("isDeleted") boolean isDeleted) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);

        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();
            ticket.setDeleted(isDeleted);
            ticketRepository.save(ticket);
            return "Status changed successfully";
        } else {
            throw new EntityNotFoundException("Ticket with id " + id + " not found!");
        }
    }
}
