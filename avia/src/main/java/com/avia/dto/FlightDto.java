package com.avia.dto;

import com.avia.model.entity.Flight;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * A DTO for the {@link Flight} entity
 */
@Data
public class FlightDto implements Serializable {
    private final Long idFlight;
    private final String flightNumber;
    private final Long idPlaneType;
    private final Long idDepartureAirport;
    private final Long idArrivalAirport;
    private final Long idFlightStatus;
    private final Timestamp departureTime;
    private final Timestamp arrivalTime;
    private final Timestamp created;
    private final Timestamp changed;
    private final Boolean isDeleted;
}