package io.comeback.moviecatalogservice.resources;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import io.comeback.moviecatalogservice.models.CatalogItem;
import io.comeback.moviecatalogservice.models.Movie;
import io.comeback.moviecatalogservice.models.Rating;
import io.comeback.moviecatalogservice.models.UserRating;
import io.comeback.moviecatalogservice.services.MovieInfo;
import io.comeback.moviecatalogservice.services.UserRatingInfo;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;




@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
    

    @Autowired
    @Lazy
    private RestTemplate restTemplate;
    
        
    //public static final String RATING_SERVICE="rating-service";
    // public static final String MOVIE_SERVICE="movie-service";
    
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    MovieInfo movieInfo;
   
    @Autowired
    UserRatingInfo userRatingInfo;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
       
        //get all rated movie IDs
        //ParameterizedType 
 
        UserRating ratings = userRatingInfo.getUserRating(userId);     
      
        return ratings.getUserRating().stream().map(rating -> {
// for each movie Id, call movie info service and get details
         return movieInfo.getCatalogItem(rating);})
        .collect(Collectors.toList());
        
        //Put them all together
    }
    
//     @GetMapping
//     @CircuitBreaker(name=MOVS, fallbackMethod = "getCatalogItemFallback")
//     public CatalogItem getCatalogItem(Rating rating){ 
//         Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + 100, Movie.class);
//         // Movie movie = webClientBuilder.build()
//         // .get()
//         // .uri("http://localhost:8082/movies/" + rating.getMovieId())
//         // .retrieve()
//         // .bodyToMono(Movie.class)
//         // .block();

//         return new CatalogItem(movie.getName(), "Test", rating.getRating());
    
//     }

  
  
    
}
