package com.avia.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@Data
@Validated
public class AirlineRequest implements Serializable {

    @Size(min = 2, max = 50, message = "Airline name must be between 2 and 50 characters")
    @NotNull(message = "Airline name must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "WizzAir",
            type = "string", description = "Airline name")
    private String nameAirline;

    @Size(min = 2, max = 3, message = "Airline code must be between 2 and 3 characters")
    @NotNull(message = "Airline code must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "AFL",
            type = "string", description = "Airline code")
    private String codeAirline;

}