package ru.davyd.NauJava.entities;

import jakarta.persistence.*;
import java.util.UUID;

/**
 * Класс, представляющий пользователя
 */
@Entity
@Table(name = "users")
public class User {
    /**
     * Уникальный идентификатор пользователя
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Имя пользователя
     */
    private String username;

    /**
     * Электронная почта пользователя
     */
    private String email;

    /**
     * Пароль пользователя
     */
    private String password;


    /**
     * Флаг, указывающий, является ли пользователь администратором
     * По умолчанию устанавливается в false, если не указано иное
     */
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isAdmin;

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}