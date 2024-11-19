package ru.froleod.FinanceTrackerApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.froleod.FinanceTrackerApp.model.BankAccount;
import ru.froleod.FinanceTrackerApp.repo.BankAccountRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    public BankAccount createBankAccount(BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }

    public Optional<BankAccount> getBankAccountById(Long id) {
        return bankAccountRepository.findById(id);
    }

    public List<BankAccount> getAllBankAccounts() {
        return bankAccountRepository.findAll();
    }

    public BankAccount updateBankAccount(BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }

    public void deleteBankAccount(Long id) {
        bankAccountRepository.deleteById(id);
    }

    public BankAccount getBankAccountByUserUsername(String username) {
        return bankAccountRepository.findBankAccountByUserUsername(username);
    }
}