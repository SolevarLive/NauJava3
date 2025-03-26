package ru.davyd.NauJava.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Класс представляющий задачу в системе управления задачами
 * содержит информацию о задаче, включая уникальный идентификатор,
 * заголовок, описание, статус и срок выполнения
 */
@Entity
@Table(name = "tasks")
public class Task {
    /**
     * Уникальный идентификатор задачи
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Заголовок задачи
     */
    @Size(max = 40)
    @Column(length = 40)
    private String title;

    /**
     * Описание задачи
     */
    private String description;

    /**
     * Срок выполнения задачи
     */
    private Date dueDate;

    /**
     * Статус задачи
     */
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    /**
     * Приоритет задачи
     */
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    /**
     * Пользователь, которому принадлежит задача
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Список комментариев к задаче
     */
    @OneToMany(mappedBy = "task")
    private List<Comment> comments;

    /**
     * Список тегов, примененных к задаче
     */
    @ManyToMany
    @JoinTable(
            name = "task_tags",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    /**
     * Проект, к которому относится задача
     */
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

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
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * Установка нового срока выполнения задачи
     *
     * @param dueDate новая дата завершения задачи
     */
    public void setDueDate(Date dueDate) {
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

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}