package com.gestoracademico.gestoracademico.model;

import com.gestoracademico.gestoracademico.enums.Role;
import com.gestoracademico.gestoracademico.enums.TypeStudent;
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
    private Long idUser;
    @Column(name = "user_email", nullable = false)
    private String userEmail;
    @Column(name = "password", nullable = false)
    private String password;

    //Commonly attributes of Students, Professors and Admins
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "dni_person", unique = true, nullable = false)
    private String dni;
    @Column(name = "phone", nullable = false)
    private String phone;

    // Fields to Profesor
    @Column(name = "file_number", nullable = true, unique = true)
    private String fileNumber;

    // Fiels to Students
    @Enumerated(EnumType.STRING)
    @Column (name = "type_student", nullable = false)
    private TypeStudent typeStudent;

    // Roles for privileges ('ADMIN', 'STUDENT', 'PROFESSOR')
    @Enumerated(EnumType.STRING)
    @Column (name = "role", nullable = false)
    private Role role;
}
