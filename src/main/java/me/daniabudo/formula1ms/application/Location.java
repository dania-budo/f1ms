package me.daniabudo.formula1ms.application;

public class Location {
    String country;
    public String city;
    public String street;
    public Integer number;

    // Constructor to initialize location attributes
    public Location(String country, String city, String street, Integer number) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.number = number;
    }

    @Override
    public String toString() {
        return String.format("%d %s, %s, %s", number, street, city, country);
    }

    // Getter for the country field
    public String getCountry() {
        return country;
    }
}
