package com.avia.service;

import com.avia.dto.requests.FlightDto;
import com.avia.model.entity.Flight;

public interface FlightService {

    Flight createFlight(FlightDto flightDto);

    Flight updateFlight(Long id, FlightDto flightDto);
}