package com.avia.model.entity.requests;

import com.avia.model.entity.DocumentType;
import com.avia.model.entity.Passenger;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class DocumentPassDto implements Serializable {

    @NotNull
    private Integer idDocumentType;

    @Size(min = 8, max = 30)
    @NotNull
    private String documentNum;

    private Long idPass;
}