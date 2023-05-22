package com.avia.service.impl;

import com.avia.exception.EntityNotFoundException;
import com.avia.mapper.DocumentPassMapper;
import com.avia.model.entity.DocumentPass;
import com.avia.model.entity.DocumentType;
import com.avia.model.entity.Passenger;
import com.avia.model.request.DocumentPassRequest;
import com.avia.repository.DocumentPassRepository;
import com.avia.repository.DocumentTypeRepository;
import com.avia.repository.PassengerRepository;
import com.avia.service.DocumentPassService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentPassServiceImpl implements DocumentPassService {

    private final DocumentPassRepository documentPassRepository;

    private final DocumentPassMapper documentPassMapper;

    private final DocumentTypeRepository documentTypeRepository;

    private final PassengerRepository passengerRepository;

    @Override
    @Transactional
    public DocumentPass createDocumentPass(DocumentPassRequest documentPassRequest) {

        DocumentType documentType = documentTypeRepository.findById(documentPassRequest.getIdDocumentType()).orElseThrow(() ->
                new EntityNotFoundException("Document pass class with id " + documentPassRequest.getIdDocumentType() + " not found"));

        DocumentPass documentPass = documentPassMapper.toEntity(documentPassRequest);
        documentPass.getDocumentType().setIdDocumentType(documentPass.getDocumentType().getIdDocumentType());
        documentPass.setDocumentType(documentType);

        Passenger passenger = passengerRepository.findById(documentPassRequest.getIdPass()).orElseThrow(() ->
                new EntityNotFoundException("Passenger with id " + documentPassRequest.getIdPass() + " not found"));
        documentPass.getPassenger().setIdPass(passenger.getIdPass());
        documentPass.setPassenger(passenger);

        return documentPassRepository.save(documentPass);
    }

    @Override
    @Transactional
    public DocumentPass updateDocumentPass(Long id, DocumentPassRequest documentPassRequest) {

        DocumentPass documentPass = documentPassRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Document pass with id " + id + " not found"));

        documentPassMapper.partialUpdate(documentPassRequest, documentPass);

        return documentPassRepository.save(documentPass);
    }
}
