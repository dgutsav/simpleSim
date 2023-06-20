public class EloRankings {
    // Importance Coefficients
    public static final int FRIENDLY = 10;
    public static final int QUALIFIERS = 25;
    public static final int CC_GROUP_STAGE = 35;
    public static final int CC_KNOCKOUTS = 40;
    public static final int WC_GROUP_STAGE = 50;
    public static final int WC_KNOCKOUTS = 60;

    private EloRankings() {
    }

    private static int getWeightage(int level) {
        switch (level) {
            case 1:
                return QUALIFIERS;
            case 2:
                return WC_GROUP_STAGE;
            case 3:
                return WC_KNOCKOUTS;
            case 4:
                return CC_GROUP_STAGE;
            case 5:
                return CC_KNOCKOUTS;
            default:
                return FRIENDLY;
        }
    }

    public static void calculate(String a, String b, Match match) {
        NationalTeam home = Sim.getNationalTeam(a);
        NationalTeam away = Sim.getNationalTeam(b);
        double points_home = home.getPoints();
        double points_away = home.getPoints();
        float K = getWeightage(match.getTournamentLevel());
        float W = 0f;
        if (MatchSimulator.getHomeGoals() > MatchSimulator.getAwayGoals()) {
            W = 1.0f;
        } else if (MatchSimulator.getHomeGoals() == MatchSimulator.getAwayGoals()) {
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
