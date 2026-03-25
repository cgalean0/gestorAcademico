package com.gestoracademico.gestoracademico.controller;

import com.gestoracademico.gestoracademico.dto.input.*;
import com.gestoracademico.gestoracademico.dto.output.UserDTO;
import com.gestoracademico.gestoracademico.service.IUserService;
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

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "Gestión de usuarios del sistema")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final IUserService userService;
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Crear un nuevo usuario", description = "Crea un usuario genérico en el sistema. Solo administradores pueden realizar esta acción.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Prohibido - se requiere rol ADMIN", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserCreationDTO user) {
        UserDTO createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @Operation(summary = "Eliminar un usuario", description = "Elimina un usuario por su ID. Solo administradores pueden realizar esta acción.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuario eliminado exitosamente", content = @Content),
            @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Prohibido - se requiere rol ADMIN", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Actualizar un usuario", description = "Actualiza los datos de un usuario existente. Solo administradores pueden realizar esta acción.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Prohibido - se requiere rol ADMIN", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserUpdateDTO user) {
        UserDTO updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Crear un profesor", description = "Crea un usuario con rol de profesor. Solo administradores pueden realizar esta acción.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Profesor creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Prohibido - se requiere rol ADMIN", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/professors")
    public ResponseEntity<UserDTO> createProfessor(@RequestBody ProfessorCreationDTO professor) {
        UserDTO createdProfessor = userService.createProfessor(professor);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfessor);
    }

    @Operation(summary = "Actualizar un profesor", description = "Actualiza los datos de un profesor existente. Solo administradores pueden realizar esta acción.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profesor actualizado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Prohibido - se requiere rol ADMIN", content = @Content),
            @ApiResponse(responseCode = "404", description = "Profesor no encontrado", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/professors/{id}")
    public ResponseEntity<UserDTO> updateProfessor(@PathVariable Long id, @RequestBody ProfessorUpdateDTO professor) {
        UserDTO updatedProfessor = userService.updateProfessor(id, professor);
        return ResponseEntity.ok(updatedProfessor);
    }

    @Operation(summary = "Crear un estudiante", description = "Crea un usuario con rol de estudiante. Solo administradores pueden realizar esta acción.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Estudiante creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Prohibido - se requiere rol ADMIN", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/students")
    public ResponseEntity<UserDTO> createStudent(@RequestBody StudentCreationDTO student) {
        UserDTO createdStudent = userService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    @Operation(summary = "Actualizar un estudiante", description = "Actualiza los datos de un estudiante existente. Solo administradores pueden realizar esta acción.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estudiante actualizado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Prohibido - se requiere rol ADMIN", content = @Content),
            @ApiResponse(responseCode = "404", description = "Estudiante no encontrado", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/students/{id}")
    public ResponseEntity<UserDTO> updateStudent(@PathVariable Long id, @RequestBody StudentUpdateDTO student) {
        UserDTO updatedStudent = userService.updateStudent(id, student);
        return ResponseEntity.ok(updatedStudent);
    }

    @Operation(summary = "Obtener un usuario por ID", description = "Retorna los datos de un usuario específico. Administradores y profesores pueden realizar esta acción.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Prohibido - se requiere rol ADMIN o PROFESSOR", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO foundUser = userService.getUserById(id);
        return ResponseEntity.ok(foundUser);
    }

    @Operation(summary = "Obtener un usuario por email", description = "Busca y retorna un usuario según su dirección de email. Administradores y profesores pueden realizar esta acción.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Prohibido - se requiere rol ADMIN o PROFESSOR", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content)
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        UserDTO foundUser = userService.getUserByEmail(email);
        return ResponseEntity.ok(foundUser);
    }

    @Operation(summary = "Listar todos los usuarios", description = "Retorna un listado de todos los usuarios registrados. Solo administradores pueden realizar esta acción.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de usuarios",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserDTO.class)))),
            @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Prohibido - se requiere rol ADMIN", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> userDTOList = userService.getUsers();
        return ResponseEntity.ok(userDTOList);
    }

    @Operation(summary = "Listar estudiantes", description = "Retorna un listado de todos los usuarios con rol de estudiante. Administradores y profesores pueden realizar esta acción.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de estudiantes",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserDTO.class)))),
            @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Prohibido - se requiere rol ADMIN o PROFESSOR", content = @Content)
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    @GetMapping("/role/students")
    public ResponseEntity<List<UserDTO>> getStudents() {
        List<UserDTO> studentsDTOList = userService.getStudents();
        return ResponseEntity.ok(studentsDTOList);
    }

    @Operation(summary = "Listar profesores", description = "Retorna un listado de todos los usuarios con rol de profesor. Solo administradores pueden realizar esta acción.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de profesores",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserDTO.class)))),
            @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Prohibido - se requiere rol ADMIN", content = @Content)
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/role/professors")
    public ResponseEntity<List<UserDTO>> getProfessors() {
        List<UserDTO> professorsDTOList = userService.getProfessors();
        return ResponseEntity.ok(professorsDTOList);
    }

    @Operation(summary = "Obtener mi perfil", description = "Retorna los datos del usuario actualmente autenticado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil del usuario",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "401", description = "No autorizado", content = @Content)
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getMyProfile(Principal principal) {
        UserDTO userProfile = userService.getUserByEmail(principal.getName());
        return ResponseEntity.ok(userProfile);
    }
}
