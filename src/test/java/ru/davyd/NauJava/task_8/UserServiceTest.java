package ru.davyd.NauJava.task_8;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.davyd.NauJava.entities.User;
import ru.davyd.NauJava.exceptions.InvalidPasswordException;
import ru.davyd.NauJava.exceptions.UserOrEmailAlreadyExistsException;
import ru.davyd.NauJava.repository.UserRepository;
import ru.davyd.NauJava.service.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Тестовый класс для UserService
 * Проверяет корректность работы методов добавления пользователя и обработки ошибок
 */
class UserServiceTest {

    /**
     * Мок-объект репозитория пользователей
     */
    @Mock
    private UserRepository userRepository;

    /**
     * Мок-объект кодировщика паролей
     */
    @Mock
    private PasswordEncoder passwordEncoder;

    /**
     * Сервис пользователей с внедрёнными мок-объектами
     */
    @InjectMocks
    private UserService userService;

    /**
     * Тестовый пользователь, используемый в тестах
     */
    private User user;

    /**
     * Инициализация моков и тестового пользователя перед каждым тестом
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password123");
    }

    /**
     * Проверяет успешное добавление пользователя:
     * - Имя пользователя и email уникальны
     * - Пароль корректной длины
     * - Пароль шифруется
     * - Пользователь сохраняется в репозитории
     */
    @Test
    void addUser_Success() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User savedUser = userService.addUser(user);

        assertNotNull(savedUser);
        assertEquals("encodedPassword", savedUser.getPassword());
        verify(userRepository).save(savedUser);
    }

    /**
     * Проверяет, что если имя пользователя уже занято,
     * выбрасывается исключение UserOrEmailAlreadyExistsException
     */
    @Test
    void addUser_UsernameAlreadyExists_ThrowsException() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        UserOrEmailAlreadyExistsException ex = assertThrows(UserOrEmailAlreadyExistsException.class,
                () -> userService.addUser(user));
        assertEquals("Пользователь с таким именем уже существует.", ex.getMessage());
    }

    /**
     * Проверяет, что если email уже занят,
     * выбрасывается исключение UserOrEmailAlreadyExistsException
     */
    @Test
    void addUser_EmailAlreadyExists_ThrowsException() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        UserOrEmailAlreadyExistsException ex = assertThrows(UserOrEmailAlreadyExistsException.class,
                () -> userService.addUser(user));
        assertEquals("Пользователь с таким email уже существует.", ex.getMessage());
    }

    /**
     * Проверяет, что если пароль слишком короткий,
     * выбрасывается исключение InvalidPasswordException
     */
    @Test
    void addUser_PasswordTooShort_ThrowsException() {
        user.setPassword("short");

        InvalidPasswordException ex = assertThrows(InvalidPasswordException.class,
                () -> userService.addUser(user));
        assertEquals("Пароль должен содержать не менее 8 символов.", ex.getMessage());
    }
}
