package com.avia.controller.rest;

import com.avia.exception.EntityNotFoundException;
import com.avia.exception.ValidationException;
import com.avia.model.entity.Ticket;
import com.avia.model.request.TicketRequest;
import com.avia.repository.TicketRepository;
import com.avia.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@Tag(name = "TicketRestController", description = "Ticket management methods")
@RequestMapping("/rest/tickets")
@RequiredArgsConstructor
public class TicketRestController {

    private final TicketRepository ticketRepository;

    private final TicketService ticketService;

    @Value("${ticket.page-capacity}")
    private Integer ticketPageCapacity;

    @Operation(
            summary = "Get All Tickets",
            description = "List of flight tickets for all passengers",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "Successfully loaded tickets",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ticket.class))
                            )
                    )
            }
    )
    @GetMapping()
    public ResponseEntity<List<Ticket>> getAllTickets() {

        return new ResponseEntity<>(ticketRepository.findAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "Get All Tickets with Paging and Sorting",
            description = "Get a paginated list of all tickets with optional sorting",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "Successfully loaded tickets",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Ticket.class)))
                    ),
                    @ApiResponse(
                            responseCode = "NOT_FOUND",
                            description = "Tickets not found"
                    )
            }
    )
    @GetMapping("/page/{page}")
    public ResponseEntity<Page<Ticket>> getAllTicketsWithPageAndSort(
            @Parameter(name = "page", example = "0", required = true) @PathVariable int page) {

        Pageable pageable = PageRequest.of(page, ticketPageCapacity, Sort.by("idTicket").ascending());

        Page<Ticket> tickets = ticketRepository.findAll(pageable);

        if (tickets.hasContent()) {
            return new ResponseEntity<>(tickets, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Create a Ticket",
            description = "Create a new ticket",
            responses = {
                    @ApiResponse(
                            responseCode = "CREATED",
                            description = "Ticket created",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Ticket.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "BAD_REQUEST",
                            description = "Validation error"
                    )

            }
    )
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @PostMapping
    public ResponseEntity<Ticket> createTicket(@Valid @RequestBody @Parameter(description = "Ticket information", required = true)
                                               TicketRequest ticketRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        Ticket createdTicket = ticketService.createTicket(ticketRequest);

        return new ResponseEntity<>(createdTicket, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Ticket by ID",
            description = "Get a ticket by its ID",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "Ticket found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Ticket.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "NOT_FOUND",
                            description = "Ticket not found"
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@Parameter(description = "Ticket ID", example = "1", required = true)
                                                @PathVariable("id") Long id) {

        Optional<Ticket> ticket = ticketRepository.findById(id);

        return ticket.map(ResponseEntity::ok).orElseThrow(() ->
                new EntityNotFoundException("Ticket with id " + id + " not found"));
    }

    @Operation(
            summary = "Update a Ticket",
            description = "Update an existing ticket",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "Ticket updated",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Ticket.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "NOT_FOUND",
                            description = "Ticket not found"
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable Long id, @RequestBody TicketRequest ticketRequest) {

        Ticket updatedTicket = ticketService.updateTicket(id, ticketRequest);

        return new ResponseEntity<>(updatedTicket, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete a Ticket (for admins only)",
            description = "Delete a ticket by its ID",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "Ticket deleted"
                    ),
                    @ApiResponse(
                            responseCode = "NOT_FOUND",
                            description = "Ticket not found"
                    )
            }
    )
    @DeleteMapping("/{id}")
    public String deleteTicket(@Parameter(name = "id", example = "1", required = true) @PathVariable("id") Long id) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);

        if (ticketOptional.isPresent()) {
            ticketRepository.deleteById(id);
            return "Ticket with ID " + id + " deleted successfully.";
        } else {
            throw new EntityNotFoundException("Ticket with ID " + id + " not found!");
        }
    }

    @Operation(
            summary = "Change Ticket Status (for admins only)",
            description = "Change the status of a ticket",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "Ticket status changed",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Ticket.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "NOT_FOUND",
                            description = "Ticket not found"
                    )
            }
    )
    @Secured("ROLE_ADMIN")
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

    @Operation(
            summary = "Find Most Expensive Ticket Price",
            description = "Get the price of the most expensive ticket for a passenger",
            responses = {
                    @ApiResponse(
                            responseCode = "OK",
                            description = "Price found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "number", format = "double")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "NOT_FOUND",
                            description = "No ticket found for the passenger"
                    )
            }
    )
    @GetMapping("/expensive/{id}")
    public ResponseEntity<BigDecimal> findMostExpensiveTicketPrice(@Parameter(description = "Passenger ID") @PathVariable Long id) {

        BigDecimal mostExpensivePrice = ticketService.findMostExpensiveTicketPrice(id);

        return ResponseEntity.ok(mostExpensivePrice);
    }
}
