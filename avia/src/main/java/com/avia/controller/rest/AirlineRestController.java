package com.avia.controller.rest;

import com.avia.model.dto.AirlineDto;
import com.avia.exception.EntityNotFoundException;
import com.avia.model.entity.Airline;
import com.avia.repository.AirlineRepository;
import com.avia.service.AirlineService;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/airlines")
public class AirlineRestController {

    private final AirlineRepository airlineRepository;

    private final AirlineService airlineService;

    private static final Logger log = Logger.getLogger(AirlineRestController.class);

    @GetMapping()
    public ResponseEntity<List<Airline>> getAllAirlines() {

        return new ResponseEntity<>(airlineRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Object> getAllAirlinesWithPageAndSort(@PathVariable int page) {

        Pageable pageable = PageRequest.of(page, 4, Sort.by("idAirline").ascending());

        Page<Airline> airlines = airlineRepository.findAll(pageable);

        if (airlines.hasContent()) {
            return new ResponseEntity<>(airlines, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Airline> createAirline(@RequestBody AirlineDto airlineDto) {

        Airline createdAirline = airlineService.createAirline(airlineDto);

        return new ResponseEntity<>(createdAirline, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Airline> getAirlineById(@PathVariable("id") Integer id) {

        Optional<Airline> airline = airlineRepository.findById(id);

        return airline.map(ResponseEntity::ok).orElseThrow(() ->
                new EntityNotFoundException("Airline with id " + id + " not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Airline> updateAirline(@PathVariable Integer id, @RequestBody AirlineDto airlineDto) {

        Airline updatedAirline = airlineService.updateAirline(id, airlineDto);

        return new ResponseEntity<>(updatedAirline, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable("id") Integer id) {
        Optional<Airline> airlineOptional = airlineRepository.findById(id);

        if (airlineOptional.isPresent()) {
            airlineRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("Airline with id " + id + " not found!");
        }
    }

    @PutMapping("/{id}/delete")
    public ResponseEntity<Void> softDeleteAirline(@PathVariable("id") Integer id) {
        Optional<Airline> airlineOptional = airlineRepository.findById(id);

        if (airlineOptional.isPresent()) {
            Airline airline = airlineOptional.get();
            airline.setIsDeleted(true);
            airlineRepository.save(airline);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("Airline with id " + id + " not found!");
        }
    }

    @GetMapping("/popular")
    public List<Airline> getPopularAirlines() {
        List<Object[]> result = airlineRepository.findPopularAirlines();
        List<Airline> airlines = new ArrayList<>();
        for (Object[] row : result) {
            Airline airline = new Airline();
            airline.setIdAirline((Integer) row[0]);
            airline.setNameAirline((String) row[1]);
            airline.setCodeAirline((String) row[2]);
            airlines.add(airline);
        }
        return airlines;
    }
}

