package com.avia.repository;

import com.avia.model.entity.DocumentType;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

@Cacheable("c_document_type")
public interface DocumentTypeRepository extends JpaRepository<DocumentType, Integer> {
}