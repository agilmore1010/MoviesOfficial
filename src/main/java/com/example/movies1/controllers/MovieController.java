package com.example.movies1.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.movies1.models.Comment;
import com.example.movies1.models.Genre;
import com.example.movies1.models.Movie;
import com.example.movies1.models.User;
import com.example.movies1.services.GenreService;
import com.example.movies1.services.MovieService;
import com.example.movies1.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class MovieController {

    @Autowired
    private UserService users;

    @Autowired
    private MovieService movies;

    @Autowired
    private GenreService genres;

    private Long getLoggedInUserId(HttpSession session) {
        return (Long) session.getAttribute("userId");
    }

    private boolean isUserLoggedIn(HttpSession session) {
        return getLoggedInUserId(session) != null;
    }

    @GetMapping("/movies")
    public String showMovies(Model model, HttpSession session) {
        Long userId = getLoggedInUserId(session);
        Optional<User> optionalUser = users.getLoggedInUser(userId);
        if (optionalUser.isPresent()) {
            model.addAttribute("user", optionalUser.get());
        } else {
            return "redirect:/";
        }

        List<Movie> genreList = movies.getAllByTitleAsc(); // Call the method from the service
        model.addAttribute("movies", genreList);
        return "genreList"; // Your JSP page to display movies
    }

    @GetMapping("/new/movies")
    public String showAddMovieForm(@ModelAttribute("movie") Movie movie, Model model, HttpSession session) {
        if (!isUserLoggedIn(session)) {
            return "redirect:/";
        }

        List<Genre> genreList = genres.getAllGenres(); // Fetch all genres
        model.addAttribute("genres", genreList); // Pass genres to the view

        return "addMovie";
    }

    @PostMapping("/new/movies")
    public String addMovie(@Valid @ModelAttribute("movie") Movie movie, BindingResult result,
            @ModelAttribute("newGenre") String newGenre, HttpSession session,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.movie", result);
            redirectAttributes.addFlashAttribute("movie", movie);
            redirectAttributes.addFlashAttribute("error",
                    "Please make sure your Movie has a title, description, a valid release year, your own rating from 0-5, a genre, and a director.");
            return "redirect:/new/movies";
        }

        // Check for duplicate movie
        if (movies.isMovieDuplicate(movie)) {
            redirectAttributes.addFlashAttribute("error",
                    "A movie with the same title, release year, and director already exists.");
            return "redirect:/new/movies";
        }

        Optional<User> currentUserOpt = users.getLoggedInUser(getLoggedInUserId(session));
        if (currentUserOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "User not found.");
            return "redirect:/new/movies";
        }

        User currentUser = currentUserOpt.get();
        movie.setUser(currentUser);

        Genre genre;
        if (newGenre != null && !newGenre.trim().isEmpty()) {
            genre = new Genre();
            genre.setName(newGenre);
            genres.createGenre(genre); // Save the new genre
        } else {
            genre = genres.getGenreById(movie.getGenre().getId());
            if (genre == null) {
                redirectAttributes.addFlashAttribute("error", "Invalid genre selected.");
                return "redirect:/new/movies";
            }
        }

        movie.setGenre(genre);
        movies.createMovie(movie);
        redirectAttributes.addFlashAttribute("success", "Movie added successfully!");
        return "redirect:/genres";
    }

    @GetMapping("/view/movies/{id}")
    public String viewMovie(@PathVariable("id") Long id, Model model, HttpSession session) {
        if (!isUserLoggedIn(session)) {
            return "redirect:/";
        }
        Movie movie = movies.getOneMovie(id);
        if (movie == null) {
            return "redirect:/genres";
        }
        if (!model.containsAttribute("commentForm")) {
            model.addAttribute("commentForm", new Comment());
        }

        model.addAttribute("movie", movie);
        model.addAttribute("usersWhoHaveSeen", movie.getUsersWhoHaveSeen());

        // Debug logged-in user ID and movie owner ID
        Long loggedInUserId = getLoggedInUserId(session);
        Long movieOwnerId = movie.getUser().getId();
        System.out.println("Logged-in User ID: " + loggedInUserId);
        System.out.println("Movie Owner ID: " + movieOwnerId);

        model.addAttribute("user", users.getLoggedInUser(loggedInUserId).orElse(null));
        model.addAttribute("isOwner", loggedInUserId.equals(movieOwnerId));
        return "viewMovie";
    }

    @GetMapping("/edit/movies/{id}")
    public String editMovie(@PathVariable("id") Long id, Model model, HttpSession session) {
        if (!isUserLoggedIn(session)) {
            return "redirect:/";
        }
        Movie movie = movies.getOneMovie(id);
        if (movie == null) {
            return "redirect:/genres";
        }
        model.addAttribute("movie", movie);
        List<Genre> genreList = genres.getAllGenres(); // Fetch all genres
        model.addAttribute("genres", genreList); // Pass genres to the view
        return "editMovie";
    }

    @PutMapping("/edit/movies/{id}")
    public String updateMovie(@Valid @ModelAttribute("movie") Movie movie, BindingResult result, HttpSession session,
            RedirectAttributes redirectAttributes) {
        if (!isUserLoggedIn(session)) {
            return "redirect:/";
        }

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.movie", result);
            redirectAttributes.addFlashAttribute("movie", movie);
            redirectAttributes.addFlashAttribute("error",
                    "No fields can be blank, movie title must be between 3-50 characters, a valid release year, and the rating must be valid, must be a valid movie rating, and lead Actor/Actress must not be blank.");
            return "redirect:/edit/movies/" + movie.getId();
        }

        Optional<User> currentUserOpt = users.getLoggedInUser(getLoggedInUserId(session));
        if (currentUserOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "User not found.");
            return "redirect:/edit/movies/" + movie.getId();
        }
        User currentUser = currentUserOpt.get();

        Movie existingMovie = movies.getOneMovie(movie.getId());
        if (existingMovie == null) {
            redirectAttributes.addFlashAttribute("error", "Movie not found.");
            return "redirect:/genres";
        }

        movie.setUser(currentUser);
        movies.updateMovie(movie);
        redirectAttributes.addFlashAttribute("success", "Movie updated successfully!");
        return "redirect:/genres";
    }

    @DeleteMapping("/delete/movies/{movieId}")
    public String deleteMovie(@PathVariable("movieId") Long movieId, HttpSession session,
            RedirectAttributes redirectAttributes) {
        if (!isUserLoggedIn(session)) {
            return "redirect:/";
        }
        movies.deleteMovie(movieId);
        redirectAttributes.addFlashAttribute("success", "Movie deleted successfully!");
        return "redirect:/genres";
    }

    @PostMapping("/movies/{id}/seen")
    public String markAsSeen(@PathVariable("id") Long id, HttpSession session) {
        if (!isUserLoggedIn(session)) {
            return "redirect:/";
        }

        Long userId = getLoggedInUserId(session);
        Optional<User> userOpt = users.getLoggedInUser(userId);
        Movie movie = movies.getOneMovie(id);

        if (userOpt.isPresent() && movie != null) {
            User user = userOpt.get();

            if (!movie.getUsersWhoHaveSeen().contains(user)) {
                movie.getUsersWhoHaveSeen().add(user);
                movies.saveMovie(movie);  // Save updated movie
            }
        }

        return "redirect:/view/movies/" + id;
    }

    @PostMapping("/movies/{movieId}/notSeen/{userId}")
    public String removeSeenUser(@PathVariable("movieId") Long movieId, @PathVariable("userId") Long userId, HttpSession session) {
        if (!isUserLoggedIn(session)) {
            return "redirect:/";
        }

        Movie movie = movies.getOneMovie(movieId);
        Optional<User> currentUserOpt = users.getLoggedInUser(getLoggedInUserId(session));
        Optional<User> seenUserOpt = users.getLoggedInUser(userId);

        if (currentUserOpt.isPresent() && seenUserOpt.isPresent() && movie != null) {
            User currentUser = currentUserOpt.get();
            User seenUser = seenUserOpt.get();

            // Check if the current user is the owner of the seen user
            if (currentUser.getId().equals(seenUser.getId())) {
                movie.getUsersWhoHaveSeen().remove(seenUser);
                movies.saveMovie(movie);
            }
        }

        return "redirect:/view/movies/" + movieId;
    }

    @GetMapping("/searchRandomMovie")
    public String searchRandomMovie(
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String sGenre,
            @RequestParam(required = false) String movieRating,
            @RequestParam(required = false) String leadActor,
            @RequestParam(required = false) Integer releaseYear,
			@RequestParam(required = false) Integer rating,
            @RequestParam(required = false) String director,
            @RequestParam(required = false) String user,
            @RequestParam(required = false) String timeLength,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {

        List<Movie> filteredMovies = movies.filterMovies(genre, sGenre, releaseYear, rating, director, user, movieRating, leadActor, timeLength);

        if (!filteredMovies.isEmpty()) {
            Movie randomMovie = filteredMovies.get(new Random().nextInt(filteredMovies.size()));
            return "redirect:/view/movies/" + randomMovie.getId(); // Redirect to viewMovie page
        } else {
            redirectAttributes.addFlashAttribute("message", "No movies found for the selected criteria.");
            return "redirect:/genres"; // Redirect to the movies page with a message
        }
    }

}
