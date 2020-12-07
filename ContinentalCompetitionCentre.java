import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ContinentalCompetitionCentre {

    public static final int NUMBER_OF_COMPETITIONS = 3;
    public static final int TEAMS_PER_GROUP = 4;
    public static final int NUM_CL_TEAMS = 32;
    public static final int NUM_EL_TEAMS = 48;
    public static final int NUM_ECL_TEAMS = 40;

    private static Map<String, String> clTeams = new HashMap<>();
    private static Map<String, String> elTeams = new HashMap<>();
    private static Map<String, String> eclTeams = new HashMap<>();

    private static String[][] clGroups = new String[NUM_CL_TEAMS / TEAMS_PER_GROUP][TEAMS_PER_GROUP];
    private static String[][] elGroups = new String[NUM_EL_TEAMS / TEAMS_PER_GROUP][TEAMS_PER_GROUP];
    private static String[][] eclGroups = new String[NUM_ECL_TEAMS / TEAMS_PER_GROUP][TEAMS_PER_GROUP];

    private ContinentalCompetitionCentre() {
        // compliance
    }

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

    public static int getNumGroups(int competition_level) {
        switch (competition_level) {
            case 1:
                return ContinentalCompetitionCentre.NUM_CL_TEAMS / ContinentalCompetitionCentre.TEAMS_PER_GROUP;
            case 2:
                return ContinentalCompetitionCentre.NUM_EL_TEAMS / ContinentalCompetitionCentre.TEAMS_PER_GROUP;
            default:
                return ContinentalCompetitionCentre.NUM_ECL_TEAMS / ContinentalCompetitionCentre.TEAMS_PER_GROUP;
        }
    }

    public static Map<String, String> getTeams(int competition_level) {
        switch (competition_level) {
            case 1:
                return clTeams;
            case 2:
                return elTeams;
            default:
                return eclTeams;
        }
    }

    public static String[][] getGroups(int competition_level) {
        switch (competition_level) {
            case 1:
                return clGroups;
            case 2:
                return elGroups;
            default:
                return eclGroups;
        }
    }

    public static void addTeams(int competition_level, String code, String team_id) {
        getTeams(competition_level).put(code, team_id);
    }

    public static List<String> sortByCoefficients(int competition_level) {
        Map<String, String> comp_teams = getTeams(competition_level);
        Map<String, Team> teams = new HashMap<>();
        for (Map.Entry<String, String> entry : comp_teams.entrySet()) {
            teams.put(entry.getKey(), Sim.getTeam(entry.getValue()));
        }
        List<Map.Entry<String, Team>> list = new LinkedList<>(teams.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Team>>() {
            public int compare(Map.Entry<String, Team> o1, Map.Entry<String, Team> o2) {
                return (int) ((o2.getValue().getCoefficient()) - (o1.getValue().getCoefficient()));
            }
        });
        List<String> sorted = new ArrayList<>();
        for (Map.Entry<String, Team> aa : list) {
            sorted.add(aa.getKey());
        }
        return sorted;
    }

    public static String[][] setPots(int competition_level, List<String> list) {
        int number_of_groups = ContinentalCompetitionCentre.getNumGroups(competition_level);
        String[][] pots = new String[ContinentalCompetitionCentre.TEAMS_PER_GROUP][number_of_groups];
        if (competition_level == 1) {
            int count = 0;
            for (int i = 0; i < list.size(); i++) {
                if ((list.get(i).charAt(list.get(i).length() - 1) == '1')) {
                    pots[0][count] = list.get(i);
                    list.remove(i);
                    i--;
                    count++;
                    if (count >= number_of_groups) {
                        break;
                    }
                }
            }
            for (int i = 1; i < ContinentalCompetitionCentre.TEAMS_PER_GROUP; i++) {
                for (int j = 0; j < number_of_groups; j++) {
                    pots[i][j] = list.get(0);
                    list.remove(0);
                }
            }
        } else {
            for (int i = 0; i < ContinentalCompetitionCentre.TEAMS_PER_GROUP; i++) {
                for (int j = 0; j < number_of_groups; j++) {
                    pots[i][j] = list.get(0);
                    list.remove(0);
                }
            }
        }

        return pots;
    }

    public static boolean fromSameNation(String code1, String code2) {
        return code1.substring(0, 2).equalsIgnoreCase(code2.substring(0, 2));
    }

    public static void setGroups(int competition_level) {
        int number_of_groups = ContinentalCompetitionCentre.getNumGroups(competition_level);
        String[][] pots = ContinentalCompetitionCentre.setPots(competition_level,
                ContinentalCompetitionCentre.sortByCoefficients(competition_level));
        String[][] groups = new String[number_of_groups][TEAMS_PER_GROUP];
        for (int i = 0; i < number_of_groups; i++) {
            for (int j = 0; j < TEAMS_PER_GROUP; j++) {
                groups[i][j] = "LOREM";
            }
        }
        int current_pot = 0;
        String[] current_pot_teams = pots[current_pot];
        current_pot_teams = CommonFunctions.shuffle(current_pot_teams);
        for (int i = 0; i < current_pot_teams.length; i++) {
            groups[i][0] = current_pot_teams[i];
        }
        for (int i = 2; i <= TEAMS_PER_GROUP; i++) {
            Pot pot = new Pot();
            pot.init(pots[i - 1], ContinentalCompetitionCentre.getNumGroups(competition_level), i, groups);
            while (!pot.isEmpty()) {
                String current_team = pot.drawTeam();
                List<Integer> possible_groups = pot.getPossibleGroupsForDrawnTeam();
                int group_selected = possible_groups.get(CommonFunctions.getRandomNumber(possible_groups.size()));
                groups[group_selected][i - 1] = current_team;
                pot.updatePossibleGroups(groups);
            }
        }
        if (competition_level == 1) {
            clGroups = groups;
        } else if (competition_level == 2) {
            elGroups = groups;
        } else {
            eclGroups = groups;
        }
    }

    public static void displayGroups(int competition_level) {
        String[][] groups = ContinentalCompetitionCentre.getGroups(competition_level);
        System.out.println(ContinentalCompetitionCentre.getName(competition_level));
        for (int i = 0; i < ContinentalCompetitionCentre.getNumGroups(competition_level); i++) {
            System.out.println("Group " + (i + 1));
            for (String str : groups[i]) {
                System.out.print(str + "\t");
            }
            System.out.println();
        }
    }

    public static void displayPots(int competition_level, List<String> list) {
        String[][] pots = ContinentalCompetitionCentre.setPots(competition_level, list);
        System.out.println(ContinentalCompetitionCentre.getName(competition_level));
        for (int i = 0; i < TEAMS_PER_GROUP; i++) {
            System.out.println("Group " + (i + 1));
            for (String str : pots[i]) {
                System.out.print(str + "\t");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        List<String> teams = new ArrayList<>();
        teams.add("ESP1");
        teams.add("ESP2");
        teams.add("ESP3");
        teams.add("ESP4");
        teams.add("ENG1");
        teams.add("ENG2");
        teams.add("ENG3");
        teams.add("ENG4");
        teams.add("ITA1");
        teams.add("ITA2");
        teams.add("ITA3");
        teams.add("ITA4");
        teams.add("GER1");
        teams.add("GER2");
        teams.add("GER3");
        teams.add("FRA1");
        teams.add("FRA2");
        teams.add("FRA3");
        teams.add("POR1");
        teams.add("POR2");
        teams.add("UKR1");
        teams.add("UKR2");
        teams.add("NED1");
        teams.add("NED2");
        teams.add("RUS1");
        teams.add("RUS2");
        teams.add("TUR1");
        teams.add("SCO1");
        teams.add("CRO1");
        teams.add("AUT1");
        teams.add("BEL1");
        teams.add("DEN1");
        displayPots(1, teams);
    }
}