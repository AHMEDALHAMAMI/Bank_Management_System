package org.example.bank_management_system.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RegisterEmployeeDTO {

    @NotBlank(message = "Username cannot be null")
    @Size(min = 4, max = 10, message = "Username must be between 4 and 10 characters")
    private String username;

    @NotBlank(message = "Password cannot be null")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Name cannot be null")
    @Size(min = 2, max = 20, message = "Name must be between 2 and 20 characters")
    private String name;

    @NotBlank(message = "Email cannot be null")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Position cannot be null")
    private String position;

    @NotNull(message = "Salary cannot be null")
    @DecimalMin(value = "0.0", message = "Salary must be non-negative")
    private BigDecimal salary;
}