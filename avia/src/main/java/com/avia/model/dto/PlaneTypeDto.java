package com.avia.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class PlaneTypeDto implements Serializable {

    @Size(min = 3, max = 20)
    @NotNull
    private String planeType;
}