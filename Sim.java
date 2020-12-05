import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Sim {
    static Map<String, League> leagues = new HashMap<>();

    static int num_teams = 20;
    static Team[] teams = new Team[num_teams];

    static Map<String, Team> teams_map = new HashMap<>();
    static List<Match> matches = new ArrayList<>();
    static Map<Integer, Nation> nations = new HashMap<>();

    // static void initTeams() {
    // String[] team_names = { "Arsenal", "Aston Villa", "Leeds United", "Brighton",
    // "Burnley", "Newcastle United",
    // "Manchester United", "Tottenham", "Manchester City", "Liverpool", "Chelsea",
    // "Leicester", "Wolves",
    // "Crystal Palace", "West Ham", "Everton", "Fulham", "West Brom",
    // "Southampton", "Sheffield United" };
    // int[] team_ratings = { 81, 75, 76, 74, 76, 75, 82, 85, 86, 86, 85, 83, 81,
    // 78, 78, 81, 75, 75, 79, 75 };
    // for (int i = 0; i < team_names.length; i++) {
    // Team team = new Team();
    // team.name = team_names[i];
    // team.squad_rating = team_ratings[i];
    // teams[i] = team;
    // }
    // }

    // static void initLeagues() {
    // League league = new League("Premier League", 1, 0, 0);
    // for (int i = 0; i < teams.length; i++) {
    // league.addTeam(i);
    // }
    // league.seasonStart();
    // leagues.put(leagues.size(), league);
    // }

    public static void updateTeam(Team team, String id) {
        teams_map.put(id, team);
    }

    public static Team getTeam(String id) {
        if (teams_map.containsKey(id)) {
            return teams_map.get(id);
        } else {
            return null;
        }
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

    public static void sim() {
        for (Map.Entry<String, League> entry : leagues.entrySet()) {
            entry.getValue().seasonStart();
            entry.getValue().simulateSeason();
        }
    }

    public static void updateTeamForm(String team_id, int result) {
        Team t = getTeam(team_id);
        if (t != null) {
            t.updateForm(result);
        }
    }

    public static String addTeam(Team team) {
        String id = Base.getRandomId();
        team.id = id;
        teams_map.put(id, team);
        return id;
    }

    public static String addLeague(League league) {
        String id = Base.getRandomId();
        league.setId(id);
        leagues.put(id, league);
        return id;
    }

    public static List<Nation_Setup> getSetups() {
        List<Nation_Setup> setups = new ArrayList<>();
        setups.add(new Nation_Setup("England", "ENG", 1, 1, 1, 20, 81));
        setups.add(new Nation_Setup("Spain", "ESP", 2, 2, 1, 20, 78));
        setups.add(new Nation_Setup("Germany", "GER", 3, 3, 1, 18, 79));
        setups.add(new Nation_Setup("Italy", "ITA", 4, 4, 1, 20, 78));
        setups.add(new Nation_Setup("France", "FRA", 5, 5, 1, 20, 74));
        setups.add(new Nation_Setup("Portugal", "POR", 6, 6, 1, 18, 70));
        setups.add(new Nation_Setup("Belgium", "BEL", 7, 7, 1, 16, 68));
        setups.add(new Nation_Setup("Netherlands", "NED", 8, 8, 1, 18, 69));
        setups.add(new Nation_Setup("Russia", "RUS", 9, 9, 1, 16, 68));
        setups.add(new Nation_Setup("Ukraine", "UKR", 10, 10, 1, 10, 67));
        setups.add(new Nation_Setup("Austria", "AUT", 11, 11, 1, 12, 64));
        setups.add(new Nation_Setup("Scotland", "SCO", 12, 12, 1, 12, 64));
        setups.add(new Nation_Setup("Turkey", "TUR", 13, 13, 1, 16, 64));
        setups.add(new Nation_Setup("Denmark", "DEN", 14, 14, 1, 14, 60));
        setups.add(new Nation_Setup("Croatia", "CRO", 15, 15, 1, 10, 62));
        return setups;
    }

    public static void initSetup() {
        int count = 0;
        List<Nation_Setup> setups = Sim.getSetups();
        for (Nation_Setup setup : setups) {
            Nation nation = new Nation(setup.name, setup.short_name, count, setup.league_ranking);
            League league = Base.createLeague(setup.league_rating, setup.num_team_leagues, Base.getRandomNumber(4),
                    nation.getName());
            String league_id = Sim.addLeague(league);
            nation.addLeague(league_id);
            nations.put(nation.getId(), nation);
            count++;
        }
    }

    public static Map<String, Team> sortByElo(Map<String, Team> map) {
        List<Map.Entry<String, Team>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Team>>() {
            public int compare(Map.Entry<String, Team> o1, Map.Entry<String, Team> o2) {
                return (int) ((o2.getValue().getEloRating()) - (o1.getValue().getEloRating()));
            }
        });
        HashMap<String, Team> temp = new LinkedHashMap<String, Team>();
        for (Map.Entry<String, Team> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public static void showElo() {
        Map<String, Team> sorted = Sim.sortByElo((HashMap<String, Team>) teams_map);
        int count = 1;
        for (Map.Entry<String, Team> entry : sorted.entrySet()) {
            System.out.println(count + " " + entry.getValue().getName() + " " + entry.getValue().getEloRating());
            count++;
        }
    }

    public static void main(String args[]) {
        initSetup();
        sim();
        showElo();
    }
}