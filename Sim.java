import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Sim {

    private static Map<String, League> leagues = new HashMap<>();
    private static Map<String, Team> teams = new HashMap<>();
    private static Map<Integer, Nation> nations = new HashMap<>();
    private static Map<String, Player> players = new HashMap<>();
    private static Map<String, Cup> cups = new HashMap<>();

    public static int current_season = 2020;

    public static boolean SIMULATE_CONTINENTAL_COMPETITIONS = false;
    public static boolean TEST = false;

    private Sim() {
    }

    public static void updateTeam(Team team, String id) {
        teams.put(id, team);
    }

    public static void updateLeague(League league, String id) {
        leagues.put(id, league);
    }

    public static void updateCup(Cup cup, String id) {
        cups.put(id, cup);
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

    public static Player getPlayer(String player_id) {
        if (players.containsKey(player_id)) {
            return players.get(player_id);
        }
        return null;
    }

    public static Nation getNation(int id) {
        if (nations.containsKey(id)) {
            return nations.get(id);
        }
        return null;
    }

    public static Cup getCup(String id) {
        if (cups.containsKey(id)) {
            return cups.get(id);
        }
        return null;
    }

    public static League[] getLeagues(List<String> array) {
        League[] array_leagues = new League[array.size()];
        for (int i = 0; i < array.size(); i++) {
            array_leagues[i] = getLeague(array.get(i));
        }
        return array_leagues;
    }

    public static void setupContinentalCompetitions() {
        TournamentController.setupCompetitions();
        for (Map.Entry<Integer, Nation> entry : nations.entrySet()) {
            for (int i = 1; i <= TournamentController.NUMBER_OF_COMPETITIONS; i++) {
                entry.getValue().getContinentalTeams(i);
            }
        }
    }

    public static void sim() {
        for (Map.Entry<String, League> entry : leagues.entrySet()) {
            entry.getValue().seasonStart();
            entry.getValue().simulateSeason();
        }

        for (Map.Entry<String, Cup> entry : cups.entrySet()) {
            entry.getValue().setup();
            entry.getValue().simulateCup();
        }

        if (SIMULATE_CONTINENTAL_COMPETITIONS && !TEST) {
            setupContinentalCompetitions();
            TournamentController.simulateCompetitions();
        }
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

    public static String addPlayer(Player player) {
        String id = CommonFunctions.getRandomId();
        player.setId(id);
        players.put(id, player);
        return id;
    }

    public static String addCup(Cup cup) {
        String id = CommonFunctions.getRandomId();
        cup.setId(id);
        cups.put(id, cup);
        return id;
    }

    public static void removePlayer(String player_id) {
        Team team = Sim.getTeam(Sim.findPlayerInTeam(player_id));
        if (team != null) {
            team.removePlayer(player_id);
        }
        players.remove(player_id);
    }

    public static void updatePlayer(Player player) {
        players.put(player.getId(), player);
    }

    public static String findTeamInLeague(Team team) {
        Nation nation = Sim.getNation(team.getNationId());
        if (nation != null) {
            for (String league_id : nation.getLeagues()) {
                League league = Sim.getLeague(league_id);
                if (league != null && league.getTeams().contains(team.getId())) {
                    return league_id;
                }
            }
        }
        return "";
    }

    public static String findPlayerInTeam(String player_id) {
        for (Map.Entry<String, Team> entry : teams.entrySet()) {
            if (entry.getValue().getPlayers().contains(player_id)) {
                return entry.getKey();
            }
        }
        return "";
    }

    public static String findPlayerInLeague(String player_id) {
        Team team = Sim.getTeam(Sim.findPlayerInTeam(player_id));
        if (team != null) {
            return Sim.findTeamInLeague(team);
        }
        return "";
    }

    public static List<Nation_Setup> getSetups() {
        List<Nation_Setup> setups = new ArrayList<>();
        setups.add(new Nation_Setup("England", "ENG", 1, 1, 4, 20, 80));
        setups.add(new Nation_Setup("Spain", "ESP", 2, 2, 2, 20, 78));
        setups.add(new Nation_Setup("Germany", "GER", 3, 3, 3, 18, 79));
        setups.add(new Nation_Setup("Italy", "ITA", 4, 4, 2, 20, 78));
        setups.add(new Nation_Setup("France", "FRA", 5, 5, 2, 20, 76));
        setups.add(new Nation_Setup("Russia", "RUS", 6, 6, 3, 16, 74));
        setups.add(new Nation_Setup("Portugal", "POR", 7, 7, 2, 18, 74));
        setups.add(new Nation_Setup("Belgium", "BEL", 8, 8, 2, 16, 74));
        setups.add(new Nation_Setup("Ukraine", "UKR", 9, 9, 2, 10, 72));
        setups.add(new Nation_Setup("Turkey", "TUR", 10, 10, 3, 16, 73));
        setups.add(new Nation_Setup("Netherlands", "NED", 11, 11, 2, 18, 74));
        setups.add(new Nation_Setup("Austria", "AUT", 12, 12, 2, 12, 69));
        setups.add(new Nation_Setup("Czechia", "CZE", 13, 13, 2, 14, 72));
        setups.add(new Nation_Setup("Greece", "GRE", 14, 14, 3, 18, 73));
        setups.add(new Nation_Setup("Croatia", "CRO", 15, 15, 2, 10, 68));
        setups.add(new Nation_Setup("Denmark", "DEN", 16, 16, 2, 14, 68));
        setups.add(new Nation_Setup("Switzerland", "SUI", 17, 17, 3, 12, 73));
        setups.add(new Nation_Setup("Cyprus", "CYP", 18, 18, 2, 10, 66));
        setups.add(new Nation_Setup("Serbia", "SRB", 19, 19, 3, 16, 68));
        setups.add(new Nation_Setup("Scotland", "SCO", 20, 20, 2, 12, 69));
        setups.add(new Nation_Setup("Norway", "NOR", 21, 21, 3, 14, 67));
        setups.add(new Nation_Setup("Poland", "POL", 22, 22, 3, 18, 68));
        setups.add(new Nation_Setup("Israel", "ISR", 23, 23, 3, 12, 68));
        setups.add(new Nation_Setup("Romania", "ROU", 24, 24, 3, 16, 69));
        setups.add(new Nation_Setup("Hungary", "HUN", 25, 25, 3, 14, 68));
        return setups;
    }

    public static List<Nation_Setup> getTestSetup() {
        List<Nation_Setup> setups = new ArrayList<>();
        setups.add(new Nation_Setup("England", "ENG", 1, 1, 1, 20, 80));
        setups.add(new Nation_Setup("Spain", "ESP", 2, 2, 1, 20, 78));
        return setups;
    }

    public static void initSetup() {
        int count = 0;
        List<Nation_Setup> setups = Sim.getSetups();
        if (Sim.TEST) {
            setups = Sim.getTestSetup();
        }
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
                        CommonFunctions.getRandomNumber(4), nation.getId(), i, promotion, relegation, nation.getName());
                System.out.println(league.getName() + " " + rating);
                String league_id = Sim.addLeague(league);
                nation.addLeague(league_id);
            }
            Cup cup = new Cup(Catalogues.getDenonym(nation.getName()) + " Cup", nation.getId());
            String cup_id = Sim.addCup(cup);
            nation.setCupId(cup_id);
            nations.put(nation.getId(), nation);
            count++;
        }
        if (SIMULATE_CONTINENTAL_COMPETITIONS && !TEST) {
            TournamentController.initCompetitions();
        }
    }

    public static Map<String, Team> sortBySquadRating(Map<String, Team> map) {
        List<Map.Entry<String, Team>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list,
                (Map.Entry<String, Team> o1, Map.Entry<String, Team> o2) -> (int) ((o2.getValue().getSquadRating())
                        - (o1.getValue().getSquadRating())));
        HashMap<String, Team> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Team> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public static void showTeams() {
        System.out.println("\nTeams");
        Map<String, Team> sorted = Sim.sortBySquadRating(teams);
        int count = 1;
        for (Map.Entry<String, Team> entry : sorted.entrySet()) {
            System.out.println(count + " " + entry.getValue().getPaddedName() + "\t"
                    + ((int) entry.getValue().getEloRating()) + "\t" + entry.getValue().getSquadRating());
            if (count >= 100) {
                break;
            }
            count++;
        }
    }

    public static Map<String, Player> sortByRating(Map<String, Player> map) {
        List<Map.Entry<String, Player>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, (Map.Entry<String, Player> o1,
                Map.Entry<String, Player> o2) -> (int) ((o2.getValue().getRating()) - (o1.getValue().getRating())));
        HashMap<String, Player> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Player> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public static void showPlayers() {
        System.out.println("\nPlayers");
        Map<String, Player> sorted = Sim.sortByRating(players);
        int count = 1;
        String team_name = "";
        Team team;
        Nation nation;
        String code = "";
        for (Map.Entry<String, Player> entry : sorted.entrySet()) {
            Player player = entry.getValue();
            team = Sim.getTeam(Sim.findPlayerInTeam(player.getId()));
            if (team != null) {
                team_name = team.getPaddedName();
            }
            nation = Sim.getNation(player.getNationId());
            if (nation != null) {
                code = nation.getCode();
            }
            System.out.println(
                    count + "\t" + player.getPaddedName() + code + "\t" + team_name + "\t" + player.getRating());
            if (count >= 300) {
                break;
            }
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
        for (Map.Entry<String, Cup> entry : cups.entrySet()) {
            entry.getValue().saveCompetitionHistory();
            updateCup(entry.getValue(), entry.getKey());
        }
        if (SIMULATE_CONTINENTAL_COMPETITIONS && !TEST) {
            TournamentController.saveCompetitionHistory();
        }
        List<String> retired_players = new ArrayList<>();
        for (Map.Entry<String, Player> entry : players.entrySet()) {
            entry.getValue().seasonEndChanges();
            if (entry.getValue().isRetired()) {
                retired_players.add(entry.getValue().getId());
            }
        }
        System.out.println("retired players count: " + retired_players.size());
        for (String player : retired_players) {
            Sim.removePlayer(player);
        }
        for (Map.Entry<String, Team> entry : teams.entrySet()) {
            entry.getValue().endSeasonChanges();
        }
        System.out.println("season end changes done");
        current_season++;
    }

    public static void showCompetitionHistories() {
        for (Map.Entry<String, League> entry : leagues.entrySet()) {
            entry.getValue().showCompetitionHistory();
        }
        for (Map.Entry<String, Cup> entry : cups.entrySet()) {
            entry.getValue().showCompetitionHistory();
        }
        if (SIMULATE_CONTINENTAL_COMPETITIONS && !TEST) {
            TournamentController.showCompetitionHistory();
        }
    }

    public static void main(String[] args) {
        initSetup();
        for (int i = 0; i < 1; i++) {
            sim();
        }
        showCompetitionHistories();
        showTeams();
        showPlayers();
    }
}