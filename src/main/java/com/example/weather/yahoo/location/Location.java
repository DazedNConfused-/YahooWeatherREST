package com.example.weather.yahoo.location;

public class Location {

    private String name;
    private String woe;

    public Location(String name, String woe) {
        this.name = name;
        this.woe = woe;
    }

    //Overrides
    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", woe='" + woe + '\'' +
                '}';
    }

    //Getters & Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWoe() {
        return woe;
    }

    public void setWoe(String woe) {
        this.woe = woe;
    }
}
