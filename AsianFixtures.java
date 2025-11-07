import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class AsianFixtures {
    private Map<String, Integer> teams = new HashMap<>();
    private Map<String, List<String>> oppList = new HashMap<>();
    private Map<Integer, List<String[]>> matchups = new HashMap<>();

    private int NUM_MATCHES = 8;

    // region SETUP
    private void setTeams() {
        teams.put("Al Nassr", 1);
        teams.put("Al Hilal", 1);
        teams.put("Al Ahli", 1);
        teams.put("Urawa Red Diamonds", 2);
        teams.put("Yokohama F. Marinos", 2);
        teams.put("Vissel Kobe", 2);
        teams.put("Persepolis", 3);
        teams.put("Esteghlal", 3);
        teams.put("Pohang Steelers", 4);
        teams.put("Ulsan HD", 4);
        teams.put("Melbourne Victory", 5);
        teams.put("Pakhtakor", 6);
    }

    private String getNationCode(int nat) {
        switch (nat) {
            case 1:
                return "KSA";
            case 2:
                return "JPN";
            case 3:
                return "IRN";
            case 4:
                return "KOR";
            case 5:
                return "AUS";
            case 6:
                return "UZB";
        }
        return null;
    }
    // endregion SETUP

    private void init() {
        for (String t : teams.keySet()) {
            oppList.put(t, new ArrayList<>());
        }
    }

    private boolean canFace(String a, String b) {
        if (a.equals(b))
            return false;
        else if (teams.get(a) == teams.get(b))
            return false;
        else if (oppList.get(a).contains(b))
            return false;
        else if (oppList.get(b).contains(a))
            return false;
        else
            return true;
    }

    private List<String> getOptions(String t, List<String> options) {
        List<String> opt = new ArrayList<>();
        for (String x : options) {
            if (canFace(t, x))
                opt.add(x);
        }
        return opt;
    }

    private String[] drawPair(List<String> t) {
        List<String> options = new ArrayList<>();
        String team = null;
        int count = Integer.MAX_VALUE;
        for (String x : t) {
            List<String> o = getOptions(x, t);
            if (o.size() < count) {
                options = o;
                team = x;
                count = o.size();
            }
        }
        String opp = null;
        count = Integer.MAX_VALUE;
        for (String x : options) {
            List<String> o = getOptions(x, t);
            if (o.size() < count) {
                opp = x;
                count = o.size();
            }
        }
        return new String[] { team, opp };
    }

    private List<String[]> drawMatchday() {
        List<String> x = new ArrayList<>();
        for (String t : teams.keySet()) {
            x.add(t);
        }
        Collections.shuffle(x);
        int size = x.size() / 2;
        List<String[]> pairs = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            String[] p = drawPair(x);
            x = CommonFunctions.removeElement(p[0], CommonFunctions.removeElement(p[1], x));
            pairs.add(p);
        }
        return pairs;
    }

    private void addPair(String[] p) {
        oppList.get(p[0]).add(p[1]);
        oppList.get(p[1]).add(p[0]);
    }

    private void schedule() {
        for (int i = 0; i < NUM_MATCHES; i++) {
            List<String[]> matches = drawMatchday();
            matchups.put(i + 1, matches);
            for (String[] x : matches) {
                addPair(x);
            }
        }
    }

    private String getTeamName(String x) {
        return x + "(" + getNationCode(teams.get(x)) + ")";
    }

    private void display() {
        for (int i = 1; i <= NUM_MATCHES; i++) {
            List<String[]> m = matchups.get(i);
            System.out.println("MATCHDAY " + i);
            for (String[] p : m) {
                System.out.println(getTeamName(p[0]) + " vs " + getTeamName(p[1]));
            }
        }
    }

    private List<String[]> orderPots(List<String[]> matches) {
        Map<String, Integer> homeGames = new HashMap<>(), awayGames = new HashMap<>();
        for (String[] pair : matches) {
            for (String p : pair) {
                homeGames.put(p, 0);
                awayGames.put(p, 0);
            }
        }

        if (recursePairs(matches, homeGames, awayGames, 0)) {
            return matches;
        } else {
            System.out.println("No SOLUTION ");
            return new ArrayList<>();
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

    private void orderMatches() {
        for (int i = 1; i <= NUM_MATCHES; i += 2) {
            List<String[]> m = new ArrayList<>();
            m.addAll(matchups.get(i));
            m.addAll(matchups.get(i + 1));
            List<String[]> o = orderPots(m);
            int s = o.size();
            List<String[]> g1 = new ArrayList<>();
            for (int x = 0; x< s/2; x++) {
                g1.add(o.get(x));
            }
            List<String[]> g2 = new ArrayList<>();
            for (int x = s/2; x< s; x++) {
                g2.add(o.get(x));
            }
            matchups.put(i, g1);
            matchups.put(i+1, g2);
        }
    }

    public static void main(String[] args) {
        AsianFixtures a = new AsianFixtures();
        a.setTeams();
        a.init();
        a.schedule();
        a.orderMatches();
        a.display();
    }
}