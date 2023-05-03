package com.avia.dto;

import com.avia.model.entity.TicketClass;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;


@Data
@Setter
@Getter
public class TicketCreateDto implements Serializable {
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
    private String numberPlace;
    private BigDecimal price;
}