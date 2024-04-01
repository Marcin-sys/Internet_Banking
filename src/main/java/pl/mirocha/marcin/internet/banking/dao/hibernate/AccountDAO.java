package pl.mirocha.marcin.internet.banking.dao.hibernate;

import jakarta.persistence.NoResultException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import pl.mirocha.marcin.internet.banking.dao.IAccountDAO;
import pl.mirocha.marcin.internet.banking.model.Account;
import pl.mirocha.marcin.internet.banking.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class AccountDAO implements IAccountDAO {
    private final SessionFactory sessionFactory;

    private final String GET_BY_ID_HQL = "FROM pl.mirocha.marcin.internet.banking.model.Account WHERE id = :id";
    private final String GET_ALL_HQL = "FROM pl.mirocha.marcin.internet.banking.model.Account";
    private final String GET_ACCOUNTS_BY_USER_ID_HQL = "FROM pl.mirocha.marcin.internet.banking.model.User " +
            "WHERE id = :id";
    private final String GET_ACCOUNTS_BY_ACCOUNT_NUMBER_HQL = "FROM pl.mirocha.marcin.internet.banking.model.Account " +
            "WHERE accountNumber = :accountNumber";

    public AccountDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Account> getById(int id) {
        Session session = this.sessionFactory.openSession();
        Query<Account> query = session.createQuery(GET_BY_ID_HQL, Account.class);
        query.setParameter("id", id);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Account> getAll() {
        Session session = this.sessionFactory.openSession();
        Query<Account> query = session.createQuery(GET_ALL_HQL, Account.class);
        List<Account> result = query.getResultList();
        session.close();
        return result;
    }

    @Override
    public Optional<Account> getByAccountNumber(long accountNumber) {
        Session session = this.sessionFactory.openSession();
        Query<Account> query = session.createQuery(GET_ACCOUNTS_BY_ACCOUNT_NUMBER_HQL, Account.class);
        query.setParameter("accountNumber", accountNumber);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Account> getByUserId(int userId) {
        Session session = this.sessionFactory.openSession();
        Query<User> query = session.createQuery(GET_ACCOUNTS_BY_USER_ID_HQL, User.class);
        query.setParameter("id", userId);
        try {
            User user = query.getSingleResult();
            return new ArrayList<>(user.getAccounts());
        } catch (NoResultException e) {
            return Collections.emptyList();
        } finally {
            session.close();
        }
    }

    @Override
    public void persist(Account account) {
        Session session = this.sessionFactory.openSession();
        try {
            session.refresh(account.getUser());
            account.getUser().getAccounts().add(account);
            session.beginTransaction();
            session.merge(account);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(int id) {
        Session session = this.sessionFactory.openSession();
        try {
            session.getTransaction();

            session.remove(new Account(id));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
}
