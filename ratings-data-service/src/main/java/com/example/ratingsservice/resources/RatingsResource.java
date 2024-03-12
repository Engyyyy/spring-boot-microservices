package com.example.ratingsservice.resources;
import com.example.ratingsservice.models.Rating;
import com.example.ratingsservice.models.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingsResource {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private class RatingMapper implements RowMapper<Rating> {
        public Rating mapRow(ResultSet result, int rowNumber) throws SQLException {
            Rating rating = new Rating();
            rating.setMovieId(result.getString("movie_id"));
            rating.setRating(result.getInt("rating"));
            return rating;
        }
    }
    @RequestMapping("/{userId}")
    public UserRating getRatingsOfUser(@PathVariable String userId) {

        String sql = "SELECT movie_id, rating FROM user_ratings WHERE user_id = ?";
        List<Rating> ratings = jdbcTemplate.query(sql, new Object[]{userId}, new RatingMapper());

        return new UserRating(ratings);
    }
}

