package com.top10moviesservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.top10moviesservice.models.TopRatings;
import com.top10moviesservice.models.Movie;
import com.top10moviesservice.models.Rating;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class TopRatingService {

    private final RestTemplate restTemplate;

    public TopRatingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "getFallbackTopRatings",
        commandProperties = {
                // Time to cause timeout
                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                // N, Hystrix looks at (analyzes) last N requests.
                @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                // if >= 50 percent of the last N requests fail, break the circuit
                @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                // Wait/Sleep for 5 seconds before sending another request to the failed service
                @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
        })
    public TopRatings getTopRating() {
        String ratingsUrl = "http://ratings-data-service/ratings/top";
        return Objects.requireNonNull(restTemplate.getForObject(ratingsUrl, TopRatings.class));
    }

    public TopRatings getFallbackTopRatings() {
        TopRatings topRatings = new TopRatings();
        topRatings.setTopRated(Collections.singletonList(
                new Rating()
        ));

        return topRatings;
    }


    @HystrixCommand(fallbackMethod = "getFallbackMovieInfo",
        commandProperties = {
                // Time to cause timeout
                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                // N, Hystrix looks at (analyzes) last N requests.
                @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                // if >= 50 percent of the last N requests fail, break the circuit
                @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                // Wait/Sleep for 5 seconds before sending another request to the failed service
                @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
        })
    public Movie getMovieInfo(String movieId) {
        String ratingsUrl = "http://movie-info-service/movies/" + movieId;
        return Objects.requireNonNull(restTemplate.getForObject(ratingsUrl, Movie.class));
    }

    public Movie getFallbackMovieInfo() {
        Movie movie = new Movie();
        return movie;
    }

    public List<Movie> getTop10Movies() {
        TopRatings topRatings = this.getTopRating();
        List<Movie> topRated = new ArrayList<>();
        for(Rating top : topRatings.getTopRated()) {
            Movie movie = this.getMovieInfo(top.getMovieId());
            topRated.add(movie);
        }
        return topRated;
    }
}
