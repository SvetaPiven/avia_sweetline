package com.avia.controller.rest;

import com.avia.model.entity.Airline;
import com.avia.model.entity.DocumentPass;
import com.avia.repository.AirlineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/airlines")
public class AirlineController {

    private final AirlineRepository airlineRepository;

    @GetMapping()
    public ResponseEntity<List<Airline>> getAllAirlines() {
        List<Airline> airlines = airlineRepository.findAll();
        return new ResponseEntity<>(airlines, HttpStatus.OK);
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

