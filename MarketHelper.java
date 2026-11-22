import java.util.Map;

import Models.Player;

public class MarketHelper {
    public static final int MAX_DIGITS_PRICE = 3;

    public static int fixPriceToMaxDigits(int value) {
        String amount = String.valueOf(value);
        int length = amount.length();
        length -= MAX_DIGITS_PRICE;
        int multiplier = (int) (Math.pow(10, length));
        double temp = (double) value / (double) multiplier;
        temp = Math.round(temp) * multiplier;
        return (int) temp;
    }

    public static int getPlayerValue(Player player, Club_Team club) {
        int base_value = player.getBaseValue();
        if (club == null) {
            club = Sim.getClub(Sim.findPlayerInClub(player.getId()));
        }
        int player_rank = club.getPlayerRankAtClub(player);
        if (player_rank != 1) {
            base_value *= 0.85;
        }
        double club_factor = club.getReputation() / 10000.0;
        base_value *= club_factor;
        int league_reputation = Sim.getLeagueStructure(club.getNationId()).getClubDivision(club.getId())
                .getReputation();
        double league_factor = league_reputation / 200.0;
        base_value *= league_factor;
        return fixPriceToMaxDigits(base_value);
    }

    public static boolean canMakeOffer(Target target, Player player, long market_value, String club_id) {
        Club_Team buying_club = Sim.getClub(target.getClubId());
        if (target.getPosition() != player.getPosition()) { // check position
            return false;
        }
        if (target.getRating() > player.getRating()) { // check if better rating
            return false;
        }
        if (buying_club.getId().equalsIgnoreCase(club_id)) { // check if same club
            return false;
        }
        long clubs_max_valuation = (long) (getPlayerValue(player, buying_club) * 1.1);
        log(buying_club.getName() + " interested in " + player.getName() + " of " + Sim.getClub(club_id).getName()
                + " for position " + player.getPosition() + "("
                + player.getRating()+"/"+target.getRating() + ")" + " MV: "
                + CommonFunctions.formatMoney(market_value)
                + " CV: " + CommonFunctions.formatMoney(clubs_max_valuation) + " Budget: "
                + CommonFunctions.formatMoney(target.getPrice()));
        if (clubs_max_valuation > target.getPrice()) {
            log("Asking Price too high");
            return false;
        }
        Club_Team selling_club = Sim.getClub(club_id);
        int rank = selling_club.getPlayerRankAtClub(player);
        if (rank != 1) {
            log(buying_club.getName() + " to make offer for " + player.getName() + " of " + selling_club.getName());
            return true;
        }
        int rep_diff = selling_club.getReputation() - buying_club.getReputation();
        int buy_rank = buying_club.getPlayerRankAtClub(player);
        boolean rep_difference = rep_diff < 0;
        if (buy_rank > rank && rep_difference) {
            log("Not promising project rep diff: " + rep_diff);
            return false;
        }
        
        int rep_sell = Sim.getLeagueStructure(selling_club.getNationId()).getClubDivision(selling_club.getId()).getReputation();
        int rep_buy = Sim.getLeagueStructure(buying_club.getNationId()).getClubDivision(buying_club.getId()).getReputation();
        if ((rep_sell + 10) > rep_buy && buy_rank > rank) return false;

        log(buying_club.getName() + " to make offer for " + player.getName() + " of " + selling_club.getName());
        return true;
    }

    private static void log(String str) {
        boolean DEBUG = false;
        if (DEBUG)
            System.out.println(str);
    }
}
