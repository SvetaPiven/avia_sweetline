package com.avia.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link com.avia.model.entity.Airport} entity
 */
@Data
public class AirportDto implements Serializable {
    private Long idAirport;
    @Size(max = 50)
    @NotNull
    private String nameAirport;
    @Size(max = 50)
    @NotNull
    private String city;
    @NotNull
    private Float longitude;
    @NotNull
    private Float latitude;
    @NotNull
    private String timezone;
    @Size(max = 30)
    @NotNull
    private String country;
//    private Set<FlightDto> flightsArrival;
//    private Set<FlightDto> flightsDeparture;
}