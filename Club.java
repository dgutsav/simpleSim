import java.util.ArrayList;
import java.util.List;

public class Club {
    private Squad senior_squad;
    private Squad youth_squad;
    private String id;
    private String name;
    private int nation_id;
    private int recruitment_level;
    private boolean has_youth_squad;
    private double coefficient;

    private static final int MAX_YOUTH_AGE = 19;

    public Club(String name, int nation_id) {
        this.name = name;
        this.nation_id = nation_id;
        this.senior_squad = new Squad();
        this.has_youth_squad = false;
        this.senior_squad.setEmergencyPlayerPromotionCallback(pos -> {
            return this.promoteAcademyPlayer(pos);
        });
        this.coefficient = 0;
        this.recruitment_level = 15 + CommonFunctions.getRandomInt(3);
    }

    public double getCoefficient() {
        return this.coefficient;
    }

    public void update() {
        Sim.updateTeam(this, this.getId());
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
        q *= 0.839;
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
        List<Player> promoted_players = new ArrayList<>();
        int promoted_from_youth_squad = 0;
        for (Position position : all_positions) {
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
                if (player.getAge() > Club.MAX_YOUTH_AGE) {
                    promoted_players.add(player);
                    promoted_from_youth_squad++;
                }
            }
        } else {
            int promote_players = CommonFunctions.getRandomNumber(5) + 5;
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
}