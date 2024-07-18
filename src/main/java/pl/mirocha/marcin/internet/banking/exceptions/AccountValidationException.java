package pl.mirocha.marcin.internet.banking.exceptions;

public class AccountValidationException extends RuntimeException {

    public AccountValidationException(String message) {
        super(message);
    }
}
