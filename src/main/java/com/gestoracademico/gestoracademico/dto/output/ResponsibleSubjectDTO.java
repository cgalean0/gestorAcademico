package com.gestoracademico.gestoracademico.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponsibleSubjectDTO {
    private Long professorId;
    private Long subjectId;

    private String professorName;
    private String subjectName;
}
