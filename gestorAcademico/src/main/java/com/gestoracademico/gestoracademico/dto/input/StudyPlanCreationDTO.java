package com.gestoracademico.gestoracademico.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudyPlanCreationDTO {
    @NotNull
    private Long careerId;
    @NotNull
    private Long subjectId;
    @NotNull
    private Integer year;
}
