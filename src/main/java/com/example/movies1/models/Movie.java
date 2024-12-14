package com.example.movies1.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.movies1.validations.ValidMovieRating;
import com.example.movies1.validations.ValidReleaseYear;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Title is required!")
    private String title;

    @NotEmpty(message = "Description is required!")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id") // Ensure this matches your database schema
    private Genre genre;

    @ValidMovieRating
    private String movieRating;

    @ValidReleaseYear
    private Integer releaseYear;

    @Max(value = 5, message = "Rating must be less than or equal to 5.")
    @Min(value = 0, message = "Rating must be greater than or equal to 0.")
    private Integer rating;

    @NotEmpty(message = "Director is required!")
    private String director;

    @NotEmpty(message = "Lead actor is required!")
    private String leadActor;

    @NotEmpty (message = "Time length of the movie is required!")
    private String timeLength;
    
    @Column(nullable = true)
    private String sGenre;

    private String posterUrl;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_users", // Define the join table
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> usersWhoHaveSeen = new ArrayList<>();

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    // Default constructor
    public Movie() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setMovie(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setMovie(null);
    }

    public List<User> getUsersWhoHaveSeen() {
        return usersWhoHaveSeen;
    }

    public void setUsersWhoHaveSeen(List<User> usersWhoHaveSeen) {
        this.usersWhoHaveSeen = usersWhoHaveSeen;
    }
    public String getSGenre() {
        return sGenre;
    }

    public void setSGenre(String sGenre) {
        this.sGenre = sGenre;
    }

    public String getPosterUrl(){
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl){
        this.posterUrl = posterUrl;
    }

    public String getMovieRating(){
        return movieRating;
    }

    public void setMovieRating(String movieRating){
        this.movieRating = movieRating;
    }

    public String getLeadActor(){
        return leadActor;
    }

    public void setLeadActor (String leadActor){
        this.leadActor = leadActor;
    }

    public String getTimeLength(){
        return timeLength;
    }

    public void setTimeLength (String timeLength){
        this.timeLength = timeLength;
    }
}
