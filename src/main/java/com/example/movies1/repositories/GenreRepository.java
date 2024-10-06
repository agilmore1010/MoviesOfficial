package com.example.movies1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.movies1.models.Genre;

public interface GenreRepository extends JpaRepository <Genre, Long> {
}
