package ru.davyd.NauJava.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.davyd.NauJava.entity.Task;
import ru.davyd.NauJava.storage.DatabaseStorage;

import java.util.List;
import java.util.UUID;

/**
 * Реализация репозитория для работы с данными задач,
 * используем оперативную память как хранилище
 */
@Repository
public class DbTaskRepository implements TaskRepository {
    private final DatabaseStorage storage;

    @Autowired
    public DbTaskRepository(DatabaseStorage storage) {
        this.storage = storage;
    }

    @Override
    public void save(Task task) {
        storage.saveTask(task);
    }

    @Override
    public List<Task> findAll() {
        return storage.getAllTasks();
    }

    @Override
    public Task findById(UUID id) {
        return storage.getTask(id);
    }

    @Override
    public void update(Task task) {
        storage.updateTask(task);
    }

    @Override
    public void delete(UUID id) {
        storage.deleteTask(id);
    }
}
