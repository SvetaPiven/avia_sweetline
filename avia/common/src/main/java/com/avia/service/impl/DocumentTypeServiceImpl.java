package com.avia.service.impl;

import com.avia.exception.EntityNotFoundException;
import com.avia.mapper.DocumentTypeMapper;
import com.avia.model.entity.DocumentType;
import com.avia.model.request.DocumentTypeRequest;
import com.avia.repository.DocumentTypeRepository;
import com.avia.service.DocumentTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentTypeServiceImpl implements DocumentTypeService {

    private final DocumentTypeMapper documentTypeMapper;
    private final DocumentTypeRepository documentTypeRepository;

    @Override
    @Transactional
    public DocumentType createDocumentType(DocumentTypeRequest documentTypeRequest) {

        DocumentType documentType = documentTypeMapper.toEntity(documentTypeRequest);

        return documentTypeRepository.save(documentType);
    }

    @Override
    @Transactional
    public DocumentType updateDocumentType(Integer id, DocumentTypeRequest documentTypeRequest) {

        DocumentType documentType = documentTypeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Document type with id " + id + " not found"));

        documentTypeMapper.partialUpdate(documentTypeRequest, documentType);

        return documentTypeRepository.save(documentType);
    }
}
