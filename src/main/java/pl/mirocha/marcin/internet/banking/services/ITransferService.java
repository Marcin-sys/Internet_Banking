package pl.mirocha.marcin.internet.banking.services;

import pl.mirocha.marcin.internet.banking.model.Account;

public interface ITransferService {
    //TODO make interface here

    void transferMoney(int id, double amountOfMoneyToTransfer, String accountNumberForTransfer);

}