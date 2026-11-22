import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public final class CCDrawHelper {
    private CCDrawHelper() {
    }

    public static String[][] drawGroups(String[][] pots, int numGroups, int numPots,
            Map<String, Integer> nations) {
        return drawGroups(pots, numGroups, numPots, nations, new Random());
    }

    public static String[][] drawGroups(String[][] pots, int numGroups, int numPots,
            Map<String, Integer> nations, Random rng) {
        validate(pots, numGroups, numPots, nations);

        String[][] groups = new String[numGroups][numPots];
        for (int g = 0; g < numGroups; g++)
            groups[g][0] = pots[0][g];

        if (numPots > 1 && !fillPot(groups, 1, shuffled(pots[1], rng), pots, nations, rng))
            throw new IllegalStateException("No valid draw exists for these pots and nations");
        return groups;
    }

    private static boolean fillPot(String[][] groups, int pot, List<String> remaining,
            String[][] pots, Map<String, Integer> nations, Random rng) {
        if (remaining.isEmpty()) {
            int next = pot + 1;
            if (next == groups[0].length)
                return true;
            return fillPot(groups, next, shuffled(pots[next], rng), pots, nations, rng);
        }

        String team = null;
        List<Integer> options = null;
        for (String candidate : remaining) {
            List<Integer> opts = getGroupOptions(candidate, pot, groups, nations);
            if (opts.isEmpty())
                return false; // dead end: this team can't be placed anywhere
            if (options == null || opts.size() < options.size()) {
                team = candidate;
                options = opts;
            }
        }

        Collections.shuffle(options, rng);
        remaining.remove(team);
        for (int g : options) {
            groups[g][pot] = team;
            if (fillPot(groups, pot, remaining, pots, nations, rng))
                return true;
            groups[g][pot] = null;
        }
        remaining.add(team); // undo for the caller's next attempt
        return false;
    }

    private static List<Integer> getGroupOptions(String team, int pot, String[][] groups,
            Map<String, Integer> nations) {
        Integer nation = nations.get(team);
        List<Integer> options = new ArrayList<>();
        for (int g = 0; g < groups.length; g++) {
            if (groups[g][pot] != null)
                continue;
            boolean clash = false;
            for (int p = 0; p < pot && !clash; p++)
                clash = nation.equals(nations.get(groups[g][p]));
            if (!clash)
                options.add(g);
        }
        return options;
    }

    private static List<String> shuffled(String[] pot, Random rng) {
        List<String> list = new ArrayList<>(Arrays.asList(pot));
        Collections.shuffle(list, rng);
        return list;
    }

    private static void validate(String[][] pots, int numGroups, int numPots,
            Map<String, Integer> nations) {
        if (numGroups < 1 || numPots < 1)
            throw new IllegalArgumentException("numGroups and numPots must be positive");
        if (pots.length < numPots)
            throw new IllegalArgumentException("Expected " + numPots + " pots, got " + pots.length);

        Set<String> seen = new HashSet<>();
        Map<Integer, Integer> perNation = new HashMap<>();
        for (int p = 0; p < numPots; p++) {
            if (pots[p].length != numGroups)
                throw new IllegalArgumentException(
                        "Pot " + p + " has " + pots[p].length + " teams, expected " + numGroups);
            for (String team : pots[p]) {
                Integer nation = nations.get(team);
                if (nation == null)
                    throw new IllegalArgumentException("No nation for team: " + team);
                if (!seen.add(team))
                    throw new IllegalArgumentException("Team listed twice: " + team);
                if (perNation.merge(nation, 1, Integer::sum) > numGroups)
                    throw new IllegalArgumentException(
                            "Nation " + nation + " has more teams than there are groups");
            }
        }
    }
}