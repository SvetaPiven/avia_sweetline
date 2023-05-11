package com.avia.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class FlightDto implements Serializable {

    @Size(min = 6, max = 10)
    @NotNull
    private String flightNumber;

    @NotNull
    private Integer idPlaneType;

    @NotNull
    private Long idDepartureAirport;

    @NotNull
    private Long idArrivalAirport;

    @NotNull
    private Integer idFlightStatus;

    @NotNull
    private Timestamp departureTime;

    @NotNull
    private Timestamp arrivalTime;

}