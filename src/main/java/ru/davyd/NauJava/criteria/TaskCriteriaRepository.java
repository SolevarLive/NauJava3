package ru.davyd.NauJava.criteria;


import ru.davyd.NauJava.entities.Task;
import ru.davyd.NauJava.entities.TaskPriority;

import java.util.List;
/**
 * Интерфейс для работы с задачами по критериям
 */
public interface TaskCriteriaRepository {

    /**
     * Находит задачи по заголовку и приоритету
     *
     * @param title   заголовок задачи для поиска
     * @param priority приоритет задачи для поиска
     * @return список задач, соответствующих критериям
     */
    List<Task> findByTitleAndPriorityCriteria(String title, TaskPriority priority);

    /**
     * Находит задачи по имени пользователя
     *
     * @param username имя пользователя для поиска
     * @return список задач, принадлежащих пользователю
     */
    List<Task> findTasksByUsernameCriteria(String username);
}