package com.avia.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@Data
@Validated
public class PassengerRequest implements Serializable {

    private Long idPass;

    @Size(min = 4, max = 50)
    @NotNull
    private String fullName;

    @Size(min = 14, max = 50)
    @NotNull
    private String personalId;

    private Double miles;
}