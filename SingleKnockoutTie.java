public class SingleKnockoutTie implements KnockoutTie {

    private String team1;
    private String team2;
    private Match match;
    private boolean after_extra_time;
    private int winning_team;

    public SingleKnockoutTie(String a, String b, int level) {
        this.team1 = a;
        this.team2 = b;
        this.match = new Match();
        this.match.home_id = team1;
        this.match.away_id = team2;
        this.match.away_team = Sim.getClub(team2).getName();
        this.match.home_team = Sim.getClub(team1).getName();
        this.match.setTournamentLevel(level);
        this.after_extra_time = false;
    }

    public SingleKnockoutTie(String a, String b) {
        this.team1 = a;
        this.team2 = b;
        this.match = new Match();
        this.match.home_id = team1;
        this.match.away_id = team2;
        this.match.away_team = Sim.getClub(team2).getName();
        this.match.home_team = Sim.getClub(team1).getName();
        this.match.setTournamentLevel(1);
        this.after_extra_time = false;
    }

    public void simulate() {
        this.match.simulate();
        this.setWinner();
    }

    public void setWinner() {
        int team_1_goals = this.match.home_team_goals;
        int team_2_goals = this.match.away_team_goals;

        if (team_1_goals > team_2_goals) {
            this.winning_team = 1;
        } else if (team_2_goals > team_1_goals) {
            this.winning_team = 2;
        } else {
            this.after_extra_time = true;
            Match extra = new Match();
            extra.home_id = team2;
            extra.away_id = team1;
            extra.away_team = Sim.getClub(team1).getName();
            extra.home_team = Sim.getClub(team2).getName();
            extra.simulate();
            if (extra.home_team_goals > extra.away_team_goals) {
                this.winning_team = 2;
            } else if (extra.away_team_goals > extra.home_team_goals) {
                this.winning_team = 1;
            } else {
                this.winning_team = CommonFunctions.getRandomNumber(2) % 2 == 0 ? 1 : 2;
            }
        }
    }

    public int getTeam1Goals() {
        return this.match.home_team_goals;
    }

    public int getTeam2Goals() {
        return this.match.away_team_goals;
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

    public Match getMatch() {
        return this.match;
    }

    public void show() {
        String str = (this.match.home_team + " " + this.match.home_team_goals + "-" + this.match.away_team_goals + " " + this.match.away_team);
        if (this.after_extra_time) str+= "(a.e.t.)";
        System.out.println(str);
    }

    public void showContinental() {
        Club_Team t1 = Sim.getClub(this.match.home_id);
        String st1 = t1.getName() + "(" + Sim.getNation(t1.getNationId()).getCode() + ")";
        Club_Team t2 = Sim.getClub(this.match.away_id);
        String st2 = t2.getName() + "(" + Sim.getNation(t2.getNationId()).getCode() + ")";
        String agg = st1 + " " + this.getTeam1Goals() + "-" + this.getTeam2Goals() + " " + st2;
        if (this.after_extra_time)
            agg += " (a.e.t.)";
        System.out.println(agg);
    }

    public StageType getType() {
        return StageType.KNOCKOUT;
    }
}
