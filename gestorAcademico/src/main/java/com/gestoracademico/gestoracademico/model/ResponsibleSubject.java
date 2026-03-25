package com.gestoracademico.gestoracademico.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "responsible_subjects")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponsibleSubject {
    @EmbeddedId
    private ResponsibleSubjectId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("subjectId")
    @JoinColumn(name = "subject_id", insertable = false, updatable = false)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("professorId")
    @JoinColumn(name = "professor_id", insertable = false, updatable = false)
    private User professor;

    public ResponsibleSubject(User professor, Subject subject) {
        this.professor = professor;
        this.subject = subject;
        this.id = new ResponsibleSubjectId(professor.getIdUser(), subject.getIdSubject());
    }

}
