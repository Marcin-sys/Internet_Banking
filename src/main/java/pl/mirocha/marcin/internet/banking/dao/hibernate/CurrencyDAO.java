package pl.mirocha.marcin.internet.banking.dao.hibernate;

import jakarta.persistence.NoResultException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import pl.mirocha.marcin.internet.banking.dao.ICurrencyDAO;
import pl.mirocha.marcin.internet.banking.model.Currency;

import java.util.List;
import java.util.Optional;

@Repository
public class CurrencyDAO implements ICurrencyDAO {

    private final SessionFactory sessionFactory;

    private final String GET_BY_ID_HQL = "FROM pl.mirocha.marcin.internet.banking.model.Currency WHERE id = :id";
    private final String GET_ALL_HQL = "FROM pl.mirocha.marcin.internet.banking.model.Currency";
    private final String GET_CURRENCY_BY_CURRENCY_HELD_AND_CURRENCY_EXCHANGE =
            "FROM pl.mirocha.marcin.internet.banking.model.Currency " +
            "WHERE currencyHeld = :currencyHeld AND currencyExchange = :currencyExchange";

    public CurrencyDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Currency> getById(int id) {
        Session session = this.sessionFactory.openSession();
        Query<Currency> query = session.createQuery(GET_BY_ID_HQL, Currency.class);
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
    public List<Currency> getAll(){
        Session session = this.sessionFactory.openSession();
        Query<Currency> query = session.createQuery(GET_ALL_HQL, Currency.class);
        List<Currency> result = query.getResultList();
        session.close();
        return result;
    }
    @Override
    public Optional<Currency> getByCurrencyHeldAndCurrencyExchange(String currencyHeld, String currencyExchange) {
        Session session = this.sessionFactory.openSession();
        Query<Currency> query = session.createQuery(GET_CURRENCY_BY_CURRENCY_HELD_AND_CURRENCY_EXCHANGE, Currency.class);
        query.setParameter("currencyHeld", currencyHeld);
        query.setParameter("currencyExchange", currencyExchange);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            session.close();
        }
    }

    @Override
    public void persist(Currency currency) {
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.merge(currency);
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

            session.remove(new Currency(id));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
}
