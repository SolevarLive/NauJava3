package ru.davyd.NauJava;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.davyd.NauJava.criteria.TaskCriteriaRepository;
import ru.davyd.NauJava.entities.Task;
import ru.davyd.NauJava.entities.enums.TaskPriority;
import ru.davyd.NauJava.entities.User;
import ru.davyd.NauJava.repository.TaskRepository;
import ru.davyd.NauJava.repository.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тесты для репозитория критериев задач
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TaskCriteriaRepositoryTests {

    private static final Logger logger = LoggerFactory.getLogger(TaskCriteriaRepositoryTests.class);

    /**
     * Репозиторий критериев задач
     */
    @Autowired
    private TaskCriteriaRepository taskCriteriaRepository;

    /**
     * Репозиторий задач
     */
    @Autowired
    private TaskRepository taskRepository;

    /**
     * Репозиторий пользователей
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Тестовый пользователь
     */
    private User testUser;

    /**
     * Настройка тестового окружения перед каждым тестом
     * Создаем тестового пользователя
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
     * Очистка тестового окружения после каждого теста
     * Удаляем все задачи, связанные с тестовым пользователем
     */
    @AfterEach
    public void tearDown() {
        if (testUser != null) {
            taskRepository.deleteAll(taskRepository.findTasksByUsername(testUser.getUsername()));
        }
    }

    /**
     * Тестирование поиска задач по заголовку и приоритету
     */
    @Test
    public void testFindByTitleAndPriorityCriteria() {
        Task task1 = new Task();
        task1.setTitle("Criteria Task");
        task1.setPriority(TaskPriority.HIGH);
        task1.setUser(testUser);
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setTitle("Another Task");
        task2.setPriority(TaskPriority.LOW);
        task2.setUser(testUser);
        taskRepository.save(task2);

        List<Task> foundTasks = taskCriteriaRepository.findByTitleAndPriorityCriteria("criteria", TaskPriority.HIGH);

        assertEquals(1, foundTasks.size());
        assertEquals("Criteria Task", foundTasks.get(0).getTitle());
        assertEquals(TaskPriority.HIGH, foundTasks.get(0).getPriority());
    }

    /**
     * Тестирование поиска задач по имени пользователя
     */
    @Test
    public void testFindTasksByUsernameCriteria() {
        Task task1 = new Task();
        task1.setTitle("Criteria Task for testuser");
        task1.setPriority(TaskPriority.MEDIUM);
        task1.setUser(testUser);
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setTitle("Another Task for another user");
        task2.setPriority(TaskPriority.LOW);
        User anotherUser = new User();
        anotherUser.setUsername("anotheruser");
        anotherUser.setEmail("another@example.com");
        anotherUser.setPassword("password");
        anotherUser = userRepository.save(anotherUser);
        task2.setUser(anotherUser);
        taskRepository.save(task2);

        List<Task> foundTasks = taskCriteriaRepository.findTasksByUsernameCriteria("testuser");

        logger.info("Found tasks: {}", foundTasks);

        assertEquals(1, foundTasks.size());
        assertEquals("Criteria Task for testuser", foundTasks.get(0).getTitle());
    }

    /**
     * Тестирование поиска задач по заголовку и приоритету без совпадений
     * Ожидаем отсутствие совпадений
     */
    @Test
    public void testFindByTitleAndPriorityCriteria_NoMatch() {
        Task task1 = new Task();
        task1.setTitle("Criteria Task");
        task1.setPriority(TaskPriority.MEDIUM);
        task1.setUser(testUser);
        taskRepository.save(task1);

        List<Task> foundTasks = taskCriteriaRepository.findByTitleAndPriorityCriteria("criteria", TaskPriority.HIGH);

        assertEquals(0, foundTasks.size());
    }

    /**
     * Тестирование поиска задач по имени пользователя без совпадений
     */
    @Test
    public void testFindTasksByUsernameCriteria_NoMatch() {
        // Arrange
        Task task1 = new Task();
        task1.setTitle("Criteria Task for testuser");
        task1.setPriority(TaskPriority.MEDIUM);
        task1.setUser(testUser);
        taskRepository.save(task1);


        List<Task> foundTasks = taskCriteriaRepository.findTasksByUsernameCriteria("nonexistentuser");
        logger.info("Found tasks: {}", foundTasks);

        assertEquals(0, foundTasks.size());
    }
}
