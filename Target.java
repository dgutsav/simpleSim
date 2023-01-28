import java.util.ArrayList;
import java.util.List;

import Functions.CatalogueHelper;
import Models.*;

public class Target {
    private Position position;
    private int min_rating;
    private long max_price;
    private Club_Team club;
    private String id;
    private boolean made_offer;
    private List<String> made_offers_for;
    private int div_level;

    public static final int PREMIUM_REPUTATION_CUTOFF = 7000;

    public Target(Position position, int rating, long price, Club_Team club2) {
        this.position = position;
        this.min_rating = rating;
        this.max_price = price;
        this.club = club2;
        this.id = CommonFunctions.getRandomId();
        this.made_offer = false;
        this.made_offers_for = new ArrayList<>();
        this.div_level = Sim.getLeague(Sim.findTeamInLeague(club2)).getCompetitionLevel();
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

    public Club_Team getClub() {
        return this.club;
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
        return (this.club.getReputation() > PREMIUM_REPUTATION_CUTOFF);
    }

    public int getDivLevel() {
        return this.div_level;
    }

    public ArrayList<String> getTargetNations() {
        String nation_name = Sim.getNation(this.club.getNationId()).getName();
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