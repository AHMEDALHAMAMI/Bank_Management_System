package org.example.bank_management_system.Service;

import lombok.RequiredArgsConstructor;
import org.example.bank_management_system.DTO.RegisterCustomerDTO;
import org.example.bank_management_system.DTO.RegisterEmployeeDTO;
import org.example.bank_management_system.Exception.ApiException;
import org.example.bank_management_system.Model.Customer;
import org.example.bank_management_system.Model.Employee;
import org.example.bank_management_system.Model.Role;
import org.example.bank_management_system.Model.User;
import org.example.bank_management_system.Repository.CustomerRepository;
import org.example.bank_management_system.Repository.EmployeeRepository;
import org.example.bank_management_system.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void registerCustomer(RegisterCustomerDTO dto) {
        validateUniqueUser(dto.getUsername(), dto.getEmail());

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole(Role.CUSTOMER);

        userRepository.save(user);

        Customer customer = new Customer();
        customer.setPhoneNumber(dto.getPhoneNumber());
        customer.setUser(user);

        customerRepository.save(customer);
    }

    @Transactional
    public void registerEmployee(RegisterEmployeeDTO dto) {
        validateUniqueUser(dto.getUsername(), dto.getEmail());

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole(Role.EMPLOYEE);

        userRepository.save(user);

        Employee employee = new Employee();
        employee.setPosition(dto.getPosition());
        employee.setSalary(dto.getSalary());
        employee.setUser(user);

        employeeRepository.save(employee);
    }

    private void validateUniqueUser(String username, String email) {
        if (userRepository.existsByUsername(username)) {
            throw new ApiException("Username already exists");
        }

        if (userRepository.existsByEmail(email)) {
            throw new ApiException("Email already exists");
        }
    }
}