package com.gestoracademico.gestoracademico.model;

import com.gestoracademico.gestoracademico.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String userName;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
}
