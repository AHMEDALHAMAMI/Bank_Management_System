package org.example.bank_management_system.Service;

import lombok.RequiredArgsConstructor;
import org.example.bank_management_system.Exception.ApiException;
import org.example.bank_management_system.Model.Account;
import org.example.bank_management_system.Repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountRepository accountRepository;

    @Transactional
    public void deposit(Integer accountId, BigDecimal amount) {
        validateAmount(amount);

        Account account = getAccount(accountId);
        validateActive(account);

        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
    }

    @Transactional
    public void withdraw(Integer accountId, BigDecimal amount) {
        validateAmount(amount);

        Account account = getAccount(accountId);
        validateActive(account);

        if (account.getBalance().compareTo(amount) < 0) {
            throw new ApiException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
    }

    @Transactional
    public void transfer(String sourceNumber, String destinationNumber, BigDecimal amount) {
        validateAmount(amount);

        if (sourceNumber.equals(destinationNumber)) {
            throw new ApiException("Source and destination accounts must be different");
        }

        Account source = getAccountByNumber(sourceNumber);
        Account destination = getAccountByNumber(destinationNumber);

        validateActive(source);
        validateActive(destination);

        if (source.getBalance().compareTo(amount) < 0) {
            throw new ApiException("Insufficient balance");
        }

        source.setBalance(source.getBalance().subtract(amount));
        destination.setBalance(destination.getBalance().add(amount));

        accountRepository.save(source);
        accountRepository.save(destination);
    }

    private Account getAccount(Integer id) {
        Account account = accountRepository.findById(id).orElse(null);

        if (account == null) {
            throw new ApiException("Account not found");
        }

        return account;
    }

    private Account getAccountByNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber);

        if (account == null) {
            throw new ApiException("Account not found");
        }

        return account;
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ApiException("Amount must be greater than zero");
        }
    }

    private void validateActive(Account account) {
        if (!account.isActive()) {
            throw new ApiException("Account is inactive or blocked");
        }
    }
}