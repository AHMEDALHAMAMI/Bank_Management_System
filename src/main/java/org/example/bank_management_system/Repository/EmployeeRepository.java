package org.example.bank_management_system.Repository;

import org.example.bank_management_system.Model.Employee;
import org.example.bank_management_system.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findByUser(User user);

    boolean existsByUser(User user);
}