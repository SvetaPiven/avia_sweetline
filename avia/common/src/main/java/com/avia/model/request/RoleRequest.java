package com.avia.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@Data
@Validated
public class RoleRequest implements Serializable {

    @Size(min = 2, max = 100, message = "Role name must be between 2 and 100 characters")
    @NotNull(message = "Role name must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "ROLE_ADMIN",
            type = "string", description = "Role Name")
    private String roleName;
}