import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Functions.CatalogueHelper;
import Functions.Market;
import Models.PositionHelper;
import Models.Player;
import Models.Deal;

public class TransferMarket {
    private static Map<String, String> player_list_map = new HashMap<>();
    private static Map<String, List<Player>> lists = new HashMap<>();
    private static Map<String, List<String>> key_list = new HashMap<>();

    private static Map<String, Target> targets = new HashMap<>();
    private static List<Deal> completed_transfers = new ArrayList<>();

    private static int day = 1;

    private static final int WINDOW_LENGTH = 50;

    private static Map<Integer, ArrayList<Integer>> time_data = new HashMap<>();
    private static Map<Integer, String> size_data = new HashMap<>();
    private static Map<String, Map<String, Integer>> team_stats = new HashMap<>();

    private static Map<String, String> player_club_link = new HashMap<>();
    private static Map<String, Integer> player_price_map = new HashMap<>();

    private static List<String> premium_clubs = new ArrayList<>();

    private static boolean continue_window = true;

    private static void resetSeasonVariables() {
        continue_window = true;
        targets = new HashMap<>();
        lists = new HashMap<>();
        key_list = new HashMap<>();
        player_list_map = new HashMap<>();

        player_club_link = new HashMap<>();
        player_price_map = new HashMap<>();
    }

    private static void setTargets() {
        premium_clubs = new ArrayList<>();
        Set<Integer> nat_ids = Sim.getAllNations().keySet();
        for (int x : nat_ids) {
            LeagueStructure y = Sim.getLeagueStructure(x);
            int level = 1;
            while (y.getDivisions().containsKey(level)) {
                List<String> ids = y.getDivisions().get(level).getTeams();
                for (String t : ids) {
                    Club_Team team = Sim.getClub(t);
                    Target target = new Target(team.getSeniorSquad().getWeakestPosition(),
                            team.getSeniorSquad().getBestLineupRating(), team.getBudget(), t, team.getReputation(),
                            level, x);
                    targets.put(t, target);
                    if (team.getReputation() >= Target.PREMIUM_REPUTATION_CUTOFF) {
                        premium_clubs.add(team.getId());
                    }
                    // System.out.println(team.getName() + ": " + target.getPosition() + " $"
                    //         + CommonFunctions.formatMoney(target.getPrice()) + " " + target.getRating() + "/100");
                }
                level++;
            }
        }
    }

    public static String getLeagueKey(League league) {
        return Sim.getNation(league.getNationId()).getName() + "_" + league.getCompetitionLevel();
    }

    public static Map<Integer, List<Target>> groupTargetsByNation() {
        Map<Integer, List<Target>> nat_targets = new HashMap<>();
        Set<Integer> nat_ids = Sim.getAllNations().keySet();
        for (int x : nat_ids) {
            List<Target> nats = new ArrayList<>();
            LeagueStructure y = Sim.getLeagueStructure(x);
            int level = 1;
            while (y.getDivisions().containsKey(level)) {
                List<String> ids = y.getDivisions().get(level).getTeams();
                for (String t : ids) {
                    nats.add(targets.get(t));
                }
                level++;
            }
            nat_targets.put(x, nats);
        }
        return nat_targets;
    }

    public static void updateKeyList(String desc, String key) {
        List<String> temp = new ArrayList<>();
        if (key_list.containsKey(desc)) {
            temp = key_list.get(desc);
        }
        temp.add(key);
        key_list.put(desc, temp);
    }

    private static void setupLists() {
        setupPremiumList();
        setupNationalLists();
    }

    private static void setupPremiumList() {
        String key = "PREMIUM";
        int rating_cutoff = 84;
        Map<String, Player> players = Sim.sortByRating(Sim.getAllPlayers());
        List<Player> prem_players = new ArrayList<>();
        for (Map.Entry<String, Player> entry : players.entrySet()) {
            Player player = entry.getValue();
            if (player.getRating() < rating_cutoff)
                break;
            prem_players.add(player);
            player_list_map.put(entry.getKey(), key);
        }
        prem_players = Sim.sortByRating(prem_players);
        lists.put(key, prem_players);
    }

    private static void setupNationalLists() {
        int max_size = 0;
        String max_key = "";
        Set<Integer> nat_ids = Sim.getAllNations().keySet();
        for (int x : nat_ids) {
            LeagueStructure y = Sim.getLeagueStructure(x);
            int level = 1;
            while (y.getDivisions().containsKey(level)) {
                String key = Sim.getNation(x).getName() + "_" + level;
                List<String> ids = y.getDivisions().get(level).getTeams();
                List<Player> players = new ArrayList<>();
                for (String t : ids) {
                    Club_Team team = Sim.getClub(t);
                    for (String pl : team.getSeniorPlayers()) {
                        Player player = Sim.getPlayer(pl);
                        player_club_link.put(pl, t);
                        player_price_map.put(pl, MarketHelper.getPlayerValue(player, team));
                        if (player_list_map.containsKey(pl))
                            continue;
                        players.add(player);
                        player_list_map.put(pl, key);
                    }
                }
                players = Sim.sortByRating(players);
                lists.put(key, players);
                updateKeyList(Sim.getNation(x).getName(), key);
                updateKeyList("div" + level, key);
                if (max_size < players.size()) {
                    max_size = players.size();
                    max_key = key;
                }
                level++;
            }
        }
        size_data.put(Sim.getSeasonNumber(), max_key + " size: " + max_size);
    }

    public static List<Player> getPlayersList(int nation_id) {
        Nation nat = Sim.getNation(nation_id);
        List<Player> players = new ArrayList<>();
        ArrayList<String> fav_nations = new ArrayList<>();
        fav_nations.addAll(Arrays.asList(CatalogueHelper.getFavourableNations(nat.getName())));
        if (!fav_nations.contains(nat.getName()))
            fav_nations.add(nat.getName());
        for (String fav_nation : fav_nations) {
            List<String> list_names = key_list.get(fav_nation);
            if (list_names == null)
                System.out.println("No lists found for country " + fav_nation);
            else {
                for (String list_name : list_names) {
                    List<Player> league_list = lists.get(list_name);
                    if (league_list == null)
                        System.out.println("No List found for name " + list_name);
                    players.addAll(league_list);
                }
            }
        }
        return players;
    }

    public static void makeOffers() {
        int max_size = 0;
        String max_key = "";
        Map<String, ArrayList<Offer>> offers = new HashMap<>();
        List<Player> prem = lists.get("PREMIUM");
        for (String x : premium_clubs) {
            for (Player p: prem) {
                if (p.getRating() < targets.get(x).getRating()) break;
                Target target = targets.get(x);
                if (MarketHelper.canMakeOffer(target, p, player_price_map.get(p.getId()), player_club_link.get(p.getId()))) {
                        Offer offer = new Offer(target.getClubId(), player_club_link.get(p.getId()), p.getId(), player_price_map.get(p.getId()));
                        ArrayList<Offer> list = offers.containsKey(p.getId()) ? offers.get(p.getId()) : new ArrayList<>();
                        list.add(offer);
                        offers.put(p.getId(), list);
                        targets.get(x).offerMade(p.getId());
                        break;
                    }
            }
        }
        Map<Integer, List<Target>> targets = groupTargetsByNation();
        for (Map.Entry<Integer, List<Target>> entry : targets.entrySet()) {
            int nation_id = entry.getKey();
            List<Target> league_targets = entry.getValue();
            List<Player> players = getPlayersList(nation_id);
            if (max_size < players.size()) {
                max_size = players.size();
                max_key = Sim.getNation(nation_id).getName();
            }
            int targets_met = 0;
            for (Player player : players) {
                Club_Team player_club = Sim.getClub(player_club_link.get(player.getId()));
                long asking_price = player_price_map.get(player.getId());
                for (int i = 0; i < league_targets.size(); i++) {
                    Target target = league_targets.get(i);
                    if (target.hasMadeOffer())
                        continue;
                    if (MarketHelper.canMakeOffer(target, player, asking_price, player_club.getId())) {
                        Offer offer = new Offer(target.getClubId(), player_club.getId(), player.getId(),
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
            // for (int i = 0; i < league_targets.size(); i++) {
            //     if (league_targets.get(i).hasMadeOffer()) continue;
            //     for (Player player: players) {
            //         if (player.getRating() < league_targets.get(i).getRating()) break;
            //         Target target = league_targets.get(i);
            //         if (MarketHelper.canMakeOffer(target, player, player_price_map.get(player.getId()), player_club_link.get(player.getId()))) {
            //             Offer offer = new Offer(target.getClubId(), player_club_link.get(player.getId()), player.getId(), player_price_map.get(player.getId()));
            //             ArrayList<Offer> list = offers.containsKey(player.getId()) ? offers.get(player.getId()) : new ArrayList<>();
            //             list.add(offer);
            //             offers.put(player.getId(), list);
            //             entry.getValue().get(i).offerMade(player.getId());
            //             break;
            //         }
            //     }
            // }
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
        System.out.println("Transfers completed: " + finalizedOffers.size());
        if (finalizedOffers.size() == 0) continue_window = false;
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
        Target old_t = targets.remove(buyer.getId());
        Target new_target = new Target(buyer.getSeniorSquad().getWeakestPosition(),
                buyer.getSeniorSquad().getBestLineupRating(), buyer.getBudget(), buyer.getId(), buyer.getReputation(),
                old_t.getDivLevel(), old_t.getNationId());
        targets.put(buyer.getId(), new_target);
        Deal deal = new Deal();
        deal.player_name = offer.getPlayer().getName();
        deal.seller_name = seller.getName();
        deal.buyer_name = buyer.getName();
        deal.day = day;
        deal.year = Sim.getSeasonNumber();
        deal.amount = offer.getAmount();
        deal.rating = offer.getPlayer().getRating();
        deal.position = PositionHelper.getPositionName(offer.getPlayer().getPosition());
        deal.nation = offer.getPlayer().getNationId();
        deal.seller_nation = seller.getNationId();
        deal.buyer_nation = buyer.getNationId();
        completed_transfers.add(deal);
        // System.out.println(offer.getPlayer().getName() + "(" + offer.getPlayer().getRating() + ":"
        //         + offer.getPlayer().getPosition() + ")" + " moves from " + seller.getName() + " to " + buyer.getName()
        //         + " for $" + CommonFunctions.formatMoney(offer.getAmount()));
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
        int diff = (int) ((System.nanoTime() - start) / 1000000);
        System.out.println(Sim.getSeasonNumber() + " offers, day " + day + " made time: " + diff + " ms");
    }

    public static void simulate() {
        ArrayList<Integer> time_season = new ArrayList<>();
        setStats(Sim.getSeasonNumber() + " START");
        setupMarket();
        while (day <= WINDOW_LENGTH && continue_window) {
            long start = System.nanoTime();
            simulateDay();
            day++;
            int diff = (int) ((System.nanoTime() - start) / 1000000000);
            time_season.add(diff);
        }
        time_data.put(Sim.getSeasonNumber(), time_season);
        setStats(Sim.getSeasonNumber() + " END");
    }

    public static void showTopTransfers() {
        Collections.sort(completed_transfers, (Deal a, Deal b) -> (int) (b.amount - a.amount));
        int count = 1;
        for (Deal deal : completed_transfers) {
            if (count > 30) {
                break;
            }
            String x = CommonFunctions.getPaddedString(deal.seller_name + " --> " + deal.buyer_name, 50);
            System.out.println(count + " " + deal.date() + "\t" + CommonFunctions.getPaddedString(deal.player_name, 40)
                    + "  " + x + CommonFunctions.formatMoney(deal.amount));
            count++;
        }
    }

    public static void showTransferStats() {
        for (int start = Sim.getSeasonNumber() - 1; start >= Sim.STARTING_SEASON; start--) {
            ArrayList<Integer> times = time_data.get(start);
            String size = size_data.get(start);
            System.out.print(start + " Time: ");
            for (int time : times)
                System.out.print(time + " ");
            System.out.println("Size: " + size);
        }
        showTeamStats();
    }

    private static void setStats(String key) {
        Map<String, Integer> stats = new HashMap<>();
        Set<String> c_id = Sim.getAllClubs().keySet();
        for (String c : c_id) {
            stats.put(Sim.getClub(c).getName(), Sim.getClub(c).getBestSquadRating());
        }
        team_stats.put(key, stats);
        System.out.println("TEAM STATS KEY ADDED: " + key);
    }

    public static void showTeamStats() {
        for (int i = Sim.STARTING_SEASON; i < Sim.getSeasonNumber(); i++) {
            Map<String, Integer> start = team_stats.get(i + " START");
            if (start == null) {
                System.out.println(i + " START is null");
            }
            Map<String, Integer> end = team_stats.get(i + " END");
            if (end == null) {
                System.out.println(i + " END is null");
            }
        }

        Set<String> c_id = Sim.getAllClubs().keySet();
        for (String c : c_id) {
            String name = Sim.getClub(c).getName();
            String line = CommonFunctions
                    .getPaddedString(name + "(" + Sim.getNation(Sim.getClub(c).getNationId()).getCode() + ")", 30);
            for (int i = Sim.STARTING_SEASON; i < Sim.getSeasonNumber(); i++) {
                line += " " + i + ": " + team_stats.get(i + " START").get(name) + "->"
                        + team_stats.get(i + " END").get(name);
            }
            System.out.println(line);
        }
    }

    public static Map<Integer, List<List<Deal>>> getTransfersOrder(Club_Team club) {
        Collections.sort(completed_transfers, (Deal a, Deal b) -> (int) (a.year - b.year));
        int index = 0;
        Map<Integer, List<List<Deal>>> deals = new HashMap<>();
        for (int i = Sim.STARTING_SEASON; i < Sim.getSeasonNumber(); i++) {
            List<Deal> buys = new ArrayList<>(), sells = new ArrayList<>();
            while(index < completed_transfers.size() && completed_transfers.get(index).year == i) {
                if (completed_transfers.get(index).buyer_name.equals(club.getName())) {
                    buys.add(completed_transfers.get(index));
                } else if (completed_transfers.get(index).seller_name.equals(club.getName())) {
                    sells.add(completed_transfers.get(index));
                }
                index++;
            }
            List<List<Deal>> list_deals = new ArrayList<>();
            list_deals.add(buys);
            list_deals.add(sells);
            deals.put(i, list_deals);
        }
        return deals;
    }
}
