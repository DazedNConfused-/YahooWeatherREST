package com.example.weather.location;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class LocationProviderClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationProviderClient.class);

    private String server = "https://login.yahoo.com/account/module/preferences/xhr?sourceType=gossip&q=<LOCATION>&action=&lat=&lon=";
    private RestTemplate rest;
    private HttpHeaders headers;
    private HttpStatus status;

    public LocationProviderClient() {
        this.rest = new RestTemplate();
        this.headers = new HttpHeaders();
        //headers.add("Content-Type", "application/json");
        headers.add("Accept", "*/*");
    }

    public String getQuery(String locationQuery) {
        LOGGER.trace("LocationProviderClient - getQuery() with value {}", locationQuery);
        HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);
        ResponseEntity<String> responseEntity = rest.exchange(server.replace("<LOCATION>", locationQuery), HttpMethod.GET, requestEntity, String.class);
        this.setStatus(responseEntity.getStatusCode());
        return responseEntity.getBody();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}