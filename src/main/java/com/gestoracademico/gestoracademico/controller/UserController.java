package com.gestoracademico.gestoracademico.controller;

import com.gestoracademico.gestoracademico.dto.UserDTO;
import com.gestoracademico.gestoracademico.dto.UserUpdateDTO;
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

    /**
     * Solo los Usuarios con Rol ADMIN pueden eliminar a usuarios
     * */
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
    /**
     * Solo los usuarios con rol de ADMIN pueden modificar a un usuario
     * */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{Id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserUpdateDTO user) {
        UserDTO updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

}
