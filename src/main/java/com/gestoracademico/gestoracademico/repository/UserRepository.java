package com.gestoracademico.gestoracademico.repository;

import com.gestoracademico.gestoracademico.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByUserName(String userName);
}
