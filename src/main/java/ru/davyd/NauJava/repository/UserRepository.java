package ru.davyd.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.davyd.NauJava.entities.User;

import java.util.UUID;
import java.util.Optional;

/**
 * Интерфейс репозитория для пользователей
 */
public interface UserRepository extends CrudRepository<User, UUID> {
    Optional<User> findByUsername(String username);
}
