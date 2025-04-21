package ru.davyd.NauJava;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.davyd.NauJava.entities.Task;
import ru.davyd.NauJava.entities.enums.TaskPriority;
import ru.davyd.NauJava.entities.User;
import ru.davyd.NauJava.repository.TaskRepository;
import ru.davyd.NauJava.repository.UserRepository;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тесты для репозитория задач
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TaskRepositoryTests {

    /**
     * Репозиторий для задач
     */
    @Autowired
    private TaskRepository taskRepository;

    /**
     * Репозиторий для пользователей
     */
    @Autowired
    private UserRepository userRepository;

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
        taskRepository.deleteAll(taskRepository.findTasksByUsername("testuser"));
    }

    /**
     * Тестирование поиска задач по заголовку и приоритету
     * Проверяет, что найдена одна задача с соответствующим заголовком и приоритетом
     */
    @Test
    public void testFindByTitleContainingIgnoreCaseAndPriority() {
        Task task1 = new Task();
        task1.setTitle("Important Task");
        task1.setPriority(TaskPriority.HIGH);
        task1.setUser(testUser);
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setTitle("Another Task");
        task2.setPriority(TaskPriority.LOW);
        task2.setUser(testUser);
        taskRepository.save(task2);

        List<Task> foundTasks = taskRepository.findByTitleContainingIgnoreCaseAndPriority("task", TaskPriority.HIGH);

        assertEquals(1, foundTasks.size());
        assertEquals("Important Task", foundTasks.get(0).getTitle());
        assertEquals(TaskPriority.HIGH, foundTasks.get(0).getPriority());
    }

    /**
     * Тестирование поиска задач по имени пользователя
     * Проверяет, что найдена одна задача для тестового пользователя
     */
    @Test
    public void testFindTasksByUsername() {
        Task task1 = new Task();
        task1.setTitle("Task for testuser");
        task1.setPriority(TaskPriority.MEDIUM);
        task1.setUser(testUser);
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setTitle("Task for anotheruser");
        task2.setPriority(TaskPriority.LOW);
        User anotherUser = new User();
        anotherUser.setUsername("anotheruser");
        anotherUser.setEmail("another@example.com");
        anotherUser.setPassword("password");
        anotherUser = userRepository.save(anotherUser);
        task2.setUser(anotherUser);
        taskRepository.save(task2);

        List<Task> foundTasks = taskRepository.findTasksByUsername("testuser");

        assertEquals(1, foundTasks.size());
        assertEquals("Task for testuser", foundTasks.get(0).getTitle());
    }

    /**
     * Тестирование поиска задач по заголовку и приоритету без совпадений
     * Проверяет, что не найдено задач с соответствующим заголовком и приоритетом
     */
    @Test
    public void testFindByTitleContainingIgnoreCaseAndPriority_NoMatch() {
        Task task1 = new Task();
        task1.setTitle("Important Task");
        task1.setPriority(TaskPriority.MEDIUM);
        task1.setUser(testUser);
        taskRepository.save(task1);

        List<Task> foundTasks = taskRepository.findByTitleContainingIgnoreCaseAndPriority("task", TaskPriority.HIGH);

        assertEquals(0, foundTasks.size());
    }

    /**
     * Тестирование поиска задач по имени пользователя без совпадений
     * Проверяет, что не найдено задач для несуществующего пользователя
     */
    @Test
    public void testFindTasksByUsername_NoMatch() {
        Task task1 = new Task();
        task1.setTitle("Task for testuser");
        task1.setPriority(TaskPriority.MEDIUM);
        task1.setUser(testUser);
        taskRepository.save(task1);

        List<Task> foundTasks = taskRepository.findTasksByUsername("nonexistentuser");

        assertEquals(0, foundTasks.size());
    }

    /**
     * Тестирование поиска задач по заголовку и приоритету или диапазону дат
     * Проверяет, что найдены обе задачи, соответствующие либо приоритету и заголовку, либо диапазону
     * Ожидаем найти обе задачи, т.к. одна соответствует приоритету и заголовку, а другая - диапазону дат
     */
    @Test
    public void testFindByTitleContainingIgnoreCaseAndPriorityOrDueDateBetween() {
        User testUser = new User();
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");
        testUser.setPassword("password");
        testUser = userRepository.save(testUser);

        Task task1 = new Task();
        task1.setTitle("Important Task");
        task1.setPriority(TaskPriority.HIGH);
        task1.setDueDate(new Date(2025 - 1900, 03 - 1, 25));
        task1.setUser(testUser);
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setTitle("Another Task");
        task2.setPriority(TaskPriority.LOW);
        task2.setDueDate(new Date(2025 - 1900, 03 - 1, 20));
        task2.setUser(testUser);
        taskRepository.save(task2);

        Date startDate = new Date(2025 - 1900, 03 - 1, 15);
        Date endDate = new Date(2025 - 1900, 03 - 1, 22);

        List<Task> foundTasks = taskRepository.findByTitleContainingIgnoreCaseAndPriorityOrDueDateBetween(
                "task", TaskPriority.HIGH, startDate, endDate
        );

        assertEquals(2, foundTasks.size());
    }
}
