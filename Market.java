import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Models.Deal;
import Models.Player;

public class Market {
    public static final int MAX_DIGITS_PRICE = 3;
    public static final int WINDOW_LENGTH = 5;

    public static Map<String, Player> players;
    public static Map<String, Target> targets;
    public static List<Deal> completed_transfers;

    private static int day = 0;

    public static void initMarket() {
        completed_transfers = new ArrayList<>();
    }

    public static void initMarketForSeason() {
        targets = getInitialTargets();
        players = Sim.sortByRating(Sim.getAllPlayers());
        day = 0;
    }

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
        return Market.fixPriceToMaxDigits(base_value);
    }

    public static Map<String, Target> getInitialTargets() {
        Map<String, Target> details = new HashMap<>();
        Map<String, Club_Team> clubs = Sim.getAllClubs();
        for (Map.Entry<String, Club_Team> entry : clubs.entrySet()) {
            Target target = entry.getValue().getTransferTargetDetails();
            details.put(target.getId(), target);
        }
        return details;
    }

    public static boolean canMakeOffer(Target target, Player player, long market_value, String club_id) {
        Club_Team buying_club = target.getClub();
        if (target.getPosition() != player.getPosition()) {
            return false;
        }
        if (buying_club.getId().equalsIgnoreCase(club_id)) {
            return false;
        }
        if (target.getRating() < player.getRating()) {
            return false;
        }
        long clubs_max_valuation = (long) (Market.getPlayerValue(player, buying_club) * 1.1);
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

    public static void makeOffers() {
        int total_offers_made = 0;
        List<Offer> finalizedOffers = new ArrayList<>();
        for (Map.Entry<String, Player> entry : players.entrySet()) {
            String player_id = entry.getKey();
            Player player = entry.getValue();
            String club_id = Sim.findPlayerInClub(player.getId());
            Club_Team player_club = Sim.getClub(club_id);
            long asking_price = Market.getPlayerValue(player, player_club);
            List<Offer> poffers = new ArrayList<>();
            for (Map.Entry<String, Target> target_entry : targets.entrySet()) {
                Target target = target_entry.getValue();
                if (!target.hasMadeOffer() && canMakeOffer(target, player, asking_price, club_id)) {
                    poffers.add(new Offer(target.getClub().getId(), club_id, player_id, asking_price, target.getId()));
                    target_entry.getValue().offerMade(player_id);
                    total_offers_made++;
                }
            }
            Offer selected_offer = selectFinalOffer(poffers);
            if (selected_offer != null) {
                finalizedOffers.add(selected_offer);
            }
            if (total_offers_made >= targets.size()) {
                break;
            }
        }
        for (Offer final_offer : finalizedOffers) {
            finalizeOffer(final_offer);
        }
        for (Map.Entry<String, Target> target_entry : targets.entrySet()) {
            target_entry.getValue().clearOffer();
        }
    }

    public static Offer selectFinalOffer(List<Offer> player_offers) {
        Offer best = null;
        int best_rep = 0;
        for (Offer player_offer : player_offers) {
            if (player_offer.isRejected()) {
                continue;
            }
            if (player_offer.getBuyingClub().getReputation() > best_rep) {
                best = player_offer;
                best_rep = player_offer.getBuyingClub().getReputation();
            }
        }
        return best;
    }

    public static void finalizeOffer(Offer offer) {
        Club_Team buyer = offer.getBuyingClub();
        Club_Team seller = offer.getSellingClub();
        buyer.getSeniorSquad().addPlayer(offer.getPlayerId());
        seller.getSeniorSquad().removePlayer(offer.getPlayerId());
        buyer.updateBudget(offer.getAmount() * -1);
        seller.updateBudget(offer.getAmount());
        buyer.update();
        seller.update();
        players.remove(offer.getPlayerId());
        targets.remove(offer.getTargetId());
        Target new_target = buyer.getTransferTargetDetails();
        targets.put(new_target.getId(), new_target);
        Deal deal = new Deal();
        deal.player_name = offer.getPlayer().getName();
        deal.seller_name = seller.getName();
        deal.buyer_name = buyer.getName();
        deal.date = day + "/" + Sim.getSeasonNumber();
        deal.amount = offer.getAmount();
        completed_transfers.add(deal);
    }

    public static void simulateDay() {
        long start = System.nanoTime();
        makeOffers();
        int diff = (int) ((System.nanoTime() - start) / 1000000000);
        System.out.println(Sim.getSeasonNumber() + " offers made time " + diff);
    }

    public static void showTopTransfers() {
        Collections.sort(completed_transfers, (Deal a, Deal b) -> (int) (b.amount - a.amount));
        int count = 1;
        for (Deal deal : completed_transfers) {
            if (count > 25) {
                break;
            }
            String x = CommonFunctions.getPaddedString(deal.seller_name + " --> " + deal.buyer_name, 50);
            System.out.println(count + " " + deal.date + "\t" + CommonFunctions.getPaddedString(deal.player_name, 40)
                    + "  " + x + CommonFunctions.formatMoney(deal.amount));
            count++;
        }
    }

    public static void simulate() {
        initMarketForSeason();
        for (day = 0; day < WINDOW_LENGTH; day++) {
            simulateDay();
        }
    }
}
