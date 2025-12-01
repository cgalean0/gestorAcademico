package com.gestoracademico.gestoracademico.controller;

import com.gestoracademico.gestoracademico.dto.input.SubjectCreationDTO;
import com.gestoracademico.gestoracademico.dto.output.SubjectDTO;
import com.gestoracademico.gestoracademico.dto.input.SubjectUpdateDTO;
import com.gestoracademico.gestoracademico.service.ISubjectService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {
    private final ISubjectService subjectService;
    public SubjectController(ISubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<SubjectDTO> createSubject(@RequestBody SubjectCreationDTO subject) {
        SubjectDTO createdSubject = subjectService.createSubject(subject);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubject);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    @PutMapping("/{id}")
    public ResponseEntity<SubjectDTO> updateSubject(@PathVariable Long id, @RequestBody SubjectUpdateDTO subject) {
        SubjectDTO updatedSubject = subjectService.updateSubject(id, subject);
        return ResponseEntity.ok().body(updatedSubject);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        try {
            subjectService.deleteSubject(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectDTO> getSubjectById(@PathVariable Long id) {
        SubjectDTO subject = subjectService.getSubjectById(id);
        return ResponseEntity.ok(subject);
    }

    @GetMapping
    public ResponseEntity<List<SubjectDTO>> getSubjects() {
        List<SubjectDTO> subjectDTOList = subjectService.getSubjects();
        return ResponseEntity.ok(subjectDTOList);
    }
}
