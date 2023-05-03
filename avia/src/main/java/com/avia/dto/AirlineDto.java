package com.avia.dto;

import com.avia.model.entity.Ticket;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link com.avia.model.entity.Airline} entity
 */
@Data
public class AirlineDto implements Serializable {
    private Integer idAirline;
    @Size(max = 50)
    @NotNull
    private String nameAirline;
    @Size(max = 3)
    @NotNull
    private String codeAirline;
    private Set<Ticket> tickets;
}