package com.avia.controller.rest;

import com.avia.exception.ValidationException;
import com.avia.model.entity.Ticket;
import com.avia.model.entity.TicketStatus;
import com.avia.repository.TicketStatusRepository;
import com.avia.service.TicketStatusService;
import com.avia.model.request.TicketStatusRequest;
import com.avia.exception.EntityNotFoundException;
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
import org.springframework.validation.annotation.Validated;
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
@RequiredArgsConstructor
@RequestMapping("/rest/ticket-statuses")
public class TicketStatusRestController {

    private final TicketStatusRepository ticketStatusRepository;

    private final TicketStatusService ticketStatusService;

    @Value("${ticketStatus.page-capacity}")
    private Integer ticketStatusPageCapacity;

    @GetMapping()
    public ResponseEntity<List<TicketStatus>> getAllTicketStatuses() {

        List<TicketStatus> ticketStatuses = ticketStatusRepository.findAll();

        return ResponseEntity.ok(ticketStatuses);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Page<TicketStatus>> getAllTicketStatusesWithPageAndSort(@PathVariable int page) {

        Pageable pageable = PageRequest.of(page, ticketStatusPageCapacity, Sort.by("idTicketStatus").ascending());

        Page<TicketStatus> ticketStatuses = ticketStatusRepository.findAll(pageable);

        if (ticketStatuses.hasContent()) {
            return new ResponseEntity<>(ticketStatuses, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<TicketStatus> createTicketStatus(@Validated @RequestBody TicketStatusRequest ticketStatusRequest,
                                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        TicketStatus ticketStatus = ticketStatusService.createTicketStatus(ticketStatusRequest);

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
                                                           @Valid @RequestBody TicketStatusRequest ticketStatusRequest,
                                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }
        TicketStatus updatedTicketStatus = ticketStatusService.updateTicketStatus(id, ticketStatusRequest);
        return new ResponseEntity<>(updatedTicketStatus, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public String deleteTicketStatus(@PathVariable("id") Integer id) {
        Optional<TicketStatus> ticketStatusOptional = ticketStatusRepository.findById(id);

        if (ticketStatusOptional.isPresent()) {
            ticketStatusRepository.deleteById(id);
            return "Ticket status with ID " + id + " deleted successfully.";
        } else {
            throw new EntityNotFoundException("Ticket status with ID " + id + " not found!");
        }
    }

    @PutMapping("/{id}/status")
    public String changeStatus(@PathVariable("id") Integer id, @RequestParam("isDeleted") boolean isDeleted) {
        Optional<TicketStatus> ticketStatusOptional = ticketStatusRepository.findById(id);

        if (ticketStatusOptional.isPresent()) {
            TicketStatus ticketStatus = ticketStatusOptional.get();
            ticketStatus.setDeleted(isDeleted);
            ticketStatusRepository.save(ticketStatus);
            return "Status changed successfully";
        } else {
            throw new EntityNotFoundException("Ticket status with id " + id + " not found!");
        }
    }
}
