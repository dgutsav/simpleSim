package Models;

import java.util.HashMap;
import java.util.Map;

import Functions.SimBettered;

public class Nation {
    private int id;
    private String name;
    private String code;
    private String national_team;
    private Map<Integer, String> leagues = new HashMap<>();
    private String cup;
    private int youth_rating;
    private int league_ranking;

    public Nation(int id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public void setLeagueRanking(int rank) {
        this.league_ranking = rank;
    }

    public int getLeagueRanking() {
        return this.league_ranking;
    }

    public void addLeague(int level, String league_id) {
        leagues.put(level, league_id);
    }

    public Map<Integer, String> getLeagues() {
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

    public String getNationalTeamId() {
        return this.national_team;
    }

    public NationalTeam getNationalTeam() {
        return SimBettered.getNationalTeam(this.national_team);
    }

    public void setNationalTeamId(String id) {
        this.national_team = id;
    }

    public String getCupId() {
        return this.cup;
    }

    public void setCupId(String cup_id) {
        this.cup = cup_id;
    }
}