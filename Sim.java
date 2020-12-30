import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
//import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Sim {

    private static Map<String, League> leagues = new HashMap<>();
    private static Map<String, Club> teams = new HashMap<>();
    private static Map<String, NationalTeam> national_teams = new HashMap<>();
    private static Map<Integer, Nation> nations = new HashMap<>();
    private static Map<String, Player> players = new HashMap<>();
    private static Map<String, Cup> cups = new HashMap<>();

    private static int season = 2020;

    public static boolean SIMULATE_CONTINENTAL_COMPETITIONS = true;
    public static boolean TEST = false;

    private Sim() {
    }

    public static void updateTeam(Club team, String id) {
        teams.put(id, team);
    }

    public static void updateNationalTeam(NationalTeam team, String id) {
        national_teams.put(id, team);
    }

    public static void updateLeague(League league, String id) {
        leagues.put(id, league);
    }

    public static void updateCup(Cup cup, String id) {
        cups.put(id, cup);
    }

    public static int getSeasonNumber() {
        return Sim.season;
    }

    public static Club getTeam(String id) {
        if (teams.containsKey(id)) {
            return teams.get(id);
        } else {
            return null;
        }
    }

    public static NationalTeam getNationalTeam(String id) {
        if (national_teams.containsKey(id)) {
            return national_teams.get(id);
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

    public static List<Nation> getNationFromContinent(String continent) {
        List<Nation> cont_nations = new ArrayList<>();
        for (Map.Entry<Integer, Nation> entry : nations.entrySet()) {
            if (continent.equalsIgnoreCase(entry.getValue().getContinent())) {
                cont_nations.add(entry.getValue());
            }
        }
        return cont_nations;
    }

    public static Club[] getTeamArray(List<String> array) {
        Club[] array_teams = new Club[array.size()];
        for (int i = 0; i < array.size(); i++) {
            array_teams[i] = getTeam(array.get(i));
        }
        return array_teams;
    }

    public static NationalTeam[] getNationalTeamArray(List<String> array) {
        NationalTeam[] array_teams = new NationalTeam[array.size()];
        for (int i = 0; i < array.size(); i++) {
            array_teams[i] = getNationalTeam(array.get(i));
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
        ControllerUEFA.setupCompetitions();
        ControllerUEFA.getTeams();
        ControllerCONMEBOL.setupCompetitions();
        ControllerCONMEBOL.getTeams();
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
            ControllerUEFA.simulateCompetitions();
            ControllerCONMEBOL.simulateCompetitions();
        }
        if(season % 5 == 0) {
            Internationals.setup();
            Internationals.simulateNationsLeague();
        }
        seasonEndChanges();
    }

    public static void updateTeamForm(String team_id, int result, boolean youth) {
        Club t = getTeam(team_id);
        if (t != null) {
            if (youth) {
                t.getYouthSquad().postMatchChanges(result);
            } else {
                t.getSeniorSquad().postMatchChanges(result);
            }
        }
    }

    public static String addTeam(Club team) {
        String id = CommonFunctions.getRandomId();
        team.setId(id);
        teams.put(id, team);
        return id;
    }

    public static String addNationalTeam(NationalTeam team) {
        String id = CommonFunctions.getRandomId();
        team.setId(id);
        national_teams.put(id, team);
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
        Club team = Sim.getTeam(Sim.findPlayerInTeam(player_id));
        if (team != null) {
            team.getSeniorSquad().removePlayer(player_id);
        }
        players.remove(player_id);
    }

    public static void updatePlayer(Player player) {
        players.put(player.getId(), player);
    }

    public static String findTeamInLeague(Club team) {
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
        for (Map.Entry<String, Club> entry : teams.entrySet()) {
            if (entry.getValue().getAllPlayers().contains(player_id)) {
                return entry.getKey();
            }
        }
        return "";
    }

    public static String findPlayerInLeague(String player_id) {
        Club team = Sim.getTeam(Sim.findPlayerInTeam(player_id));
        if (team != null) {
            return Sim.findTeamInLeague(team);
        }
        return "";
    }

    public static List<Player> getAllPlayersFromNation(int nation_id) {
        List<Player> all_players = new ArrayList<>();
        for (Map.Entry<String, Player> entry : players.entrySet()) {
            if (entry.getValue().getNationId() == nation_id) {
                all_players.add(entry.getValue());
            }
        }
        return all_players;
    }

    public static List<Nation_Setup> getSetups() {
        List<Nation_Setup> setups = new ArrayList<>();
        // Europe
        setups.add(new Nation_Setup("England", "ENG", 4, 1, 4, 20, 80, 157, "Europe"));
        setups.add(new Nation_Setup("Spain", "ESP", 6, 2, 2, 20, 78, 163, "Europe"));
        setups.add(new Nation_Setup("Germany", "GER", 13, 3, 3, 18, 79, 162, "Europe"));
        setups.add(new Nation_Setup("Italy", "ITA", 10, 4, 2, 20, 78, 156, "Europe"));
        setups.add(new Nation_Setup("France", "FRA", 2, 5, 2, 20, 76, 165, "Europe"));
        setups.add(new Nation_Setup("Russia", "RUS", 39, 6, 3, 16, 74, 139, "Europe"));
        setups.add(new Nation_Setup("Portugal", "POR", 5, 7, 2, 18, 74, 159, "Europe"));
        setups.add(new Nation_Setup("Belgium", "BEL", 1, 8, 2, 16, 74, 160, "Europe"));
        setups.add(new Nation_Setup("Ukraine", "UKR", 24, 9, 2, 10, 72, 141, "Europe"));
        setups.add(new Nation_Setup("Turkey", "TUR", 32, 10, 3, 16, 73, 140, "Europe"));
        setups.add(new Nation_Setup("Netherlands", "NED", 14, 11, 2, 18, 74, 159, "Europe"));
        setups.add(new Nation_Setup("Austria", "AUT", 23, 12, 2, 12, 69, 144, "Europe"));
        setups.add(new Nation_Setup("Czechia", "CZE", 42, 13, 2, 14, 72, 145, "Europe"));
        setups.add(new Nation_Setup("Greece", "GRE", 53, 14, 3, 18, 73, 135, "Europe"));
        setups.add(new Nation_Setup("Croatia", "CRO", 11, 15, 2, 10, 68, 150, "Europe"));
        setups.add(new Nation_Setup("Denmark", "DEN", 12, 16, 2, 14, 68, 130, "Europe"));
        setups.add(new Nation_Setup("Switzerland", "SUI", 16, 17, 3, 12, 73, 137, "Europe"));
        setups.add(new Nation_Setup("Cyprus", "CYP", 100, 18, 2, 10, 66, 127, "Europe"));
        setups.add(new Nation_Setup("Serbia", "SRB", 30, 19, 3, 16, 68, 148, "Europe"));
        setups.add(new Nation_Setup("Scotland", "SCO", 48, 20, 2, 12, 69, 135, "Europe"));
        setups.add(new Nation_Setup("Norway", "NOR", 44, 21, 3, 14, 67, 134, "Europe"));
        setups.add(new Nation_Setup("Poland", "POL", 19, 22, 3, 18, 68, 136, "Europe"));
        setups.add(new Nation_Setup("Israel", "ISR", 87, 23, 3, 12, 68, 127, "Europe"));
        setups.add(new Nation_Setup("Romania", "ROU", 37, 24, 3, 16, 69, 143, "Europe"));
        setups.add(new Nation_Setup("Hungary", "HUN", 40, 25, 3, 14, 68, 134, "Europe"));
        // SouthAmerica
        setups.add(new Nation_Setup("Argentina", "ARG", 7, 1, 3, 22, 74, 162, "South America"));
        setups.add(new Nation_Setup("Brazil", "BRA", 3, 2, 3, 20, 75, 163, "South America"));
        setups.add(new Nation_Setup("Colombia", "COL", 15, 3, 3, 20, 71, 154, "South America"));
        setups.add(new Nation_Setup("Chile", "CHI", 17, 4, 2, 18, 70, 149, "South America"));
        setups.add(new Nation_Setup("Uruguay", "URU", 8, 5, 2, 16, 69, 150, "South America"));
        setups.add(new Nation_Setup("Paraguay", "PAR", 34, 6, 3, 12, 69, 140, "South America"));
        setups.add(new Nation_Setup("Ecuador", "ECU", 56, 7, 3, 16, 68, 141, "South America"));
        setups.add(new Nation_Setup("Peru", "PER", 25, 8, 2, 20, 69, 145, "South America"));
        setups.add(new Nation_Setup("Venezuela", "VEN", 28, 9, 2, 20, 67, 137, "South America"));
        setups.add(new Nation_Setup("Bolivia", "BOL", 79, 10, 2, 14, 66, 130, "South America"));
        return setups;
    }

    public static List<Nation_Setup> getTestSetup() {
        List<Nation_Setup> setups = new ArrayList<>();
        setups.add(new Nation_Setup("England", "ENG", 1, 1, 1, 6, 88, 200, "Europe"));
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
                League league = CommonFunctions.createLeague(rating, setup.num_team_leagues, nation.getId(), i,
                        promotion, relegation, nation.getName());
                System.out.println(league.getName() + " " + rating);
                String league_id = Sim.addLeague(league);
                nation.addLeague(league_id);
            }
            Cup cup = new Cup(Catalogues.getDenonym(nation.getName()) + " Cup", nation.getId());
            String cup_id = Sim.addCup(cup);
            nation.setCupId(cup_id);
            nation.setYouthRating(setup.youth_rating);
            nation.setContinent(setup.continent);
            NationalTeam national_team = new NationalTeam(nation.getId(), setup.national_team_ranking);
            String national_id = Sim.addNationalTeam(national_team);
            nation.setNationalTeamId(national_id);
            nations.put(nation.getId(), nation);
            count++;
        }
        if (SIMULATE_CONTINENTAL_COMPETITIONS && !TEST) {
            ControllerUEFA.initCompetitions();
            ControllerCONMEBOL.initCompetitions();
        }
    }

    public static Map<String, Club> sortBySquadRating(Map<String, Club> map) {
        List<Map.Entry<String, Club>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list,
                (Map.Entry<String, Club> o1, Map.Entry<String, Club> o2) -> (int) ((o2.getValue().getBestSquadRating())
                        - (o1.getValue().getBestSquadRating())));
        HashMap<String, Club> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Club> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public static void showTeams() {
        System.out.println("\nTeams");
        Map<String, Club> sorted = Sim.sortBySquadRating(teams);
        int count = 1;
        for (Map.Entry<String, Club> entry : sorted.entrySet()) {
            System.out.println(count + " " + entry.getValue().getName() + "\t" + entry.getValue().getBestSquadRating());
            if (count >= 100) {
                break;
            }
            count++;
        }
    }

    public static Map<String, Player> sortByRating(Map<String, Player> map) {
        List<Map.Entry<String, Player>> list = new ArrayList<>(map.entrySet());
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
        Club team;
        Nation nation;
        String code = "";
        for (Map.Entry<String, Player> entry : sorted.entrySet()) {
            Player player = entry.getValue();
            team = Sim.getTeam(Sim.findPlayerInTeam(player.getId()));
            if (team != null) {
                team_name = team.getName();
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
            ControllerUEFA.saveCompetitionHistory();
            ControllerCONMEBOL.saveCompetitionHistory();
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
        long start = System.nanoTime();
        for (Map.Entry<String, Club> entry : teams.entrySet()) {
            entry.getValue().seasonEndPlayerPromotions();
        }
        long end = System.nanoTime();
        long diff = end - start;
        double diff_s = diff / (1000.0 * 1000 * 1000);
        double per_player = (double) diff / retired_players.size();
        System.out.println(diff_s + " " + per_player);
        System.out.println("season end changes done");
        season++;
    }

    public static void showCompetitionHistories() {
        for (Map.Entry<String, League> entry : leagues.entrySet()) {
            entry.getValue().showCompetitionHistory();
        }
        for (Map.Entry<String, Cup> entry : cups.entrySet()) {
            entry.getValue().showCompetitionHistory();
        }
        if (SIMULATE_CONTINENTAL_COMPETITIONS && !TEST) {
            ControllerUEFA.showCompetitionHistory();
            ControllerCONMEBOL.showCompetitionHistory();
        }
    }

    public static void showNationalTeams() {
        for (Map.Entry<Integer, Nation> entry : nations.entrySet()) {
            System.out.print("\n" + entry.getValue().getName() + " ");
            entry.getValue().getNationalTeam().callup();
        }
    }

    public static void main(String[] args) {
        int num_seasons = 21;
        double[] d = new double[num_seasons];
        initSetup();
        try {
            for (int i = 0; i < num_seasons; i++) {
                double start = System.nanoTime();
                sim();
                double end = System.nanoTime();
                d[i] = end - start;
            }
        } catch (Exception e) {
            System.out.println(Sim.season);
            e.printStackTrace();
        }
        for (int i = 0; i < num_seasons; i++) {
            System.out.println(i + " " + d[i] / 1000000000);
        }
        //showCompetitionHistories();
        //showTeams();
        //showPlayers();
        //showNationalTeams();
    }
}