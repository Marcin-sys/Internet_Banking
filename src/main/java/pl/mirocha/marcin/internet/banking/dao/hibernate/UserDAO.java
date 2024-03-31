package pl.mirocha.marcin.internet.banking.dao.hibernate;

import jakarta.persistence.NoResultException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import pl.mirocha.marcin.internet.banking.dao.IUserDAO;
import pl.mirocha.marcin.internet.banking.model.User;

import java.util.List;
import java.util.Optional;
@Repository
public class UserDAO implements IUserDAO {
    private final SessionFactory sessionFactory;
    private final String GET_BY_ID_HQL = "FROM pl.mirocha.marcin.internet.banking.model.User WHERE id = :id";
    private final String GET_BY_LOGIN_HQL = "FROM pl.mirocha.marcin.internet.banking.model.User WHERE login = :login";
    private final String GET_ALL_HQL = "FROM pl.mirocha.marcin.internet.banking.model.User";

    public UserDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<User> getById(int id) {
        Session session = this.sessionFactory.openSession();
        Query<User> query = session.createQuery(GET_BY_ID_HQL, User.class);
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
    public Optional<User> getByLogin(String login) {
        Session session = this.sessionFactory.openSession();
        Query<User> query = session.createQuery(GET_BY_LOGIN_HQL, User.class);
        query.setParameter("login", login);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAll() {
        Session session = this.sessionFactory.openSession();
        Query<User> query = session.createQuery(GET_ALL_HQL, User.class);
        List<User> result = query.getResultList();
        session.close();
        return result;
    }

    @Override
    public void delete(int id) {
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.remove(new User(id));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void persist(User user) {
        Session session = this.sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.merge(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
}
