import java.util.ArrayList;
import java.util.List;

public class Nation {
    private List<String> leagues;
    private String name;
    private int id;
    private int ranking;

    //Continental places
    private int[] continental_places;

    public Nation(String name, int id, int rank) {
        this.name = name;
        this.id = id;
        this.ranking = rank;
        this.leagues = new ArrayList<>();
    }

    public void addLeague(String league_id) {
        leagues.add(league_id);
    }

    public League getLeague (int level) {
        League[] leagues_list = Sim.getLeagues(this.leagues);
        for(League league : leagues_list) {
            if(league.getCompetitionLevel() == level) {
                return league;
            }
        }
        return null;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public int getRanking() {
        return this.ranking;
    }

    public List<String> getContinentalTeams (int continental_competition) {
        int start_rank = 1; 
        int end_rank = 1;
        switch(continental_competition) {
            case 0:
                start_rank = 1;
                end_rank = continental_places[0];
                break;
            case 1:
                start_rank = continental_places[0] + 1;
                end_rank = continental_places[0] + continental_places[1];
                break;
            case 2:
                start_rank = continental_places[0] + continental_places[1] + 1;
                end_rank = continental_places[0] + continental_places[1] + continental_places[2];
                break;
            default:
                start_rank = 1;
                end_rank = continental_places[0];
        }
        List<String> continental_teams = new ArrayList<>();
        for(int i=start_rank; i<=end_rank; i++) {
            continental_teams.add(this.getLeague(0).getTeamAtRank(i));
        }
        return continental_teams;
    }
}
