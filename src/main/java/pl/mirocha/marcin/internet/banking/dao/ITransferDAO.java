package pl.mirocha.marcin.internet.banking.dao;

import pl.mirocha.marcin.internet.banking.model.Transfer;

import java.util.List;
import java.util.Optional;

public interface ITransferDAO {
    Optional<Transfer> getById(int id);

    List<Transfer> getAll();

    default void persist(Transfer transfer) {
        throw new UnsupportedOperationException();
    }

    void delete(int id);

    List<Transfer> getByUserId(int userId);

}
