package com.avia.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class PassengerDto implements Serializable {

    private Long idPass;

    @Size(min = 4, max = 50)
    @NotNull
    private String fullName;

    @Size(min = 14, max = 50)
    @NotNull
    private String personalId;

    private Double miles;
}