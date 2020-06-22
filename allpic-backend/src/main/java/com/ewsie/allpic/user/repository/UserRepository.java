package com.ewsie.allpic.user.repository;

import com.ewsie.allpic.user.model.User;
import com.ewsie.allpic.user.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<List<User>> findUsersByRole(Role role);
}
