import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Functions.CatalogueHelper;
import Models.Player;
import Models.Deal;

public class TransferMarket {
    private static Map<String, String> player_list_map = new HashMap<>();
    private static Map<String, List<Player>> lists = new HashMap<>();
    private static Map<String, List<String>> key_list = new HashMap<>();

    private static Map<String, Target> targets = new HashMap<>();
    private static List<Deal> completed_transfers = new ArrayList<>();

    private static int day = 1;

    private static final int WINDOW_LENGTH = 3;

    public static void setTargets() {
        Map<String, Club_Team> clubs = Sim.getAllClubs();
        for (Map.Entry<String, Club_Team> entry : clubs.entrySet()) {
            Club_Team club = entry.getValue();
            targets.put(club.getId(), club.getTransferTargetDetails());
        }
    }

    public static String getLeagueKey(League league) {
        return Sim.getNation(league.getNationId()).getName() + "_" + league.getCompetitionLevel();
    }

    public static Map<League, List<Target>> groupTargets() {
        Map<League, List<Target>> league_targets = new HashMap<>();
        Map<String, League> all_leagues = Sim.getAllLeagues();
        for (Map.Entry<String, League> entry : all_leagues.entrySet()) {
            League league = entry.getValue();
            List<Target> league_t = new ArrayList<>();
            for (String club_id : league.getTeams()) {
                league_t.add(targets.get(club_id));
            }
            league_targets.put(league, league_t);
        }
        return league_targets;
    }

    public static void updateKeyList(String desc, String key) {
        List<String> temp = new ArrayList<>();
        if (key_list.containsKey(desc)) {
            temp = key_list.get(desc);
        }
        temp.add(key);
        key_list.put(desc, temp);
    }

    public static void setupLists() {
        Map<String, League> all_leagues = Sim.getAllLeagues();
        for (Map.Entry<String, League> entry : all_leagues.entrySet()) {
            League league = entry.getValue();
            String key = getLeagueKey(league);
            List<Player> players = new ArrayList<>();
            for (String club_id : league.getTeams()) {
                Club_Team club = Sim.getClub(club_id);
                for (String player_id : club.getSeniorPlayers()) {
                    players.add(Sim.getPlayer(player_id));
                    player_list_map.put(player_id, key);
                }
            }
            lists.put(key, players);
            updateKeyList(Sim.getNation(league.getNationId()).getName(), key);
            updateKeyList("div" + league.getCompetitionLevel(), key);
        }
    }

    public static List<Player> getPlayersList(League league) {
        List<Player> players = new ArrayList<>();
        String[] fav_nations = CatalogueHelper.getFavourableNations(Sim.getNation(league.getNationId()).getName());
        for (String fav_nation : fav_nations) {
            List<String> list_names = key_list.get(fav_nation);
            if (list_names == null)
                System.out.println("No lists found for country " + fav_nation);
            for (String list_name : list_names) {
                List<Player> league_list = lists.get(list_name);
                if (league_list == null)
                    System.out.println("No List found for name " + list_name);
                players.addAll(league_list);
            }
        }
        return players;
    }

    public static void makeOffers() {
        Map<String, ArrayList<Offer>> offers = new HashMap<>();
        Map<League, List<Target>> targets = groupTargets();
        for (Map.Entry<League, List<Target>> entry : targets.entrySet()) {
            long start = System.nanoTime();
            League league = entry.getKey();
            List<Target> league_targets = entry.getValue();
            List<Player> players = getPlayersList(league);
            int targets_met = 0;
            int players_searched = 0;
            for (Player player : players) {
                players_searched++;
                Club_Team player_club = Sim.getClub(Sim.findPlayerInClub(player.getId()));
                long asking_price = MarketHelper.getPlayerValue(player, player_club);
                for (int i = 0; i < league_targets.size(); i++) {
                    Target target = league_targets.get(i);
                    if (target.hasMadeOffer())
                        continue;
                    if (MarketHelper.canMakeOffer(target, player, asking_price, player_club.getId())) {
                        Offer offer = new Offer(target.getClub().getId(), player_club.getId(), player.getId(),
                                asking_price);
                        targets_met++;
                        ArrayList<Offer> pOffers = new ArrayList<>();
                        if (offers.containsKey(player.getId())) {
                            pOffers = offers.get(player.getId());
                        }
                        pOffers.add(offer);
                        offers.put(player.getId(), pOffers);
                        entry.getValue().get(i).offerMade(player.getId());
                    }
                }
                if (targets_met >= league_targets.size())
                    break;
            }
            long diff = (System.nanoTime() - start);
            double sec_diff = diff / 1000000.0;
            double search_ratio = players_searched / players.size();
            if (search_ratio > 0.6)
                System.out.println("League: " + league.getName() + " Time: " + sec_diff + "ms Targets: "
                        + league_targets.size() + " Players: " + players.size() + " Searched: " + players_searched);
        }
        ArrayList<Offer> finalizedOffers = new ArrayList<>();
        for (Map.Entry<String, ArrayList<Offer>> offer_Entry : offers.entrySet()) {
            ArrayList<Offer> poffers = offer_Entry.getValue();
            Offer selected_offer = selectFinalOffer(poffers);
            if (selected_offer != null) {
                finalizedOffers.add(selected_offer);
            }
        }
        for (Offer final_offer : finalizedOffers) {
            finalizeOffer(final_offer);
        }
        clearAllTargets();
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
        removePlayerFromList(offer.getPlayerId());
        targets.remove(buyer.getId());
        Target new_target = buyer.getTransferTargetDetails();
        targets.put(buyer.getId(), new_target);
        Deal deal = new Deal();
        deal.player_name = offer.getPlayer().getName();
        deal.seller_name = seller.getName();
        deal.buyer_name = buyer.getName();
        deal.date = day + "/" + Sim.getSeasonNumber();
        deal.amount = offer.getAmount();
        completed_transfers.add(deal);
    }

    public static void clearAllTargets() {
        for (Map.Entry<String, Target> entry : targets.entrySet()) {
            entry.getValue().clearOffer();
        }
    }

    public static void removePlayerFromList(String player_id) {
        String list = player_list_map.get(player_id);
        List<Player> players = lists.get(list);
        Player player = Sim.getPlayer(player_id);
        players.remove(player);
        lists.put(list, players);
    }

    public static void setupMarket() {
        setTargets();
        setupLists();
        day = 1;
    }

    public static void simulateDay() {
        long start = System.nanoTime();
        makeOffers();
        int diff = (int) ((System.nanoTime() - start) / 1000000000);
        System.out.println(Sim.getSeasonNumber() + " offers made time: " + diff + " seconds");
    }

    public static void simulate() {
        setupMarket();
        while (day <= WINDOW_LENGTH) {
            simulateDay();
            day++;
        }
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
}
