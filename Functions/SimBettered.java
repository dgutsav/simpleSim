package Functions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Models.*;

public class SimBettered {
    private static Map<String, Club> clubs = new HashMap<>();
    private static Map<String, NationalTeam> national_teams = new HashMap<>();
    private static Map<String, Player> players = new HashMap<>();
    private static Map<Integer, Nation> nations = new HashMap<>();
    private static Map<String, Competition> competitions = new HashMap<>();

    private static int current_season = 2000;

    public static int getSeasonNumber() {
        return current_season;
    }

    public static String addClub(Club club) {
        String id = GeneratorFunctions.getRandomId();
        club.setId(id);
        clubs.put(club.getId(), club);
        return id;
    }

    public static void updateClub(Club club, String id) {
        clubs.put(id, club);
    }

    public static Club getClub(String id) {
        return clubs.get(id);
    }

    public static String addNationalTeam(NationalTeam nat_team) {
        String id = GeneratorFunctions.getRandomId();
        nat_team.setId(id);
        national_teams.put(nat_team.getId(), nat_team);
        return id;
    }

    public static void updateNationalTeam(NationalTeam nat_team, String id) {
        national_teams.put(id, nat_team);
    }

    public static NationalTeam getNationalTeam(String id) {
        return national_teams.get(id);
    }

    public static String getTeamName(String id) {
        if (clubs.containsKey(id)) {
            return SimBettered.getClub(id).getName();
        } else if (national_teams.containsKey(id)) {
            return SimBettered.getNationalTeam(id).getName();
        } else {
            return "";
        }
    }

    public static String addPlayer(Player player) {
        String id = GeneratorFunctions.getRandomId();
        player.setId(id);
        players.put(player.getId(), player);
        return id;
    }

    public static void updatePlayer(Player player, String id) {
        players.put(id, player);
    }

    public static Player getPlayer(String id) {
        return players.get(id);
    }

    public static List<Club> getAllClubs() {
        return new ArrayList<Club>(clubs.values());
    }

    public static List<Player> getAllPlayers() {
        return new ArrayList<Player>(players.values());
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

    public static void addNation(Nation nation) {
        nations.put(nation.getId(), nation);
    }

    public static Nation getNation(int id) {
        return nations.get(id);
    }

    public static String addCompetition(Competition Competition) {
        String id = GeneratorFunctions.getRandomId();
        Competition.setId(id);
        competitions.put(Competition.getId(), Competition);
        return id;
    }

    public static void updateCompetition(Competition Competition, String id) {
        competitions.put(id, Competition);
    }

    public static Competition getCompetition(String id) {
        return competitions.get(id);
    }

    public static String findTeamInLeague(Club team) {
        Nation nation = getNation(team.getNationId());
        if (nation != null) {
            for (String league_id : nation.getLeagues().values()) {
                Competition league = getCompetition(league_id);
                if (league != null && league.isDomesticLeague() && league.getTeams().contains(team.getId())) {
                    return league_id;
                }
            }
        }
        return "";
    }

    public static String findPlayerInClub(String player_id) {
        for (Map.Entry<String, Club> entry : clubs.entrySet()) {
            if (entry.getValue().getAllPlayers().contains(player_id)) {
                return entry.getKey();
            }
        }
        return "";
    }

    public static String findPlayerInLeague(String player_id) {
        Club team = getClub(findPlayerInClub(player_id));
        if (team != null) {
            return findTeamInLeague(team);
        }
        return "";
    }

    public static Squad[] getSquads(Match match) {
        Squad[] squads = new Squad[2];
        if (match.isInternational()) {
            squads[0] = getNationalTeam(match.getHomeId()).getSquad();
            squads[1] = getNationalTeam(match.getAwayId()).getSquad();
        } else if (match.isYouth()) {
            squads[0] = getClub(match.getHomeId()).getYouthSquad();
            squads[1] = getClub(match.getAwayId()).getYouthSquad();
        } else {
            squads[0] = getClub(match.getHomeId()).getSeniorSquad();
            squads[1] = getClub(match.getAwayId()).getSeniorSquad();
        }
        return squads;
    }

    public static void loadSetup() {
        String[] nations_list = SetupHelper.getNationsList();
        int count = 1;
        for (String item : nations_list) {
            nations.put(count, GeneratorFunctions.getNation(item, count));
            count++;
        }

        System.out.println(nations.size());
        System.out.println(clubs.size());
        System.out.println(competitions.size());
        System.out.println(players.size());
    }

    public static void simulateCompetitions() {
        Calendar current_date = Calendar.getInstance();
        current_date.set(current_season, Calendar.JANUARY, 1);
        Calendar end_date = Calendar.getInstance();
        end_date.set(current_season, Calendar.DECEMBER, 31);
        while (current_date.before(end_date)) {
            for (Map.Entry<String, Competition> entry : competitions.entrySet()) {
                entry.getValue().simulate(current_date);
            }
            current_date.add(Calendar.DATE, 1);
        }
    }

    public static void main(String args[]) {
        loadSetup();
        //simulateCompetitions();
    }
}
