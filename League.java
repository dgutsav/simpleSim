import java.util.ArrayList;
import java.util.List;

public class League {
    private String name;
    private List<String> teams;
    private Table table;
    private int competition_level;
    private int promotion_places;
    private int relegation_places;
    private List<Match> fixtures;
    private String id;
    private List<CompetitionHistory> competitionHistory;
    private int nation_id;
    private boolean is_international = false;
    private int reputation;

    public League() {
        this.name = "";
        this.competition_level = 0;
        this.promotion_places = 0;
        this.relegation_places = 0;
        this.nation_id = 0;
        this.teams = new ArrayList<>();
        this.fixtures = new ArrayList<>();
        this.competitionHistory = new ArrayList<>();
        this.reputation = 0;
    }

    public League(String name, int level, int promotion, int relegation, int nation_id, int rating) {
        this.name = name;
        this.competition_level = level;
        this.promotion_places = promotion;
        this.relegation_places = relegation;
        this.nation_id = nation_id;
        this.teams = new ArrayList<>();
        this.fixtures = new ArrayList<>();
        this.competitionHistory = new ArrayList<>();
        this.reputation = (rating + 10) * 2;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setInternational() {
        this.is_international = true;
    }

    public boolean isInternational() {
        return this.is_international;
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
        this.table = new Table(this.teams.size(), this.isInternational());
        this.table.startNewSeason(this.teams);
    }

    public void seasonStart() {
        this.setFixtures();
        this.setupTable();
    }

    public Table getTable() {
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
            match.simulate();
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
        System.out.println(Sim.getClub(this.getWinner()).getName());
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
            int rating = (int) Sim.getClub(this.getTeamAtRank(i)).getBestSquadRating();
            TeamHistory history = new TeamHistory(this.getTeamAtRank(i), i, rating);
            histories.add(history);
        }
        CompetitionHistory compHistory = new CompetitionHistory(Sim.getSeasonNumber(), histories);
        this.competitionHistory.add(compHistory);
    }

    public void showCompetitionHistory() {
        System.out.println(this.getName());
        for (CompetitionHistory compHistory : this.competitionHistory) {
            compHistory.show(3, false);
        }
    }

    public int getNationId() {
        return this.nation_id;
    }

    public static int getPyramidPlaces(int size) {
        return size / 6;
    }

    public int getReputation() {
        return this.reputation;
    }

    public void endSeasonChanges() {
        this.updateTeamReputations();
        this.distributePrizeMoney();
    }

    public void updateTeamReputations() {
        int reputation_steps = (this.reputation / this.teams.size()) * 10;
        List<String> sorted_teams = CommonFunctions.sortByReputation(this.teams);
        for (int i = 0; i < this.teams.size(); i++) {
            String team = sorted_teams.get(i);
            int rank = this.table.getRankOfTeam(team);
            int diff = rank - (i + 1);
            if (diff < 3 || diff > -3) {
                continue;
            } else if (diff > 0) {
                Sim.getClub(team).addReputation(reputation_steps * (diff - 3) * -1);
            } else {
                Sim.getClub(team).addReputation(reputation_steps * ((diff * -1) + 3));
            }
        }
    }

    public void distributePrizeMoney() {
        long prize_money = this.reputation * 50000 * (long)(Math.pow(1.01, (this.reputation - 200))); 
        int size = this.teams.size();
        float[] shares = new float[size];
        for (int i = 0; i < size / 2; i++) {
            float multiplier = ((size / 2) - i) * 0.05f;
            shares[i] = 1 + multiplier;
            shares[size - i - 1] = 1 - multiplier;
        }
        for (int i = 1; i <= size; i++) {
            double change = (double) prize_money * shares[i - 1];
            Sim.getClub(this.getTeamAtRank(i)).updateBudget((long) change);
        }
    }

    public List<Match> getFixtures() {
        return this.fixtures;
    }
}
