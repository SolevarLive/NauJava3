package ru.davyd.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.davyd.NauJava.repository.TaskRepository;
import ru.davyd.NauJava.entity.Task;
import ru.davyd.NauJava.entity.TaskStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Реализация сервисного слоя для работы с задачами
 * предоставляет полную функциональность управления задачами через
 * взаимодействие с репозиторием данных
 */
@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskDao;

    @Autowired
    public TaskServiceImpl(TaskRepository taskDao){
        this.taskDao = taskDao;
    }

    @Override
    public Task createTask(String title, String description, TaskStatus status, LocalDate dueDate) {
        Task task = new Task(title, description, status, dueDate);
        taskDao.save(task);
        return task;
    }

    @Override
    public List<Task> getAllTasks() {
        return taskDao.findAll();
    }

    @Override
    public Task getTask(UUID id) {
        return taskDao.findById(id);
    }

    @Override
    public Task updateTask(UUID id, String title, String description, TaskStatus status, LocalDate dueDate) {
        Task task = getTask(id);
        if (task != null){
            task.setTitle(title);
            task.setDescription(description);
            task.setStatus(status);
            task.setDueDate(dueDate);
            taskDao.update(task);
        }
        return task;
    }

    @Override
    public void deleteTask(UUID id) {
        taskDao.delete(id);
    }

    @Override
    public List<Task> getTasksByStatus(TaskStatus status) {
        return getAllTasks().stream()
                .filter(task -> task.getStatus().equals(status))
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> getOverdueTasks() {
        return getAllTasks().stream()
                .filter(task -> task.getDueDate().isBefore(LocalDate.now()))
                .collect(Collectors.toList());
    }
}
