import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerNAmerica {
    private static final String CONTINENT = "North America";

    private static class StageData {
        public Stage stage;
        public List<Stage> winnersFrom;
        public List<Stage> losersFrom;
        public SeedingType seeding;
        public Stage after;

        public StageData(Stage stage) {
            this.stage = stage;
            this.winnersFrom = new ArrayList<>();
            this.losersFrom = new ArrayList<>();
            this.after = null;
            this.seeding = null;
        }

        public StageData winner(Stage stage) {
            this.winnersFrom.add(stage);
            this.after = stage;
            return this;
        }

        public StageData winners(List<Stage> stages) {
            this.winnersFrom.addAll(stages);
            return this;
        }

        public StageData loser(Stage stage) {
            this.losersFrom.add(stage);
            return this;
        }

        public StageData losers(List<Stage> stages) {
            this.losersFrom.addAll(stages);
            return this;
        }

        public StageData after(Stage stage) {
            this.after = stage;
            return this;
        }

        public StageData seeding(SeedingType seeding) {
            this.seeding = seeding;
            return this;
        }
    }

    private enum Zone {
        NORTH,
        CERNTRAL,
        CARRIBEAN,
        DEFAULT
    }

    private enum SeedingType {
        CAC_GROUP,
        NACL_ROUND_16,
        DEFAULT
    }

    private enum Stage {
        CAC_GROUP("CAC Group Stage", CCType.StageType.GROUPS, CCType.CompType.NACL, false),
        CAC_QUARTER_FINAL("CAC Quarter Final", CCType.StageType.KNOCKOUT, CCType.CompType.NACL, false),
        CAC_SEMI_FINAL("CAC Semi Final", CCType.StageType.KNOCKOUT, CCType.CompType.NACL, false),
        CAC_PLAY_IN("CAC Play-In", CCType.StageType.KNOCKOUT, CCType.CompType.NACL, false),
        CAC_FINAL("Central American Cup Final", CCType.StageType.KNOCKOUT, CCType.CompType.NACL, false),

        NACL_ROUND_16("NACL Round of 16", CCType.StageType.KNOCKOUT, CCType.CompType.NACL, false),
        NACL_QUARTER_FINAL("NACL Quarter Final", CCType.StageType.KNOCKOUT, CCType.CompType.NACL, false),
        NACL_SEMI_FINAL("NACL Semi Final", CCType.StageType.KNOCKOUT, CCType.CompType.NACL, false),
        NACL_FINAL("North American Champions League Final", CCType.StageType.KNOCKOUT, CCType.CompType.NACL, false);

        private final String stage_name;

        Stage(String stage_name, CCType.StageType stage_type, CCType.CompType competition, boolean qualifying) {
            this.stage_name = stage_name;
        }
    }

    private static Map<String, SeedingType> seedings = new HashMap<>();
    private static Map<Stage, List<TwoLeggedKnockoutTie>> stages = new HashMap<>();
    private static Map<Integer, Integer> zonal_rankings = new HashMap<>();

    private static String[] central_american_nations = { "Nicaragua", "El Salvador", "Costa Rica", "Panama",
            "Guatemala", "Honduras", "Belize" };
    private static String[] north_american_nations = { "United States", "Canada", "Mexico" };
    private static String[] carribean_nations = { "Jamaica" };

    private static List<String> central_america = new ArrayList<>(Arrays.asList(central_american_nations));
    private static List<String> north_america = new ArrayList<>(Arrays.asList(north_american_nations));
    private static List<String> carribean = new ArrayList<>(Arrays.asList(carribean_nations));

    private static Map<Stage, StageData> stage_data = new HashMap<>();

    private static void addStage(StageData data) {
        stage_data.put(data.stage, data);
    }

    private static void initialize() {
        addStage(new StageData(Stage.CAC_GROUP).seeding(SeedingType.CAC_GROUP));
        addStage(new StageData(Stage.CAC_QUARTER_FINAL).after(Stage.CAC_GROUP));
        addStage(new StageData(Stage.CAC_SEMI_FINAL).winner(Stage.CAC_QUARTER_FINAL));
        addStage(new StageData(Stage.CAC_PLAY_IN).loser(Stage.CAC_QUARTER_FINAL).after(Stage.CAC_QUARTER_FINAL));
        addStage(new StageData(Stage.CAC_FINAL).winner(Stage.CAC_SEMI_FINAL));
    }

    private static Zone getZone(Nation nation) {
        String natname = nation.getName();
        if (central_america.contains(natname))
            return Zone.CERNTRAL;
        else if (north_america.contains(natname))
            return Zone.NORTH;
        else if (carribean.contains(natname))
            return Zone.CARRIBEAN;
        else
            return Zone.DEFAULT;
    }

    private static void setZonalRankings() {
        List<Nation> nations = Sim.getNationFromContinent(CONTINENT);
        Collections.sort(nations, Comparator.comparing(Nation::getLeagueRanking));
        int central_rank = 1, north_rank = 1, carib_rank = 1;
        for (Nation nation : nations) {
            if (getZone(nation) == Zone.NORTH) {
                zonal_rankings.put(nation.getId(), north_rank++);
            } else if (getZone(nation) == Zone.CARRIBEAN) {
                zonal_rankings.put(nation.getId(), carib_rank++);
            } else if (getZone(nation) == Zone.CERNTRAL) {
                zonal_rankings.put(nation.getId(), central_rank++);
            }
        }
    }

    private static int getZonalRanking(Nation nation) {
        return zonal_rankings.get(nation.getId());
    }

    private static int getZonalRanking(int nation_id) {
        return zonal_rankings.get(nation_id);
    }

    private static int getPlaces(Zone zone, int ranking) {
        if (zone == Zone.CERNTRAL) {
            if (ranking <= 2)
                return 4;
            else if (ranking <= 5)
                return 3;
            else if (ranking <= 6)
                return 2;
            else if (ranking <= 7)
                return 1;
        }
        return 0;
    }

    private static SeedingType getSeeding(Zone zone, int league_ranking, int rank) {
        if (zone == Zone.CERNTRAL) {
            return SeedingType.CAC_GROUP;
        }
        return SeedingType.DEFAULT;
    }

    private static void setSeedings() {
        List<Nation> nations = Sim.getNationFromContinent(CONTINENT);
        for (Nation nation : nations) {
            int ranking = getZonalRanking(nation);
            List<String> teams = getTeams(nation, ranking);
            for (int i = 0; i < teams.size(); i++) {
                int rank = i + 1;
                SeedingType seedingType = getSeeding(getZone(nation), ranking, rank);
                seedings.put(teams.get(i), seedingType);
            }
        }
    }

    private static List<String> getTeams(Nation nation, int ranking) {
        Division league = nation.getDivision(1);
        int places = getPlaces(getZone(nation), ranking);
        List<String> teams_list = new ArrayList<>();
        for (int i = 1; i <= places; i++) {
            teams_list.add(league.getTeamAtRank(i));
        }
        return teams_list;
    }

    private static SeedingType getSeedingTypeForStage(Stage stage) {
        if (stage_data.containsKey(stage))
            return stage_data.get(stage).seeding;
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
            case NACL_FINAL:
                stages.add(Stage.NACL_SEMI_FINAL);
                break;
            case NACL_SEMI_FINAL:
                stages.add(Stage.NACL_QUARTER_FINAL);
                break;
            case NACL_QUARTER_FINAL:
                stages.add(Stage.NACL_ROUND_16);
                break;
            default:
                break;
        }
        return stages;
    }

    private static List<String> getTeamsFromStage(Stage stage, boolean winner) {
        List<String> teams = new ArrayList<>();
        List<TwoLeggedKnockoutTie> ties = stages.get(stage);
        for (TwoLeggedKnockoutTie tie : ties) {
            if (winner)
                teams.add(tie.getWinner());
            else
                teams.add(tie.getLoser());
        }
        return teams;
    }

    private static List<String> getWinnersFromStage(Stage stage) {
        return getTeamsFromStage(stage, true);
    }

    private static List<String> getLosersFromStage(Stage stage) {
        return getTeamsFromStage(stage, false);
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

    private static List<String> getTeamsForStage(Stage stage) {
        if (stage_data.containsKey(stage)) {
            StageData sdata = stage_data.get(stage);
            List<String> t = new ArrayList<>();
            t.addAll(getTeamsWithSeeding(sdata.seeding));
            for (Stage s : sdata.winnersFrom)
                t.addAll(getWinnersFromStage(s));
            for (Stage s : sdata.losersFrom)
                t.addAll(getLosersFromStage(s));
            return t;
        }
        return new ArrayList<>();
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
            seeded = removeFromList(seeded, selected_team);
            unseeded = removeFromList(unseeded, selected_team_2);
        }
        stages.put(type, ties);
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

    public static void simulate() {
        seedings = new HashMap<>();
        stages = new HashMap<>();
        setSeedings();
        setStage(Stage.NACL_ROUND_16);
        simulateStage(Stage.NACL_ROUND_16);
        setStage(Stage.NACL_QUARTER_FINAL);
        simulateStage(Stage.NACL_QUARTER_FINAL);
        setStage(Stage.NACL_SEMI_FINAL);
        simulateStage(Stage.NACL_SEMI_FINAL);
        setStage(Stage.NACL_FINAL);
        simulateStage(Stage.NACL_FINAL);
        updateCompetitionHistory();
    }

    // #region CompetitionHistory
    private static Map<CCType.CompType, List<CompetitionHistory>> history = new HashMap<>();

    private static void updateHistory(CCType.CompType type) {
        Stage stageFinal = Stage.NACL_FINAL;
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
        updateHistory(CCType.CompType.NACL);
    }

    private static void showHistory(CCType.CompType type) {
        System.out.println(type.title());
        List<CompetitionHistory> cHist = history.get(type);
        for (CompetitionHistory compHistory : cHist) {
            compHistory.show(2, false);
        }
    }

    public static void showCompetitionHistory() {
        showHistory(CCType.CompType.NACL);
    }

    public static Map<CCType.CompType, List<CompetitionHistory>> getHistory() {
        return history;
    }
    // #endregion CompetitionHistory

}
