package com.avia.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserDto implements Serializable {

    @Size(min = 10, max = 30)
    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    @NotNull
    private String email;

    @Size(min = 8, max = 200)
    @Pattern(regexp = "^.*(?=.{6,})(?=.*d)(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*? ]).*$")
    @NotNull
    private String userPassword;

    private Long idPass;

    private Integer idRole;
}