package ru.davyd.NauJava.storage;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import ru.davyd.NauJava.entity.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Класс для хранения задач
 * реализует потокобезопасное хранение и управление задачами
 * использует AtomicReference для обеспечения потокобезопасности
 */
@Component
@Configuration
public class DatabaseStorage {
    private final AtomicReference<List<Task>> tasks = new AtomicReference<>(new ArrayList<>());

    /**
     * Сохраняет новую задачу в хранилище
     *
     * @param task задача для сохранения
     */
    public synchronized void saveTask(Task task) {
        tasks.get().add(task);
    }

    /**
     * Возвращает список всех задач из хранилища
     * создает новый список для защиты от внешних модификаций
     *
     * @return список всех задач
     */
    public synchronized List<Task> getAllTasks() {
        return new ArrayList<>(tasks.get());
    }

    /**
     * Ищет задачу по её уникальному идентификатору
     *
     * @param id идентификатор искомой задачи
     * @return найденная задача или null если задача не существует
     */
    public synchronized Task getTask(UUID id) {
        for (Task task : tasks.get()) {
            if (task.getId().equals(id)) {
                return task;
            }
        }
        return null;
    }

    /**
     * Обновляет существующую задачу в хранилище
     *
     * @param task задача с обновленными данными
     */
    public synchronized void updateTask(Task task) {
        List<Task> currentTasks = tasks.get();
        int index = 0;
        while (index < currentTasks.size()) {
            if (currentTasks.get(index).getId().equals(task.getId())) {
                currentTasks.set(index, task);
                break;
            }
            index++;
        }
    }

    /**
     * Удаляет задачу из хранилища по её идентификатору
     *
     * @param id идентификатор удаляемой задачи
     */
    public synchronized void deleteTask(UUID id) {
        List<Task> currentTasks = tasks.get();
        int index = 0;
        while (index < currentTasks.size()) {
            if (currentTasks.get(index).getId().equals(id)) {
                currentTasks.remove(index);
                break;
            }
            index++;
        }
    }
}