import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class International {
    private enum QGroupConfig {
        ASIA("Asian WC Qualifiers Group Stage", "Asia", 3, 6, 3, 6),
        EUROPE("European WC Qualifiers Group Stage", "Europe", 10, 10, 2, 5),
        AFRICA("African WC Qualifiers Group Stage", "Africa", 4, 4, -1, 4),
        S_AMERICA("Sudamericana WC Qualifiers Group Stage", "South America", 1, 5, -1, 10),
        N_AMERICA("North American WC Qualifiers Group Stage", "North America", 1, 4, -1, 6);

        private String stage_name;
        private int num_groups;
        private int direct_qualifiers;
        private int play_off_rank;
        private int teams_per_group;
        private String continent;

        private QGroupConfig(String stage_name, String continent, int num_groups, int direct_qualifiers,
                int play_off_rank, int teams_per_group) {
            this.stage_name = stage_name;
            this.num_groups = num_groups;
            this.direct_qualifiers = direct_qualifiers;
            this.play_off_rank = play_off_rank;
            this.continent = continent;
            this.teams_per_group = teams_per_group;
        }

        public String stage_name() {
            return this.stage_name;
        }

        public int num_groups() {
            return this.num_groups;
        }

        public int direct_qualifiers() {
            return this.direct_qualifiers;
        }

        public int play_off_rank() {
            return this.play_off_rank;
        }

        public int teams_per_group() {
            return this.teams_per_group;
        }

        public String continent() {
            return this.continent;
        }
    }

    private enum MatchLevel {
        QUALIFIERS(1),
        WC_GROUP_STAGE(2),
        WC_KNOCKOUTS(3),
        CC_GROUP_STAGE(4),
        CC_KNOCKOUTS(5),
        FRIENDLY(0);

        private int level;

        private MatchLevel(int level) {
            this.level = level;
        }

        public int level() {
            return this.level;
        }
    }

    private static HashMap<QGroupConfig, League[]> wcq_groups = new HashMap<>();
    private static List<String> wc_qualifiers = new ArrayList<>();

    private static void setupWCQGroups() {
        setupWCQGroup(QGroupConfig.AFRICA);
        setupWCQGroup(QGroupConfig.ASIA);
        setupWCQGroup(QGroupConfig.EUROPE);
        setupWCQGroup(QGroupConfig.S_AMERICA);
        setupWCQGroup(QGroupConfig.N_AMERICA);
    }

    private static void setupWCQGroup(QGroupConfig config) {
        List<NationalTeam> teams = sortByPoints(Sim.getNationFromContinent(config.continent().toLowerCase()));
        int[][] groups = drawIntoGroups(divideIntoPots(teams.size(), config.teams_per_group()), config.num_groups(),
                config.teams_per_group());
        League[] leagues = new League[config.num_groups()];
        for (int i = 0; i < config.num_groups(); i++) {
            League league = new League();
            for (int j = 0; j < config.teams_per_group(); j++) {
                NationalTeam team = teams.get(groups[i][j]);
                team.callup();
                league.addTeam(team.getId());
            }
            List<Match> matches = FixtureScheduler.scheduleInternationalFixtures(league.getTeams(), false, false);
            matches = setAsInternationalMatch(matches, MatchLevel.QUALIFIERS.level());
            league.setFixtures(matches);
            league.setInternational();
            league.setupTable();
            leagues[i] = league;
        }
        wcq_groups.put(config, leagues);
    }

    private static void simulateWCQGroups() {
        simulateWCQGroup(QGroupConfig.AFRICA);
        simulateWCQGroup(QGroupConfig.ASIA);
        simulateWCQGroup(QGroupConfig.EUROPE);
        simulateWCQGroup(QGroupConfig.S_AMERICA);
        simulateWCQGroup(QGroupConfig.N_AMERICA);
    }

    private static void simulateWCQGroup(QGroupConfig config) {
        int rounds = config.teams_per_group() % 2 == 0 ? 2 * (config.teams_per_group() - 1)
                : 2 * config.teams_per_group();
        for (int matchday = 1; matchday <= rounds; matchday++) {
            simulateGroupsMatchDay(config, matchday);
        }
        // Show Tables
        System.out.println("\nStandings");
        dispGroupTables(config);

        int qual_per_group = config.direct_qualifiers() / config.num_groups();
        League[] groups = wcq_groups.get(config);
        for (League league : groups) {
            for (int i = 1; i <= qual_per_group; i++) {
                wc_qualifiers.add(league.getTeamAtRank(i));
            }
        }
    }

    private static void simulateGroupsMatchDay(QGroupConfig config, int matchday) {
        League[] gLeagues = wcq_groups.get(config);
        for (int i = 0; i < gLeagues.length; i++) {
            char ch = (char) (65 + i);
            System.out.println(config.stage_name() + ": Group " + ch + " Matchday " + matchday);
            gLeagues[i].simulateMatchDay(matchday);
        }
    }

    private static void dispGroupTables(QGroupConfig config) {
        System.out.println("\n" + config.stage_name());
        League[] gLeagues = wcq_groups.get(config);
        for (int i = 0; i < gLeagues.length; i++) {
            char ch = (char) (65 + i);
            System.out.println("Group " + ch);
            gLeagues[i].getTable().showLong();
        }
    }

    private static void showQualifiers() {
        System.out.println("Direct Qualifiers");
        int count = 1;
        for (String team : wc_qualifiers) {
            System.out.println(count + " " + Sim.getNationalTeam(team).getName());
            count++;
        }
    }

    public enum EuropeWCQualifiersStage {
        SEMI_FINAL,
        FINAL
    }

    public static List<String> getEuroPlayoffTeams() {
        League[] groups = wcq_groups.get(QGroupConfig.EUROPE);
        List<String> qualifiers = new ArrayList<>();
        List<String> playoffs = new ArrayList<>();
        TeamPerformance[] perf = new TeamPerformance[QGroupConfig.EUROPE.num_groups()];
        int count = 0;
        for (League league : groups) {
            qualifiers.add(league.getTeamAtRank(2));
            playoffs.add(league.getTeamAtRank(3));
            TeamPerformance performance = league.getTable().getTeamPerformance(league.getTeamAtRank(3));
            perf[count] = performance;
            count++;
        }
        Table table = new Table(perf.length, true);
        table.startNewSeason(playoffs);
        table.performances = perf;
        table.sortTable();
        qualifiers.add(table.getTeamAtRank(1));
        qualifiers.add(table.getTeamAtRank(2));
        return qualifiers;
    }

    private static Map<EuropeWCQualifiersStage, List<Match>> euro_wc_playoffs = new HashMap<>();
    private static int EURO_PLAYOFF_QUALIFIERS = 3;
    private static int EURO_PLAYOFF_PARTICIPANTS = 12;
    private static String[][] brackets = new String[EURO_PLAYOFF_QUALIFIERS][EURO_PLAYOFF_PARTICIPANTS
            / EURO_PLAYOFF_QUALIFIERS];

    private static void setEuroPlayoffBrackets() {
        List<String> teams = getEuroPlayoffTeams();
        teams = sortNTByPoints(teams);
        for (int i = 0; i < teams.size(); i += EURO_PLAYOFF_QUALIFIERS) {
            String[] group = new String[EURO_PLAYOFF_QUALIFIERS];
            for (int j = i; j < i + EURO_PLAYOFF_QUALIFIERS; j++) {
                group[j - i] = teams.get(j);
            }
            group = CommonFunctions.shuffle(group);
            for (int a = 0; a < brackets.length; a++) {
                brackets[a][i / EURO_PLAYOFF_QUALIFIERS] = group[a];
            }
        }
    }

    private static void setEuroPlayoffSemiFinals() {
        int matches_per_bracket = (EURO_PLAYOFF_PARTICIPANTS / EURO_PLAYOFF_QUALIFIERS) / 2;
        List<Match> matches = new ArrayList<>();
        for (int i = 0; i < EURO_PLAYOFF_QUALIFIERS; i++) {
            for (int j = 0; j < matches_per_bracket; j++) {
                Match match = new Match();
                match.home_id = brackets[i][(j * 2)];
                match.away_id = brackets[i][(j * 2) + 1];
                match.home_team = Sim.getNationalTeam(match.home_id).getName();
                match.away_team = Sim.getNationalTeam(match.away_id).getName();
                match.tournament_level = MatchLevel.QUALIFIERS.level();
                match.is_international_match = true;
                matches.add(match);
            }
        }
        euro_wc_playoffs.put(EuropeWCQualifiersStage.SEMI_FINAL, matches);
    }

    private static void simulateEuroPlayoffSemiFinal() {
        System.out.println("\nEuropean WC Qualifiers Playoff Semi Finals");
        List<Match> matches = euro_wc_playoffs.get(EuropeWCQualifiersStage.SEMI_FINAL);
        for (int i = 0; i < matches.size(); i++) {
            matches.get(i).simulate();
            matches.get(i).display();
        }
        euro_wc_playoffs.put(EuropeWCQualifiersStage.SEMI_FINAL, matches);
    }

    private static void setEuroPlayoffFinals() {
        List<String> finalists = new ArrayList<>();
        List<Match> semis = euro_wc_playoffs.get(EuropeWCQualifiersStage.SEMI_FINAL);
        for (Match match : semis) {
            finalists.add(match.getWinner(true));
        }
        List<Match> matches = new ArrayList<>();
        for (int i = 0; i < EURO_PLAYOFF_QUALIFIERS; i++) {
            boolean found = false;
            String opp = null;
            for (int j = 0; j < EURO_PLAYOFF_PARTICIPANTS / EURO_PLAYOFF_QUALIFIERS; j++) {
                if (finalists.contains(brackets[i][j])) {
                    if (found && opp != null) {
                        Match match = new Match();
                        match.home_id = opp;
                        match.away_id = brackets[i][j];
                        match.home_team = Sim.getNationalTeam(match.home_id).getName();
                        match.away_team = Sim.getNationalTeam(match.away_id).getName();
                        match.tournament_level = MatchLevel.QUALIFIERS.level();
                        match.is_international_match = true;
                        matches.add(match);
                        break;
                    } else {
                        found = true;
                        opp = brackets[i][j];
                    }
                }
            }
        }
        euro_wc_playoffs.put(EuropeWCQualifiersStage.FINAL, matches);
    }

    private static void simulateEuroPlayoffFinals() {
        System.out.println("\nEuropean WC Qualifiers Playoff Finals");
        List<Match> matches = euro_wc_playoffs.get(EuropeWCQualifiersStage.FINAL);
        for (int i = 0; i < matches.size(); i++) {
            matches.get(i).simulate();
            matches.get(i).display();
            wc_qualifiers.add(matches.get(i).getWinner(true));
        }
        euro_wc_playoffs.put(EuropeWCQualifiersStage.FINAL, matches);
    }

    private static void simulateEuroPlayoffs() {
        setEuroPlayoffBrackets();
        setEuroPlayoffSemiFinals();
        simulateEuroPlayoffSemiFinal();
        setEuroPlayoffFinals();
        simulateEuroPlayoffFinals();
    }

    public static void simulateWC() {
        setupWCQGroups();
        simulateWCQGroups();
        simulateEuroPlayoffs();
        showQualifiers();
    }

    private enum Competition {
        WORLD_CUP("World Cup", "WC");

        private String comp_name;
        private String short_name;

        private Competition(String comp_name, String short_name) {
            this.comp_name = comp_name;
            this.short_name = short_name;
        }

        public String comp_name() {
            return this.comp_name;
        }

        public String short_name() {
            return this.short_name;
        }
    }

    private enum Stage {
        WC_GROUP_STAGE("World Cup Group Stage", Competition.WORLD_CUP),
        WC_ROUND_OF_16("World Cup Round of 16", Competition.WORLD_CUP),
        WC_QUARTER_FINAL("World Cup Quarter Final", Competition.WORLD_CUP),
        WC_SEMI_FINAL("World Cup Semi Final", Competition.WORLD_CUP),
        WC_FINAL("World Cup Final", Competition.WORLD_CUP);

        private String stage_name;
        private Competition competition;

        private Stage(String stage_name, Competition competition) {
            this.stage_name = stage_name;
            this.competition = competition;
        }

        public String stage_name() {
            return this.stage_name;
        }

        public Competition competition() {
            return this.competition;
        }
    }

    // region Helper
    private static List<NationalTeam> sortByPoints(List<Nation> nations) {
        List<NationalTeam> national_teams = new ArrayList<>();
        for (Nation nation : nations) {
            national_teams.add(nation.getNationalTeam());
        }
        Collections.sort(national_teams, (NationalTeam a, NationalTeam b) -> (int) (b.getPoints() - a.getPoints()));
        return national_teams;
    }

    private static List<String> sortNTByPoints(List<String> teams) {
        List<NationalTeam> national_teams = new ArrayList<>();
        for (String nation : teams) {
            national_teams.add(Sim.getNationalTeam(nation));
        }
        Collections.sort(national_teams, (NationalTeam a, NationalTeam b) -> (int) (b.getPoints() - a.getPoints()));
        List<String> nt_teams = new ArrayList<>();
        for (NationalTeam team : national_teams)
            nt_teams.add(team.getId());
        return nt_teams;
    }

    private static List<Match> setAsInternationalMatch(List<Match> matches, int level) {
        for (int i = 0; i < matches.size(); i++) {
            matches.get(i).is_international_match = true;
            matches.get(i).setTournamentLevel(level);
        }
        return matches;
    }

    private static int[][] divideIntoPots(int teams, int num_pots) {
        int[][] pots = new int[num_pots][teams / num_pots];
        int count = 0;
        for (int i = 0; i < num_pots; i++) {
            for (int j = 0; j < teams / num_pots; j++) {
                pots[i][j] = count;
                count++;
            }
        }
        return pots;
    }

    private static int[][] drawIntoGroups(int[][] pots, int num_groups, int num_pots) {
        int[][] groups = new int[num_groups][num_pots];
        for (int i = 0; i < num_pots; i++) {
            int[] pot_teams = new int[num_groups];
            for (int j = 0; j < num_groups; j++) {
                pot_teams[j] = pots[i][j];
            }
            pot_teams = CommonFunctions.shuffle(pot_teams);
            for (int j = 0; j < num_groups; j++) {
                groups[j][i] = pot_teams[j];
            }
        }
        return groups;
    }

    private static String[][] drawIntoGroups(List<String> teams, int num_groups) {
        int teams_per_group = teams.size() / num_groups;
        teams = sortNTByPoints(teams);
        int[][] groups_sym = getGroups(num_groups, teams_per_group);
        String[][] groups = new String[num_groups][teams_per_group];
        for(int i=0;i<num_groups;i++) {
            for(int j=0;j<teams_per_group;j++) {
                groups[i][j] = teams.get(groups_sym[i][j]);
            }
        }
        return groups;
    }

    private static int[][] getGroups(int a, int b) {
        int[][] groups = new int[a][b];
        for (int i = 0; i < a; i++) {
            int[] base = new int[b];
            for (int j = 0; j < b; j++) {
                base[j] = (a * j) + i;
            }
            base = CommonFunctions.shuffle(base);
            groups[i] = base;
        }
        return groups;
    }
    // endregion Helper
}
