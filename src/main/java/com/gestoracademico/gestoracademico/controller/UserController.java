package com.gestoracademico.gestoracademico.controller;

import com.gestoracademico.gestoracademico.dto.*;
import com.gestoracademico.gestoracademico.service.IUserService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final IUserService userService;
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{id}")
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
    @PutMapping("/users/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserUpdateDTO user) {
        UserDTO updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/professor")
    public ResponseEntity<UserDTO> createProfessor(@RequestBody ProfessorCreationDTO professor) {
        UserDTO createdProfessor = userService.createProfessor(professor);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfessor);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/professor/{id}")
    public ResponseEntity<UserDTO> updateProfessor(@PathVariable Long id, @RequestBody ProfessorUpdateDTO professor) {
        UserDTO updatedProfessor = userService.updateProfessor(id, professor);
        return ResponseEntity.ok(updatedProfessor);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/student")
    public ResponseEntity<UserDTO> createStudent(@RequestBody StudentCreationDTO student) {
        UserDTO createdStudent = userService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/student/{id}")
    public ResponseEntity<UserDTO> updateStudent(@PathVariable Long id, @RequestBody StudentUpdateDTO student) {
        UserDTO updatedStudent = userService.updateStudent(id, student);
        return ResponseEntity.ok(updatedStudent);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO foundUser = userService.getUserById(id);
        return ResponseEntity.ok(foundUser);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSOR')")
    @GetMapping("/users/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        UserDTO foundUser = userService.getUserByEmail(email);
        return ResponseEntity.ok(foundUser);
    }


}
