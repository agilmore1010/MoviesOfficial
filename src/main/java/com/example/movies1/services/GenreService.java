package com.example.movies1.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.movies1.models.Genre;
import com.example.movies1.repositories.GenreRepository;

@Service
public class GenreService {

    @Autowired
    private GenreRepository gRepo;

    public List<Genre> getAllGenres() {
        return gRepo.findAll();
    }

    public void createGenre(Genre genre) {
        gRepo.save(genre);
    }

    public Genre getGenreById(Long id) {
        return gRepo.findById(id).orElse(null);
    }

}
