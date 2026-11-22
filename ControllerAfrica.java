import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ControllerAfrica {
    private static final String CONTINENT = "Africa";

    private enum SeedingType {
        ACC_Q1,
        ACC_Q2,
        ACF_Q1,
        ACF_Q2,
        DEFAULT
    }

    private enum Stage {
        ACC_Q1("ACC Qualifying Round 1", CCType.StageType.KNOCKOUT, CCType.CompType.ACC, true),
        ACC_Q2("ACC Qualifying Round 2", CCType.StageType.KNOCKOUT, CCType.CompType.ACC, true),
        ACC_GROUP_STAGE("ACC Group Stage", CCType.StageType.GROUPS, CCType.CompType.ACC, false),
        ACC_QUARTER_FINAL("ACC Quarter Final", CCType.StageType.KNOCKOUT, CCType.CompType.ACC, false),
        ACC_SEMI_FINAL("ACC Semi Final", CCType.StageType.KNOCKOUT, CCType.CompType.ACC, true),
        ACC_FINAL("African Champions Cup Final", CCType.StageType.KNOCKOUT, CCType.CompType.ACC, false),

        ACF_Q1("ACF Qualifying Round 1", CCType.StageType.KNOCKOUT, CCType.CompType.ACF, true),
        ACF_Q2("ACF Qualifying Round 2", CCType.StageType.KNOCKOUT, CCType.CompType.ACF, true),
        ACF_GROUP_STAGE("ACF Group Stage", CCType.StageType.GROUPS, CCType.CompType.ACF, false),
        ACF_QUARTER_FINAL("ACF Quarter Final", CCType.StageType.KNOCKOUT, CCType.CompType.ACF, false),
        ACF_SEMI_FINAL("ACF Semi Final", CCType.StageType.KNOCKOUT, CCType.CompType.ACF, true),
        ACF_FINAL("African Confederation Cup Final", CCType.StageType.KNOCKOUT, CCType.CompType.ACF, false);

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

    private static int NUM_POTS = 4;
    private static int NUM_GROUPS = 4;

    private static Map<String, SeedingType> seedings = new HashMap<>();
    private static Map<Stage, List<TwoLeggedKnockoutTie>> stages = new HashMap<>();
    private static Map<Stage, League[]> groups = new HashMap<>();
    private static Map<String, Integer> team_nations = new HashMap<>();
    private static String acc_champion = null;

    private static int getPlaces(int rank, CCType.CompType comp) {
        if (comp == CCType.CompType.ACC) {
            return (rank <= 12) ? 2 : 1;
        } else if (comp == CCType.CompType.ACF) {
            return (rank <= 12) ? 2 : 1;
        }
        return 0;
    }

    private static int getTotalPlaces(int rank) {
        return getPlaces(rank, CCType.CompType.ACC) + getPlaces(rank, CCType.CompType.ACF);
    }

    private static SeedingType getSeeding(Nation nation, int rank, int leg_rank) {
        if (rank == 1)
            return SeedingType.ACC_Q1;
        else if (rank == 2) {
            if (leg_rank <= 12)
                return SeedingType.ACC_Q1;
            else
                return SeedingType.ACF_Q1;
        } else if (rank == 3) {
            if (leg_rank <= 12)
                return SeedingType.ACF_Q1;
        } else if (rank == 4) {
            if (leg_rank <= 12)
                return SeedingType.ACF_Q1;
        }
        return SeedingType.DEFAULT;
    }

    private static void setDefaultChampion() {
        List<Nation> nations = Sim.getNationFromContinent(CONTINENT);
        int least_rank = nations.get(0).getLeagueRanking();
        Nation nid = nations.get(0);
        for (Nation n : nations) {
            if (least_rank > n.getLeagueRanking()) {
                least_rank = n.getLeagueRanking();
                nid = n;
            }
        }
        List<String> nteams = nid.getTeamIds(1);
        acc_champion = Sim.sortByReputation(nteams).get(0);
    }

    private static void setPreviousChampions() {
        if (Sim.isFirstSeason()) {
            setDefaultChampion();
        } else {
            int last_season = Sim.getSeasonNumber() - 1;
            acc_champion = getCompetitionHistory(CCType.CompType.ACC, last_season).getWinner();
        }
    }

    private static void checkAndAddChampion(List<String> teams, int ranking) {
        int acc_places = getPlaces(ranking, CCType.CompType.ACC);
        if (!teams.contains(acc_champion)) {
            teams.add(0, acc_champion);
            teams.subList(0, teams.size() - 1);
        }
        if (!teams.subList(0, Math.min(acc_places, teams.size())).contains(acc_champion)) {
            boolean wasInList = teams.remove(acc_champion);
            teams.add(0, acc_champion);
            if (!wasInList)
                teams.remove(teams.size() - 1);
        }
    }

    private static void setSeedings() {
        List<Nation> nations = Sim.getNationFromContinent(CONTINENT);
        Map<Integer, Integer> league_rankings = getLeagueRankingsForSeason(Sim.getSeasonNumber());
        int acc_champ_nation = Sim.getClub(acc_champion).getNationId();
        for (Nation nation : nations) {
            int ranking = league_rankings.get(nation.getId());
            List<String> teams = getTeams(nation, ranking);
            if (acc_champ_nation == nation.getId()) {
                checkAndAddChampion(teams, ranking);
            }

            for (int i = 0; i < teams.size(); i++) {
                int rank = i + 1;
                SeedingType seedingType = getSeeding(nation, rank, ranking);
                seedings.put(teams.get(i), seedingType);
                team_nations.put(teams.get(i), nation.getId());
            }
        }
    }

    private static void adjustSeedingsbyCoefficient(CCType.CompType comp) {
        SeedingType sd = comp == CCType.CompType.ACC ? SeedingType.ACC_Q1 : SeedingType.ACF_Q1;
        SeedingType sn = comp == CCType.CompType.ACC ? SeedingType.ACC_Q2 : SeedingType.ACF_Q2;

        List<String> teamsList = new ArrayList<>();
        for (String t : seedings.keySet()) {
            if (seedings.get(t) == sd) {
                teamsList.add(t);
            }
        }
        int to_move = (NUM_GROUPS * NUM_POTS * 4) - teamsList.size();
        teamsList = sortByCoefficients(teamsList);
        for (int i = 0; i < to_move; i++) {
            seedings.put(teamsList.get(i), sn);
        }
    }

    private static List<String> getTeams(Nation nation, int ranking) {
        Division league = nation.getDivision(1);
        int places = getTotalPlaces(ranking);
        List<String> teams_list = new ArrayList<>();
        for (int i = 1; i <= places; i++) {
            teams_list.add(league.getTeamAtRank(i));
        }
        return teams_list;
    }

    private static SeedingType getSeedingTypeForStage(Stage stage) {
        switch (stage) {
            case ACC_Q1:
                return SeedingType.ACC_Q1;
            case ACC_Q2:
                return SeedingType.ACC_Q2;
            case ACF_Q1:
                return SeedingType.ACF_Q1;
            case ACF_Q2:
                return SeedingType.ACF_Q2;
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
            case ACC_FINAL:
                stages.add(Stage.ACC_SEMI_FINAL);
                break;
            case ACC_SEMI_FINAL:
                stages.add(Stage.ACC_QUARTER_FINAL);
                break;
            case ACC_GROUP_STAGE:
                stages.add(Stage.ACC_Q2);
                break;
            case ACC_Q2:
                stages.add(Stage.ACC_Q1);
                break;
            case ACF_FINAL:
                stages.add(Stage.ACF_SEMI_FINAL);
                break;
            case ACF_SEMI_FINAL:
                stages.add(Stage.ACF_QUARTER_FINAL);
                break;
            case ACF_GROUP_STAGE:
                stages.add(Stage.ACF_Q2);
                break;
            case ACF_Q2:
                stages.add(Stage.ACF_Q1);
                break;
            default:
                break;
        }
        return stages;
    }

    private static List<String> getWinnersFromStage(Stage stage) {
        List<String> winners = new ArrayList<>();
        List<TwoLeggedKnockoutTie> ties = stages.get(stage);
        for (TwoLeggedKnockoutTie tie : ties) {
            winners.add(tie.getWinner());
        }
        return winners;
    }

    private static boolean isStageGroup(Stage stage) {
        return stage.stage_type() == CCType.StageType.GROUPS;
    }

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
                pot_teams.remove(selected_team);
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
            List<TwoLeggedKnockoutTie> ties = new ArrayList<>();
            int ties_size = teams.size() / 2;
            for (int i = 0; i < ties_size; i++) {
                String selected_team = CommonFunctions.getRandomElement(seeded);
                List<String> filtered = unseeded;
                if (filtered.size() == 0)
                    System.out.println("No team found in stage: " + type);
                String selected_team_2 = CommonFunctions.getRandomElement(filtered);
                TwoLeggedKnockoutTie tie = new TwoLeggedKnockoutTie(selected_team, selected_team_2, 1);
                ties.add(tie);
                seeded.remove(selected_team);
                unseeded.remove(selected_team_2);
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
        List<TwoLeggedKnockoutTie> ties = stages.get(stage);
        for (int i = 0; i < ties.size(); i++) {
            ties.get(i).simulate();
            System.out.print("Match " + (i + 1) + ": ");
            ties.get(i).showContinental();
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
            gLeagues[i].getTable().showLongContinental();
        }
    }

    private static void simulateGroupStage() {
        for (int i = 1; i <= 6; i++) {
            simulateGroupsMatchDay(Stage.ACC_GROUP_STAGE, i);
            simulateGroupsMatchDay(Stage.ACF_GROUP_STAGE, i);
        }
        // Show Tables
        System.out.println("\nStandings: ACC Group Stage");
        dispGroupTables(Stage.ACC_GROUP_STAGE);

        System.out.println("\nStandings: ACF Group Stage");
        dispGroupTables(Stage.ACF_GROUP_STAGE);
    }

    private static List<String> getGroupTeamsAtRank(Stage stage, int rank) {
        League[] leagues = groups.get(stage);
        List<String> teams = new ArrayList<>();
        for (int i = 0; i < leagues.length; i++) {
            teams.add(leagues[i].getTeamAtRank(rank));
        }
        return teams;
    }

    private static void setQFStage(Stage from, Stage to) {
        ArrayList<String> seeded = new ArrayList<>();
        ArrayList<String> unseeded = new ArrayList<>();

        seeded.addAll(getGroupTeamsAtRank(from, 1));
        unseeded.addAll(getGroupTeamsAtRank(from, 2));

        List<TwoLeggedKnockoutTie> ties = new ArrayList<>();
        int ties_size = seeded.size();
        for (int i = 0; i < ties_size; i++) {
            String selected_team = CommonFunctions.getRandomElement(seeded);
            String selected_team_2 = CommonFunctions.getRandomElement(unseeded);
            TwoLeggedKnockoutTie tie = new TwoLeggedKnockoutTie(selected_team, selected_team_2, 1);
            ties.add(tie);
            seeded.remove(selected_team);
            unseeded.remove(selected_team_2);
        }
        stages.put(to, ties);
    }

    private static void adjustSeedings() {
        adjustSeedingsbyCoefficient(CCType.CompType.ACC);
        adjustSeedingsbyCoefficient(CCType.CompType.ACF);
    }

    private static void qualifiers() {
        setStage(Stage.ACC_Q1);
        simulateStage(Stage.ACC_Q1);

        setStage(Stage.ACF_Q1);
        simulateStage(Stage.ACF_Q1);

        setStage(Stage.ACC_Q2);
        simulateStage(Stage.ACC_Q2);

        setStage(Stage.ACF_Q2);
        simulateStage(Stage.ACF_Q2);
    }

    private static void setGroups() {
        setStage(Stage.ACC_GROUP_STAGE);
        setStage(Stage.ACF_GROUP_STAGE);
    }

    private static void setQFStage() {
        setQFStage(Stage.ACC_GROUP_STAGE, Stage.ACC_QUARTER_FINAL);
        setQFStage(Stage.ACF_GROUP_STAGE, Stage.ACF_QUARTER_FINAL);
    }

    private static void knockouts() {
        simulateStage(Stage.ACC_QUARTER_FINAL);
        simulateStage(Stage.ACF_QUARTER_FINAL);

        setStage(Stage.ACF_SEMI_FINAL);
        setStage(Stage.ACC_SEMI_FINAL);

        simulateStage(Stage.ACF_SEMI_FINAL);
        simulateStage(Stage.ACC_SEMI_FINAL);

        setStage(Stage.ACF_FINAL);
        setStage(Stage.ACC_FINAL);

        simulateStage(Stage.ACF_FINAL);
        simulateStage(Stage.ACC_FINAL);
    }

    public static void simulate() {
        seedings = new HashMap<>();
        stages = new HashMap<>();
        groups = new HashMap<>();
        team_nations = new HashMap<>();
        acc_champion = null;
        setPreviousChampions();
        setSeedings();
        adjustSeedings();
        qualifiers();
        setGroups();
        simulateGroupStage();
        setQFStage();
        knockouts();
        updateCompetitionHistory();
        updateCoefficients();
        showLeagueRankings();
    }

    // #region CompetitionHistory
    private static Map<CCType.CompType, List<CompetitionHistory>> history = new HashMap<>();

    private static void updateHistory(CCType.CompType type) {
        Stage stageFinal = type == CCType.CompType.ACC ? Stage.ACC_FINAL : Stage.ACF_FINAL;
        List<TeamHistory> tHistory = new ArrayList<>();
        TwoLeggedKnockoutTie tie = stages.get(stageFinal).get(0);
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
        updateHistory(CCType.CompType.ACF);
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
        showHistory(CCType.CompType.ACF);
    }

    public static Map<CCType.CompType, List<CompetitionHistory>> getHistory() {
        return history;
    }

    private static CompetitionHistory getCompetitionHistory(CCType.CompType type, int season) {
        List<CompetitionHistory> compHistory = history.get(type);
        for (CompetitionHistory c : compHistory) {
            if (c.getSeason() == season)
                return c;
        }
        return null;
    }
    // #endregion CompetitionHistory

    // #region Coefficients
    private static Map<Integer, Map<Integer, Double>> nation_coefficients;
    private static Map<Integer, Map<String, Double>> club_coefficients;

    public static void initializeCoefficients() {
        nation_coefficients = new HashMap<>();
        club_coefficients = new HashMap<>();

        List<Nation> nations = Sim.getNationFromContinent(CONTINENT);
        for (int i = Sim.STARTING_SEASON - 5; i <= Sim.STARTING_SEASON - 1; i++) {
            Map<Integer, Double> p_map = new HashMap<>();
            for (Nation nation : nations) {
                int r = nation.getLeagueRanking();
                double p = 0.0;
                if (r <= 13) {
                    p = (13 - r) / 2.0;
                }
                p_map.put(nation.getId(), p);
            }
            nation_coefficients.put(Integer.valueOf(i), p_map);
        }
    }

    public static Map<Integer, Integer> getLeagueRankingsForSeason(int season) {
        int start = season - 5, end = season - 1;
        Map<Nation, Double> points = new HashMap<>();
        List<Nation> nations = Sim.getNationFromContinent(CONTINENT);
        for (Nation nation : nations) {
            double point = 0.0;
            for (int year = start; year <= end; year++) {
                int multiplier = year - start + 1;
                Map<Integer, Double> year_map = nation_coefficients.get(year);
                point += year_map.get(nation.getId()) * multiplier;
            }
            points.put(nation, point);
        }
        List<Map.Entry<Nation, Double>> list = new ArrayList<>(points.entrySet());
        Collections.sort(list, (o1, o2) -> Double.compare(o2.getValue(), o1.getValue()));
        int count = 1;
        Map<Integer, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<Nation, Double> aa : list) {
            temp.put(aa.getKey().getId(), count);
            count++;
        }
        return temp;
    }

    private static double getCoefficientForTeam(String team, int season) {
        int start = season - 5, end = season - 1;
        double points = 0;
        for (int year = start; year <= end; year++) {
            int multiplier = year - start + 1;
            if (club_coefficients.containsKey(year)) {
                Map<String, Double> map = club_coefficients.get(year);
                if (map.containsKey(team)) {
                    points += map.get(team) * multiplier;
                }
            }
        }
        return points;
    }

    public static List<String> sortByCoefficients(List<String> teams_to_sort) {
        int season = Sim.getSeasonNumber();
        Collections.sort(teams_to_sort,
                (String a, String b) -> (int) (getCoefficientForTeam(b, season) - getCoefficientForTeam(a, season)));
        return teams_to_sort;
    }

    // current season variables
    private static Map<Integer, Double> nation_points;
    private static Map<String, Double> club_points;

    private static void setPointsForClub(String c, double p) {
        club_points.put(c, p);

        int nid = team_nations.get(c);
        double d = nation_points.get(nid);
        d = d + p;
        nation_points.put(nid, d);
    }

    private static void setPointsForLoser(Stage stage, double p) {
        List<TwoLeggedKnockoutTie> kt = stages.get(stage);
        for (TwoLeggedKnockoutTie k : kt) {
            setPointsForClub(k.getLoser(), p);
        }
    }

    private static void setPointsForGroupPosition(Stage stage, int rank, double p) {
        League[] g = groups.get(stage);
        for (League l : g) {
            setPointsForClub(l.getTeamAtRank(rank), p);
        }
    }

    private static void initCoefficients() {
        List<Nation> nations = Sim.getNationFromContinent(CONTINENT);

        club_points = new HashMap<>();
        nation_points = new HashMap<>();

        for (Nation n : nations) {
            nation_points.put(n.getId(), 0.0);
        }
    }

    private static void setCoefficientPoints() {
        List<TwoLeggedKnockoutTie> fin = stages.get(Stage.ACC_FINAL);
        setPointsForClub(fin.get(0).getWinner(), 6.0);
        setPointsForClub(fin.get(0).getLoser(), 5.0);
        setPointsForLoser(Stage.ACC_SEMI_FINAL, 4.0);
        setPointsForLoser(Stage.ACC_QUARTER_FINAL, 3.0);
        setPointsForGroupPosition(Stage.ACC_GROUP_STAGE, 3, 2.0);
        setPointsForGroupPosition(Stage.ACC_GROUP_STAGE, 4, 1.0);

        List<TwoLeggedKnockoutTie> fig = stages.get(Stage.ACF_FINAL);
        setPointsForClub(fig.get(0).getWinner(), 5.0);
        setPointsForClub(fig.get(0).getLoser(), 4.0);
        setPointsForLoser(Stage.ACF_SEMI_FINAL, 3.0);
        setPointsForLoser(Stage.ACF_QUARTER_FINAL, 2.0);
        setPointsForGroupPosition(Stage.ACF_GROUP_STAGE, 3, 1.0);
        setPointsForGroupPosition(Stage.ACF_GROUP_STAGE, 4, 0.5);

        club_coefficients.put(Sim.getSeasonNumber(), club_points);
        nation_coefficients.put(Sim.getSeasonNumber(), nation_points);
    }

    public static void showLeagueRankings() {
        Map<Integer, Map<Integer, Integer>> map = new HashMap<>();
        System.out.print(CommonFunctions.getPaddedString("Country", 25));
        for (int year = Sim.STARTING_SEASON; year <= Sim.getSeasonNumber(); year++) {
            Map<Integer, Integer> ymap = getLeagueRankingsForSeason(year);
            map.put(year, ymap);
            System.out.print(CommonFunctions.getPaddedString(String.valueOf(year), 7));
        }
        System.out.println();
        List<Nation> nations = Sim.getNationFromContinent(CONTINENT);
        for (Nation nation : nations) {
            System.out.print(CommonFunctions.getPaddedString(nation.getName(), 25));
            for (int year = Sim.STARTING_SEASON; year <= Sim.getSeasonNumber(); year++) {
                System.out.print(CommonFunctions.getPaddedString(String.valueOf(map.get(year).get(nation.getId())), 7));
            }
            System.out.println();
        }
    }

    private static void updateCoefficients() {
        initCoefficients();
        setCoefficientPoints();
    }
    // #endregion Coefficients
}
