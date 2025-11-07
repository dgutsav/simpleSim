import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
//import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import Models.*;
import Functions.CatalogueHelper;

public class Sim {

    private static Map<String, League> leagues = new HashMap<>();
    private static Map<String, Club_Team> clubs = new HashMap<>();
    private static Map<String, NationalTeam> national_teams = new HashMap<>();
    private static Map<Integer, Nation> nations = new HashMap<>();
    private static Map<Integer, LeagueStructure> league_structures = new HashMap<>();
    private static Map<String, Player> players = new HashMap<>();
    private static Map<String, Cup> cups = new HashMap<>();

    public static final int STARTING_SEASON = 2024;
    private static int season = STARTING_SEASON;

    public static boolean SIMULATE_CONTINENTAL_COMPETITIONS = true;
    public static boolean TEST = true;
    public static boolean SHOW_DETAILS = true;
    public static boolean USE_TRANSFER_MARKET = true;
    public static boolean USE_INTERNATIONALS = false;

    private Sim() {
    }

    public static Map<String, Club_Team> getAllClubs() {
        return clubs;
    }

    public static Map<String, Player> getAllPlayers() {
        return players;
    }

    public static Map<String, League> getAllLeagues() {
        return leagues;
    }

    public static void updateClub(Club_Team club, String id) {
        clubs.put(id, club);
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

    public static boolean isFirstSeason() { return season == STARTING_SEASON; }

    public static Club_Team getClub(String id) {
        if (clubs.containsKey(id)) {
            return clubs.get(id);
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
            if (continent.equalsIgnoreCase(entry.getValue().getContinent()) || continent.equalsIgnoreCase("all")) {
                cont_nations.add(entry.getValue());
            }
        }
        return cont_nations;
    }

    public static Map<Integer, Nation> getAllNations() {
        return nations;
    }

    public static Club_Team[] getClubArray(List<String> array) {
        Club_Team[] array_clubs = new Club_Team[array.size()];
        for (int i = 0; i < array.size(); i++) {
            array_clubs[i] = getClub(array.get(i));
        }
        return array_clubs;
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

    public static LeagueStructure getLeagueStructure(int nation_id) {
        return league_structures.get(nation_id);
    }

    public static void addLeagueStructure(int id, LeagueStructure struct) {
        league_structures.put(id, struct);
    }

    public static Division getDivision(int nation_id, int level) {
        return league_structures.get(nation_id).getDivisions().get(level);
    }

    public static List<Division> getAllDivisions() {
        List<Division> div = new ArrayList<>();
        for (int x: league_structures.keySet()) {
            List<Division> d = new ArrayList<>(league_structures.get(x).getDivisions().values());
            div.addAll(d);
        }
        return div;
    }

    public static void sim() {
        // for (Map.Entry<String, League> entry : leagues.entrySet()) {
        //     entry.getValue().seasonStart();
        //     entry.getValue().simulateSeason();
        //     entry.getValue().endSeasonChanges();
        // }

        for (Map.Entry<Integer, LeagueStructure> entry: league_structures.entrySet()) {
            entry.getValue().startSeason();
            entry.getValue().simulateSeason();
            entry.getValue().endSeason();
        }

        for (Map.Entry<String, Cup> entry : cups.entrySet()) {
            entry.getValue().setup();
            entry.getValue().simulateCup();
        }

        if (SIMULATE_CONTINENTAL_COMPETITIONS && !TEST) {
            System.out.println("CONTINENTAL COMPS");
            // ControllerSAmerica.simulate();
            // ControllerNAmerica.simulate();
            ControllerEurope_Swiss.simulate();
            // ControllerAfrica.simulate();
            // ControllerAsia.simulate();
            // ControllerCWC.simulate();
            //Controller_WCL.simulate();
        }

        if (USE_INTERNATIONALS && !TEST) {
            International.simulateWC();
        }
        seasonEndChanges();
        if (USE_TRANSFER_MARKET) {
            TransferMarket.simulate();
        }
        season++;
    }

    public static String addClub(Club_Team team) {
        String id = CommonFunctions.getRandomId();
        team.setId(id);
        clubs.put(id, team);
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
        Club_Team team = Sim.getClub(Sim.findPlayerInClub(player_id));
        if (team != null) {
            team.getSeniorSquad().removePlayer(player_id);
        }
        players.remove(player_id);
    }

    public static void updatePlayer(Player player) {
        players.put(player.getId(), player);
    }

    public static String findTeamInLeague(Club_Team team) {
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

    public static String findTeamInDivision(Club_Team club) {
        LeagueStructure ls = getLeagueStructure(club.getNationId());
        for (Division x: ls.getDivisions().values()){
            if(x.getTeams().contains(club.getId()))
                return x.getId();
        }
        return "";
    }

    public static String findPlayerInClub(String player_id) {
        for (Map.Entry<String, Club_Team> entry : clubs.entrySet()) {
            if (entry.getValue().getAllPlayers().contains(player_id)) {
                return entry.getKey();
            }
        }
        return "";
    }

    public static String findPlayerInLeague(String player_id) {
        Club_Team team = Sim.getClub(Sim.findPlayerInClub(player_id));
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

    public static void initSetup() {
        int count = 0;
        List<Setups.Nation_Setup> setups = Setups.getNormalSetup();
        if (Sim.TEST) {
            setups = Setups.getTestSetup();
        }
        for (Setups.Nation_Setup setup : setups) {
            List<Setups.Division_Setup> divs = Setups.getLeagueStructureSetups(setup);
            Nation nation = new Nation(setup.name, setup.short_name, count, setup.league_ranking);
            Map<Integer, Division> divisions = new HashMap<>();
            for (Setups.Division_Setup div: divs) {
                Division d = CommonFunctions.createDivision(div, nation);
                divisions.put(div.level, d);
            }
            LeagueStructure struct = new LeagueStructure(nation.getId(), divisions);
            addLeagueStructure(nation.getId(), struct);
            Cup cup = new Cup(CatalogueHelper.getDenonym(nation.getName()) + " Cup", nation.getId());
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
            ControllerEurope_Swiss.initCoefficientsCalculations();
            //Controller_WCL.initCoefficientsCalculations();
        }
    }

    public static Map<String, Club_Team> sortBySquadRating(Map<String, Club_Team> map) {
        List<Map.Entry<String, Club_Team>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list,
                (Map.Entry<String, Club_Team> o1,
                        Map.Entry<String, Club_Team> o2) -> (int) ((o2.getValue().getBestSquadRating())
                                - (o1.getValue().getBestSquadRating())));
        HashMap<String, Club_Team> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Club_Team> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public static Map<String, Club_Team> sortByReputation(Map<String, Club_Team> map) {
        List<Map.Entry<String, Club_Team>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list,
                (Map.Entry<String, Club_Team> o1,
                        Map.Entry<String, Club_Team> o2) -> (int) ((o2.getValue().getReputation())
                                - (o1.getValue().getReputation())));
        HashMap<String, Club_Team> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Club_Team> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public static List<String> sortByReputation(List<String> teams) {
        Map<String, Club_Team> map = new HashMap<>();
        for(String team: teams) map.put(team, clubs.get(team));
        map = sortByReputation(map);
        return new ArrayList<>(map.keySet());
    }

    public static void showTeams() {
        System.out.println("\nTeams");
        Map<String, Club_Team> sorted = Sim.sortByReputation(clubs);
        int count = 1;
        for (Map.Entry<String, Club_Team> entry : sorted.entrySet()) {
            System.out.println(count + " " + entry.getValue().getPaddedName() + "\t"
                    + entry.getValue().getBestSquadRating() + "\t" + entry.getValue().getReputation());
            if (count >= 250) {
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

    public static List<Player> sortByRating(List<Player> players) {
        Collections.sort(players, Comparator.comparingInt(Player::getRating).reversed());
        return players;
    }

    public static void showPlayers() {
        System.out.println("\nPlayers");
        Map<String, Player> sorted = Sim.sortByRating(players);
        int count = 1;
        String team_name = "";
        Club_Team team;
        Nation nation;
        String code = "";
        for (Map.Entry<String, Player> entry : sorted.entrySet()) {
            Player player = entry.getValue();
            team = Sim.getClub(Sim.findPlayerInClub(player.getId()));
            if (team != null) {
                team_name = team.getPaddedName();
            }
            nation = Sim.getNation(player.getNationId());
            if (nation != null) {
                code = nation.getCode();
            }
            System.out.println(count + "\t" + player.getPaddedName() + code + "\t" + team_name + "\t"
                    + CatalogueHelper.getPositionName(player.getPosition()) + "\t"
                    + player.getRating() + "\t" + player.getAge() + "\t"
                    + CommonFunctions.formatMoney(MarketHelper.getPlayerValue(player, null)));
            if (count >= 50) {
                break;
            }
            count++;
        }
    }

    public static void seasonEndChanges() {
        // for (Map.Entry<Integer, Nation> entry : nations.entrySet()) {
        //     entry.getValue().promotionDemotion();
        // }

        for (Map.Entry<Integer, LeagueStructure> entry: league_structures.entrySet()) {
            entry.getValue().promoteAndRelegate();
        }

        // for (Map.Entry<String, League> entry : leagues.entrySet()) {
        //     entry.getValue().saveCompetitionHistory();
        //     updateLeague(entry.getValue(), entry.getKey());
        // }
        for (Map.Entry<String, Cup> entry : cups.entrySet()) {
            entry.getValue().saveCompetitionHistory();
            updateCup(entry.getValue(), entry.getKey());
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
        for (Map.Entry<String, Club_Team> entry : clubs.entrySet()) {
            entry.getValue().seasonEndPlayerPromotions();
        }
        System.out.println("season end changes done");
    }

    public static void showCompetitionHistories() {
        // for (Map.Entry<String, League> entry : leagues.entrySet()) {
        //     entry.getValue().showCompetitionHistory();
        // }
        // for (Map.Entry<String, Cup> entry : cups.entrySet()) {
        //     entry.getValue().showCompetitionHistory();
        // }
        if (SIMULATE_CONTINENTAL_COMPETITIONS && !TEST) {
            ControllerEurope_Swiss.showCompetitionHistory();
            // ControllerSAmerica.showCompetitionHistory();
            // ControllerAsia.showCompetitionHistory();
            // ControllerNAmerica.showCompetitionHistory();
            // ControllerAfrica.showCompetitionHistory();
            // ControllerCWC.showCompetitionHistory();
            // Controller_WCL.showCompetitionHistory();
        }
    }

    public static void showNationalTeams() {
        System.out.println("\nNational Teams");
        List<NationalTeam> teams = Internationals.sortByPoints(Sim.getNationFromContinent("all"));
        for (NationalTeam nat_team : teams) {
            nat_team.callup();
            System.out.println(
                    nat_team.getName() + " " + nat_team.getSquad().getBestLineupRating() + " " + nat_team.getPoints());
        }
    }

    public static void main(String[] args) {
        TEST = false;
        USE_TRANSFER_MARKET = true;
        int num_seasons = 2;
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
        if (Sim.SHOW_DETAILS && !Sim.TEST) {
            //showCompetitionHistories();
            //showTeams();
            //showPlayers();
            //ControllerEurope_Swiss.showCoefficients();
            //showNationalTeams();
            TransferMarket.showTopTransfers();
            //TransferMarket.showTransferStats();
            // ControllerEurope_Swiss.showLeagueRankings();
            // Controller_WCL.showLeagueRankings();
            // Controller_WCL.getClubRankingsForSeason(Sim.getSeasonNumber());
            // String x = Sim.sortBySquadRating(clubs).entrySet().iterator().next().getKey();
            // System.out.println(clubs.get(x).getName() + " (" + nations.get(clubs.get(x).getNationId()).getCode()+ ")");
            // Viewer.showTransferList(TransferMarket.getTransfersOrder(clubs.get(x)));
        }
        Viewer.showMenu();
    }
}