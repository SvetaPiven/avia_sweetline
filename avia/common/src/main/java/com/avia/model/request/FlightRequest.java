package com.avia.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@Data
@Validated
public class FlightRequest implements Serializable {

    @Size(min = 6, max = 10, message = "Flight number must be between 6 and 10 characters")
    @NotNull(message = "Flight number must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "PG0556",
            type = "string", description = "Flight number")
    private String flightNumber;

    @NotNull(message = "Plane type ID must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1",
            type = "integer", description = "Plane type ID")
    private Integer idPlaneType;

    @NotNull(message = "Departure airport ID must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1",
            type = "number", format = "long", description = "Departure airport ID")
    private Long idDepartureAirport;

    @NotNull(message = "Arrival airport ID must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "2",
            type = "number", format = "long", description = "Arrival airport ID")
    private Long idArrivalAirport;

    @NotNull(message = "Flight status ID must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1",
            type = "number", format = "integer", description = "Flight status ID")
    private Integer idFlightStatus;

}