package org.example.bank_management_system.Service;

import lombok.RequiredArgsConstructor;
import org.example.bank_management_system.Exception.ApiException;
import org.example.bank_management_system.Model.Account;
import org.example.bank_management_system.Model.Customer;
import org.example.bank_management_system.Model.User;
import org.example.bank_management_system.Repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerService customerService;

    @Transactional
    public Account createAccount(String accountNumber, User user) {
        Customer customer = customerService.getMyCustomer(user);

        if (accountRepository.existsByAccountNumber(accountNumber)) {
            throw new ApiException("Account number already exists");
        }

        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setBalance(BigDecimal.ZERO);
        account.setActive(false);
        account.setCustomer(customer);

        return accountRepository.save(account);
    }

    public Account getAccountById(Integer id) {
        Account account = accountRepository.findById(id).orElse(null);

        if (account == null) {
            throw new ApiException("Account not found");
        }

        return account;
    }

    public Account getMyAccount(Integer id, User user) {
        Customer customer = customerService.getMyCustomer(user);
        Account account = accountRepository.findByIdAndCustomer(id, customer);

        if (account == null) {
            throw new ApiException("Account not found for this customer");
        }

        return account;
    }

    public List<Account> getMyAccounts(User user) {
        Customer customer = customerService.getMyCustomer(user);
        return accountRepository.findAllByCustomer(customer);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Transactional
    public void activateAccount(Integer id) {
        Account account = getAccountById(id);

        if (account.isActive()) {
            throw new ApiException("Account is already active");
        }

        account.setActive(true);
        accountRepository.save(account);
    }

    @Transactional
    public void blockAccount(Integer id) {
        Account account = getAccountById(id);

        if (!account.isActive()) {
            throw new ApiException("Account is already blocked or inactive");
        }

        account.setActive(false);
        accountRepository.save(account);
    }

    @Transactional
    public void updateAccount(Integer id, Account updatedAccount) {
        Account account = getAccountById(id);

        if (updatedAccount.getAccountNumber() != null
                && !updatedAccount.getAccountNumber().equals(account.getAccountNumber())) {

            if (accountRepository.existsByAccountNumber(updatedAccount.getAccountNumber())) {
                throw new ApiException("Account number already exists");
            }

            account.setAccountNumber(updatedAccount.getAccountNumber());
        }

        accountRepository.save(account);
    }

    @Transactional
    public void deleteAccount(Integer id) {
        Account account = getAccountById(id);
        accountRepository.delete(account);
    }
}