package com.avia.controller.rest;

import com.avia.model.entity.DocumentPass;
import com.avia.model.request.DocumentPassRequest;
import com.avia.repository.DocumentPassRepository;
import com.avia.service.DocumentPassService;
import com.avia.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/documents")
@RequiredArgsConstructor
public class DocumentPassRestController {

    private final DocumentPassRepository documentPassRepository;

    private final DocumentPassService documentPassService;

    @Value("${documentPass.page-capacity}")
    private Integer documentPassPageCapacity;

    @GetMapping()
    public ResponseEntity<List<DocumentPass>> getAllDocumentPass() {

        List<DocumentPass> documentPass = documentPassRepository.findAll();

        return new ResponseEntity<>(documentPass, HttpStatus.OK);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Object> getAllDocumentPassWithPageAndSort(@PathVariable int page) {

        Pageable pageable = PageRequest.of(page, documentPassPageCapacity, Sort.by("idDocumentPass").ascending());

        Page<DocumentPass> documentPasses = documentPassRepository.findAll(pageable);

        if (documentPasses.hasContent()) {
            return new ResponseEntity<>(documentPasses, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<DocumentPass> createTicketStatus(@RequestBody DocumentPassRequest documentPassRequest) {

        DocumentPass documentPass = documentPassService.createDocumentPass(documentPassRequest);

        return new ResponseEntity<>(documentPass, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentPass> getTicketStatusById(@PathVariable("id") Long id) {

        Optional<DocumentPass> documentPassOptional = documentPassRepository.findById(id);

        return documentPassOptional.map(ResponseEntity::ok).orElseThrow(() ->
                new EntityNotFoundException("Ticket status with id " + id + " not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentPass> updateTicketStatus(@PathVariable Long id,
                                                           @RequestBody DocumentPassRequest documentPassRequest) {
        DocumentPass updatedDocumentPass = documentPassService.updateDocumentPass(id, documentPassRequest);
        return new ResponseEntity<>(updatedDocumentPass, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumentPass(@PathVariable("id") Long id) {

        Optional<DocumentPass> documentPassOptional = documentPassRepository.findById(id);

        if (documentPassOptional.isPresent()) {
            documentPassRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("Ticket status with id " + id + " not found");
        }
    }


    @PutMapping("/{id}/status")
    public String changeStatus(@PathVariable("id") Long id, @RequestParam("isDeleted") boolean isDeleted) {
        Optional<DocumentPass> documentPassOptional = documentPassRepository.findById(id);

        if (documentPassOptional.isPresent()) {
            DocumentPass documentPass = documentPassOptional.get();
            documentPass.setDeleted(isDeleted);
            documentPassRepository.save(documentPass);
            return "Status changed successfully";
        } else {
            throw new EntityNotFoundException("DocumentPass with id " + id + " not found!");
        }
    }

    @GetMapping("/search-by-number")
    public ResponseEntity<DocumentPass> getDocumentByNumber(@RequestParam(value = "number") String number) {

        Optional<DocumentPass> document = documentPassRepository.findDocumentPassByDocumentNum(number);

        return document.map(ResponseEntity::ok).orElseThrow(() ->
                new EntityNotFoundException("Ticket status with number " + number + " not found"));
    }
}
