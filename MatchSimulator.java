public class MatchSimulator {

    private static int homeGoals = 0;
    private static int awayGoals = 0;

    // Global Constants
    public static final float HOME_ADVANTAGE = 0.5f;
    public static final float RANDOMNESS = 10.0f;

    public static final int HOME_WIN = 1;
    public static final int HOME_LOSS = 2;
    public static final int DRAW = 3;

    private MatchSimulator() {
    }

    public static boolean simulateMatch(Match current_match, Team home, Team away) {
        float home_team_rating = home.getSquadRating() + home.getForm() - home.getInjuries() + HOME_ADVANTAGE
                + getRandomness();
        float away_team_rating = away.getSquadRating() + away.getForm() - away.getInjuries() + getRandomness();

        int winner_goals = getWinnerGoals(home_team_rating, away_team_rating);
        int result;
        if (winner_goals == 0) {
            result = DRAW;
        } else if (home_team_rating > away_team_rating) {
            result = HOME_WIN;
        } else {
            result = HOME_LOSS;
        }

        if (result == DRAW) {
            winner_goals = getGoalsForDraw();
            homeGoals = winner_goals;
            awayGoals = winner_goals;
            Sim.updateTeamForm(current_match.home_id, 3);
            Sim.updateTeamForm(current_match.away_id, 3);
        } else {
            if (result == HOME_WIN) {
                homeGoals = winner_goals;
                awayGoals = getLoserGoals(winner_goals);
                Sim.updateTeamForm(current_match.home_id, 1);
                Sim.updateTeamForm(current_match.away_id, 2);
            } else {
                homeGoals = getLoserGoals(winner_goals);
                awayGoals = winner_goals;
                Sim.updateTeamForm(current_match.home_id, 2);
                Sim.updateTeamForm(current_match.away_id, 1);
            }
        }
        EloRankings.calculate(home, away, current_match);
        return true;
    }

    public static int getGoalsForDraw() {
        int rand = CommonFunctions.getRandomNumber(100) + 1;
        if (rand <= 25) {
            return 0;
        } else if (rand <= 80) {
            return 1;
        } else if (rand <= 92) {
            return 2;
        } else if (rand <= 96) {
            return 3;
        } else if (rand <= 99) {
            return 4;
        } else {
            return 5;
        }
    }

    public static int getLoserGoals(int diff) {
        int rand = CommonFunctions.getRandomNumber(100) + 1;
        if (diff >= 4) {
            if (rand <= 60) {
                return 0;
            } else if (rand <= 90) {
                return 1;
            } else if (rand <= 97) {
                return 2;
            } else {
                return 3;
            }
        } else if (diff == 3) {
            if (rand <= 60) {
                return 0;
            } else if (rand <= 85) {
                return 1;
            } else {
                return 2;
            }
        } else if (diff == 2) {
            if (rand <= 70) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return 0;
        }
    }

    public static float getRandomness() {
        double rand = Math.random() * RANDOMNESS;
        rand -= RANDOMNESS / 2.0f;
        return (float) rand;
    }

    public static int getWinnerGoals(float a, float b) {
        float diff = (a - b) > 0 ? (a - b) : (b - a);
        if (diff < 1.5f) {
            return 0;
        } else if (diff < 8.5f) {
            return 1;
        } else if (diff < 13.0f) {
            return 2;
        } else if (diff < 15.0f) {
            return 3;
        } else if (diff < 16.0f) {
            return 4;
        } else if (diff < 17.0f) {
            return 5;
        } else {
            return 6 + (int) ((diff - 17.0f) / 8);
        }
    }

    public static int getHomeGoals() {
        return homeGoals;
    }

    public static int getAwayGoals() {
        return awayGoals;
    }
}
