package com.avia.dto;

import com.avia.model.entity.DocumentType;
import com.avia.model.entity.Passenger;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * A DTO for the {@link com.avia.model.entity.DocumentPass} entity
 */
@Data
public class DocumentPassDto implements Serializable {

    private final Long idDocumentPass;

    private final Long idDocumentType;

    private final String documentNum;

    private final Long idPass;

    private final Timestamp created;

    private final Timestamp changed;

    private final Boolean isDeleted;

    private final DocumentType documentType;

    private final Passenger passengers;
}