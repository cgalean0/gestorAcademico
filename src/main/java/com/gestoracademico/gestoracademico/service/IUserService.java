package com.gestoracademico.gestoracademico.service;

import com.gestoracademico.gestoracademico.dto.*;

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

    /**
     * Get user data by id
     * */
    UserDTO getUserById(Long id);

    /**
     * Get user data from email
     * */
    UserDTO getUserByEmail(String email);
    /**
     * Create a new Professor
     * */
    UserDTO createProfessor(ProfessorCreationDTO prof);

    /**
     * Update a current Professor
     * */
    UserDTO updateProfessor(Long id, ProfessorUpdateDTO prof);

    /**
     * Create a new Student
     * */
    UserDTO createStudent(StudentCreationDTO stud);

    /**
     * Update a current Student
     * */
    UserDTO updateStudent(Long id, StudentUpdateDTO stud);

}
