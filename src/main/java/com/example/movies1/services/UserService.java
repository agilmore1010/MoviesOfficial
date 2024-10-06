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
        // Check if the email is already registered
        Optional<User> potentialUser = uRepo.findByEmail(newUser.getEmail());
        if (potentialUser.isPresent()) {
            result.rejectValue("email", "error.email", "An account with this email already exists.");
            return null;
        }

        // Check if passwords match
        if (!newUser.getPassword().equals(newUser.getConfirm())) {
            result.rejectValue("confirm", "error.confirm", "Passwords do not match.");
            return null;
        }

        // Validate birthdate
        if (newUser.getBirthdate() == null) {
            result.rejectValue("birthdate", "error.birthdate", "Birthdate is required.");
            return null;
        } else {
            LocalDate today = LocalDate.now();
            LocalDate birthdate = newUser.getBirthdate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
            int age = Period.between(birthdate, today).getYears();
            if (age < 13) {
                result.rejectValue("birthdate", "error.birthdate", "You must be at least 13 years old to register.");
                return null;
            }
        }

        // Hash the password
        String hashedPassword = BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt());
        newUser.setPassword(hashedPassword);

        return uRepo.save(newUser);
    }

    public User login(LoggedInUser newLoginObject, BindingResult result) {
        // Check if email exists
        Optional<User> potentialUser = uRepo.findByEmail(newLoginObject.getEmail());
        if (!potentialUser.isPresent()) {
            result.rejectValue("email", "error.email", "Email not found. Try registering.");
            return null;
        }

        User user = potentialUser.get();

        // Validate password
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
		Optional<User> thisUser = uRepo.findById(id);
		if (thisUser.isEmpty()) {
			return null;
		}

		return thisUser.get();
	}
	public void save(User user) {
		uRepo.save(user);
	}
}
