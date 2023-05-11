package com.avia.repository;

import com.avia.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByIdPass(Long idPass);

    //Optional<User> findUserByEmail(String email);
}