package ru.davyd.NauJava.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.davyd.NauJava.entities.Comment;
import ru.davyd.NauJava.entities.Task;
import ru.davyd.NauJava.entities.TaskStatus;
import ru.davyd.NauJava.repository.CommentRepository;
import ru.davyd.NauJava.repository.TaskRepository;

import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для работы с задачами
 */
@Service
@Validated
public class TaskService {

    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);

    /**
     * Репозиторий для задач
     */
    @Autowired
    private TaskRepository taskRepository;

    /**
     * Репозиторий для комментариев
     */
    @Autowired
    private CommentRepository commentRepository;

    /**
     * Создает новую задачу с комментарием
     *
     * @param task   задача для создания
     * @param comment комментарий для создания
     */
    @Transactional
    public void createTaskWithComment(@Valid Task task, Comment comment) {
        try {
            taskRepository.save(task);
            comment.setTask(task);
            commentRepository.save(comment);
        } catch (ConstraintViolationException e) {
            logger.error("Ошибка валидации при создании задачи: " + e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Получает список задач по статусу
     *
     * @param status статус задач для поиска
     * @return список задач с указанным статусом
     */
    public List<Task> getTasksByStatus(TaskStatus status) {
        return ((List<Task>) taskRepository.findAll()).stream()
                .filter(task -> task.getStatus().equals(status))
                .collect(Collectors.toList());
    }

    /**
     * Получает список просроченных задач
     *
     * @return список задач, срок выполнения которых истек
     */
    public List<Task> getOverdueTasks() {
        LocalDate today = LocalDate.now();
        return ((List<Task>) taskRepository.findAll()).stream()
                .filter(task -> {
                    if (task.getDueDate() != null) {
                        LocalDate dueDate = task.getDueDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        return dueDate.isBefore(today);
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }
}
