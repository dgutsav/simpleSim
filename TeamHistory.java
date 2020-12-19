public class TeamHistory {
    private String team_id;
    private int rating = 0;
    private int rank;

    public TeamHistory(String team, int standing, int rating) {
        this.team_id = team;
        this.rank = standing;
        this.rating = rating;
    }

    public String getTeamId() {
        return this.team_id;
    }

    public int getRank() {
        return this.rank;
    }

    public int getRating() {
        return this.rating;
    }

    public void show() {
        System.out.println(rank + "  " + Sim.getTeam(team_id).getName() + "(" + rating + ")");
    }
}
