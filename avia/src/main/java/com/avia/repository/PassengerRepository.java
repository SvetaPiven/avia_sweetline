package com.avia.repository;

import com.avia.model.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    Optional<Passenger> findPassengerByFullName(String fullName);

    Optional<Passenger> findPassengerByPersonalId(String pid);

}