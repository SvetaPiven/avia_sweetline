package com.avia.controller.rest;

import com.avia.exception.EntityNotFoundException;
import com.avia.exception.ValidationException;
import com.avia.model.entity.TicketClass;
import com.avia.model.request.TicketClassRequest;
import com.avia.repository.TicketClassRepository;
import com.avia.service.TicketClassService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
import java.util.Objects;
import java.util.Optional;

@RestController
@Tag(name = "TicketClassRestController", description = "TicketClass management methods")
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
    public ResponseEntity<Page<TicketClass>> getAllTicketStatusesWithPageAndSort(@PathVariable int page) {

        Pageable pageable = PageRequest.of(page, ticketClassPageCapacity, Sort.by("idTicketClass").ascending());

        Page<TicketClass> ticketClasses = ticketClassRepository.findAll(pageable);

        if (ticketClasses.hasContent()) {
            return new ResponseEntity<>(ticketClasses, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<TicketClass> createTicketClass(@Valid @RequestBody TicketClassRequest ticketClassRequest,
                                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

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
    public ResponseEntity<TicketClass> updateTicketClass(@PathVariable Integer id,
                                                         @Valid @RequestBody TicketClassRequest ticketClassRequest,
                                                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        TicketClass updatedTicketClass = ticketClassService.updateTicketClass(id, ticketClassRequest);

        return new ResponseEntity<>(updatedTicketClass, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public String deleteTicketClass(@PathVariable("id") Integer id) {
        Optional<TicketClass> ticketClassOptional = ticketClassRepository.findById(id);

        if (ticketClassOptional.isPresent()) {
            ticketClassRepository.deleteById(id);
            return "Ticket class with ID " + id + " deleted successfully.";
        } else {
            throw new EntityNotFoundException("Ticket class with ID " + id + " not found!");
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
