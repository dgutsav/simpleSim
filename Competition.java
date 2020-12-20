import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Competition {
    private Map<Integer, List<String>> teams;
    private int competition_level;

    private Map<Integer, List<KnockoutTie>> qualifiers;
    private League[] groups;
    private Map<Integer, List<KnockoutTie>> knockouts;

    private List<CompetitionHistory> competitionHistories;

    public Competition(int level) {
        this.teams = new HashMap<>();
        this.competition_level = level;
        this.reset();
        this.competitionHistories = new ArrayList<>();
    }

    public void reset() {
        this.groups = new League[this.getTotalGroups()];
        for (int i = 0; i < this.getTotalGroups(); i++) {
            this.groups[i] = new League();
        }
        this.qualifiers = new HashMap<>();
        this.knockouts = new HashMap<>();
        this.teams = new HashMap<>();
    }

    public void addTeam(int seeding, String team_to_add) {
        if (this.teams.containsKey(seeding)) {
            this.teams.get(seeding).add(team_to_add);
        } else {
            List<String> seeded_teams = new ArrayList<>();
            seeded_teams.add(team_to_add);
            this.teams.put(seeding, seeded_teams);
        }
    }

    public int getCompetitionLevel() {
        return this.competition_level;
    }

    public String getName() {
        return TournamentController.getName(this.competition_level);
    }

    public int getHighestSeed() {
        int seed = 0;
        while (this.teams.containsKey(seed)) {
            seed++;
        }
        return seed - 1;
    }

    public int getNumberOfKnockoutRounds() {
        int total_knockout_teams = TournamentController.TEAMS_PROGRESS_FROM_GROUP * this.getTotalGroups()
                + TournamentController.getCompetition(this.competition_level - 1).getTotalGroups();

        int rounds = 0;
        while (total_knockout_teams != 1) {
            total_knockout_teams /= 2;
            rounds++;
        }
        return rounds;
    }

    public List<String> getPossibleOpponents(String team, List<String> opponents, boolean is_qualifiers) {
        int nation = Sim.getTeam(team).getNationId();
        List<String> possible = new ArrayList<>();
        boolean champion = TournamentController.isDomesticChampion(team);
        for (String opponent : opponents) {
            if (nation == Sim.getTeam(opponent).getNationId()) {
                continue;
            }
            if (!is_qualifiers) {
                for (int i = 0; i < this.groups.length; i++) {
                    List<String> group = this.groups[i].getTeams();
                    if (group.contains(team) && group.contains(opponent)) {
                        continue;
                    }
                }
            } else {
                boolean opponent_champion = TournamentController.isDomesticChampion(opponent);
                if (opponent_champion != champion) {
                    continue;
                }
            }
            possible.add(opponent);
        }
        return possible;
    }

    public String drawTeam(List<String> pot, List<String> opponents, boolean is_qualifiers) {
        for (String team : pot) {
            if (getPossibleOpponents(team, opponents, is_qualifiers).size() == 1) {
                return team;
            }
        }
        return CommonFunctions.getRandomElement(pot);
    }

    public List<String> getWinnersFromRound(int round, boolean is_qualifiers) {
        List<String> winners = new ArrayList<>();
        if (is_qualifiers) {
            List<KnockoutTie> ties = this.qualifiers.get(round);
            for (KnockoutTie tie : ties) {
                winners.add(tie.getWinner());
            }
            return winners;
        }
        return winners;
    }

    public List<String> getTeamsForQualifiers(int seed) {
        List<String> quali_teams = this.teams.get(seed);
        if (seed < this.getHighestSeed()) {
            quali_teams.addAll(this.getWinnersFromRound(seed + 1, true));
        }
        return quali_teams;
    }

    public void setQualifiers(int seed) {
        List<KnockoutTie> ties = new ArrayList<>();
        List<List<String>> pots = TournamentController.divideIntoSeededPots(this.getTeamsForQualifiers(seed));
        while (!pots.get(1).isEmpty()) {
            String drawn_team = this.drawTeam(pots.get(1), pots.get(0), true);
            List<String> possible_opponents = this.getPossibleOpponents(drawn_team, pots.get(0), true);
            if (possible_opponents.isEmpty()) {
                this.setQualifiers(seed);
                return;
            }
            String opponent = CommonFunctions.getRandomElement(possible_opponents);
            pots.get(0).remove(opponent);
            pots.get(1).remove(drawn_team);
            KnockoutTie tie = new KnockoutTie(drawn_team, opponent, this.competition_level);
            ties.add(tie);
        }
        qualifiers.put(seed, ties);
    }

    public void simulateRound(int round, boolean is_qualifiers) {
        if (is_qualifiers) {
            System.out.println(this.getName() + " Qualifying Round " + round);
            List<KnockoutTie> ties = qualifiers.get(round);
            for (int i = 0; i < ties.size(); i++) {
                ties.get(i).simulate();
                ties.get(i).show();
                System.out.println(Sim.getTeam(ties.get(i).getWinner()).getName());
            }
            qualifiers.put(round, ties);
        }
    }

    public void simulateQualifiers() {
        int current_seed = this.getHighestSeed();
        while (current_seed > 0) {
            this.setQualifiers(current_seed);
            this.simulateRound(current_seed, true);
            current_seed--;
        }
    }

    public List<String> getTeamsForGroup() {
        if (competition_level == 2) {
            List<String> actual_teams = this.getTeamsForQualifiers(0);
            List<String> unqualified = TournamentController.getCompetition(1).getUnqualifiedTeams();
            actual_teams.addAll(unqualified);
            return actual_teams;
        }
        return this.getTeamsForQualifiers(0);
    }

    public int getTotalGroups() {
        return TournamentController.getNumGroups(this.competition_level);
    }

    public String[][] getGroupPots() {
        List<String> group_teams = TournamentController.sortByCoefficients(this.getTeamsForGroup());
        String[][] pots = new String[TournamentController.TEAMS_PER_GROUP][this.getTotalGroups()];
        for (int i = 0; i < TournamentController.TEAMS_PER_GROUP; i++) {
            for (int j = 0; j < this.getTotalGroups(); j++) {
                pots[i][j] = group_teams.get(0);
                group_teams.remove(pots[i][j]);
                pots[i][j] = TournamentController.getCodeForTeam(pots[i][j]);
            }
        }
        return pots;
    }

    public List<Match> setCompetitionLevelMatches(List<Match> matches) {
        List<Match> competiion_matches = new ArrayList<>();
        for (Match match : matches) {
            match.setTournamentLevel(this.competition_level);
            competiion_matches.add(match);
        }
        return competiion_matches;
    }

    public void setupGroups() {
        TournamentController.setGroups(this.competition_level, this.getGroupPots());
        String[][] group_matrix = TournamentController.getGroups(this.competition_level);
        for (int i = 0; i < this.getTotalGroups(); i++) {
            for (int j = 0; j < TournamentController.TEAMS_PER_GROUP; j++) {
                group_matrix[i][j] = TournamentController.getTeamForCode(group_matrix[i][j]);
            }
        }
        for (int i = 0; i < TournamentController.getNumGroups(competition_level); i++) {
            for (String team : group_matrix[i]) {
                this.groups[i].addTeam(team);
            }
            this.groups[i].setFixtures(this
                    .setCompetitionLevelMatches(FixtureScheduler.scheduleFixtures(this.groups[i].getTeams(), false)));
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
        for (int i = 0; i < this.getTotalGroups(); i++) {
            simulateGroup(i);
        }
    }

    public List<String> getTeamsAtRank(int rank) {
        List<String> ranks = new ArrayList<>();
        for (int i = 0; i < this.getTotalGroups(); i++) {
            ranks.add(this.groups[i].getTeamAtRank(rank));
        }
        return ranks;
    }

    public String[] getTransferredTeams() {
        String[] transferred = new String[this.getTotalGroups()];
        TeamPerformance[] performances = new TeamPerformance[this.getTotalGroups()];
        for (int i = 0; i < this.getTotalGroups(); i++) {
            performances[i] = this.groups[i].getTable().getTeamPerformance(
                    this.groups[i].getTeamAtRank(TournamentController.TEAMS_PROGRESS_FROM_GROUP + 1));
        }
        LeagueTable table = new LeagueTable(this.getTotalGroups());
        table.performances = performances;
        table.sortTable();
        for (int i = 1; i <= this.getTotalGroups(); i++) {
            transferred[i - 1] = table.getTeamAtRank(i);
        }
        return transferred;
    }

    public List<List<String>> getInitialKnockoutPots() {
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

    public void setInitialKnockoutTies() {
        List<List<String>> pots = this.getInitialKnockoutPots();
        List<String> seeded = pots.get(0);
        List<String> unseeded = pots.get(1);
        List<KnockoutTie> ties = new ArrayList<>();
        while (!seeded.isEmpty()) {
            String drawn_team = this.drawTeam(seeded, unseeded, false);
            List<String> possible_opponents = this.getPossibleOpponents(drawn_team, unseeded, false);
            if (possible_opponents.isEmpty()) {
                this.setInitialKnockoutTies();
                return;
            }
            String opponent_team = possible_opponents.get(CommonFunctions.getRandomNumber(possible_opponents.size()));
            seeded.remove(drawn_team);
            unseeded.remove(opponent_team);
            KnockoutTie tie = new KnockoutTie(opponent_team, drawn_team, this.competition_level);
            ties.add(tie);
        }
        this.knockouts.put(1, ties);
    }

    public List<String> simulateKnockoutRound(int round) {
        List<KnockoutTie> ties = this.knockouts.get(round);
        List<String> progressed_teams = new ArrayList<>();
        for (int i = 0; i < ties.size(); i++) {
            ties.get(i).simulate();
            progressed_teams.add(ties.get(i).getWinner());
        }
        this.knockouts.put(round, ties);
        return progressed_teams;
    }

    public List<KnockoutTie> drawRound(List<String> teamList) {
        List<KnockoutTie> ties = new ArrayList<>();
        while (!teamList.isEmpty()) {
            String team1 = teamList.remove(CommonFunctions.getRandomNumber(teamList.size()));
            String team2 = teamList.remove(CommonFunctions.getRandomNumber(teamList.size()));
            KnockoutTie tie = new KnockoutTie(team2, team1, this.competition_level);
            ties.add(tie);
        }
        return ties;
    }

    public void simulateKnockoutRounds() {
        int current_round = 1;
        if (this.knockouts.get(current_round).isEmpty()) {
            System.out.println("No matches found");
        }
        while (!this.knockouts.get(current_round).isEmpty()) {
            List<String> next_teams = this.simulateKnockoutRound(current_round);
            showKnockoutTies(current_round);
            if (next_teams.size() <= 1) {
                break;
            }
            List<KnockoutTie> next_ties = drawRound(next_teams);
            current_round++;
            this.knockouts.put(current_round, next_ties);
        }
        this.show();
    }

    public String getWinner() {
        int count = 1;
        while (true) {
            if (this.knockouts.get(count).size() == 1) {
                return this.knockouts.get(count).get(0).getWinner();
            } else if (this.knockouts.get(count).isEmpty()) {
                break;
            }
            count++;
        }
        return "";
    }

    public void showKnockoutTies(int round) {
        List<KnockoutTie> ties = this.knockouts.get(round);
        System.out.println("\nRound " + round);
        for (KnockoutTie tie : ties) {
            tie.show();
        }
    }

    public void show() {
        System.out.println(this.getName() + "   " + Sim.getTeam(this.getWinner()).getName());
    }

    public Map<Integer, List<KnockoutTie>> getKnockoutRound() {
        return this.knockouts;
    }

    public void saveCompetitionHistory() {
        String team_id;
        List<TeamHistory> histories = new ArrayList<>();
        team_id = this.knockouts.get(this.getNumberOfKnockoutRounds()).get(0).getWinner();
        histories.add(new TeamHistory(team_id, 1, Sim.getTeam(team_id).getBestSquadRating()));
        team_id = this.knockouts.get(this.getNumberOfKnockoutRounds()).get(0).getLoser();
        histories.add(new TeamHistory(team_id, 2, Sim.getTeam(team_id).getBestSquadRating()));
        team_id = this.knockouts.get(this.getNumberOfKnockoutRounds() - 1).get(0).getLoser();
        histories.add(new TeamHistory(team_id, 3, Sim.getTeam(team_id).getBestSquadRating()));
        team_id = this.knockouts.get(this.getNumberOfKnockoutRounds() - 1).get(1).getLoser();
        histories.add(new TeamHistory(team_id, 4, Sim.getTeam(team_id).getBestSquadRating()));
        CompetitionHistory compHistory = new CompetitionHistory(Sim.getSeasonNumber(), histories);
        this.competitionHistories.add(compHistory);
    }

    public void showCompetitionHistory() {
        System.out.println(this.getName());
        for (CompetitionHistory compHistory : this.competitionHistories) {
            compHistory.show(4);
        }
    }

    public List<String> getUnqualifiedTeams() {
        List<String> losers = new ArrayList<>();
        int max_seed = this.getHighestSeed();
        while (max_seed > 0) {
            List<KnockoutTie> ties = this.qualifiers.get(max_seed);
            for (KnockoutTie tie : ties) {
                losers.add(tie.getLoser());
            }
            max_seed--;
        }
        return losers;
    }
}
