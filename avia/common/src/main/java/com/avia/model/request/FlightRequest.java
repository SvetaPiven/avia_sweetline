package com.avia.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Validated
public class FlightRequest implements Serializable {

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