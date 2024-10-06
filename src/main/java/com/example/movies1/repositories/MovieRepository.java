package com.example.movies1.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.movies1.models.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> { // Changed Integer to Long for ID type

    boolean existsByTitleAndDirectorAndReleaseYear(String title, String director, Integer releaseYear);

    List<Movie> findByTitle(@Param("title") String title);

    List<Movie> findAllByOrderByTitleAsc();

    @Override
    Optional<Movie> findById(Long id); // Changed return type to Optional

    List<Movie> findByGenreId(Long genreId);

    List<Movie> findByGenreIdOrderByTitleAsc(Long genreId);

    @Query("SELECT m FROM Movie m WHERE (:genre IS NULL OR m.genre.name = :genre) AND (:releaseYear IS NULL OR m.releaseYear = :releaseYear) AND (:director IS NULL OR m.director = :director) AND (:user IS NULL OR m.user.firstName = :user)")
    List<Movie> filterMovies(@Param("genre") String genre, @Param("releaseYear") Integer releaseYear, @Param("director") String director, @Param("user") String user);

    long countByGenreId(Long genreId);
}
