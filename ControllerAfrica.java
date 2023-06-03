import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerAfrica {
    private enum SeedingType {
        GROUP,
        QUALIFIER,
        DEFAULT
    }

    private enum Stage {
        QUALIFIER("ACC Qualifying Round", CCType.StageType.KNOCKOUT, CCType.CompType.ACC, true),
        GROUP_STAGE("ACC Group Stage", CCType.StageType.GROUPS, CCType.CompType.ACC, false),
        QUARTER_FINAL("ACC Quarter Final", CCType.StageType.KNOCKOUT, CCType.CompType.ACC, false),
        SEMI_FINAL("ACC Semi Final", CCType.StageType.KNOCKOUT, CCType.CompType.ACC, true),
        FINAL("African Champions Cup Grand Final", CCType.StageType.KNOCKOUT, CCType.CompType.ACC, false);

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

    private static Map<String, SeedingType> seedings = new HashMap<>();
    private static Map<Stage, List<KnockoutTie>> stages = new HashMap<>();
    private static Map<Stage, League[]> groups = new HashMap<>();

    private static int getPlaces(Nation nation) {
        int rank = nation.getLeagueRanking();
        if (rank <= 8)
            return 2;
        else if (rank <= 16)
            return 1;
        return 0;
    }

    private static SeedingType getSeeding(Nation nation, int rank) {
        int leg_rank = nation.getLeagueRanking();
        if (leg_rank <= 8 && rank == 1)
            return SeedingType.GROUP;
        else if (leg_rank <= 16)
            return SeedingType.QUALIFIER;
        return SeedingType.DEFAULT;
    }

    private static void setSeedings() {
        List<Nation> nations = Sim.getNationFromContinent("Africa");
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
            case GROUP_STAGE:
                return SeedingType.GROUP;
            case QUALIFIER:
                return SeedingType.QUALIFIER;
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
                stages.add(Stage.SEMI_FINAL);
                break;
            case SEMI_FINAL:
                stages.add(Stage.QUARTER_FINAL);
                break;
            case GROUP_STAGE:
                stages.add(Stage.QUALIFIER);
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
    private static int NUM_GROUPS = 4;

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

    private static String preventZeroGroups(List<String> pot, int pot_number, String[][] groups) {
        String min_team = null;
        int min_options = 1000;
        for (String team : pot) {
            List<Integer> options = getGroupOptions(team, pot_number, groups);
            if (options.size() < min_options) {
                min_options = options.size();
                min_team = team;
            }
        }
        return min_team;
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
                String zero_team = preventZeroGroups(pot_teams, i, groups);
                if (zero_team != null)
                    selected_team = zero_team;
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
            simulateGroupsMatchDay(Stage.GROUP_STAGE, i);
        }
        // Show Tables
        System.out.println("\nStandings");
        dispGroupTables(Stage.GROUP_STAGE);
    }

    private static List<String> getGroupTeamsAtRank(Stage stage, int rank) {
        League[] leagues = groups.get(stage);
        List<String> teams = new ArrayList<>();
        for (int i = 0; i < leagues.length; i++) {
            teams.add(leagues[i].getTeamAtRank(rank));
        }
        return teams;
    }

    private static void setQFStage() {
        ArrayList<String> seeded = new ArrayList<>();
        ArrayList<String> unseeded = new ArrayList<>();

        seeded.addAll(getGroupTeamsAtRank(Stage.GROUP_STAGE, 1));
        unseeded.addAll(getGroupTeamsAtRank(Stage.GROUP_STAGE, 2));

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
        stages.put(Stage.QUARTER_FINAL, ties);
    }

    private static void qualifiers() {
        setStage(Stage.QUALIFIER);
        simulateStage(Stage.QUALIFIER);
    }

    private static void setGroups() {
        setStage(Stage.GROUP_STAGE);
    }

    public static void simulate() {
        seedings = new HashMap<>();
        stages = new HashMap<>();
        groups = new HashMap<>();
        setSeedings();
        qualifiers();
        setGroups();
        simulateGroupStage();
        setQFStage();
        simulateStage(Stage.QUARTER_FINAL);
        setStage(Stage.SEMI_FINAL);
        simulateStage(Stage.SEMI_FINAL);
        setStage(Stage.FINAL);
        simulateStage(Stage.FINAL);
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
        updateHistory(CCType.CompType.ACC);
    }

    private static void showHistory(CCType.CompType type) {
        System.out.println(type.title());
        List<CompetitionHistory> cHist = history.get(type);
        for (CompetitionHistory compHistory : cHist) {
            compHistory.show(2, false);
        }
    }

    public static void showCompetitionHistory() {
        showHistory(CCType.CompType.ACC);
    }

    public static Map<CCType.CompType, List<CompetitionHistory>> getHistory() {
        return history;
    }
    // #endregion CompetitionHistory

}
