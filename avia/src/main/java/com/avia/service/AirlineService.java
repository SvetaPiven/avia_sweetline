package com.avia.service;

import com.avia.dto.requests.AirlineDto;
import com.avia.model.entity.Airline;

public interface AirlineService {

    Airline createAirline(AirlineDto airlineDto);

    Airline updateAirline(Integer id, AirlineDto airlineDto);
}