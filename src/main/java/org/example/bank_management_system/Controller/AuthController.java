package org.example.bank_management_system.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bank_management_system.Api.ApiResponse;
import org.example.bank_management_system.DTO.RegisterCustomerDTO;
import org.example.bank_management_system.DTO.RegisterEmployeeDTO;
import org.example.bank_management_system.Service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register/customer")
    public ResponseEntity<ApiResponse> registerCustomer(
            @Valid @RequestBody RegisterCustomerDTO dto) {

        authService.registerCustomer(dto);

        return ResponseEntity.status(200)
                .body(new ApiResponse("Customer registered successfully"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register/employee")
    public ResponseEntity<ApiResponse> registerEmployee(
            @Valid @RequestBody RegisterEmployeeDTO dto) {

        authService.registerEmployee(dto);

        return ResponseEntity.status(200)
                .body(new ApiResponse("Employee registered successfully"));
    }
}