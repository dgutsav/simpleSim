import java.util.ArrayList;
import java.util.List;

import Functions.CatalogueHelper;
import Models.*;

public class Target {
    private Position position;
    private int min_rating;
    private long max_price;
    //private Club_Team club;
    private String id;
    private boolean made_offer;
    private List<String> made_offers_for;
    private int div_level;
    private String club_id;
    private int club_reputation;
    private int nation_id;

    public static final int PREMIUM_REPUTATION_CUTOFF = 7000;

    public Target(Position position, int rating, long price, Club_Team club2) {
        this.position = position;
        this.min_rating = rating;
        this.max_price = price;
        this.id = CommonFunctions.getRandomId();
        this.made_offer = false;
        this.made_offers_for = new ArrayList<>();
        this.div_level = Sim.getLeague(Sim.findTeamInLeague(club2)).getCompetitionLevel();
    }

    public Target(Position pos, int rate, long price, String club_id, int rep, int level, int nation_id) {
        this.position = pos;
        this.min_rating = rate;
        this.max_price = price;
        this.club_id = club_id;
        this.club_reputation = rep;
        this.div_level = level;
        this.id = CommonFunctions.getRandomId();
        this.made_offer = false;
        this.made_offers_for = new ArrayList<>();
        this.nation_id = nation_id;
    }

    public Position getPosition() {
        return this.position;
    }

    public int getRating() {
        return this.min_rating;
    }

    public long getPrice() {
        return this.max_price;
    }

    public String getId() {
        return this.id;
    }

    public void offerMade(String player_id) {
        this.made_offers_for.add(player_id);
        this.made_offer = true;
    }

    public boolean hasMadeOffer() {
        return this.made_offer;
    }

    public List<String> getTargettedPlayers() {
        return this.made_offers_for;
    }

    public boolean hasMadeOfferForPlayer(String player_id) {
        return this.made_offers_for.contains(player_id);
    }

    public void clearOffer() {
        this.made_offer = false;
    }

    public boolean canAccessPremium() {
        return (this.club_reputation > PREMIUM_REPUTATION_CUTOFF);
    }

    public int getDivLevel() {
        return this.div_level;
    }

    public String getClubId() { return this.club_id; }

    public int getNationId() { return this.nation_id; }

    public ArrayList<String> getTargetNations() {
        String nation_name = Sim.getNation(this.nation_id).getName();
        if (this.div_level == 1) {
            String[] arrays = CatalogueHelper.getFavourableNations(nation_name);
            ArrayList<String> nations = new ArrayList<>();
            boolean has_nation = false;
            for (String nat : arrays) {
                nations.add(nat);
                if (nation_name.equalsIgnoreCase(nat))
                    has_nation = true;
            }
            if (!has_nation)
                nations.add(nation_name);
            return nations;
        } else {
            ArrayList<String> nations = new ArrayList<>();
            nations.add(nation_name);
            return nations;
        }
    }
}