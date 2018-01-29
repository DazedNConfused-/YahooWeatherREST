# Weather

A short programming exercise that queries Yahoo's Weather API, and
exposes a REST API in a simplistic, account-based approach using Spring Boot.

## Exercise Premise

The objective is to build an application about the weather that allows its users to register, associate localities of interest and visualize weather data from the selected site.

The application will interact with Yahoo Weather REST services: https://developer.yahoo.com/weather/

It must feature a username/password based login, and once logged display a dashboard with weather data from the user’s localities of interest. In case that the user logs in for the first time, he should be able to select one or more favorite locations.

The application’s main route must be like this: `/dashboards/currentUser/`

There will be three REST resources:

- API to insert new users.
- API to insert and remove locations.
- API to retrieve weather data from all localities for the dashboard.

## How-to Build

### Prerequisites

- JDK 1.8
- Maven 3 or later
- A local MySQL database, or Docker
  - The database connection properties are as follows:
    - **Database name**: DB_YahooWeatherREST
    - **Username**: weather
    - **Password**: weather2033
  - Alternatively, if **Docker** is present in the running machine, a
    database can be set up by running `OTHER/docker_config.sh` and
    afterwards creating the user-credentials and database with `OTHER/CREATE_DATABASE.sql`.

### Running

Enter the following command in the project's root folder:

`clean spring-boot:run -e`


## Exposed API

### Preliminary Notes

- All routes use `BasicAuth` underneath. Any access attempt with invalid
  credentials will result in a `401 : UNAUTHORIZED` error.
- The accessing `<user>` and the provided `BasicAuth` credentials must
  match. Otherwise, the petition will be rejected with (yet another)
  `401 : UNAUTHORIZED` error.

### Routes

```
http://<IP>:8081/dashboard/<user>
```

- Retrieves all weather info for the `<user>`'s subscribed locations.
- If the `<user>` is not subscribed to any locations, an empty JSON is returned.

**Example**: The user is subscribed to a single location: Buenos Aires.

```
[
    {
        "channel": {
            "units": {
                "distance": "mi",
                "pressure": "in",
                "speed": "mph",
                "temperature": "F"
            },
            "title": "Yahoo! Weather - Buenos Aires, Autonomous City of Buenos Aires, AR",
            "link": "http://us.rd.yahoo.com/dailynews/rss/weather/Country__Country/*https://weather.yahoo.com/country/state/city-468739/",
            "description": "Yahoo! Weather for Buenos Aires, Autonomous City of Buenos Aires, AR",
            "language": "en-us",
            "lastBuildDate": "Mon, 29 Jan 2018 12:43 AM ART",
            "ttl": "60",
            "location": {
                "city": "Buenos Aires",
                "country": "Argentina",
                "region": " Autonomous City of Buenos Aires"
            },
            "wind": {
                "chill": "73",
                "direction": "68",
                "speed": "25"
            },
            "atmosphere": {
                "humidity": "64",
                "pressure": "1012.0",
                "rising": "0",
                "visibility": "16.1"
            },
            "astronomy": {
                "sunrise": "6:11 am",
                "sunset": "8:3 pm"
            },
            "image": {
                "title": "Yahoo! Weather",
                "width": "142",
                "height": "18",
                "link": "http://weather.yahoo.com",
                "url": "http://l.yimg.com/a/i/brand/purplelogo//uh/us/news-wea.gif"
            },
            "item": {
                "title": "Conditions for Buenos Aires, Autonomous City of Buenos Aires, AR at 12:00 AM ART",
                "lat": "-34.614498",
                "long": "-58.44619",
                "link": "http://us.rd.yahoo.com/dailynews/rss/weather/Country__Country/*https://weather.yahoo.com/country/state/city-468739/",
                "pubDate": "Mon, 29 Jan 2018 12:00 AM ART",
                "condition": {
                    "code": "23",
                    "date": "Mon, 29 Jan 2018 12:00 AM ART",
                    "temp": "73",
                    "text": "Breezy"
                },
                "forecast": [
                    {
                        "code": "32",
                        "date": "29 Jan 2018",
                        "day": "Mon",
                        "high": "81",
                        "low": "70",
                        "text": "Sunny"
                    },
                    {
                        "code": "34",
                        "date": "30 Jan 2018",
                        "day": "Tue",
                        "high": "83",
                        "low": "71",
                        "text": "Mostly Sunny"
                    },
                    {
                        "code": "32",
                        "date": "31 Jan 2018",
                        "day": "Wed",
                        "high": "89",
                        "low": "72",
                        "text": "Sunny"
                    },
                    {
                        "code": "32",
                        "date": "01 Feb 2018",
                        "day": "Thu",
                        "high": "85",
                        "low": "73",
                        "text": "Sunny"
                    },
                    {
                        "code": "32",
                        "date": "02 Feb 2018",
                        "day": "Fri",
                        "high": "92",
                        "low": "71",
                        "text": "Sunny"
                    },
                    {
                        "code": "32",
                        "date": "03 Feb 2018",
                        "day": "Sat",
                        "high": "77",
                        "low": "64",
                        "text": "Sunny"
                    },
                    {
                        "code": "28",
                        "date": "04 Feb 2018",
                        "day": "Sun",
                        "high": "82",
                        "low": "73",
                        "text": "Mostly Cloudy"
                    },
                    {
                        "code": "30",
                        "date": "05 Feb 2018",
                        "day": "Mon",
                        "high": "84",
                        "low": "76",
                        "text": "Partly Cloudy"
                    },
                    {
                        "code": "32",
                        "date": "06 Feb 2018",
                        "day": "Tue",
                        "high": "88",
                        "low": "76",
                        "text": "Sunny"
                    },
                    {
                        "code": "32",
                        "date": "07 Feb 2018",
                        "day": "Wed",
                        "high": "89",
                        "low": "78",
                        "text": "Sunny"
                    }
                ],
                "description": "<![CDATA[<img src=\"http://l.yimg.com/a/i/us/we/52/23.gif\"/>\n<BR />\n<b>Current Conditions:</b>\n<BR />Breezy\n<BR />\n<BR />\n<b>Forecast:</b>\n<BR /> Mon - Sunny. High: 81Low: 70\n<BR /> Tue - Mostly Sunny. High: 83Low: 71\n<BR /> Wed - Sunny. High: 89Low: 72\n<BR /> Thu - Sunny. High: 85Low: 73\n<BR /> Fri - Sunny. High: 92Low: 71\n<BR />\n<BR />\n<a href=\"http://us.rd.yahoo.com/dailynews/rss/weather/Country__Country/*https://weather.yahoo.com/country/state/city-468739/\">Full Forecast at Yahoo! Weather</a>\n<BR />\n<BR />\n<BR />\n]]>",
                "guid": {
                    "isPermaLink": "false"
                }
            }
        }
    }
]
```

---


```
http://<IP>:8081/dashboard/<user>/subscription/location
```

- A `POST` request adds a location to the `<user>`'s location subscription.
  - Attempts to add an already subscribed location will result in a `409
    \: CONFLICT` error.
  - Attempts to add an invalid location will result in a `404 : NOT
    FOUND` error.
    - A location is validated against Yahoo's Weather services before
      being considered valid or not. A location that yields no result
      from Yahoo's API is considered invalid.
- A `DELETE` request removes a location to the `<user>`'s location
  subscription.
  - Attempts to remove a non-existent location will result in a `404 :
    NOT FOUND` error.
- If the `<user>` is not subscribed to any locations, an empty JSON is returned.
- The location is defined as JSON in the request's body:
```
{
  "name":"BUENOS AIRES"
}
```

---


```
http://<IP>:8081/dashboard/<user>/account
```

- A `POST` request creates a new user account within the application. It
  won't have any associated locations yet, they must be assigned calling
  the corresponding API routes.
  - Attempts to add an already existing account (meaning, the username
    already exists in the database) will result in a `409 : CONFLICT`
    error.

## Acknowledgements

- http://www.jsonschema2pojo.org/ for providing a way to generate POJO's
  from Yahoo's Weather API's JSON results.

## TODO List

The following are potential desirable upgrades that could be done to improve the application:

- [ ] Separate Entities from POJOs.
- [ ] Implement Servlet and Views (that consume the REST's API data), in
      order to provide a GUI to a user that access the application from
      a browser.

## Licence

MIT