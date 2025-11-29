package com.gestoracademico.gestoracademico.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "subjects")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Subject {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long idSubject;
    @Column(name = "code", nullable = false, unique = true)
    private String code;
    @Column(name = "name", nullable = false)
    private String name;
}
