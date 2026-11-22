import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Internationals {
    private static League[] conmebol_qualifiers;
    private static League[] afc_qualifiers;
    private static League[] caf_qualifiers;
    private static League[] concacaf_qualifiers;
    private static League[] uefa_qualifiers;

    private static League[] wc_groups;
    private static Cup wc_knockouts;

    public static List<Match> setAsInternationalMatch(List<Match> matches, boolean qualifier) {
        for (int i = 0; i < matches.size(); i++) {
            matches.get(i).is_international_match = true;
            if (qualifier) {
                matches.get(i).setTournamentLevel(1);
            }
        }
        return matches;
    }

    public static List<NationalTeam> sortByPoints(List<Nation> nations) {
        List<NationalTeam> national_teams = new ArrayList<>();
        for (Nation nation : nations) {
            national_teams.add(nation.getNationalTeam());
        }
        Collections.sort(national_teams, (NationalTeam a, NationalTeam b) -> (int) (b.getPoints() - a.getPoints()));
        return national_teams;
    }

    public static int[][] getGroups(int a, int b) {
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

    public static int getNumGroups(String continent) {
        if (continent.equalsIgnoreCase("europe")) {
            return 6;
        } else if (continent.equalsIgnoreCase("south america") || continent.equalsIgnoreCase("north america")) {
            return 1;
        } else if (continent.equalsIgnoreCase("africa") || continent.equalsIgnoreCase("asia")) {
            return 2;
        } else {
            return 1;
        }
    }

    public static int getTotalMatchdays(String continent) {
        if (continent.equalsIgnoreCase("europe") || continent.equalsIgnoreCase("north america")
                || continent.equalsIgnoreCase("asia")) {
            return 10;
        } else if (continent.equalsIgnoreCase("south america")) {
            return 18;
        } else if (continent.equalsIgnoreCase("africa")) {
            return 6;
        } else {
            return 1;
        }
    }

    public static int getQualifiers(String continent) {
        if (continent.equalsIgnoreCase("europe")) {
            return 6;
        } else if (continent.equalsIgnoreCase("south america")) {
            return 4;
        } else if (continent.equalsIgnoreCase("africa") || continent.equalsIgnoreCase("north america")
                || continent.equalsIgnoreCase("asia")) {
            return 2;
        } else {
            return 0;
        }
    }

    public static League[] setupQualifier(League[] qualifiers, String continent) {
        int num_groups = qualifiers.length;
        List<NationalTeam> teams = Internationals.sortByPoints(Sim.getNationFromContinent(continent.toLowerCase()));
        int num_teams = teams.size();
        int num_teams_per_group = num_teams / num_groups;
        int[][] groups = Internationals.getGroups(num_groups, num_teams_per_group);
        for (int i = 0; i < num_groups; i++) {
            for (int j = 0; j < num_teams_per_group; j++) {
                if (teams.size() <= groups[i][j]) {
                    System.out.println(groups[i][j] + " " + continent);
                }
                teams.get(groups[i][j]).callup();
                qualifiers[i].addTeam(teams.get(groups[i][j]).getId());
            }
            qualifiers[i].setFixtures(Internationals.setAsInternationalMatch(
                    FixtureScheduler.scheduleInternationalFixtures(qualifiers[i].getTeams(), false, false), true));
            qualifiers[i].setupTable();
        }
        return qualifiers;
    }

    public static League[] initQualifier(String continent) {
        int groups = Internationals.getNumGroups(continent);
        League[] leagues = new League[groups];
        if (groups == 1) {
            leagues[0] = new League(continent + " Qualifiers", 0, 0, 0, 0, 200);
            leagues[0].setInternational();
        } else {
            for (int i = 0; i < groups; i++) {
                leagues[i] = new League(continent + " Qualifiers " + (i + 1), 0, 0, 0, 0, 200);
                leagues[i].setInternational();
            }
        }
        return leagues;
    }

    public static League[] simulateQualifier(League[] leagues, String continent) {
        int matchdays = Internationals.getTotalMatchdays(continent);
        for (int i = 0; i < leagues.length; i++) {
            System.out.println(leagues[i].getName());
            for (int j = 1; j <= matchdays; j++) {
                leagues[i].simulateMatchDay(j);
            }
            leagues[i].getTable().showLong();
        }
        return leagues;
    }

    public static List<String> getQualifiersFromContinent(League[] leagues, String continent) {
        List<String> qualifiers = new ArrayList<>();
        int total_qualifiers = Internationals.getQualifiers(continent);
        for (int i = 1; i < 10; i++) {
            for (int j = 0; j < leagues.length; j++) {
                qualifiers.add(leagues[j].getTeamAtRank(i));
                total_qualifiers--;
                if (total_qualifiers <= 0) {
                    break;
                }
            }
            if (total_qualifiers <= 0) {
                break;
            }
        }
        return qualifiers;
    }

    public static void setupQualifiers() {
        uefa_qualifiers = setupQualifier(initQualifier("europe"), "europe");
        conmebol_qualifiers = setupQualifier(initQualifier("south america"), "south america");
        concacaf_qualifiers = setupQualifier(initQualifier("north america"), "north america");
        afc_qualifiers = setupQualifier(initQualifier("asia"), "asia");
        caf_qualifiers = setupQualifier(initQualifier("africa"), "africa");
    }

    public static void simulateQualifiers() {
        uefa_qualifiers = simulateQualifier(uefa_qualifiers, "europe");
        conmebol_qualifiers = simulateQualifier(conmebol_qualifiers, "south america");
        concacaf_qualifiers = simulateQualifier(concacaf_qualifiers, "north america");
        afc_qualifiers = simulateQualifier(afc_qualifiers, "asia");
        caf_qualifiers = simulateQualifier(caf_qualifiers, "africa");
    }

    public static List<String> getAllQualifiers() {
        List<String> qualified_teams = new ArrayList<>();
        qualified_teams.addAll(getQualifiersFromContinent(uefa_qualifiers, "europe"));
        qualified_teams.addAll(getQualifiersFromContinent(concacaf_qualifiers, "north america"));
        qualified_teams.addAll(getQualifiersFromContinent(conmebol_qualifiers, "south america"));
        qualified_teams.addAll(getQualifiersFromContinent(caf_qualifiers, "africa"));
        qualified_teams.addAll(getQualifiersFromContinent(afc_qualifiers, "asia"));
        return qualified_teams;
    }

    public static void setupWorldCup() {
        List<String> qualifiers = getAllQualifiers();
        List<NationalTeam> qualified_teams = new ArrayList<>();
        for (String id : qualifiers) {
            qualified_teams.add(Sim.getNationalTeam(id));
        }
        Collections.sort(qualified_teams, (NationalTeam a, NationalTeam b) -> (int) (b.getPoints() - a.getPoints()));
        wc_groups = new League[4];
        int[][] groups = Internationals.getGroups(4, 4);
        for (int i = 0; i < 4; i++) {
            wc_groups[i] = new League("WC Group " + (i + 1), 0, 0, 0, 0, 200);
            wc_groups[i].setInternational();
            for (int j = 0; j < 4; j++) {
                qualified_teams.get(groups[i][j]).callup();
                wc_groups[i].addTeam(qualified_teams.get(groups[i][j]).getId());
            }
            wc_groups[i].setFixtures(Internationals.setAsInternationalMatch(
                    FixtureScheduler.scheduleInternationalFixtures(wc_groups[i].getTeams(), false, true), true));
            wc_groups[i].setupTable();
        }
    }

    public static void simulateWorldCupGroups() {
        int matchdays = 3;
        for (int i = 0; i < wc_groups.length; i++) {
            System.out.println("\n" + wc_groups[i].getName());
            for (int j = 1; j <= matchdays; j++) {
                wc_groups[i].simulateMatchDay(j);
            }
            wc_groups[i].getTable().showLong();
        }
    }

    public static void setupWorldCupKnockouts() {
        wc_knockouts = new Cup("WC Knockouts", -1);
        wc_knockouts.setInternational();
        List<String> teams = new ArrayList<>();
        for (int j = 1; j <= 2; j++) {
            for (int i = 0; i < 4; i++) {
                teams.add(wc_groups[i].getTeamAtRank(j));
            }
        }
        System.out.println(teams.toString());
        wc_knockouts.setTeams(teams);
        wc_knockouts.simulateCup();
        wc_knockouts.saveCompetitionHistory();
        wc_knockouts.showCompetitionHistory();
    }

    public static void showAllNames(List<String> teams) {
        for (String id : teams) {
            System.out.println(Sim.getNationalTeam(id).getName());
        }
    }
}
