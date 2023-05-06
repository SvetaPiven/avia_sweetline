package com.avia.dto.requests;

import com.avia.model.entity.FlightStatus;
import com.avia.model.entity.PlaneType;
import com.avia.model.entity.Ticket;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Data
public class FlightDto implements Serializable {

    @Size(max = 10)
    @NotNull
    private String flightNumber;

    @NotNull
    private PlaneType idPlaneType;

    @NotNull
    private AirportDto idDepartureAirport;

    @NotNull
    private AirportDto idArrivalAirport;

    @NotNull
    private FlightStatus idFlightStatus;

    @NotNull
    private Timestamp departureTime;

    @NotNull
    private Timestamp arrivalTime;

}