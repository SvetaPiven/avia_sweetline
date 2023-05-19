package com.avia.controller.rest;

import com.avia.exception.ValidationException;
import com.avia.model.entity.Airline;
import com.avia.model.entity.Airport;
import com.avia.repository.AirportRepository;
import com.avia.service.AirportService;
import com.avia.model.request.AirportRequest;
import com.avia.exception.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/airports")
public class AirportRestController {

    private final AirportRepository airportRepository;

    private final AirportService airportService;

    @Value("${airports.page-capacity}")
    private Integer airportsPageCapacity;

    @GetMapping()
    public ResponseEntity<List<Airport>> getAllAirlines() {

        return new ResponseEntity<>(airportRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Object> getAllAirportsWithPageAndSort(@PathVariable int page) {

        Pageable pageable = PageRequest.of(page, airportsPageCapacity, Sort.by("idAirport").ascending());

        Page<Airport> airports = airportRepository.findAll(pageable);

        if (airports.hasContent()) {
            return new ResponseEntity<>(airports, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Airport> createAirline(@Valid @RequestBody AirportRequest airportRequest,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        Airport createdAirline = airportService.createAirport(airportRequest);

        return new ResponseEntity<>(createdAirline, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Airport> getAirlineById(@PathVariable("id") Long id) {

        Optional<Airport> airline = airportRepository.findById(id);

        return airline.map(ResponseEntity::ok).orElseThrow(() ->
                new EntityNotFoundException("Airport with id " + id + " not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Airport> updateAirline(@PathVariable Long id,
                                                 @Valid @RequestBody AirportRequest airportRequest,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        Airport updatedAirport = airportService.updateAirport(id, airportRequest);

        return new ResponseEntity<>(updatedAirport, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable("id") Long id) {

        Optional<Airport> airportOptional = airportRepository.findById(id);

        if (airportOptional.isPresent()) {
            airportRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("Airport with id " + id + " not found!");
        }
    }

    @PutMapping("/{id}/status")
    public String changeStatus(@PathVariable("id") Long id, @RequestParam("isDeleted") boolean isDeleted) {
        Optional<Airport> airportOptional = airportRepository.findById(id);
        if (airportOptional.isPresent()) {
            Airport airport = airportOptional.get();
            airport.setDeleted(isDeleted);
            airportRepository.save(airport);
            return "Status changed successfully";
        } else {
            throw new EntityNotFoundException("Airport with id " + id + " not found!");
        }
    }

    @GetMapping("/popular")
    public List<Airport> getPopularAirports() {
        return airportRepository.findPopularAirports();
    }
}


