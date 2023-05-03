package com.avia.dto;

import com.avia.model.entity.TicketClass;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link com.avia.model.entity.Ticket} entity
 */
@Data
public class TicketDto implements Serializable {
    private Long idTicket;
    private PassengerDto idPass;
    @NotNull
    private TicketStatusDto idTicketStatus;
    @NotNull
    private TicketClass idTicketClass;
    @NotNull
    private FlightDto idFlight;
    @NotNull
    private AirlineDto idAirline;
    @Size(max = 5)
    private String idPlace;
    private BigDecimal price;
}