package ru.davyd.NauJava.entities;

import jakarta.persistence.*;
import ru.davyd.NauJava.entities.enums.ReportStatus;

/**
 * Сущность, представляющая отчёт
 * Содержит информацию о статусе отчёта и его содержимом
 */
@Entity
@Table(name="report")
public class Report {

    /**
     * Уникальный идентификатор отчёта
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Статус отчёта
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status;

    /**
     * Содержимое отчёта в виде текста
     */
    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    /**
     * Конструктор по умолчанию
     */
    public Report (){}

    /**
     * Конструктор с параметрами
     * @param status статус отчёта
     * @param content содержимое отчёта
     */
    public Report(ReportStatus status, String content) {
        this.status = status;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
