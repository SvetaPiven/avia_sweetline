package com.avia.repository;

import com.avia.model.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Cacheable("users")
public interface UserRepository extends JpaRepository<User, Long> {
    User findByIdPass(Long idPass);

    @Query(value = "SELECT * FROM users WHERE email LIKE %:email%", nativeQuery = true)
    List<User> findUsersByEmail(@Param("email") String email);

    Optional<User> findByAuthenticationInfoEmail(String username);

}