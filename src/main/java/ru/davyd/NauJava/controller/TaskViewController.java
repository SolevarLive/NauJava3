package ru.davyd.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ru.davyd.NauJava.repository.TaskRepository;

/**
 * Контроллер для отображения задач в виде HTML-страницы
 * Предоставляет эндпоинт для просмотра списка всех задач
 */
@Controller
public class TaskViewController {

    /**
     * Репозиторий для задач
     * Используется для получения списка всех задач из базы данных
     */
    private final TaskRepository taskRepository;

    /**
     * Конструктор контроллера
     * Внедряет зависимость репозитория через конструктор
     * @param taskRepository Репозиторий для задач
     */
    @Autowired
    public TaskViewController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Эндпоинт для просмотра списка всех задач
     * Возвращает HTML-страницу с таблицей задач
     * @param model Модель для передачи данных на HTML-страницу
     * @return Имя HTML-шаблона для отображения
     */
    @GetMapping("/tasks")
    public String viewTasks(Model model) {
        model.addAttribute("tasks", taskRepository.findAll());
        return "tasks";
    }
}

