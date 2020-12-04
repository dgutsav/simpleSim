import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sim {
    static Map<String, League> leagues = new HashMap<>();

    static int num_teams = 20;
    static Team[] teams = new Team[num_teams];

    static Map<String, Team> teams_map = new HashMap<>();
    static List<Match> matches = new ArrayList<>();
    static Map<Integer, Nation> nations = new HashMap<>();

    //static void initTeams() {
    //    String[] team_names = { "Arsenal", "Aston Villa", "Leeds United", "Brighton", "Burnley", "Newcastle United",
    //            "Manchester United", "Tottenham", "Manchester City", "Liverpool", "Chelsea", "Leicester", "Wolves",
    //            "Crystal Palace", "West Ham", "Everton", "Fulham", "West Brom", "Southampton", "Sheffield United" };
    //    int[] team_ratings = { 81, 75, 76, 74, 76, 75, 82, 85, 86, 86, 85, 83, 81, 78, 78, 81, 75, 75, 79, 75 };
    //    for (int i = 0; i < team_names.length; i++) {
    //        Team team = new Team();
    //        team.name = team_names[i];
    //        team.squad_rating = team_ratings[i];
    //        teams[i] = team;
    //    }
    //}

    //static void initLeagues() {
    //    League league = new League("Premier League", 1, 0, 0);
    //    for (int i = 0; i < teams.length; i++) {
    //        league.addTeam(i);
    //    }
    //    league.seasonStart();
    //    leagues.put(leagues.size(), league);
    //}

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

    public static String addTeam (Team team) {
        String id = Base.getRandomId();
        team.id = id;
        teams_map.put(id, team);
        return id;
    }

    public static String addLeague (League league) {
        String id = Base.getRandomId();
        league.setId(id);
        leagues.put(id, league);
        return id;
    }

    public static void init() {
        Nation nat;
        League league;
        String league_id;
        nat = new Nation("Austria", 1, 1);
        league = Base.createLeague(80, 10, 5, nat.getName());
        league_id = Sim.addLeague(league);
        nat.addLeague(league_id);
        nations.put(nat.getId(), nat);
        
        /*nat = new Nation("Spain", 2, 2);
        league = Base.createLeague(79, 20, 4, nat.getName());
        league_id = Sim.addLeague(league);
        nat.addLeague(league_id);
        nations.put(nat.getId(), nat);

        nat = new Nation("Germany", 3, 3);
        league = Base.createLeague(78, 18, 3, nat.getName());
        league_id = Sim.addLeague(league);
        nat.addLeague(league_id);
        nations.put(nat.getId(), nat);

        nat = new Nation("Italy", 4, 4);
        league = Base.createLeague(76, 20, 4, nat.getName());
        league_id = Sim.addLeague(league);
        nat.addLeague(league_id);
        nations.put(nat.getId(), nat);

        nat = new Nation("France", 5, 5);
        league = Base.createLeague(74, 20, 2, nat.getName());
        league_id = Sim.addLeague(league);
        nat.addLeague(league_id);
        nations.put(nat.getId(), nat);*/
    }

    public static List<Nation_Setup> setup () {
        List<Nation_Setup> setups = new ArrayList<>();
        setups.add(new Nation_Setup("England", "ENG", 1, 1, 1, 20));
        setups.add(new Nation_Setup("Spain", "ESP", 2, 2, 1, 20));
        setups.add(new Nation_Setup("Germany", "GER", 3, 3, 1, 18));
        setups.add(new Nation_Setup("Italy", "ITA", 4, 4, 1, 20));
        setups.add(new Nation_Setup("France", "FRA", 5, 5, 1, 20));
        setups.add(new Nation_Setup("Portugal", "POR", 6, 6, 1, 18));
        setups.add(new Nation_Setup("Belgium", "BEL", 7, 7, 1, 16));
        setups.add(new Nation_Setup("Netherlands", "NED", 8, 8, 1, 18));
        setups.add(new Nation_Setup("Russia", "RUS", 9, 9, 1, 16));
        setups.add(new Nation_Setup("Ukraine", "UKR", 10, 10, 1, 10));
        setups.add(new Nation_Setup("Austria", "AUT", 11, 11, 1, 12));
        setups.add(new Nation_Setup("Scotland", "SCO", 12, 12, 1, 12));
        setups.add(new Nation_Setup("Turkey", "TUR", 13, 13, 1, 16));
        setups.add(new Nation_Setup("Denmark", "DEN", 14, 14, 1, 14));
        setups.add(new Nation_Setup("Croatia", "CRO", 15, 15, 1, 10));
        return setups;
    }

    public static void main(String args[]) {
        //initTeams();
        //initLeagues();
        init();
        sim();
    }
}