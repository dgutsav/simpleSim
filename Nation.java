import java.util.ArrayList;
import java.util.List;

public class Nation {
    private List<String> leagues;
    private String name;
    private String code;
    private int id;
    private int league_ranking;

    public Nation(String name, String code, int id, int rank) {
        this.name = name;
        this.code = code;
        this.id = id;
        this.league_ranking = rank;
        this.leagues = new ArrayList<>();
    }

    public void addLeague(String league_id) {
        leagues.add(league_id);
    }

    public League getLeague(int level) {
        League[] leagues_list = Sim.getLeagues(this.leagues);
        for (League league : leagues_list) {
            if (league.getCompetitionLevel() == level) {
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
        return this.league_ranking;
    }

    public String getCode() {
        return this.code;
    }

    public void getContinentalTeams(int continental_competition) {
        int start_rank = 1;
        int end_rank = 1;
        switch (continental_competition) {
            case 1:
                start_rank = 1;
                end_rank = ContinentalCompetitionCentre.getPlacesForCompetition(1, this.league_ranking);
                break;
            case 2:
                start_rank = ContinentalCompetitionCentre.getPlacesForCompetition(1, this.league_ranking) + 1;
                end_rank = ContinentalCompetitionCentre.getPlacesForCompetition(1, this.league_ranking)
                        + ContinentalCompetitionCentre.getPlacesForCompetition(2, this.league_ranking);
                break;
            case 3:
                start_rank = ContinentalCompetitionCentre.getPlacesForCompetition(1, this.league_ranking)
                        + ContinentalCompetitionCentre.getPlacesForCompetition(2, this.league_ranking) + 1;
                end_rank = ContinentalCompetitionCentre.getPlacesForCompetition(1, this.league_ranking)
                        + ContinentalCompetitionCentre.getPlacesForCompetition(2, this.league_ranking)
                        + ContinentalCompetitionCentre.getPlacesForCompetition(3, this.league_ranking);
                break;
            default:
                start_rank = 1;
                end_rank = ContinentalCompetitionCentre.getPlacesForCompetition(1, this.league_ranking);
        }
        for (int i = start_rank; i <= end_rank; i++) {
            ContinentalCompetitionCentre.addTeams(continental_competition, this.code + i, this.getLeague(1).getTeamAtRank(i));
        }
    }
}
