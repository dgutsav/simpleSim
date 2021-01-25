import java.util.List;

public class Table {
    public TeamPerformance[] performances;
    public boolean is_international = false;

    public Table(int size, boolean internationals) {
        performances = new TeamPerformance[size];
        is_international = internationals;
    }

    public void startNewSeason(List<String> list) {
        if (!this.is_international) {
            Club_Team[] teams = Sim.getClubArray(list);
            for (int i = 0; i < teams.length; i++) {
                performances[i] = new TeamPerformance(teams[i].getId(), teams[i].getName());
            }
        } else {
            NationalTeam[] nat_teams = Sim.getNationalTeamArray(list);
            for (int i = 0; i < nat_teams.length; i++) {
                performances[i] = new TeamPerformance(nat_teams[i].getId(), nat_teams[i].getName());
            }
        }
    }

    public void sortTable() {
        int num_teams = performances.length;
        int big;
        int index;
        TeamPerformance temp;
        for (int i = 0; i < num_teams; i++) {
            big = performances[i].points();
            index = i;
            for (int j = i + 1; j < num_teams; j++) {
                if (big < performances[j].points()) {
                    big = performances[j].points();
                    index = j;
                } else if (big == performances[j].points()) {
                    if (performances[i].goalDifference() < performances[j].goalDifference()) {
                        index = j;
                    } else if (performances[i].goalDifference() == performances[j].goalDifference()) {
                        if (performances[i].matchesWon() < performances[j].matchesWon()) {
                            index = j;
                        } else if (performances[i].matchesWon() == performances[j].matchesWon()) {
                            if (performances[i].goalsScored() < performances[j].goalsScored()) {
                                index = j;
                            } else {
                                // nothing to do here
                            }
                        }
                    }
                }
            }
            temp = performances[i];
            performances[i] = performances[index];
            performances[index] = temp;
        }
    }

    public String getTeamAtRank(int rank) {
        sortTable();
        if (rank > 0 && rank <= performances.length) {
            return performances[rank - 1].getTeamId();
        }
        return null;
    }

    public int getRankOfTeam(String team_id) {
        this.sortTable();
        for (int i = 0; i < performances.length; i++) {
            if (team_id.equalsIgnoreCase(performances[i].getTeamId())) {
                return i + 1;
            }
        }
        return -1;
    }

    public void updateTeamPerformance(String id, int scored, int conceded) {
        for (int i = 0; i < performances.length; i++) {
            if (performances[i].getTeamId().equals(id)) {
                performances[i].updatePerformance(scored, conceded);
            }
        }
    }

    public void showLong() {
        sortTable();
        System.out.println();
        for (int i = 0; i < performances.length; i++) {
            System.out.println((i + 1) + " " + performances[i].displayLong());
        }
    }

    public void showShort() {
        sortTable();
        for (int i = 0; i < performances.length; i++) {
            System.out.println((i + 1) + " " + performances[i].displayShort());
        }
    }

    public TeamPerformance getTeamPerformance(String team_id) {
        for (TeamPerformance performance : this.performances) {
            if (performance.getTeamId().equalsIgnoreCase(team_id)) {
                return performance;
            }
        }
        return new TeamPerformance(team_id, "");
    }
}
