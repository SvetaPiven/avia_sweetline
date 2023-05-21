package com.avia.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@Data
@Validated
public class DocumentPassRequest implements Serializable {

    @NotNull(message = "Document type ID must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "1",
            type = "integer", description = "ID Document Type")
    private Integer idDocumentType;

    @Size(min = 8, max = 30, message = "Document number must be between 8 and 30 characters")
    @NotNull(message = "Document number must not be null")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "MP2643444",
            type = "string", description = "Document number")
    private String documentNum;

    @Schema(description = "ID passenger")
    private Long idPass;
}