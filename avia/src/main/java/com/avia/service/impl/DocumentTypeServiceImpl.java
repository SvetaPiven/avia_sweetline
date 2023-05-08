package com.avia.service.impl;

import com.avia.dto.requests.DocumentTypeDto;
import com.avia.exception.EntityNotFoundException;
import com.avia.mapper.DocumentTypeMapper;
import com.avia.model.entity.DocumentType;
import com.avia.repository.DocumentTypeRepository;
import com.avia.service.DocumentTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentTypeServiceImpl implements DocumentTypeService {

    private final DocumentTypeMapper documentTypeMapper;
    private final DocumentTypeRepository documentTypeRepository;

    @Override
    @Transactional
    public DocumentType createDocumentType(DocumentTypeDto documentTypeDto) {

        DocumentType documentType = documentTypeMapper.toEntity(documentTypeDto);

        return documentTypeRepository.save(documentType);
    }

    @Override
    @Transactional
    public DocumentType updateDocumentType(Integer id, DocumentTypeDto documentTypeDto) {

        DocumentType documentType = documentTypeRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Document type with id " + id + " not found"));

        documentTypeMapper.partialUpdate(documentTypeDto, documentType);

        return documentTypeRepository.save(documentType);
    }
}
