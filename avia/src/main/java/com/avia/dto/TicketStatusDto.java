package com.avia.dto;

import com.avia.model.entity.Ticket;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link com.avia.model.entity.TicketStatus} entity
 */
@Data
public class TicketStatusDto implements Serializable {
    private Integer idTicketStatus;
    @Size(max = 20)
    @NotNull
    private String nameTicketStatus;
    private Set<Ticket> tickets;
}