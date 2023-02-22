import java.util.ArrayList;
import java.util.List;

import Functions.CatalogueHelper;
import Models.Player;
import Models.Position;
import Models.PositionHelper;

public class Club_Team {
    private Squad senior_squad;
    private Squad youth_squad;
    private String id;
    private String name;
    private int nation_id;
    private int recruitment_level;
    private boolean has_youth_squad;
    private double coefficient;
    private int reputation;
    private long budget;

    private static final int MAX_YOUTH_AGE = 19;
    private static final int MAX_SQUAD_SIZE = 40;

    public Club_Team(String name, int nation_id, int starting_reputation) {
        this.name = name;
        this.nation_id = nation_id;
        this.senior_squad = new Squad();
        this.has_youth_squad = false;
        this.senior_squad.setEmergencyPlayerPromotionCallback(pos -> {
            return this.promoteAcademyPlayer(pos);
        });
        this.coefficient = 0;
        this.recruitment_level = getStartingRecruitmentLevel(starting_reputation);
        this.reputation = starting_reputation;
        this.budget = starting_reputation * 1000 * 10 * 2;
    }

    private int getStartingRecruitmentLevel(int reputation) {
        float base = reputation / 1000.0f;
        base += CommonFunctions.getRandomInt(3);
        base = base > 20 ? 20 : base;
        return Math.round(base);
    }

    public double getCoefficient() {
        return this.coefficient;
    }

    public void update() {
        Sim.updateClub(this, this.getId());
    }

    public void setCoefficient(double new_coefficient) {
        this.coefficient = new_coefficient;
    }

    public Squad getSeniorSquad() {
        return this.senior_squad;
    }

    public Squad getYouthSquad() {
        return this.youth_squad;
    }

    public void setId(String id) {
        this.id = id;
        this.senior_squad.setUpdateCallback(x -> {
            this.update();
            return x;
        });
    }

    public String getName() {
        return this.name;
    }

    public String getPaddedName() {
        return CommonFunctions.getPaddedString(this.name, 40);
    }

    public String getId() {
        return this.id;
    }

    public int getNationId() {
        return this.nation_id;
    }

    public int getRecruitmentLevel() {
        return this.recruitment_level;
    }

    public void setEliteAcademy() {
        this.youth_squad = new Squad(this.senior_squad.getFormationId());
        this.has_youth_squad = true;
        this.recruitment_level = 20;
        this.senior_squad.setEmergencyPlayerPromotionCallback(pos -> {
            return this.promoteFromYouthSquad(pos);
        });
        this.youth_squad.setEmergencyPlayerPromotionCallback(pos -> {
            return this.promoteAcademyPlayer(pos);
        });
    }

    public List<String> getSeniorPlayers() {
        return this.senior_squad.getPlayerIds();
    }

    public List<String> getYouthPlayers() {
        return this.youth_squad.getPlayerIds();
    }

    public List<String> getAllPlayers() {
        List<String> players = this.getSeniorPlayers();
        if (this.has_youth_squad) {
            players.addAll(this.getYouthPlayers());
        }
        return players;
    }

    public int getAcademyRating() {
        float num = (float) (this.recruitment_level * Sim.getNation(this.nation_id).getYouthRating() * 100);
        float den = 200.0f * 20.0f;
        double q = num / den;
        q *= 0.75;
        return (int) (Math.round(q));
    }

    public Player promoteAcademyPlayer(Position position) {
        int rating = this.getAcademyRating() + CommonFunctions.getRandomInt(10);
        rating = Player.adjustRating(rating);
        int age = 17 + CommonFunctions.getRandomInt(2);
        String nation_name = Sim.getNation(this.nation_id).getName();
        Player player = CommonFunctions.getPlayer(nation_name, this.nation_id, rating, age, position);
        String player_id = Sim.addPlayer(player);
        player.setId(player_id);
        return player;
    }

    public Player promoteFromYouthSquad(Position position) {
        List<String> dummy = new ArrayList<>();
        if (this.has_youth_squad && this.youth_squad != null) {
            Player player = this.youth_squad.getBestPlayerAtPosition(position, dummy, true);
            this.youth_squad.removePlayer(player.getId());
            return player;
        }
        return null;
    }

    public int getBestSquadRating() {
        return this.senior_squad.getBestLineupRating();
    }

    public void seasonEndPlayerPromotions() {
        List<Position> all_positions = PositionHelper.getAllPositions();
        int formation = this.senior_squad.getFormationId();
        Position[] form_pos = CatalogueHelper.getFormation(formation);
        List<Player> promoted_players = new ArrayList<>();
        int promoted_from_youth_squad = 0;
        for (Position position : form_pos) {
            if (this.senior_squad.countPlayers(position) == 0) {
                if (this.has_youth_squad) {
                    promoted_players.add(this.promoteFromYouthSquad(position));
                    promoted_from_youth_squad++;
                } else {
                    promoted_players.add(this.promoteAcademyPlayer(position));
                }
            }
        }
        if (this.has_youth_squad) {
            for (String player_id : this.getYouthPlayers()) {
                Player player = Sim.getPlayer(player_id);
                if (player.getAge() > Club_Team.MAX_YOUTH_AGE) {
                    promoted_players.add(player);
                    promoted_from_youth_squad++;
                }
            }
        } else {
            int squad_size = this.senior_squad.getPlayerIds().size();
            int promote_players = CommonFunctions.getRandomNumber(3);
            if ((squad_size + promote_players) >= MAX_SQUAD_SIZE)
                promote_players = MAX_SQUAD_SIZE - squad_size;
            for (int i = 0; i < promote_players; i++) {
                Position position = all_positions.get(CommonFunctions.getRandomNumber(all_positions.size()));
                promoted_players.add(this.promoteAcademyPlayer(position));
            }
        }
        if (this.has_youth_squad) {
            promoted_from_youth_squad += CommonFunctions.getRandomNumber(5) + 1;
            for (int i = 0; i < promoted_from_youth_squad; i++) {
                Position position = all_positions.get(CommonFunctions.getRandomNumber(all_positions.size()));
                this.youth_squad.addPlayer(this.promoteAcademyPlayer(position).getId());
            }
            for (Position position : all_positions) {
                if (this.youth_squad.countPlayers(position) == 0) {
                    this.youth_squad.addPlayer(this.promoteAcademyPlayer(position).getId());
                }
            }
        }
        for (Player player : promoted_players) {
            this.senior_squad.addPlayer(player.getId());
        }
    }

    public int getPlayerRankAtClub(Player player) {
        if (this.senior_squad.getPlayerIds().contains(player.getId())) {
            return this.senior_squad.getPlayerRankInSquad(player);
        } else {
            return this.senior_squad.getProspectivePlayerRankInSquad(player);
        }
    }

    public int getReputation() {
        return this.reputation;
    }

    public void addReputation(int change) {
        this.reputation += change;
        this.update();
    }

    public long getBudget() {
        return this.budget;
    }

    public void updateBudget(long change) {
        this.budget += change;
        this.update();
    }

    public Target getTransferTargetDetails() {
        return new Target(this.senior_squad.getWeakestPosition(), this.senior_squad.getBestLineupRating(), this.budget,
                this);
    }
}