package com.avia.dto.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class AirportDto implements Serializable {

    @Size(max = 50)
    @NotNull
    private String nameAirport;

    @Size(max = 50)
    @NotNull
    private String city;

    @NotNull
    private Float longitude;

    @NotNull
    private Float latitude;

    @NotNull
    private String timezone;

    @Size(max = 30)
    @NotNull
    private String country;

}