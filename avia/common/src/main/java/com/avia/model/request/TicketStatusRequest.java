package com.avia.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;


@Data
@Validated
public class TicketStatusRequest implements Serializable {

    @Size(message = "Name Ticket Status must be between 3 and 20 characters", min = 3, max = 20)
    @NotNull(message = "Name Ticket Status must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Used",
            type = "string", description = "Name Ticket Status")
    private String nameTicketStatus;
}