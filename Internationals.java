import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Internationals {
    private static League nationsLeague;

    public static List<Match> setAsInternationalMatch(List<Match> matches) {
        for (int i = 0; i < matches.size(); i++) {
            matches.get(i).is_international_match = true;
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

    public static void setup() {
        List<NationalTeam> europeanTeams = Internationals.sortByPoints(Sim.getNationFromContinent("europe"));
        List<NationalTeam> latinTeams = Internationals.sortByPoints(Sim.getNationFromContinent("south america"));
        nationsLeague = new League("Nations League", 0, 0, 0, 1);
        nationsLeague.setInternational();
        for (int i = 0; i < europeanTeams.size(); i++) {
            europeanTeams.get(i).callup();
            nationsLeague.addTeam(europeanTeams.get(i).getId());
        }
        for (int i = 0; i < latinTeams.size(); i++) {
            latinTeams.get(i).callup();
            nationsLeague.addTeam(latinTeams.get(i).getId());
        }
        List<Match> matches = FixtureScheduler.scheduleInternationalFixtures(nationsLeague.getTeams(), false);
        matches = Internationals.setAsInternationalMatch(matches);
        nationsLeague.setFixtures(matches);
        nationsLeague.setupTable();
    }

    public static void simulateNationsLeague() {
        System.out.println("Nations League");
        for (int i = 1; i <= 14; i++) {
            System.out.println("Matchday " + i);
            nationsLeague.simulateMatchDay(i);
        }
        nationsLeague.getTable().showLong();
    }
}
