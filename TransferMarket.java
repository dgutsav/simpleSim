import java.util.ArrayList;
import java.util.Arrays;
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

    private static Map<Integer, ArrayList<Integer>> time_data = new HashMap<>();
    private static Map<Integer, String> size_data = new HashMap<>();

    private static void resetSeasonVariables() {
        targets = new HashMap<>();
        lists = new HashMap<>();
        key_list = new HashMap<>();
        player_list_map = new HashMap<>();
    }

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
        int max_size = 0;
        String max_key = "";
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
            if (max_size < players.size()) {
                max_size = players.size();
                max_key = key;
            }
        }
        size_data.put(Sim.getSeasonNumber(), max_key + " size: " + max_size);
    }

    public static List<Player> getPlayersList(League league) {
        List<Player> players = new ArrayList<>();
        String nation_name = Sim.getNation(league.getNationId()).getName();
        String[] nations_list = CatalogueHelper.getFavourableNations(nation_name);
        ArrayList<String> fav_nations = new ArrayList<>();
        fav_nations.addAll(Arrays.asList(nations_list));
        if (league.getCompetitionLevel() != 1) {
            fav_nations = new ArrayList<>();
        }
        fav_nations.add(nation_name);
        //List<String> first_div_lists = key_list.get("div1");
        for (String fav_nation : fav_nations) {
            List<String> list_names = key_list.get(fav_nation);
            if (list_names == null)
                System.out.println("No lists found for country " + fav_nation);
            for (String list_name : list_names) {
                //if (!fav_nation.equalsIgnoreCase(nation_name) && !first_div_lists.contains(list_name)) continue;
                List<Player> league_list = lists.get(list_name);
                if (league_list == null)
                    System.out.println("No List found for name " + list_name);
                players.addAll(league_list);
            }
        }
        return players;
    }

    public static void makeOffers() {
        int max_size = 0;
        String max_key = "";
        Map<String, ArrayList<Offer>> offers = new HashMap<>();
        Map<League, List<Target>> targets = groupTargets();
        for (Map.Entry<League, List<Target>> entry : targets.entrySet()) {
            long start = System.nanoTime();
            League league = entry.getKey();
            List<Target> league_targets = entry.getValue();
            List<Player> players = getPlayersList(league);
            if (max_size < players.size()) {
                max_size = players.size();
                max_key = getLeagueKey(league);
            }
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
            double search_ratio = players_searched / (double) players.size();
            if (search_ratio > 0.5)
                System.out.println("League: " + league.getName() + " Time: " + sec_diff + "ms Targets: "
                        + league_targets.size() + " Players: " + players.size() + " Searched: " + players_searched
                        + " Ratio: " + search_ratio);
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
        String size = size_data.get(Sim.getSeasonNumber());
        if (size != null) {
            size += " Largest Player List: " + max_key + "(" + max_size + ")";
        }
        size_data.put(Sim.getSeasonNumber(), size);
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
        resetSeasonVariables();
        setTargets();
        setupLists();
        day = 1;
    }

    public static void simulateDay() {
        long start = System.nanoTime();
        makeOffers();
        int diff = (int) ((System.nanoTime() - start) / 1000000000);
        System.out.println(Sim.getSeasonNumber() + " offers, day " + day + " made time: " + diff + " seconds");
    }

    public static void simulate() {
        ArrayList<Integer> time_season = new ArrayList<>();
        setupMarket();
        while (day <= WINDOW_LENGTH) {
            long start = System.nanoTime();
            simulateDay();
            day++;
            int diff = (int) ((System.nanoTime() - start) / 1000000000);
            time_season.add(diff);
        }
        time_data.put(Sim.getSeasonNumber(), time_season);
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

    public static void showTransferStats() {
        for (int start = Sim.getSeasonNumber() - 1; start >= 2020; start--) {
            ArrayList<Integer> times = time_data.get(start);
            String size = size_data.get(start);
            System.out.print(start + " Time: ");
            for (int time : times)
                System.out.print(time + " ");
            System.out.println("Size: " + size);
        }
    }
}
