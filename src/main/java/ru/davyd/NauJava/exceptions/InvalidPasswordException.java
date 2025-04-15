package ru.davyd.NauJava.exceptions;

/**
 * Исключение, выбрасываемое при некорректном пароле (например, слишком коротком)
 */
public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
