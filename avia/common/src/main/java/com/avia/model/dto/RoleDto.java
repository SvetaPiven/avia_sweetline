package com.avia.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
@Data
public class RoleDto implements Serializable {

    @Size(min = 2, max = 100)
    @NotNull
    private String roleName;
}