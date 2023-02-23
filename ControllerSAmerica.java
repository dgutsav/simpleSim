import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerSAmerica {
    private enum SeedingType {
        LIB_GROUP,
        LIB_Q2,
        LIB_Q1,
        SUD_GROUP,
        SUD_Q1,
        DEFAULT
    }

    private enum Stage {
        LIB_FINAL("Copa Libertadores Final", CCType.StageType.KNOCKOUT, CCType.CompType.LIB, false),
        LIB_SF("Copa Libertadores Semi-Final", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.LIB, false),
        LIB_QF("Copa Libertadores Quarter-Final", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.LIB, false),
        LIB_R16("Copa Libertadores Round of 16", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.LIB, false),
        LIB_GS("Copa Libertadores Group Stage", CCType.StageType.GROUPS, CCType.CompType.LIB, false),
        LIB_Q3("LIB Third Qualifying Round", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.LIB, true),
        LIB_Q2("LIB Second Qualifying Round", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.LIB, true),
        LIB_Q1("LIB First Qualifying Round", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.LIB, true),
        SUD_FINAL("Copa Sudamericana Final", CCType.StageType.KNOCKOUT, CCType.CompType.SUD, false),
        SUD_SF("Copa Sudamericana Semi-Final", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.SUD, false),
        SUD_QF("Copa Sudamericana Quarter-Final", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.SUD, false),
        SUD_R16("Copa Sudamericana Round of 16", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.SUD, false),
        SUD_GS("Copa Sudamericana Group Stage", CCType.StageType.GROUPS, CCType.CompType.SUD, false),
        SUD_Q1("SUD First Qualifying Round", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.SUD, true);

        private final String stage_name;
        private final CCType.StageType stage_type;
        private final CCType.CompType competition;
        private final boolean qualifying;

        Stage(String stage_name, CCType.StageType stage_type, CCType.CompType competition, boolean qualifying) {
            this.stage_name = stage_name;
            this.stage_type = stage_type;
            this.competition = competition;
            this.qualifying = qualifying;
        }

        public String stage_name() {
            return this.stage_name;
        }

        public CCType.StageType stage_type() {
            return this.stage_type;
        }

        public CCType.CompType competition() {
            return this.competition;
        }

        public boolean qualifying() {
            return this.qualifying;
        }
    }

    private static String lib_champion = null;
    private static String sud_champion = null;

    private static Map<String, SeedingType> seedings = new HashMap<>();
    private static Map<Stage, List<KnockoutTie>> stages = new HashMap<>();
    private static Map<Stage, League[]> groups = new HashMap<>();

    private static int getPlaces(CCType.CompType type, int rank) {
        if (type == CCType.CompType.LIB) {
            if (rank == 1)
                return 7;
            else if (rank == 2)
                return 6;
            else
                return 4;
        } else if (type == CCType.CompType.SUD) {
            if (rank <= 2)
                return 6;
            else
                return 4;
        } else {
            return 0;
        }
    }

    private static SeedingType getSeeding(CCType.CompType type, int league_rank, int rank) {
        if (type == CCType.CompType.LIB) {
            if (league_rank <= 2) {
                if (rank <= 5) {
                    return SeedingType.LIB_GROUP;
                } else
                    return SeedingType.LIB_Q2;
            } else if (league_rank <= 4) {
                if (rank <= 2)
                    return SeedingType.LIB_GROUP;
                else
                    return SeedingType.LIB_Q2;
            } else if (league_rank <= 10) {
                if (rank <= 2)
                    return SeedingType.LIB_GROUP;
                else if (rank <= 3)
                    return SeedingType.LIB_Q2;
                else
                    return SeedingType.LIB_Q1;
            }
        } else if (type == CCType.CompType.SUD) {
            if (league_rank <= 2)
                return SeedingType.SUD_GROUP;
            else
                return SeedingType.SUD_Q1;
        }
        return SeedingType.DEFAULT;
    }

    private static void setDefaultChampions() {
        List<Nation> nations = Sim.getNationFromContinent("South America");
        boolean set_lib = false, set_sud = false;
        for (Nation nation : nations) {
            League league = null;
            league = nation.getLeague(1);
            List<String> teams = Sim.sortByReputation(league.getTeams());
            String team = teams.get(0);
            if (nation.getLeagueRanking() == 1) {
                System.out.println("default lib champion: " + team + " (" + nation.getCode() + ")");
                lib_champion = team;
                set_lib = true;
            } else if (nation.getLeagueRanking() == 2) {
                System.out.println("default sud champion: " + team + " (" + nation.getCode() + ")");
                sud_champion = team;
                set_sud = true;
            }
            if (set_lib && set_sud)
                break;
        }
        System.out.println("default champions set");
    }

    private static void setSeedings() {
        if (lib_champion == null && sud_champion == null) {
            setDefaultChampions();
        }
        seedings.put(lib_champion, SeedingType.LIB_GROUP);
        seedings.put(sud_champion, SeedingType.LIB_GROUP);
        int lib_group_seed_count = 2;
        List<Nation> nations = Sim.getNationFromContinent("South America");
        for (Nation nation : nations) {
            Map<CCType.CompType, List<String>> teams = getTeams(nation, nation.getLeagueRanking());
            for (Map.Entry<CCType.CompType, List<String>> entry : teams.entrySet()) {
                CCType.CompType type = entry.getKey();
                List<String> nat_teams = entry.getValue();
                for (int i = 0; i < nat_teams.size(); i++) {
                    int rank = i + 1;
                    SeedingType seeding = getSeeding(type, nation.getLeagueRanking(), rank);
                    if (seeding == SeedingType.LIB_GROUP) lib_group_seed_count++;
                    seedings.put(nat_teams.get(i), seeding);
                }
            }
        }
    }

    private static Map<CCType.CompType, List<String>> getTeams(Nation nation, int ranking) {
        Map<CCType.CompType, List<String>> map = new HashMap<>();
        int lib_places = getPlaces(CCType.CompType.LIB, ranking);
        int sud_places = getPlaces(CCType.CompType.SUD, ranking);
        List<String> teams = new ArrayList<>();
        League league = nation.getLeague(1);
        int rank = 1;
        while (teams.size() < (lib_places + sud_places)) {
            String team = league.getTeamAtRank(rank);
            if (!team.equalsIgnoreCase(lib_champion) && !team.equalsIgnoreCase(sud_champion)) {
                teams.add(team);
            }
            rank++;
        }
        String cw = nation.getCupWinner();
        if (!teams.contains(cw) && !cw.equalsIgnoreCase(lib_champion) && !cw.equalsIgnoreCase(sud_champion))
            teams.add(0, cw);
        map.put(CCType.CompType.LIB, teams.subList(0, lib_places));
        map.put(CCType.CompType.SUD, teams.subList(lib_places, lib_places + sud_places));
        return map;
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
            case LIB_FINAL:
                stages.add(Stage.LIB_SF);
                break;
            case LIB_SF:
                stages.add(Stage.LIB_QF);
                break;
            case LIB_QF:
                stages.add(Stage.LIB_R16);
                break;
            case LIB_R16:
                stages.add(Stage.LIB_GS);
                break;
            case LIB_GS:
                stages.add(Stage.LIB_Q3);
                break;
            case LIB_Q3:
                stages.add(Stage.LIB_Q2);
                break;
            case LIB_Q2:
                stages.add(Stage.LIB_Q1);
                break;
            case LIB_Q1:
                break;
            case SUD_FINAL:
                stages.add(Stage.SUD_SF);
                break;
            case SUD_SF:
                stages.add(Stage.SUD_QF);
                break;
            case SUD_QF:
                stages.add(Stage.SUD_R16);
                break;
            case SUD_R16:
                stages.add(Stage.SUD_GS);
                break;
            case SUD_GS:
                stages.add(Stage.SUD_Q1);
                break;
            case SUD_Q1:
                break;
        }
        return stages;
    }

    private static List<Stage> getPreviousLoserStages(Stage stage) {
        List<Stage> stages = new ArrayList<>();
        if (stage == Stage.SUD_GS) {
            stages.add(Stage.LIB_Q3);
        }
        return stages;
    }

    private static SeedingType getSeedingTypeForStage(Stage stage) {
        switch (stage) {
            case LIB_GS:
                return SeedingType.LIB_GROUP;
            case LIB_Q2:
                return SeedingType.LIB_Q2;
            case LIB_Q1:
                return SeedingType.LIB_Q1;
            case SUD_GS:
                return SeedingType.SUD_GROUP;
            case SUD_Q1:
                return SeedingType.SUD_Q1;
            default:
                return SeedingType.DEFAULT;
        }
    }

    private static boolean isStageGroup(Stage stage) {
        return stage.stage_type() == CCType.StageType.GROUPS;
    }

    private static List<String> getWinnersFromStage(Stage stage) {
        List<String> winners = new ArrayList<>();
        List<KnockoutTie> ties = stages.get(stage);
        for (KnockoutTie tie : ties) {
            winners.add(tie.getWinner());
        }
        return winners;
    }

    private static List<String> getLosersFromStage(Stage stage) {
        List<String> losers = new ArrayList<>();
        List<KnockoutTie> ties = stages.get(stage);
        for (KnockoutTie tie : ties) {
            losers.add(tie.getLoser());
        }
        return losers;
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

    private static String[][] divideIntoPots(List<String> teams) {
        String[][] pots = new String[4][8];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                pots[i][j] = teams.get((i * 8) + j);
            }
        }
        return pots;
    }

    private static List<Integer> getGroupOptions(String team, int pot_number, String[][] groups) {
        List<Integer> options = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            if (groups[i][pot_number] == null) {
                options.add(i);
            }
        }
        return options;
    }

    private static String[][] drawGroups(String[][] pots) {
        String[][] groups = new String[8][4];
        for (int i = 0; i < 8; i++) {
            groups[i][0] = pots[0][i];
        }
        for (int i = 1; i < 4; i++) {
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

        List<Stage> loss_stages = getPreviousLoserStages(type);
        for (Stage stage : loss_stages) {
            teams.addAll(getLosersFromStage(stage));
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
            League[] stage_groups = new League[8];
            for (int i = 0; i < 8; i++) {
                League league = new League();
                for (int j = 0; j < 4; j++) {
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

    private static void qualifiers() {
        setStage(Stage.LIB_Q1);
        simulateStage(Stage.LIB_Q1);
        setStage(Stage.LIB_Q2);
        simulateStage(Stage.LIB_Q2);
        setStage(Stage.LIB_Q3);
        simulateStage(Stage.LIB_Q3);
        setStage(Stage.SUD_Q1);
        simulateStage(Stage.SUD_Q1);
    }

    private static void setGroups() {
        setStage(Stage.LIB_GS);
        setStage(Stage.SUD_GS);
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
            simulateGroupsMatchDay(Stage.LIB_GS, i);
            simulateGroupsMatchDay(Stage.SUD_GS, i);
        }
        // Show Tables
        System.out.println("\nStandings");
        dispGroupTables(Stage.LIB_GS);
        dispGroupTables(Stage.SUD_GS);
    }

    private static List<String> getGroupTeamsAtRank(Stage stage, int rank) {
        League[] leagues = groups.get(stage);
        List<String> teams = new ArrayList<>();
        for (int i = 0; i < leagues.length; i++) {
            teams.add(leagues[i].getTeamAtRank(rank));
        }
        return teams;
    }

    private static void setR16Stage(Stage stage) {
        ArrayList<String> seeded = new ArrayList<>();
        ArrayList<String> unseeded = new ArrayList<>();

        if (stage == Stage.LIB_R16) {
            seeded.addAll(getGroupTeamsAtRank(Stage.LIB_GS, 1));
            unseeded.addAll(getGroupTeamsAtRank(Stage.LIB_GS, 2));
        } else if (stage == Stage.SUD_R16) {
            seeded.addAll(getGroupTeamsAtRank(Stage.SUD_GS, 1));
            unseeded.addAll(getGroupTeamsAtRank(Stage.LIB_GS, 3));
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

    private static void setKnockouts1() {
        setR16Stage(Stage.LIB_R16);
        setR16Stage(Stage.SUD_R16);
    }

    private static void simulateKnockouts1() {
        simulateStage(Stage.LIB_R16);
        simulateStage(Stage.SUD_R16);
    }

    private static void setKnockouts2() {
        setStage(Stage.LIB_QF);
        setStage(Stage.SUD_QF);
    }

    private static void simulateKnockouts2() {
        simulateStage(Stage.LIB_QF);
        simulateStage(Stage.SUD_QF);
    }

    private static void setKnockouts3() {
        setStage(Stage.LIB_SF);
        setStage(Stage.SUD_SF);
    }

    private static void simulateKnockouts3() {
        simulateStage(Stage.LIB_SF);
        simulateStage(Stage.SUD_SF);
    }

    private static void setFinal() {
        setStage(Stage.LIB_FINAL);
        setStage(Stage.SUD_FINAL);
    }

    private static void simulateFinal() {
        simulateStage(Stage.LIB_FINAL);
        simulateStage(Stage.SUD_FINAL);
    }

    public static void simulate() {
        seedings = new HashMap<>();
        stages = new HashMap<>();
        groups = new HashMap<>();
        setSeedings();
        count();
        qualifiers();
        setGroups();
        simulateGroupStage();
        setKnockouts1();
        simulateKnockouts1();
        setKnockouts2();
        simulateKnockouts2();
        setKnockouts3();
        simulateKnockouts3();
        setFinal();
        simulateFinal();
        updateCompetitionHistory();
    }

    // #region CompetitionHistory
    private static Map<CCType.CompType, List<CompetitionHistory>> history = new HashMap<>();

    private static void updateHistory(CCType.CompType type) {
        Stage stageFinal = type == CCType.CompType.LIB ? Stage.LIB_FINAL : Stage.SUD_FINAL;
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
        if (type == CCType.CompType.LIB)
            lib_champion = tie.getWinner();
        else if (type == CCType.CompType.SUD)
            sud_champion = tie.getWinner();
    }

    private static void updateCompetitionHistory() {
        updateHistory(CCType.CompType.LIB);
        updateHistory(CCType.CompType.SUD);
    }

    private static void showHistory(CCType.CompType type) {
        System.out.println(type.title());
        List<CompetitionHistory> cHist = history.get(type);
        for (CompetitionHistory compHistory : cHist) {
            compHistory.show(2, false);
        }
    }

    public static void showCompetitionHistory() {
        showHistory(CCType.CompType.LIB);
        showHistory(CCType.CompType.SUD);
    }
    // #endregion CompetitionHistory

    private static void count() {
        int count = 0;
        for (Map.Entry<String, SeedingType> entry : seedings.entrySet()) {
            if (entry.getValue() == SeedingType.LIB_GROUP)
                count++;
        }

        System.out.println("Seedings LIB GROUP: " + count);
    }
}
