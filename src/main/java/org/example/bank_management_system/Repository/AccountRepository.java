package org.example.bank_management_system.Repository;

import org.example.bank_management_system.Model.Account;
import org.example.bank_management_system.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findByAccountNumber(String accountNumber);

    boolean existsByAccountNumber(String accountNumber);

    List<Account> findAllByCustomer(Customer customer);

    Account findByIdAndCustomer(Integer id, Customer customer);

    boolean existsByIdAndCustomer(Integer id, Customer customer);
}