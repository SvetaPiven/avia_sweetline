package com.avia.controller.rest;

import com.avia.exception.ValidationException;
import com.avia.model.entity.DocumentType;
import com.avia.repository.DocumentTypeRepository;
import com.avia.service.DocumentTypeService;
import com.avia.model.request.DocumentTypeRequest;
import com.avia.exception.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/document-types")
public class DocumentTypeRestController {

    private final DocumentTypeRepository documentTypeRepository;

    private final DocumentTypeService documentTypeService;

    @Value("${documentType.page-capacity}")
    private Integer documentTypePageCapacity;

    @GetMapping()
    public ResponseEntity<List<DocumentType>> getAllDocumentTypes() {

        return new ResponseEntity<>(documentTypeRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Object> getAllDocumentTypesWithPageAndSort(@PathVariable int page) {

        Pageable pageable = PageRequest.of(page, documentTypePageCapacity, Sort.by("idDocumentType").ascending());

        Page<DocumentType> documentTypes = documentTypeRepository.findAll(pageable);

        if (documentTypes.hasContent()) {
            return new ResponseEntity<>(documentTypes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<DocumentType> createDocumentType(@Valid @RequestBody DocumentTypeRequest documentTypeRequest,
                                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        DocumentType createdDocumentType = documentTypeService.createDocumentType(documentTypeRequest);

        return new ResponseEntity<>(createdDocumentType, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<DocumentType> getDocumentTypeById(@PathVariable("id") Integer id) {

        Optional<DocumentType> documentType = documentTypeRepository.findById(id);

        return documentType.map(ResponseEntity::ok).orElseThrow(() ->
                new EntityNotFoundException("Document type with id " + id + " not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentType> updateDocumentType(@PathVariable Integer id,
                                                           @Valid @RequestBody DocumentTypeRequest documentTypeRequest,
                                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
            throw new ValidationException(errorMessage);
        }

        DocumentType updatedDocumentType = documentTypeService.updateDocumentType(id, documentTypeRequest);

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

    @PutMapping("/{id}/status")
    public String changeStatus(@PathVariable("id") Integer id, @RequestParam("isDeleted") boolean isDeleted) {
        Optional<DocumentType> documentTypeOptional = documentTypeRepository.findById(id);

        if (documentTypeOptional.isPresent()) {
            DocumentType documentType = documentTypeOptional.get();
            documentType.setDeleted(isDeleted);
            documentTypeRepository.save(documentType);
            return "Status changed successfully";
        } else {
            throw new EntityNotFoundException("DocumentType with id " + id + " not found!");
        }
    }
}
