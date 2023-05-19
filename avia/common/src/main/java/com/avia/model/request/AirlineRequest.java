package com.avia.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@Data
@Validated
public class AirlineRequest implements Serializable {

    @Size(min = 2, max = 50)
    @NotNull
    private String nameAirline;

    @Size(min = 2, max = 3)
    @NotNull
    private String codeAirline;

}