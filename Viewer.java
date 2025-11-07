import java.util.*;

import Functions.CatalogueHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import Models.Deal;
import Models.Player;
import Models.Position;
import Models.PositionHelper;

class Viewer {
    public static void showTransferList(Map<Integer, List<List<Deal>>> deals) {
        for (int i = Sim.STARTING_SEASON; i < Sim.getSeasonNumber(); i++) {
            System.out.println("\n SEASON " + i);
            System.out.println(CommonFunctions.getPaddedString("IN", 95) + "OUT");
            int b_c = deals.get(i).get(0).size(), s_c = deals.get(i).get(1).size();
            int max = b_c > s_c ? b_c : s_c;
            long buy = 0, sell = 0;
            for (int a = 0; a < max; a++) {
                if (a < b_c) {
                    Deal d = deals.get(i).get(0).get(a);
                    String x = CommonFunctions.getPaddedString(d.player_name + " (" + d.position + "|" + d.rating + "|"
                            + Sim.getNation(d.nation).getCode() + ")", 40);
                    x += " <-- ";
                    x += CommonFunctions
                            .getPaddedString(d.seller_name + "(" + Sim.getNation(d.seller_nation).getCode() + ")", 30);
                    x += "$ " + CommonFunctions.getLeftPaddedString(CommonFunctions.formatMoney(d.amount), 11);
                    System.out.print(x + "       ");
                    buy += d.amount;
                } else {
                    System.out.print(CommonFunctions.getPaddedString(" ", 95));
                }
                if (a < s_c) {
                    Deal d = deals.get(i).get(1).get(a);
                    String x = CommonFunctions.getPaddedString(d.player_name + " (" + d.position + "|" + d.rating + "|"
                            + Sim.getNation(d.nation).getCode() + ")", 40);
                    x += " --> ";
                    x += CommonFunctions
                            .getPaddedString(d.buyer_name + "(" + Sim.getNation(d.buyer_nation).getCode() + ")", 30);
                    x += "$ " + CommonFunctions.getLeftPaddedString(CommonFunctions.formatMoney(d.amount), 11);
                    System.out.println(x);
                    sell += d.amount;
                } else {
                    System.out.println();
                }
            }
            System.out.println("\nSeason Balance: $ " + CommonFunctions.formatMoney(sell - buy));
        }
    }

    public static int getInput() {
        int r = 0;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            r = Integer.parseInt(br.readLine());
            // br.close();
        } catch (IOException e) {
        }
        return r;
    }

    public static String getText() {
        String str = " ";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            str = br.readLine();
        } catch(IOException e) {}
        return str;
    }

    public static int showContinents() {
        System.out.println("1. Europe\n2. South America\n3. North America\n4. Asia\n5. Africa");
        System.out.println("Enter Choice: ");
        return getInput();
    }

    public static Nation showNations(int c) {
        String continent = "";
        if (c == 1)
            continent = "Europe";
        else if (c == 2)
            continent = "South America";
        else if (c == 3)
            continent = "North America";
        else if (c == 4)
            continent = "Asia";
        else if (c == 5)
            continent = "Africa";
        else
            return null;
        List<Nation> x = Sim.getNationFromContinent(continent);
        int count = 1;
        for (Nation n : x) {
            System.out.print(CommonFunctions.getPaddedString(count + ". " + n.getName(), 30));
            if (count % 4 == 0)
                System.out.println();
            count++;
        }
        if (count % 4 != 0)
            System.out.println();
        System.out.println("Enter Choice: ");
        int s = getInput();
        return x.get(s - 1);
    }

    public static Division showLeagues(Nation n) {
        LeagueStructure ls = Sim.getLeagueStructure(n.getId());
        for (Map.Entry<Integer, Division> entry : ls.getDivisions().entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue().getName());
        }
        System.out.println("Enter Choice: ");
        int s = getInput();
        return ls.getDivisions().get(s);
    }

    public static Club_Team showClubs(Division div) {
        List<String> x = div.getTeams();
        int count = 1;
        for (String y : x) {
            System.out.print(CommonFunctions.getPaddedString(count + ". " + Sim.getClub(y).getName(), 30));
            if (count % 4 == 0)
                System.out.println();
            count++;
        }
        if (count % 4 != 0)
            System.out.println();
        System.out.println("Enter Choice: ");
        int p = getInput();
        return Sim.getClub(x.get(p - 1));
    }

    public static void showClub(Club_Team club) {
        System.out.println(club.getName());
        Map<String, Position> line = club.getSeniorSquad().getBestLineup();
        int form = club.getSeniorSquad().getFormationId();
        Position[] pos = CatalogueHelper.getFormation(form);
        String[] pls = new String[pos.length];
        System.out.println("Formation: " + CatalogueHelper.getFormationName(form));
        for (int i = 0; i < pos.length; i++) {
            Position x = pos[i];
            for (Map.Entry<String, Position> entry : line.entrySet()) {
                if (x == entry.getValue()) {
                    boolean flag = false;
                    for (int j = 0; j < i; j++) {
                        if (pls[j].equals(entry.getKey())) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        pls[i] = entry.getKey();
                        break;
                    }
                }
            }
        }
        for (int i = 0; i < pos.length; i++) {
            Player p = Sim.getPlayer(pls[i]);
            System.out.println(CommonFunctions.getPaddedString(PositionHelper.getPositionName(pos[i]), 3) + "| "
                    + CommonFunctions.getPaddedString(p.getName(), 30) + p.getRatingForPosition(pos[i]) + " "
                    + Sim.getNation(p.getNationId()).getCode() + " " + p.getAge());
        }
        List<String> pl = club.getSeniorPlayers();
        System.out.println("\nSUBS: ");
        List<Player> s = new ArrayList<>();
        for (String x : pl) {
            if (line.containsKey(x))
                continue;
            s.add(Sim.getPlayer(x));
        }
        if (s.size() == 0)
            return;
        s = Sim.sortByRating(s);
        int NUM_SUBS = 9;
        for (int i = 0; i < (s.size() < NUM_SUBS ? s.size() : NUM_SUBS); i++) {
            Player p = s.get(i);
            System.out
                    .println(CommonFunctions.getPaddedString(PositionHelper.getPositionName(p.getPosition()), 3) + "| "
                            + CommonFunctions.getPaddedString(p.getName(), 30) + p.getRating() + " "
                            + Sim.getNation(p.getNationId()).getCode() + " " + p.getAge());
        }
        System.out.println("RES:");
        for (int i = NUM_SUBS; i < s.size(); i++) {
            Player p = s.get(i);
            System.out
                    .println(CommonFunctions.getPaddedString(PositionHelper.getPositionName(p.getPosition()), 3) + "| "
                            + CommonFunctions.getPaddedString(p.getName(), 30) + p.getRating() + " "
                            + Sim.getNation(p.getNationId()).getCode() + " " + p.getAge());
        }
        System.out.println("\n Budget: " + CommonFunctions.formatMoney(club.getBudget()));
    }

    public static void showNationalTeam(Nation nat) {
        NationalTeam nt = nat.getNationalTeam();
        nt.callup();
        System.out.println(nat.getName() + " Rating: " + nt.getSquad().getBestLineupRating());
        Position pos[] = CatalogueHelper.getFormation(nt.getSquad().getFormationId());
        Map<String, Position> line = nt.getSquad().getBestLineup();
        String[] pls = new String[pos.length];
        for (int i = 0; i < pos.length; i++) {
            Position x = pos[i];
            for (Map.Entry<String, Position> entry : line.entrySet()) {
                if (x == entry.getValue()) {
                    boolean flag = false;
                    for (int j = 0; j < i; j++) {
                        if (pls[j].equals(entry.getKey())) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        pls[i] = entry.getKey();
                        break;
                    }
                }
            }
        }
        for (int i = 0; i < pos.length; i++) {
            Player p = Sim.getPlayer(pls[i]);
            Club_Team cl = Sim.getClub(Sim.findPlayerInClub(pls[i]));
            System.out.println(CommonFunctions.getPaddedString(PositionHelper.getPositionName(pos[i]), 3) + "| "
                    + CommonFunctions.getPaddedString(p.getName(), 30) + p.getRatingForPosition(pos[i]) + "\t"
                    + p.getAge() + "\t" + cl.getName() + "(" + Sim.getNation(cl.getNationId()).getCode() + ")");
        }
        List<String> pl = nt.getSquad().getPlayerIds();
        List<Player> s = new ArrayList<>();
        for (String x : pl) {
            if (line.containsKey(x))
                continue;
            s.add(Sim.getPlayer(x));
        }
        s = Sim.sortByRating(s);
        System.out.println("\nRES:");
        for (int i = 0; i < s.size(); i++) {
            Player p = s.get(i);
            Club_Team cl = Sim.getClub(Sim.findPlayerInClub(p.getId()));
            System.out.println(CommonFunctions.getPaddedString(PositionHelper.getPositionName(p.getPosition()), 3) + "| "
                    + CommonFunctions.getPaddedString(p.getName(), 30) + p.getRating() + "\t"
                    + p.getAge() + "\t" + cl.getName() + "(" + Sim.getNation(cl.getNationId()).getCode() + ")");
        }
    }

    public static void showLeagueRanks() {
        Map<Integer, Integer> leagues = new HashMap<>();
        Map<Integer, Integer> sizes = new HashMap<>();

        Map<Integer, Nation> nat = Sim.getAllNations();
        for(Map.Entry<Integer, Nation> entry: nat.entrySet()) {
            int x = entry.getKey();
            LeagueStructure l = Sim.getLeagueStructure(x);
            Division d = l.getDivisions().get(1);
            leagues.put(x, d.getReputation());
            sizes.put(x, d.getTeams().size());
        }
        ArrayList<Map.Entry<Integer, Integer>> list = new ArrayList<>(leagues.entrySet());
        Collections.sort(list,
                (Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) -> (int) (o2.getValue() - o1.getValue()));
        for (Map.Entry<Integer, Integer> aa : list) {
            System.out.print(CommonFunctions.getPaddedString(Sim.getNation(aa.getKey()).getName(), 20));
            long prize_money = (long)(aa.getValue() * 50000 * (double)(Math.pow(1.04, (aa.getValue() - 200)))); 
            String str = CommonFunctions.getPaddedString(Sim.getLeagueStructure(aa.getKey()).getDivisions().get(1).getName(), 40);
            str += " " + aa.getValue() + "  ";
            str += "$ " + CommonFunctions.formatMoney(prize_money);
            System.out.println(str);
        }
    }

    public static void searchPlayers() {
        System.out.println("Enter search:");
        String sch = getText();
        Map<String, Player> x = Sim.getAllPlayers();
        List<Player> players = new ArrayList<>();
        for (Map.Entry<String, Player> entry: x.entrySet()) {
            if (entry.getValue().getName().equalsIgnoreCase(sch)) players.add(entry.getValue()); 
        }
        players = Sim.sortByRating(players);
        for (int i = 0; i < players.size(); i++) {
            System.out.print(CommonFunctions.getPaddedString((i+1)+".", 5));
            System.out.print(CommonFunctions.getPaddedString(players.get(i).getName(), 25));
            System.out.print(Sim.getNation(players.get(i).getNationId()).getCode() + "  ");
            System.out.println(Sim.getClub(Sim.findPlayerInClub(players.get(i).getId())).getName());
        }
        if (players.size() == 0) return;
        System.out.println("Enter Choice:");
        int ch = getInput();
        Player player = players.get(ch - 1);
        System.out.println(player.getName());
    }

    public static void showMenu() {
        System.out.println(
                "\n1. Show Transfers\n2. Show European History\n3. Show Club Lineup\n4. Show National Team\n5. Show League Reps\n6. Search Players\n0. Exit");
        System.out.println("Enter Choice: ");
        int x = getInput();
        if (x == 0)
            return;
        else if (x == 1) {
            int cnt = showContinents();
            Nation nat = showNations(cnt);
            Division div = showLeagues(nat);
            Club_Team c = showClubs(div);
            showTransferList(TransferMarket.getTransfersOrder(c));
            showMenu();
        } else if (x == 2) {
            ControllerEurope_Swiss.showCompetitionHistory();
            showMenu();
        } else if (x == 3) {
            int cnt = showContinents();
            Nation nat = showNations(cnt);
            Division div = showLeagues(nat);
            Club_Team c = showClubs(div);
            showClub(c);
            showMenu();
        } else if (x == 4) {
            int cnt = showContinents();
            Nation nat = showNations(cnt);
            showNationalTeam(nat);
            showMenu();
        } else if (x == 5) {
            showLeagueRanks();
            showMenu();
        } else if (x == 6) {
            searchPlayers();
            showMenu();
        }
    }
}