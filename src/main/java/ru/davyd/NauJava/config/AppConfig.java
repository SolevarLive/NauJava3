package ru.davyd.NauJava.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурационный класс приложения
 * отвечает за инициализацию и настройку основных параметров приложения
 * использует внешние свойства для конфигурации
 */
@Configuration
public class AppConfig {
    /**
     * Имя приложения, загружается из внешних свойств
     */
    @Value("${app.name}")
    private String appName;

    /**
     * Версия приложения, загружается из внешних свойств
     */
    @Value("${app.version}")
    private String appVersion;

    /**
     * Метод инициализации, вызывается после создания бина
     * выводит информацию о запуске приложения
     */
    @PostConstruct
    public void init() {
        System.out.println("Приложение запущено:");
        System.out.println("Имя: " + appName);
        System.out.println("Версия: " + appVersion);
    }
}