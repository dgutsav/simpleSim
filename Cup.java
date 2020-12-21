import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cup {
    private Map<Integer, List<String>> teams;
    private Map<Integer, List<Match>> matches;
    private int total_teams = 0;
    private String name = "";
    private int nation_id;
    private String id;
    private List<CompetitionHistory> competitionHistories;

    public Cup(String name, int nation_id) {
        this.teams = new HashMap<>();
        this.matches = new HashMap<>();
        this.total_teams = 0;
        this.name = name;
        this.nation_id = nation_id;
        this.competitionHistories = new ArrayList<>();
    }

    public void reset() {
        this.matches = new HashMap<>();
    }

    public String getName() {
        return this.name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public int getNationId() {
        return this.nation_id;
    }

    public void setup() {
        List<String> all_teams = Sim.getNation(this.getNationId()).getTeamsForCup();
        int size = all_teams.size();
        this.total_teams = size;
        int first_round_seeding = Cup.getRoundCount(size) + 1;
        int second_round_seeding = Cup.getRoundCount(size);
        int num_first_round_teams = 2 * (size - (int) (Math.pow(2, Cup.getRoundCount(size))));
        List<String> first_round_teams = new ArrayList<>();
        List<String> second_round_teams = new ArrayList<>();
        for (int i = 0; i < all_teams.size(); i++) {
            if (i < num_first_round_teams) {
                first_round_teams.add(all_teams.get(i));
            } else {
                second_round_teams.add(all_teams.get(i));
            }
        }
        this.teams.put(first_round_seeding, first_round_teams);
        this.teams.put(second_round_seeding, second_round_teams);
    }

    public List<Match> drawRound(List<String> teamList) {
        List<Match> ties = new ArrayList<>();
        while (!teamList.isEmpty()) {
            String team1 = teamList.remove(CommonFunctions.getRandomNumber(teamList.size()));
            String team2 = teamList.remove(CommonFunctions.getRandomNumber(teamList.size()));
            Match tie = new Match();
            tie.home_id = team1;
            tie.away_id = team2;
            tie.home_team = Sim.getTeam(team1).getName();
            tie.away_team = Sim.getTeam(team2).getName();
            ties.add(tie);
        }
        return ties;
    }

    public List<String> getTeamsForRound(int round) {
        List<String> this_round_teams = new ArrayList<>();
        if (this.teams.containsKey(round)) {
            this_round_teams.addAll(this.teams.get(round));
        }
        if (this.matches.containsKey(round + 1)) {
            List<Match> last_round_matches = this.matches.get(round + 1);
            for (Match match : last_round_matches) {
                this_round_teams.add(match.getWinner(true));
            }
        }
        return this_round_teams;
    }

    public void setMatches(int round) {
        List<Match> round_matches = this.drawRound(this.getTeamsForRound(round));
        this.matches.put(round, round_matches);
    }

    public void simulateRound(int round) {
        String round_name = Cup.getRoundName(round);
        if (round > Cup.getRoundCount(this.total_teams)) {
            round_name = "Qualifying Round";
        } else if (round > 4) {
            round_name += " " + (Cup.getRoundCount(this.total_teams) - round + 1);
        }
        System.out.println(round_name);
        List<Match> matches_to_simulate = this.matches.get(round);
        for (int i = 0; i < matches_to_simulate.size(); i++) {
            Team home_team = Sim.getTeam(matches_to_simulate.get(i).home_id);
            Team away_team = Sim.getTeam(matches_to_simulate.get(i).away_id);
            matches_to_simulate.get(i).simulate(home_team, away_team);
            matches_to_simulate.get(i).display();
        }
        this.matches.put(round, matches_to_simulate);
    }

    public void simulateCup() {
        int starting_round = 1 + Cup.getRoundCount(this.total_teams);
        if (!this.teams.containsKey(starting_round)) {
            starting_round--;
        }
        System.out.println("\n" + this.getName());
        while (starting_round > 0) {
            this.setMatches(starting_round);
            this.simulateRound(starting_round);
            starting_round--;
        }
    }

    public String getWinner() {
        Match final_match = this.matches.get(1).get(0);
        return final_match.getWinner(true);
    }

    public String getTeamAtRank(int rank) {
        Match grand_final = this.matches.get(1).get(0);
        String winner = this.getWinner();
        if (rank == 1) {
            return winner;
        } else {
            String runner_up = grand_final.home_id.equalsIgnoreCase(winner) ? grand_final.away_id : grand_final.home_id;
            if (rank == 2) {
                return runner_up;
            } else {
                return "";
            }
        }

    }

    public static int getRoundCount(int size) {
        int rounds = 1;
        int num_rounds = 0;
        while (true) {
            if (size < rounds * 2) {
                return num_rounds;
            } else {
                rounds *= 2;
                num_rounds++;
            }
        }
    }

    public void saveCompetitionHistory() {
        String team_id;
        List<TeamHistory> histories = new ArrayList<>();
        team_id = this.getTeamAtRank(1);
        histories.add(new TeamHistory(team_id, 1, Sim.getTeam(team_id).getBestSquadRating()));
        team_id = this.getTeamAtRank(2);
        histories.add(new TeamHistory(team_id, 2, Sim.getTeam(team_id).getBestSquadRating()));
        CompetitionHistory compHistory = new CompetitionHistory(Sim.getSeasonNumber(), histories);
        this.competitionHistories.add(compHistory);
    }

    public void showCompetitionHistory() {
        System.out.println(this.getName());
        for (CompetitionHistory compHistory : this.competitionHistories) {
            compHistory.show(2);
        }
    }

    public static String getRoundName(int round) {
        switch (round) {
            case 1:
                return "Final";
            case 2:
                return "Semi Final";
            case 3:
                return "Quarter Final";
            case 4:
                return "Round of 16";
            default:
                return "Preliminary Round";
        }
    }

    public static void main(String args[]) {
        System.out.println(getRoundCount(80));
        int first_round_teams = (80 - (int) (Math.pow(2, Cup.getRoundCount(80))));
        System.out.println(first_round_teams);
    }
}
