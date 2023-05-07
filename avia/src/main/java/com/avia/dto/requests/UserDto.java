package com.avia.dto.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
@Data
public class UserDto implements Serializable {

    @Size(max = 30)
    @NotNull
    private String email;

    @Size(max = 30)
    @NotNull
    private String userPassword;

    private Long idPass;
}