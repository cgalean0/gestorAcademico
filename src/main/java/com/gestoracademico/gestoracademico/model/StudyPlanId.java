package com.gestoracademico.gestoracademico.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class StudyPlanId implements Serializable {
    @Column(name = "career_id")
    private Long careerId;
    @Column(name = "subject_id")
    private Long subjectId;
}
