package ru.davyd.NauJava.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Конфигурация безопасности для приложения
 * Настройка авторизации и аутентификации с использованием Spring Security
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    /**
     * Создает Bean для PasswordEncoder, используя BCryptPasswordEncoder
     * Используется для шифрования и проверки паролей пользователей
     *
     * @return PasswordEncoder для шифрования паролей
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Настройка цепочки фильтров безопасности для приложения
     * Определяет правила доступа к различным ресурсам приложения
     *
     * @param http Объект для настройки безопасности HTTP
     * @return Цепочка фильтров безопасности
     * @throws Exception Если возникает ошибка при настройке безопасности
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register").permitAll()
                        .requestMatchers("/swagger-ui/**").hasRole("ADMIN")
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll())
                .formLogin(Customizer.withDefaults())
                .logout(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}
