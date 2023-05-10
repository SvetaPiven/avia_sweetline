package com.avia.controller.rest;

import com.avia.model.entity.Airport;
import com.avia.repository.AirportRepository;
import com.avia.service.LocationService;
import com.google.maps.errors.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.stream.Location;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LocationGoogle {
    private final LocationService locationService;
    private final AirportRepository airportRepository;

    @GetMapping("/location/{id}")
    public ResponseEntity<String> getAirportAddress(@PathVariable Long id) throws Exception {
        Airport airport = airportRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Location not found"));

        String address = locationService.getAddressFromLatLng(airport.getLatitude(), airport.getLongitude());

        return ResponseEntity.ok(address);
    }
}