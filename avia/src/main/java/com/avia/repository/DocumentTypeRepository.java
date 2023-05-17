package com.avia.repository;

import com.avia.model.entity.Airline;
import com.avia.model.entity.DocumentType;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Cacheable("c_document_type")
public interface DocumentTypeRepository extends JpaRepository<DocumentType, Integer> {

    @NotNull
    List<DocumentType> findAll();
}