package com.avia.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@Data
@Validated
public class TicketClassRequest implements Serializable {

    @Size(min = 3, max = 30, message = "Name class must be between 3 and 30 characters")
    @NotNull(message = "Name class must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Business class",
            type = "string", description = "Name Class")
    private String nameClass;

}