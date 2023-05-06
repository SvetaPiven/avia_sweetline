package com.avia.dto.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class PassengerDto implements Serializable {

    private Long idPass;

    @Size(max = 50)
    @NotNull
    private String fullName;

    @Size(max = 50)
    @NotNull
    private String personalId;

    private Double miles;
}