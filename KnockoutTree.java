import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class KnockoutTree {
    enum KnockoutType {
        SINGLE,
        TWO_LEGGED
    }

    private Map<String, List<KnockoutTie>> stages;
    private List<String> stage_names;
    private String bye = "bye";
    private KnockoutType type = KnockoutType.SINGLE;

    public void setup(List<String[]> pairs, List<String> names) {
        this.stage_names = names;
        this.stages = new HashMap<>();
        this.stages.put(this.stage_names.get(0), getInitialStage(pairs));
    }

    private KnockoutTie getSingleKnockout(String x, String y) {
        return new SingleKnockoutTie(x, y);
    }

    private KnockoutTie getTwoLeggedKnockout(String x, String y) {
        return new TwoLeggedKnockoutTie(x, y, 1);
    }

    private KnockoutTie getTie(String x, String y) {
        if (type == KnockoutType.SINGLE) {
            return (getSingleKnockout(x, y));
        } else if (type == KnockoutType.TWO_LEGGED) {
            return (getTwoLeggedKnockout(x, y));
        } else {
            return (getSingleKnockout(x, y));
        }
    }

    private List<KnockoutTie> getInitialStage(List<String[]> pairs) {
        List<KnockoutTie> ties = new ArrayList<>();
        for (String[] pair : pairs) {
            if (pair[0].equals(bye) || pair[1].equals(bye)) {
                if (pair[0].equals(bye))
                    ties.add(new DummyKnockout(pair[1]));
                else
                    ties.add(new DummyKnockout(pair[0]));
            } else {
                ties.add(getTie(pair[0], pair[1]));
            }
        }
        return ties;
    }

    private void simulateStage(String name) {
        int size = stages.get(name).size();
        for (int i = 0; i < size; i++) {
            stages.get(name).get(i).simulate();
            stages.get(name).get(i).show();
        }
    }

    private boolean isFinalStage(String name) {
        return stage_names.get(stage_names.size() - 1).equals(name);
    }

    private String getNextStage(String name) {
        for (int i = 0; i < stage_names.size(); i++) {
            if (stage_names.get(i).equals(name) && i + 1 < stage_names.size())
                return stage_names.get(i + 1);
        }
        return null;
    }

    private List<KnockoutTie> setStage(String name) {
        List<KnockoutTie> ties = new ArrayList<>();
        int size = stages.get(name).size();
        for (int i = 0; i < size; i += 2) {
            String home = stages.get(name).get(i).getWinner();
            String away = stages.get(name).get(i + 1).getWinner();
            ties.add(getTie(home, away));
        }
        return ties;
    }

    public void simulate() {
        for (int i = 0; i < stage_names.size(); i++) {
            String stage = stage_names.get(i);
            System.out.println(stage);
            simulateStage(stage);
            if (!isFinalStage(stage)) {
                List<KnockoutTie> ties = setStage(stage);
                stages.put(getNextStage(stage), ties);
            }
        }
    }

    public String getWinner() {
        return stages.get(stage_names.get(stage_names.size() - 1)).get(0).getWinner();
    }
}