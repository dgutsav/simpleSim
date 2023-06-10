public class TeamPerformance {
    private String team_id;
    private String team_name;
    private int matches_won;
    private int matches_drawn;
    private int matches_lost;
    private int goals_conceded;
    private int goals_scored;

    // Constants
    private static final int POINTS_FOR_WIN = 3;
    private static final int POINTS_FOR_DRAW = 1;
    private static final int POINTS_FOR_LOSS = 0;

    public TeamPerformance(String id, String name) {
        this.team_id = id;
        this.team_name = name;
        this.matches_won = 0;
        this.matches_drawn = 0;
        this.matches_lost = 0;
        this.goals_conceded = 0;
        this.goals_scored = 0;
    }

    public int goalDifference() {
        return this.goals_scored - this.goals_conceded;
    }

    public int matchesPlayed() {
        return this.matches_won + this.matches_drawn + this.matches_lost;
    }

    public int points() {
        return (this.matches_won * POINTS_FOR_WIN) + (this.matches_drawn * POINTS_FOR_DRAW)
                + (this.matches_lost * POINTS_FOR_LOSS);
    }

    public int matchesWon() {
        return this.matches_won;
    }

    public int matchesDrawn() {
        return this.matches_drawn;
    }

    public int matchesLost() {
        return this.matches_lost;
    }

    public int goalsScored() {
        return this.goals_scored;
    }

    public int goalsConceded() {
        return this.goals_conceded;
    }

    public String displayLong() {
        return (CommonFunctions.getPaddedString(this.team_name, 35) + "\t" + this.matches_won + "-" + this.matches_drawn
                + "-" + this.matches_lost + "\t" + this.goals_scored + ":" + this.goals_conceded + "\t"
                + this.points());
    }

    public String displayLongContinental() {
        String code = Sim.getNation(Sim.getClub(this.team_id).getNationId()).getCode();
        return (CommonFunctions.getPaddedString(this.team_name + "(" + code + ")", 35) + "\t" + this.matches_won + "-" + this.matches_drawn
                + "-" + this.matches_lost + "\t" + this.goals_scored + ":" + this.goals_conceded + "\t"
                + this.points());
    }

    public String displayShort() {
        return this.team_name + "\t" + this.matchesPlayed() + "  " + this.goalDifference() + "  " + this.points();
    }

    public void updatePerformance(int scored, int against) {
        this.goals_scored += scored;
        this.goals_conceded += against;
        if (scored > against) {
            this.matches_won++;
        } else if (scored < against) {
            this.matches_lost++;
        } else {
            this.matches_drawn++;
        }
    }

    public String getTeamId() {
        return this.team_id;
    }
}
