public class EloRankings {
    // Importance Coefficients
    public static final int FRIENDLY = 10;
    public static final int QUALIFIERS = 25;
    public static final int WC_GROUP_STAGE = 40;
    public static final int WC_KNOCKOUTS = 60;

    private EloRankings() {
    }

    public static void calculate(String a, String b, Match match) {
        NationalTeam home = Sim.getNationalTeam(a);
        NationalTeam away = Sim.getNationalTeam(b);
        double points_home = home.getPoints();
        double points_away = home.getPoints();
        float K = FRIENDLY;
        if (match.getTournamentLevel() == 1) {
            K = QUALIFIERS;
        } else if (match.getTournamentLevel() == 2) {
            K = WC_GROUP_STAGE;
        } else if (match.getTournamentLevel() == 3) {
            K = WC_KNOCKOUTS;
        }
        float W = 0f;
        if (match.home_team_goals > match.away_team_goals) {
            W = 1.0f;
        } else if (match.home_team_goals == match.away_team_goals) {
            W = 0.5f;
        }
        double dr = points_home - points_away;
        double den = 1 + Math.pow(10, dr / (-600));
        double We = 1.0 / den;
        double P = K * (W - We);
        home.updatePoints(P);
        away.updatePoints(P * -1);
    }
}
