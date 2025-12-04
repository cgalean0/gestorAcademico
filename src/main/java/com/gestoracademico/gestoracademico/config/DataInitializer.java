package com.gestoracademico.gestoracademico.config;

import com.gestoracademico.gestoracademico.enums.TypeStudent;
import com.gestoracademico.gestoracademico.model.User;
import com.gestoracademico.gestoracademico.enums.Role;
import com.gestoracademico.gestoracademico.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    // El CommandLineRunner se ejecuta automáticamente al inicio.
    @Bean
    public CommandLineRunner initData(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        return args -> {
            // Verifica si el usuario ya existe para no duplicarlo (opcional)
            if (userRepository.findByUserEmail("pepe@email.com").isEmpty()) {

                // 1. Genera el hash dentro del código (es más seguro)
                String hashedPassword = passwordEncoder.encode("pepe123");

                // 2. Crea la entidad de Usuario
                User adminUser = User.builder()
                        .userEmail("pepe@email.com")
                        .password(hashedPassword) // Contraseña ya hasheada
                        .name("pepe")
                        .lastName("marconi")
                        .dni("45456765")
                        .phone("123456789")
                        .role(Role.ADMIN)
                        .build();

                // 3. Guarda en la base de datos
                userRepository.save(adminUser);
                userRepository.save(
                        User.builder()
                                .userEmail("TestUser@email.com")
                                .password(passwordEncoder.encode("pass"))
                                .name("Test")
                                .lastName("User")
                                .dni("45456768")
                                .phone("1234567890")
                                .typeStudent(TypeStudent.Begginer)
                                .role(Role.STUDENT)
                                .build()
                );
            }
        };
    }
}