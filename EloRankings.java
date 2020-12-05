public class EloRankings {
    public static int WEIGHT_NORMAL = 50;

    public static void calculate(Team a, Team b, Match match) {
        double old_a = a.getEloRating();
        double old_b = b.getEloRating();

        int result = match.home_team_goals > match.away_team_goals ? 1
                : match.away_team_goals > match.home_team_goals ? 2 : 3;

        float K = WEIGHT_NORMAL;

        int gd = Math.abs(match.home_team_goals - match.away_team_goals);
        float G = 0f;
        if (gd <= 1) {
            G = 1f;
        } else if (gd == 2) {
            G = 1.5f;
        } else {
            G = (11 + gd) / 8;
        }

        float W = result == 1 ? 1.0f : result == 2 ? 0.5f : 0f;

        double dr = old_a - old_b;

        double den = 1 + Math.pow(10, dr / (-400));
        double We = 1.0 / den;

        double P = K * G * (W - We);

        a.updateElo(old_a + P);
        b.updateElo(old_b - P);
        Sim.updateTeam(a, a.id);
        Sim.updateTeam(b, b.id);
    }
}
