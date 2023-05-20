package com.avia.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@Data
@Validated
public class UserRequest implements Serializable {

    @Size(min = 10, max = 30)
    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    @NotNull
    private String email;

    @Size(min = 8, max = 200)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).{6,}$")
    @NotNull
    private String userPassword;

    private Long idPass;

    private Integer idRole;
}