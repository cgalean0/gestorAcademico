package com.gestoracademico.gestoracademico.controller;

import com.gestoracademico.gestoracademico.dto.input.CareerCreationDTO;
import com.gestoracademico.gestoracademico.dto.input.CareerUpdateDTO;
import com.gestoracademico.gestoracademico.dto.output.CareerDTO;
import com.gestoracademico.gestoracademico.service.ICareerService;
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
@RequestMapping("/api/careers")
@Tag(name = "Careers", description = "Gestión de carreras académicas")
@SecurityRequirement(name = "bearerAuth")
public class CareerController {
    private final ICareerService careerService;

    public CareerController(ICareerService careerService) {
        this.careerService = careerService;
    }

    @Operation(summary = "Crear una nueva carrera", description = "Crea una carrera académica. Solo administradores pueden realizar esta acción.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Carrera creada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CareerDTO.class))),
            @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Prohibido - se requiere rol ADMIN", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CareerDTO> createCareer(@RequestBody CareerCreationDTO career) {
        CareerDTO createdCareer = careerService.createCareer(career);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCareer);
    }

    @Operation(summary = "Actualizar una carrera", description = "Actualiza los datos de una carrera existente. Administradores y profesores pueden realizar esta acción.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrera actualizada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CareerDTO.class))),
            @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Prohibido - se requiere rol ADMIN o PROFESSOR", content = @Content),
            @ApiResponse(responseCode = "404", description = "Carrera no encontrada", content = @Content)
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    @PutMapping("/{id}")
    public ResponseEntity<CareerDTO> updateCareer(@PathVariable Long id, @RequestBody CareerUpdateDTO career) {
        CareerDTO updatedCareer = careerService.updateCareer(id, career);
        return ResponseEntity.ok(updatedCareer);
    }

    @Operation(summary = "Obtener una carrera por ID", description = "Retorna los datos de una carrera específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrera encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CareerDTO.class))),
            @ApiResponse(responseCode = "404", description = "Carrera no encontrada", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<CareerDTO> getCareer(@PathVariable Long id) {
        CareerDTO career = careerService.getCareer(id);
        return ResponseEntity.ok(career);
    }

    @Operation(summary = "Listar todas las carreras", description = "Retorna un listado de todas las carreras académicas registradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de carreras",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CareerDTO.class))))
    })
    @GetMapping
    public ResponseEntity<List<CareerDTO>> getCareers() {
        List<CareerDTO> careerDTOList = careerService.getCareers();
        return ResponseEntity.ok(careerDTOList);
    }

    @Operation(summary = "Eliminar una carrera", description = "Elimina una carrera por su ID. Solo administradores pueden realizar esta acción.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Carrera eliminada exitosamente", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Prohibido - se requiere rol ADMIN", content = @Content),
            @ApiResponse(responseCode = "404", description = "Carrera no encontrada", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCareer(@PathVariable Long id) {
        try {
            careerService.deleteCareer(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
