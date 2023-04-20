package com.avia.repository;

import com.avia.model.entity.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface TicketStatusRepository extends JpaRepository<TicketStatus, Integer> {
}