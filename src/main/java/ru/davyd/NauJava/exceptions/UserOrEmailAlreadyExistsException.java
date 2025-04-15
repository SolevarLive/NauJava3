package ru.davyd.NauJava.exceptions;

/**
 * Исключение, выбрасываемое при попытке создать пользователя с уже существующим именем пользователя или email
 */
public class UserOrEmailAlreadyExistsException extends RuntimeException {
    public UserOrEmailAlreadyExistsException(String message) {
        super(message);
    }
}