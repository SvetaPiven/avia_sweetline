package com.avia.service;

import com.avia.model.dto.FlightDto;
import com.avia.model.entity.Flight;

public interface FlightService {

    Flight createFlight(FlightDto flightDto);

    Flight updateFlight(Long id, FlightDto flightDto);
}
