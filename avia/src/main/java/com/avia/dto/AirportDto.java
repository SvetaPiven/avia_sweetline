package com.avia.dto;

import com.avia.model.entity.Flight;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * A DTO for the {@link com.avia.model.entity.Airport} entity
 */
@Data
public class AirportDto implements Serializable {

    private final Long idAirport;

    private final String nameAirport;

    private final String city;

    private final Float longitude;

    private final Float latitude;

    private final String timezone;

    private final Timestamp created;

    private final Timestamp changed;

    private final Boolean isDeleted;

    private final String country;

    private final Set<Flight> departureAirport;

    private final Set<Flight> arrivalAirport;
}