import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class NationalTeam {
    private int nation_id;
    private Squad squad;
    private Map<Position, List<Player>> pool;

    public NationalTeam(int id) {
        this.nation_id = id;
        this.squad = new Squad();
        this.pool = new EnumMap<>(Position.class);
    }

    public static int getPoolRequirements(Position pos) {
        switch (pos) {
            case GOALKEEPER:
            case ATTACKING_MIDFIELDER:
                return 5;
            case RIGHT_BACK:
            case LEFT_BACK:
            case RIGHT_MIDFIELDER:
            case LEFT_MIDFIELDER:
            case DEFENSIVE_MIDFIELDER:
            case RIGHT_WINGER:
            case LEFT_WINGER:
                return 4;
            case CENTRAL_MIDFIELDER:
            case STRIKER:
                return 7;
            case CENTRE_BACK:
                return 8;
            default:
                return 0;
        }
    }

    public boolean isPositionFilledInPool(Position position) {
        return this.pool.containsKey(position)
                && this.pool.get(position).size() >= NationalTeam.getPoolRequirements(position);
    }

    public boolean isPoolFilled() {
        List<Position> all_positions = PositionHelper.getAllPositions();
        for (Position pos : all_positions) {
            if (!isPositionFilledInPool(pos)) {
                return false;
            }
        }
        return true;
    }

    public void addToPool(Position position, Player player) {
        if (this.pool.containsKey(position)) {
            this.pool.get(position).add(player);
        } else {
            List<Player> players = new ArrayList<>();
            players.add(player);
            this.pool.put(position, players);
        }
    }

    public void fillPool() {
        List<Player> all_players = Sim.getAllPlayersFromNation(this.nation_id);
        Collections.sort(all_players, (Player a, Player b) -> (b.getRating() - a.getRating()));
        for (Player player : all_players) {
            if (!this.isPositionFilledInPool(player.getPosition())) {
                this.addToPool(player.getPosition(), player);
            }
            if (this.isPoolFilled()) {
                break;
            }
        }
    }

    public void fillSquad() {
        for (Map.Entry<Position, List<Player>> entry : this.pool.entrySet()) {
            this.squad.addPlayer(entry.getValue().get(0).getId());
            this.squad.addPlayer(entry.getValue().get(1).getId());
            this.squad.addPlayer(entry.getValue().get(2).getId());
            this.squad.addPlayer(entry.getValue().get(3).getId());
        }
    }

    public void setBestFormation() {
        int best_rating = 0;
        int best_id = 0;
        for (int i = 0; i < Catalogues.numFormations(); i++) {
            this.squad.setFormation(i);
            int rating = this.squad.getBestLineupRating();
            if (rating > best_rating) {
                best_rating = rating;
                best_id = i;
            }
        }
        this.squad.setFormation(best_id);
    }

    public void showBestLineup() {
        System.out.println("Rating " + this.squad.getBestLineupRating());
        Map<String, Position> lineup = this.squad.getBestLineup();
        for (Map.Entry<String, Position> entry : lineup.entrySet()) {
            Player player = Sim.getPlayer(entry.getKey());
            System.out.println(PositionHelper.getPositionName(entry.getValue()) + " " + player.getName() + "\t"
                    + player.getRatingForPosition(entry.getValue()));
        }
    }

    public void callup() {
        this.fillPool();
        this.fillSquad();
        this.setBestFormation();
        this.showBestLineup();
    }
}
