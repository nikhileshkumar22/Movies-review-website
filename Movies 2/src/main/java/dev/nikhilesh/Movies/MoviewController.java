package dev.nikhilesh.Movies;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/movies")
public class MoviewController {
@Autowired
private MovieService movieService;
    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies(){
       return new ResponseEntity<List<Movie>>(movieService.allMovies(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Movie>> getMovieById(@PathVariable String id) {

        ObjectId objectId;
        try {
            objectId = new ObjectId(id);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Movie> movie = movieService.movieById(objectId);
        return movie.map(value -> new ResponseEntity<>(Optional.of(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{imdbId}/")
    public ResponseEntity<Optional<Movie>> getMovieByImdbId(@PathVariable String imdbId) {
        return new ResponseEntity<Optional<Movie>>(movieService.movieByImdbId(imdbId),HttpStatus.OK);
    }


    @PostMapping("/review")
    public ResponseEntity<Review> createReview(@RequestBody Map<String, String> payload) throws Exception {

        return new ResponseEntity<Review>(movieService.createReview(payload.get("reviewBody"), payload.get("imdbId")), HttpStatus.OK);
    }


}
