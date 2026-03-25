package com.gestoracademico.gestoracademico.repository;

import com.gestoracademico.gestoracademico.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
