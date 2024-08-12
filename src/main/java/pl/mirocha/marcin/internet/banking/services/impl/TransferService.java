package pl.mirocha.marcin.internet.banking.services.impl;

import org.springframework.stereotype.Service;
import pl.mirocha.marcin.internet.banking.dao.IAccountDAO;
import pl.mirocha.marcin.internet.banking.dao.ITransferDAO;
import pl.mirocha.marcin.internet.banking.model.Account;
import pl.mirocha.marcin.internet.banking.model.Transfer;
import pl.mirocha.marcin.internet.banking.services.ITransferService;

import java.util.Optional;

@Service
public class TransferService implements ITransferService {


    private final IAccountDAO accountDAO;
    private final ITransferDAO transferDAO;

    public TransferService(IAccountDAO accountDAO, ITransferDAO transferDAO) {
        this.accountDAO = accountDAO;
        this.transferDAO = transferDAO;
    }

    @Override
    public void transferMoney(int id, double amountOfMoneyToTransfer, String accountNumberForTransfer) {
        Optional<Account> accountOptional = accountDAO.getById(id);
        Optional<Account> accountThatGettingTransferOptional = this.accountDAO.getByAccountNumber(accountNumberForTransfer);

        if (accountOptional.isPresent() && accountThatGettingTransferOptional.isPresent()) {
            Account account = accountOptional.get();
            account.setAccountBalance(account.getAccountBalance() - amountOfMoneyToTransfer);
            this.accountDAO.persist(account);
            Account accountThatGettingTransfer = accountThatGettingTransferOptional.get();
            accountThatGettingTransfer
                    .setAccountBalance(accountThatGettingTransfer.getAccountBalance() + amountOfMoneyToTransfer);
            this.accountDAO.persist(accountThatGettingTransfer);
            Transfer transfer = new Transfer(amountOfMoneyToTransfer, account.getUser(), account, accountThatGettingTransfer);
            this.transferDAO.persist(transfer);
        }
    }
}
