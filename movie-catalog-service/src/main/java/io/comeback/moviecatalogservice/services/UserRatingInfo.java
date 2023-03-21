package io.comeback.moviecatalogservice.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.comeback.moviecatalogservice.models.Rating;
import io.comeback.moviecatalogservice.models.UserRating;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class UserRatingInfo {
    

    @Autowired
    private RestTemplate restTemplate;
    private static final String RATINGS="ratings";

    @CircuitBreaker(name=RATINGS, fallbackMethod = "getUserRatingFallback" )
    public UserRating getUserRating(String userId){
        System.out.println("Hey I am in getUSerRating method");
        return restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/"+ userId, UserRating.class);

    }
  
      
    public UserRating getUserRatingFallback(Exception e){
        UserRating userRating=new UserRating();
        userRating.setUserRating(Arrays.asList(new Rating("0",0)));
          return userRating; 
    }

}
