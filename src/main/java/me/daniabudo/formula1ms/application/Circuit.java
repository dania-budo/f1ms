package me.daniabudo.formula1ms.application;

public class Circuit implements Staggering, Comparable<Circuit> {
    Integer id;
    String name;
    Double lengthKm;
    Integer laps;

    public Circuit(Integer id, String name, Double lengthKm, Integer laps) {
        this.id = id;
        this.name = name;
        this.lengthKm = lengthKm;
        this.laps = laps;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getLengthKm() {
        return lengthKm;
    }

    public Integer getLaps() {
        return laps;
    }

    @Override
    public String getDizzinessLevel() {
        return laps > 60 ? "The drivers will get dizzy!" : "The drivers will be chill, not dizzy!";
    }

    @Override
    public int compareTo(Circuit other) {
        return this.lengthKm.compareTo(other.lengthKm);
    }

    @Override
    public String toString() {
        return String.format("Circuit ID: %d, Name: %s, Length: %.2f km, Laps: %d, Dizziness: %s",
                id, name, lengthKm, laps, getDizzinessLevel());
    }

    public double getLength() {
        return lengthKm.doubleValue();
    }
}
