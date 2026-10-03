package com.app.aulavirtual.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.aulavirtual.entidades.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
