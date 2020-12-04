import java.util.List;

public class LeagueTable {
    public TeamPerformance[] performances;
    
    public LeagueTable(int size) {
        performances = new TeamPerformance[size];
    }
    
    public void startNewSeason(List<String> list) {
        Team[] teams = Sim.getTeamArray(list);
        for(int  i = 0; i<teams.length;i++) {
            //System.out.println(teams[i].name);
            performances[i] = new TeamPerformance(teams[i].id, teams[i].name);
        }
    }

    private void diag() {
        for(int i=0; i<performances.length; i++) {
            System.out.print(performances[i].points() + "   ");
        }
        System.out.println();
    }
    
    public void sortTable() {
        int num_teams = performances.length;
        int big, index;
        TeamPerformance temp;
        for(int i=0; i<num_teams; i++) {
            //diag();
            big = performances[i].points();
            index = i;
            for(int j=i+1; j<num_teams; j++) {
                if(big < performances[j].points()) {
                    big = performances[j].points();
                    index = j;
                } else if (big == performances[j].points()) {
                    if(performances[i].goal_difference() < performances[j].goal_difference()) {
                        index = j;
                    } else if (performances[i].goal_difference() == performances[j].goal_difference()) {
                        if(performances[i].matches_won() < performances[j].matches_won()) {
                            index = j;
                        } else if (performances[i].matches_won() == performances[j].matches_won()) {
                            if(performances[i].goals_scored() < performances[j].goals_scored()) {
                                index = j;
                            } else {
                                //nothing to do here
                            }
                        }
                    }
                }
            }
            temp = performances[i];
            performances[i] = performances[index];
            performances[index] = temp;
            //diag();
        }
    }
    
    public String getTeamAtRank(int rank) {
        sortTable();
        if(rank > 0 && rank <= performances.length) {
            return performances[rank-1].getTeamId();
        }
        return null;
    }

    public void updateTeamPerformance(String id, int scored, int conceded) {
        for(int i=0;i<performances.length;i++) {
            if(performances[i].getTeamId().equals(id)) {
                performances[i].updatePerformance(scored, conceded);
            }
        }
    }

    public void showLong() {
        sortTable();
        for(int i=0; i<performances.length; i++) {
            System.out.println((i+1) + " " + performances[i].displayShort());
        }
    }

    public void showShort() {
        sortTable();
        for(int i=0; i<performances.length; i++) {
            System.out.println((i+1) + " " + performances[i].displayShort());
        }
    }
}
