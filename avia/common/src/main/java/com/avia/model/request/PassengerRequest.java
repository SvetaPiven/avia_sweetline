package com.avia.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@Data
@Validated
public class PassengerRequest implements Serializable {

    @NotNull(message = "ID Passenger must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1",
            type = "number", format = "long", description = "ID Passenger")
    private Long idPass;

    @Size(min = 4, max = 50, message = "Full Name must be between 4 and 50 characters")
    @NotNull(message = "Full Name must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Sveta Piven",
            type = "string", description = "Full Name")
    private String fullName;

    @Size(min = 14, max = 50, message = "Personal ID must be between 14 and 50 characters")
    @NotNull(message = "Personal ID must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "4130577B636PB5",
            type = "string", description = "Personal ID")
    private String personalId;

    @Schema(description = "Miles")
    private Double miles;
}