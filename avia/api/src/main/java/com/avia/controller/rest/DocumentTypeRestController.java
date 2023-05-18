package com.avia.controller.rest;

import com.avia.model.entity.DocumentType;
import com.avia.repository.DocumentTypeRepository;
import com.avia.service.DocumentTypeService;
import com.avia.model.dto.DocumentTypeDto;
import com.avia.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/document-types")
public class DocumentTypeRestController {

    private final DocumentTypeRepository documentTypeRepository;

    private final DocumentTypeService documentTypeService;

    @GetMapping()
    public ResponseEntity<List<DocumentType>> getAllDocumentTypes() {

        return new ResponseEntity<>(documentTypeRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Object> getAllDocumentTypesWithPageAndSort(@PathVariable int page) {

        Pageable pageable = PageRequest.of(page, 3, Sort.by("idDocumentType").ascending());

        Page<DocumentType> documentTypes = documentTypeRepository.findAll(pageable);

        if (documentTypes.hasContent()) {
            return new ResponseEntity<>(documentTypes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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

    @PutMapping("/{id}/delete")
    public ResponseEntity<Void> softDeleteDocumentType(@PathVariable("id") Integer id) {
        Optional<DocumentType> documentTypeOptional = documentTypeRepository.findById(id);

        if (documentTypeOptional.isPresent()) {
            DocumentType documentType = documentTypeOptional.get();
            documentType.setIsDeleted(true);
            documentTypeRepository.save(documentType);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("DocumentType with id " + id + " not found!");
        }
    }
}
