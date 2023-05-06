package com.avia.service;

import com.avia.dto.requests.DocumentPassDto;
import com.avia.model.entity.DocumentPass;

public interface DocumentPassService {

    DocumentPass createDocumentPass(DocumentPassDto documentPassDto);

    DocumentPass updateDocumentPass(Long id, DocumentPassDto documentPassDto);
}
