package org.example.bank_management_system.Service;

import lombok.RequiredArgsConstructor;
import org.example.bank_management_system.Exception.ApiException;
import org.example.bank_management_system.Model.Customer;
import org.example.bank_management_system.Model.User;
import org.example.bank_management_system.Repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer getMyCustomer(User user) {
        Customer customer = customerRepository.findByUser(user);

        if (customer == null) {
            throw new ApiException("Customer profile not found");
        }

        return customer;
    }

    public Customer getCustomerById(Integer id) {
        Customer customer = customerRepository.findById(id).orElse(null);

        if (customer == null) {
            throw new ApiException("Customer not found");
        }

        return customer;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}