package com.example.weather.controller;

import com.example.weather.auth.entity.Account;
import com.example.weather.yahoo.Atmosphere;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/dashboard/{currentUser}")
class MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

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



    /* ACCOUNT MANAGEMENT */

    @RequestMapping(path = "/account/add", method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable String currentUser, @RequestBody Account input) {
        LOGGER.info("User {} has created account [{}]", currentUser, input);

        URI location = ServletUriComponentsBuilder
                .fromPath("/dashboard/{addedUser}")
                .buildAndExpand(input.getUsername()).toUri();

        return ResponseEntity.created(location).build();
    }
}