package ru.davyd.NauJava.entities;

import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;

/**
 * Класс, представляющий комментарий к задаче
 */
@Entity
@Table(name = "comments")
public class Comment {
    /**
     * Уникальный идентификатор комментария
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Текст комментария
     */
    private String text;

    /**
     * Дата создания комментария
     */
    private Date createdAt;

    /**
     * Задача, к которой относится комментарий
     */
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    public UUID getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
