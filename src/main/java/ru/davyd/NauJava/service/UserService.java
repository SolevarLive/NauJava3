package ru.davyd.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.davyd.NauJava.entities.User;
import ru.davyd.NauJava.repository.UserRepository;

import java.util.Optional;

/**
 * Сервис для работы с пользователями
 * Предоставляет методы для добавления и поиска пользователей
 */
@Service
public class UserService {

    /**
     * Репозиторий для работы с пользователями
     * Используется для базовых операций CRUD с пользователями
     */
    private final UserRepository userRepository;

    /**
     * Кодировщик паролей
     * Используется для шифрования паролей пользователей
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Конструктор сервиса
     * Внедряет зависимости репозитория и кодировщика паролей через конструктор
     *
     * @param userRepository Репозиторий для работы с пользователями
     * @param passwordEncoder Кодировщик паролей
     */
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Находит пользователя по имени
     * Возвращает Optional, содержащий пользователя, если он найден
     *
     * @param username Имя пользователя для поиска
     * @return Optional с информацией о пользователе
     */
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Добавляет нового пользователя в базу данных
     * Шифрует пароль пользователя перед сохранением
     *
     * @param user Пользователь для добавления
     * @return Добавленный пользователь
     */
    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
