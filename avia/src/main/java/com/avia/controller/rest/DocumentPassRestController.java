package com.avia.controller.rest;

import com.avia.model.entity.DocumentPass;
import com.avia.repository.DocumentPassRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping()
    public ResponseEntity<List<DocumentPass>> getAllDocumentPass() {
        List<DocumentPass> documentPass = documentPassRepository.findAll();
        return new ResponseEntity<>(documentPass, HttpStatus.OK);
    }

    @GetMapping("/search-by-number")
    public ResponseEntity<DocumentPass> getDocumentByNumber(@RequestParam(value = "number") String number) {
        Optional<DocumentPass> document = documentPassRepository.findDocumentPassByDocumentNum(number);
        return document.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
