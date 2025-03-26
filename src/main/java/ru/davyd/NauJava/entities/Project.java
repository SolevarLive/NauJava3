package ru.davyd.NauJava.entities;

import jakarta.persistence.*;
import java.util.UUID;
import java.util.List;

/**
 * Класс, представляющий проект
 */
@Entity
@Table(name = "projects")
public class Project {
    /**
     * Уникальный идентификатор проекта
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Название проекта
     */
    private String name;

    /**
     * Описание проекта
     */
    private String description;

    /**
     * Список задач, входящих в проект
     */
    @OneToMany(mappedBy = "project")
    private List<Task> tasks;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
