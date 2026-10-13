package org.example.bank_management_system.Repository;

import org.example.bank_management_system.Model.Customer;
import org.example.bank_management_system.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findByUser(User user);

    boolean existsByUser(User user);

    List<Customer> findAllByUser(User user);
}