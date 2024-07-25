package pl.mirocha.marcin.internet.banking.services.impl;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mirocha.marcin.internet.banking.dao.IAccountDAO;
import pl.mirocha.marcin.internet.banking.dao.IUserDAO;
import pl.mirocha.marcin.internet.banking.model.Account;
import pl.mirocha.marcin.internet.banking.model.User;
import pl.mirocha.marcin.internet.banking.services.IAccountService;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements IAccountService {

    @Autowired
    HttpSession httpSession;

    private final IAccountDAO accountDAO;
    private final IUserDAO userDAO;

    public AccountService(IAccountDAO accountDAO, IUserDAO userDAO) {
        this.accountDAO = accountDAO;
        this.userDAO = userDAO;
    }

    @Override
    public void persist(Account account) {
        account.setUser((User) this.httpSession.getAttribute("user"));
        this.accountDAO.persist(account);
    }

    @Override
    public Optional<Account> getById(int id) {
        return this.accountDAO.getById(id);
    }

    @Override
    public void update(int id, Account account) {
        account.setId(id);
        this.accountDAO.persist(account); //TODO  update ?
    }

    @Override
    public List<Account> getAll() {
        return this.accountDAO.getAll();
    }

    @Override
    public List<Account> getByCurrentUser() {
        User user = (User) this.httpSession.getAttribute("user");
        return this.accountDAO.getByUserId(user.getId());
    }
}
