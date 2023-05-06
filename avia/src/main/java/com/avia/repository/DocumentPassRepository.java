package com.avia.repository;

import com.avia.model.entity.DocumentPass;
import com.avia.model.entity.Passenger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface DocumentPassRepository extends JpaRepository<DocumentPass, Long> {

    Optional<DocumentPass> findDocumentPassByDocumentNum(String number);
}