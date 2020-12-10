public class TeamHistory {
    private String team_id;
    private int rank;

    public TeamHistory(String team, int standing) {
        this.team_id = team;
        this.rank = standing;
    }

    public String getTeamId() {
        return this.team_id;
    }

    public int getRank() {
        return this.rank;
    }

    public void show() {
        System.out.println(rank + "  " + Sim.getTeam(team_id).getName());
    }
}
