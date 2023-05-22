package com.avia.service.impl;

import com.avia.exception.EntityNotFoundException;
import com.avia.mapper.FlightMapper;
import com.avia.model.entity.Airport;
import com.avia.model.entity.Flight;
import com.avia.model.entity.FlightStatus;
import com.avia.model.entity.PlaneType;
import com.avia.model.request.FlightRequest;
import com.avia.repository.AirportRepository;
import com.avia.repository.FlightRepository;
import com.avia.repository.FlightStatusRepository;
import com.avia.repository.PlaneTypeRepository;
import com.avia.service.FlightService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;
    private final PlaneTypeRepository planeTypeRepository;
    private final AirportRepository airportRepository;
    private final FlightStatusRepository flightStatusRepository;

    @Override
    @Transactional
    public Flight createFlight(FlightRequest flightRequest) {
        PlaneType planeType = planeTypeRepository.findById(flightRequest.getIdPlaneType()).orElseThrow(() ->
                new EntityNotFoundException("Plane type with id " + flightRequest.getIdPlaneType() + " not found"));
        Flight flight = flightMapper.toEntity(flightRequest);
        flight.getPlaneType().setIdPlaneType(flight.getPlaneType().getIdPlaneType());
        flight.setPlaneType(planeType);

        Airport airportArrival = airportRepository.findById(flightRequest.getIdArrivalAirport()).orElseThrow(() ->
                new EntityNotFoundException("ArrivalAirport with id " + flightRequest.getIdArrivalAirport() + " not found"));
        flight.getArrivalAirport().setIdAirport(airportArrival.getIdAirport());
        flight.setArrivalAirport(airportArrival);

        Airport airportDeparture = airportRepository.findById(flightRequest.getIdDepartureAirport()).orElseThrow(() ->
                new EntityNotFoundException("DepartureAirport with id " + flightRequest.getIdDepartureAirport() + " not found"));
        flight.getDepartureAirport().setIdAirport(airportDeparture.getIdAirport());
        flight.setDepartureAirport(airportDeparture);

        FlightStatus flightStatus = flightStatusRepository.findById(flightRequest.getIdFlightStatus()).orElseThrow(() ->
                new EntityNotFoundException("Flight status with id " + flightRequest.getIdFlightStatus() + " not found"));
        flight.getFlightStatus().setIdFlightStatus(flight.getFlightStatus().getIdFlightStatus());
        flight.setFlightStatus(flightStatus);

        return flightRepository.save(flight);
    }

    @Override
    @Transactional
    public Flight updateFlight(Long id, FlightRequest flightRequest) {

        Flight flight = flightRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Flight with id " + id + " not found"));

        flightMapper.partialUpdate(flightRequest, flight);

        return flightRepository.save(flight);
    }

    @Override
    public Flight findById(Long idFlight) {
        return flightRepository.findById(idFlight)
                .orElseThrow(() -> new EntityNotFoundException("Flight with id " + idFlight + " not found"));
    }
}
