package com.avia.controller.rest;

import com.avia.model.entity.Passenger;
import com.avia.repository.PassengerRepository;
import com.avia.service.LocationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Tag(name = "LocationRestController", description = "Location management method")
@RequestMapping("/rest/passengers")
public class LocationRestController {

    private final LocationService locationService;

    private final PassengerRepository passengerRepository;

    @PutMapping("/calculate-miles")
    public ResponseEntity<String> calculatePassengerMiles() {

        List<Passenger> passengers = passengerRepository.findAll();
        for (Passenger passenger : passengers) {
            locationService.calculatePassengerMiles(passenger);
        }
        return ResponseEntity.ok("Miles calculated for all passengers.");
    }
}
