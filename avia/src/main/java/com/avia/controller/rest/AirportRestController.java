package com.avia.controller.rest;

import com.avia.model.dto.AirportDto;
import com.avia.exception.EntityNotFoundException;
import com.avia.model.entity.Airport;
import com.avia.repository.AirportRepository;
import com.avia.service.AirportService;
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
    @RequestMapping("/rest/airports")
    public class AirportRestController {

        private final AirportRepository airportRepository;

        private final AirportService airportService;

        private static final Logger log = Logger.getLogger(com.avia.controller.rest.AirportRestController.class);

        @GetMapping()
        public ResponseEntity<List<Airport>> getAllAirlines() {

            return new ResponseEntity<>(airportRepository.findAll(), HttpStatus.OK);
        }

        @GetMapping("/page/{page}")
        public ResponseEntity<Object> getAllAirportsWithPageAndSort(@PathVariable int page) {

            Pageable pageable = PageRequest.of(page, 5, Sort.by("idAirport").ascending());

            Page<Airport> airports = airportRepository.findAll(pageable);

            if (airports.hasContent()) {
                return new ResponseEntity<>(airports, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }

        @PostMapping
        public ResponseEntity<Airport> createAirline(@RequestBody AirportDto airportDto) {

            Airport createdAirline = airportService.createAirport(airportDto);

            return new ResponseEntity<>(createdAirline, HttpStatus.CREATED);
        }


        @GetMapping("/{id}")
        public ResponseEntity<Airport> getAirlineById(@PathVariable("id") Long id) {

            Optional<Airport> airline = airportRepository.findById(id);

            return airline.map(ResponseEntity::ok).orElseThrow(() ->
                    new EntityNotFoundException("Airport with id " + id + " not found"));
        }

        @PutMapping("/{id}")
        public ResponseEntity<Airport> updateAirline(@PathVariable Long id, @RequestBody AirportDto airportDto) {

            Airport updatedAirport = airportService.updateAirport(id, airportDto);

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

        @PutMapping("/{id}/delete")
        public ResponseEntity<Void> softDeleteAirport(@PathVariable("id") Long id) {
            Optional<Airport> airportOptional = airportRepository.findById(id);
            if (airportOptional.isPresent()) {
                Airport airport = airportOptional.get();
                airport.setIsDeleted(true);
                airportRepository.save(airport);
                return ResponseEntity.noContent().build();
            } else {
                throw new EntityNotFoundException("Airport with id " + id + " not found!");
            }
        }

        @GetMapping("/popular")
        public List<Airport> getPopularAirports() {
            List<Object[]> result = airportRepository.findPopularAirports();
            List<Airport> airports = new ArrayList<>();
            for (Object[] row : result) {
                Airport airport = new Airport();
                airport.setIdAirport((Long) row[0]);
                airport.setNameAirport((String) row[1]);
                airport.setCity((String) row[2]);
                airport.setCountry((String) row[3]);
                airports.add(airport);
            }
            return airports;
        }
    }


