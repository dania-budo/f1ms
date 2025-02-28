package me.daniabudo.formula1ms.application;

import java.time.LocalDateTime;

public class Race implements Presentable, Comparable<Race> {
    Integer id;
    String name;
    LocalDateTime date;
    Integer points;

    public Race(Integer id, String name, LocalDateTime date, Integer points) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.points = points;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Integer getPoints() {
        return points;
    }

    @Override
    public String getPresentation() {
        return String.format("The %s will take place on %s", name, date);
    }

    @Override
    public int compareTo(Race other) {
        return Integer.compare(this.points, other.points);
    }

    @Override
    public String toString() {
        return String.format("Race ID: %d, Name: %s, Date: %s, Points: %d",
                id, name, date, points);
    }
}
