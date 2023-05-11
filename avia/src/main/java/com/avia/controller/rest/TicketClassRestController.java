package com.avia.controller.rest;

import com.avia.model.dto.TicketClassDto;
import com.avia.exception.EntityNotFoundException;
import com.avia.model.entity.TicketClass;
import com.avia.repository.TicketClassRepository;
import com.avia.service.TicketClassService;
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
@RequestMapping("/rest/ticket-class")
@RequiredArgsConstructor
public class TicketClassRestController {

    private final TicketClassRepository ticketClassRepository;

    private final TicketClassService ticketClassService;

    private static final Logger log = Logger.getLogger(TicketClassRestController.class);

    @GetMapping()
    public ResponseEntity<List<TicketClass>> getAllTicketClasses() {

        return new ResponseEntity<>(ticketClassRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TicketClass> createTicketClass(@RequestBody TicketClassDto ticketClassDto) {

        TicketClass createdTicketClass = ticketClassService.createTicketClass(ticketClassDto);

        return new ResponseEntity<>(createdTicketClass, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<TicketClass> getTicketClassById(@PathVariable("id") Integer id) {

        Optional<TicketClass> ticketClass = ticketClassRepository.findById(id);

        return ticketClass.map(ResponseEntity::ok).orElseThrow(() ->
                new EntityNotFoundException("Ticket class with id " + id + " not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketClass> updateTicketClass(@PathVariable Integer id, @RequestBody TicketClassDto ticketClassDto) {

        TicketClass updatedTicketClass = ticketClassService.updateTicketClass(id, ticketClassDto);

        return new ResponseEntity<>(updatedTicketClass, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicketClass(@PathVariable("id") Integer id) {

        Optional<TicketClass> ticketClassOptional = ticketClassRepository.findById(id);

        if (ticketClassOptional.isPresent()) {
            ticketClassRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("Ticket class with id " + id + " not found!");
        }
    }

    @PutMapping("/{id}/delete")
    public ResponseEntity<Void> softDeleteTicketClass(@PathVariable("id") Integer id) {

        Optional<TicketClass> ticketClassOptional = ticketClassRepository.findById(id);

        if (ticketClassOptional.isPresent()) {
            TicketClass ticketClass = ticketClassOptional.get();
            ticketClass.setIsDeleted(true);
            ticketClassRepository.save(ticketClass);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("Ticket class with id " + id + " not found!");
        }
    }
}
