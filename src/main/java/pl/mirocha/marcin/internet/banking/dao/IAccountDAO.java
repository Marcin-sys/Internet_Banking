package pl.mirocha.marcin.internet.banking.dao;

import pl.mirocha.marcin.internet.banking.model.Account;

import java.util.List;
import java.util.Optional;

public interface IAccountDAO {
    Optional<Account> getById(int id);

    List<Account> getAll();

    Optional<Account> getByAccountNumber(String accountNumber);

    List<Account> getByUserId(int userId);

    default void persist(Account account) {
        throw new UnsupportedOperationException();
    }

    void delete(int id);
}
