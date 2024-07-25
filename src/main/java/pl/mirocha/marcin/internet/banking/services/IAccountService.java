package pl.mirocha.marcin.internet.banking.services;

import pl.mirocha.marcin.internet.banking.model.Account;

import java.util.List;
import java.util.Optional;

public interface IAccountService {

    void persist(Account account);

    Optional<Account> getById(int id);

    void update(int id, Account account);

    List<Account> getAll();

    List<Account> getByCurrentUser();


}
