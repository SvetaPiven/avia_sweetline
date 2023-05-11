package com.avia.service;

import com.avia.model.dto.DocumentTypeDto;
import com.avia.model.entity.DocumentType;

public interface DocumentTypeService {

    DocumentType createDocumentType(DocumentTypeDto documentTypeDto);

    DocumentType updateDocumentType(Integer id, DocumentTypeDto documentTypeDto);
}
