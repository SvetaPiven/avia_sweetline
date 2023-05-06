package com.avia.service.impl;

import com.avia.dto.requests.DocumentPassDto;
import com.avia.exception.EntityNotFoundException;
import com.avia.mapper.DocumentPassMapper;
import com.avia.model.entity.DocumentPass;
import com.avia.repository.DocumentPassRepository;
import com.avia.service.DocumentPassService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentPassServiceImpl implements DocumentPassService {

    private final DocumentPassRepository documentPassRepository;
    private final DocumentPassMapper documentPassMapper;

    @Override
    public DocumentPass createDocumentPass(DocumentPassDto documentPassDto) {

        DocumentPass documentPass = documentPassMapper.toEntity(documentPassDto);

        return documentPassRepository.save(documentPass);
    }

    @Override
    public DocumentPass updateDocumentPass(Long id, DocumentPassDto documentPassDto) {

        DocumentPass documentPass = documentPassRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Document pass with id " + id + " not found"));

        documentPassMapper.partialUpdate(documentPassDto, documentPass);

        return documentPassRepository.save(documentPass);
    }
}
