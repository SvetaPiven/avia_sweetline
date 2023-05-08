package com.avia.dto.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
@Data
public class RoleDto implements Serializable {

    @Size(max = 100)
    @NotNull
    private String roleName;
}