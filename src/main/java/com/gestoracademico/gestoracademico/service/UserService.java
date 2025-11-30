package com.gestoracademico.gestoracademico.service;

import com.gestoracademico.gestoracademico.dto.*;
import com.gestoracademico.gestoracademico.enums.Role;
import com.gestoracademico.gestoracademico.exceptions.UserNotFoundException;
import com.gestoracademico.gestoracademico.mapper.UserMapper;
import com.gestoracademico.gestoracademico.mapper.UserUpdateMapper;
import com.gestoracademico.gestoracademico.model.User;
import com.gestoracademico.gestoracademico.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
@Service
public class UserService implements IUserService{
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserUpdateMapper userUpdateMapper;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, UserMapper userMapper, UserUpdateMapper userUpdateMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.userUpdateMapper = userUpdateMapper;
    }

    private User hashAndSaveUser(User user, String plainPassword) {
        if (plainPassword == null || plainPassword.trim().isEmpty())
            throw new IllegalArgumentException("");

        String hashPassword = passwordEncoder.encode(plainPassword);
        user.setPassword(hashPassword);
        return userRepository.save(user);
    }
    @Override
    public UserDTO createUser(UserCreationDTO user) {
        if (user == null)
            return null;
        User userEntity = userMapper.toEntity(user);
        String plainPassword = user.getPassword();
        User createdUser = hashAndSaveUser(userEntity, plainPassword);
        return userMapper.toDTO(createdUser);
    }

    @Override
    public UserDTO updateUser(Long id, UserUpdateDTO user) {
        User existClient = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(""));
        userUpdateMapper.updateEntityToDto(user, existClient);
        User updatedUser = userRepository.save(existClient);
        return userMapper.toDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id))
            throw new UserNotFoundException("");
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDTO> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(""));
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByUserEmail(email)
                .orElseThrow(() -> new UserNotFoundException(""));
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO createProfessor(ProfessorCreationDTO prof) {
        if (prof == null) return null;
        User professor = userMapper.toEntity(prof);
        String plainPassword = prof.getPassword();
        professor.setRole(Role.PROFESSOR);
        User createdProfessor = hashAndSaveUser(professor, plainPassword);
        return userMapper.toDTO(createdProfessor);
    }

    @Override
    public UserDTO updateProfessor(Long id, ProfessorUpdateDTO professor) {
        User existingProfessor = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(""));
        userUpdateMapper.updateEntityToDto(professor, existingProfessor);
        User updatedProfessor = userRepository.save(existingProfessor);
        return userMapper.toDTO(updatedProfessor);
    }

    @Override
    public UserDTO createStudent(StudentCreationDTO stud) {
        if (stud == null)
            return null;
        User student = userMapper.toEntity(stud);
        String plainPassword = stud.getPassword();
        student.setRole(Role.STUDENT);
        User createdStudent = hashAndSaveUser(student, plainPassword);
        return userMapper.toDTO(createdStudent);
    }

    @Override
    public UserDTO updateStudent(Long id, StudentUpdateDTO student) {
        User existingStudent = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(""));
        userUpdateMapper.updateEntityToDto(student, existingStudent);
        User updatedStudent = userRepository.save(existingStudent);
        return userMapper.toDTO(updatedStudent);
    }

    @Override
    public List<UserDTO> getStudents() {
        return userRepository.findByRole(Role.STUDENT)
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Override
    public List<UserDTO> getProfessors() {
        return userRepository.findByRole(Role.PROFESSOR)
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }
}
