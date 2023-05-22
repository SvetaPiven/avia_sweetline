package com.avia.service;

import com.avia.model.entity.FlightStatus;
import com.avia.model.request.FlightStatusRequest;

public interface FlightStatusService {

    FlightStatus createFlightStatus(FlightStatusRequest flightStatusRequest);

    FlightStatus updateFlightStatus(Integer id, FlightStatusRequest flightStatusRequest);
}
