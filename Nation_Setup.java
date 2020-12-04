public class Nation_Setup {
    String name;
    String short_name;
    int national_team_ranking;
    int league_ranking;
    int num_leagues;
    int num_team_leagues;

    public Nation_Setup (String name, String shortString, int nat_rank, int leg_rank, int num_leg, int team_leg) {
        this.name = name;
        this.short_name = shortString;
        this.national_team_ranking = nat_rank;
        this.league_ranking = leg_rank;
        this.num_leagues = num_leg;
        this.num_team_leagues = team_leg;
    }
}
