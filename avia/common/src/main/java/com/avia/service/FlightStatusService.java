package com.avia.service;

import com.avia.model.dto.FlightStatusDto;
import com.avia.model.entity.FlightStatus;

public interface FlightStatusService {

    FlightStatus createFlightStatus(FlightStatusDto flightStatusDto);

    FlightStatus updateFlightStatus(Integer id, FlightStatusDto flightStatusDto);
}