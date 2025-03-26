package ru.davyd.NauJava.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.davyd.NauJava.entities.Task;
import ru.davyd.NauJava.entities.TaskPriority;


import java.util.UUID;
import java.util.List;
import java.util.Date;

/**
 * Интерфейс репозитория для задач
 */
public interface TaskRepository extends CrudRepository<Task, UUID> {

    /**
     * Находит задачи по заголовку и приоритету
     *
     * @param title   заголовок задачи для поиска
     * @param priority приоритет задачи для поиска
     * @return список задач, соответствующих критериям
     */
    List<Task> findByTitleContainingIgnoreCaseAndPriority(String title, TaskPriority priority);

    /**
     * Находит задачи по имени пользователя
     *
     * @param username имя пользователя для поиска
     * @return список задач, принадлежащих пользователю
     */
    @Query("SELECT t FROM Task t JOIN t.user u WHERE u.username = :username")
    List<Task> findTasksByUsername(String username);

    /**
     * Находит задачи по заголовку и приоритету или в указанном диапазоне дат
     *
     * @param title       заголовок задачи для поиска
     * @param priority    приоритет задачи для поиска
     * @param startDate   начало диапазона дат
     * @param endDate     конец диапазона дат
     * @return список задач, соответствующих критериям
     */
    List<Task> findByTitleContainingIgnoreCaseAndPriorityOrDueDateBetween(String title, TaskPriority priority, Date startDate, Date endDate);
}
