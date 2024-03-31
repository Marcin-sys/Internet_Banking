package pl.mirocha.marcin.internet.banking.dao;

import pl.mirocha.marcin.internet.banking.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserDAO {
    Optional<User> getById(int id);

    Optional<User> getByLogin(String login);

    List<User> getAll();

    void delete(int id);

    default void persist(User user) {
        throw new UnsupportedOperationException();
    }

}
