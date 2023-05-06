package com.avia.repository;

import com.avia.model.entity.TicketClass;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Cacheable("c_ticket_class")
public interface TicketClassRepository extends JpaRepository<TicketClass, Integer> {
}