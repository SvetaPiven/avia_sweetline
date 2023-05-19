package com.avia.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@Data
@Validated
public class PlaneTypeRequest implements Serializable {

    @Size(min = 3, max = 20)
    @NotNull
    private String planeType;
}