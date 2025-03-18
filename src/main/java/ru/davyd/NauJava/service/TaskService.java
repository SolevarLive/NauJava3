package ru.davyd.NauJava.service;

import ru.davyd.NauJava.entity.Task;
import ru.davyd.NauJava.entity.TaskStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Интерфейс для работы со службой управления задачами
 * предоставляет методы для создания, чтения, обновления и удаления задач,
 * а также методы для фильтрации задач по статусу и срокам
 */
public interface TaskService {
    /**
     * Создание новой задачи в системе
     *
     * @param title       заголовок задачи
     * @param description описание задачи
     * @param status      начальный статус задачи
     * @param dueDate     срок выполнения задачи
     * @return созданная задача с присвоенным ID
     */
    Task createTask(String title, String description, TaskStatus status, LocalDate dueDate);

    /**
     * Получение списка всех задач в системе
     *
     * @return список всех существующих задач
     */
    List<Task> getAllTasks();

    /**
     * Поиск задачи по её уникальному идентификатору
     *
     * @param id идентификатор искомой задачи
     * @return найденная задача или null если задача не существует
     */
    Task getTask(UUID id);

    /**
     * Обновление существующей задачи в системе
     *
     * @param id          идентификатор обновляемой задачи
     * @param title       новое название задачи
     * @param description новое описание задачи
     * @param status      новый статус задачи
     * @param dueDate     новый срок выполнения
     * @return обновленная задача или null если задача не найдена
     */
    Task updateTask(UUID id, String title, String description, TaskStatus status, LocalDate dueDate);

    /**
     * Удаление задачи из системы по её идентификатору
     *
     * @param id идентификатор удаляемой задачи
     */
    void deleteTask(UUID id);

    /**
     * Получение списка задач с указанным статусом
     *
     * @param status фильтруемый статус
     * @return список задач с соответствующим статусом
     */
    List<Task> getTasksByStatus(TaskStatus status);

    /**
     * Получение списка просроченных задач
     *
     * @return список задач, срок выполнения которых истёк
     */
    List<Task> getOverdueTasks();
}
