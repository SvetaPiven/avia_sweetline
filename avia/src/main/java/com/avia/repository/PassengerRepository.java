package com.avia.repository;

import com.avia.model.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
//    @Query("SELECT e from Passenger e left join fetch e.tickets")
//    List<Passenger> findBlaBlah();
}