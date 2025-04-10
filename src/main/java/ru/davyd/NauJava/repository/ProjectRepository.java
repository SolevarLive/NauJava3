package ru.davyd.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.davyd.NauJava.entities.Project;

import java.util.UUID;

/**
 * Интерфейс репозитория для проектов
 */
@RepositoryRestResource(path = "projects", collectionResourceRel = "projects")
public interface ProjectRepository extends CrudRepository<Project, UUID> {
}
