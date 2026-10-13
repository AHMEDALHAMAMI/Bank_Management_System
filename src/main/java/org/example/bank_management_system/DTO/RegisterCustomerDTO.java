package org.example.bank_management_system.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterCustomerDTO {

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

    @NotBlank(message = "Phone number cannot be null")
    @Pattern(regexp = "^05[0-9]{8}$",
            message = "Phone number must start with 05 and contain 10 digits")
    private String phoneNumber;
}