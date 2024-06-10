package dev.nikhilesh.Movies;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Movie>allMovies(){
        return movieRepository.findAll();
    }

    public Optional<Movie> movieById(ObjectId id) {
        return movieRepository.findById(id);
    }

    public Optional<Movie> movieByImdbId(String id) {
        return movieRepository.findMovieByImdbId(id);
    }

    public Review createReview(String reviewRequest, String imdbId) throws Exception {
        Optional<Movie> optionalMovie = movieRepository.findMovieByImdbId(imdbId);
        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();
            Review review = new Review();
            review.setBody(reviewRequest);

            Review savedReview = reviewRepository.save(review);


            if (movie.getReviewIds() == null) {
                movie.setReviewIds(new ArrayList<>());
            }
            movie.getReviewIds().add(savedReview);
            movieRepository.save(movie);

            return savedReview;
        } else {
            throw new Exception("Movie not found");
        }
    }



}
