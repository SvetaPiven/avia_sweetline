package com.avia.repository;

import com.avia.model.entity.Passenger;
import com.avia.model.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findTicketByIdPass(Passenger idPass);
}