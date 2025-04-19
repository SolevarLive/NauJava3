package ru.davyd.NauJava.service.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.davyd.NauJava.entities.User;
import ru.davyd.NauJava.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Реализация интерфейса UserDetailsService для кастомной загрузки пользователей
 * Используется для аутентификации пользователей в приложении
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * Репозиторий для работы с пользователями
     * Используется для поиска пользователей по имени
     */
    private final UserRepository userRepository;

    /**
     * Конструктор сервиса
     * Внедряет зависимость репозитория через конструктор
     *
     * @param userRepository Репозиторий для работы с пользователями
     */
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Загружает пользователя по имени
     * Если пользователь не найден, бросает исключение UsernameNotFoundException
     * Назначает роли пользователю на основе его статуса (администратор или пользователь)
     *
     * @param username Имя пользователя для загрузки
     * @return UserDetails с информацией о пользователе и его ролями
     * @throws UsernameNotFoundException Если пользователь не найден
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь не найден: " + username);
        }

        User user = optionalUser.get();
        List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                user.isAdmin() ? new SimpleGrantedAuthority("ROLE_ADMIN") : new SimpleGrantedAuthority("ROLE_USER")
        );

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}
