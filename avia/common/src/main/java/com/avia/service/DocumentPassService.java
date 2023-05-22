package com.avia.service;

import com.avia.model.entity.DocumentPass;
import com.avia.model.request.DocumentPassRequest;

public interface DocumentPassService {

    DocumentPass createDocumentPass(DocumentPassRequest documentPassRequest);

    DocumentPass updateDocumentPass(Long id, DocumentPassRequest documentPassRequest);
}
