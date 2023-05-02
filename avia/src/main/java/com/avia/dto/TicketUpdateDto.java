package com.avia.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class TicketUpdateDto implements Serializable {

    private final Long idPass;

    private final Integer idTicketStatus;

    private final Integer idTicketClass;

    private final Long idFlight;

    private final Integer idAirline;

    private final String numberPlace;

    private final BigDecimal price;

}