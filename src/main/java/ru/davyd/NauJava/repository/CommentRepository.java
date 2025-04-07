package ru.davyd.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.davyd.NauJava.entities.Comment;

import java.util.UUID;

/**
 * Интерфейс репозитория для комментариев
 */
@RepositoryRestResource(path = "comments", collectionResourceRel = "comments")
public interface CommentRepository extends CrudRepository<Comment, UUID> {
}
