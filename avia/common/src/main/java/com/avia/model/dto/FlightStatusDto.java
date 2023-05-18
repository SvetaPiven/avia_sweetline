package com.avia.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
@Data
public class FlightStatusDto implements Serializable {
    @Size(min = 3, max = 30)
    @NotNull
    private String nameFlightStatus;
}