public class EloRankings {
    private static final int WEIGHT_NORMAL = 50;
    private static final int WEIGHT_CL = 100;
    private static final int WEIGHT_EL = 80;
    private static final int WEIGHT_ECL = 55;

    private EloRankings() {
    }

    public static void calculate(Team a, Team b, Match match) {
        double old_a = a.getEloRating();
        double old_b = b.getEloRating();
        float K = WEIGHT_NORMAL;
        if(match.getTournamentLevel() == 1) {
            K = WEIGHT_CL;
        } else if (match.getTournamentLevel() == 2) {
            K = WEIGHT_EL;
        } else if(match.getTournamentLevel() == 3) {
            K = WEIGHT_ECL;
        }
        float G;
        int gd = Math.abs(match.home_team_goals - match.away_team_goals);
        if (gd <= 1) {
            G = 1f;
        } else if (gd == 2) {
            G = 1.5f;
        } else {
            G = (11 + gd) / 8.0f;
        }
        float W = 0f;
        if (match.home_team_goals > match.away_team_goals) {
            W = 1.0f;
        } else if (match.home_team_goals == match.away_team_goals) {
            W = 0.5f;
        }
        double dr = old_a - old_b;
        double den = 1 + Math.pow(10, dr / (-400));
        double We = 1.0 / den;
        double P = K * G * (W - We);
        a.updateElo(old_a + P > 0 ? old_a + P : 0);
        b.updateElo(old_b - P > 0 ? old_b - P : 0);
        Sim.updateTeam(a, a.getId());
        Sim.updateTeam(b, b.getId());
    }

    public static void main(String[] args) {
        Team team1 = CommonFunctions.getTeam(85.0f, "England");
        Team team2 = CommonFunctions.getTeam(85.0f, "Spain");
        Match match = new Match();
        match.home_team = team1.getName();
        match.away_team = team2.getName();
        match.home_team_goals = 2;
        match.away_team_goals = 5;
        EloRankings.calculate(team1, team2, match);
    }
}
