package com.avia.util;

import com.avia.model.entity.Airport;
import com.avia.model.entity.Flight;
import com.avia.service.AirportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FlightCoordinatesTaker {

    private final AirportService airportService;

    public List<Double> flightCoordinatesTaker(Flight flight){
        Airport departureAirport = airportService.findById(flight.getDepartureAirport().getIdAirport());
        Airport arrivalAirport = airportService.findById(flight.getArrivalAirport().getIdAirport());

        double latitudeDeparture = departureAirport.getLatitude();
        double longitudeDeparture = departureAirport.getLongitude();
        double latitudeArrival = arrivalAirport.getLatitude();
        double longitudeArrival = arrivalAirport.getLongitude();

        return List.of(latitudeDeparture, longitudeDeparture, latitudeArrival, longitudeArrival);
    }
}
