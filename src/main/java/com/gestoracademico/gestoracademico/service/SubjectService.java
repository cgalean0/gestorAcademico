package com.gestoracademico.gestoracademico.service;

import com.gestoracademico.gestoracademico.dto.SubjectCreationDTO;
import com.gestoracademico.gestoracademico.dto.SubjectDTO;
import com.gestoracademico.gestoracademico.dto.SubjectUpdateDTO;
import com.gestoracademico.gestoracademico.exceptions.SubjectNotFoundException;
import com.gestoracademico.gestoracademico.mapper.SubjectMapper;
import com.gestoracademico.gestoracademico.mapper.SubjectUpdateMapper;
import com.gestoracademico.gestoracademico.model.Subject;
import com.gestoracademico.gestoracademico.repository.SubjectRepository;

import java.util.List;

public class SubjectService implements ISubjectService{

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;
    private final SubjectUpdateMapper subjectUpdateMapper;
    public SubjectService(SubjectRepository subjectRepository, SubjectMapper subjectMapper, SubjectUpdateMapper subjectUpdateMapper) {
        this.subjectRepository = subjectRepository;
        this.subjectMapper = subjectMapper;
        this.subjectUpdateMapper = subjectUpdateMapper;
    }

    @Override
    public SubjectDTO getSubjectById(Long id) {
        Subject foundSubject = subjectRepository.findById(id)
                .orElseThrow(() -> new SubjectNotFoundException(""));
        return subjectMapper.toDTO(foundSubject);
    }

    @Override
    public SubjectDTO updateSubject(Long id, SubjectUpdateDTO subject) {
        Subject existingSubject = subjectRepository.findById(id)
                .orElseThrow(() -> new SubjectNotFoundException(""));
        subjectUpdateMapper.updateEntityToDto(subject, existingSubject);
        Subject updatedSubject = subjectRepository.save(existingSubject);
        return subjectMapper.toDTO(updatedSubject);
    }

    @Override
    public SubjectDTO createSubject(SubjectCreationDTO subject) {
        if (subject == null) return null;
        Subject createSubject = subjectRepository.save(subjectMapper.toEntity(subject));
        return subjectMapper.toDTO(createSubject);
    }

    @Override
    public void deleteSubject(Long id) {
        if (!subjectRepository.existsById(id)) {
            throw new SubjectNotFoundException("");
        }
        subjectRepository.deleteById(id);
    }

    @Override
    public List<SubjectDTO> getSubjects() {
        return subjectRepository.findAll().stream().map(subjectMapper::toDTO).toList();
    }
}
