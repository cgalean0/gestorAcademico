package com.gestoracademico.gestoracademico.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudyPlanDTO {
    private Long careerId;
    private Long subjectId;

    private String careerName;
    private String subjectName;

    private Integer year;
}
