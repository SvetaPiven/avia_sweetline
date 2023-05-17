package com.avia.repository;

import com.avia.model.entity.Role;
import com.avia.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByIdPass(Long idPass);

    @Query(value = "select * from users where email = :email", nativeQuery = true)
    Optional<User> findUserByEmail(String email);

        @Query(value = " select * " +
            " from c_roles r " +
            " join users u on u.id_role = r.id_role " +
            " where u.id_user = :idUser " +
            " order by u.id_user desc", nativeQuery = true)
    List<Role> getAuthorities(Long idUser);

        List<Role> findRolesByIdUser(Long userId);

    Optional<User> findByAuthenticationInfoEmail(String username);
}