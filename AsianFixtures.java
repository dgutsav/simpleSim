import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Builds a league-phase schedule from pots of clubs.
 *
 * Layout: the draw is a grid with one ROW per slot in a pot and one COLUMN per
 * pot,
 * so every row holds one club from each pot. Rows form a ring; a club plays
 * every club
 * in the two neighbouring rows (2 x number-of-pots matches). Clubs in the same
 * row, or in
 * non-adjacent rows, never meet. To keep clubs from the same nation apart, no
 * nation may
 * appear in two adjacent rows (same-row is fine, they never face each other).
 */
final class AsianFixtures {
    private final String[][] pots;
    private final Map<String, Integer> nationOf;
    private final Random rng;
    private final int potCount; // columns
    private final int rowCount; // clubs per pot

    AsianFixtures(String[][] pots, Map<String, Integer> nationOf) {
        this(pots, nationOf, new Random());
    }

    /** Pass a seeded Random for reproducible draws (useful in tests). */
    AsianFixtures(String[][] pots, Map<String, Integer> nationOf, Random rng) {
        validate(pots, nationOf);
        this.pots = Arrays.stream(pots).map(String[]::clone).toArray(String[][]::new);
        this.nationOf = Map.copyOf(nationOf);
        this.rng = rng;
        this.potCount = pots.length;
        this.rowCount = pots[0].length;
    }

    private static void validate(String[][] pots, Map<String, Integer> nationOf) {
        if (pots == null || pots.length == 0 || pots.length % 2 != 0)
            throw new IllegalArgumentException("Need an even, non-zero number of pots");
        int size = pots[0].length;
        if (size < 4 || size % 2 != 0)
            throw new IllegalArgumentException("Each pot needs an even number of clubs, at least 4");
        Set<String> seen = new HashSet<>();
        for (String[] pot : pots) {
            if (pot.length != size)
                throw new IllegalArgumentException("All pots must be the same size");
            for (String team : pot) {
                if (nationOf.get(team) == null)
                    throw new IllegalArgumentException("No nation for team: " + team);
                if (!seen.add(team))
                    throw new IllegalArgumentException("Team appears twice: " + team);
            }
        }
    }

    // region DRAW

    /** Returns grid[row][pot]. Throws if no valid draw exists. */
    String[][] drawGrid() {
        List<List<String>> shuffled = new ArrayList<>();
        for (String[] pot : pots) {
            List<String> list = new ArrayList<>(Arrays.asList(pot));
            Collections.shuffle(list, rng);
            shuffled.add(list);
        }
        String[][] grid = new String[rowCount][potCount];
        if (!fill(grid, shuffled, 0, 0)) {
            Map<Integer, Integer> s = new HashMap<>();
            for (String x : nationOf.keySet()) {
                int n = nationOf.get(x);
                if (s.containsKey(n)) {
                    int c = s.get(n);
                    s.put(n, c + 1);
                } else
                    s.put(n, 1);
            }
            for (Integer i : s.keySet()) {
                System.out.println(Sim.getNation(i).getName() + " " + s.get(i));
            }
            throw new IllegalStateException("No draw satisfies the same-nation constraint for these pots");
        }
        return grid;
    }

    /**
     * Backtracking: place club `idx` of pot `pot` into some free row of that pot's
     * column.
     */
    private boolean fill(String[][] grid, List<List<String>> shuffled, int pot, int idx) {
        if (pot == potCount)
            return true;
        if (idx == rowCount)
            return fill(grid, shuffled, pot + 1, 0);

        String team = shuffled.get(pot).get(idx);
        List<Integer> rows = new ArrayList<>();
        for (int r = 0; r < rowCount; r++)
            rows.add(r);
        Collections.shuffle(rows, rng);

        for (int row : rows) {
            if (grid[row][pot] != null || clashesWithNeighbour(team, row, grid))
                continue;
            grid[row][pot] = team;
            if (fill(grid, shuffled, pot, idx + 1))
                return true;
            grid[row][pot] = null;
        }
        return false;
    }

    private boolean clashesWithNeighbour(String team, int row, String[][] grid) {
        Integer nation = nationOf.get(team);
        int[] neighbours = { (row + rowCount - 1) % rowCount, (row + 1) % rowCount };
        for (int n : neighbours) {
            for (String other : grid[n]) {
                if (other != null && nation.equals(nationOf.get(other)))
                    return true;
            }
        }
        return false;
    }

    /** Human-readable grid, for debugging. */
    String describeGrid(String[][] grid) {
        StringBuilder sb = new StringBuilder();
        for (String[] row : grid) {
            for (String team : row)
                sb.append(String.format("%-26s", team + " (" + nationOf.get(team) + ")"));
            sb.append('\n');
        }
        return sb.toString();
    }

    // endregion DRAW

    // region FIXTURES

    public List<Match> getFixtures() {
        String[][] grid = drawGrid();
        List<Match> matches = new ArrayList<>();

        // First half: rows (0,1), (2,3), ... Second half: rows (1,2), (3,4), ...,
        // (last,0).
        for (int a = 0; a < rowCount; a += 2)
            addPairing(matches, grid[a], grid[a + 1], 0, true);
        for (int a = 1; a < rowCount; a += 2)
            addPairing(matches, grid[a], grid[(a + 1) % rowCount], potCount, false);

        matches.sort(Comparator.comparingInt(m -> m.matchday)); // stable
        return matches;
    }

    /**
     * Every club in rowA plays every club in rowB once, over `rowA.length`
     * matchdays
     * (round-robin by rotating rowB). Home advantage alternates each matchday, and
     * the
     * second half starts with the opposite pattern, so every club alternates H/A
     * all season.
     */
    private void addPairing(List<Match> out, String[] rowA, String[] rowB, int baseMatchday, boolean aHostsFirst) {
        for (int shift = 0; shift < rowA.length; shift++) {
            boolean aHome = (shift % 2 == 0) == aHostsFirst;
            for (int j = 0; j < rowA.length; j++) {
                String a = rowA[j];
                String b = rowB[(j + shift) % rowB.length];
                int matchday = baseMatchday + shift + 1;
                out.add(aHome ? newMatch(a, b, matchday) : newMatch(b, a, matchday));
            }
        }
    }

    private static Match newMatch(String home, String away, int matchday) {
        Match m = new Match();
        m.home_id = home;
        m.away_id = away;
        m.matchday = matchday;
        m.home_team = Sim.getClub(home).getName();
        m.away_team = Sim.getClub(away).getName();
        return m;
    }

    // endregion FIXTURES
}