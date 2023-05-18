package com.avia.repository;

import com.avia.model.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Cacheable("users")
public interface UserRepository extends JpaRepository<User, Long> {
    User findByIdPass(Long idPass);

    @Query(value = "select * from users where email = :email", nativeQuery = true)
    Optional<User> findUserByEmail(String email);

    Optional<User> findByAuthenticationInfoEmail(String username);
}