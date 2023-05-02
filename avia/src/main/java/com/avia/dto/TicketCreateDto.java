package com.avia.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;


@Data
@Setter
@Getter
public class TicketCreateDto implements Serializable {

    private Long idPass;

    private Integer idTicketStatus;

    private Integer idTicketClass;

    private Long idFlight;

    private Integer idAirline;

    private String numberPlace;

    private BigDecimal price;

}