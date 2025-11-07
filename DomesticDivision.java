import java.util.ArrayList;
import java.util.List;

class DomesticDivision implements Division {
    private String name;
    private int comp_level;
    private int promotion_places;
    private int relegation_places;
    private String id;
    private List<CompetitionHistory> competitionHistory;
    private int nation_id;
    private int reputation;
    private SimpleLeague league;

    public DomesticDivision(String name, int level, int promotion, int relegation, int rating, int nation_id) {
        this.name = name;
        this.comp_level = level;
        this.promotion_places = promotion;
        this.relegation_places = relegation;
        this.reputation = (rating + 10) * 2;
        this.nation_id = nation_id;
        this.competitionHistory = new ArrayList<>();
        this.league = new SimpleLeague();
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
        league.setFixtures();
        league.setupTable();
    }

    public void simulate() { 
        league.simulate(); 
        System.out.println("\n" + Sim.getSeasonNumber() + " " + name);
        league.getTable().showLong();
    }

    public List<String> getTeams() { return league.getTeams(); }

    public boolean isTeamInLeague(String team_id) { return league.getTeams().contains(team_id); }

    public List<String> getPromotedTeams() {
        List<String> promoted = new ArrayList<>();
        for (int i = 1; i <= this.promotion_places; i++) {
            promoted.add(league.getTeamAtRank(i));
        }
        return promoted;
    }

    public List<String> getRelegatedTeams() {
        List<String> demoted = new ArrayList<>();
        for (int i = this.getTeams().size(); i > this.getTeams().size() - this.relegation_places; i--) {
            demoted.add(league.getTeamAtRank(i));
        }
        return demoted;
    }

    public void saveCompetitionHistory() {
        List<TeamHistory> histories = new ArrayList<>();
        for (int i = 1; i <= this.getTeams().size(); i++) {
            int rating = (int) Sim.getClub(league.getTeamAtRank(i)).getBestSquadRating();
            TeamHistory history = new TeamHistory(league.getTeamAtRank(i), i, rating);
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

    public void updateTeamReputations() {
        int reputation_steps = (this.reputation / this.getTeams().size()) * 10; 
        List<String> sorted_teams = CommonFunctions.sortByReputation(this.getTeams());
        for (int i = 0; i < this.getTeams().size(); i++) {
            String team = sorted_teams.get(i);
            int rank = league.getRankOfTeam(team);
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
        long prize_money = this.reputation * 50000 * (long)(Math.pow(1.04, (this.reputation - 200))); 
        int size = this.getTeams().size();
        float[] shares = new float[size];
        for (int i = 0; i < size / 2; i++) {
            float multiplier = ((size / 2) - i) * 0.05f;
            shares[i] = 1 + multiplier;
            shares[size - i - 1] = 1 - multiplier;
        }
        for (int i = 1; i <= size; i++) {
            double change = (double) prize_money * shares[i - 1];
            Sim.getClub(league.getTeamAtRank(i)).updateBudget((long) change);
        }
    }

    public void endSeason() {
        saveCompetitionHistory();
        updateTeamReputations();
        distributePrizeMoney();
    }

    public String getTeamAtRank(int rank) {
        return league.getTable().getTeamAtRank(rank);    
    }
}