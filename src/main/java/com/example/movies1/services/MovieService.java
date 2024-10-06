package com.example.movies1.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.movies1.comparators.MovieTitleComparator;
import com.example.movies1.models.Genre;
import com.example.movies1.models.Movie;
import com.example.movies1.models.User;
import com.example.movies1.repositories.GenreRepository;
import com.example.movies1.repositories.MovieRepository;

@Service
public class MovieService {

    @Autowired
    private MovieRepository mRepo;

    @Autowired
    private GenreRepository gRepo;

    // Create or Update a Movie
    public void createMovie(Movie movie) {
        if (isMovieDuplicate(movie)){
            throw new IllegalArgumentException("A movie with the same title, director, and release year already exists.");
        }
        // Ensure that the genre exists
        Genre genre = gRepo.findById(movie.getGenre().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid genre ID"));
        movie.setGenre(genre);
        mRepo.save(movie);
    }

    // Retrieve all movies sorted by title
    public List<Movie> getAllByTitleAsc() {
        return mRepo.findAllByOrderByTitleAsc();
    }

    // Retrieve all movies categorized by the first letter of the title
    public Map<Character, List<Movie>> getMoviesByLetter() {
        List<Movie> movies = getAllByTitleAsc(); // Fetch sorted movies

        // Categorize movies by the first letter of their title
        return movies.stream().collect(Collectors.groupingBy(movie -> movie.getTitle().toUpperCase().charAt(0)));
    }

        public List<Movie> getSortedMovies(List<Movie> movies) {
        Collections.sort(movies, new MovieTitleComparator());
        return movies;
    }

    // Update an existing movie
    public void updateMovie(Movie movie) {
        // Ensure that the genre exists
        Genre genre = gRepo.findById(movie.getGenre().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid genre ID"));
        movie.setGenre(genre);
        mRepo.save(movie);
    }

    // Delete a movie by its ID
    public void deleteMovie(Long id) {
        if(!mRepo.existsById(id)){
            throw new IllegalArgumentException("Movie not found" +id);
        }
        mRepo.deleteById(id);
    }

    // Retrieve a single movie by its ID
    public Movie getOneMovie(Long id) {
        return mRepo.findById(id).orElse(null);
    }

    public boolean isMovieDuplicate(Movie movie) {
        return mRepo.existsByTitleAndDirectorAndReleaseYear(movie.getTitle(), movie.getDirector(),
                movie.getReleaseYear());
    }

    public List<Movie> getMoviesByGenre(Long genreId) {
        return mRepo.findByGenreIdOrderByTitleAsc(genreId);
    }

    public List<User> getUsersWhoHaveSeen(Long movieId) {
        Optional<Movie> movie = mRepo.findById(movieId);
        return movie.isPresent() ? movie.get().getUsersWhoHaveSeen() : new ArrayList<>();
    }

    public void saveMovie(Movie movie) {
        mRepo.save(movie);
    }

    public List<Movie> filterMovies(String genre, String sGenre, Integer releaseYear, Integer rating, String director, String user, String leadActor, String movieRating, String timeLength) {
        // Start with all movies
        List<Movie> movies = mRepo.findAll();

        if (genre != null && !genre.isEmpty()) {
            movies = movies.stream()
                    .filter(m -> m.getGenre() != null && genre.equals(m.getGenre().getName())) // Compare genre names
                    .collect(Collectors.toList());
        }
        if (sGenre !=null && !sGenre.isEmpty()){
            movies = movies.stream()
                    .filter(m -> sGenre.equalsIgnoreCase(m.getSGenre()))
                    .collect(Collectors.toList());
        }
        if (timeLength !=null && !timeLength.isEmpty()){
            movies = movies.stream()
                    .filter(m -> timeLength.equalsIgnoreCase(m.getTimeLength()))
                    .collect(Collectors.toList());
        } 
        if (leadActor !=null && !leadActor.isEmpty()){
            movies = movies.stream()
                    .filter(m -> leadActor.equalsIgnoreCase(m.getLeadActor()))
                    .collect(Collectors.toList());
        }        
        if (movieRating !=null && !movieRating.isEmpty()){
            movies = movies.stream()
                    .filter(m -> movieRating.equalsIgnoreCase(m.getMovieRating()))
                    .collect(Collectors.toList());
        }
        if (releaseYear != null) {
            movies = movies.stream()
                    .filter(m -> releaseYear.equals(m.getReleaseYear()))
                    .collect(Collectors.toList());
        }
        if (rating != null) {
            movies = movies.stream()
                    .filter(m -> rating.equals(m.getRating()))
                    .collect(Collectors.toList());
        }
        if (director != null && !director.isEmpty()) {
            movies = movies.stream()
                    .filter(m -> director.equalsIgnoreCase(m.getDirector()))
                    .collect(Collectors.toList());
        }
        if (user != null && !user.isEmpty()) {
            movies = movies.stream()
                    .filter(m -> m.getUser() != null
                    && (m.getUser().getFirstName() + " " + m.getUser().getLastName()).equalsIgnoreCase(user))
                    .collect(Collectors.toList());
        }

        return movies;
    }
    public long countMoviesByGenre (Long genreId){
        return mRepo.countByGenreId(genreId);
    }
    public long countTotaMovies (){
        return mRepo.count();
    }
}
