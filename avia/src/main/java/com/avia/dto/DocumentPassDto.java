package com.avia.dto;

import com.avia.model.entity.DocumentType;
import com.avia.model.entity.Passenger;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.avia.model.entity.DocumentPass} entity
 */
@Data
public class DocumentPassDto implements Serializable {
    private Long idDocumentPass;
    @NotNull
    private DocumentType idDocumentType;
    @Size(max = 30)
    @NotNull
    private String documentNum;
    private Passenger idPass;
}