public class TwoLeggedKnockoutTie implements KnockoutTie {
    private String team1;
    private String team2;
    private Match first_leg;
    private Match second_leg;
    private boolean after_extra_time;
    private int winning_team;

    public TwoLeggedKnockoutTie(String a, String b, int level) {
        this.team1 = a;
        this.team2 = b;
        this.first_leg = new Match();
        this.first_leg.home_id = team2;
        this.first_leg.away_id = team1;
        this.first_leg.away_team = Sim.getClub(team1).getName();
        this.first_leg.home_team = Sim.getClub(team2).getName();
        this.first_leg.setTournamentLevel(level);
        this.second_leg = new Match();
        this.second_leg.home_id = team1;
        this.second_leg.away_id = team2;
        this.second_leg.away_team = Sim.getClub(team2).getName();
        this.second_leg.home_team = Sim.getClub(team1).getName();
        this.second_leg.setTournamentLevel(level);
        this.after_extra_time = false;
    }

    public void simulate() {
        this.first_leg.simulate();
        this.second_leg.simulate();
        this.setWinner();
    }

    public void setWinner() {
        int team_1_goals = this.first_leg.away_team_goals + this.second_leg.home_team_goals;
        int team_2_goals = this.second_leg.away_team_goals + this.first_leg.home_team_goals;

        if (team_1_goals > team_2_goals) {
            this.winning_team = 1;
        } else if (team_2_goals > team_1_goals) {
            this.winning_team = 2;
        } else {
            if (this.first_leg.away_team_goals > this.second_leg.away_team_goals) {
                this.winning_team = 1;
            } else if (this.first_leg.away_team_goals < this.second_leg.away_team_goals) {
                this.winning_team = 2;
            } else {
                this.after_extra_time = true;
                Match match = new Match();
                match.home_id = team2;
                match.away_id = team1;
                match.away_team = Sim.getClub(team1).getName();
                match.home_team = Sim.getClub(team2).getName();
                match.simulate();
                if (match.home_team_goals > match.away_team_goals) {
                    this.winning_team = 2;
                } else if (match.away_team_goals > match.home_team_goals) {
                    this.winning_team = 1;
                } else {
                    this.winning_team = CommonFunctions.getRandomNumber(2) % 2 == 0 ? 1 : 2;
                }
            }
        }
    }

    public int getTeam1Goals() {
        return this.first_leg.away_team_goals + this.second_leg.home_team_goals;
    }

    public int getTeam2Goals() {
        return this.second_leg.away_team_goals + this.first_leg.home_team_goals;
    }

    public String getWinner() {
        if (this.winning_team == 1) {
            return this.team1;
        } else {
            return this.team2;
        }
    }

    public String getLoser() {
        if (this.winning_team != 1) {
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

    public void showAggregate() {
        String agg = this.first_leg.away_team + " " + this.getTeam1Goals() + "-" + this.getTeam2Goals() + " "
                + this.first_leg.home_team;
        if (this.after_extra_time) {
            System.out.println(agg + " (a.e.t.)");
        } else {
            System.out.println(agg);
        }
    }

    public Match getFirstLeg() {
        return this.first_leg;
    }

    public Match getSecondLeg() {
        return this.second_leg;
    }

    public void show() {
        // System.out.println();
        // this.first_leg.display();
        // this.second_leg.display();
        this.showAggregate();
    }

    public void showContinental() {
        Club_Team t1 = Sim.getClub(this.first_leg.away_id);
        String st1 = t1.getName() + "(" + Sim.getNation(t1.getNationId()).getCode() + ")";
        Club_Team t2 = Sim.getClub(this.first_leg.home_id);
        String st2 = t2.getName() + "(" + Sim.getNation(t2.getNationId()).getCode() + ")";
        String agg = st1 + " " + this.getTeam1Goals() + "-" + this.getTeam2Goals() + " " + st2;
        if (this.after_extra_time) agg += " (a.e.t.)";
        System.out.println(agg);
    }

    public StageType getType() {
        return StageType.TWO_LEGGED_KNOCKOUT;
    }
}
