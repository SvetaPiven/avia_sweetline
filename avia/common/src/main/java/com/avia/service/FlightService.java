package com.avia.service;

import com.avia.model.request.FlightRequest;
import com.avia.model.entity.Flight;

import java.util.Optional;

public interface FlightService {

    Flight createFlight(FlightRequest flightRequest);

    Flight updateFlight(Long id, FlightRequest flightRequest);

    Flight findById(Long idFlight);
}
