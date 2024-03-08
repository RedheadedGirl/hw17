package ru.sbrf.exceptions;

public class DbException extends RuntimeException {

    public DbException(String message, Exception exception) {
        super(message, exception);
    }

}
