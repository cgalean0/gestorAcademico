package com.gestoracademico.gestoracademico.service;

import com.gestoracademico.gestoracademico.dto.UserCreationDTO;
import com.gestoracademico.gestoracademico.dto.UserDTO;
import com.gestoracademico.gestoracademico.dto.UserUpdateDTO;
import com.gestoracademico.gestoracademico.model.User;

import java.util.List;

public interface IUserService {
    /**
     * Create a new admin User in the database
     * */
    UserDTO createUser(UserCreationDTO user);
    /**
     * Update already exist User in the db
     * */
    UserDTO updateUser(Long id, UserUpdateDTO user);
    /**
     * Delete a User in the DB.
     * */
    void deleteUser(Long id);
    /**
     * Returns the List of Users in the DB
     * */
    List<UserDTO> listUsers();
}
