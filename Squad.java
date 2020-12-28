import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Squad {
    private List<String> players;
    private int formation;
    private Function<Position, Player> emergency_player_promotion;
    private String form = "DDDDDD";

    public Squad() {
        this.players = new ArrayList<>();
        this.formation = CommonFunctions.getRandomNumber(Catalogues.numFormations());
        this.emergency_player_promotion = null;
        this.form = "DDDDDD";
    }

    public Squad(int formation_id) {
        this.players = new ArrayList<>();
        this.formation = formation_id;
        this.emergency_player_promotion = null;
        this.form = "DDDDDD";
    }

    public void setFormation(int id) {
        this.formation = id;
    }

    public void setEmergencyPlayerPromotionCallback(Function<Position, Player> callback) {
        this.emergency_player_promotion = callback;
    }

    public List<String> getPlayerIds() {
        return this.players;
    }

    public List<Player> getPlayers() {
        List<Player> all_players = new ArrayList<>();
        for (String player_id : this.players) {
            all_players.add(Sim.getPlayer(player_id));
        }
        return all_players;
    }

    public int getFormationId() {
        return this.formation;
    }

    public Position[] getPositions() {
        return Catalogues.getFormation(this.formation);
    }

    public void removeAllPlayers() {
        this.players.clear();
    }

    public void addPlayer(String player_id) {
        this.players.add(player_id);
    }

    public void removePlayer(String player_id) {
        this.players.remove(player_id);
    }

    public Player getBestPlayerAtPosition(Position position, List<String> ignore, boolean injuries) {
        int rating = 0;
        String player_id = "";
        for (String str : this.players) {
            Player player = Sim.getPlayer(str);
            int player_rating = player.getRatingForPosition(position);
            if (ignore.contains(str) || player_rating == 0 || (injuries && player.isInjured())) {
                continue;
            }
            if (player_rating > rating) {
                player_id = player.getId();
                rating = player_rating;
            }
        }
        return Sim.getPlayer(player_id);
    }

    public Map<String, Position> getLineup(boolean best_lineup) {
        Position[] positions = this.getPositions();
        Map<String, Position> lineup = new HashMap<>();
        List<String> already_included = new ArrayList<>();
        for (Position position : positions) {
            Player player = this.getBestPlayerAtPosition(position, already_included, !best_lineup);
            if (player == null) {
                player = this.emergency_player_promotion.apply(position);
                this.addPlayer(player.getId());
            }
            already_included.add(player.getId());
            lineup.put(player.getId(), position);
        }
        return lineup;
    }

    public int getLineupRating(boolean best_lineup) {
        int rating = 0;
        Map<String, Position> lineup = this.getLineup(best_lineup);
        for (Map.Entry<String, Position> entry : lineup.entrySet()) {
            rating += Sim.getPlayer(entry.getKey()).getRatingForPosition(entry.getValue());
        }
        return rating / 11;
    }

    public List<String> getLineupPlayers(boolean best_lineup) {
        Map<String, Position> lineup = this.getLineup(best_lineup);
        List<String> lineup_players = new ArrayList<>();
        for (Map.Entry<String, Position> entry : lineup.entrySet()) {
            lineup_players.add(entry.getKey());
        }
        return lineup_players;
    }

    public Map<String, Position> getCurrentLineup() {
        return this.getLineup(false);
    }

    public Map<String, Position> getBestLineup() {
        return this.getLineup(true);
    }

    public List<String> getCurrentLineupPlayers() {
        return this.getLineupPlayers(false);
    }

    public List<String> getBestLineupPlayers() {
        return this.getLineupPlayers(true);
    }

    public int getCurrentLineupRating() {
        return this.getLineupRating(false);
    }

    public int getBestLineupRating() {
        return this.getLineupRating(true);
    }

    public void medical() {
        for (String player_id : this.players) {
            Player player = Sim.getPlayer(player_id);
            if (player.isInjured()) {
                player.getInjury().gameMissed();
                if (player.getInjury().getGames() < 1) {
                    player.healInjury();
                }
                player.updatePlayer();
            }
        }
        List<String> current_lineup = this.getCurrentLineupPlayers();
        int physio_check = CommonFunctions.getRandomNumber(100);
        int injured_players = 0;
        if (physio_check > 99) {
            injured_players = 3;
        } else if (injured_players > 95) {
            injured_players = 2;
        } else if (injured_players > 85) {
            injured_players = 1;
        }
        for (int i = 0; i < injured_players; i++) {
            String player_id = CommonFunctions.getRandomElement(current_lineup);
            current_lineup.remove(player_id);
            Injury injury = new Injury(Injury.getInjury(Injury.injuryTypes()), 0);
            Player player = Sim.getPlayer(player_id);
            player.injurePlayer(injury);
            player.updatePlayer();
        }
    }

    public void postMatchChanges(int type) {
        this.medical();
        this.updateForm(type);
    }

    public int countPlayersAtPosition(Position pos) {
        int count = 0;
        for (String player : this.players) {
            if (Sim.getPlayer(player).getRatingForPosition(pos) != 0) {
                count++;
            }
        }
        return count;
    }

    public int countPlayers(Position pos) {
        int count = 0;
        for (String player : this.players) {
            if (Sim.getPlayer(player).getPosition() == pos) {
                count++;
            }
        }
        return count;
    }

    public float getForm() {
        float form_rating = 0.0f;
        for (int i = 0; i < form.length(); i++) {
            if (form.charAt(i) == 'W')
                form_rating += (form.length() - i) / 10.0f;
            else if (form.charAt(i) == 'L')
                form_rating -= (form.length() - i) / 5.0f;
        }
        return form_rating;
    }

    public void updateForm(int type) {
        int FORM_RESULTS_SIZE = 6;
        char result = 'D';
        if (type == 1)
            result = 'W';
        else if (type == 2)
            result = 'L';
        this.form = this.form.substring(0, FORM_RESULTS_SIZE - 1);
        this.form = result + this.form;
    }
}