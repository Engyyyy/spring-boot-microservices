package com.top10moviesservice.services;

import java.util.List;

import com.top10moviesservice.models.RatedMovie;
import com.top10moviesservice.proto.Top10MoviesRequest;
import com.top10moviesservice.proto.Top10MoviesResponse;
import com.top10moviesservice.proto.TrendingMoviesGrpc.TrendingMoviesImplBase;

import io.grpc.stub.StreamObserver;


public class Top10Service extends TrendingMoviesImplBase {
     private final TopRatingService topRatingService;

    public Top10Service(TopRatingService topRatingService) {
        this.topRatingService = topRatingService;
    }

    @Override
    public void getTop10Movies(Top10MoviesRequest request, StreamObserver<Top10MoviesResponse> responseObserver) {
        // Implementation
        // ...
        List<RatedMovie> movies = topRatingService.getTop10Movies();
        for(RatedMovie movie : movies) {
            responseObserver.onNext(Top10MoviesResponse.newBuilder()
            .setId(movie.getMovieId())
            .setName(movie.getName())
            .setDescription(movie.getDescription())
            .setRating(movie.getRating()).build());
        }

        // Send the response asynchronously
        responseObserver.onCompleted();
    }

}
