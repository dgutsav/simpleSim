import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerNAmerica {
    private enum SeedingType {
        ROUND_16,
        DEFAULT
    }

    private enum Stage {
        ROUND_16("NACL Round of 16", CCType.StageType.KNOCKOUT, CCType.CompType.NACL, false),
        QUARTER_FINAL("NACL Quarter Final", CCType.StageType.KNOCKOUT, CCType.CompType.NACL, false),
        SEMI_FINAL("NACL Semi Final", CCType.StageType.KNOCKOUT, CCType.CompType.NACL, false),
        FINAL("North American Champions League Final", CCType.StageType.KNOCKOUT, CCType.CompType.NACL, false);

        private final String stage_name;

        Stage(String stage_name, CCType.StageType stage_type, CCType.CompType competition, boolean qualifying) {
            this.stage_name = stage_name;
        }
    }

    private static Map<String, SeedingType> seedings = new HashMap<>();
    private static Map<Stage, List<KnockoutTie>> stages = new HashMap<>();

    private static int getPlaces(Nation nation) {
        int rank = nation.getLeagueRanking();
        if (rank <= 2) return 4;
        else if (rank <= 3) return 3;
        else if (rank <= 5) return 2;
        else if (rank <= 6) return 1;
        return 0;
    }

    private static SeedingType getSeeding(Nation nation, int rank) {
        return SeedingType.ROUND_16;
    }

    private static void setSeedings() {
        List<Nation> nations = Sim.getNationFromContinent("North America");
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
            case ROUND_16: return SeedingType.ROUND_16;
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
            case QUARTER_FINAL:
                stages.add(Stage.ROUND_16);
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
    }

    private static void simulateStage(Stage stage) {
        System.out.println("\n" + stage.stage_name);
        List<KnockoutTie> ties = stages.get(stage);
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
        setStage(Stage.ROUND_16);
        simulateStage(Stage.ROUND_16);
        setStage(Stage.QUARTER_FINAL);
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
