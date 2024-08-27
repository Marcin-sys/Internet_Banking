package pl.mirocha.marcin.internet.banking.services;

public interface ITransferService {
    void transferMoney(int id, double amountOfMoneyToTransfer, String accountNumberForTransfer);
}