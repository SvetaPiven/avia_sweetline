package com.avia.service.impl;

import com.avia.dto.requests.FlightDto;
import com.avia.exception.EntityNotFoundException;
import com.avia.mapper.FlightMapper;
import com.avia.model.entity.Airline;
import com.avia.model.entity.Airport;
import com.avia.model.entity.Flight;
import com.avia.model.entity.FlightStatus;
import com.avia.model.entity.PlaneType;
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
    public Flight createFlight(FlightDto flightDto) {
        PlaneType planeType = planeTypeRepository.findById(flightDto.getIdPlaneType()).orElseThrow(() ->
                new EntityNotFoundException("Plane type with id " + flightDto.getIdPlaneType() + " not found"));
        Flight flight = flightMapper.toEntity(flightDto);
        flight.getIdPlaneType().setIdPlaneType(flight.getIdPlaneType().getIdPlaneType());
        flight.setIdPlaneType(planeType);

        Airport airportArrival = airportRepository.findById(flightDto.getIdArrivalAirport()).orElseThrow(() ->
                new EntityNotFoundException("ArrivalAirport with id " + flightDto.getIdArrivalAirport() + " not found"));
        flight.getIdArrivalAirport().setIdAirport(airportArrival.getIdAirport());
        flight.setIdArrivalAirport(airportArrival);

        Airport airportDeparture = airportRepository.findById(flightDto.getIdDepartureAirport()).orElseThrow(() ->
                new EntityNotFoundException("DepartureAirport with id " + flightDto.getIdDepartureAirport() + " not found"));
        flight.getIdDepartureAirport().setIdAirport(airportDeparture.getIdAirport());
        flight.setIdDepartureAirport(airportDeparture);

        FlightStatus flightStatus = flightStatusRepository.findById(flightDto.getIdFlightStatus()).orElseThrow(() ->
                new EntityNotFoundException("Flight status with id " + flightDto.getIdFlightStatus() + " not found"));
        flight.getIdFlightStatus().setIdFlightStatus(flight.getIdFlightStatus().getIdFlightStatus());
        flight.setIdFlightStatus(flightStatus);

        return flightRepository.save(flight);
    }

    @Override
    @Transactional
    public Flight updateFlight(Long id, FlightDto flightDto) {

        Flight flight = flightRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Flight with id " + id + " not found"));

        flightMapper.partialUpdate(flightDto, flight);

        return flightRepository.save(flight);
    }
}
