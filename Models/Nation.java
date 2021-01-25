package Models;

import java.util.List;

public class Nation {
    private int id;
    private String name;
    private String code;
    private String national_team;
    private List<String> leagues;
    private String cup;
    private int youth_rating;

    public Nation(int id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public void addLeague(String league_id) {
        leagues.add(league_id);
    }

    public List<String> getLeagues() {
        return leagues;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }

    public int getYouthRating() {
        return this.youth_rating;
    }
}