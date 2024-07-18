package pl.mirocha.marcin.internet.banking.validators;

import pl.mirocha.marcin.internet.banking.exceptions.AccountValidationException;
import pl.mirocha.marcin.internet.banking.model.Account;

public class AccountValidator {

    public static void validateCurrency(String currency) {
        switch (currency.toLowerCase()) {
            case "euro", "dollar", "zloty" -> {}
            default -> throw new AccountValidationException("Incorrect Currency");
        }
    }

    public static void validateAccount(Account account){
        validateCurrency(account.getAccountCurrency());
    }
}
