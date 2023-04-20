package com.avia.repository;

import com.avia.model.entity.DocumentPass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface DocumentPassRepository extends JpaRepository<DocumentPass, Long> {
}