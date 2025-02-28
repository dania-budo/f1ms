package me.daniabudo.formula1ms.application;

public class Team implements Presentable {
    Location location;
    String name;
    Integer id;
    String principal;

    public Team(Location location, String name, String principal, Integer id) {
        this.location = location;
        this.name = name;
        this.principal = principal;
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public String getPrincipal() {
        return principal;
    }

    @Override
    public String getPresentation() {
        return String.format("We are %s! Our principal is %s and we are located in %s.", name, principal, location);
    }

    @Override
    public String toString() {
        return String.format("Team ID: %d, Name: %s, Principal: %s, Location: %s",
                id, name, principal, location);
    }
}
