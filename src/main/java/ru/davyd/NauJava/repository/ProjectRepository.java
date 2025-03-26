package ru.davyd.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import ru.davyd.NauJava.entities.Project;

import java.util.UUID;

/**
 * Интерфейс репозитория для проектов
 */
public interface ProjectRepository extends CrudRepository<Project, UUID> {
}
