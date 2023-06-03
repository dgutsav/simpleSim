import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerAsia {
    private enum Zone {
        EAST,
        WEST,
        DEFAULT
    }

    private enum SeedingType {
        WEST_GROUP,
        WEST_QUALIFIER,
        EAST_GROUP,
        EAST_QUALIFIER,
        DEFAULT
    }

    private enum Stage {
        Q_EAST("ACL East Zone Qualifying Round", CCType.StageType.KNOCKOUT, CCType.CompType.ACL, true),
        GROUP_EAST("ACL East Zone Group Stage", CCType.StageType.GROUPS, CCType.CompType.ACL, false),
        SF_EAST("ACL East Zone Semi Final", CCType.StageType.KNOCKOUT, CCType.CompType.ACL, false),
        FINAL_EAST("ACL East Zone Final", CCType.StageType.KNOCKOUT, CCType.CompType.ACL, false),
        Q_WEST("ACL West Zone Qualifying Round", CCType.StageType.KNOCKOUT, CCType.CompType.ACL, true),
        GROUP_WEST("ACL West Zone Group Stage", CCType.StageType.GROUPS, CCType.CompType.ACL, false),
        SF_WEST("ACL West Zone Semi Final", CCType.StageType.KNOCKOUT, CCType.CompType.ACL, false),
        FINAL_WEST("ACL West Zone Final", CCType.StageType.KNOCKOUT, CCType.CompType.ACL, false),
        FINAL("Asian Champions League Grand Final", CCType.StageType.KNOCKOUT, CCType.CompType.ACL, false);

        private final String stage_name;
        private final CCType.StageType stage_type;

        Stage(String stage_name, CCType.StageType stage_type, CCType.CompType competition, boolean qualifying) {
            this.stage_name = stage_name;
            this.stage_type = stage_type;
        }

        public String stage_name() {
            return this.stage_name;
        }

        public CCType.StageType stage_type() {
            return this.stage_type;
        }
    }

    private static Map<Integer, Integer> zonal_rankings = new HashMap<>();

    private static Map<String, SeedingType> seedings = new HashMap<>();
    private static Map<Stage, List<KnockoutTie>> stages = new HashMap<>();
    private static Map<Stage, League[]> groups = new HashMap<>();

    private static Zone getZone(Nation nation) {
        switch (nation.getName()) {
            case "Saudi Arabia":
            case "Iran":
            case "Iraq":
            case "Syria":
            case "Uzbekistan":
            case "Emirates":
            case "Qatar":
                return Zone.WEST;
            case "Korea":
            case "Japan":
            case "China":
            case "Thailand":
            case "Australia":
                return Zone.EAST;
        }
        return Zone.DEFAULT;
    }

    private static void setZonalRankings() {
        List<Nation> nations = Sim.getNationFromContinent("Asia");
        Collections.sort(nations, Comparator.comparing(Nation::getLeagueRanking));
        int east_rank = 1, west_rank = 1;
        for (Nation nation : nations) {
            if (getZone(nation) == Zone.EAST) {
                zonal_rankings.put(nation.getId(), east_rank);
                east_rank++;
            } else if (getZone(nation) == Zone.WEST) {
                zonal_rankings.put(nation.getId(), west_rank);
                west_rank++;
            }
        }
    }

    private static int getZonalRanking(Nation nation) {
        return zonal_rankings.get(nation.getId());
    }

    private static int getPlaces(Nation nation) {
        int ranking = getZonalRanking(nation);
        if (getZone(nation) == Zone.EAST) {
            if (ranking <= 2)
                return 3;
            else if (ranking <= 4)
                return 2;
            else
                return 1;
        } else if (getZone(nation) == Zone.WEST) {
            if (ranking <= 4)
                return 2;
            else
                return 1;
        }
        return 0;
    }

    private static SeedingType getSeeding(Nation nation, int rank) {
        Zone zone = getZone(nation);
        int zone_rank = getZonalRanking(nation);
        switch (zone) {
            case EAST:
                if (zone_rank <= 2) {
                    if (rank <= 2)
                        return SeedingType.EAST_GROUP;
                    else
                        return SeedingType.EAST_QUALIFIER;
                } else if (zone_rank <= 4) {
                    if (rank == 1)
                        return SeedingType.EAST_GROUP;
                    else
                        return SeedingType.EAST_QUALIFIER;
                } else if (zone_rank <= 5 && rank == 1)
                    return SeedingType.EAST_GROUP;
            case WEST:
                if (zone_rank <= 4) {
                    if (rank == 1)
                        return SeedingType.WEST_GROUP;
                    else
                        return SeedingType.WEST_QUALIFIER;
                } else if (zone_rank <= 7 && rank == 1)
                    return SeedingType.WEST_GROUP;
            case DEFAULT:
                return SeedingType.DEFAULT;
        }
        return SeedingType.DEFAULT;
    }

    private static void setSeedings() {
        List<Nation> nations = Sim.getNationFromContinent("Asia");
        for (Nation nation : nations) {
            List<String> teams = getTeams(nation);
            for (int i = 0; i < teams.size(); i++) {
                int rank = i + 1;
                SeedingType seedingType = getSeeding(nation, rank);
                seedings.put(teams.get(i), seedingType);
            }
        }
    }

    private static List<String> getTeams(Nation nation) {
        League league = nation.getLeague(1);
        int places = getPlaces(nation);
        List<String> teams_list = new ArrayList<>();
        for (int i = 1; i <= places; i++) {
            teams_list.add(league.getTeamAtRank(i));
        }
        return teams_list;
    }

    private static SeedingType getSeedingTypeForStage(Stage stage) {
        switch (stage) {
            case GROUP_EAST:
                return SeedingType.EAST_GROUP;
            case GROUP_WEST:
                return SeedingType.WEST_GROUP;
            case Q_EAST:
                return SeedingType.EAST_QUALIFIER;
            case Q_WEST:
                return SeedingType.WEST_QUALIFIER;
            case FINAL:
                break;
            case FINAL_EAST:
                break;
            case FINAL_WEST:
                break;
            case SF_EAST:
                break;
            case SF_WEST:
                break;
            default:
                break;
        }
        return SeedingType.DEFAULT;
    }

    private static List<String> getTeamsWithSeeding(SeedingType seedingType) {
        List<String> teams_list = new ArrayList<>();
        for (Map.Entry<String, SeedingType> entry : seedings.entrySet()) {
            if (entry.getValue() == seedingType)
                teams_list.add(entry.getKey());
        }
        return teams_list;
    }

    private static List<Stage> getPreviousWinnerStages(Stage stage) {
        List<Stage> stages = new ArrayList<>();
        switch (stage) {
            case FINAL:
                stages.add(Stage.FINAL_EAST);
                stages.add(Stage.FINAL_WEST);
                break;
            case FINAL_EAST:
                stages.add(Stage.SF_EAST);
                break;
            case GROUP_EAST:
                stages.add(Stage.Q_EAST);
                break;
            case FINAL_WEST:
                stages.add(Stage.SF_WEST);
                break;
            case GROUP_WEST:
                stages.add(Stage.Q_WEST);
                break;
            case Q_EAST:
                break;
            case Q_WEST:
                break;
            case SF_EAST:
                break;
            case SF_WEST:
                break;
            default:
                break;
        }
        return stages;
    }

    private static List<String> getWinnersFromStage(Stage stage) {
        List<String> winners = new ArrayList<>();
        List<KnockoutTie> ties = stages.get(stage);
        for (KnockoutTie tie : ties) {
            winners.add(tie.getWinner());
        }
        return winners;
    }

    private static boolean isStageGroup(Stage stage) {
        return stage.stage_type() == CCType.StageType.GROUPS;
    }

    private static ArrayList<String> removeFromList(ArrayList<String> list, String to_remove) {
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equalsIgnoreCase(to_remove)) {
                index = i;
                break;
            }
        }
        list.remove(index);
        return list;
    }

    private static int NUM_POTS = 4;
    private static int NUM_GROUPS = 2;

    private static String[][] divideIntoPots(List<String> teams) {
        String[][] pots = new String[NUM_POTS][NUM_GROUPS];
        for (int i = 0; i < NUM_POTS; i++) {
            for (int j = 0; j < NUM_GROUPS; j++) {
                pots[i][j] = teams.get((i * NUM_GROUPS) + j);
            }
        }
        return pots;
    }

    private static List<Integer> getGroupOptions(String team, int pot_number, String[][] groups) {
        List<Integer> options = new ArrayList<>();
        for (int i = 0; i < NUM_GROUPS; i++) {
            if (groups[i][pot_number] == null) {
                options.add(i);
            }
        }
        return options;
    }

    private static String[][] drawGroups(String[][] pots) {
        String[][] groups = new String[NUM_GROUPS][NUM_POTS];
        for (int i = 0; i < NUM_GROUPS; i++) {
            groups[i][0] = pots[0][i];
        }
        for (int i = 1; i < NUM_POTS; i++) {
            ArrayList<String> pot_teams = new ArrayList<>();
            pot_teams.addAll(Arrays.asList(pots[i]));
            while (!pot_teams.isEmpty()) {
                String selected_team = CommonFunctions.getRandomElement(pot_teams);
                List<Integer> options = getGroupOptions(selected_team, i, groups);
                int group = CommonFunctions.getRandomIntElement(options);
                groups[group][i] = selected_team;
                pot_teams = removeFromList(pot_teams, selected_team);
            }
        }
        return groups;
    }

    private static void setStage(Stage type) {
        List<String> teams = new ArrayList<>();
        SeedingType seeding = getSeedingTypeForStage(type);
        if (seeding != SeedingType.DEFAULT) {
            teams.addAll(getTeamsWithSeeding(seeding));
        }

        List<Stage> win_stages = getPreviousWinnerStages(type);
        for (Stage stage : win_stages) {
            teams.addAll(getWinnersFromStage(stage));
        }

        if (!isStageGroup(type)) {
            if (teams.size() % 2 != 0)
                System.out.println("Irregular no of teams found for stage: " + type);
            teams = Sim.sortByReputation(teams);
            int len = teams.size();
            ArrayList<String> seeded = new ArrayList<>();
            seeded.addAll(teams.subList(0, len / 2));
            ArrayList<String> unseeded = new ArrayList<>();
            unseeded.addAll(teams.subList(len / 2, len));
            List<KnockoutTie> ties = new ArrayList<>();
            int ties_size = teams.size() / 2;
            for (int i = 0; i < ties_size; i++) {
                String selected_team = CommonFunctions.getRandomElement(seeded);
                List<String> filtered = unseeded;
                if (filtered.size() == 0)
                    System.out.println("No team found in stage: " + type);
                String selected_team_2 = CommonFunctions.getRandomElement(filtered);
                KnockoutTie tie = new KnockoutTie(selected_team, selected_team_2, 1);
                ties.add(tie);
                seeded = removeFromList(seeded, selected_team);
                unseeded = removeFromList(unseeded, selected_team_2);
            }
            stages.put(type, ties);
        } else {
            teams = Sim.sortByReputation(teams);
            String[][] pots = divideIntoPots(teams);
            String[][] groups_matrix = drawGroups(pots);
            League[] stage_groups = new League[NUM_GROUPS];
            for (int i = 0; i < NUM_GROUPS; i++) {
                League league = new League();
                for (int j = 0; j < NUM_POTS; j++) {
                    league.addTeam(groups_matrix[i][j]);
                }
                league.setFixtures();
                league.setupTable();
                stage_groups[i] = league;
            }
            groups.put(type, stage_groups);
        }
    }

    private static void simulateStage(Stage stage) {
        System.out.println("\n" + stage.stage_name);
        List<KnockoutTie> ties = stages.get(stage);
        for (int i = 0; i < ties.size(); i++) {
            ties.get(i).simulate();
            System.out.print("Match " + (i + 1) + ": ");
            ties.get(i).show();
        }
        stages.put(stage, ties);
    }

    private static void simulateGroupsMatchDay(Stage stage, int matchday) {
        League[] gLeagues = groups.get(stage);
        for (int i = 0; i < gLeagues.length; i++) {
            char ch = (char) (65 + i);
            System.out.println(stage.stage_name() + ": Group " + ch + " Matchday " + matchday);
            gLeagues[i].simulateMatchDay(matchday);
        }
    }

    private static void dispGroupTables(Stage stage) {
        System.out.println("\n" + stage.stage_name());
        League[] gLeagues = groups.get(stage);
        for (int i = 0; i < gLeagues.length; i++) {
            char ch = (char) (65 + i);
            System.out.println("Group " + ch);
            gLeagues[i].getTable().showLong();
        }
    }

    private static void simulateGroupStage() {
        for (int i = 1; i <= 6; i++) {
            simulateGroupsMatchDay(Stage.GROUP_EAST, i);
            simulateGroupsMatchDay(Stage.GROUP_WEST, i);
        }
        // Show Tables
        System.out.println("\nStandings");
        dispGroupTables(Stage.GROUP_EAST);
        dispGroupTables(Stage.GROUP_WEST);
    }

    private static List<String> getGroupTeamsAtRank(Stage stage, int rank) {
        League[] leagues = groups.get(stage);
        List<String> teams = new ArrayList<>();
        for (int i = 0; i < leagues.length; i++) {
            teams.add(leagues[i].getTeamAtRank(rank));
        }
        return teams;
    }

    private static void setSFStage(Stage stage) {
        ArrayList<String> seeded = new ArrayList<>();
        ArrayList<String> unseeded = new ArrayList<>();

        if (stage == Stage.SF_EAST) {
            seeded.addAll(getGroupTeamsAtRank(Stage.GROUP_EAST, 1));
            unseeded.addAll(getGroupTeamsAtRank(Stage.GROUP_EAST, 2));
        } else if (stage == Stage.SF_WEST) {
            seeded.addAll(getGroupTeamsAtRank(Stage.GROUP_WEST, 1));
            unseeded.addAll(getGroupTeamsAtRank(Stage.GROUP_WEST, 2));
        }

        List<KnockoutTie> ties = new ArrayList<>();
        int ties_size = seeded.size();
        for (int i = 0; i < ties_size; i++) {
            String selected_team = CommonFunctions.getRandomElement(seeded);
            String selected_team_2 = CommonFunctions.getRandomElement(unseeded);
            KnockoutTie tie = new KnockoutTie(selected_team, selected_team_2, 1);
            ties.add(tie);
            seeded = removeFromList(seeded, selected_team);
            unseeded = removeFromList(unseeded, selected_team_2);
        }
        stages.put(stage, ties);
    }

    private static void qualifiers() {
        setStage(Stage.Q_EAST);
        simulateStage(Stage.Q_EAST);
        setStage(Stage.Q_WEST);
        simulateStage(Stage.Q_WEST);
    }

    private static void setGroups() {
        setStage(Stage.GROUP_EAST);
        setStage(Stage.GROUP_WEST);
    }

    private static void setSFStage() {
        setSFStage(Stage.SF_EAST);
        setSFStage(Stage.SF_WEST);
    }

    private static void simulateSFStage() {
        simulateStage(Stage.SF_EAST);
        simulateStage(Stage.SF_WEST);
    }

    private static void setZoneFinals() {
        setStage(Stage.FINAL_EAST);
        setStage(Stage.FINAL_WEST);
    }

    private static void simulateZoneFinals() {
        simulateStage(Stage.FINAL_EAST);
        simulateStage(Stage.FINAL_WEST);
    }

    private static void setFinal() {
        setStage(Stage.FINAL);
    }

    private static void simulateFinal() {
        simulateStage(Stage.FINAL);
    }

    public static void simulate() {
        seedings = new HashMap<>();
        stages = new HashMap<>();
        groups = new HashMap<>();
        zonal_rankings = new HashMap<>();
        setZonalRankings();
        setSeedings();
        qualifiers();
        setGroups();
        simulateGroupStage();
        setSFStage();
        simulateSFStage();
        setZoneFinals();
        simulateZoneFinals();
        setFinal();
        simulateFinal();
        updateCompetitionHistory();
    }

    // #region CompetitionHistory
    private static Map<CCType.CompType, List<CompetitionHistory>> history = new HashMap<>();

    private static void updateHistory(CCType.CompType type) {
        Stage stageFinal = Stage.FINAL;
        List<TeamHistory> tHistory = new ArrayList<>();
        KnockoutTie tie = stages.get(stageFinal).get(0);
        tHistory.add(new TeamHistory(tie.getWinner(), 1, Sim.getClub(tie.getWinner()).getBestSquadRating()));
        tHistory.add(new TeamHistory(tie.getLoser(), 2, Sim.getClub(tie.getLoser()).getBestSquadRating()));
        List<CompetitionHistory> chistory;
        if (history.containsKey(type)) {
            chistory = history.get(type);
        } else {
            chistory = new ArrayList<>();
        }
        CompetitionHistory cHist = new CompetitionHistory(Sim.getSeasonNumber(), tHistory);
        chistory.add(cHist);
        history.put(type, chistory);
    }

    private static void updateCompetitionHistory() {
        updateHistory(CCType.CompType.ACL);
    }

    private static void showHistory(CCType.CompType type) {
        System.out.println(type.title());
        List<CompetitionHistory> cHist = history.get(type);
        for (CompetitionHistory compHistory : cHist) {
            compHistory.show(2, false);
        }
    }

    public static void showCompetitionHistory() {
        showHistory(CCType.CompType.ACL);
    }
    // #endregion CompetitionHistory

}
