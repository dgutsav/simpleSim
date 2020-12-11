import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Sim {

    static Map<String, League> leagues = new HashMap<>();
    static Map<String, Team> teams = new HashMap<>();
    static List<Match> matches = new ArrayList<>();
    static Map<Integer, Nation> nations = new HashMap<>();

    public static int current_season = 2020;

    private Sim() {
    }

    public static void updateTeam(Team team, String id) {
        teams.put(id, team);
    }

    public static void updateLeague(League league, String id) {
        leagues.put(id, league);
    }

    public static int getSeasonNumber() {
        return Sim.current_season;
    }

    public static Team getTeam(String id) {
        if (teams.containsKey(id)) {
            return teams.get(id);
        } else {
            return null;
        }
    }

    public static Nation getNationByLeagueRanking(int rank) {
        for (Map.Entry<Integer, Nation> entry : nations.entrySet()) {
            if (entry.getValue().getLeagueRanking() == rank) {
                return entry.getValue();
            }
        }
        return null;
    }

    public static Team[] getTeamArray(List<String> array) {
        Team[] array_teams = new Team[array.size()];
        for (int i = 0; i < array.size(); i++) {
            array_teams[i] = getTeam(array.get(i));
        }
        return array_teams;
    }

    public static League getLeague(String id) {
        if (leagues.containsKey(id)) {
            return leagues.get(id);
        } else {
            return null;
        }
    }

    public static League[] getLeagues(List<String> array) {
        League[] array_leagues = new League[array.size()];
        for (int i = 0; i < array.size(); i++) {
            array_leagues[i] = getLeague(array.get(i));
        }
        return array_leagues;
    }

    public static void setupContinentalCompetitions() {
        for (Map.Entry<Integer, Nation> entry : nations.entrySet()) {
            for (int i = 1; i <= TournamentController.NUMBER_OF_COMPETITIONS; i++) {
                entry.getValue().getContinentalTeams(i);
            }
        }
        for (int i = 1; i <= TournamentController.NUMBER_OF_COMPETITIONS; i++) {
            TournamentController.setGroups(i);
        }
        TournamentController.setupCompetitions();
    }

    public static void sim() {
        for (Map.Entry<String, League> entry : leagues.entrySet()) {
            entry.getValue().seasonStart();
            entry.getValue().simulateSeason();
        }
        setupContinentalCompetitions();
        TournamentController.simulateCompetitions();
        seasonEndChanges();
    }

    public static void updateTeamForm(String team_id, int result) {
        Team t = getTeam(team_id);
        if (t != null) {
            t.updateForm(result);
        }
    }

    public static String addTeam(Team team) {
        String id = CommonFunctions.getRandomId();
        team.setId(id);
        teams.put(id, team);
        return id;
    }

    public static String addLeague(League league) {
        String id = CommonFunctions.getRandomId();
        league.setId(id);
        leagues.put(id, league);
        return id;
    }

    public static List<Nation_Setup> getSetups() {
        List<Nation_Setup> setups = new ArrayList<>();
        setups.add(new Nation_Setup("England", "ENG", 1, 1, 4, 20, 80));
        setups.add(new Nation_Setup("Spain", "ESP", 2, 2, 2, 20, 78));
        setups.add(new Nation_Setup("Germany", "GER", 3, 3, 3, 18, 79));
        setups.add(new Nation_Setup("Italy", "ITA", 4, 4, 2, 20, 78));
        setups.add(new Nation_Setup("France", "FRA", 5, 5, 2, 20, 76));
        setups.add(new Nation_Setup("Portugal", "POR", 6, 6, 2, 18, 74));
        setups.add(new Nation_Setup("Belgium", "BEL", 7, 7, 2, 16, 74));
        setups.add(new Nation_Setup("Netherlands", "NED", 8, 8, 2, 18, 74));
        setups.add(new Nation_Setup("Russia", "RUS", 9, 9, 3, 16, 74));
        setups.add(new Nation_Setup("Ukraine", "UKR", 10, 10, 2, 10, 72));
        setups.add(new Nation_Setup("Austria", "AUT", 11, 11, 2, 12, 69));
        setups.add(new Nation_Setup("Scotland", "SCO", 12, 12, 2, 12, 69));
        setups.add(new Nation_Setup("Turkey", "TUR", 13, 13, 3, 16, 73));
        setups.add(new Nation_Setup("Denmark", "DEN", 14, 14, 2, 14, 68));
        setups.add(new Nation_Setup("Croatia", "CRO", 15, 15, 2, 10, 68));
        return setups;
    }

    public static void initSetup() {
        int count = 0;
        List<Nation_Setup> setups = Sim.getSetups();
        for (Nation_Setup setup : setups) {
            Nation nation = new Nation(setup.name, setup.short_name, count, setup.league_ranking);
            for (int i = 1; i <= setup.num_leagues; i++) {
                int promotion = League.getPyramidPlaces(setup.num_team_leagues);
                int relegation = League.getPyramidPlaces(setup.num_team_leagues);
                if (i == 1) {
                    promotion = 0;
                } else if (i == setup.num_leagues) {
                    relegation = 0;
                }
                int rating = setup.league_rating;
                if (i > 1) {
                    double multiplier = (Math.pow(0.9, (double) (i - 1))) * setup.league_rating;
                    rating = (int) (multiplier);
                }
                League league = CommonFunctions.createLeague(rating, setup.num_team_leagues,
                        CommonFunctions.getRandomNumber(4), nation.getName(), i, promotion, relegation);
                System.out.println(league.getName() + " " + rating);
                String league_id = Sim.addLeague(league);
                nation.addLeague(league_id);
            }
            nations.put(nation.getId(), nation);
            count++;
        }
    }

    public static Map<String, Team> sortByElo(Map<String, Team> map) {
        List<Map.Entry<String, Team>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, (Map.Entry<String, Team> o1,
                Map.Entry<String, Team> o2) -> (int) ((o2.getValue().getEloRating()) - (o1.getValue().getEloRating())));
        HashMap<String, Team> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Team> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public static void showElo() {
        Map<String, Team> sorted = Sim.sortByElo(teams);
        int count = 1;
        for (Map.Entry<String, Team> entry : sorted.entrySet()) {
            System.out.println(count + " " + entry.getValue().getName() + "\t" + entry.getValue().getEloRating() + "\t"
                    + entry.getValue().getSquadRating());
            count++;
        }
    }

    public static void seasonEndChanges() {
        for (Map.Entry<Integer, Nation> entry : nations.entrySet()) {
            entry.getValue().promotionDemotion();
        }
        for (Map.Entry<String, League> entry : leagues.entrySet()) {
            entry.getValue().saveCompetitionHistory();
            updateLeague(entry.getValue(), entry.getKey());
        }
        TournamentController.saveCompetitionHistory();
        current_season++;
    }

    public static void main(String[] args) {
        initSetup();
        TournamentController.initCompetitions();
        sim();
        sim();
        sim();
        sim();
        for (Map.Entry<String, League> entry : leagues.entrySet()) {
            entry.getValue().showCompetitionHistory();
        }
        TournamentController.showCompetitionHistory();
        showElo();
    }
}