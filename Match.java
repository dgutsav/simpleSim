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
    boolean is_youth_team_match = false;
    boolean is_international_match = false;

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
        if (this.home_team_goals > this.away_team_goals) {
            return this.home_id;
        } else if (this.home_team_goals < this.away_team_goals) {
            return this.away_id;
        } else {
            if (cup) {
                this.simulated = false;
                this.simulate();
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

    public void simulate() {
        Squad home, away;
        if (!this.simulated) {
            if (this.is_international_match) {
                home = Sim.getNationalTeam(this.home_id).getSquad();
                away = Sim.getNationalTeam(this.away_id).getSquad();
            } else {
                if (this.is_youth_team_match) {
                    home = Sim.getTeam(this.home_id).getYouthSquad();
                    away = Sim.getTeam(this.away_id).getYouthSquad();
                } else {
                    home = Sim.getTeam(this.home_id).getSeniorSquad();
                    away = Sim.getTeam(this.away_id).getSeniorSquad();
                }
            }
            if (MatchSimulator.simulateMatch(this, home, away)) {
                this.home_team_goals = MatchSimulator.getHomeGoals();
                this.away_team_goals = MatchSimulator.getAwayGoals();
                this.afterSimulation();
            }
        }
    }
}
