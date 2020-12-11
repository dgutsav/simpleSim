public class KnockoutTie {
    private String team1;
    private String team2;
    private Match first_leg;
    private Match second_leg;
    private boolean after_extra_time;
    private int winning_team;

    public KnockoutTie(String a, String b, int level) {
        this.team1 = a;
        this.team2 = b;
        this.first_leg = new Match();
        this.first_leg.home_id = team2;
        this.first_leg.away_id = team1;
        this.first_leg.away_team = Sim.getTeam(team1).getName();
        this.first_leg.home_team = Sim.getTeam(team2).getName();
        this.first_leg.setTournamentLevel(level);
        this.second_leg = new Match();
        this.second_leg.home_id = team1;
        this.second_leg.away_id = team2;
        this.second_leg.away_team = Sim.getTeam(team2).getName();
        this.second_leg.home_team = Sim.getTeam(team1).getName();
        this.second_leg.setTournamentLevel(level);
        this.after_extra_time = false;
    }

    public void simulate() {
        this.first_leg.simulate(Sim.getTeam(this.first_leg.home_id), Sim.getTeam(this.first_leg.away_id));
        this.second_leg.simulate(Sim.getTeam(this.second_leg.home_id), Sim.getTeam(this.second_leg.away_id));
        this.setWinner();
        this.show();
    }

    public void setWinner() {
        int team_1_goals = this.first_leg.away_team_goals + this.second_leg.home_team_goals;
        int team_2_goals = this.second_leg.away_team_goals + this.first_leg.home_team_goals;

        if (team_1_goals > team_2_goals) {
            this.winning_team = 1;
        } else if (team_2_goals > team_1_goals) {
            this.winning_team = 2;
        } else {
            if(this.first_leg.away_team_goals > this.second_leg.away_team_goals) {
                this.winning_team = 1;
            } else if(this.first_leg.away_team_goals < this.second_leg.away_team_goals) {
                this.winning_team = 2;
            } else {
                //need to add condition here
                this.after_extra_time = true;
                this.winning_team = 1;
            }
        }
    }

    public String getWinner() {
        if(this.winning_team == 1) {
            return this.team1;
        } else {
            return this.team2;
        }
    }

    public String getLoser() {
        if(this.winning_team != 1) {
            return this.team1;
        } else {
            return this.team2;
        }
    }

    public String getTeam1() {
        return this.team1;
    }

    public String getTeam2() {
        return this.team2;
    }

    public void show() {
        this.first_leg.display();
        this.second_leg.display();
    }
}
