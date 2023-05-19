package com.avia.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class DocumentPassRequest implements Serializable {

    @NotNull
    private Integer idDocumentType;

    @Size(min = 8, max = 30)
    @NotNull
    private String documentNum;

    private Long idPass;
}