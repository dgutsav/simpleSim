import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerCWC {

    private static List<String> teams = new ArrayList<>();

    private static String getCompWinner(List<CompetitionHistory> compHistory, int year) {
        if (compHistory == null) System.out.println("Comp History is null");
        for (CompetitionHistory comp : compHistory) {
            if (comp.getSeason() == year) {
                System.out.println("Comp winner found: " + Sim.getClub(comp.getTeamHistory(1).getTeamId()).getName());
                return comp.getTeamHistory(1).getTeamId();
            }
        }
        System.out.println("NO Team found");
        return null;
    }

    private static void getTeams() {
        int year = Sim.getSeasonNumber();
        Map<CCType.CompType, List<CompetitionHistory>> europeHistory = ControllerEurope.getHistory();
        teams.add(getCompWinner(europeHistory.get(CCType.CompType.UCL), year));
        teams.add(getCompWinner(europeHistory.get(CCType.CompType.UEL), year));
        teams.add(getCompWinner(europeHistory.get(CCType.CompType.UECL), year));
        Map<CCType.CompType, List<CompetitionHistory>> latamHistory = ControllerSAmerica.getHistory();
        teams.add(getCompWinner(latamHistory.get(CCType.CompType.LIB), year));
        teams.add(getCompWinner(latamHistory.get(CCType.CompType.SUD), year));
        Map<CCType.CompType, List<CompetitionHistory>> nortAmHistory = ControllerNAmerica.getHistory();
        teams.add(getCompWinner(nortAmHistory.get(CCType.CompType.NACL), year));
        Map<CCType.CompType, List<CompetitionHistory>> asiaHistory = ControllerAsia.getHistory();
        teams.add(getCompWinner(asiaHistory.get(CCType.CompType.ACL), year));
        Map<CCType.CompType, List<CompetitionHistory>> africaHistory = ControllerAfrica.getHistory();
        teams.add(getCompWinner(africaHistory.get(CCType.CompType.ACC), year));
    }

    private enum Stage {
        GROUP_STAGE("CWC Group Stage", CCType.StageType.GROUPS, CCType.CompType.CWC, false),
        SEMI_FINAL("CWC Semi Final", CCType.StageType.KNOCKOUT, CCType.CompType.CWC, true),
        FINAL("Club World Championship Final", CCType.StageType.KNOCKOUT, CCType.CompType.CWC, false);

        private final String stage_name;

        Stage(String stage_name, CCType.StageType stage_type, CCType.CompType competition, boolean qualifying) {
            this.stage_name = stage_name;
        }

        public String stage_name() {
            return this.stage_name;
        }
    }

    private static Map<Stage, List<TwoLeggedKnockoutTie>> stages = new HashMap<>();
    private static Map<Stage, League[]> groups = new HashMap<>();

    private static void setGroups() {
        teams = Sim.sortByReputation(teams);
        int NUM_GROUPS = 2;
        League[] stage_groups = new League[NUM_GROUPS];
        for (int i = 0; i < NUM_GROUPS; i++) {
            League league = new League();
            for (int j = i; j < teams.size(); j += NUM_GROUPS) {
                league.addTeam(teams.get(j));
            }
            league.setFixtures();
            league.setupTable();
            stage_groups[i] = league;
        }
        groups.put(Stage.GROUP_STAGE, stage_groups);
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
            simulateGroupsMatchDay(Stage.GROUP_STAGE, i);
        }
        // Show Tables
        System.out.println("\nStandings");
        dispGroupTables(Stage.GROUP_STAGE);
    }

    private static void setSemiFinals() {
        League[] group_leagues = groups.get(Stage.GROUP_STAGE);
        int groups_count = group_leagues.length;
        List<TwoLeggedKnockoutTie> ties = new ArrayList<>();
        for (int i = 0; i < groups_count; i += 2) {
            ties.add(new TwoLeggedKnockoutTie(group_leagues[i].getTeamAtRank(1), group_leagues[i + 1].getTeamAtRank(2),
                    1));
            ties.add(new TwoLeggedKnockoutTie(group_leagues[i].getTeamAtRank(2), group_leagues[i + 1].getTeamAtRank(1),
                    1));
        }
        stages.put(Stage.SEMI_FINAL, ties);
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

    private static void setFinal() {
        List<String> finalists = new ArrayList<>();
        for (TwoLeggedKnockoutTie tie : stages.get(Stage.SEMI_FINAL)) {
            finalists.add(tie.getWinner());
        }
        List<TwoLeggedKnockoutTie> ties = new ArrayList<>();
        ties.add(new TwoLeggedKnockoutTie(finalists.get(0), finalists.get(1), 1));
        stages.put(Stage.FINAL, ties);
    }

    public static void simulate() {
        teams = new ArrayList<>();
        stages = new HashMap<>();
        groups = new HashMap<>();
        getTeams();
        setGroups();
        simulateGroupStage();
        setSemiFinals();
        simulateStage(Stage.SEMI_FINAL);
        setFinal();
        simulateStage(Stage.FINAL);
        updateCompetitionHistory();
    }

    // #region CompetitionHistory
    private static Map<CCType.CompType, List<CompetitionHistory>> history = new HashMap<>();

    private static void updateHistory(CCType.CompType type) {
        Stage stageFinal = Stage.FINAL;
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
        updateHistory(CCType.CompType.CWC);
    }

    private static void showHistory(CCType.CompType type) {
        System.out.println(type.title());
        List<CompetitionHistory> cHist = history.get(type);
        for (CompetitionHistory compHistory : cHist) {
            compHistory.show(2, false);
        }
    }

    public static void showCompetitionHistory() {
        showHistory(CCType.CompType.CWC);
    }

    public static Map<CCType.CompType, List<CompetitionHistory>> getHistory() {
        return history;
    }
}
