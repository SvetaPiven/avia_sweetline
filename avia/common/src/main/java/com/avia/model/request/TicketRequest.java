package com.avia.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class TicketRequest implements Serializable {

    @NotNull(message = "ID Passenger must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "95",
            type = "number", format = "long", description = "ID Passenger")
    private Long idPass;

    @NotNull(message = "ID Ticket Status must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1",
            type = "number", format = "integer", description = "ID Ticket Status")
    private Integer idTicketStatus;

    @NotNull(message = "ID Ticket Class must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2",
            type = "number", format = "integer", description = "ID Ticket Class")
    private Integer idTicketClass;

    @NotNull(message = "ID Flight must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "10",
            type = "number", format = "long", description = "ID Flight")
    private Long idFlight;

    @NotNull(message = "ID Airline must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "6",
            type = "number", format = "integer", description = "ID Airline")
    private Integer idAirline;

    @Size(min = 3, max = 15, message = "Number place must be between 3 and 15 characters")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "A13",
            type = "string", description = "Number place")
    private String numberPlace;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "247.74",
            type = "number", format = "double", description = "Price")
    private BigDecimal price;
}