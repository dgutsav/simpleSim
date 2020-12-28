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
    private List<CompetitionHistory> competitionHistory;
    private int nation_id;

    public League() {
        this.name = "";
        this.competition_level = 0;
        this.promotion_places = 0;
        this.relegation_places = 0;
        this.nation_id = 0;
        this.teams = new ArrayList<>();
        this.fixtures = new ArrayList<>();
        this.competitionHistory = new ArrayList<>();
    }

    public League(String name, int level, int promotion, int relegation, int nation_id) {
        this.name = name;
        this.competition_level = level;
        this.promotion_places = promotion;
        this.relegation_places = relegation;
        this.nation_id = nation_id;
        this.teams = new ArrayList<>();
        this.fixtures = new ArrayList<>();
        this.competitionHistory = new ArrayList<>();
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

    public void removeTeam(String team_to_remove) {
        this.teams.remove(team_to_remove);
    }

    public List<String> getTeams() {
        return this.teams;
    }

    public int getTotalMatchDays() {
        int matches_per_team = this.teams.size() - 1;
        if (this.teams.size() > 12) {
            return matches_per_team * 2;
        } else {
            return matches_per_team * 4;
        }
    }

    public void setFixtures() {
        fixtures = FixtureScheduler.scheduleFixtures(this.teams, true);
    }

    public void setFixtures(List<Match> fixtures) {
        this.fixtures = fixtures;
    }

    public void setupTable() {
        this.table = new LeagueTable(this.teams.size());
        this.table.startNewSeason(this.teams);
    }

    public void seasonStart() {
        this.setFixtures();
        this.setupTable();
    }

    public LeagueTable getTable() {
        return this.table;
    }

    public List<Match> getMatchesForMatchDay(int matchday) {
        List<Match> matches = new ArrayList<>();
        for (Match match : fixtures) {
            if (match.matchday == matchday) {
                matches.add(match);
            }
        }
        return matches;
    }

    public void simulateMatchDay(int matchday) {
        List<Match> matches = getMatchesForMatchDay(matchday);
        for (Match match : matches) {
            match.simulate(Sim.getTeam(match.home_id), Sim.getTeam(match.away_id));
            if (!Sim.TEST) {
                match.display();
            }
            table.updateTeamPerformance(match.home_id, match.home_team_goals, match.away_team_goals);
            table.updateTeamPerformance(match.away_id, match.away_team_goals, match.home_team_goals);
        }
    }

    public void simulateSeason() {
        if (!Sim.TEST) {
            System.out.println("\n" + this.name);
        }
        for (int i = 1; i <= this.getTotalMatchDays(); i++) {
            if (!Sim.TEST) {
                System.out.println("Matchday " + i);
            }
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

    public List<String> getPromotedTeams() {
        List<String> promoted = new ArrayList<>();
        for (int i = 1; i <= this.promotion_places; i++) {
            promoted.add(this.getTeamAtRank(i));
        }
        return promoted;
    }

    public List<String> getRelegatedTeams() {
        List<String> demoted = new ArrayList<>();
        for (int i = this.teams.size(); i > this.teams.size() - this.relegation_places; i--) {
            demoted.add(this.getTeamAtRank(i));
        }
        return demoted;
    }

    public boolean isTeamInLeague(String team_id) {
        if (this.teams.contains(team_id)) {
            return true;
        } else {
            return false;
        }
    }

    public void saveCompetitionHistory() {
        List<TeamHistory> histories = new ArrayList<>();
        for (int i = 1; i <= this.teams.size(); i++) {
            int rating = (int) Sim.getTeam(this.getTeamAtRank(i)).getBestSquadRating();
            TeamHistory history = new TeamHistory(this.getTeamAtRank(i), i, rating);
            histories.add(history);
        }
        CompetitionHistory compHistory = new CompetitionHistory(Sim.getSeasonNumber(), histories);
        this.competitionHistory.add(compHistory);
    }

    public void showCompetitionHistory() {
        System.out.println(this.getName());
        for (CompetitionHistory compHistory : this.competitionHistory) {
            compHistory.show(3);
        }
    }

    public int getNationId() {
        return this.nation_id;
    }

    public static int getPyramidPlaces(int size) {
        return size / 6;
    }
}
