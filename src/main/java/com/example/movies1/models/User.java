package com.example.movies1.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.movies1.validations.ValidAge;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @NotEmpty(message = "First name is required!")
    @Size(min = 2, message = "First name must be at least 2 characters!")
    private String firstName;

    @NotEmpty(message = "Last name is required!")
    @Size(min = 2, message = "Last name must be at least 2 characters!")
    private String lastName;
	
	@NotEmpty(message= "Email is required!")
	@Email(message = "Please enter a valid email")
	private String email; 
	
	@NotEmpty(message = "Password is required!")
	@Size(min = 8, max = 128, message= "Password must be between 8 and 128 characters")
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).+$", message = "Password must contain at least 1 capital letter and at least 1 number!")
	private String password;
	
	@Transient
	@NotEmpty(message = "Confirm password is required!")
	@Size(min = 8, max = 128, message = "Confirm Password must be between 8 and 128 characters")
	private String confirm;
	
	@NotNull(message = "Birthdate is required")
	@ValidAge (minAge = 13) 
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate birthdate;
	
	@Column(updatable=false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDateTime createdAt;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    @ManyToMany(mappedBy = "usersWhoHaveSeen", fetch = FetchType.LAZY)
    private List<Movie> moviesSeen = new ArrayList<>();
	
	public User( ) {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
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

	public List<Movie> getMoviesSeen() {
		return moviesSeen;
	}

	public void setMoviesSeen(List<Movie> moviesSeen) {
		this.moviesSeen = moviesSeen;
	}
	
}

