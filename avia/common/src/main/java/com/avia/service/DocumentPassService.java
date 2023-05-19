package com.avia.service;

import com.avia.model.request.DocumentPassRequest;
import com.avia.model.entity.DocumentPass;

public interface DocumentPassService {

    DocumentPass createDocumentPass(DocumentPassRequest documentPassRequest);

    DocumentPass updateDocumentPass(Long id, DocumentPassRequest documentPassRequest);
}
