package com.avia.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class TicketDto implements Serializable {

    private Long idPass;

    @NotNull
    private Integer idTicketStatus;

    @NotNull
    private Integer idTicketClass;

    @NotNull
    private Long idFlight;

    @NotNull
    private Integer idAirline;

    @Size(min = 3, max = 15)
    private String numberPlace;

    private BigDecimal price;
}