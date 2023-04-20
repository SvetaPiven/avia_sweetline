package com.avia.repository;

import com.avia.model.entity.TicketClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface TicketClassRepository extends JpaRepository<TicketClass, Integer> {
}