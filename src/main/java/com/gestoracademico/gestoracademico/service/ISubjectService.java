package com.gestoracademico.gestoracademico.service;

import com.gestoracademico.gestoracademico.dto.SubjectCreationDTO;
import com.gestoracademico.gestoracademico.dto.SubjectDTO;
import com.gestoracademico.gestoracademico.dto.SubjectUpdateDTO;

import java.util.List;

public interface ISubjectService {
    SubjectDTO getSubjectById(Long id);
    SubjectDTO updateSubject(Long id, SubjectUpdateDTO subject);
    SubjectDTO createSubject(SubjectCreationDTO subject);
    void deleteSubject(Long id);
    List<SubjectDTO> getSubjects();
}
