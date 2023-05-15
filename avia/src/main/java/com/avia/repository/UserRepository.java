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
            " join l_user_role ur on r.id_role = ur.id_role " +
            " join users u on u.id_user = ur.id_user " +
            " where ur.id_user = :idUser " +
            " order by ur.id_user desc", nativeQuery = true)
    List<Role> getAuthorities(Long idUser);
}