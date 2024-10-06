package com.example.movies1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.movies1.models.LoggedInUser;
import com.example.movies1.models.User;
import com.example.movies1.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {
	@Autowired
	UserService users;

	@GetMapping("/")
	public String index(Model model) {

		// Bind empty User and LoginUser objects to the JSP
		// to capture the form input
		model.addAttribute("newUser", new User());
		model.addAttribute("newLogin", new LoggedInUser());
		return "login";
	}

	@PostMapping("/register/user")
	public String registerUser(@Valid @ModelAttribute("newUser") User newUser, BindingResult result,
			HttpSession session, Model model) {
		users.register(newUser, result);
		if (result.hasErrors()) {
			model.addAttribute("newLogin", new LoggedInUser());
			return "login";
		} else {
			session.setAttribute("userId", newUser.getId());
			return "redirect:/genres";
		}
	}

	@PostMapping("/login")
	public String loginUser(@Valid @ModelAttribute("newLogin") LoggedInUser newLogin, BindingResult result,
			HttpSession session, Model model) {

		// Call the user service for login
		User user = users.login(newLogin, result);
		if (result.hasErrors()) {
			model.addAttribute("newUser", new User());
			return "login";
		} else {
			session.setAttribute("userId", user.getId());
			return "redirect:/genres";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

}
