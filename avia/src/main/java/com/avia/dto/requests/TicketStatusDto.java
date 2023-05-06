package com.avia.dto.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;


@Data
public class TicketStatusDto implements Serializable {

    @Size(max = 20)
    @NotNull
    private String nameTicketStatus;
}