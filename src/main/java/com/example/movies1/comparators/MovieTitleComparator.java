package com.example.movies1.comparators;

import java.util.Comparator;

import com.example.movies1.models.Movie;

public class MovieTitleComparator implements Comparator<Movie> {
    @Override
    public int compare(Movie m1, Movie m2) {
        String title1 = m1.getTitle().toLowerCase();
        String title2 = m2.getTitle().toLowerCase();

        // Remove "the" if it's at the start of the title
        if (title1.startsWith("the ")) {
            title1 = title1.substring(4); // Remove "the "
        }
        if (title2.startsWith("the ")) {
            title2 = title2.substring(4); // Remove "the "
        }

        return title1.compareTo(title2);
    }
}
