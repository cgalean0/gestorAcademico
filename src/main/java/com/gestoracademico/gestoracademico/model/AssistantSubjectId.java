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
public class AssistantSubjectId {
    @Column(name = "assistant_id")
    private Long assistantId;
    @Column(name = "subject_id")
    private Long subjectId;
}
