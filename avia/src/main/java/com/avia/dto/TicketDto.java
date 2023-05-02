package com.avia.dto;

import com.avia.model.entity.TicketClass;
import com.avia.model.entity.TicketStatus;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * A DTO for the {@link com.avia.model.entity.Ticket} entity
 */
@Data
public class TicketDto implements Serializable {

    private final Long idTicket;

    private final Long idPass;

    private final Long idTicketStatus;

    private final Long idTicketClass;

    private final Long idFlight;

    private final Integer idAirline;

    private final String numberPlace;

    private final BigDecimal price;

    private final Timestamp created;

    private final Timestamp changed;

    private final Boolean isDeleted;

    private final AirlineDto airlines;

    private final TicketStatus ticketStatus;

    private final TicketClass ticketClass;

    private final FlightDto flights;

    private final PassengerDto passengers;
}