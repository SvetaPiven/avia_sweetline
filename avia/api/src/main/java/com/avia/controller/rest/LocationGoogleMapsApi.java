package com.avia.controller.rest;

import com.avia.model.entity.Airport;
import com.avia.service.AirportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "LocationGoogleMapsApi", description = "GoogleMapsApi management method")
@RequestMapping("/api")
public class LocationGoogleMapsApi {

    private final AirportService airportService;

    @GetMapping("/location/{id}")
    public ResponseEntity<String> getAirportAddress(@PathVariable Long id) throws Exception {
        Airport airport = airportService.findById(id);

        String address = airportService.getAddressFromLatLng(airport.getLatitude(), airport.getLongitude());

        return ResponseEntity.ok(address);
    }
}