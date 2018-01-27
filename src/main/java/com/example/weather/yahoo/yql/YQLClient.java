package com.example.weather.yahoo.yql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class YQLClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(YQLClient.class);

    private static final String URL = "http://query.yahooapis.com/v1/public/yql";

    public YQLClient() {
    }

    /*
     * Must take a different approach in building the request to Yahoo's API, since the parameters can't be passed
     * as part of the URL (results in a 400 - bad request).
     *
     * https://stackoverflow.com/a/47524129
     * */
    public String getQuery(String yql) {
        LOGGER.trace("YQLClient - getQuery() with value {}", yql);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("q", yql)
                .queryParam("format", "json");

        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<String> response = restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.GET,
                entity,
                String.class);

        return response.getBody();
    }

}