package com.gestoracademico.gestoracademico.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponsibleSubjectId {
    @Column(name = "professor_id")
    private Long professorId;
    @Column(name = "subject_id")
    private Long subjectId;
}
