import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContinentalCompetition {

    public static final int TEAMS_PER_GROUP = 4;

    public static final int NUM_CL_TEAMS = 32;
    public static final int NUM_EL_TEAMS = 48;
    public static final int NUM_ECL_TEAMS = 40;

    private static Map<String, String> clTeams = new HashMap<>();
    private static Map<String, String> elTeams = new HashMap<>();
    private static Map<String, String> eclTeams = new HashMap<>();

    public static int getPlacesForCompetition(int competition_level, int league_ranking) {
        switch (competition_level) {
            case 1:
                if (league_ranking <= 3) {
                    return 4;
                } else if (league_ranking <= 5) {
                    return 3;
                } else if (league_ranking <= 9) {
                    return 2;
                } else {
                    return 1;
                }
            case 2:
                if (league_ranking <= 5) {
                    return 3;
                } else if (league_ranking <= 8) {
                    return 4;
                } else {
                    return 3;
                }
            case 3:
                if (league_ranking <= 11) {
                    return 3;
                } else {
                    return 2;
                }
            default:
                return 1;
        }
    }

    public static String getName(int competition_level) {
        switch (competition_level) {
            case 1:
                return "Champions League";
            case 2:
                return "Europa League";
            case 3:
                return "Conference League";
            default:
                return "Normal League";
        }
    }

    public static void addTeams(int competition_level, String code, String team_id) {
        if (competition_level == 1) {
            clTeams.put(code, team_id);
        } else if (competition_level == 2) {
            elTeams.put(code, team_id);
        } else if (competition_level == 3) {
            eclTeams.put(code, team_id);
        } else {
            clTeams.put(code, team_id);
        }
    }
}