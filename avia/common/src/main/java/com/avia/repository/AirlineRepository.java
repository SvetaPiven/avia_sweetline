package com.avia.repository;

import com.avia.model.entity.Airline;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Cacheable("c_airlines")
public interface AirlineRepository extends JpaRepository<Airline, Integer> {

    @NotNull
    List<Airline> findAll();

    @Query("SELECT a FROM Airline a JOIN a.tickets t " +
            "GROUP BY a.idAirline, a.nameAirline, a.codeAirline " +
            "ORDER BY COUNT(t) DESC")
    List<Airline> findPopularAirlines();
}