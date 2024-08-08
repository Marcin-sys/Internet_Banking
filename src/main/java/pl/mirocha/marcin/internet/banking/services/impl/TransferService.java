package pl.mirocha.marcin.internet.banking.services.impl;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mirocha.marcin.internet.banking.dao.IAccountDAO;
import pl.mirocha.marcin.internet.banking.dao.ITransferDAO;
import pl.mirocha.marcin.internet.banking.model.Account;
import pl.mirocha.marcin.internet.banking.model.Transfer;
import pl.mirocha.marcin.internet.banking.services.ITransferService;

import java.util.Optional;

@Service
public class TransferService implements ITransferService {

//    @Autowired
//    HttpSession httpSession;

    private final IAccountDAO accountDAO;
    private final ITransferDAO transferDAO;
//    private final IUserDAO userDAO;

    public TransferService(IAccountDAO accountDAO, ITransferDAO transferDAO) {
        this.accountDAO = accountDAO;
        this.transferDAO = transferDAO;
    }

    @Override
    public void transferMoney(int id, double amountOfMoneyToTransfer, String accountNumberForTransfer) {
        Optional<Account> accountOptional = accountDAO.getById(id);
        Optional<Account> accountThatGettingTransferOptional = this.accountDAO.getByAccountNumber(accountNumberForTransfer);

        if (accountOptional.isPresent() && accountThatGettingTransferOptional.isPresent() ){
            System.out.println("here");
            Account account = accountOptional.get();
            account.setAccountBalance(account.getAccountBalance() - amountOfMoneyToTransfer);
            this.accountDAO.persist(account);
            System.out.println("here2");
            Account accountThatGettingTransfer = accountThatGettingTransferOptional.get();
            accountThatGettingTransfer
                    .setAccountBalance(accountThatGettingTransfer.getAccountBalance() + amountOfMoneyToTransfer);
            this.accountDAO.persist(accountThatGettingTransfer);
            System.out.println("here3");
            Transfer transfer = new Transfer(amountOfMoneyToTransfer,account.getUser(),account,accountThatGettingTransfer);
            this.transferDAO.persist(transfer);
            System.out.println("here4");
        }
    }
}
