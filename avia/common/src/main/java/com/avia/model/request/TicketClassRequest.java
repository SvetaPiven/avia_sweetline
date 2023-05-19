package com.avia.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@Data
@Validated
public class TicketClassRequest implements Serializable {

    @Size(min = 3, max = 30)
    @NotNull
    private String nameClass;

}