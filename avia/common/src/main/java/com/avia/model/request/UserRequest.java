package com.avia.model.request;

import com.avia.model.valid.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@Data
@Validated
@Schema(description = "User Request")
public class UserRequest implements Serializable {

    @Value("${validation.passwordPattern}")
    private String passwordPattern;

    @NotNull
    @Size(min = 10, max = 30)
    @Email(message = "Invalid email address")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "svetapiven93@gmail.com",
            type = "string", description = "User email")
    private String email;

    @NotNull
    @Size(min = 8, max = 200)
    @ValidPassword
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "123456qW",
            type = "string", description = "User password")
    private String userPassword;

    @Schema(description = "ID passenger")
    private Long idPass;

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1",
            type = "string", description = "ID Role")
    private Integer idRole;
}