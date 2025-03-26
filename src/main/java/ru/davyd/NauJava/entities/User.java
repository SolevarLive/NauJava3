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