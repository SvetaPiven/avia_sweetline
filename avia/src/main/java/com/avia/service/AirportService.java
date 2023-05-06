package com.avia.service;

import com.avia.dto.requests.AirportDto;
import com.avia.model.entity.Airport;

public interface AirportService {

    Airport createAirport(AirportDto airportDto);

    Airport updateAirport(Long id, AirportDto airportDto);
}
