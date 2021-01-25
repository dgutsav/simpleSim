package Stages.Helpers;

public class Performance {
    public String team_id;
    public int home_wins;
    public int away_wins;
    public int home_draws;
    public int away_draws;
    public int home_losses;
    public int away_losses;
    public int home_goals_scored;
    public int away_goals_scored;
    public int home_goals_conceded;
    public int away_goals_conceded;

    private static int POINTS_FOR_WIN = 3;
    private static int POINTS_FOR_DRAW = 1;
    private static int POINTS_FOR_LOSS = 0;

    public Performance(String id) {
        this.team_id = id;
        this.home_wins = 0;
        this.away_wins = 0;
        this.home_draws = 0;
        this.away_draws = 0;
        this.home_losses = 0;
        this.away_losses = 0;
        this.home_goals_scored = 0;
        this.away_goals_scored = 0;
        this.home_goals_conceded = 0;
        this.away_goals_conceded = 0;
    }

    public String getId() {
        return this.team_id;
    }

    public int played() {
        return this.home_wins + this.home_draws + this.home_losses + this.away_wins + this.away_draws
                + this.away_losses;
    }

    public int wins() {
        return this.home_wins + this.away_wins;
    }

    public int draws() {
        return this.home_draws + this.away_draws;
    }

    public int losses() {
        return this.home_losses + this.away_losses;
    }

    public int goalScored() {
        return this.home_goals_scored + this.away_goals_scored;
    }

    public int goalConceded() {
        return this.home_goals_conceded + this.away_goals_conceded;
    }

    public int homeGoalDifference() {
        return this.home_goals_scored - this.home_goals_conceded;
    }

    public int awayGoalDifference() {
        return this.away_goals_scored - this.away_goals_conceded;
    }

    public int goalDifference() {
        return this.goalScored() - this.goalConceded();
    }

    public int points() {
        return (this.wins() * POINTS_FOR_WIN) + (this.draws() * POINTS_FOR_DRAW) + (this.losses() * POINTS_FOR_LOSS);
    }

    public void updatePerformance(int goals_scored, int goals_conceded, boolean home) {
        if (home) {
            this.home_goals_scored += goals_scored;
            this.home_goals_conceded += goals_conceded;
        } else {
            this.away_goals_scored += goals_scored;
            this.away_goals_conceded += goals_conceded;
        }
        if (goals_scored > goals_conceded) {
            if (home) {
                this.home_wins++;
            } else {
                this.away_wins++;
            }
        } else if (goals_scored == goals_conceded) {
            if (home) {
                this.home_draws++;
            } else {
                this.away_wins++;
            }
        } else {
            if (home) {
                this.home_losses++;
            } else {
                this.away_losses++;
            }
        }
    }

    public String recordShort() {
        return this.played() + " " + this.goalDifference() + " " + this.points();
    }

    public String recordLong() {
        return this.wins() + "-" + this.draws() + "-" + this.losses() + " " + this.goalScored() + ":"
                + this.goalConceded() + " " + this.points();
    }
}
