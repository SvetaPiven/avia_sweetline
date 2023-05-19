package com.avia.service.impl;

import com.avia.model.entity.Airport;
import com.avia.repository.AirportRepository;
import com.avia.service.AirportService;
import com.avia.model.request.AirportRequest;
import com.avia.exception.EntityNotFoundException;
import com.avia.mapper.AirportMapper;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService {

    private final AirportMapper airportMapper;

    private final AirportRepository airportRepository;

    private final GeoApiContext geoApiContext;

    @Override
    @Transactional
    public Airport createAirport(AirportRequest airportRequest) {

        Airport airport = airportMapper.toEntity(airportRequest);

        return airportRepository.save(airport);
    }

    @Override
    @Transactional
    public Airport updateAirport(Long id, AirportRequest airportRequest) {

        Airport airport = airportRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Airport with id " + id + " not found"));

        airportMapper.partialUpdate(airportRequest, airport);

        return airportRepository.save(airport);
    }

    @Override
    public String getAddressFromLatLng(Float latitude, Float longitude) throws Exception {
        GeocodingResult[] results = GeocodingApi.newRequest(geoApiContext)
                .latlng(new LatLng(latitude, longitude))
                .await();

        if (results.length > 0) {
            return results[0].formattedAddress;
        } else {
            throw new Exception("No results found");
        }
    }

    @Override
    public Airport findById(Long idFlight) {

        return airportRepository.findById(idFlight)
                .orElseThrow(() -> new IllegalArgumentException("Departure airport not found"));
    }
}
