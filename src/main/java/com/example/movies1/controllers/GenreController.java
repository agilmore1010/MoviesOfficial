package com.example.movies1.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.movies1.comparators.MovieTitleComparator;
import com.example.movies1.models.Genre;
import com.example.movies1.models.Movie;
import com.example.movies1.models.User;
import com.example.movies1.services.GenreService;
import com.example.movies1.services.MovieService;
import com.example.movies1.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class GenreController {

    @Autowired
    private UserService users;
    
    @Autowired
    private GenreService genres;

    @Autowired
    private MovieService movies;

    private Long getLoggedInUserId(HttpSession session) {
        return (Long) session.getAttribute("userId");
    }
    
    @GetMapping("/genres")
    public String listGenres(Model model, HttpSession session) {
        Long userId = getLoggedInUserId(session);
        
        if (userId == null) {
            return "redirect:/login";
        }
        
        Optional<User> loggedInUserOpt = users.getLoggedInUser(userId);
        if (loggedInUserOpt.isPresent()) {
            model.addAttribute("user", loggedInUserOpt.get());
        }
        
        model.addAttribute("totalMovieCount", movies.countTotaMovies());
        model.addAttribute("genres", genres.getAllGenres());
        return "genreList";
    }

    @PostMapping("/genres")
    public String createGenre(@RequestParam("genreName") String genreName, RedirectAttributes redirectAttributes) {
        if (genreName == null || genreName.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Genre name cannot be empty.");
            return "redirect:/new/movies";
        }

        Genre genre = new Genre();
        genre.setName(genreName);
        genres.createGenre(genre);

        redirectAttributes.addFlashAttribute("success", "Genre added successfully!");
        return "redirect:/new/movies";
    }

    @GetMapping("/genres/{id}")
    public String viewMoviesByGenre(@PathVariable("id") Long genreId, Model model) {
        Genre genre = genres.getGenreById(genreId);
        if (genre == null) {
            return "redirect:/genres";
        }

        List<Movie> moviesInGenre = movies.getMoviesByGenre(genreId);
        long movieCount = movies.countMoviesByGenre(genreId);
        long totalMovieCount = movies.countTotaMovies();

        Collections.sort(moviesInGenre, new MovieTitleComparator());

        model.addAttribute("genre", genre);
        model.addAttribute("movies", movies.getMoviesByGenre(genreId));
        model.addAttribute("movies", moviesInGenre);
        model.addAttribute("movieCount", movieCount);
        model.addAttribute("totalMovieCount", totalMovieCount);
        return "genreMovies";
    }
    
    @GetMapping("/genres/random")
    public String getRandomMovie(Model model) {
        List<Genre> genreList = genres.getAllGenres();
        
        if (genreList != null && !genreList.isEmpty()) {
            Genre randomGenre = genreList.get(new Random().nextInt(genreList.size()));
            
            List<Movie> movieList = randomGenre.getMovies();
            
            if (movieList != null && !movieList.isEmpty()) {
                Movie randomMovie = movieList.get(new Random().nextInt(movieList.size()));
                return "redirect:/view/movies/" + randomMovie.getId();
            }
        }

        return "redirect:/genres";
    }
}
