package com.gestoracademico.gestoracademico.security;
import com.gestoracademico.gestoracademico.dto.output.AuthResponseDTO;
import com.gestoracademico.gestoracademico.model.User;
import com.gestoracademico.gestoracademico.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
public class AuthService implements IAuthService{
    private final String MESSAGE_ERROR = "Invalid user or password";
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
    public AuthResponseDTO login(String userEmail, String plainPassword) throws AuthenticationException {
        //Search by UserName
        User user = userRepository.findByUserEmail(userEmail)
                            .orElseThrow(() -> new BadCredentialsException(MESSAGE_ERROR));
        String storedHash = user.getPassword();
        // Check that the password in plain text is equal to storeHash
        if (checkPassword(plainPassword, storedHash)) {
            //Generate a JWT token
            String token = jwtProvider.generateToken(user);
            return new AuthResponseDTO(token, user.getUserEmail());
        } else {
            throw new BadCredentialsException(MESSAGE_ERROR);
        }
    }

    @Override
    public void resetPassword(String userEmail, String newPassword) {
        User user = userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new BadCredentialsException(MESSAGE_ERROR));
        user.setPassword(newPassword);
        userRepository.save(user);
    }
}
