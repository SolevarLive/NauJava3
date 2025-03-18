package ru.davyd.NauJava.entity;

/**
 * Перечисление возможных статусов задачи
 */
public enum TaskStatus {
    OPEN("Открыта"),
    IN_PROGRESS("В процессе"),
    COMPLETED("Завершена");

    private final String description;

    TaskStatus(String description) {
        this.description = description;
    }

    /**
     * Получение описания статуса.
     *
     * @return строка с описанием статуса
     */
    public String getDescription() {
        return description;
    }
}