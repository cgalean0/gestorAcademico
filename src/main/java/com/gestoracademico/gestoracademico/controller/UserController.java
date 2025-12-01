package com.gestoracademico.gestoracademico.controller;

import com.gestoracademico.gestoracademico.dto.input.*;
import com.gestoracademico.gestoracademico.dto.output.UserDTO;
import com.gestoracademico.gestoracademico.service.IUserService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final IUserService userService;
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserCreationDTO user) {
        UserDTO createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

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

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserUpdateDTO user) {
        UserDTO updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/professors")
    public ResponseEntity<UserDTO> createProfessor(@RequestBody ProfessorCreationDTO professor) {
        UserDTO createdProfessor = userService.createProfessor(professor);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfessor);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/professors/{id}")
    public ResponseEntity<UserDTO> updateProfessor(@PathVariable Long id, @RequestBody ProfessorUpdateDTO professor) {
        UserDTO updatedProfessor = userService.updateProfessor(id, professor);
        return ResponseEntity.ok(updatedProfessor);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/students")
    public ResponseEntity<UserDTO> createStudent(@RequestBody StudentCreationDTO student) {
        UserDTO createdStudent = userService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/students/{id}")
    public ResponseEntity<UserDTO> updateStudent(@PathVariable Long id, @RequestBody StudentUpdateDTO student) {
        UserDTO updatedStudent = userService.updateStudent(id, student);
        return ResponseEntity.ok(updatedStudent);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO foundUser = userService.getUserById(id);
        return ResponseEntity.ok(foundUser);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        UserDTO foundUser = userService.getUserByEmail(email);
        return ResponseEntity.ok(foundUser);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> userDTOList = userService.getUsers();
        return ResponseEntity.ok(userDTOList);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    @GetMapping("/role/students")
    public ResponseEntity<List<UserDTO>> getStudents() {
        List<UserDTO> studentsDTOList = userService.getStudents();
        return ResponseEntity.ok(studentsDTOList);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/role/professors")
    public ResponseEntity<List<UserDTO>> getProfessors() {
        List<UserDTO> professorsDTOList = userService.getProfessors();
        return ResponseEntity.ok(professorsDTOList);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getMyProfile(java.security.Principal principal) {
        UserDTO userProfile = userService.getUserByEmail(principal.getName());
        return ResponseEntity.ok(userProfile);
    }
}
