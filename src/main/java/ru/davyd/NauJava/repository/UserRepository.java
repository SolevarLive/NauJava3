package ru.davyd.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.davyd.NauJava.entities.User;

import java.util.UUID;
import java.util.Optional;

/**
 * Интерфейс репозитория для пользователей
 */
@RepositoryRestResource(path = "users", collectionResourceRel = "users")
public interface UserRepository extends CrudRepository<User, UUID> {
    /**
     * Поиск пользователя по Имени
     */
    Optional<User> findByUsername(String username);

    /**
     * Поиск пользователя по email
     */
    Optional<User> findByEmail(String email);
}
