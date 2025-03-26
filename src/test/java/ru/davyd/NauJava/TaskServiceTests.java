package ru.davyd.NauJava;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import ru.davyd.NauJava.entities.*;
import ru.davyd.NauJava.repository.TaskRepository;
import ru.davyd.NauJava.repository.UserRepository;
import ru.davyd.NauJava.service.TaskService;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для сервиса задач
 */
@SpringBootTest
public class TaskServiceTests {

    /**
     * Сервис для задач
     */
    @Autowired
    private TaskService taskService;

    /**
     * Репозиторий для пользователей
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Репозиторий для задач
     */
    @Autowired
    private TaskRepository taskRepository;

    /**
     * Тестовый пользователь
     */
    private User testUser;

    /**
     * Настройка перед каждым тестом
     * Создаёт тестового пользователя
     */
    @BeforeEach
    public void setUp() {
        // Создание тестового пользователя для использования в тестах
        testUser = new User();
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setPassword("password");
        testUser = userRepository.save(testUser);
    }

    /**
     * Очистка после каждого теста
     * Удаляет все задачи тестового пользователя
     */
    @AfterEach
    public void tearDown() {
        if (testUser != null) {
            taskRepository.deleteAll(taskRepository.findTasksByUsername(testUser.getUsername()));
        }
    }

    /**
     * Тестирование создания задачи с комментарием с успешным исходом
     * Проверяет, что задача и комментарий успешно сохранены
     */
    @Test
    @Transactional
    public void testCreateTaskWithComment_Success() {
        Task task = new Task();
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setDueDate(new Date());
        task.setStatus(TaskStatus.OPEN);
        task.setPriority(TaskPriority.MEDIUM);
        task.setUser(testUser);

        Comment comment = new Comment();
        comment.setText("Test Comment");
        comment.setCreatedAt(new Date());

        taskService.createTaskWithComment(task, comment);

        assertNotNull(task.getId());
        assertNotNull(comment.getId());
        assertEquals(task.getId(), comment.getTask().getId());
    }

    /**
     * Тестирование создания задачи с комментарием с откатом транзакции
     * Проверяет, что при создании задачи с слишком длинным заголовком возникает исключение и происходит откат транзакции
     */
    @Test
    @Transactional
    public void testCreateTaskWithComment_Rollback() {
        Task task = new Task();
        task.setTitle("This title is way too long and should cause a DataIntegrityViolationException which should roll back the transaction, " +
                "This title is way too long and should cause a DataIntegrityViolationException which should roll back the transaction");
        task.setDescription("Should cause a rollback");
        task.setDueDate(new Date());
        task.setStatus(TaskStatus.OPEN);
        task.setPriority(TaskPriority.HIGH);
        task.setUser(testUser);

        Comment comment = new Comment();
        comment.setText("Valid Comment");
        comment.setCreatedAt(new Date());

        assertThrows(ConstraintViolationException.class, () -> {
            taskService.createTaskWithComment(task, comment);
        });
    }
}
