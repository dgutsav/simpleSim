import java.util.ArrayList;
import java.util.List;

public class DomesticDivisionWithPlayoff implements Division {

    public enum PlayoffType {
        PROMOTION,
        CHAMPIONSHIP
    }

    private String name;
    private int comp_level;
    private int promotion_places;
    private int relegation_places;
    private String id;
    private List<CompetitionHistory> competitionHistory;
    private int nation_id;
    private int reputation;
    private SimpleLeague league;

    // Playoff properties
    private PlayoffType playoff_type;
    private int auto_promotion_places;
    private boolean two_legged_semis;
    private String playoff_winner;
    private String playoff_runner_up;
    private List<String> playoff_qualifiers;

    public DomesticDivisionWithPlayoff(String name, int level, int promotion, int relegation, int rating, int nation_id) {
        this.name = name;
        this.comp_level = level;
        this.promotion_places = promotion;
        this.relegation_places = relegation;
        this.reputation = (rating + 10) * 2;
        this.nation_id = nation_id;
        this.competitionHistory = new ArrayList<>();
        this.league = new SimpleLeague();
        this.playoff_qualifiers = new ArrayList<>();

        // By default: if multiple promotion spots, top (promotion - 1) get auto-promoted,
        // and ranks (promotion) through (promotion + 3) compete in a 4-team promotion playoff.
        // If 1 or 0 promotion spots, default to a top-4 Championship Playoff.
        if (promotion > 1) {
            this.playoff_type = PlayoffType.PROMOTION;
            this.auto_promotion_places = promotion - 1;
        } else {
            this.playoff_type = PlayoffType.CHAMPIONSHIP;
            this.auto_promotion_places = promotion;
        }
        this.two_legged_semis = true;
    }

    public DomesticDivisionWithPlayoff(String name, int level, int promotion, int relegation, int rating, int nation_id,
                                      PlayoffType type, int auto_promotion, boolean two_legged_semis) {
        this.name = name;
        this.comp_level = level;
        this.promotion_places = promotion;
        this.relegation_places = relegation;
        this.reputation = (rating + 10) * 2;
        this.nation_id = nation_id;
        this.competitionHistory = new ArrayList<>();
        this.league = new SimpleLeague();
        this.playoff_qualifiers = new ArrayList<>();
        this.playoff_type = type;
        this.auto_promotion_places = auto_promotion;
        this.two_legged_semis = two_legged_semis;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getLevel() {
        return this.comp_level;
    }

    @Override
    public int getPromotionPlaces() {
        return this.promotion_places;
    }

    @Override
    public int getRelegationPlaces() {
        return this.relegation_places;
    }

    @Override
    public int getNation() {
        return this.nation_id;
    }

    @Override
    public int getReputation() {
        return this.reputation;
    }

    @Override
    public void setTeams(List<String> teams) {
        this.league.setTeams(teams);
    }

    @Override
    public void startSeason() {
        this.playoff_winner = null;
        this.playoff_runner_up = null;
        this.playoff_qualifiers.clear();
        this.league.setFixtures();
        this.league.setupTable();
    }

    @Override
    public void simulate() {
        // 1. Regular Season round-robin
        this.league.simulate();
        System.out.println("\n" + Sim.getSeasonNumber() + " " + this.name + " (Regular Season Final Table)");
        this.league.getTable().showLong();

        // 2. Post-Season Play-offs
        simulatePlayoffs();
    }

    private void simulatePlayoffs() {
        int total_teams = this.getTeams().size();
        if (total_teams < 4) {
            return;
        }

        playoff_qualifiers.clear();
        String seed1, seed2, seed3, seed4;

        if (this.playoff_type == PlayoffType.PROMOTION) {
            // e.g. If auto_promotion = 2, seeds are ranks 3, 4, 5, 6
            int start_rank = this.auto_promotion_places + 1;
            if (start_rank + 3 > total_teams) {
                return;
            }
            seed1 = this.league.getTeamAtRank(start_rank);
            seed2 = this.league.getTeamAtRank(start_rank + 1);
            seed3 = this.league.getTeamAtRank(start_rank + 2);
            seed4 = this.league.getTeamAtRank(start_rank + 3);
            System.out.println("\n--- " + this.name + " Promotion Play-offs ---");
            System.out.println("Automatic Promotion: ");
            for (int r = 1; r <= this.auto_promotion_places; r++) {
                String team_id = this.league.getTeamAtRank(r);
                System.out.println("  " + r + ". " + Sim.getClub(team_id).getName());
            }
            System.out.println("Play-off Participants: ");
            System.out.println("  Seed 1 (Rank " + start_rank + "): " + Sim.getClub(seed1).getName());
            System.out.println("  Seed 2 (Rank " + (start_rank + 1) + "): " + Sim.getClub(seed2).getName());
            System.out.println("  Seed 3 (Rank " + (start_rank + 2) + "): " + Sim.getClub(seed3).getName());
            System.out.println("  Seed 4 (Rank " + (start_rank + 3) + "): " + Sim.getClub(seed4).getName());
        } else {
            // Championship playoff: top 4 teams
            seed1 = this.league.getTeamAtRank(1);
            seed2 = this.league.getTeamAtRank(2);
            seed3 = this.league.getTeamAtRank(3);
            seed4 = this.league.getTeamAtRank(4);
            System.out.println("\n--- " + this.name + " Championship Play-offs ---");
            System.out.println("  Seed 1: " + Sim.getClub(seed1).getName());
            System.out.println("  Seed 2: " + Sim.getClub(seed2).getName());
            System.out.println("  Seed 3: " + Sim.getClub(seed3).getName());
            System.out.println("  Seed 4: " + Sim.getClub(seed4).getName());
        }

        playoff_qualifiers.add(seed1);
        playoff_qualifiers.add(seed2);
        playoff_qualifiers.add(seed3);
        playoff_qualifiers.add(seed4);

        // Semi-Final 1: Seed 1 vs Seed 4 (Seed 1 hosts 2nd leg if two-legged)
        // Semi-Final 2: Seed 2 vs Seed 3 (Seed 2 hosts 2nd leg if two-legged)
        String winner_sf1, winner_sf2;

        System.out.println("\n[Play-off Semi-Finals]");
        if (this.two_legged_semis) {
            TwoLeggedKnockoutTie sf1 = new TwoLeggedKnockoutTie(seed1, seed4, this.comp_level);
            sf1.simulate();
            sf1.showAggregate();
            winner_sf1 = sf1.getWinner();

            TwoLeggedKnockoutTie sf2 = new TwoLeggedKnockoutTie(seed2, seed3, this.comp_level);
            sf2.simulate();
            sf2.showAggregate();
            winner_sf2 = sf2.getWinner();
        } else {
            SingleKnockoutTie sf1 = new SingleKnockoutTie(seed1, seed4, this.comp_level);
            sf1.simulate();
            sf1.show();
            winner_sf1 = sf1.getWinner();

            SingleKnockoutTie sf2 = new SingleKnockoutTie(seed2, seed3, this.comp_level);
            sf2.simulate();
            sf2.show();
            winner_sf2 = sf2.getWinner();
        }

        // Final: Winner SF1 vs Winner SF2 (Single match at neutral venue / Wembley style)
        System.out.println("\n[Play-off Final]");
        SingleKnockoutTie playoff_final = new SingleKnockoutTie(winner_sf1, winner_sf2, this.comp_level);
        playoff_final.getMatch().isNeutral = true;
        playoff_final.simulate();
        playoff_final.show();

        this.playoff_winner = playoff_final.getWinner();
        this.playoff_runner_up = playoff_final.getLoser();

        if (this.playoff_type == PlayoffType.PROMOTION) {
            System.out.println(">>> " + Sim.getClub(this.playoff_winner).getName()
                    + " WINS THE PLAY-OFF FINAL AND IS PROMOTED! <<<\n");
        } else {
            System.out.println(">>> " + Sim.getClub(this.playoff_winner).getName()
                    + " WINS THE PLAY-OFF FINAL AND IS CROWNED CHAMPION! <<<\n");
        }
    }

    @Override
    public List<String> getTeams() {
        return this.league.getTeams();
    }

    @Override
    public boolean isTeamInLeague(String team_id) {
        return this.league.getTeams().contains(team_id);
    }

    @Override
    public List<String> getPromotedTeams() {
        List<String> promoted = new ArrayList<>();
        if (this.playoff_type == PlayoffType.PROMOTION) {
            // 1. Automatic promotion teams
            for (int i = 1; i <= this.auto_promotion_places; i++) {
                promoted.add(this.league.getTeamAtRank(i));
            }
            // 2. Playoff winner gets the final promotion spot
            if (this.playoff_winner != null && !promoted.contains(this.playoff_winner)) {
                promoted.add(this.playoff_winner);
            } else {
                // Fallback if playoffs were not simulated
                int next_rank = this.auto_promotion_places + 1;
                while (promoted.size() < this.promotion_places && next_rank <= this.getTeams().size()) {
                    String candidate = this.league.getTeamAtRank(next_rank);
                    if (!promoted.contains(candidate)) {
                        promoted.add(candidate);
                    }
                    next_rank++;
                }
            }
        } else {
            // Championship playoff or standard promotion
            for (int i = 1; i <= this.promotion_places; i++) {
                promoted.add(this.league.getTeamAtRank(i));
            }
        }
        return promoted;
    }

    @Override
    public List<String> getRelegatedTeams() {
        List<String> demoted = new ArrayList<>();
        int size = this.getTeams().size();
        for (int i = size; i > size - this.relegation_places; i--) {
            demoted.add(this.league.getTeamAtRank(i));
        }
        return demoted;
    }

    @Override
    public void saveCompetitionHistory() {
        List<TeamHistory> histories = new ArrayList<>();
        int size = this.getTeams().size();
        for (int i = 1; i <= size; i++) {
            String team_id = this.league.getTeamAtRank(i);
            int rating = (int) Sim.getClub(team_id).getBestSquadRating();
            TeamHistory history = new TeamHistory(team_id, i, rating);
            histories.add(history);
        }
        CompetitionHistory compHistory = new CompetitionHistory(Sim.getSeasonNumber(), histories);
        this.competitionHistory.add(compHistory);
    }

    @Override
    public void showCompetitionHistory() {
        System.out.println(this.getName());
        for (CompetitionHistory compHistory : this.competitionHistory) {
            compHistory.show(3, false);
        }
    }

    @Override
    public void endSeason() {
        saveCompetitionHistory();
        updateTeamReputations();
        distributePrizeMoney();
    }

    public void updateTeamReputations() {
        int reputation_steps = (this.reputation / this.getTeams().size()) * 10;
        List<String> sorted_teams = CommonFunctions.sortByReputation(this.getTeams());
        for (int i = 0; i < this.getTeams().size(); i++) {
            String team = sorted_teams.get(i);
            int rank = this.league.getRankOfTeam(team);
            int diff = rank - (i + 1);
            if (diff > -3 && diff < 3) {
                continue;
            } else if (diff > 0) {
                Sim.getClub(team).addReputation(reputation_steps * (diff - 3) * -1);
            } else {
                Sim.getClub(team).addReputation(reputation_steps * ((diff * -1) + 3));
            }
        }

        // Play-off Winner reputation boost
        if (this.playoff_winner != null) {
            Sim.getClub(this.playoff_winner).addReputation(reputation_steps * 2);
        }
    }

    public void distributePrizeMoney() {
        long prize_money = this.reputation * 50000 * (long) (Math.pow(1.04, (this.reputation - 200)));
        int size = this.getTeams().size();
        prize_money = (prize_money / 20) * size;
        float[] shares = new float[size];
        for (int i = 0; i < size / 2; i++) {
            float multiplier = ((size / 2) - i) * 0.05f;
            shares[i] = 1 + multiplier;
            shares[size - i - 1] = 1 - multiplier;
        }
        for (int i = 1; i <= size; i++) {
            double change = (double) prize_money * shares[i - 1];
            Sim.getClub(this.league.getTeamAtRank(i)).updateBudget((long) change);
        }

        // Play-off Winner extra bonus prize money (10% of base prize fund)
        if (this.playoff_winner != null) {
            long bonus = prize_money / 10;
            Sim.getClub(this.playoff_winner).updateBudget(bonus);
        }
    }

    @Override
    public String getTeamAtRank(int rank) {
        if (this.playoff_type == PlayoffType.CHAMPIONSHIP && rank == 1 && this.playoff_winner != null) {
            return this.playoff_winner;
        }
        return this.league.getTable().getTeamAtRank(rank);
    }

    public String getPlayoffWinner() {
        return this.playoff_winner;
    }

    public String getPlayoffRunnerUp() {
        return this.playoff_runner_up;
    }

    public List<String> getPlayoffQualifiers() {
        return this.playoff_qualifiers;
    }

    public PlayoffType getPlayoffType() {
        return this.playoff_type;
    }

    public int getAutoPromotionPlaces() {
        return this.auto_promotion_places;
    }

    public SimpleLeague getLeague() {
        return this.league;
    }
}
