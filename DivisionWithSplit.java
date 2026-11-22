import java.util.List;

class DivisionWithSplit implements Division {
    private String name;
    private int comp_level;
    private int promotion_places;
    private int relegation_places;
    private String id;
    private List<CompetitionHistory> competitionHistory;
    private int nation_id;
    private int reputation;
    private int split_at;
    private int rounds_before_split;
    private int rounds_after_split;
    private boolean half_points;
    private SimpleLeague league;
    private SimpleLeague upper;
    private SimpleLeague lower;

    public DivisionWithSplit(String name, int level, int promotion_places, int relegation_places, int nation_id, int rating, int split_at, int before_split, int after_split) {
        super();
        this.name = name;
        this.comp_level = level;
        this.promotion_places = promotion_places;
        this.relegation_places = relegation_places;
        this.nation_id = nation_id;
        this.reputation = (rating + 10) * 2;
        this.split_at = split_at;
        this.rounds_before_split = before_split;
        this.rounds_after_split = after_split;
    }

    public void setId(String id) { this.id = id; }

    public String getId() { return this.id; }

    public String getName() { return this.name; }

    public int getLevel() { return this.comp_level; }
    
    public int getPromotionPlaces() { return this.promotion_places; }
    
    public int getRelegationPlaces() { return this.relegation_places; }

    public int getNation() { return this.nation_id; }

    public int getReputation() { return this.reputation; }

    public void setTeams(List<String> teams) { league.setTeams(teams); }

    public void startSeason() {
        league.setupTable();
        league.setFixtures();
    }   

    public void simulateSeason() {
        league.simulate();
    }

    public void setupPlayoffs() {
        //SPLiT AT for nth positiion of table upper included
        
    }

    public void endSeason() {

    }
}