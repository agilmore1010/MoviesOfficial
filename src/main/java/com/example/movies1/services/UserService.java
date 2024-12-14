package com.example.movies1.services;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.example.movies1.models.LoggedInUser;
import com.example.movies1.models.User;
import com.example.movies1.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository uRepo;

    public User register(User newUser, BindingResult result) {
        checkEmailExists(newUser, result);
        checkPasswordsMatch(newUser, result);
        validateAge(newUser, result);

        if (result.hasErrors()) {
            return null;
        }

        // Hash the password
        String hashedPassword = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
        newUser.setPassword(hashedPassword);

        return uRepo.save(newUser);
    }

    private void checkEmailExists(User newUser, BindingResult result) {
        Optional<User> potentialUser = uRepo.findByEmail(newUser.getEmail());
        if (potentialUser.isPresent()) {
            result.rejectValue("email", "error.email", "An account with this email already exists.");
        }
    }

    private void checkPasswordsMatch(User newUser, BindingResult result) {
        if (!newUser.getPassword().equals(newUser.getConfirm())) {
            result.rejectValue("confirm", "error.confirm", "Passwords do not match.");
        }
    }

    private void validateAge(User newUser, BindingResult result) {
        if (newUser.getBirthdate() == null) {
            result.rejectValue("birthdate", "error.birthdate", "Birthdate is required.");
        } else {
            LocalDate today = LocalDate.now();
            int age = Period.between(newUser.getBirthdate(), today).getYears();
            if (age < 13) {
                result.rejectValue("birthdate", "error.birthdate", "You must be at least 13 years old to register.");
            }
        }
    }

    public User login(LoggedInUser newLoginObject, BindingResult result) {
        Optional<User> potentialUser = uRepo.findByEmail(newLoginObject.getEmail());
        if (potentialUser.isEmpty()) {
            result.rejectValue("email", "error.email", "Email not found. Try registering.");
            return null;
        }

        User user = potentialUser.get();
        if (!BCrypt.checkpw(newLoginObject.getPassword(), user.getPassword())) {
            result.rejectValue("password", "error.password", "Invalid login attempt, try again!");
            return null;
        }

        return user;
    }

    public Optional<User> getLoggedInUser(Long id) {
        return uRepo.findById(id);
    }

    public User getUserById(Long id) {
        return uRepo.findById(id).orElse(null);
    }

    public void save(User user) {
        uRepo.save(user);
    }
}
