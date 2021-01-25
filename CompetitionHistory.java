import java.util.ArrayList;
import java.util.List;

public class CompetitionHistory {
    private int season;
    private List<TeamHistory> team_history;

    private static final int TEAM_NAME_LENGTH = 30;

    public CompetitionHistory(int season) {
        this.season = season;
        this.team_history = new ArrayList<>();
    }

    public CompetitionHistory(int season, List<TeamHistory> team) {
        this.season = season;
        this.team_history = team;
    }

    public int getSeason() {
        return this.season;
    }

    public List<TeamHistory> getTeamHistories() {
        return this.team_history;
    }

    public TeamHistory getTeamHistory(int rank) {
        for (TeamHistory history : team_history) {
            if (history.getRank() == rank) {
                return history;
            }
        }
        return null;
    }

    public static String padString(String string) {
        for (int i = string.length(); i < TEAM_NAME_LENGTH; i++) {
            string = string + " ";
        }
        return string;
    }

    public void show(int rank, boolean international) {
        System.out.print(season);
        for (int i = 1; i <= rank; i++) {
            TeamHistory hist = this.getTeamHistory(i);
            String team_name = "";
            if (international) {
                team_name = Sim.getNationalTeam(hist.getTeamId()).getName();
            } else {
                team_name = Sim.getClub(hist.getTeamId()).getName();
            }
            team_name += "(" + hist.getRating() + ")";
            System.out.print("\t" + CompetitionHistory.padString(team_name));
        }
        System.out.println();
    }
}
