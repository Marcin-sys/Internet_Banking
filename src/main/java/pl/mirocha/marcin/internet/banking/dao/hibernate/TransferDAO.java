package pl.mirocha.marcin.internet.banking.dao.hibernate;

import jakarta.persistence.NoResultException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import pl.mirocha.marcin.internet.banking.dao.ITransferDAO;
import pl.mirocha.marcin.internet.banking.model.Transfer;
import pl.mirocha.marcin.internet.banking.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class TransferDAO implements ITransferDAO {
    private final SessionFactory sessionFactory;
    private final String GET_BY_ID_HQL = "FROM pl.mirocha.marcin.internet.banking.model.Transfer WHERE id = :id";
    private final String GET_ALL_HQL = "FROM pl.mirocha.marcin.internet.banking.model.Transfer";
    private final String GET_TRANSFERS_BY_USER_ID_HQL = "FROM pl.mirocha.marcin.internet.banking.model.User WHERE id = :id";


    public TransferDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Transfer> getById(int id) {
        Session session = this.sessionFactory.openSession();
        Query<Transfer> query = session.createQuery(GET_BY_ID_HQL, Transfer.class);
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
    public List<Transfer> getAll() {
        Session session = this.sessionFactory.openSession();
        Query<Transfer> query = session.createQuery(GET_ALL_HQL, Transfer.class);
        List<Transfer> result = query.getResultList();
        session.close();
        return result;
    }

    @Override
    public void persist(Transfer transfer) {
        Session session = this.sessionFactory.openSession();
        try {
            session.refresh(transfer.getUser());
            transfer.getUser().getTransfers().add(transfer);
            session.beginTransaction();
            session.merge(transfer);
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

            session.remove(new Transfer(id));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Transfer> getByUserId(int userId) {
        Session session = this.sessionFactory.openSession();
        Query<User> query = session.createQuery(GET_TRANSFERS_BY_USER_ID_HQL, User.class);
        query.setParameter("id", userId);
        try {
            User user = query.getSingleResult();
            return new ArrayList<>(user.getTransfers());
        } catch (NoResultException e) {
            return Collections.emptyList();
        } finally {
            session.close();
        }
    }
}
