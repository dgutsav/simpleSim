import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerCONMEBOL {

    private ControllerCONMEBOL() {
    }

    public static final String CONTINENT = "South America";
    private static Map<String, String> teams = new HashMap<>();

    private static Competition libertadores;
    private static Competition sudamericana;

    private static String[][] lib_groups = new String[8][4];

    public static String getName(int level) {
        if (level == 1) {
            return "Copa Libertadores";
        } else {
            return "Copa Sudamericana";
        }
    }

    public static Competition getCompetition(int level) {
        if (level == 1) {
            return libertadores;
        } else {
            return sudamericana;
        }
    }

    public static int getPlaces(int level, int rank) {
        if (level == 1) {
            if (rank <= 2) {
                return 7;
            } else if (rank <= 3) {
                return 5;
            } else {
                return 4;
            }
        } else {
            if (rank <= 2) {
                return 6;
            } else {
                return 4;
            }
        }
    }

    public static int getSeeding(int level, int league_rank, int div_rank) {
        if (level == 1) {
            if (league_rank <= 2) {
                if (div_rank <= 6) {
                    return 0;
                } else {
                    return 2;
                }
            } else if (league_rank <= 4) {
                if (div_rank <= 2) {
                    return 0;
                } else if (div_rank <= 5) {
                    return 2;
                }
            } else {
                if (div_rank <= 2) {
                    return 0;
                } else if (div_rank == 3) {
                    return 2;
                } else {
                    return 3;
                }
            }
        } else {
            return 1;
        }
        return 0;
    }

    public static int getHighestSeed(int competition_level) {
        if (competition_level == 1) {
            return 3;
        } else {
            return 1;
        }
    }

    public static List<Nation> sortByLeagueRanking() {
        List<Nation> nations = Sim.getNationFromContinent("South America");
        Collections.sort(nations, (Nation o1, Nation o2) -> o1.getLeagueRanking() - o2.getLeagueRanking());
        return nations;
    }

    public static void getTeams() {
        teams.clear();
        List<Nation> nations = ControllerCONMEBOL.sortByLeagueRanking();
        for (Nation nation : nations) {
            int lib_places = ControllerCONMEBOL.getPlaces(1, nation.getLeagueRanking());
            int sud_places = ControllerCONMEBOL.getPlaces(2, nation.getLeagueRanking());
            List<String> lib_teams = new ArrayList<>();
            List<String> sud_teams = new ArrayList<>();
            String cup_winner = nation.getCupWinner();
            for (int i = 1; i <= lib_places; i++) {
                lib_teams.add(nation.getTeamAtRank(i));
            }
            for (int i = 1 + lib_places; i <= lib_places + sud_places; i++) {
                sud_teams.add(nation.getTeamAtRank(i));
            }
            if (sud_teams.contains(cup_winner)) {
                String replaceWith = lib_teams.get(lib_teams.size() - 1);
                lib_teams.add(lib_teams.size() - 1, cup_winner);
                sud_teams.remove(cup_winner);
                sud_teams.add(0, replaceWith);
            } else if (!lib_teams.contains(cup_winner)) {
                String replaceWith = lib_teams.get(lib_teams.size() - 1);
                lib_teams.add(lib_teams.size() - 1, cup_winner);
                sud_teams.remove(sud_teams.get(sud_teams.size() - 1));
                sud_teams.add(0, replaceWith);
            }
            for (int i = 1; i <= lib_places; i++) {
                String code = nation.getCode() + i;
                teams.put(code, lib_teams.get(i - 1));
                int seeding = ControllerCONMEBOL.getSeeding(1, nation.getLeagueRanking(), i);
                getCompetition(1).addTeam(seeding, lib_teams.get(i - 1));
            }
            for (int i = lib_places + 1; i <= sud_places + lib_places; i++) {
                String code = nation.getCode() + i;
                teams.put(code, sud_teams.get(i - (1 + lib_places)));
                int seeding = ControllerCONMEBOL.getSeeding(2, nation.getLeagueRanking(), i);
                getCompetition(2).addTeam(seeding, sud_teams.get(i - (1 + lib_places)));
            }
        }
        ControllerCONMEBOL.setLibGroups(getCompetition(1).getTeamsForQualifiers(0));
    }

    public static List<List<String>> divideIntoPots(List<String> teams) {
        teams = CommonFunctions.sortByCoefficients(teams);
        List<String> seeded_teams = new ArrayList<>();
        List<String> unseeded_teams = new ArrayList<>();
        for (int i = 0; i < teams.size() / 2; i++) {
            seeded_teams.add(teams.get(i));
            unseeded_teams.add(teams.get((teams.size() / 2) + i));
        }
        List<List<String>> pots = new ArrayList<>();
        pots.add(seeded_teams);
        pots.add(unseeded_teams);
        return pots;
    }

    public static String getCodeForTeam(String team) {
        for (Map.Entry<String, String> entry : teams.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(team)) {
                return entry.getKey();
            }
        }
        return "";
    }

    public static String getTeamForCode(String code) {
        if (teams.containsKey(code)) {
            return teams.get(code);
        } else {
            return "";
        }
    }

    public static String[][] getPots(List<String> group_teams) {
        group_teams = CommonFunctions.sortByCoefficients(group_teams);
        String[][] pots = new String[4][8];
        for (int i = 0; i < group_teams.size(); i++) {
            pots[i / 8][i % 8] = ControllerCONMEBOL.getCodeForTeam(group_teams.get(i));
        }
        pots[3][4] = "XYZ1";
        pots[3][5] = "XYZ2";
        pots[3][6] = "XYZ3";
        pots[3][7] = "XYZ4";
        return pots;
    }

    public static void setLibGroups(List<String> group_teams) {
        String[][] pots = ControllerCONMEBOL.getPots(group_teams);
        String[][] groups = new String[8][4];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                groups[i][j] = "LOREM";
            }
        }
        String[] pot1 = pots[0];
        pot1 = CommonFunctions.shuffle(pot1);
        for (int i = 0; i < pot1.length; i++) {
            groups[i][0] = pot1[i];
        }
        for (int i = 2; i <= 4; i++) {
            Pot pot = new Pot();
            pot.init(pots[i - 1], 8, i, groups);
            while (!pot.isEmpty()) {
                String current_team = pot.drawTeam();
                List<Integer> possible_groups = pot.getPossibleGroupsForDrawnTeam();
                if (possible_groups.isEmpty()) {
                    ControllerCONMEBOL.setLibGroups(group_teams);
                    return;
                }
                int group_selected = CommonFunctions.getRandomIntElement(possible_groups);
                groups[group_selected][i - 1] = current_team;
                pot.updatePossibleGroups(groups);
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                groups[i][j] = ControllerCONMEBOL.getTeamForCode(groups[i][j]);
            }
        }
        CommonFunctions.display2DArray(groups, 8, 4);
        lib_groups = groups;
    }

    public static void postQualifyingAdjustments() {
        List<String> winners = libertadores.getWinnersFromRound(1, true);
        int count = 0;
        for (int i = 0; i < 8; i++) {
            if (lib_groups[i][3].length() == 0) {
                lib_groups[i][3] = winners.get(count);
                System.out.println(Sim.getTeam(lib_groups[i][3]).getName() + " added to LIB GS");
                count++;
            }
        }
    }

    public static void setupSudamericana() {
        String[] transferred = libertadores.getTransferredTeams();
        List<String> gs = sudamericana.getTeamsForGroup();
        List<String> sorted_winners = ControllerCONMEBOL.convertTiesToTable(sudamericana.getTies(1, true), gs);
        List<String> seeded = new ArrayList<>();
        List<String> unseeded = new ArrayList<>();
        for (int i = 0; i < sorted_winners.size(); i++) {
            if (i < 6) {
                seeded.add(sorted_winners.get(i));
            } else {
                unseeded.add(sorted_winners.get(i));
            }
        }
        System.out.println(seeded.size());
        for (int i = 0; i < transferred.length; i++) {
            seeded.add(transferred[i]);
        }
        System.out.println(seeded.size());
        List<KnockoutTie> lib_ties = libertadores.getTies(1, true);
        List<String> losers = new ArrayList<>();
        for (KnockoutTie tie : lib_ties) {
            losers.add(tie.getLoser());
        }
        losers = ControllerCONMEBOL.convertTiesToTable(lib_ties, losers);
        seeded.add(losers.get(0));
        seeded.add(losers.get(1));
        System.out.println(seeded.size());
        List<KnockoutTie> ties = new ArrayList<>();
        while (!seeded.isEmpty()) {
            String team1 = seeded.remove(CommonFunctions.getRandomNumber(seeded.size()));
            String team2 = unseeded.remove(CommonFunctions.getRandomNumber(unseeded.size()));
            KnockoutTie tie = new KnockoutTie(team2, team1, 2);
            ties.add(tie);
        }
        sudamericana.setInitialKnockoutTies(ties);
    }

    public static String[][] getLibGroups() {
        return lib_groups;
    }

    public static List<String> convertTiesToTable(List<KnockoutTie> ties, List<String> teams_to_include) {
        LeagueTable all_table = new LeagueTable(ties.size() * 2);
        List<String> current_teams = new ArrayList<>();
        for (KnockoutTie tie : ties) {
            current_teams.add(tie.getTeam1());
            current_teams.add(tie.getTeam2());
        }
        all_table.startNewSeason(current_teams);
        for (KnockoutTie tie : ties) {
            Match match1 = tie.getFirstLeg();
            all_table.updateTeamPerformance(match1.home_id, match1.home_team_goals, match1.away_team_goals);
            all_table.updateTeamPerformance(match1.away_id, match1.away_team_goals, match1.home_team_goals);
            Match match2 = tie.getSecondLeg();
            all_table.updateTeamPerformance(match2.home_id, match2.home_team_goals, match2.away_team_goals);
            all_table.updateTeamPerformance(match2.away_id, match2.away_team_goals, match2.home_team_goals);
        }
        all_table.sortTable();
        List<String> sorted = new ArrayList<>();
        for (int i = 1; i <= ties.size() * 2; i++) {
            String team = all_table.getTeamAtRank(i);
            if (teams_to_include.contains(team)) {
                sorted.add(team);
            }
        }
        return sorted;
    }

    public static void initCompetitions() {
        libertadores = new Competition(1, CONTINENT);
        sudamericana = new Competition(2, CONTINENT);
    }

    public static void setupCompetitions() {
        libertadores.reset();
        sudamericana.reset();
    }

    public static void simulateQualifiers() {
        libertadores.simulateQualifiers();
        sudamericana.simulateQualifiers();
        ControllerCONMEBOL.postQualifyingAdjustments();
    }

    public static void simulateGroups() {
        libertadores.setupGroupsCONMEBOL();
        libertadores.simulateGroups();
    }

    public static void simulateKnockouts() {
        libertadores.setInitialKnockoutTies();
        libertadores.simulateKnockoutRounds();
        ControllerCONMEBOL.setupSudamericana();
        sudamericana.simulateKnockoutRounds();
    }

    public static void simulateCompetitions() {
        simulateQualifiers();
        simulateGroups();
        simulateKnockouts();
    }

    public static void saveCompetitionHistory() {
        libertadores.saveCompetitionHistory();
        sudamericana.saveCompetitionHistory();
    }

    public static void showCompetitionHistory() {
        libertadores.showCompetitionHistory();
        sudamericana.showCompetitionHistory();
    }
}
