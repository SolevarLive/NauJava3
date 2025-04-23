package ru.davyd.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.davyd.NauJava.entities.Task;
import ru.davyd.NauJava.service.TaskService;

import java.util.List;

/**
 * Контроллер для работы с задачами через REST API.
 * Предоставляет эндпоинты для поиска задач по названию и приоритету,
 * а также по имени пользователя.
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    /**
     * Сервис для работы с задачами
     * Используется для выполнения бизнес-логики
     */
    private final TaskService taskService;

    /**
     * Конструктор контроллера
     * Внедряет зависимость сервиса через конструктор
     *
     * @param taskService Сервис для задач
     */
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Эндпоинт для поиска задач по названию и приоритету
     * Возвращает список задач, соответствующих заданным критериям
     *
     * @param title Название задачи для поиска
     * @param priority Приоритет задачи для поиска
     * @return ResponseEntity с списком задач
     */
    @GetMapping("/by-title-and-priority/{title}/{priority}")
    public ResponseEntity<List<Task>> findByTitleAndPriority(@PathVariable String title, @PathVariable String priority) {
        List<Task> tasks = taskService.findTasksByTitleAndPriority(title, priority);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Эндпоинт для поиска задач по имени пользователя
     * Возвращает список задач, принадлежащих пользователю с заданным именем
     *
     * @param username Имя пользователя для поиска задач
     * @return ResponseEntity с списком задач
     */
    @GetMapping("/by-username/{username}")
    public ResponseEntity<List<Task>> findTasksByUsername(@PathVariable String username) {
        List<Task> tasks = taskService.findTasksByUsername(username);
        return ResponseEntity.ok(tasks);
    }
}
