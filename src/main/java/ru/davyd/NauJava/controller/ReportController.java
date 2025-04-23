package ru.davyd.NauJava.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.davyd.NauJava.entities.enums.ReportStatus;
import ru.davyd.NauJava.service.ReportService;

import java.util.concurrent.CompletableFuture;

/**
 * REST-контроллер для работы с отчётами
 * Позволяет создавать отчёты и получать их содержимое
 */
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * Создаёт новый отчёт и запускает асинхронное формирование
     * Возвращает id отчёта сразу, не дожидаясь окончания формирования
     */
    @PostMapping("/create")
    public ResponseEntity<Long> createReport(){
        Long reportId = reportService.createReport();
        CompletableFuture<Void> future = reportService.generateReportAsync(reportId);
        return ResponseEntity.ok(reportId);
    }

    /**
     * Получение содержимого отчёта по id
     * Если отчёт в статусе CREATED или ERROR, возвращает соответствующее сообщение
     */
    @GetMapping("/{id}")
    public ResponseEntity<String> getReport(@PathVariable Long id) {
        ReportStatus status = reportService.getReportStatus(id);
        if (status == null){
            return ResponseEntity.notFound().build();
        }
        switch (status){
            case CREATED:
                return ResponseEntity.ok("Отчёт еще формируется, попробуйте позже");
            case ERROR:
                return ResponseEntity.ok("Произошла ошибка при формировании отчёта.");
            case COMPLETED:
                String content = reportService.getReportContent(id);
                return ResponseEntity.ok().header("Content-Type", "text/html; charset=UTF-8").body(content);
            default:
                return ResponseEntity.ok("Неизвестный статус отчёта.");
        }
    }
}
