package com.avia.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@Data
@Validated
public class PlaneTypeRequest implements Serializable {

    @Size(min = 3, max = 20, message = "Plane type must be between 3 and 20 characters")
    @NotNull(message = "Plane type must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Boeing",
            type = "string", description = "Plane type")
    private String planeType;
}