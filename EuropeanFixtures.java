import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

class EuropeanFixtures {
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

    // #region INIT
    public void initCL(String[][] p, Map<String, Integer> m) {
        pots = p;
        NUM_POTS = 4;
        MATCHES_PER_POT = 2;
        teams = setTeamsMap(p, m);
    }

    public void initConfLeague(String[][] p, Map<String, Integer> m) {
        pots = p;
        NUM_POTS = 6;
        MATCHES_PER_POT = 1;
        teams = setTeamsMap(p, m);
    }

    private Map<String, Integer> setTeamsMap(String[][] p, Map<String, Integer> m) {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < NUM_POTS; i++) {
            for (int j = 0; j < (NUM_TEAMS / NUM_POTS); j++) {
                map.put(p[i][j], m.get(p[i][j]));
            }
        }
        return map;
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
            count++;
            if (count > 10) {
                showPots();
                System.out.println("MAX COUNT REACHED CL/EL FIXTURES");
                return null;
            }
            if (count == 3) {
                adjustPots();
                showPots();
                System.out.println("ADJUSTED POT");
                continue;
            }
            if (count == 6) {
                adjustPotsNation();
                showPots();
                System.out.println("ADJUSTED POT");
                continue;
            }
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
    // #endregion INIT

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
            // System.out.println(p1 + ": " + o + " " + o.size());
            if (o.size() < count) {
                team = p1;
                options = o;
                count = o.size();
            }
        }

        for (String p2 : pot2) {
            List<String> o = getPossibleOpponents(p2, pot1);
            // System.out.println(p2 + ": " + o + " " + o.size());
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
            // System.out.println("MATCH: " + (j + 1) + " LEFT: " + pot1.size() + "/" +
            // pot2.size());
            String[] pair = drawCrossPot(pot1, pot2);
            // System.out.println(pair[0] + " v " + pair[1]);
            if (pot1.contains(pair[0])) {
                pot1 = CommonFunctions.removeElement(pair[0], pot1);
            } else {
                pot2 = CommonFunctions.removeElement(pair[0], pot2);
            }
            if (pot1.contains(pair[1])) {
                pot1 = CommonFunctions.removeElement(pair[1], pot1);
            } else {
                pot2 = CommonFunctions.removeElement(pair[1], pot2);
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
            pot = CommonFunctions.removeElement(opp, CommonFunctions.removeElement(team, pot));
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
                        dummies.add(arr);
                    }
                }
            }
        }
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
            t1 = CommonFunctions.removeElement(opp, CommonFunctions.removeElement(select, t1));
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
            t2 = CommonFunctions.removeElement(opp, CommonFunctions.removeElement(select, t2));
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

    private void showPots() {
        Map<Integer, Integer> count = new HashMap<>();
        System.out.println("CURRENT POTS: ");
        for (int i = 0; i < NUM_POTS; i++) {
            System.out.print("POT " + (i + 1) + ": ");
            for (int j = 0; j < NUM_TEAMS / NUM_POTS; j++) {
                String t = pots[i][j];
                if (count.containsKey(teams.get(t))) {
                    int c = count.get(teams.get(t));
                    count.put(teams.get(t), c + 1);
                } else {
                    count.put(teams.get(t), 1);
                }
                System.out.print(Sim.getClub(t).getName() + "(" + (teams.get(t)) + ")" + " ");
            }
            System.out.println();
        }
        for (Map.Entry<Integer, Integer> e : count.entrySet()) {
            if (e.getKey() >= 1000) {
                System.out.print(e.getKey() + "-" + e.getValue() + " ");
            } else {
                System.out.print(Sim.getNation(e.getKey()).getCode() + "-" + e.getValue() + " ");
            }
        }
        System.out.println();
    }

    private void adjustPotsNation() {
        Map<Integer, Integer> count = new HashMap<>();
        for (int i = 0; i < NUM_POTS; i++) {
            for (int j = 0; j < NUM_TEAMS / NUM_POTS; j++) {
                String t = pots[i][j];
                if (count.containsKey(teams.get(t))) {
                    int c = count.get(teams.get(t));
                    count.put(teams.get(t), c + 1);
                } else {
                    count.put(teams.get(t), 1);
                }
            }
        }
        int c = 0, n = 0;
        for (Map.Entry<Integer, Integer> e : count.entrySet()) {
            if (e.getValue() > c) {
                c = e.getValue();
                n = e.getKey();
            }
        }
        if (c > 5) {
            for (int i = 0; i < NUM_POTS; i++) {
                boolean f = false;
                for (int j = 0; j < NUM_TEAMS / NUM_POTS; j++) {
                    String t = pots[i][j];
                    if (teams.get(t) == n) {
                        int next = CommonFunctions.getRandomInt(10) + 2000;
                        teams.put(t, next);
                        System.out.println("CHANGE " + Sim.getClub(t).getName() + " " + next);
                        f = true;
                        break;
                    }
                }
                if (f)
                    break;
            }
        }
    }

    private void adjustPots() {
        int max = 3;
        for (int i = 0; i < NUM_POTS; i++) {
            System.out.println("POT " + (i + 1));
            List<String> t = getPotTeams(i + 1);
            Map<Integer, Integer> pot_nation = new HashMap<>();
            for (String x : t) {
                if (pot_nation.containsKey(teams.get(x)))
                    continue;
                int c = 0;
                for (String y : t) {
                    if (getNation(x) == getNation(y))
                        c++;
                }
                pot_nation.put(getNation(x), c);
            }
            List<Integer> max_nations = new ArrayList<>();
            for (Map.Entry<Integer, Integer> m : pot_nation.entrySet()) {
                if (m.getValue() > max) {
                    max_nations.add(m.getKey());
                }
            }
            for (Integer m : max_nations) {
                System.out.println(Sim.getNation(m).getCode() + "-" + pot_nation.get(m));
                int diff = pot_nation.get(m) - max;
                for (int count = 0; count < diff; count++) {
                    int to = -1;
                    for (int j = (NUM_TEAMS / NUM_POTS) - 1; j >= 0; j--) {
                        if (getNation(pots[i][j]) == m) {
                            to = j;
                            System.out.println(pots[i][to] + " moving");
                            break;
                        }
                    }
                    boolean swapped = false;
                    for (int p = i + 1; p < NUM_POTS && !swapped; p++) {
                        for (int j = 0; j < (NUM_TEAMS / NUM_POTS); j++) {
                            int nat_p = getNation(pots[p][j]);
                            if (pot_nation.containsKey(nat_p) && pot_nation.get(nat_p) >= max)
                                continue;
                            System.out.println(Sim.getClub(pots[p][j]).getName() + " " + Sim.getNation(nat_p).getCode()
                                    + " " + pot_nation.get(nat_p));
                            if (nat_p != m) {
                                System.out.println("SWAPPING: " + pots[p][j] + "(" + p + "," + j + ") and "
                                        + pots[i][to] + "(" + i + "," + to + ")");
                                String temp = pots[p][j];
                                pots[p][j] = pots[i][to];
                                pots[i][to] = temp;
                                pot_nation.put(m, pot_nation.get(m) - 1);
                                int update = 1;
                                if (pot_nation.containsKey(getNation(temp)))
                                    update = pot_nation.get(getNation(temp));
                                pot_nation.put(getNation(temp), update + 1);
                                swapped = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}