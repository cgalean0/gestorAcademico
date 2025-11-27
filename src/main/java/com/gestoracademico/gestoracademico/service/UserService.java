package com.gestoracademico.gestoracademico.service;

import com.gestoracademico.gestoracademico.dto.UserCreationDTO;
import com.gestoracademico.gestoracademico.dto.UserDTO;
import com.gestoracademico.gestoracademico.dto.UserUpdateDTO;
import com.gestoracademico.gestoracademico.mapper.Mapper;
import com.gestoracademico.gestoracademico.model.User;
import com.gestoracademico.gestoracademico.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
@Service
public class UserService implements IUserService{
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private User hashAndSaveUser(User user, String plainPassword) {
        String hashPassword = passwordEncoder.encode(plainPassword);
        user.setPassword(hashPassword);
        return userRepository.save(user);
    }
    @Override
    public UserDTO createUser(UserCreationDTO user) {
        if (user == null)
            return null;
        User userEntity = Mapper.toEntity(user);
        String plainPassword = user.getPassword();
        User createdUser = hashAndSaveUser(userEntity, plainPassword);
        return Mapper.toDTO(createdUser);
    }

    @Override
    public UserDTO updateUser(Long id, UserUpdateDTO user) {
        User existClient = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("The User with the ID: " + id + "doesn't exists."));
        existClient.setUserName(user.getUserName());
        existClient.setRole(user.getRole());
        userRepository.save(existClient);
        return Mapper.toDTO(existClient);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id))
            throw new RuntimeException("The User with the ID: " + id + " doesn't exists.");
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDTO> listUsers() {
        return userRepository.findAll().stream().map(Mapper::toDTO).toList();
    }
}
