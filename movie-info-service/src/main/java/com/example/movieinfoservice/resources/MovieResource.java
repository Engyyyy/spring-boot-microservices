package com.example.movieinfoservice.resources;

import com.example.movieinfoservice.cache.CacheMovie;
import com.example.movieinfoservice.cache.CacheMovieRepository;
import com.example.movieinfoservice.models.Movie;
import com.example.movieinfoservice.models.MovieSummary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/movies")
public class MovieResource {

    @Value("${api.key}")
    private String apiKey;

    private RestTemplate restTemplate;

    private CacheMovieRepository cacheMovieRepository;

    public MovieResource(RestTemplate restTemplate, CacheMovieRepository cacheMovieRepository) {
        this.restTemplate = restTemplate;
        this.cacheMovieRepository = cacheMovieRepository;
    }

    @RequestMapping("/{movieId}")
    public Movie getMovieInfo(@PathVariable("movieId") String movieId) {

        CacheMovie cacheMovie = cacheMovieRepository.findByMovieId(movieId);
        if(cacheMovie == null) {
            // Get the movie info from TMDB
            final String url = "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" + apiKey;
            MovieSummary movieSummary = restTemplate.getForObject(url, MovieSummary.class);

            Movie movie = new Movie(movieId, movieSummary.getTitle(), movieSummary.getOverview());
            CacheMovie newCacheMovie = new CacheMovie(movie);
            cacheMovieRepository.save(newCacheMovie);

            return movie;
        } else {
            System.out.println(cacheMovie.getDescription());
            return new Movie(movieId, cacheMovie.getName(), cacheMovie.getDescription());
        }
    }
}
