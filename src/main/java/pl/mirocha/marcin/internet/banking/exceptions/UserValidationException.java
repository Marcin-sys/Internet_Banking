package pl.mirocha.marcin.internet.banking.exceptions;

public class UserValidationException extends RuntimeException {
    public UserValidationException(String message) {
        super(message);
    }
}
