package com.gestoracademico.gestoracademico.controller;

import com.gestoracademico.gestoracademico.dto.input.SubjectCreationDTO;
import com.gestoracademico.gestoracademico.dto.output.SubjectDTO;
import com.gestoracademico.gestoracademico.dto.input.SubjectUpdateDTO;
import com.gestoracademico.gestoracademico.service.ISubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@Tag(name = "Subjects", description = "Gestión de materias")
@SecurityRequirement(name = "bearerAuth")
public class SubjectController {
    private final ISubjectService subjectService;
    public SubjectController(ISubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Operation(summary = "Crear una nueva materia", description = "Crea una materia académica. Solo administradores pueden realizar esta acción.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Materia creada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SubjectDTO.class))),
            @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Prohibido - se requiere rol ADMIN", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<SubjectDTO> createSubject(@RequestBody SubjectCreationDTO subject) {
        SubjectDTO createdSubject = subjectService.createSubject(subject);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubject);
    }

    @Operation(summary = "Actualizar una materia", description = "Actualiza los datos de una materia existente. Administradores y profesores pueden realizar esta acción.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Materia actualizada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SubjectDTO.class))),
            @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Prohibido - se requiere rol ADMIN o PROFESSOR", content = @Content),
            @ApiResponse(responseCode = "404", description = "Materia no encontrada", content = @Content)
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    @PutMapping("/{id}")
    public ResponseEntity<SubjectDTO> updateSubject(@PathVariable Long id, @RequestBody SubjectUpdateDTO subject) {
        SubjectDTO updatedSubject = subjectService.updateSubject(id, subject);
        return ResponseEntity.ok().body(updatedSubject);
    }

    @Operation(summary = "Eliminar una materia", description = "Elimina una materia por su ID. Solo administradores pueden realizar esta acción.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Materia eliminada exitosamente", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Prohibido - se requiere rol ADMIN", content = @Content),
            @ApiResponse(responseCode = "404", description = "Materia no encontrada", content = @Content)
    })
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

    @Operation(summary = "Obtener una materia por ID", description = "Retorna los datos de una materia específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Materia encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = SubjectDTO.class))),
            @ApiResponse(responseCode = "404", description = "Materia no encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<SubjectDTO> getSubjectById(@PathVariable Long id) {
        SubjectDTO subject = subjectService.getSubjectById(id);
        return ResponseEntity.ok(subject);
    }

    @Operation(summary = "Listar todas las materias", description = "Retorna un listado de todas las materias académicas registradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de materias",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SubjectDTO.class))))
    })
    @GetMapping
    public ResponseEntity<List<SubjectDTO>> getSubjects() {
        List<SubjectDTO> subjectDTOList = subjectService.getSubjects();
        return ResponseEntity.ok(subjectDTOList);
    }
}
