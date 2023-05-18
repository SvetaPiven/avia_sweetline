package com.avia.service;

import com.avia.model.entity.DocumentType;
import com.avia.model.dto.DocumentTypeDto;

public interface DocumentTypeService {

    DocumentType createDocumentType(DocumentTypeDto documentTypeDto);

    DocumentType updateDocumentType(Integer id, DocumentTypeDto documentTypeDto);
}
