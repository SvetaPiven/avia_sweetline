package com.avia.repository;

import com.avia.model.entity.DocumentPass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocumentPassRepository extends JpaRepository<DocumentPass, Long> {

    Optional<DocumentPass> findDocumentPassByDocumentNum(String number);
}