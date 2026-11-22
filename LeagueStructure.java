import java.util.HashMap;
import java.util.List;
import java.util.Map;

class LeagueStructure {
    enum DivisionType {
        BASIC,
        PLAYOFF,
        CUSTOM
    }
    private Map<Integer, Division> divisions;
    private int nation_id;

    public LeagueStructure() {
        this.divisions = new HashMap<>();
        this.nation_id = 0;
    }

    public LeagueStructure(int nation_id, Map<Integer, Division> divisions) {
        this.nation_id = nation_id;
        this.divisions = divisions;
    }

    public Map<Integer, Division> getDivisions() { return this.divisions; }

    public int getNation() { return this.nation_id; }

    public void setDivisions(Map<Integer, Division> divisions) {
        this.divisions = divisions;
    }

    public void setNation(int nation) { this.nation_id = nation; }

    public void startSeason() {
        for (int x : divisions.keySet()) {
            divisions.get(x).startSeason();
        }
    }

    public void simulateSeason() {
        for (int x : divisions.keySet()) {
            divisions.get(x).simulate();
        }
    }

    public void endSeason() {
        for (int x : divisions.keySet()) {
            divisions.get(x).endSeason();
        }
    }

    public void promoteAndRelegate() {
        int size = divisions.size();
        if (size == 1) {
            return;
        }
        for (int i = 1; i < size; i++) {
            List<String> prom = divisions.get(i+1).getPromotedTeams(), rel = divisions.get(i).getRelegatedTeams();
            if (prom.size() == rel.size()) {
                List<String> upper = divisions.get(i).getTeams();
                upper.addAll(prom);
                List<String> lower = divisions.get(i+1).getTeams();
                lower.addAll(rel);
                for (int p = 0; p < prom.size(); p++) {
                    lower = CommonFunctions.removeElement(prom.get(p), lower);
                    upper = CommonFunctions.removeElement(rel.get(p), upper);
                }
                divisions.get(i).setTeams(upper);
                divisions.get(i+1).setTeams(lower);
            }
        }
    }

    public Division getClubDivision(String c_id) {
        int level = 1;
        while(divisions.containsKey(level)){
            if (divisions.get(level).getTeams().contains(c_id)) return divisions.get(level);
            level++;
        }
        return null;
    }
}