package Stages.Helpers;

import java.util.Comparator;
import java.util.List;

public class Table implements Comparator<Performance> {
    public Performance[] performances;
    private boolean is_sorted = false;

    public Table(int size) {
        this.performances = new Performance[size];
    }

    public void setup(List<String> ids) {
        for (int i = 0; i < ids.size(); i++) {
            performances[i] = new Performance(ids.get(i));
        }
    }

    public void updatePerformance(String id, int scored, int conceded, boolean home) {
        for (int i = 0; i < performances.length; i++) {
            if (performances[i].getId().equals(id)) {
                performances[i].updatePerformance(scored, conceded, home);
                is_sorted = false;
                break;
            }
        }
    }

    public void sortTable() {
        if (is_sorted) {
            return;
        }
        int size = performances.length;
        Performance temp;
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < (size - 1 - i); j++) {
                if (this.compare(performances[j], performances[j + 1]) < 0) {
                    temp = performances[j];
                    performances[j] = performances[j + 1];
                    performances[j + 1] = temp;
                }
            }
        }
        is_sorted = true;
    }

    @Override
    public int compare(Performance o1, Performance o2) {
        int diff1 = o1.points() - o2.points();
        if (diff1 == 0) {
            int diff2 = o1.goalDifference() - o2.goalDifference();
            if (diff2 == 0) {
                int diff3 = o1.wins() - o2.wins();
                if (diff3 == 0) {
                    return o1.goalScored() - o2.goalScored();
                } else {
                    return diff3;
                }
            } else {
                return diff2;
            }
        } else {
            return diff1;
        }
    }

    public String getTeamAtRank(int rank) {
        if (rank > 0 && rank <= this.performances.length) {
            return this.performances[rank - 1].getId();
        }
        return "";
    }

    public int getRankOfTeam(String team_id) {
        for (int i = 0; i < this.performances.length; i++) {
            if (this.performances[i].getId().equals(team_id)) {
                return i + 1;
            }
        }
        return this.performances.length;
    }

    public Performance getPerformance(int rank) {
        return this.getPerformance(this.getTeamAtRank(rank));
    }

    public Performance getPerformance(String team_id) {
        for (Performance perf : this.performances) {
            if (perf.getId().equals(team_id)) {
                return perf;
            }
        }
        return new Performance(team_id);
    }
}
