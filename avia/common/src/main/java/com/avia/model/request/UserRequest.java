package com.avia.model.request;

import com.avia.model.valid.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@Data
@Validated
@Schema(description = "User Request")
public class UserRequest implements Serializable {

    @NotNull(message = "User email must not be null")
    @Size(message = "User email must be between 10 and 30 characters", min = 10, max = 30)
    @Email(message = "Invalid email address")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "svetapiven93@gmail.com",
            type = "string", description = "User email")
    private String email;

    @NotNull(message = "User password must not be null")
    @Size(message = "User password must be between 8 and 200 characters", min = 8, max = 200)
    @ValidPassword
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "123456qW",
            type = "string", description = "User password")
    private String userPassword;

    @Schema(description = "ID passenger")
    private Long idPass;

    @NotNull(message = "ID role must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1",
            type = "number", format = "integer", description = "ID Role")
    private Integer idRole;
}