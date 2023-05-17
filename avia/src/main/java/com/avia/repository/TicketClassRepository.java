package com.avia.repository;

import com.avia.model.entity.Airline;
import com.avia.model.entity.TicketClass;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Cacheable("c_ticket_class")
public interface TicketClassRepository extends JpaRepository<TicketClass, Integer> {

    @NotNull
    List<TicketClass> findAll();
}