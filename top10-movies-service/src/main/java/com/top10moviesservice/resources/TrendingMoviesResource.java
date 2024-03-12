package com.top10moviesservice.resources;

import com.top10moviesservice.models.Movie;
import com.top10moviesservice.models.RatedMovie;
import com.top10moviesservice.services.TopRatingService;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/trending")
public class TrendingMoviesResource {

    private final RestTemplate restTemplate;

    private final TopRatingService topRatingService;

    public TrendingMoviesResource(RestTemplate restTemplate,
            TopRatingService topRatingService) {

        this.restTemplate = restTemplate;
        this.topRatingService = topRatingService;
    }

    /**
     * Makes a call to MovieInfoService to get movieId, name and description,
     * Makes a call to RatingsService to get ratings
     * Accumulates both data to create a MovieCatalog
     * 
     * @param
     * @return CatalogItem that contains name, description and rating
     */
    @RequestMapping("/top-10-ratings")
    public List<RatedMovie> getTop10MoviesByRating() {
        return topRatingService.getTop10Movies();
    }
}