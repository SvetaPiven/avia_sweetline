package com.avia.service;

import com.avia.model.dto.AirlineDto;
import com.avia.model.entity.Airline;

import java.util.List;

public interface AirlineService {

    Airline createAirline(AirlineDto airlineDto);

    Airline updateAirline(Integer id, AirlineDto airlineDto);
}
