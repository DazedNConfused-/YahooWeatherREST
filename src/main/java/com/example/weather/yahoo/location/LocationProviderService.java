package com.example.weather.yahoo.location;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class LocationProviderService {

    private static volatile LocationProviderService instance;
    private static final Object mutex = new Object();

    private LocationProviderService() {
    }

    public static LocationProviderService getInstance() {
        LocationProviderService result = instance;
        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null)
                    instance = result = new LocationProviderService();
            }
        }
        return result;
    }

    /**
     * Get (almost all? a lot of?) possible weather locations by querying Yahoo's services alphabetically.
     * <p>
     * This method was made by reverse-engineering how the site 'https://login.yahoo.com/account/preferences' operates,
     * by checking the ajax calls made in the background using the browser's debugging tools.
     * <p>
     * By returning a Set we avoid any possible duplicates in the returned result, and by using a Tree implementation we
     * order the results as they are retrieved from the endpoint.
     *
     * @apiNote Since this implementation makes a GET call for every letter in the alphabet, the performance is terrible.
     * It should be deprecated and replaced for a more direct approach (a single API call that retrieves all possible
     * weather locations perhaps), whenever one is found.
     */
    public Set<Location> getLocations() {

        Set<Location> result = new TreeSet<>(new AlphabeticalComparator());

        LocationProviderClient client = new LocationProviderClient();

        for (char alphabet = 'a'; alphabet <= 'z'; alphabet++) {

            //query all possible weather locations by alphabet
            String clientResult = client.getQuery(String.valueOf(alphabet));

            //split resulting page's body into processable elements
            String[] list = clientResult.split("(<li class=\"autosuggest-item\">)|(</li>)");

            for (String item : list) {
                //Ignore non-<li> elements (like script or header elements)
                if (item.contains("<a href=\"#\" class=\"autosuggest-lnk ellipsis\"")) {

                    //clean <li> element as much as possible
                    String liElement = item.replace("\n", "").replace("\t", "").replace("<a href=\"#\" class=\"autosuggest-lnk ellipsis\"", "").replace("</a>", "").trim();

                    //get location name from title
                    String locationName = liElement.substring(liElement.indexOf("title=\"") + 7, liElement.length());
                    locationName = locationName.substring(0, locationName.indexOf("\""));

                    //get woe (id) from data-value
                    String locationWoe = liElement.substring(liElement.indexOf("data-value=\"woe|") + 16, liElement.length());
                    locationWoe = locationWoe.substring(0, locationWoe.indexOf("|"));

                    Location location = new Location(locationName, locationWoe);
                    result.add(location);
                }
            }

        }

        return result;
    }

    /** Allows for alphabetical sorting of Locations */
    class AlphabeticalComparator implements Comparator<Location> {
        @Override
        public int compare(Location o1, Location o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }

}
