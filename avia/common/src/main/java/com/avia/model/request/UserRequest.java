package com.avia.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@Data
@Validated
@Schema(description = "User Request")
public class UserRequest implements Serializable {

    @Size(min = 10, max = 30)
    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    @Schema(description = "User email", example = "svetapiven93@gmail.com")
    @NotNull
    private String email;

    @Size(min = 8, max = 200)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).{6,}$")
    @Schema(description = "User password", example = "123456qW")
    @NotNull
    private String userPassword;

    @Schema(description = "ID passenger")
    private Long idPass;

    @Schema(description = "ID role")
    private Integer idRole;
}