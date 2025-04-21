package ru.davyd.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.davyd.NauJava.entities.Report;

/**
 * Интерфейс репозитория для отчёта
 */
@RepositoryRestResource(path = "reports", collectionResourceRel = "reports")
public interface ReportRepository extends CrudRepository<Report,Long> {
}
