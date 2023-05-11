package com.avia.repository;

import com.avia.model.entity.TicketStatus;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

@Cacheable("c_ticket_status")
public interface TicketStatusRepository extends JpaRepository<TicketStatus, Integer> {
}