package ru.davyd.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.davyd.NauJava.entities.Comment;

import java.util.UUID;

/**
 * Интерфейс репозитория для комментариев
 */
public interface CommentRepository extends CrudRepository<Comment, UUID> {
}
