package io.comeback.moviecatalogservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.comeback.moviecatalogservice.models.CatalogItem;
import io.comeback.moviecatalogservice.models.Movie;
import io.comeback.moviecatalogservice.models.Rating;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


@Service
public class MovieInfo {
    
    @Autowired
    private RestTemplate restTemplate;
    
    private static final String MOVS = "movs"; 

    @CircuitBreaker(name = MOVS, fallbackMethod = "getCatalogItemFallback" )
public CatalogItem getCatalogItem(Rating rating){
    Movie movie= restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
    return new CatalogItem(movie.getName(), "Test", 0);
}   

public CatalogItem getCatalogItemFallback(Exception e){
    return new CatalogItem("Lagaan", "Freedom movement",0);
}

}
