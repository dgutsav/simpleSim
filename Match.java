public class Match {
    String home_id;
    String away_id;
    String home_team;
    String away_team;
    int home_team_goals = 0;
    int away_team_goals = 0;
    int matchday;
    boolean simulated = false;
    boolean isNeutral = false;
    int tournament_level = 0;

    public boolean isSimulated() {
        return simulated;
    }

    public void afterSimulation() {
        this.simulated = true;
    }

    public void setTournamentLevel(int competition_level) {
        this.tournament_level = competition_level;
    }

    public int getTournamentLevel() {
        return this.tournament_level;
    }

    public void setNeutralMatch() {
        this.isNeutral = true;
    }

    public String getWinner(boolean cup) {
        if(this.home_team_goals > this.away_team_goals) {
            return this.home_id;
        } else if (this.home_team_goals < this.away_team_goals) {
            return this.away_id; 
        } else {
            if(cup) {
                this.simulated = false;
                this.simulate(Sim.getTeam(away_id), Sim.getTeam(home_id));
                return this.getWinner(cup);
            } else {
                return "";
            }
        }
    }

    public void display() {
        if (simulated) {
            System.out.println(home_team + " " + home_team_goals + "-" + away_team_goals + " " + away_team);
        } else {
            System.out.println(home_team + " vs " + away_team);
        }
    }

    public void simulate(Team home, Team away) {
        if (!this.simulated && MatchSimulator.simulateMatch(this, home, away)) {
            this.home_team_goals = MatchSimulator.getHomeGoals();
            this.away_team_goals = MatchSimulator.getAwayGoals();
            this.afterSimulation();
        }
    }
}
