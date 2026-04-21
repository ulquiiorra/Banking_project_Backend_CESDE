package application.service;

import application.domain.SavingsAccount;
import application.domain.enums.AccountState;
import application.service.outputs.SavingsAccountService;
import application.service.ports.SavingsAccountRepositoryPort;

import java.util.Optional;

public class SavingsAccountServiceImpl implements SavingsAccountService {
    private final SavingsAccountRepositoryPort repository;

    public SavingsAccountServiceImpl(SavingsAccountRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public void withdraw(String accountNumber, double amount) {
        Optional<SavingsAccount> accountOpt = repository.findById(accountNumber);

        if (accountOpt.isEmpty()) {
            System.out.println("Error: La cuenta " + accountNumber + " no existe.");
            return;
        }

        SavingsAccount account = accountOpt.get();

        if (account.getAccountState() != AccountState.ACTIVE) {
            System.out.println("Error: La cuenta no está activa.");
            return;
        }

        if (amount < 0) {
            System.out.println("Error: El monto a retirar debe ser mayor a cero.");
            return;
        }

        if (account.getBalance() < amount) {
            System.out.println("Error: Saldo insuficiente. Tu saldo es: $" + account.getBalance());
            return;
        }

        double newBalance = account.getBalance() - amount;
        account.setBalance(newBalance);

        
    }

}
