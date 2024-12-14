package com.example.movies1.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.movies1.models.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    boolean existsByTitleAndDirectorAndReleaseYear(String title, String director, Integer releaseYear);

    List<Movie> findByTitle(@Param("title") String title);

    List<Movie> findAllByOrderByTitleAsc();

    List<Movie> findByGenreId(Long genreId);

    List<Movie> findByGenreIdOrderByTitleAsc(Long genreId);

    @Query("SELECT m FROM Movie m WHERE (:genre IS NULL OR m.genre.name = :genre) AND (:releaseYear IS NULL OR m.releaseYear = :releaseYear) AND (:director IS NULL OR m.director = :director) AND (:user IS NULL OR m.user.firstName = :user)")
    List<Movie> filterMovies(@Param("genre") String genre, @Param("releaseYear") Integer releaseYear, @Param("director") String director, @Param("user") String user);

    long countByGenreId(Long genreId); // Count movies by genre ID

    List<Movie> findByReleaseYear(int releaseYear);

    List<Movie> findByRating(int rating);

    List<Movie> findByMovieRating(String movieRating);

    List<Movie> findByTimeLength(String timeLength);
    
    List<Movie> findByLeadActor(String leadActor);
    
    List<Movie> findByDirector(String director);
    
    // Counting Queries
    @Query("SELECT COUNT(m) FROM Movie m WHERE m.releaseYear = :releaseYear")
    long countMoviesByReleaseYear(@Param("releaseYear") int releaseYear);

    @Query("SELECT COUNT(m) FROM Movie m")
    long countTotalMovies(); 

    @Query("SELECT COUNT(m) FROM Movie m WHERE m.rating = :rating")
    long countMoviesByRating(@Param("rating") int rating);

    @Query("SELECT COUNT(m) FROM Movie m WHERE m.movieRating = :movieRating")
    long countMoviesByMovieRating(@Param("movieRating") String movieRating);
    
    @Query("SELECT COUNT(m) FROM Movie m WHERE m.timeLength = :timeLength")
    long countMoviesByTimeLength(@Param("timeLength") String timeLength);
    
    @Query("SELECT COUNT(m) FROM Movie m WHERE m.leadActor = :leadActor")
    long countMoviesByLeadActor(@Param("leadActor") String leadActor);
    
    @Query("SELECT COUNT(m) FROM Movie m WHERE m.director = :director")
    long countMoviesByDirector(@Param("director") String director);
}
