package com.avia.dto;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

/**
 * A DTO for the {@link com.avia.model.entity.Passenger} entity
 */
@Data
public class PassengerDto implements Serializable {
    private Long idPass;
    private String fullName;
    private String personalId;
    private Double miles;
    private Timestamp created;
    private Timestamp changed;
    private Boolean isDeleted;
    private Set<TicketDto> tickets;
    private Set<DocumentPassDto> documentPasses;
}