package com.avia.controller.rest;

import com.avia.model.dto.FlightDto;
import com.avia.exception.EntityNotFoundException;
import com.avia.model.entity.Airport;
import com.avia.model.entity.Flight;
import com.avia.repository.FlightRepository;
import com.avia.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/flights")
public class FlightRestController {

    private final FlightRepository flightRepository;
    private final FlightService flightService;
    private static final Logger log = Logger.getLogger(FlightRestController.class);

    @GetMapping
    public ResponseEntity<List<Flight>> getAllFlights() {
        return new ResponseEntity<>(flightRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Object> getAllFlightsWithPageAndSort(@PathVariable int page) {

        Pageable pageable = PageRequest.of(page, 5, Sort.by("idFlight").ascending());

        Page<Flight> flights = flightRepository.findAll(pageable);

        if (flights.hasContent()) {
            return new ResponseEntity<>(flights, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Flight> createFlight(@RequestBody FlightDto flightDto) {
        Flight createdFlight = flightService.createFlight(flightDto);
        return new ResponseEntity<>(createdFlight, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable("id") Long id) {
        Optional<Flight> flight = flightRepository.findById(id);
        return flight.map(ResponseEntity::ok).orElseThrow(() ->
                new EntityNotFoundException("Flight with id " + id + " not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @RequestBody FlightDto flightDto) {
        Flight updatedFlight = flightService.updateFlight(id, flightDto);
        return new ResponseEntity<>(updatedFlight, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable("id") Long id) {
        Optional<Flight> flightOptional = flightRepository.findById(id);
        if (flightOptional.isPresent()) {
            flightRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("Flight with id " + id + " not found!");
        }
    }


    @PutMapping("/{id}/delete")
    public ResponseEntity<Void> softDeleteFlight(@PathVariable("id") Long id) {
        Optional<Flight> flightOptional = flightRepository.findById(id);

        if (flightOptional.isPresent()) {
            Flight flight = flightOptional.get();
            flight.setIsDeleted(true);
            flightRepository.save(flight);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("Flight with id " + id + " not found!");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Flight>> getFlightByIdArrivalAirport(@RequestParam(value = "id") Airport id) {
        List<Flight> flights = flightRepository.findFlightByIdArrivalAirport(id);
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @GetMapping("/search-by-number")
    public ResponseEntity<Flight> getFlightByNumber(@RequestParam(value = "number") String number) {
        Flight flight = flightRepository.findFlightByFlightNumber(number);
        return new ResponseEntity<>(flight, HttpStatus.OK);
    }

    @GetMapping("/search-by-departure")
    public ResponseEntity<List<Flight>> getFlightsByDepartureTime() {
        List<Flight> flights = flightRepository.findFlightsByDepartureTimeAfter(Timestamp.valueOf(LocalDateTime.now()));
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }
}
