package com.avia.dto.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class TicketClassDto implements Serializable {

    @Size(max = 30)
    @NotNull
    private String nameClass;

}