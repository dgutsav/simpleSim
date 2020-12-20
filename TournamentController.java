import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TournamentController {

    public static final int NUMBER_OF_COMPETITIONS = 3;
    public static final int TEAMS_PER_GROUP = 4;
    public static final int NUM_CL_TEAMS = 32;
    public static final int NUM_EL_TEAMS = 48;
    public static final int NUM_ECL_TEAMS = 40;

    public static final int TEAMS_PROGRESS_FROM_GROUP = 2;
    public static final int GROUP_MATCHDAYS = 6;

    private static Map<String, String> clTeams = new HashMap<>();
    private static Map<String, String> elTeams = new HashMap<>();
    private static Map<String, String> eclTeams = new HashMap<>();

    private static String[][] clGroups = new String[NUM_CL_TEAMS / TEAMS_PER_GROUP][TEAMS_PER_GROUP];
    private static String[][] elGroups = new String[NUM_EL_TEAMS / TEAMS_PER_GROUP][TEAMS_PER_GROUP];
    private static String[][] eclGroups = new String[NUM_ECL_TEAMS / TEAMS_PER_GROUP][TEAMS_PER_GROUP];

    private static Competition championsLeague;
    private static Competition europaLeague;
    private static Competition conferenceLeague;

    private static final boolean USE_CONFERENCE_LEAGUE = false;

    private TournamentController() {
        // compliance
    }

    public static int getPlacesForCompetition(int level, int rank) {
        switch (level) {
            case 1:
                if (rank <= 4) {
                    return 4;
                } else if (rank <= 6) {
                    return 3;
                } else if (rank <= 15) {
                    return 2;
                } else {
                    return 1;
                }
            case 2:
                return 3;
            case 3:
                if (rank <= 11) {
                    return 3;
                } else if (rank <= 15) {
                    return 2;
                } else {
                    return 0;
                }
            default:
                return 1;
        }
    }

    public static int getSeeding(int level, int league_rank, int div_rank) {
        if (level == 1) {
            if (div_rank == 1) {
                if (league_rank <= 11) {
                    return 0;
                } else if (league_rank <= 13) {
                    return 1;
                } else {
                    return 2;
                }
            } else if (div_rank == 2) {
                if (league_rank <= 6) {
                    return 0;
                } else if (league_rank <= 9) {
                    return 2;
                } else {
                    return 3;
                }
            } else if (div_rank == 3) {
                if (league_rank <= 5) {
                    return 0;
                } else {
                    return 1;
                }
            } else {
                return 0;
            }
        } else if (level == 2) {
            div_rank -= getPlacesForCompetition(1, league_rank);
            System.out.print(div_rank + " ");
            if (div_rank == 1) {
                if (league_rank <= 12) {
                    return 0;
                } else if (league_rank <= 24) {
                    return 1;
                } else {
                    return 2;
                }
            } else if (div_rank == 2) {
                if (league_rank <= 5) {
                    System.out.print(league_rank + "  ");
                    return 0;
                } else if (league_rank <= 9) {
                    return 2;
                } else {
                    return 3;
                }
            } else {
                if (league_rank <= 5) {
                    return 2;
                } else {
                    return 3;
                }
            }
        } else {
            return 0;
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
                return TournamentController.NUM_CL_TEAMS / TournamentController.TEAMS_PER_GROUP;
            case 2:
                return TournamentController.NUM_EL_TEAMS / TournamentController.TEAMS_PER_GROUP;
            default:
                return TournamentController.NUM_ECL_TEAMS / TournamentController.TEAMS_PER_GROUP;
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

    public static Competition getCompetition(int competition_level) {
        switch (competition_level) {
            case 1:
                return championsLeague;
            case 2:
                return europaLeague;
            default:
                return conferenceLeague;
        }
    }

    public static String getCodeForTeam(String team_id) {
        for (int i = 1; i <= TournamentController.NUMBER_OF_COMPETITIONS; i++) {
            Map<String, String> findTeams = TournamentController.getTeams(i);
            for (Map.Entry<String, String> entry : findTeams.entrySet()) {
                if (entry.getValue().equalsIgnoreCase(team_id)) {
                    return entry.getKey();
                }
            }
        }
        return "LOREM";
    }

    public static boolean isDomesticChampion(String team_id) {
        String code = TournamentController.getCodeForTeam(team_id);
        return code.charAt(code.length() - 1) == '1';
    }

    public static String getTeamForCode(String code) {
        if (clTeams.containsKey(code)) {
            return clTeams.get(code);
        } else if (elTeams.containsKey(code)) {
            return elTeams.get(code);
        } else if (eclTeams.containsKey(code)) {
            return eclTeams.get(code);
        } else {
            return "";
        }
    }

    public static void addTeams(int level, int nation_id, int rank, String team_id) {
        if (level == 3) {
            return;
        }
        Nation nation = Sim.getNation(nation_id);
        String team_code = nation.getCode() + rank;
        int seeding = getSeeding(level, nation.getLeagueRanking(), rank);
        getTeams(level).put(team_code, team_id);
        getCompetition(level).addTeam(seeding, team_id);
    }

    public static List<String> sortByCoefficients(int competition_level) {
        Map<String, String> comp_teams = getTeams(competition_level);
        Map<String, Team> teams = new HashMap<>();
        for (Map.Entry<String, String> entry : comp_teams.entrySet()) {
            teams.put(entry.getKey(), Sim.getTeam(entry.getValue()));
        }
        List<Map.Entry<String, Team>> list = new LinkedList<>(teams.entrySet());
        Collections.sort(list,
                (Map.Entry<String, Team> o1, Map.Entry<String, Team> o2) -> (int) ((o2.getValue().getCoefficient())
                        - (o1.getValue().getCoefficient())));
        List<String> sorted = new ArrayList<>();
        for (Map.Entry<String, Team> aa : list) {
            sorted.add(aa.getKey());
        }
        return sorted;
    }

    public static List<String> sortByCoefficients(List<String> teams_to_sort) {
        List<String> sorted = new ArrayList<>();
        List<Team> teams = new ArrayList<>();
        for (String team : teams_to_sort) {
            teams.add(Sim.getTeam(team));
        }
        Collections.sort(teams, (Team a, Team b) -> (int) (b.getCoefficient() - a.getCoefficient()));
        for (Team team : teams) {
            sorted.add(team.getId());
        }
        return sorted;
    }

    public static List<List<String>> divideIntoSeededPots(List<String> current_teams) {
        List<String> champions = new ArrayList<>();
        List<String> non_champions = new ArrayList<>();
        for (String team : current_teams) {
            if (isDomesticChampion(team)) {
                champions.add(team);
            } else {
                non_champions.add(team);
            }
        }
        champions = TournamentController.sortByCoefficients(champions);
        non_champions = TournamentController.sortByCoefficients(non_champions);
        List<String> seeded_teams = new ArrayList<>();
        List<String> unseeded_teams = new ArrayList<>();
        for (int i = 0; i < champions.size() / 2; i++) {
            seeded_teams.add(champions.get(i));
            unseeded_teams.add(champions.get((champions.size() / 2) + i));
        }
        for (int i = 0; i < non_champions.size() / 2; i++) {
            seeded_teams.add(non_champions.get(i));
            unseeded_teams.add(non_champions.get((non_champions.size() / 2) + i));
        }
        List<List<String>> pots = new ArrayList<>();
        pots.add(seeded_teams);
        pots.add(unseeded_teams);
        return pots;
    }

    public static String[][] setPots(int competition_level, List<String> list) {
        int number_of_groups = TournamentController.getNumGroups(competition_level);
        String[][] pots = new String[TournamentController.TEAMS_PER_GROUP][number_of_groups];
        if (competition_level == 1) {
            for (int i = 1; i <= number_of_groups; i++) {
                String code = Sim.getNationByLeagueRanking(i).getCode() + "1";
                pots[0][i - 1] = code;
                list.remove(code);
            }
            for (int i = 1; i < TournamentController.TEAMS_PER_GROUP; i++) {
                for (int j = 0; j < number_of_groups; j++) {
                    pots[i][j] = list.get(0);
                    list.remove(pots[i][j]);
                }
            }
        } else {
            for (int i = 0; i < TournamentController.TEAMS_PER_GROUP; i++) {
                for (int j = 0; j < number_of_groups; j++) {
                    pots[i][j] = list.get(0);
                    list.remove(pots[i][j]);
                }
            }
        }

        return pots;
    }

    public static boolean fromSameNation(String code1, String code2) {
        return code1.substring(0, 2).equalsIgnoreCase(code2.substring(0, 2));
    }

    public static boolean fromSameGroup(String[][] groups, String code1, String code2) {
        for (int i = 0; i < groups.length / TournamentController.TEAMS_PER_GROUP; i++) {
            boolean found1 = false;
            boolean found2 = false;
            for (String team : groups[i]) {
                if (team.equalsIgnoreCase(code1)) {
                    found1 = true;
                }
                if (team.equalsIgnoreCase(code2)) {
                    found2 = true;
                }
            }
            if (found1 && found2) {
                return true;
            }
        }
        return false;
    }

    public static void setGroups(int competition_level, String[][] pots) {
        int number_of_groups = TournamentController.getNumGroups(competition_level);
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
            pot.init(pots[i - 1], TournamentController.getNumGroups(competition_level), i, groups);
            while (!pot.isEmpty()) {
                String current_team = pot.drawTeam();
                List<Integer> possible_groups = pot.getPossibleGroupsForDrawnTeam();
                if (possible_groups.isEmpty()) {
                    TournamentController.setGroups(competition_level, pots);
                    return;
                }
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

    public static void initCompetitions() {
        championsLeague = new Competition(1);
        europaLeague = new Competition(2);
        conferenceLeague = new Competition(3);

    }

    public static void setupCompetitions() {
        championsLeague.reset();
        europaLeague.reset();
        if (USE_CONFERENCE_LEAGUE) {
            conferenceLeague.reset();
        }
    }

    public static void simulateQualifiers() {
        championsLeague.simulateQualifiers();
        europaLeague.simulateQualifiers();
        if (USE_CONFERENCE_LEAGUE) {
            conferenceLeague.simulateQualifiers();
        }
    }

    public static void simulateGroups() {
        championsLeague.setupGroups();
        championsLeague.simulateGroups();
        europaLeague.setupGroups();
        europaLeague.simulateGroups();
        if (USE_CONFERENCE_LEAGUE) {
            conferenceLeague.setupGroups();
            conferenceLeague.simulateGroups();
        }
    }

    public static void simulateKnockouts() {
        championsLeague.setInitialKnockoutTies();
        europaLeague.setInitialKnockoutTies();
        if (USE_CONFERENCE_LEAGUE) {
            conferenceLeague.setInitialKnockoutTies();
        }
        championsLeague.simulateKnockoutRounds();
        europaLeague.simulateKnockoutRounds();
        if (USE_CONFERENCE_LEAGUE) {
            conferenceLeague.simulateKnockoutRounds();
        }
    }

    public static void simulateCompetitions() {
        simulateQualifiers();
        simulateGroups();
        simulateKnockouts();
    }

    public static void saveCompetitionHistory() {
        championsLeague.saveCompetitionHistory();
        europaLeague.saveCompetitionHistory();
        if (USE_CONFERENCE_LEAGUE) {
            conferenceLeague.saveCompetitionHistory();
        }
    }

    public static void showCompetitionHistory() {
        championsLeague.showCompetitionHistory();
        europaLeague.showCompetitionHistory();
        if (USE_CONFERENCE_LEAGUE) {
            conferenceLeague.showCompetitionHistory();
        }
    }

    public static char getGroupName(int group_number) {
        group_number += 65;
        return (char) group_number;
    }
}