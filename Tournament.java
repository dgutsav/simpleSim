import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tournament {
    private Map<String, String> teams = new HashMap<>();
    private int number_of_groups;
    private int competition_level;
    private League[] groups;
    private Map<Integer, List<KnockoutTie>> knockoutRound;

    public Tournament(int level, int num_groups) {
        this.competition_level = level;
        this.number_of_groups = num_groups;
        this.groups = new League[num_groups];
        for (int i = 0; i < num_groups; i++) {
            this.groups[i] = new League();
        }
        this.knockoutRound = new HashMap<>();
    }

    public String getName() {
        return TournamentController.getName(this.competition_level);
    }

    public void setTeams(Map<String, String> contininental_teams) {
        this.teams = contininental_teams;
    }

    public Map<String, String> getTeams() {
        return this.teams;
    }

    public void setGroupNumber(int num) {
        this.number_of_groups = num;
    }

    public void setCompetitionLevel(int level) {
        this.competition_level = level;
    }

    public void setupGroups(String[][] group_teams) {
        for (int i = 0; i < TournamentController.getNumGroups(competition_level); i++) {
            for (String team : group_teams[i]) {
                this.groups[i].addTeam(teams.get(team));
            }
            this.groups[i].setFixtures(FixtureScheduler.scheduleFixtures(this.groups[i].getTeams(), false));
            this.groups[i].setupTable();
        }
    }

    public void simulateGroup(int group_number) {
        System.out.println("\nGroup " + TournamentController.getGroupName(group_number));
        for (int i = 1; i <= TournamentController.GROUP_MATCHDAYS; i++) {
            System.out.println("Matchday " + i);
            this.groups[group_number].simulateMatchDay(i);
        }
        this.groups[group_number].getTable().showLong();
    }

    public void simulateGroups() {
        for (int i = 0; i < number_of_groups; i++) {
            simulateGroup(i);
        }
    }

    public List<String> getTeamsAtRank(int rank) {
        List<String> ranks = new ArrayList<>();
        for (int i = 0; i < this.number_of_groups; i++) {
            ranks.add(this.groups[i].getTeamAtRank(rank));
        }
        return ranks;
    }

    public String[] getTransferredTeams() {
        String[] transferred = new String[this.number_of_groups];
        TeamPerformance[] performances = new TeamPerformance[this.number_of_groups];
        for (int i = 0; i < this.number_of_groups; i++) {
            performances[i] = this.groups[i].getTable().getTeamPerformance(
                    this.groups[i].getTeamAtRank(TournamentController.TEAMS_PROGRESS_FROM_GROUP + 1));
        }
        LeagueTable table = new LeagueTable(this.number_of_groups);
        table.performances = performances;
        table.sortTable();
        for (int i = 1; i <= this.number_of_groups; i++) {
            transferred[i - 1] = table.getTeamAtRank(i);
        }
        return transferred;
    }

    public List<List<String>> getKnockoutPots() {
        List<String> seeded = this.getTeamsAtRank(1);
        List<String> unseeded = this.getTeamsAtRank(2);
        if (this.competition_level > 1) {
            String[] transferredTeams = TournamentController.getCompetition(this.competition_level - 1)
                    .getTransferredTeams();
            for (int i = 0; i < transferredTeams.length; i++) {
                if (i < transferredTeams.length / 2) {
                    seeded.add(transferredTeams[i]);
                } else {
                    unseeded.add(transferredTeams[i]);
                }
            }
        }
        List<List<String>> pots = new ArrayList<>();
        pots.add(seeded);
        pots.add(unseeded);
        return pots;
    }

    public List<String> getPossibleOpponents(String seeded_team, List<String> opponents) {
        List<String> possible_opponents = new ArrayList<>();
        String seeded_code = TournamentController.getCodeForTeam(seeded_team);
        String[][] coded_groups = TournamentController.getGroups(this.competition_level);
        for (String opponent : opponents) {
            String opponent_code = TournamentController.getCodeForTeam(opponent);
            if (!TournamentController.fromSameNation(seeded_code, opponent_code)
                    && !TournamentController.fromSameGroup(coded_groups, seeded_code, opponent_code)) {
                possible_opponents.add(opponent);
            }
        }
        return possible_opponents;
    }

    public String drawSeededTeam(List<String> seeded, List<String> unseeded) {
        for (String seeded_team : seeded) {
            if (getPossibleOpponents(seeded_team, unseeded).size() == 1) {
                return seeded_team;
            }
        }
        return seeded.get(CommonFunctions.getRandomNumber(seeded.size()));
    }

    public void setKnockoutTies() {
        List<List<String>> pots = this.getKnockoutPots();
        List<String> seeded = pots.get(0);
        List<String> unseeded = pots.get(1);
        System.out.println(this.getName() + "\n" + seeded + "\n" + unseeded);
        List<KnockoutTie> ties = new ArrayList<>();
        while (!seeded.isEmpty()) {
            String drawn_team = this.drawSeededTeam(seeded, unseeded);
            List<String> possible_opponents = this.getPossibleOpponents(drawn_team, unseeded);
            if (possible_opponents.isEmpty()) {
                this.setKnockoutTies();
                return;
            }
            String opponent_team = possible_opponents.get(CommonFunctions.getRandomNumber(possible_opponents.size()));
            System.out.println(opponent_team + " " + drawn_team);
            seeded.remove(drawn_team);
            unseeded.remove(opponent_team);
            KnockoutTie tie = new KnockoutTie(opponent_team, drawn_team);
            ties.add(tie);
        }
        if (ties.isEmpty()) {
            System.out.println("No ties set " + this.getName());
        }
        this.knockoutRound.put(1, ties);
    }

    public List<String> simulateKnockoutRound(int round) {
        List<KnockoutTie> ties = this.knockoutRound.get(round);
        List<String> progressed_teams = new ArrayList<>();
        for (int i = 0; i < ties.size(); i++) {
            ties.get(i).simulate();
            progressed_teams.add(ties.get(i).getWinner());
        }
        this.knockoutRound.put(round, ties);
        return progressed_teams;
    }

    public List<KnockoutTie> drawRound(List<String> teamList) {
        List<KnockoutTie> ties = new ArrayList<>();
        while (!teamList.isEmpty()) {
            String team1 = teamList.remove(CommonFunctions.getRandomNumber(teamList.size()));
            String team2 = teamList.remove(CommonFunctions.getRandomNumber(teamList.size()));
            KnockoutTie tie = new KnockoutTie(team2, team1);
            ties.add(tie);
        }
        return ties;
    }

    public void simulateKnockoutRounds() {
        int current_round = 1;
        if (this.knockoutRound.get(current_round).isEmpty()) {
            System.out.println("No matches found");
        }
        while (!this.knockoutRound.get(current_round).isEmpty()) {
            List<String> next_teams = this.simulateKnockoutRound(current_round);
            showKnockoutTies(current_round);
            if (next_teams.size() <= 1) {
                break;
            }
            List<KnockoutTie> next_ties = drawRound(next_teams);
            current_round++;
            this.knockoutRound.put(current_round, next_ties);
        }
        this.show();
    }

    public String getWinner() {
        int count = 1;
        while (true) {
            if (this.knockoutRound.get(count).size() == 1) {
                return this.knockoutRound.get(count).get(0).getWinner();
            } else if (this.knockoutRound.get(count).isEmpty()) {
                break;
            }
            count++;
        }
        return "";
    }

    public void showKnockoutTies(int round) {
        List<KnockoutTie> ties = this.knockoutRound.get(round);
        System.out.println("\nRound " + round);
        for (KnockoutTie tie : ties) {
            tie.show();
        }
    }

    public void show() {
        System.out.println(this.getName() + "   " + Sim.getTeam(this.getWinner()).getName());
    }

    public Map<Integer, List<KnockoutTie>> getKnockoutRound() {
        return this.knockoutRound;
    }
}
