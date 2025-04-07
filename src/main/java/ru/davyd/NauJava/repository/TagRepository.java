package ru.davyd.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.davyd.NauJava.entities.Tag;

import java.util.UUID;

/**
 * Интерфейс репозитория для тегов
 */
public interface TagRepository extends CrudRepository<Tag, UUID> {
}
