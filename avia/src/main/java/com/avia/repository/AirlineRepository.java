package com.avia.repository;

import com.avia.model.entity.Airline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AirlineRepository extends JpaRepository<Airline, Integer> {
    @Query("SELECT a.idAirline, a.nameAirline, a.codeAirline, COUNT(t.idTicket) AS soldTickets " +
            "FROM Ticket t JOIN t.idAirline a " +
            "GROUP BY a.idAirline, a.nameAirline, a.codeAirline " +
            "ORDER BY soldTickets DESC")
    List<Object []> findPopularAirlines();
}