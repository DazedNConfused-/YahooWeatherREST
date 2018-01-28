package com.example.weather.controller;

import com.example.weather.auth.entity.Account;
import com.example.weather.auth.repository.AccountRepository;
import com.example.weather.yahoo.Atmosphere;
import com.example.weather.yahoo.Query;
import com.example.weather.yahoo.Results;
import com.example.weather.yahoo.forecast.YahooWeatherForecastService;
import com.example.weather.yahoo.location.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
class MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private AccountRepository accountRepository;

    MainController() {
    }

    @RequestMapping(method = RequestMethod.GET)
    String readBookmarks(@PathVariable String currentUser) {
        return "Hello World " + currentUser + "!";
    }

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable String currentUser, @RequestBody Atmosphere input) {
        System.out.println(input.toString());
        return ResponseEntity.ok().build();
    }

    // WEATHER QUERYING SERVICES
    // =================================================================================================================

    @RequestMapping(path = "/{currentUser}", method = RequestMethod.GET)
    ResponseEntity<?> userDashboard(@PathVariable String currentUser) {
        LOGGER.info("User {} has accessed the dashboard", currentUser);

        ArrayList queryResults = new ArrayList();

        Account acc = accountRepository.findByUsername(currentUser);

        //If account doesn't exist, stop execution right here
        if(acc == null) return ResponseEntity.notFound().build();

        for(String location : acc.getSubscribedLocations()){

            Query query = null;
            try {
                query = YahooWeatherForecastService.getInstance().makeQuery(location);
            } catch (Exception e) {
                LOGGER.error(e.toString());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

            if (query.getAdditionalProperties() != null && query.getAdditionalProperties().get("query") != null) {

                Query queryResult = query.getAdditionalProperties().get("query");

                if (queryResult.getResults() != null) {
                    queryResults.add(queryResult.getResults());
                }
            }
        }

        return new ResponseEntity<List<Results>>(queryResults, HttpStatus.OK);
    }

    // LOCATION MANAGEMENT SERVICES
    // =================================================================================================================

    @RequestMapping(path = "/{currentUser}/subscription/location", method = RequestMethod.POST)
    ResponseEntity<?> locationAdd(@PathVariable String currentUser, @RequestBody Location input) {
        LOGGER.info("User {} has requested for locations to be added to his account", currentUser);

        //First check if requested account exists
        Account acc = accountRepository.findByUsername(currentUser);
        if(acc == null) return ResponseEntity.badRequest().build();

        //If account exists, check that it isn't already subscribed to requested location
        if(acc.getSubscribedLocations().contains(input.getName())) return ResponseEntity.status(HttpStatus.CONFLICT).build();

        //If account isn't already subscribed to location, check that it is valid before making the subscription
        Query query;
        try {
            query = YahooWeatherForecastService.getInstance().makeQuery(input.getName());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        LOGGER.debug("Query results for location {}", input.getName());
        LOGGER.debug(query.toString());

        if (query.getAdditionalProperties() != null && query.getAdditionalProperties().get("query") != null) {

            Query queryResult = query.getAdditionalProperties().get("query");

            if (queryResult.getResults() != null) {
                //At this point, location is guaranteed to be valid. Should be stored into Account's subscription list
                acc.getSubscribedLocations().add(input.getName());
                accountRepository.save(acc);

                URI location = ServletUriComponentsBuilder
                        .fromPath("/dashboard/{currentUser}")
                        .buildAndExpand(currentUser).toUri();

                return ResponseEntity.created(location).build();
            }
        }

        return ResponseEntity.notFound().build();
    }

    @RequestMapping(path = "/{currentUser}/subscription/location", method = RequestMethod.DELETE)
    ResponseEntity<?> locationDelete(@PathVariable String currentUser, @RequestBody Location input) {
        LOGGER.info("User {} has requested for locations to be deleted from his account", currentUser);

        //First check if requested account exists
        Account acc = accountRepository.findByUsername(currentUser);
        if(acc == null) return ResponseEntity.badRequest().build();

        //If account exists, check that it is subscribed to requested location
        if(!acc.getSubscribedLocations().contains(input.getName())) return ResponseEntity.notFound().build();

        //Account is subscribed to location. Remove and save
        acc.getSubscribedLocations().remove(input.getName());
        accountRepository.save(acc);

        return ResponseEntity.ok().build();
    }

    // ACCOUNT MANAGEMENT SERVICES
    // =================================================================================================================

    @RequestMapping(path = "/{currentUser}/account/add", method = RequestMethod.POST)
    ResponseEntity<?> accountAdd(@PathVariable String currentUser, @RequestBody Account input) throws Exception {
        LOGGER.info("User {} attempts to create account [{}]", currentUser, input);

        if(accountRepository.findByUsername(input.getUsername()) != null){
            LOGGER.debug("An account with the username {} already exists in the database", input.getUsername());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        LOGGER.debug("Account doesn't already exist. Creating...");
        accountRepository.save(input);

        URI location = ServletUriComponentsBuilder
                .fromPath("/dashboard/{addedUser}")
                .buildAndExpand(input.getUsername()).toUri();

        return ResponseEntity.created(location).build();
    }

}