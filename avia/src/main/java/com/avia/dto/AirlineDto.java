package com.avia.dto;

import com.avia.model.entity.Ticket;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * A DTO for the {@link com.avia.model.entity.Airline} entity
 */
@Data
public class AirlineDto implements Serializable {

    private final Integer idAirline;

    private final String nameAirline;

    private final String codeAirline;

    private final Timestamp created;

    private final Timestamp changed;

    private final Boolean isDeleted;

    private final Set<Ticket> tickets;
}