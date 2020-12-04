import java.util.ArrayList;
import java.util.List;

public class League {
    private String name;
    private List<String> teams;
    private LeagueTable table;
    private int competition_level;
    private int promotion_places;
    private int relegation_places;
    private List<Match> fixtures;
    private String id;

    public League(String name, int level, int promotion, int relegation) {
        this.name = name;
        this.competition_level = level;
        this.promotion_places = promotion;
        this.relegation_places = relegation;
        this.teams = new ArrayList<>();
        this.fixtures = new ArrayList<>();
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }
    
    public void addTeam(String team_id) {
        teams.add(team_id);
    }

    public void removeTeam(int position) {
        teams.remove(table.getTeamAtRank(position));
    }

    public void setFixtures() {
        fixtures = FixtureScheduler.scheduleFixtures(this.teams);
    }

    public void seasonStart() {
        this.table = new LeagueTable(this.teams.size());
        this.setFixtures();
        this.table.startNewSeason(this.teams);
    }

    public List<Match> getMatchesForMatchDay(int matchday) {
        List<Match> matches = new ArrayList<>();
        for(Match match : fixtures) {
            if(match.matchday == matchday) {
                matches.add(match);
            }
        }
        return matches;
    }

    public void simulateMatchDay(int matchday) {
        List<Match> matches = getMatchesForMatchDay(matchday);
        for(Match match : matches) {
            match.simulate(Sim.getTeam(match.home_id), Sim.getTeam(match.away_id));
            match.display();
            table.updateTeamPerformance(match.home_id, match.home_team_goals, match.away_team_goals);
            table.updateTeamPerformance(match.away_id, match.away_team_goals, match.home_team_goals);
        }
    }

    public void simulateSeason() {
        int total_matchdays = 2 * (teams.size() - 1);
        for(int i=1; i<=total_matchdays; i++) {
            this.simulateMatchDay(i);
        }
        this.table.showLong();
        System.out.println(Sim.getTeam(this.getWinner()).getName());
    }

    public String getWinner() {
        return table.getTeamAtRank(1);
    }

    public String getTeamAtRank(int rank) {
        return table.getTeamAtRank(rank);
    }

    public String getName() {
        return this.name;
    }

    public int getCompetitionLevel() {
        return this.competition_level;
    }

    public int getRelegationPlaces() {
        return this.relegation_places;
    }

    public int getPromotionPlaces() {
        return this.promotion_places;
    }
}
