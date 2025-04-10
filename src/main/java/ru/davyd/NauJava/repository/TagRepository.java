package ru.davyd.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.davyd.NauJava.entities.Tag;

import java.util.UUID;

/**
 * Интерфейс репозитория для тегов
 */
@RepositoryRestResource(path = "tags", collectionResourceRel = "tags")
public interface TagRepository extends CrudRepository<Tag, UUID> {
}
