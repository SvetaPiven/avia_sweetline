package com.avia.service;

import com.avia.dto.requests.AirlineDto;
import com.avia.dto.requests.DocumentTypeDto;
import com.avia.model.entity.Airline;
import com.avia.model.entity.DocumentType;

public interface DocumentTypeService {

    DocumentType createDocumentType(DocumentTypeDto documentTypeDto);

    DocumentType updateDocumentType(Integer id, DocumentTypeDto documentTypeDto);
}
