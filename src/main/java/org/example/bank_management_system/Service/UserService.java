package org.example.bank_management_system.Service;

import lombok.RequiredArgsConstructor;
import org.example.bank_management_system.Exception.ApiException;
import org.example.bank_management_system.Model.Role;
import org.example.bank_management_system.Model.User;
import org.example.bank_management_system.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new ApiException("User not found");
        }

        return user;
    }

    @Transactional
    public void updateUser(Integer id, User updatedUser) {
        User user = getUserById(id);

        if (updatedUser.getUsername() != null
                && !updatedUser.getUsername().equals(user.getUsername())) {
            if (userRepository.existsByUsername(updatedUser.getUsername())) {
                throw new ApiException("Username already exists");
            }
            user.setUsername(updatedUser.getUsername());
        }

        if (updatedUser.getEmail() != null
                && !updatedUser.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(updatedUser.getEmail())) {
                throw new ApiException("Email already exists");
            }
            user.setEmail(updatedUser.getEmail());
        }

        if (updatedUser.getName() != null) {
            user.setName(updatedUser.getName());
        }

        if (updatedUser.getPassword() != null
                && !updatedUser.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        if (updatedUser.getRole() != null) {
            Role role = updatedUser.getRole();
            user.setRole(role);
        }

        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Integer id) {
        User user = getUserById(id);

        if (user.getRole() == Role.CUSTOMER) {
            throw new ApiException(
                    "Delete the customer's accounts and customer profile before deleting this user");
        }

        if (user.getRole() == Role.EMPLOYEE) {
            throw new ApiException(
                    "Delete the employee profile before deleting this user");
        }

        userRepository.delete(user);
    }
}