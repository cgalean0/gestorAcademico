package com.gestoracademico.gestoracademico.security;
import com.gestoracademico.gestoracademico.dto.AuthResponseDTO;
import com.gestoracademico.gestoracademico.model.User;
import com.gestoracademico.gestoracademico.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
public class AuthService implements IAuthService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    private boolean checkPassword(String plainPassword, String storeHash) {
        return passwordEncoder.matches(plainPassword, storeHash);
    }

    @Override
    public AuthResponseDTO login(String userName, String plainPassword) throws AuthenticationException {
        //Search by UserName
        User user = userRepository.findByUserName(userName)
                            .orElseThrow(() -> new UsernameNotFoundException("The user: " + userName + "Not found"));
        String storedHash = user.getPassword();
        if (checkPassword(plainPassword, storedHash)) {
            String token = jwtProvider.generateToken(user);
            return new AuthResponseDTO(token, user.getUserName());
        } else {
            throw new AuthenticationException("Invalid credentials.");
        }
}

    @Override
    public void resetPassword(String userName, String newPassword) {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("The user: " + userName + "Not found"));
        user.setPassword(newPassword);
        userRepository.save(user);
    }
}
