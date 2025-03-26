package ru.davyd.NauJava.repository;

import ru.davyd.NauJava.entity.Task;

import java.util.List;
import java.util.UUID;

/**
 * Интерфейс репозитория для работы с данными задач
 * определяет базовые операции CRUD для задач
 */
public interface TaskRepository {
    /**
     * Сохранение новой задачи в хранилище
     *
     * @param task сохраняемая задача
     */
    void save(Task task);

    /**
     * Получение всех задач из хранилища
     *
     * @return список всех задач
     */
    List<Task> findAll();

    /**
     * Поиск задачи по её идентификатору
     *
     * @param id идентификатор искомой задачи
     * @return найденная задача
     */
    Task findById(UUID id);

    /**
     * Обновление существующей задачи в хранилище
     *
     * @param task обновляемая задача
     */
    void update(Task task);

    /**
     * Удаление задачи из хранилища по её идентификатору
     *
     * @param id идентификатор удаляемой задачи
     */
    void delete(UUID id);
}