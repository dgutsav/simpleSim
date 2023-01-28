import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Models.Deal;
import Models.Player;
import Models.Position;

public class MarketHelper {
    public static final int MAX_DIGITS_PRICE = 3;
    public static final int WINDOW_LENGTH = 5;

    public static Map<String, Player> players;
    public static Map<String, Target> targets;
    public static List<Deal> completed_transfers = new ArrayList<>();
    public static ArrayList<String> transferred_players;

    public static Map<String, Map<String, Player>> player_lists = new HashMap<>();
    public static Map<String, ArrayList<String>> list_keys = new HashMap<>();
    public static Map<Position, ArrayList<String>> position_keys = new HashMap<>();
    public static Map<Integer, ArrayList<String>> rating_keys = new HashMap<>();
    public static Map<Integer, ArrayList<String>> div_keys = new HashMap<>();
    public static Map<String, ArrayList<String>> nation_keys = new HashMap<>();

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
        double league_factor = Sim.getLeague(Sim.findTeamInLeague(club)).getReputation() / 200.0;
        base_value *= league_factor;
        return fixPriceToMaxDigits(base_value);
    }

    public static boolean canMakeOffer(Target target, Player player, long market_value, String club_id) {
        Club_Team buying_club = target.getClub();
        if (target.getPosition() != player.getPosition()) { // check position
            return false;
        }
        if (buying_club.getId().equalsIgnoreCase(club_id)) { // check if same club
            return false;
        }
        if (target.getRating() < player.getRating()) { // check if better rating
            return false;
        }
        long clubs_max_valuation = (long) (getPlayerValue(player, buying_club) * 1.1);
        if (clubs_max_valuation < market_value || market_value > target.getPrice()) {
            return false;
        }
        Club_Team selling_club = Sim.getClub(club_id);
        int rank = selling_club.getPlayerRankAtClub(player);
        if (rank != 1) {
            return true;
        }
        int rep_diff = selling_club.getReputation() - buying_club.getReputation();
        int abs_diff = (int) (Math.abs(rep_diff));
        if (Sim.findTeamInLeague(buying_club).equalsIgnoreCase(Sim.findTeamInLeague(selling_club))) {
            if (abs_diff <= 200) {
                return false;
            }
        } else {
            if (abs_diff <= 100) {
                return false;
            }
        }
        if (buying_club.getPlayerRankAtClub(player) > rank && rep_diff < 500) {
            return false;
        }
        return true;
    }
}
