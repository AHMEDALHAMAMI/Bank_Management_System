package org.example.bank_management_system.Service;

import lombok.RequiredArgsConstructor;
import org.example.bank_management_system.Exception.ApiException;
import org.example.bank_management_system.Model.Employee;
import org.example.bank_management_system.Repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Employee getEmployeeById(Integer id) {
        Employee employee = employeeRepository.findById(id).orElse(null);

        if (employee == null) {
            throw new ApiException("Employee not found");
        }

        return employee;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}