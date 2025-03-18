package ru.davyd.NauJava.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.davyd.NauJava.entity.Task;
import ru.davyd.NauJava.entity.TaskStatus;
import ru.davyd.NauJava.service.TaskService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

/**
 * Консольный интерфейс пользователя для работы со системой управления задачами
 * предоставляет меню для взаимодействия с системой через консоль
 */
@Component
public class ConsoleUI {
    private final TaskService taskService;
    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    public ConsoleUI(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Запуск консоли
     */
    public void start(){
        while (true){
            System.out.println("\n=== Меню ===");
            System.out.println("1. Создать задачу");
            System.out.println("2. Показать все задачи");
            System.out.println("3. Найти задачу по ID");
            System.out.println("4. Обновить задачу");
            System.out.println("5. Удалить задачу");
            System.out.println("6. Показать задачи по статусу");
            System.out.println("7. Показать просроченные задачи");
            System.out.println("8. Выход");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice){
                case 1 -> createTask();
                case 2 -> showAllTasks();
                case 3 -> findTaskById();
                case 4 -> updateTask();
                case 5 -> deleteTask();
                case 6 -> showTasksByStatus();
                case 7 -> showOverdueTasks();
                case 8 -> {
                    System.out.println("До свидания!");
                    return;
                }
                default -> System.out.println("Неверный выбор");
            }
        }
    }

    private void createTask() {
        System.out.print("Введите название задачи: ");
        String title = scanner.nextLine();

        System.out.print("Введите описание задачи: ");
        String description = scanner.nextLine();

        System.out.println("Выберите статус:");
        for (TaskStatus status : TaskStatus.values()) {
            System.out.println(status.ordinal() + ": " + status.getDescription());
        }
        TaskStatus status = TaskStatus.values()[Integer.parseInt(scanner.nextLine())];

        System.out.print("Введите дату завершения (ГГГГ-ММ-ДД): ");
        LocalDate dueDate = LocalDate.parse(scanner.nextLine());

        Task task = taskService.createTask(title, description, status, dueDate);
        System.out.println("Задача создана: " + task);
    }

    private void showAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("Задач нет");
            return;
        }
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    private void findTaskById() {
        System.out.print("Введите ID задачи: ");
        UUID id = UUID.fromString(scanner.nextLine());
        Task task = taskService.getTask(id);
        if (task != null) {
            System.out.println(task);
        } else {
            System.out.println("Задача не найдена");
        }
    }

    private void updateTask() {
        System.out.print("Введите ID задачи: ");
        UUID id = UUID.fromString(scanner.nextLine());

        System.out.print("Введите новое название задачи: ");
        String title = scanner.nextLine();

        System.out.print("Введите новое описание задачи: ");
        String description = scanner.nextLine();

        System.out.println("Выберите новый статус:");
        for (TaskStatus status : TaskStatus.values()) {
            System.out.println(status.ordinal() + ": " + status.getDescription());
        }
        TaskStatus status = TaskStatus.values()[Integer.parseInt(scanner.nextLine())];

        System.out.print("Введите новую дату завершения (ГГГГ-ММ-ДД): ");
        LocalDate dueDate = LocalDate.parse(scanner.nextLine());

        Task updatedTask = taskService.updateTask(id, title, description, status, dueDate);
        if (updatedTask != null) {
            System.out.println("Задача обновлена: " + updatedTask);
        } else {
            System.out.println("Задача не найдена");
        }
    }

    private void deleteTask() {
        System.out.print("Введите ID задачи: ");
        UUID id = UUID.fromString(scanner.nextLine());
        taskService.deleteTask(id);
        System.out.println("Задача удалена");
    }

    private void showTasksByStatus() {
        System.out.println("Выберите статус:");
        for (TaskStatus status : TaskStatus.values()) {
            System.out.println(status.ordinal() + ": " + status.getDescription());
        }
        TaskStatus status = TaskStatus.values()[Integer.parseInt(scanner.nextLine())];

        List<Task> tasks = taskService.getTasksByStatus(status);
        if (tasks.isEmpty()) {
            System.out.println("Задач с таким статусом нет");
            return;
        }
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    private void showOverdueTasks() {
        List<Task> tasks = taskService.getOverdueTasks();
        if (tasks.isEmpty()) {
            System.out.println("Просроченных задач нет");
            return;
        }
        for (Task task : tasks) {
            System.out.println(task);
        }
    }
}
