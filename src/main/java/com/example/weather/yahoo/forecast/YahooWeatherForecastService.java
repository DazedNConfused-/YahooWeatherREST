package com.example.weather.yahoo.forecast;

import com.example.weather.yahoo.Query;
import com.example.weather.yahoo.location.Location;
import com.example.weather.yahoo.yql.YQLClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YahooWeatherForecastService {

    private static volatile YahooWeatherForecastService instance;
    private static final Object mutex = new Object();

    private Logger LOGGER;

    private static final String YQL_LOCATION_NAME = "<YQL_LOCATION_NAME>";
    private String YQL_WEATHER_QUERY_BY_LOCATION = "select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\""+YQL_LOCATION_NAME+"\")";

    private static final String YQL_LOCATION_WOEID = "<YQL_LOCATION_WOEID>";
    private String YQL_WEATHER_QUERY_BY_WOEID = "select * from weather.forecast where woeid in ("+YQL_LOCATION_WOEID+")";

    //Singleton Constructor
    private YahooWeatherForecastService() {
        LOGGER = LoggerFactory.getLogger(YahooWeatherForecastService.class);
    }

    //Singleton Instance
    public static YahooWeatherForecastService getInstance() {
        YahooWeatherForecastService result = instance;
        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null)
                    instance = result = new YahooWeatherForecastService();
            }
        }
        return result;
    }

    /** Returns a Query to Yahoo's Weather API */
    public Query makeQuery(String location) throws Exception {
        YQLClient client = new YQLClient();

        String jsonBody = client.getQuery(YQL_WEATHER_QUERY_BY_LOCATION.replace(YQL_LOCATION_NAME, location));

        LOGGER.trace("makeQuery() - Results:");
        LOGGER.trace("{}", jsonBody);

        ObjectMapper mapper = new ObjectMapper();
        Query result = mapper.readValue(jsonBody, Query.class);

        return result;
    }

    /** Returns a Query to Yahoo's Weather API */
    public Query makeQuery(Location location) throws Exception {
        YQLClient client = new YQLClient();

        String jsonBody = client.getQuery(YQL_WEATHER_QUERY_BY_WOEID.replace(YQL_LOCATION_WOEID, location.getWoe()));

        LOGGER.trace("makeQuery() - Results:");
        LOGGER.trace("{}", jsonBody);

        ObjectMapper mapper = new ObjectMapper();
        Query result = mapper.readValue(jsonBody, Query.class);

        return result;
    }

}
