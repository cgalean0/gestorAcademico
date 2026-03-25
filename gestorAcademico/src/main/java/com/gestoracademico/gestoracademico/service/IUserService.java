package com.gestoracademico.gestoracademico.service;

import com.gestoracademico.gestoracademico.dto.input.*;
import com.gestoracademico.gestoracademico.dto.output.UserDTO;

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
    List<UserDTO> getUsers();

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
    UserDTO createProfessor(ProfessorCreationDTO professor);

    /**
     * Update a current Professor
     * */
    UserDTO updateProfessor(Long id, ProfessorUpdateDTO professor);

    /**
     * Create a new Student
     * */
    UserDTO createStudent(StudentCreationDTO student);

    /**
     * Update a current Student
     * */
    UserDTO updateStudent(Long id, StudentUpdateDTO student);

    /**
     * Get a list of students
     * */
    List<UserDTO> getStudents();

    /**
     * Get a list of professors
     * */
    List<UserDTO> getProfessors();

}
