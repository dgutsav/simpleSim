import java.util.ArrayList;
import java.util.List;

class SimpleLeague {
    private List<String> teams;
    private Table table;
    private List<Match> fixtures;
    private int rr_count; //round-robin count

    public SimpleLeague() {
        this.teams = new ArrayList<>();
        this.fixtures = new ArrayList<>();
        this.rr_count = 1;
    }

    public void setTeams(List<String> teams) {
        this.teams = teams;
    }

    public List<String> getTeams() { return this.teams; }

    public void setRRCount(int count) { this.rr_count = count; }

    public void addTeam(String team_id) {
        teams.add(team_id);
    }

    public void removeTeam(int position) {
        teams.remove(table.getTeamAtRank(position));
    }

    public void removeTeam(String team_to_remove) {
        this.teams.remove(team_to_remove);
    }

    public void setFixtures() {
        fixtures = FixtureScheduler.scheduleFixtures(this.teams, true);
    }

    public void setFixtures(List<Match> fixtures) {
        this.fixtures = fixtures;
    }

    public List<Match> getFixtures() {
        return this.fixtures;
    }

    public void setupTable() {
        this.table = new Table(this.teams.size(), false);
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
        displayConditional("Matchday " + matchday);
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

    public void simulate() {
        int matchdays = this.teams.size() % 2 == 0 ? (this.teams.size()  - 1) * (this.rr_count * 2) : this.teams.size() * (this.rr_count * 2);
        for (int i = 1; i <= matchdays; i++) {
            this.simulateMatchDay(i);
        }
    }

    public String getWinner() {
        return table.getTeamAtRank(1);
    }

    public String getTeamAtRank(int rank) {
        return table.getTeamAtRank(rank);
    }

    public int getRankOfTeam(String team_id) {
        return table.getRankOfTeam(team_id);
    }

    private void displayConditional(String x) {
        if (!Sim.TEST) System.out.println(x);
    }
}