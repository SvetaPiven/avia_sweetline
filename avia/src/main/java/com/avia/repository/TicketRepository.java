package com.avia.repository;

import com.avia.model.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}