package com.avia.model.entity.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class AirportDto implements Serializable {

    @Size(min = 3, max = 50)
    @NotNull
    private String nameAirport;

    @Size(min = 2, max = 50)
    @NotNull
    private String city;

    @Pattern(regexp = "-?\\d{1,3}\\.\\d{1,8}+")
    @NotNull
    private Float longitude;

    @Pattern(regexp = "-?\\d{1,3}\\.\\d{1,8}+")
    @NotNull
    private Float latitude;

    @Pattern(regexp = "-?\\+?\\d{1,2}")
    @NotNull
    private String timezone;

    @Size(min =3, max = 30)
    @NotNull
    private String country;

}