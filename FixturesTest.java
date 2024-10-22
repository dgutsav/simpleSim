import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

class FixturesTest {
    private String[][] pots;
    private Map<String, Integer> teams;
    private int NUM_POTS;
    private int NUM_TEAMS = 36;
    private int MATCHES_PER_POT;
    private int MAX_TEAMS_PER_NATION = 2;

    private Map<String, List<String[]>> pairmap;
    private Map<Integer, List<String[]>> matchdayMap;
    private List<String[]> matches;
    private Map<String, List<Integer>> nation_limits;
    private Map<String, List<String>> opp_list;
    private List<String> dummies_set = new ArrayList<>();

    public void initCL(String[][] p, Map<String, Integer> m) {
        pots = p;
        teams = m;
        NUM_POTS = 4;
        MATCHES_PER_POT = 2;
    }

    public void initConfLeague(String[][] p, Map<String, Integer> m) {
        pots = p;
        teams = m;
        NUM_POTS = 6;
        MATCHES_PER_POT = 1;
    }

    private void initPairMap() {
        pairmap = new HashMap<>();
        for (int i = 1; i <= NUM_POTS; i++) {
            for (int j = i; j <= NUM_POTS; j++)
                pairmap.put(getMapKey(i, j), new ArrayList<>());
        }
    }

    private void initNationLimits() {
        nation_limits = new HashMap<>();
        opp_list = new HashMap<>();
        List<String> all_teams = getAllTeams();
        for (String t : all_teams) {
            nation_limits.put(t, new ArrayList<>());
            opp_list.put(t, new ArrayList<>());
        }
    }

    public List<Match> getFixtures() {
        int count = 0;
        do {
            dummies_set = new ArrayList<>();
            if (count > 15) {
                showPots();
                System.out.println("MAX COUNT REACHED CL/EL FIXTURES");
                return null;
            }
            count++;
            initPairMap();
            initNationLimits();
            try {
                setCrossPots();
            } catch (Exception e) {
                System.out.println("SET CROSS POTS ERROR");
                continue;
            }
            if (selectDummyArrangement()) {
                orderPairs();
                schedule();
                if (matchdayMap.keySet().size() == ((MATCHES_PER_POT * NUM_POTS)))
                    break;
            } else {
                System.out.println("DUMMY ARRANGEMENT ERROR");
            }
        } while (true);
        return getMatchesFromMap();
    }

    public List<Match> getFixturesConf() {
        initPairMap();
        initNationLimits();
        setCrossPots();
        setSamePotsConf();
        scheduleConf();
        return getMatchesFromMap();
    }

    // #region HELPERS
    private String getMapKey(int a, int b) {
        return String.valueOf((a * 10) + b);
    }

    private List<String> getAllTeams() {
        List<String> t = new ArrayList<>();
        for (int i = 0; i < NUM_POTS; i++) {
            for (int j = 0; j < NUM_TEAMS / NUM_POTS; j++) {
                t.add(pots[i][j]);
            }
        }
        return t;
    }

    private List<String> getPotTeams(int pot) {
        List<String> p = Arrays.asList(pots[pot - 1]);
        return p;
    }

    private int getPot(String team) {
        for (int i = 0; i < NUM_POTS; i++) {
            for (int j = 0; j < NUM_TEAMS / NUM_POTS; j++) {
                if (pots[i][j].equals(team))
                    return i + 1;
            }
        }
        return 0;
    }

    private int getNation(String team) {
        return teams.get(team);
    }

    private void updateNationLimits(String team) {
        List<String> opps = opp_list.get(team);
        int MAX = MAX_TEAMS_PER_NATION;
        List<Integer> nats = new ArrayList<>();
        List<Integer> freq = new ArrayList<>();
        for (String o : opps) {
            int n = teams.get(o);
            if (!nats.contains(n)) {
                nats.add(n);
                freq.add(1);
            } else {
                int index = nats.indexOf(n);
                int count = freq.get(index);
                freq.set(index, count + 1);
            }
        }
        List<Integer> bans = new ArrayList<>();
        for (int i = 0; i < freq.size(); i++) {
            if (freq.get(i) >= MAX) {
                bans.add(nats.get(i));
            }
        }
        nation_limits.put(team, bans);
    }

    private void updateOppList(String team, String opponent) {
        opp_list.get(team).add(opponent);
    }

    private void updateClubLimits(String team1, String team2) {
        updateOppList(team1, team2);
        updateOppList(team2, team1);
        updateNationLimits(team1);
        updateNationLimits(team2);
    }

    private void addPair(String[] pair) {
        int pot1 = getPot(pair[0]);
        if (pot1 == 0)
            return;

        int pot2 = getPot(pair[1]);
        if (pot2 == 0)
            return;

        int temp;
        if (pot2 < pot1) {
            temp = pot1;
            pot1 = pot2;
            pot2 = temp;
        }
        pairmap.get(getMapKey(pot1, pot2)).add(pair);
        updateClubLimits(pair[0], pair[1]);
    }

    private void addPairs(List<String[]> pairs) {
        for (String[] pair : pairs)
            addPair(pair);
    }

    private boolean canFace(String team, String opp) {
        if (team.equals(opp))
            return false;
        else if (teams.get(team) == teams.get(opp))
            return false;
        else if (nation_limits.get(team).contains(teams.get(opp)))
            return false;
        else if (nation_limits.get(opp).contains(teams.get(team)))
            return false;
        else if (opp_list.get(team).contains(opp))
            return false;
        else if (opp_list.get(opp).contains(team))
            return false;
        else
            return true;
    }

    private List<String> getPossibleOpponents(String team, List<String> opps) {
        List<String> opponents = new ArrayList<>();
        for (String opp : opps) {
            if (canFace(team, opp))
                opponents.add(opp);
        }
        return opponents;
    }

    private List<String> removeElement(String remove, List<String> list) {
        List<String> filtered = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).equals(remove)) {
                filtered.add(list.get(i));
            }
        }
        return filtered;
    }

    private List<Match> getMatchesFromMap() {
        List<Match> matches = new ArrayList<>();
        for (int i = 1; i <= (NUM_POTS * MATCHES_PER_POT); i++) {
            for (String[] m : matchdayMap.get(i)) {
                Match match = new Match();
                match.home_id = m[0];
                match.away_id = m[1];
                match.matchday = i;
                match.home_team = Sim.getClub(m[0]).getName();
                match.away_team = Sim.getClub(m[1]).getName();
                matches.add(match);
            }
        }
        return matches;
    }
    // #endregion HELPERS

    // #region CROSS POTS
    private String[] drawCrossPot(List<String> pot1, List<String> pot2) {
        String team = null;
        List<String> options = new ArrayList<>();
        int count = Integer.MAX_VALUE;
        for (String p1 : pot1) {
            List<String> o = getPossibleOpponents(p1, pot2);
            //System.out.println(p1 + ": " + o + " " + o.size());
            if (o.size() < count) {
                team = p1;
                options = o;
                count = o.size();
            }
        }

        for (String p2 : pot2) {
            List<String> o = getPossibleOpponents(p2, pot1);
            //System.out.println(p2 + ": " + o + " " + o.size());
            if (o.size() < count) {
                team = p2;
                options = o;
                count = o.size();
            }
        }

        String opp = CommonFunctions.getRandomElement(options);
        return new String[] { team, opp };
    }

    private void drawPots(List<String> pot1, List<String> pot2) {
        int size = pot1.size();
        for (int j = 0; j < size; j++) {
            //System.out.println("MATCH: " + (j + 1) + " LEFT: " + pot1.size() + "/" + pot2.size());
            String[] pair = drawCrossPot(pot1, pot2);
            //System.out.println(pair[0] + " v " + pair[1]);
            if (pot1.contains(pair[0])) {
                pot1 = removeElement(pair[0], pot1);
            } else {
                pot2 = removeElement(pair[0], pot2);
            }
            if (pot1.contains(pair[1])) {
                pot1 = removeElement(pair[1], pot1);
            } else {
                pot2 = removeElement(pair[1], pot2);
            }
            addPair(pair);
        }
    }

    private void setCrossPot(int p1, int p2) {
        for (int i = 0; i < MATCHES_PER_POT; i++) {
            List<String> pot1 = getPotTeams(p1);
            List<String> pot2 = getPotTeams(p2);
            drawPots(pot1, pot2);
        }
    }

    private void setCrossPots() {
        for (int i = 1; i <= NUM_POTS; i++) {
            for (int j = i + 1; j <= NUM_POTS; j++) {
                setCrossPot(i, j);
            }
        }
    }
    // #endregion CROSS POTS

    // #region SAME POTS CONF
    private void drawPotSameConf(List<String> pot) {
        int size = pot.size() / 2;
        for (int j = 0; j < size; j++) {
            String team = null;
            List<String> options = new ArrayList<>();
            int count = Integer.MAX_VALUE;
            for (String p1 : pot) {
                List<String> o = getPossibleOpponents(p1, pot);
                if (o.size() < count) {
                    team = p1;
                    options = o;
                    count = o.size();
                }
            }
            String opp = CommonFunctions.getRandomElement(options);
            pot = removeElement(opp, removeElement(team, pot));
            addPair(new String[] { team, opp });
        }
    }

    private void setSamePotsConf() {
        for (int i = 1; i <= NUM_POTS; i++) {
            drawPotSameConf(getPotTeams(i));
        }
    }
    // #endregion SAME POTS CONF

    // #region DUMMY FOR SAME POT
    private boolean canPairDummy(String[] pair1, String[] pair2) {
        if (getPot(pair1[0]) == getPot(pair2[0]) && !canFace(pair1[0], pair2[0]))
            return false;
        if (getPot(pair1[0]) == getPot(pair2[1]) && !canFace(pair1[0], pair2[1]))
            return false;
        if (getPot(pair1[1]) == getPot(pair2[0]) && !canFace(pair1[1], pair2[0]))
            return false;
        if (getPot(pair1[1]) == getPot(pair2[1]) && !canFace(pair1[1], pair2[1]))
            return false;
        return true;
    }

    private List<List<String[]>> getDummyPairs(String key) {
        List<List<String[]>> dummy_pairs = new ArrayList<>();
        List<String[]> pairs = pairmap.get(key);
        for (int i = 0; i < pairs.size() / 2; i++) {
            for (int j = i + 1; j < pairs.size() / 2; j++) {
                if (canPairDummy(pairs.get(i), pairs.get(j))) {
                    List<String[]> dummy_pair = new ArrayList<>();
                    dummy_pair.add(pairs.get(i));
                    dummy_pair.add(pairs.get(j));
                    dummy_pairs.add(dummy_pair);
                }
            }
        }
        return dummy_pairs;
    }

    private List<String[]> orderDummies(List<String> dummies) {
        List<String> sorted = new ArrayList<>();
        for (int i = 1; i <= NUM_POTS; i++) {
            for (String x : dummies) {
                if (getPot(x) == i)
                    sorted.add(x);
            }
        }
        List<String[]> arr = new ArrayList<>();
        for (int i = 0; i < sorted.size(); i += 2) {
            arr.add(new String[] { sorted.get(i), sorted.get(i + 1) });
        }
        return arr;
    }

    private List<List<String>> getDummyTeamsForPot() {
        List<List<String>> dummies = new ArrayList<>();

        int a1 = 1;
        for (int a2 = a1 + 1; a2 <= NUM_POTS; a2++) {
            String k1 = getMapKey(a1, a2);
            for (int b1 = 1; b1 <= NUM_POTS; b1++) {
                if (a1 == b1 || a2 == b1)
                    continue;
                int b2 = 10 - (a1 + a2 + b1);
                if (b2 < b1)
                    continue;
                String k2 = getMapKey(b1, b2);
                List<List<String[]>> pairs1 = getDummyPairs(k1);
                List<List<String[]>> pairs2 = getDummyPairs(k2);

                int count = 0;
                for (List<String[]> p1 : pairs1) {
                    for (List<String[]> p2 : pairs2) {
                        List<String> arr = new ArrayList<>();
                        for (String[] x : p1) {
                            for (String a : x)
                                arr.add(a);
                        }
                        for (String[] x : p2) {
                            for (String a : x)
                                arr.add(a);
                        }
                        count++;
                        dummies.add(arr);
                    }
                }
                //System.out.println("KEY PAIR: " + k1 + "+" + k2 + ": " + count);
            }
        }
        //System.out.println("DUMMIES COUNT: " + dummies.size());
        return dummies;
    }

    private List<String> filterTeams(String dummy, List<String> t) {
        List<String> x = new ArrayList<>();
        for (String a : t) {
            if (a.equals(dummy))
                continue;
            x.add(a);
        }
        return x;
    }

    private boolean canFaceSamePot(String x, String y, List<String[]> arr) {
        if (!canFace(x, y))
            return false;

        for (String[] a : arr) {
            if (a[0].equals(x) && a[1].equals(y))
                return false;
            if (a[0].equals(y) && a[1].equals(x))
                return false;

            if (a[0].equals(x) && getNation(a[1]) == getNation(y)) {
                for (String t : opp_list.get(x)) {
                    if (getNation(t) == getNation(y))
                        return false;
                }
            }
            if (a[1].equals(x) && getNation(a[0]) == getNation(y)) {
                for (String t : opp_list.get(x)) {
                    if (getNation(t) == getNation(y))
                        return false;
                }
            }

            if (a[0].equals(y) && getNation(a[1]) == getNation(x)) {
                for (String t : opp_list.get(y)) {
                    if (getNation(t) == getNation(x))
                        return false;
                }
            }
            if (a[1].equals(y) && getNation(a[0]) == getNation(x)) {
                for (String t : opp_list.get(y)) {
                    if (getNation(t) == getNation(x))
                        return false;
                }
            }
        }
        return true;
    }

    private List<String[]> samePotArrangement(String[] dummies, int pot) {
        List<String[]> arr = new ArrayList<>();
        arr.add(dummies);
        List<String> t = getPotTeams(pot);
        List<String> t1 = filterTeams(dummies[0], t);
        List<String> t2 = filterTeams(dummies[1], t);
        int matches = t1.size() / 2;
        for (int i = 0; i < matches; i++) {
            List<String> opt = new ArrayList<>();
            int s = Integer.MAX_VALUE;
            String select = t1.get(0);
            for (String x : t1) {
                List<String> options = new ArrayList<>();
                for (String y : t1) {
                    if (canFace(x, y))
                        options.add(y);
                }
                if (options.size() < s) {
                    select = x;
                    opt = options;
                    s = options.size();
                }
                if (options.size() == 0)
                    return null;
            }
            if (opt.size() == 0)
                return null;
            String opp = CommonFunctions.getRandomElement(opt);
            arr.add(new String[] { select, opp });
            t1 = removeElement(opp, removeElement(select, t1));
        }

        for (int i = 0; i < matches; i++) {
            List<String> opt = new ArrayList<>();
            String select = t2.get(0);
            int s = Integer.MAX_VALUE;
            for (String x : t2) {
                List<String> options = new ArrayList<>();
                for (String y : t2) {
                    if (canFaceSamePot(x, y, arr))
                        options.add(y);
                }
                if (options.size() < s) {
                    select = x;
                    opt = options;
                    s = options.size();
                }
                if (options.size() == 0)
                    return null;
            }
            if (opt.size() == 0)
                return null;
            String opp = CommonFunctions.getRandomElement(opt);
            arr.add(new String[] { select, opp });
            t2 = removeElement(opp, removeElement(select, t2));
        }
        Collections.reverse(arr);
        return arr;
    }

    private List<String> getKeyPair(List<String> dummies) {
        List<String> pairs = new ArrayList<>();
        for (int i = 0; i < dummies.size(); i += 2) {
            int x = getPot(dummies.get(i)), y = getPot(dummies.get(i + 1));
            String key = null;
            if (x < y)
                key = getMapKey(x, y);
            else
                key = getMapKey(y, x);
            if (!pairs.contains(key))
                pairs.add(key);
        }
        return pairs;
    }

    private boolean selectDummyArrangement() {
        List<List<String>> list = getDummyTeamsForPot();
        Collections.shuffle(list);
        for (List<String> d : list) {
            List<String[]> l = orderDummies(d);
            List<String[]> pairs = new ArrayList<>();
            int success = 0;
            for (int i = 0; i < l.size(); i++) {
                List<String[]> p = samePotArrangement(l.get(i), i + 1);
                if (p != null) {
                    success++;
                    pairs.addAll(p);
                } else {
                    break;
                }
            }
            if (success == l.size()) {
                dummies_set = d;
                addPairs(pairs);
                return true;
            }
        }
        return false;
    }
    // #endregion DUMMY FOR SAME POT

    // #region ORDER PAIRS
    private void orderPots(String key) {
        List<String[]> pairs = pairmap.get(key);
        Map<String, Integer> homeGames = new HashMap<>(), awayGames = new HashMap<>();
        for (String[] pair : pairs) {
            for (String p : pair) {
                homeGames.put(p, 0);
                awayGames.put(p, 0);
            }
        }

        if (recursePairs(pairs, homeGames, awayGames, 0)) {
            pairmap.put(key, pairs);
            for (String[] pair : pairs) {
                matches.add(pair);
            }
        } else {
            System.out.println("No SOLUTION " + key);
        }
    }

    private boolean recursePairs(List<String[]> matches, Map<String, Integer> home, Map<String, Integer> away,
            int index) {
        if (index == matches.size())
            return true;
        String hTeam = matches.get(index)[0];
        String aTeam = matches.get(index)[1];

        if (home.get(hTeam) < 1 && away.get(aTeam) < 1) {
            home.put(hTeam, home.get(hTeam) + 1);
            away.put(aTeam, away.get(aTeam) + 1);
            if (recursePairs(matches, home, away, index + 1))
                return true;
            home.put(hTeam, home.get(hTeam) - 1);
            away.put(aTeam, away.get(aTeam) - 1);
        }

        if (home.get(aTeam) < 1 && away.get(hTeam) < 1) {
            matches.set(index, new String[] { aTeam, hTeam });
            home.put(aTeam, home.get(aTeam) + 1);
            away.put(hTeam, away.get(hTeam) + 1);
            if (recursePairs(matches, home, away, index + 1))
                return true;
            home.put(aTeam, home.get(aTeam) - 1);
            away.put(hTeam, away.get(hTeam) - 1);
            matches.set(index, new String[] { hTeam, aTeam });
        }
        return false;
    }

    private void orderPairs() {
        matches = new ArrayList<>();
        for (int i = 1; i <= NUM_POTS; i++) {
            for (int j = i; j <= NUM_POTS; j++) {
                String key = getMapKey(i, j);
                orderPots(key);
            }
        }
    }
    // #endregion ORDER PAIRS

    // #region CONF FIXTURES
    private List<Integer> getListPots() {
        List<Integer> x = new ArrayList<>();
        for (int i = 1; i <= NUM_POTS; i++)
            x.add(i);
        Collections.shuffle(x);
        return x;
    }

    private int[][] getArrangement(List<Integer> x) {
        int[][] arr = new int[x.size() / 2][2];
        for (int i = 0; i < x.size(); i += 2) {
            arr[i / 2][0] = x.get(i);
            arr[i / 2][1] = x.get(i + 1);
        }
        return arr;
    }

    private int[][] cycle(int[][] arr) {
        int first = arr[0][1];
        int last = arr[arr.length - 1][0];
        for (int i = arr.length - 1; i > 1; i--)
            arr[i][0] = arr[i - 1][0];
        for (int i = 0; i < arr.length - 1; i++)
            arr[i][1] = arr[i + 1][1];
        arr[1][0] = first;
        arr[arr.length - 1][1] = last;
        return arr;
    }

    private List<List<String>> getCrossList() {
        List<List<String>> str = new ArrayList<>();
        Set<String> keys = pairmap.keySet();
        int[][] arr = getArrangement(getListPots());
        for (int i = 1; i < NUM_POTS; i++) {
            List<String> x = new ArrayList<>();
            for (int j = 0; j < arr.length; j++) {
                String k = String.valueOf(arr[j][0] * 10 + arr[j][1]);
                if (!keys.contains(k))
                    k = String.valueOf(arr[j][1] * 10 + arr[j][0]);
                x.add(k);
            }
            str.add(x);
            arr = cycle(arr);
        }
        return str;
    }
    // #endregion CONF FIXTURES

    // #region DRAW SAME POT FIRST
    private List<String[]> getDummyPairsForPot(List<String> pTeams) {
        List<String[]> dummy_pairs = new ArrayList<>();
        for (int i = 0; i < pTeams.size(); i++) {
            for (int j = i + 1; j < pTeams.size(); j++) {
                if (canFace(pTeams.get(i), pTeams.get(j))) {
                    dummy_pairs.add(new String[] { pTeams.get(i), pTeams.get(j) });
                }
            }
        }
        return dummy_pairs;
    }

    private List<String[]> getArrangementsForPot(int pot) {
        List<String> pot_teams = getPotTeams(pot);
        List<String[]> dummy_pairs = getDummyPairsForPot(pot_teams);
        Collections.shuffle(dummy_pairs);
        for (String[] p : dummy_pairs) {
            List<String[]> pairs = samePotArrangement(p, pot);
            if (pairs != null) {
                return pairs;
            }
        }
        return null;
    }

    private void setSamePots() {
        for (int i = 1; i <= NUM_POTS; i++) {
            List<String[]> pairs = getArrangementsForPot(i);
            if (pairs != null) {
                addPairs(pairs);
            }
        }
    }

    private List<String[]> dummyPairMatchups(String[][] dummies) {
        List<String[]> dummy_pair_matches = new ArrayList<>();
        List<String> t = new ArrayList<>();
        for (String[] x : dummies) {
            for (String y : x)
                t.add(y);
        }
        int max_size = t.size() / 2;
        for (int i = 0; i < max_size; i++) {
            List<String> options = new ArrayList<>();
            int size = Integer.MAX_VALUE;
            String str = t.get(0);
            for (String p : t) {
                List<String> o = new ArrayList<>();
                for (String q : t) {
                    if (canFace(p, q) && getPot(p) != getPot(q))
                        o.add(q);
                }
                if (o.size() < size) {
                    size = o.size();
                    options = o;
                    str = p;
                }
            }
            if (options.size() != 0) {
                String op = CommonFunctions.getRandomElement(options);
                dummy_pair_matches.add(new String[] { str, op });
                t = removeElement(op, removeElement(str, t));
            } else {
                return null;
            }
        }
        return dummy_pair_matches;
    }

    private void selectDummyMatches() {
        List<String[]> pairs_a = pairmap.get(getMapKey(1, 1));
        List<String[]> pairs_b = pairmap.get(getMapKey(2, 2));
        List<String[]> pairs_c = pairmap.get(getMapKey(3, 3));
        List<String[]> pairs_d = pairmap.get(getMapKey(4, 4));

        List<String[][]> combinations = new ArrayList<>();
        for (String[] a : pairs_a) {
            for (String[] b : pairs_b) {
                for (String[] c : pairs_c) {
                    for (String[] d : pairs_d) {
                        combinations.add(new String[][] { a, b, c, d });
                    }
                }
            }
        }

        Collections.shuffle(combinations);
        for (String[][] c : combinations) {
            List<String[]> dpm = dummyPairMatchups(c);
            if (dpm != null && dpm.size() > 0) {
                addPairs(dpm);
                break;
            }
        }
    }

    private void setNonEmptyCrossPots() {
        for (int i = 1; i <= NUM_POTS; i++) {
            for (int j = i + 1; j <= NUM_POTS; j++) {
                String key = getMapKey(i, j);
                List<String[]> pairs = pairmap.get(key);
                if (pairs.size() != 0) {
                    System.out.println("====================CURRENT KEY: " + key + "================");
                    List<String> teams1 = getPotTeams(i);
                    List<String> teams2 = getPotTeams(j);
                    for (String[] p : pairs) {
                        for (String x : p) {
                            if (teams1.contains(x))
                                teams1 = removeElement(x, teams1);
                            if (teams2.contains(x))
                                teams2 = removeElement(x, teams2);
                        }
                    }
                    System.out.println("NON EMPTY BALANCE SET");
                    System.out.println(teams1 + " " + teams1.size());
                    System.out.println(teams2 + " " + teams2.size());
                    drawPots(teams1, teams2);
                    System.out.println("NON EMPTY SET 2");
                    drawPots(getPotTeams(i), getPotTeams(j));
                }
            }
        }
    }

    private void setEmptyCrossPots() {
        for (int i = 1; i <= NUM_POTS; i++) {
            for (int j = i + 1; j <= NUM_POTS; j++) {
                String key = getMapKey(i, j);
                List<String[]> pairs = pairmap.get(key);
                if (pairs.size() == 0) {
                    System.out.println("====================CURRENT KEY: " + key + "================");
                    for (int a = 0; a < MATCHES_PER_POT; a++) {
                        System.out.println("EMPTY SET: " + (a+1));
                        drawPots(getPotTeams(i), getPotTeams(j));
                    }
                }
            }
        }
    }
    // #endregion DRAW SAME POT FIRST

    private void displayOpponents() {
        List<String> t = getAllTeams();
        List<String> missing = new ArrayList<>();
        int count = 0;
        for (String team : t) {
            count++;
            String[] opps = new String[NUM_POTS * MATCHES_PER_POT];
            for (String[] p : matches) {
                if (p[0].equals(team)) {
                    int pot = getPot(p[1]);
                    opps[(pot - 1) * 2] = p[1];
                } else if (p[1].equals(team)) {
                    int pot = getPot(p[0]);
                    opps[((pot - 1) * 2) + 1] = p[0];
                }
            }
            System.out.print(count + ": " + team + ": ");
            for (int i = 0; i < NUM_POTS * MATCHES_PER_POT; i++) {
                String prefix = " " + (1 + (i / 2));
                if (i % 2 == 0)
                    prefix += "H";
                else
                    prefix += "A";
                System.out.print(prefix + ": " + opps[i] + "(" + getNationCode(teams.get(opps[i])) + ")");
                if (opps[i] == null) {
                    missing.add(team + " missing " + prefix);
                }
            }
            System.out.println();
        }
        System.out.println(missing);
    }

    private void schedule() {
        List<List<String[]>> sets = new ArrayList<>();
        List<List<String>> teamsInSet = new ArrayList<>();
        int matchdays = NUM_POTS * MATCHES_PER_POT;

        List<String> same = new ArrayList<>(), dummy = new ArrayList<>();
        for (int i = 0; i < matchdays; i++) {
            sets.add(new ArrayList<>());
            teamsInSet.add(new ArrayList<>());
        }

        for (int i = 1; i <= NUM_POTS; i++) {
            same.add(getMapKey(i, i));
            List<String[]> pairs = pairmap.get(getMapKey(i, i));
            for (String[] pair : pairs) {
                String x = pair[0], y = pair[1];
                for (int j = 0; j < 3; j++) {
                    if (teamsInSet.get(j).contains(x) || teamsInSet.get(j).contains(y))
                        continue;
                    if (teamsInSet.get(j).size() >= NUM_TEAMS)
                        continue;
                    sets.get(j).add(pair);
                    teamsInSet.get(j).add(x);
                    teamsInSet.get(j).add(y);
                    break;
                }
            }
        }

        dummy = getKeyPair(dummies_set);
        for (String key : pairmap.keySet()) {
            if (dummy.contains(key))
                continue;
            if (same.contains(key))
                continue;
            List<String[]> pairs = pairmap.get(key);
            for (String[] pair : pairs) {
                for (int d = NUM_POTS; d < matchdays; d++) {
                    if (!teamsInSet.get(d).contains(pair[0]) && !teamsInSet.get(d).contains(pair[1])) {
                        sets.get(d).add(pair);
                        teamsInSet.get(d).add(pair[0]);
                        teamsInSet.get(d).add(pair[1]);
                        break;
                    }
                }
            }
        }

        for (String key : dummy) {
            List<String[]> all_pairs = pairmap.get(key), filtered_pairs = new ArrayList<>(),
                    normal_pairs = new ArrayList<>();
            for (int i = all_pairs.size() - (NUM_TEAMS / NUM_POTS); i < all_pairs.size(); i++) {
                normal_pairs.add(all_pairs.get(i));
            }
            for (int i = 0; i < all_pairs.size() - (NUM_TEAMS / NUM_POTS); i++) {
                filtered_pairs.add(all_pairs.get(i));
            }

            for (String[] pair : filtered_pairs) {
                for (int d = 0; d < 3; d++) {
                    if (!teamsInSet.get(d).contains(pair[0]) && !teamsInSet.get(d).contains(pair[1])) {
                        sets.get(d).add(pair);
                        teamsInSet.get(d).add(pair[0]);
                        teamsInSet.get(d).add(pair[1]);
                        break;
                    }
                }
            }
            for (String[] pair : normal_pairs) {
                sets.get(3).add(pair);
                teamsInSet.get(3).add(pair[0]);
                teamsInSet.get(3).add(pair[1]);
            }
        }

        for (int i = matches.size() - 1; i >= 0; i--) {
            String[] match = matches.get(i);
            boolean found = false;
            for (List<String[]> set : sets) {
                for (String[] p : set) {
                    if (p[0].equals(match[0]) && p[1].equals(match[1])) {
                        found = true;
                        matches.remove(i);
                        break;
                    }
                }
                if (found)
                    break;
            }
        }

        if (matches.size() != 0) {
            System.out.println("No Schedule");
            // throw new Exception("No Schedule");
        } else {
            matchdayMap = new HashMap<>();
            Collections.shuffle(sets);
            for (int i = 0; i < sets.size(); i++) {
                matchdayMap.put(i + 1, sets.get(i));
            }
        }
    }

    private void scheduleConf() {
        List<List<String[]>> sets = new ArrayList<>();
        List<List<String>> teamsInSet = new ArrayList<>();
        int matchdays = NUM_POTS * MATCHES_PER_POT;

        for (int i = 0; i < matchdays; i++) {
            sets.add(new ArrayList<>());
            teamsInSet.add(new ArrayList<>());
        }

        List<String> same = new ArrayList<>();
        for (int i = 1; i <= NUM_POTS; i++)
            same.add(getMapKey(i, i));
        List<List<String>> crosslist = getCrossList();

        for (String key : same) {
            List<String[]> pairs = pairmap.get(key);
            for (String[] pair : pairs) {
                teamsInSet.get(0).add(pair[0]);
                teamsInSet.get(0).add(pair[1]);
                sets.get(0).add(pair);
            }
        }

        int matchday = 1;
        for (List<String> x : crosslist) {
            for (String key : x) {
                List<String[]> pairs = pairmap.get(key);
                for (String[] pair : pairs) {
                    teamsInSet.get(matchday).add(pair[0]);
                    teamsInSet.get(matchday).add(pair[1]);
                    sets.get(matchday).add(pair);
                }
            }
            matchday++;
        }

        matchdayMap = new HashMap<>();
        Collections.shuffle(sets);
        for (int i = 0; i < sets.size(); i++) {
            matchdayMap.put(i + 1, sets.get(i));
        }
    }

    private void displayMatchSchedule() {
        System.out.println("MATCH SCHEDULE");
        for (int k : matchdayMap.keySet()) {
            System.out.println("MATCHDAY " + k);
            for (String[] p : matchdayMap.get(k)) {
                System.out.println(p[0] + " vs " + p[1] + " " + getPot(p[0]) + "-" + getPot(p[1]));
            }
        }
    }

    // region SETUP
    private String[][] getPots() {
        String[][] tpots = new String[4][9];

        String[] t1 = { "Manchester City", "Bayern Munich", "Arsenal", "Athletic Bilbao", "Chelsea", "Liverpool",
                "Inter Milan", "Real Madrid", "Real Sociedad" };
        String[] t2 = { "Eintracht", "Barcelona", "Napoli", "Juventus", "Bayer Leverkusen", "Paris SG", "Feyenoord",
                "Atletico de Madrid", "AC Milan" };
        String[] t3 = { "Porto", "Benfica", "Ajax", "Panathinaikos", "Marseille", "Sparta Praha", "Hajduk Split",
                "Sturm Graz", "Zurich" };
        String[] t4 = { "Roda JC", "Nordsjaelland", "Aberdeen", "Anderlecht", "Molde", "Monchengladbach", "Everton",
                "Newcastle", "Lens" };

        tpots[0] = t1;
        tpots[1] = t2;
        tpots[2] = t3;
        tpots[3] = t4;
        return tpots;
    }

    private Map<String, Integer> getTeams() {
        Map<String, Integer> tnat = new HashMap<>();
        tnat.put("Manchester City", 2);
        tnat.put("Arsenal", 2);
        tnat.put("Chelsea", 2);
        tnat.put("Liverpool", 2);
        tnat.put("Everton", 2);
        tnat.put("Newcastle", 2);
        tnat.put("Athletic Bilbao", 1);
        tnat.put("Real Sociedad", 1);
        tnat.put("Real Madrid", 1);
        tnat.put("Barcelona", 1);
        tnat.put("Atletico de Madrid", 1);
        tnat.put("Bayern Munich", 3);
        tnat.put("Eintracht", 3);
        tnat.put("Bayer Leverkusen", 3);
        tnat.put("Monchengladbach", 3);
        tnat.put("Inter Milan", 5);
        tnat.put("Napoli", 5);
        tnat.put("Juventus", 5);
        tnat.put("AC Milan", 5);
        tnat.put("Paris SG", 4);
        tnat.put("Marseille", 4);
        tnat.put("Lens", 4);
        tnat.put("Feyenoord", 7);
        tnat.put("Ajax", 7);
        tnat.put("Roda JC", 7);
        tnat.put("Porto", 6);
        tnat.put("Benfica", 6);
        tnat.put("Anderlecht", 8);
        tnat.put("Aberdeen", 10);
        tnat.put("Hajduk Split", 11);
        tnat.put("Sturm Graz", 13);
        tnat.put("Sparta Praha", 14);
        tnat.put("Zurich", 16);
        tnat.put("Panathinaikos", 17);
        tnat.put("Nordsjaelland", 18);
        tnat.put("Molde", 19);
        return tnat;
    }

    private String getNationCode(int nat) {
        switch (nat) {
            case 1:
                return "ESP";
            case 2:
                return "ENG";
            case 3:
                return "GER";
            case 4:
                return "FRA";
            case 5:
                return "ITA";
            case 6:
                return "POR";
            case 7:
                return "NED";
            case 8:
                return "BEL";
            case 9:
                return "UKR";
            case 10:
                return "SCO";
            case 11:
                return "CRO";
            case 12:
                return "SRB";
            case 13:
                return "AUT";
            case 14:
                return "CZE";
            case 15:
                return "SVK";
            case 16:
                return "SUI";
            case 17:
                return "GRE";
            case 18:
                return "DEN";
            case 19:
                return "NOR";
            default:
                return "NULL";
        }
    }
    // endregion SETUP

    // #region SETUP CONF
    private Map<String, Integer> getTeamsConf() {
        Map<String, Integer> tnations = new HashMap<>();
        tnations.put("Chelsea", 1);
        tnations.put("Copenhagen", 2);
        tnations.put("Gent", 3);
        tnations.put("Fiorentina", 4);
        tnations.put("LASK", 5);
        tnations.put("Real Betis", 6);
        tnations.put("Istanbul BB", 7);
        tnations.put("Molde", 8);
        tnations.put("Legia Warsaw", 9);
        tnations.put("Heidenheim", 10);
        tnations.put("Djurgardens", 11);
        tnations.put("APOEL", 12);
        tnations.put("Rapid Wien", 5);
        tnations.put("Omonia", 12);
        tnations.put("HJK", 13);
        tnations.put("Vitoria", 14);
        tnations.put("Astana", 15);
        tnations.put("Olimpija", 16);
        tnations.put("Cercle Brugge", 3);
        tnations.put("Shamrock Rovers", 17);
        tnations.put("The New Saints", 18);
        tnations.put("Lugano", 19);
        tnations.put("Hearts", 20);
        tnations.put("Mlada Boleslav", 21);
        tnations.put("Petrocub", 22);
        tnations.put("St Gallen", 19);
        tnations.put("Panathinaikos", 23);
        tnations.put("TSC", 24);
        tnations.put("Borac BL", 25);
        tnations.put("Jagiellonia", 9);
        tnations.put("Celje", 16);
        tnations.put("Larne", 26);
        tnations.put("Dinamo Minsk", 27);
        tnations.put("Pafos", 12);
        tnations.put("Vikingur", 28);
        tnations.put("Noah", 29);
        return tnations;
    }

    private String[][] getPotsConf() {
        String[][] tpots = new String[6][6];

        String[] tp1 = { "Chelsea", "Copenhagen", "Gent", "Fiorentina", "LASK", "Real Betis" };
        String[] tp2 = { "Istanbul BB", "Molde", "Legia Warsaw", "Heidenheim", "Djurgardens", "APOEL" };
        String[] tp3 = { "Rapid Wien", "Omonia", "HJK", "Vitoria", "Astana", "Olimpija" };
        String[] tp4 = { "Cercle Brugge", "Shamrock Rovers", "The New Saints", "Lugano", "Hearts", "Mlada Boleslav" };
        String[] tp5 = { "Petrocub", "St Gallen", "Panathinaikos", "TSC", "Borac BL", "Jagiellonia" };
        String[] tp6 = { "Celje", "Larne", "Dinamo Minsk", "Pafos", "Vikingur", "Noah" };

        tpots[0] = tp1;
        tpots[1] = tp2;
        tpots[2] = tp3;
        tpots[3] = tp4;
        tpots[4] = tp5;
        tpots[5] = tp6;
        return tpots;
    }
    // #endregion SETUP CONF

    private void showPots() {
        System.out.println("CURRENT POTS: ");
        for (int i = 0; i < NUM_POTS; i++) {
            System.out.print("POT " + (i + 1) + ": ");
            for (int j = 0; j < NUM_TEAMS / NUM_POTS; j++) {
                System.out.print(Sim.getClub(pots[i][j]).getName() + "("
                        + Sim.getNation(teams.get(pots[i][j])).getCode() + ")" + "  ");
            }
            System.out.println();
        }
    }

    private void showTeams() {
        for (int i = 0; i < NUM_POTS; i++) {
            System.out.print("POT " + (i + 1) + ": ");
            for (int j = 0; j < NUM_TEAMS / NUM_POTS; j++) {
                System.out.print(Sim.getClub(pots[i][j]).getName() + " "
                        + ControllerEurope_New.getTeamCoefficient(pots[i][j]) + ", ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        FixturesTest ft = new FixturesTest();
        ft.initCL(ft.getPots(), ft.getTeams());
        ft.initPairMap();
        ft.initNationLimits();
        ft.setSamePots();
        ft.selectDummyMatches();
        ft.setNonEmptyCrossPots();
        ft.setEmptyCrossPots();
        for (String k : ft.pairmap.keySet()) {
            System.out.println(k + " " + ft.pairmap.get(k).size());
        }
    }
}