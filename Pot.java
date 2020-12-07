import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Pot {
    List<String> teams = new ArrayList<>();
    Map<String, List<Integer>> possible_groups = new HashMap<>();
    int pot_in_use = 2;

    List<Integer> drawn_team_possibilities = new ArrayList<>();

    public void init(String[] teams_to_add, int num_groups, int pot_number, String[][] currentGroups) {
        List<Integer> groups = new ArrayList<>();
        for (int i = 0; i < num_groups; i++) {
            groups.add(i);
        }
        for (String team_to_add : teams_to_add) {
            teams.add(team_to_add);
            possible_groups.put(team_to_add, groups);
        }
        pot_in_use = pot_number;
        updatePossibleGroups(currentGroups);
    }

    public String drawTeam() {
        String drawn;
        for (Map.Entry<String, List<Integer>> entry : possible_groups.entrySet()) {
            if (entry.getValue().size() == 1) {
                drawn = entry.getKey();
                drawn_team_possibilities = entry.getValue();
                possible_groups.remove(drawn);
                teams.remove(drawn);
                return drawn;
            }
        }
        int rand = CommonFunctions.getRandomNumber(teams.size());
        drawn = teams.get(rand);
        drawn_team_possibilities = possible_groups.get(drawn);
        possible_groups.remove(drawn);
        teams.remove(drawn);
        return drawn;
    }

    public boolean fromSameNation(String code1, String code2) {
        return code1.substring(0, 2).equalsIgnoreCase(code2.substring(0, 2));
    }

    public boolean canPutInGroup(String code, String[] group) {
        if (!group[this.pot_in_use - 1].equals("LOREM")) {
            return false;
        }
        for (String str : group) {
            if (fromSameNation(code, str)) {
                return false;
            }
        }
        return true;
    }

    public void updatePossibleGroups(String[][] groups) {
        for (String team : teams) {
            List<Integer> possibilities = possible_groups.get(team);
            List<Integer> duplicate = new ArrayList<>();
            for (int i : possibilities) {
                if (canPutInGroup(team, groups[i])) {
                    duplicate.add(i);
                }
            }
            possible_groups.put(team, duplicate);
        }
    }

    public boolean isEmpty() {
        return teams.isEmpty();
    }

    public List<Integer> getPossibleGroupsForDrawnTeam() {
        return this.drawn_team_possibilities;
    }

    public void displayPossibleGroups() {
        for (Map.Entry<String, List<Integer>> entry : possible_groups.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue().size());
        }
    }

    public static void main(String[] args) {
        //test pot
        Pot pot = new Pot();
        String[][] groups = { { "ENG1", "LOREM", "LOREM", "LOREM" }, { "ESP1", "LOREM", "LOREM", "LOREM" },
                { "ITA1", "LOREM", "LOREM", "LOREM" }, { "GER1", "LOREM", "LOREM", "LOREM" },
                { "FRA1", "LOREM", "LOREM", "LOREM" }, { "POR1", "LOREM", "LOREM", "LOREM" },
                { "RUS1", "LOREM", "LOREM", "LOREM" }, { "UKR1", "LOREM", "LOREM", "LOREM" } };
        List<String> teams = new ArrayList<>();
        teams.add("ENG2");
        teams.add("ENG3");
        teams.add("ESP2");
        teams.add("GER2");
        teams.add("ITA2");
        teams.add("ITA3");
        teams.add("FRA2");
        teams.add("POR2");
        pot.teams = teams;
        pot.pot_in_use = 2;
        List<Integer> groups_num = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            groups_num.add(i);
        }
        for (String team : teams) {
            pot.possible_groups.put(team, groups_num);
        }
        pot.updatePossibleGroups(groups);
        pot.displayPossibleGroups();
    }
}
