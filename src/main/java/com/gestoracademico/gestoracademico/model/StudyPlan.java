package com.gestoracademico.gestoracademico.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "study_plan")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudyPlan implements Serializable {
    @EmbeddedId
    private StudyPlanId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("careerId")
    @JoinColumn(name = "career_id", insertable = false, updatable = false)
    private Career career;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("subjectId")
    @JoinColumn(name = "subject_id", insertable = false, updatable = false)
    private Subject subject;

    @Column(name = "year")
    private Integer year;
}
