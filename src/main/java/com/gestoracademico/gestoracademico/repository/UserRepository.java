package com.gestoracademico.gestoracademico.repository;

import com.gestoracademico.gestoracademico.enums.Role;
import com.gestoracademico.gestoracademico.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByUserEmail(String userEmail);
    public List<User> findByRole(Role role);
}
