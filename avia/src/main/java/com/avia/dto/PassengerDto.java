package com.avia.dto;

import com.avia.model.entity.Ticket;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link com.avia.model.entity.Passenger} entity
 */
@Data
public class PassengerDto implements Serializable {
    private Long idPass;
    @Size(max = 50)
    @NotNull
    private String fullName;
    @Size(max = 50)
    @NotNull
    private String personalId;
    private Double miles;
    private Set<Ticket> tickets;
    private Set<DocumentPassDto> documentPasses;
}