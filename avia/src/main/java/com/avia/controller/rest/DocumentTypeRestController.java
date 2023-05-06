package com.avia.controller.rest;

import com.avia.dto.requests.DocumentTypeDto;
import com.avia.exception.EntityNotFoundException;
import com.avia.model.entity.DocumentType;
import com.avia.repository.DocumentTypeRepository;
import com.avia.service.DocumentTypeService;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/document-types")
public class DocumentTypeRestController {

    private final DocumentTypeRepository documentTypeRepository;

    private final DocumentTypeService documentTypeService;

    private static final Logger log = Logger.getLogger(DocumentTypeRestController.class);

    @GetMapping()
    public ResponseEntity<List<DocumentType>> getAllDocumentTypes() {

        return new ResponseEntity<>(documentTypeRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DocumentType> createDocumentType(@RequestBody DocumentTypeDto documentTypeDto) {

        DocumentType createdDocumentType = documentTypeService.createDocumentType(documentTypeDto);

        return new ResponseEntity<>(createdDocumentType, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<DocumentType> getDocumentTypeById(@PathVariable("id") Integer id) {

        Optional<DocumentType> documentType = documentTypeRepository.findById(id);

        return documentType.map(ResponseEntity::ok).orElseThrow(() ->
                new EntityNotFoundException("Document type with id " + id + " not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentType> updateDocumentType(@PathVariable Integer id, @RequestBody DocumentTypeDto documentTypeDto) {

        DocumentType updatedDocumentType = documentTypeService.updateDocumentType(id, documentTypeDto);

        return new ResponseEntity<>(updatedDocumentType, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumentType(@PathVariable("id") Integer id) {

        Optional<DocumentType> documentTypeOptional = documentTypeRepository.findById(id);

        if (documentTypeOptional.isPresent()) {
            documentTypeRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("Document type with id " + id + " not found!");
        }
    }
}
