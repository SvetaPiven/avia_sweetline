package com.avia.model.request;

import com.avia.model.valid.ValidCoordinate;
import com.avia.model.valid.ValidTimezone;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@Data
@Validated
public class AirportRequest implements Serializable {

    @Size(min = 3, max = 50, message = "Airport name must be between 3 and 50 characters")
    @NotNull(message = "Airport name must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Minsk National Airport",
            type = "string", description = "Airport name")
    private String nameAirport;

    @Size(min = 2, max = 50, message = "City name must be between 2 and 50 characters")
    @NotNull(message = "City name must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Minsk",
            type = "string", description = "City name")
    private String city;

    @ValidCoordinate
    @NotNull(message = "Longitude must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "51.47",
            type = "number", format = "float", description = "Longitude")
    private Float longitude;

    @ValidCoordinate
    @NotNull(message = "Latitude must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "-0.454",
            type = "number", format = "float", description = "Latitude")
    private Float latitude;

    @ValidTimezone
    @NotNull(message = "Timezone must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "+01:00",
            type = "string", description = "Timezone")
    private String timezone;

    @Size(min = 3, max = 30, message = "Country name must be between 3 and 30 characters")
    @NotNull(message = "Country name must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "Belarus",
            type = "string", description = "Country name")
    private String country;

}