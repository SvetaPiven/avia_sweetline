package com.avia.dto;

import com.avia.model.entity.FlightStatus;
import com.avia.model.entity.PlaneType;
import com.avia.model.entity.Ticket;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * A DTO for the {@link com.avia.model.entity.Flight} entity
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
    private final Set<Ticket> tickets;
    private final FlightStatus flightStatus;
    private final AirportDto departure;
    private final AirportDto arrival;
    private final PlaneType planeTypes;
}