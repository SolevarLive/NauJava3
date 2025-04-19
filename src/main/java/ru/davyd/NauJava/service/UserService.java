package ru.davyd.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.davyd.NauJava.entities.User;
import ru.davyd.NauJava.exceptions.InvalidPasswordException;
import ru.davyd.NauJava.exceptions.UserOrEmailAlreadyExistsException;
import ru.davyd.NauJava.repository.UserRepository;

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
     * Добавляет нового пользователя в базу данных
     * Шифрует пароль пользователя перед сохранением
     * Проверяет на дубликат email и имени пользователя
     *
     * @param user Пользователь для добавления
     * @return Добавленный пользователь
     */
    public User addUser(User user) {
        validateUsernameUnique(user.getUsername());
        validateEmailUnique(user.getEmail());
        validatePassword(user.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Проверяет, что имя пользователя уникально в базе данных
     * Если пользователь с таким именем уже существует, выбрасывает исключение UserOrEmailAlreadyExistsException
     *
     * @param username имя пользователя для проверки уникальности
     * @throws UserOrEmailAlreadyExistsException если имя пользователя уже занято
     */
    private void validateUsernameUnique(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserOrEmailAlreadyExistsException("Пользователь с таким именем уже существует.");
        }
    }

    /**
     * Проверяет, что email уникален в базе данных
     * Если пользователь с таким email уже существует, выбрасывает исключение UserOrEmailAlreadyExistsException
     *
     * @param email email для проверки уникальности
     * @throws UserOrEmailAlreadyExistsException если email уже занят
     */
    private void validateEmailUnique(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserOrEmailAlreadyExistsException("Пользователь с таким email уже существует.");
        }
    }

    /**
     * Проверяет, что пароль соответствует требованиям по длине
     * Если пароль короче 8 символов, выбрасывает исключение InvalidPasswordException
     *
     * @param password пароль для проверки
     * @throws InvalidPasswordException если пароль слишком короткий
     */
    private void validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new InvalidPasswordException("Пароль должен содержать не менее 8 символов.");
        }
    }
}
