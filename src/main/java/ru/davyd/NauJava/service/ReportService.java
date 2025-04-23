package ru.davyd.NauJava.service;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import ru.davyd.NauJava.entities.Report;
import ru.davyd.NauJava.entities.Task;
import ru.davyd.NauJava.entities.enums.ReportStatus;
import ru.davyd.NauJava.repository.ReportRepository;
import ru.davyd.NauJava.repository.TaskRepository;
import ru.davyd.NauJava.repository.UserRepository;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TemplateEngine templateEngine;


    public ReportService(ReportRepository reportRepository, UserRepository userRepository,
                         TaskRepository taskRepository, TemplateEngine templateEngine) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.templateEngine = templateEngine;
    }

    /**
     * Создает пустой отчет со статусом CREATED и возвращает его id
     */
    public Long createReport(){
        Report report = new Report();
        report.setStatus(ReportStatus.CREATED);
        report.setContent("");
        reportRepository.save(report);
        return report.getId();
    }

    /**
     * Получение содержимого отчёта по id
     */
    public String getReportContent(Long id){
        return reportRepository.findById(id)
                .map(Report::getContent)
                .orElse("Отчёт не найден");
    }

    /**
     * Получение статуса отчёта по id
     */
    public ReportStatus getReportStatus(Long id){
        return reportRepository.findById(id)
                .map(Report::getStatus)
                .orElse(null);
    }

    /**
     * Асинхронное формирование отчёта с использованием CompletableFuture и Thymeleaf
     */
    public CompletableFuture<Void> generateReportAsync(Long reportId) {
        return CompletableFuture.runAsync(() -> {
            Report report = reportRepository.findById(reportId)
                    .orElseThrow(() -> new RuntimeException("Отчёт не найден"));

            long totalStart = System.currentTimeMillis();

            try {
                long[] userCountTime = new long[1];
                long[] tasksTime = new long[1];

                CompletableFuture<Long> userCountFuture = CompletableFuture.supplyAsync(() -> {
                    long start = System.currentTimeMillis();
                    long count = userRepository.count();
                    userCountTime[0] = System.currentTimeMillis() - start;
                    return count;
                });

                CompletableFuture<List<Task>> tasksFuture = CompletableFuture.supplyAsync(() -> {
                    long start = System.currentTimeMillis();
                    List<Task> tasks = (List<Task>) taskRepository.findAll();
                    tasksTime[0] = System.currentTimeMillis() - start;
                    return tasks;
                });

                Long userCount = userCountFuture.join();
                List<Task> tasks = tasksFuture.join();

                long totalElapsed = System.currentTimeMillis() - totalStart;

                Context context = new Context();
                context.setVariable("userCount", userCount);
                context.setVariable("tasks", tasks);
                context.setVariable("userCountTime", userCountTime[0]);
                context.setVariable("tasksTime", tasksTime[0]);
                context.setVariable("totalTime", totalElapsed);

                String htmlContent = templateEngine.process("report", context);

                report.setContent(htmlContent);
                report.setStatus(ReportStatus.COMPLETED);
                reportRepository.save(report);

            } catch (Exception e) {
                report.setStatus(ReportStatus.ERROR);
                report.setContent("Ошибка при формировании отчёта: " + e.getMessage());
                reportRepository.save(report);
                throw new RuntimeException(e);
            }
        });
    }
}

