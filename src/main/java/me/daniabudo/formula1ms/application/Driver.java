package me.daniabudo.formula1ms.application;

public class Driver implements Presentable {
    String name;
    Integer id;
    String nationality;
    Integer teamId;

    public Driver(String name, Integer id, String nationality, Integer teamId) {
        this.name = name;
        this.id = id;
        this.nationality = nationality;
        this.teamId = teamId;
    }

    public Driver(String name, Integer id, String nationality) {
        this(name, id, nationality, null);
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public String getNationality() {
        return nationality;
    }

    public Integer getTeamId() {
        return teamId;
    }

    @Override
    public String getPresentation() {
        return String.format("Hello! I am a %s driver! My name is %s%s.",
                nationality,
                name,
                teamId != null ? " and I belong to team ID " + teamId : "");
    }

    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s, Nationality: %s%s",
                id,
                name,
                nationality,
                teamId != null ? ", Team ID: " + teamId : "");
    }
}
