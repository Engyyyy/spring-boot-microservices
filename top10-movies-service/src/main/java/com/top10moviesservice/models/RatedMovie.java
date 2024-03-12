package com.top10moviesservice.models;

public class RatedMovie {
    private String movieId;
    private String name;
    private String description;
    private double rating;

    public RatedMovie() {
    }

    public RatedMovie(String movieId, String name, String description, double rating) {
        this.movieId = movieId;
        this.name = name;
        this.description = description;
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
