package pl.mirocha.marcin.internet.banking.dao;

import pl.mirocha.marcin.internet.banking.model.Currency;
import java.util.List;
import java.util.Optional;

public interface ICurrencyDAO {

    Optional<Currency> getById(int id);

    List<Currency> getAll();

    Optional<Currency> getByCurrencyHeldAndCurrencyExchange
            (String currencyHeld, String currencyExchange);

    default void persist(Currency currency) {
        throw new UnsupportedOperationException();
    }

    void delete(int id);
}
