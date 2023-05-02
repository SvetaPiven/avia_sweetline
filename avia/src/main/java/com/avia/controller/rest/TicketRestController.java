package com.avia.controller.rest;

import com.avia.dto.TicketCreateDto;
import com.avia.dto.TicketUpdateDto;
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
    public ResponseEntity<Ticket> createTicket(@RequestBody TicketCreateDto ticketCreateDto) {

        Ticket createdTicket = ticketService.createTicket(ticketCreateDto);

        return new ResponseEntity<>(createdTicket, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable("id") Long id) {

        Optional<Ticket> ticket = ticketRepository.findById(id);

        return ticket.map(ResponseEntity::ok).orElseThrow(() ->
                new EntityNotFoundException("Ticket with id " + id + " not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable Long id, @RequestBody TicketUpdateDto ticketUpdateDto) {

        Ticket updatedTicket = ticketService.updateTicket(id, ticketUpdateDto);

        return new ResponseEntity<>(updatedTicket, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable("id") Long id) {

        Optional<Ticket> ticketOptional = ticketRepository.findById(id);

        if (ticketOptional.isPresent()) {
            ticketRepository.deleteById(id);
            log.info("Ticket with this id is deleted!");
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("Ticket with id " + id + " not found!");
        }
    }
}
//    @PostMapping
//    public ResponseEntity<Ticket> createTicket(@RequestBody TicketCreateRequest request) {
//
//        Ticket build = Ticket.builder()
//                .idTicket(request.getIdTicket())
//                .idPass(request.getIdPass())
//                .idTicketClass(request.getIdTicketClass())
//                .price(request.getPrice())
//                .idFlight(request.getIdFlight())
//                .idPlace(request.getIdPlace())
//                .idTicketClass(request.getIdTicketClass())
//                .idAirline(request.getIdAirline())
//                .build();
//
//        Ticket createdTicket = ticketService.create(build);
//
//        return new ResponseEntity<>(createdTicket, HttpStatus.CREATED);
//    }

//    @PatchMapping
//    public ResponseEntity<Ticket> partialUpdateTicket(@RequestBody TicketCreateRequest request) {
//
//        Ticket ticket = ticketService.update(Ticket.builder()
//                .idTicket(request.getIdTicket())
//                .idPass(request.getIdPass())
//                .idTicketClass(request.getIdTicketClass())
//                .price(request.getPrice())
//                .idFlight(request.getIdFlight())
//                .idPlace(request.getIdPlace())
//                .idTicketClass(request.getIdTicketClass())
//                .idAirline(request.getIdAirline())
//                .build());
//
//        return new ResponseEntity<>(ticket, HttpStatus.OK);
//    }
//
//    @DeleteMapping
//    public ResponseEntity<Optional<Ticket>> deleteTicket(@RequestBody TicketCreateRequest request) {
//
//        Optional<Ticket> ticket = ticketService.deleteById(request.getIdTicket());
//
//        return new ResponseEntity<>(ticket, HttpStatus.OK);
//    }
//
//    @GetMapping("/search/{id}")
//    public ResponseEntity<BigDecimal> searchMostExpensiveTicket(@PathVariable Long id) {
//
//        BigDecimal price = ticketRepositoryJdbcTemplate.findMostExpensiveTicket(id);
//
//        return new ResponseEntity<>(price, HttpStatus.OK);
//    }
//
//    @GetMapping("/calculate")
//    public ResponseEntity<BigDecimal> calculateProfitAirline(@RequestParam(value = "query") Long query) {
//
//        BigDecimal price = ticketRepositoryJdbcTemplate.profitAirline(query);
//
//        return new ResponseEntity<>(price, HttpStatus.OK);
//    }
