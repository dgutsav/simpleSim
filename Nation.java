import java.util.ArrayList;
import java.util.List;

public class Nation {
    private List<String> leagues;
    private String name;
    private String code;
    private int id;
    private int league_ranking;
    private String cup;
    private int youth_rating;
    private String continent;
    private String national_team;

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

    public int getLeagueRanking() {
        return this.league_ranking;
    }

    public String getCode() {
        return this.code;
    }

    public List<String> getLeagues() {
        return this.leagues;
    }

    public void setYouthRating(int youth) {
        this.youth_rating = youth;
    }

    public int getYouthRating() {
        return this.youth_rating;
    }

    public void getContinentalTeams(int continental_competition) {
        int start_rank = 1;
        int end_rank = 1;
        switch (continental_competition) {
            case 1:
                start_rank = 1;
                end_rank = ControllerUEFA.getPlacesForCompetition(1, this.league_ranking);
                break;
            case 2:
                start_rank = ControllerUEFA.getPlacesForCompetition(1, this.league_ranking) + 1;
                end_rank = ControllerUEFA.getPlacesForCompetition(1, this.league_ranking)
                        + ControllerUEFA.getPlacesForCompetition(2, this.league_ranking);
                break;
            case 3:
                start_rank = ControllerUEFA.getPlacesForCompetition(1, this.league_ranking)
                        + ControllerUEFA.getPlacesForCompetition(2, this.league_ranking) + 1;
                end_rank = ControllerUEFA.getPlacesForCompetition(1, this.league_ranking)
                        + ControllerUEFA.getPlacesForCompetition(2, this.league_ranking)
                        + ControllerUEFA.getPlacesForCompetition(3, this.league_ranking);
                break;
            default:
                start_rank = 1;
                end_rank = ControllerUEFA.getPlacesForCompetition(1, this.league_ranking);
        }
        for (int i = start_rank; i <= end_rank; i++) {
            ControllerUEFA.addTeams(continental_competition, this.id, i, this.getLeague(1).getTeamAtRank(i));
        }
    }

    public void promotionDemotion() {
        if (this.leagues.size() <= 1) {
            return;
        }
        int num_divisions = this.leagues.size();
        for (int i = 1; i < num_divisions; i++) {
            League upper = this.getLeague(i);
            League lower = this.getLeague(i + 1);
            List<String> promoted_teams = lower.getPromotedTeams();
            List<String> relegated_teams = upper.getRelegatedTeams();
            if (promoted_teams.size() != relegated_teams.size()) {
                System.out.println("Inequal movement of teams");
            } else {
                int exchange = promoted_teams.size();
                for (int j = 0; j < exchange; j++) {
                    upper.removeTeam(relegated_teams.get(j));
                    lower.removeTeam(promoted_teams.get(j));
                    upper.addTeam(promoted_teams.get(j));
                    lower.addTeam(relegated_teams.get(j));
                }
                boolean validated = true;
                for (int j = 0; j < exchange; j++) {
                    if (!upper.isTeamInLeague(promoted_teams.get(j))) {
                        validated = false;
                    }
                    if (!lower.isTeamInLeague(relegated_teams.get(j))) {
                        validated = false;
                    }
                }
                if (!validated) {
                    System.out.println("Error in promotion demotion");
                } else {
                    Sim.updateLeague(lower, lower.getId());
                    Sim.updateLeague(upper, upper.getId());
                }
            }
        }
    }

    public List<String> getTeamsForCup() {
        List<String> all_teams = new ArrayList<>();
        int total_leagues = this.leagues.size();
        for (int i = total_leagues; i > 0; i--) {
            League league = this.getLeague(i);
            int league_size = league.getTeams().size();
            for (int j = league_size; j > 0; j--) {
                all_teams.add(league.getTeamAtRank(j));
            }
        }
        return all_teams;
    }

    public void setCupId(String cup_id) {
        this.cup = cup_id;
    }

    public Cup getCup() {
        return Sim.getCup(this.cup);
    }

    public String getCupId() {
        return this.cup;
    }

    public String getCupWinner() {
        return Sim.getCup(this.cup).getWinner();
    }

    public String getTeamAtRank(int rank) {
        return this.getLeague(1).getTeamAtRank(rank);
    }

    public String getContinent() {
        return this.continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public NationalTeam getNationalTeam() {
        return Sim.getNationalTeam(this.national_team);
    }

    public String getNationalTeamId() {
        return this.national_team;
    }

    public void setNationalTeamId(String id) {
        this.national_team = id;
    }
}
