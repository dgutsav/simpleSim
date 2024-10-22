import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class TestFixtures {
    private static String[][] pots;
    private static Map<String, Integer> teams;
    private static int match_count = 0;

    private static final int NUM_POTS = 4;
    private static final int NUM_TEAMS = 36;
    private static final int NUM_MATCHES = 8;

    static class Fixture {
        public String[] home, away;

        public Fixture(int size) {
            home = new String[size];
            away = new String[size];

            for (int i = 0; i < size; i++) {
                home[i] = "null";
                away[i] = "null";
            }
        }
    }

    private static Map<String, Fixture> team_fix;
    private static Map<String, List<Integer>> team_nation_ban;
    private static List<String[]> match_pairs;

    private static void init() {
        team_fix = new HashMap<>();
        team_nation_ban = new HashMap<>();
        for (int i = 0; i < NUM_POTS; i++) {
            for (int j = 0; j < NUM_TEAMS / NUM_POTS; j++) {
                Fixture f = new Fixture(NUM_MATCHES / 2);
                team_fix.put(pots[i][j], f);
                team_nation_ban.put(pots[i][j], new ArrayList<Integer>());
            }
        }
        match_pairs = new ArrayList<>();
    }

    public static void setup() {
        String[][] tpots = new String[4][9];

        String[] tp1 = { "Real Madrid", "Manchester City", "Bayern Munich", "Paris SG", "Liverpool",
                "Inter Milan",
                "Borussia Dortmund", "RB Leipzig", "Barcelona" };
        String[] tp2 = { "Bayer Leverkusen", "Atletico Madrid", "Atalanta", "Juventus", "Benfica", "Arsenal",
                "Brugge",
                "Shakhtar", "AC Milan" };
        String[] tp3 = { "Feyenoord", "Sporting CP", "PSV", "Celtic", "Red Star Belgrade", "Dinamo Zagreb",
                "Young Boys",
                "Red Bull Salzburg", "Lille" };
        String[] tp4 = { "Monaco", "Aston Villa", "Bologna", "Girona", "Stuttgart", "Sturm Graz", "Brest",
                "Slovan Bratislava", "Sparta Prague" };

        tpots[0] = tp1;
        tpots[1] = tp2;
        tpots[2] = tp3;
        tpots[3] = tp4;

        Map<String, Integer> tnations = new HashMap<>();
        tnations.put("Real Madrid", 1);
        tnations.put("Manchester City", 2);
        tnations.put("Bayern Munich", 3);
        tnations.put("Paris SG", 4);
        tnations.put("Liverpool", 2);
        tnations.put("Inter Milan", 5);
        tnations.put("Borussia Dortmund", 3);
        tnations.put("RB Leipzig", 3);
        tnations.put("Barcelona", 1);
        tnations.put("Bayer Leverkusen", 3);
        tnations.put("Atletico Madrid", 1);
        tnations.put("Atalanta", 5);
        tnations.put("Juventus", 5);
        tnations.put("Benfica", 6);
        tnations.put("Arsenal", 2);
        tnations.put("Brugge", 8);
        tnations.put("Shakhtar", 9);
        tnations.put("AC Milan", 5);
        tnations.put("Feyenoord", 7);
        tnations.put("Sporting CP", 6);
        tnations.put("PSV", 7);
        tnations.put("Celtic", 10);
        tnations.put("Red Star Belgrade", 16);
        tnations.put("Dinamo Zagreb", 11);
        tnations.put("Young Boys", 12);
        tnations.put("Red Bull Salzburg", 13);
        tnations.put("Sparta Prague", 14);
        tnations.put("Lille", 4);
        tnations.put("Monaco", 4);
        tnations.put("Aston Villa", 2);
        tnations.put("Bologna", 5);
        tnations.put("Girona", 1);
        tnations.put("Stuttgart", 3);
        tnations.put("Sturm Graz", 13);
        tnations.put("Brest", 4);
        tnations.put("Slovan Bratislava", 15);

        pots = tpots;
        teams = tnations;
    }

    private static enum DrawType {
        Home_1, Home_2, Home_3, Home_4, Away_1, Away_2, Away_3, Away_4
    }

    private static int getPotFromType(DrawType d) {
        if (d == DrawType.Home_1 || d == DrawType.Away_1)
            return 1;
        else if (d == DrawType.Home_2 || d == DrawType.Away_2)
            return 2;
        else if (d == DrawType.Home_3 || d == DrawType.Away_3)
            return 3;
        else if (d == DrawType.Home_4 || d == DrawType.Away_4)
            return 4;
        else
            return 0;
    }

    private static boolean isHomeDrawType(DrawType d) {
        return (d == DrawType.Home_1 || d == DrawType.Home_2 || d == DrawType.Home_3 || d == DrawType.Home_4);
    }

    private static int getPotFromTeam(String t) {
        for (int i = 0; i < NUM_POTS; i++) {
            for (int j = 0; j < NUM_TEAMS / NUM_POTS; j++) {
                if (pots[i][j].equals(t))
                    return i;
            }
        }
        return -1;
    }

    private static void draw(String team, String opponent, boolean home) {
        Fixture f = team_fix.get(team);
        int opponent_pot = getPotFromTeam(opponent);
        if (home) {
            f.home[opponent_pot] = opponent;
        } else {
            f.away[opponent_pot] = opponent;
        }
        team_fix.put(team, f);

        int nation = teams.get(opponent);
        int count = 0;
        for (int i = 0; i < f.home.length; i++) {
            if (f.home[i].equals("null"))
                continue;
            if (teams.get(f.home[i]) == nation)
                count++;
        }
        for (int i = 0; i < f.away.length; i++) {
            if (f.away[i].equals("null"))
                continue;
            if (teams.get(f.away[i]) == nation)
                count++;
        }
        if (count >= 2) {
            team_nation_ban.get(team).add(nation);
        }
    }

    private static boolean canFace(String team, String opponent, boolean home) {
        if (teams.get(team) == teams.get(opponent))
            return false;
        if (team_nation_ban.get(team).contains(teams.get(team)))
            return false;
        Fixture f = team_fix.get(team);
        int pot = getPotFromTeam(opponent);
        return (home && f.home[pot].equals("null")) || (!home && f.away[pot].equals("null"));
    }

    private static String[] getPotTeams(int pot) {
        return pots[pot - 1];
    }

    private static List<String> getPossibleOpponents(String team, DrawType type) {
        List<String> opponents = new ArrayList<>();
        int pot = getPotFromType(type);
        String[] teams = getPotTeams(pot);
        for (String t : teams) {
            if (canFace(team, t, isHomeDrawType(type)) && canFace(t, team, !isHomeDrawType(type))) {
                opponents.add(t);
            }
        }
        return opponents;
    }

    private static List<DrawType> getPossibleDrawTypes(String team) {
        List<DrawType> types = new ArrayList<>();
        Fixture f = team_fix.get(team);
        for (int i = 0; i < f.home.length; i++) {
            int pot = i + 1;
            if (f.home[i].equals("null")) {
                if (pot == 1)
                    types.add(DrawType.Home_1);
                else if (pot == 2)
                    types.add(DrawType.Home_2);
                else if (pot == 3)
                    types.add(DrawType.Home_3);
                else if (pot == 4)
                    types.add(DrawType.Home_4);
            }

            if (f.away[i].equals("null")) {
                if (pot == 1)
                    types.add(DrawType.Away_1);
                else if (pot == 2)
                    types.add(DrawType.Away_2);
                else if (pot == 3)
                    types.add(DrawType.Away_3);
                else if (pot == 4)
                    types.add(DrawType.Away_4);
            }
        }
        return types;
    }

    private static void findAndDrawMinOption() {
        DrawType type = null;
        String team = null;
        int count = 99999;
        for (int i = 0; i < NUM_POTS; i++) {
            for (int j = 0; j < NUM_TEAMS / NUM_POTS; j++) {

                List<DrawType> types = getPossibleDrawTypes(pots[i][j]);
                for (DrawType t : types) {
                    List<String> opt = getPossibleOpponents(pots[i][j], t);
                    if (opt.size() < count) {
                        type = t;
                        team = pots[i][j];
                        count = opt.size();
                    }
                }
            }
        }

        List<String> options = getPossibleOpponents(team, type);
        String opponent = CommonFunctions.getRandomElement(options);
        draw(team, opponent, isHomeDrawType(type));
        draw(opponent, team, !isHomeDrawType(type));
        String[] pair = new String[2];
        if (isHomeDrawType(type)) {
            System.out.println(team + " vs " + opponent + " " + type.toString());
            pair[0] = team;
            pair[1] = opponent;
        } else {
            System.out.println(opponent + " vs " + team + " " + type.toString());
            pair[0] = opponent;
            pair[1] = team;
        }
        match_pairs.add(pair);
        match_count++;
    }

    private static void drawAll() {
        while (match_count < 144) {
            findAndDrawMinOption();
            // try {
            // }
            // catch(Exception e) {
            // System.out.println(e);
            // break;
            // }
        }
        System.out.println(match_count + " Scheduled");
    }

    private static List<String[]> selectPairs(List<String[]> pairs) {
        List<String[]> selectedPairs = new ArrayList<>();
        boolean[] usedTeams = new boolean[NUM_TEAMS];
        if (backtrack(pairs, 0, usedTeams, selectedPairs)) {
            return selectedPairs;
        }
        return null;
    }

    private static boolean backtrack(List<String[]> pairs, int start, boolean[] usedTeams,
            List<String[]> selectedPairs) {
        // Base case: Check if we've selected 18 pairs and all teams are used
        if (selectedPairs.size() == (NUM_TEAMS / 2)) {
            for (boolean used : usedTeams) {
                if (!used)
                    return false; // Not all teams are used
            }
            return true;
        }

        // Try selecting pairs starting from the 'start' index
        for (int i = start; i < pairs.size(); i++) {
            String[] pair = pairs.get(i);
            int team1 = getTeamValue(pair[0]);
            int team2 = getTeamValue(pair[1]);

            if (!usedTeams[team1] && !usedTeams[team2]) {
                // Mark the teams as used and add the pair to the solution
                usedTeams[team1] = true;
                usedTeams[team2] = true;
                selectedPairs.add(pair);

                // Recur to find more pairs
                if (backtrack(pairs, i + 1, usedTeams, selectedPairs)) {
                    return true;
                }

                // Backtrack
                usedTeams[team1] = false;
                usedTeams[team2] = false;
                selectedPairs.remove(selectedPairs.size() - 1);
            }
        }

        return false;
    }

    private static int getTeamValue(String team) {
        for (int i = 0; i < NUM_POTS; i++) {
            for (int j = 0; j < (NUM_TEAMS / NUM_POTS); j++) {
                return (i * NUM_POTS) + j;
            }
        }
        return -1;
    }

    public static void main(String args[]) {
        setup();
        init();
        drawAll();
        List<String[]> result = selectPairs(match_pairs);
        if (result != null) {
            System.out.println("Selected pairs:");
            for (String[] pair : result) {
                System.out.println(Arrays.toString(pair));
            }
        } else {
            System.out.println("No valid selection found.");
        }
    }
}