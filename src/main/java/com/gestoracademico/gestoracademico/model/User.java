package com.gestoracademico.gestoracademico.model;

import com.gestoracademico.gestoracademico.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "app_users")
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_name", unique = true, nullable = false)
    private String userName;
    @Column(name = "password", nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column (name = "role")
    private Role role;
}
