package com.avia.controller.rest;

import com.avia.exception.EntityNotFoundException;
import com.avia.model.entity.Airport;
import com.avia.repository.AirportRepository;
import com.avia.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LocationGoogleMapsApi {

    private final AirportService airportService;
    private final AirportRepository airportRepository;

    @GetMapping("/location/{id}")
    public ResponseEntity<String> getAirportAddress(@PathVariable Long id) throws Exception {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Location with " + id + "not found"));

        String address = airportService.getAddressFromLatLng(airport.getLatitude(), airport.getLongitude());

        return ResponseEntity.ok(address);
    }
}