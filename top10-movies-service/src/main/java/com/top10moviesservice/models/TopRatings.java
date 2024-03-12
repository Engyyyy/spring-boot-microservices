package com.top10moviesservice.models;

import java.util.List;

public class TopRatings {
    List<Rating> topRated;

    public TopRatings() {

    }

    public TopRatings(List<Rating> topRated) {
        this.topRated = topRated;
    }

    public List<Rating> getTopRated() {
        return topRated;
    }

    public void setTopRated(List<Rating> topRated) {
        this.topRated = topRated;
    }
}
