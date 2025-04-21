package ru.davyd.NauJava.criteria;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import ru.davyd.NauJava.entities.Task;
import ru.davyd.NauJava.entities.User;
import ru.davyd.NauJava.entities.enums.TaskPriority;

import java.util.List;

/**
 * Реализация интерфейса для работы с задачами по критериям
 */
@Repository
public class TaskCriteriaRepositoryImpl implements TaskCriteriaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Task> findByTitleAndPriorityCriteria(String title, TaskPriority priority) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> cq = cb.createQuery(Task.class);
        Root<Task> taskRoot = cq.from(Task.class);

        Predicate titlePredicate = cb.like(cb.lower(taskRoot.get("title")), "%" + title.toLowerCase() + "%");
        Predicate priorityPredicate = cb.equal(taskRoot.get("priority"), priority);

        cq.where(cb.and(titlePredicate, priorityPredicate));
        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public List<Task> findTasksByUsernameCriteria(String username) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> cq = cb.createQuery(Task.class);
        Root<Task> taskRoot = cq.from(Task.class);

        Join<Task, User> userJoin = taskRoot.join("user");

        Predicate usernamePredicate = cb.equal(userJoin.get("username"), username);
        cq.where(usernamePredicate);

        return entityManager.createQuery(cq).getResultList();
    }
}
