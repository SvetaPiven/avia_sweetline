package com.avia.service;

import com.avia.model.entity.DocumentType;
import com.avia.model.request.DocumentTypeRequest;

public interface DocumentTypeService {

    DocumentType createDocumentType(DocumentTypeRequest documentTypeRequest);

    DocumentType updateDocumentType(Integer id, DocumentTypeRequest documentTypeRequest);
}
