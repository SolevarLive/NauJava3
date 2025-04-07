package ru.davyd.NauJava.entities;

import jakarta.persistence.*;
import java.util.UUID;
import java.util.List;

/**
 * Класс, представляющий тег
 */
@Entity
@Table(name = "tags")
public class Tag {
    /**
     * Уникальный идентификатор тега
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Название тега
     */
    private String name;

    /**
     * Список задач, помеченных этим тегом
     */
    @ManyToMany(mappedBy = "tags")
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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
