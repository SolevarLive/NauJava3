package ru.davyd.NauJava.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.davyd.NauJava.criteria.TaskCriteriaRepository;
import ru.davyd.NauJava.entities.Comment;
import ru.davyd.NauJava.entities.Task;
import ru.davyd.NauJava.entities.TaskPriority;
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
     * Репозиторий для критериев задач
     * Используется для выполнения кастомных запросов к базе данных
     */
    private final TaskCriteriaRepository taskCriteriaRepository;

    /**
     * Репозиторий для задач
     * Используется для базовых операций CRUD с задачами
     */
    private final TaskRepository taskRepository;

    /**
     * Репозиторий для комментариев
     * Используется для базовых операций CRUD с комментариями
     */
    private final CommentRepository commentRepository;

    /**
     * Конструктор сервиса
     * Внедряет зависимости через конструктор
     *
     * @param taskCriteriaRepository Репозиторий для критериев задач
     * @param taskRepository         Репозиторий для задач
     * @param commentRepository      Репозиторий для комментариев
     */
    public TaskService(TaskCriteriaRepository taskCriteriaRepository, TaskRepository taskRepository, CommentRepository commentRepository) {
        this.taskCriteriaRepository = taskCriteriaRepository;
        this.taskRepository = taskRepository;
        this.commentRepository = commentRepository;
    }

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

    /**
     * Находит задачи по названию и приоритету
     *
     * @param title   Название задачи для поиска
     * @param priority Приоритет задачи для поиска
     * @return Список задач, соответствующих критериям
     */
    public List<Task> findTasksByTitleAndPriority(String title, String priority) {
        TaskPriority taskPriority = TaskPriority.valueOf(priority);
        return taskCriteriaRepository.findByTitleAndPriorityCriteria(title, taskPriority);
    }

    /**
     * Находит задачи по имени пользователя
     *
     * @param username Имя пользователя для поиска задач
     * @return Список задач, принадлежащих пользователю
     */
    public List<Task> findTasksByUsername(String username) {
        return taskCriteriaRepository.findTasksByUsernameCriteria(username);
    }
}
