package com.avia.repository;

import com.avia.model.entity.Passenger;
import com.avia.model.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findTicketByPassenger(Passenger idPass);

    @Modifying
    @Query(value = "CALL sale(:idTicket, :discount)", nativeQuery = true)
    void applyDiscount(@Param("idTicket") Long idTicket, @Param("discount") Float discount);

    @Query(value = "SELECT find_most_expensive_ticket(:id)", nativeQuery = true)
    BigDecimal findMostExpensiveTicketPrice(Long id);
}