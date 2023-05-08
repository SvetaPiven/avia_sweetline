package com.avia.service.impl;

import com.avia.dto.requests.DocumentPassDto;
import com.avia.exception.EntityNotFoundException;
import com.avia.mapper.DocumentPassMapper;
import com.avia.model.entity.DocumentPass;
import com.avia.model.entity.DocumentType;
import com.avia.model.entity.Passenger;
import com.avia.model.entity.Ticket;
import com.avia.model.entity.TicketClass;
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
    public DocumentPass createDocumentPass(DocumentPassDto documentPassDto) {

        DocumentType documentType = documentTypeRepository.findById(documentPassDto.getIdDocumentType()).orElseThrow(() ->
                new EntityNotFoundException("Document pass class with id " + documentPassDto.getIdDocumentType() + " not found"));

        DocumentPass documentPass = documentPassMapper.toEntity(documentPassDto);
        documentPass.getIdDocumentType().setIdDocumentType(documentPass.getIdDocumentType().getIdDocumentType());
        documentPass.setIdDocumentType(documentType);

        Passenger passenger = passengerRepository.findById(documentPassDto.getIdPass()).orElseThrow(() ->
                new EntityNotFoundException("Passenger with id " + documentPassDto.getIdPass() + " not found"));
        documentPass.getIdPass().setIdPass(passenger.getIdPass());
        documentPass.setIdPass(passenger);

        return documentPassRepository.save(documentPass);
    }

    @Override
    @Transactional
    public DocumentPass updateDocumentPass(Long id, DocumentPassDto documentPassDto) {

        DocumentPass documentPass = documentPassRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Document pass with id " + id + " not found"));

        documentPassMapper.partialUpdate(documentPassDto, documentPass);

        return documentPassRepository.save(documentPass);
    }
}
