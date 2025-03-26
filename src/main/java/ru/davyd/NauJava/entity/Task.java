package ru.davyd.NauJava.entity;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Класс представляющий задачу в системе управления задачами
 * содержит информацию о задаче, включая уникальный идентификатор,
 * заголовок, описание, статус и срок выполнения
 */
public class Task {
    private UUID id;
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDate dueDate;

    /**
     * Конструктор, создающий новую задачу с автоматически
     * генерируемым уникальным идентификатором
     */
    public Task() {
        this.id = UUID.randomUUID();
    }

    /**
     * Конструктор для создания новой задачи без указания ID
     * генерирует автоматически уникальный идентификатор
     *
     * @param title       заголовок задачи
     * @param description описание задачи
     * @param status      начальный статус задачи
     * @param dueDate     срок выполнения задачи
     */
    public Task(String title, String description, TaskStatus status, LocalDate dueDate) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
    }

    /**
     * Получение уникального идентификатора задачи
     *
     * @return UUID идентификатор задачи
     */
    public UUID getId() {
        return id;
    }

    /**
     * Установка уникального идентификатора задачи
     *
     * @param id новый идентификатор задачи
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Получение заголовка задачи
     *
     * @return строка с заголовком задачи
     */
    public String getTitle() {
        return title;
    }

    /**
     * Установка заголовка задачи
     *
     * @param title новый заголовок задачи
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Получение описания задачи
     *
     * @return строка с описанием задачи
     */
    public String getDescription() {
        return description;
    }

    /**
     * Установка описания задачи
     *
     * @param description новое описание задачи
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Получение текущего статуса задачи
     *
     * @return статус задачи
     */
    public TaskStatus getStatus() {
        return status;
    }

    /**
     * Установка нового статуса задачи
     *
     * @param status новый статус задачи
     */
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    /**
     * Получение срока выполнения задачи
     *
     * @return дата и время завершения задачи
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * Установка нового срока выполнения задачи
     *
     * @param dueDate новая дата завершения задачи
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "Task{" + "\n" +
                "id=" + id + "\n" +
                "title='" + title + '\'' + "\n" +
                "description='" + description + '\'' + "\n" +
                "status=" + status + "\n" +
                "dueDate=" + dueDate + "\n" +
                '}';
    }
}