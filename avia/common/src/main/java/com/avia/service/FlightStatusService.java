package com.avia.service;

import com.avia.model.request.FlightStatusRequest;
import com.avia.model.entity.FlightStatus;

public interface FlightStatusService {

    FlightStatus createFlightStatus(FlightStatusRequest flightStatusRequest);

    FlightStatus updateFlightStatus(Integer id, FlightStatusRequest flightStatusRequest);
}
