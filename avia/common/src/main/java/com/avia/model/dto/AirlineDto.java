package com.avia.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class AirlineDto implements Serializable {

    @Size(min = 2, max = 50)
    @NotNull
    private String nameAirline;

    @Size(min = 2, max = 3)
    @NotNull
    private String codeAirline;

}