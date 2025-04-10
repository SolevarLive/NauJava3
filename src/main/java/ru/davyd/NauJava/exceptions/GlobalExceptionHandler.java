package ru.davyd.NauJava.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Класс, реализующий глобальную обработку исключений в приложении
 * Обрабатывает исключения типа ConstraintViolationException и возвращает
 * стандартный ответ с HTTP статусом BAD_REQUEST
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обработчик исключений ConstraintViolationException
     * Возвращает ответ с HTTP статусом BAD_REQUEST и сообщением об ошибке
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка валидации: " + e.getMessage());
    }

    /**
     * Обработчик всех исключений (Exception).
     * Возвращает ответ с HTTP статусом INTERNAL_SERVER_ERROR и сообщением о непредвиденной ошибке.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Непредвиденная ошибка: " + e.getMessage());
    }
}

