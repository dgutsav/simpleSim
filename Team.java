import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

public class Team {
    private String id;
    private String name;
    private float squad_rating = 0.0f;
    private List<Injury> injuries = new ArrayList<>();
    private String form_results;
    private double elo_rating = 0;
    private double coefficient = 0.0;
    private int formation = 0;
    private int nation_id = 0;
    private List<String> players;

    // Constants
    public static final float INJURY_PROBABILITY = 5.0f;
    public static final int FORM_RESULTS_SIZE = 6;

    public Team() {
        this.name = "";
        this.squad_rating = 0.0f;
        this.injuries = new ArrayList<>();
        this.form_results = "DDDDDD";
        this.elo_rating = 0;
        this.coefficient = 0.0;
        this.formation = 0;
        this.nation_id = 0;
        this.players = new ArrayList<>();
    }

    public Team(String name, float rating, int nation) {
        this.name = name;
        this.squad_rating = rating;
        this.injuries = new ArrayList<>();
        this.form_results = "DDDDDD";
        this.elo_rating = (rating * 20);
        this.coefficient = (rating - 70) > 0 ? (rating - 70) * 3 : 0;
        this.formation = CommonFunctions.getRandomNumber(Catalogues.numFormations());
        this.nation_id = nation;
        this.players = new ArrayList<>();
    }

    public Document getTeamDocument() {
        return new Document("id", this.id).append("name", this.name).append("squad_rating", this.squad_rating);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String team_id) {
        this.id = team_id;
    }

    public String getName() {
        return this.name;
    }

    public float getSquadRating() {
        return this.getBestSquadRating();
    }

    public double getCoefficient() {
        return this.coefficient;
    }

    public void setCoefficient(double new_coefficient) {
        this.coefficient = new_coefficient;
    }

    public double getEloRating() {
        return this.elo_rating;
    }

    public void updateElo(double new_elo) {
        this.elo_rating = new_elo;
    }

    public float getForm() {
        float form = 0.0f;
        for (int i = 0; i < form_results.length(); i++) {
            if (form_results.charAt(i) == 'W')
                form += (form_results.length() - i) / 10.0f;
            else if (form_results.charAt(i) == 'L')
                form -= (form_results.length() - i) / 5.0f;
        }
        return form;
    }

    public void updateForm(int type) {
        char result = 'D';
        if (type == 1)
            result = 'W';
        else if (type == 2)
            result = 'L';
        this.form_results = this.form_results.substring(0, FORM_RESULTS_SIZE - 1);
        this.form_results = result + this.form_results;
        physio();
    }

    public void physio() {
        for (int i = injuries.size() - 1; i >= 0; i--) {
            injuries.get(i).gameMissed();
            if (injuries.get(i).getGames() < 1) {
                injuries.remove(i);
            }
        }

        double _injury = Math.random() * 100;
        if (_injury > (100 - INJURY_PROBABILITY)) {
            float effect = (float) (Math.random() * this.squad_rating * 0.1f);
            Injury injury = new Injury(Injury.getInjury(CommonFunctions.getRandomNumber(FORM_RESULTS_SIZE)), effect);
            injuries.add(injury);
        }
    }

    public float getInjuries() {
        float effect = 0.0f;
        for (Injury injury : injuries) {
            if (injury.getGames() > 0) {
                effect += injury.getEffect();
            }
        }
        return effect;
    }

    public int getFormationId() {
        return this.formation;
    }

    public Position[] getFormation() {
        return Catalogues.getFormation(this.formation);
    }

    public int getNationId() {
        return this.nation_id;
    }

    public void addPlayer(String player_id) {
        this.players.add(player_id);
    }

    public void removePlayer(String player_id) {
        this.players.remove(player_id);
    }

    public List<String> getPlayers() {
        return this.players;
    }

    public int getBestSquadRating() {
        int rating = 0;
        List<String> lineup = this.getBestLineup();
        for (String str : lineup) {
            rating += Sim.getPlayer(str).getRating();
        }
        return rating / 11;
    }

    public Player getBestPlayerAtPosition(Position position, List<String> players_to_ignore) {
        int rating = 0;
        String player_id = "";
        for (String str : this.players) {
            Player player = Sim.getPlayer(str);
            if (players_to_ignore.contains(str) || player.getPosition() != position) {
                continue;
            }
            if (player.getRating() > rating) {
                player_id = player.getId();
                rating = player.getRating();
            }
        }
        return Sim.getPlayer(player_id);
    }

    public List<String> getBestLineup() {
        Position[] positions = this.getFormation();
        List<String> lineup = new ArrayList<>();
        for (Position position : positions) {
            Player player = this.getBestPlayerAtPosition(position, lineup);
            if (player == null) {
                player = emergencyPlayer(position);
            }
            lineup.add(player.getId());
        }
        return lineup;
    }

    public Player emergencyPlayer(Position position) {
        String nation_name = Sim.getNation(this.nation_id).getName();
        int rating = Player.adjustRating((int) this.squad_rating + (int) (CommonFunctions.getRandomInt(5)));
        int age = CommonFunctions.getRandomInt(5) + 27;
        Player player = CommonFunctions.getPlayer(nation_name, nation_id, rating, age, position);
        String player_id = Sim.addPlayer(player);
        this.addPlayer(player_id);
        return player;
    }

    public List<Position> retiredPlayersCheck() {
        Position[] positions = this.getFormation();
        List<String> lineup = new ArrayList<>();
        List<Position> needed_positions = new ArrayList<>();
        for (Position position : positions) {
            Player player = this.getBestPlayerAtPosition(position, lineup);
            if (player == null) {
                needed_positions.add(position);
            } else {
                lineup.add(player.getId());
            }
        }
        return needed_positions;
    }

    public void promoteAcademyPlayers() {
        int count = 0;
        List<Position> positions = retiredPlayersCheck();
        if (this.players.size() < 23) {
            count = 23 - this.players.size();
        }
        count += CommonFunctions.getRandomNumber(3) + 1;
        int forced_positions = positions.size();
        for (int i = forced_positions; i < count; i++) {
            positions.add(PositionHelper.getPosition(CommonFunctions.getRandomNumber(12)));
        }
        int[] ratings = new int[count];
        for (int i = 0; i < count; i++) {
            ratings[i] = Player.adjustRating((int) (this.squad_rating * 0.75f) + CommonFunctions.getRandomInt(10));
        }
        String nation_name = Sim.getNation(this.nation_id).getName();
        for (int i = 0; i < ratings.length; i++) {
            Player player = CommonFunctions.getPlayer(nation_name, nation_id, ratings[i], 17, positions.get(i));
            String player_id = Sim.addPlayer(player);
            this.addPlayer(player_id);
        }
    }

    public void endSeasonChanges() {
        this.promoteAcademyPlayers();
    }
}
