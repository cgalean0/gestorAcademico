package com.gestoracademico.gestoracademico.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponsibleSubjectCreationDTO {
    @NotNull
    private Long professorId;
    @NotNull
    private Long subjectId;
}
