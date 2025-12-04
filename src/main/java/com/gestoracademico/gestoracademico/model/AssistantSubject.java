package com.gestoracademico.gestoracademico.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "assistant_subjects")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssistantSubject {
    @EmbeddedId
    private AssistantSubjectId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("subjectId")
    @JoinColumn(name = "subject_id", insertable = false, updatable = false)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("assistantId")
    @JoinColumn(name = "assistant_id", insertable = false, updatable = false)
    private User assistant;

    public AssistantSubject(User professor, Subject subject) {
        this.assistant = professor;
        this.subject = subject;
        this.id = new AssistantSubjectId(professor.getIdUser(), subject.getIdSubject());
    }
}
