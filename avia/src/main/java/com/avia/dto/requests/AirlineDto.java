package com.avia.dto.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class AirlineDto implements Serializable {

    @Size(max = 50)
    @NotNull
    private String nameAirline;

    @Size(max = 3)
    @NotNull
    private String codeAirline;

}