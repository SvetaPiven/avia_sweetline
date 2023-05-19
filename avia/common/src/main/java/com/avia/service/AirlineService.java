package com.avia.service;

import com.avia.model.request.AirlineRequest;
import com.avia.model.entity.Airline;

import java.util.Optional;

public interface AirlineService {

    Airline createAirline(AirlineRequest airlineRequest);

    Airline updateAirline(Integer id, AirlineRequest airlineRequest);

    Airline findById(Integer idAirline);
}
