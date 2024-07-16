package pl.mirocha.marcin.internet.banking.exceptions;

public class UserAlreadyExistException extends RuntimeException {

    public UserAlreadyExistException(String message) {
        super(message);
    }
}