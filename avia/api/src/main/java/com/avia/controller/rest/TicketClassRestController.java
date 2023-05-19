package com.avia.controller.rest;

import com.avia.model.entity.TicketClass;
import com.avia.model.entity.TicketStatus;
import com.avia.repository.TicketClassRepository;
import com.avia.service.TicketClassService;
import com.avia.model.request.TicketClassRequest;
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
@RequestMapping("/rest/ticket-class")
@RequiredArgsConstructor
public class TicketClassRestController {

    private final TicketClassRepository ticketClassRepository;

    private final TicketClassService ticketClassService;

    @Value("${ticketClass.page-capacity}")
    private Integer ticketClassPageCapacity;

    @GetMapping()
    public ResponseEntity<List<TicketClass>> getAllTicketClasses() {

        return new ResponseEntity<>(ticketClassRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Object> getAllTicketStatusesWithPageAndSort(@PathVariable int page) {

        Pageable pageable = PageRequest.of(page, ticketClassPageCapacity, Sort.by("idTicketClass").ascending());

        Page<TicketClass> ticketClasses = ticketClassRepository.findAll(pageable);

        if (ticketClasses.hasContent()) {
            return new ResponseEntity<>(ticketClasses, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<TicketClass> createTicketClass(@RequestBody TicketClassRequest ticketClassRequest) {

        TicketClass createdTicketClass = ticketClassService.createTicketClass(ticketClassRequest);

        return new ResponseEntity<>(createdTicketClass, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<TicketClass> getTicketClassById(@PathVariable("id") Integer id) {

        Optional<TicketClass> ticketClass = ticketClassRepository.findById(id);

        return ticketClass.map(ResponseEntity::ok).orElseThrow(() ->
                new EntityNotFoundException("Ticket class with id " + id + " not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketClass> updateTicketClass(@PathVariable Integer id, @RequestBody TicketClassRequest ticketClassRequest) {

        TicketClass updatedTicketClass = ticketClassService.updateTicketClass(id, ticketClassRequest);

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

    @PutMapping("/{id}/status")
    public String changeStatus(@PathVariable("id") Integer id, @RequestParam("isDeleted") boolean isDeleted) {
        Optional<TicketClass> ticketClassOptional = ticketClassRepository.findById(id);

        if (ticketClassOptional.isPresent()) {
            TicketClass ticketClass = ticketClassOptional.get();
            ticketClass.setDeleted(isDeleted);
            ticketClassRepository.save(ticketClass);
            return "Status changed successfully";
        } else {
            throw new EntityNotFoundException("Ticket class with id " + id + " not found!");
        }
    }
}
