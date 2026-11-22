package Models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Stages.*;

public class Competition {
    private List<Stage> stages;
    private String id;
    private CompetitionType type;
    private int reputation;
    private int pyramid_level;

    public static Competition createDomesticLeague(int reputation, int pyramid_level) {
        Competition comp = new Competition();
        comp.pyramid_level = pyramid_level;
        comp.reputation = reputation;
        comp.type = CompetitionType.DOMESTIC_LEAGUE;
        comp.stages = new ArrayList<>();
        comp.stages.add(new RoundRobin());
        return comp;
    }

    public static Competition createDomesticCup(int reputation) {
        Competition comp = new Competition();
        comp.reputation = reputation;
        comp.type = CompetitionType.DOMESTIC_CUP;
        comp.stages = new ArrayList<>();
        return comp;
    }

    public void setup(List<String> teams) {
        stages.get(0).setup(teams);
    }

    public int getPyramidLevel() {
        return this.pyramid_level;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public CompetitionType getType() {
        return type;
    }

    public boolean isDomesticLeague() {
        return CompetitionType.DOMESTIC_LEAGUE == type;
    }

    public List<String> getTeams() {
        if (isDomesticLeague()) {
            return stages.get(0).getTeams();
        }
        return new ArrayList<>();
    }

    public int getReputation() {
        return this.reputation;
    }

    public void simulate(Calendar date) {
        for (int i = 0; i < this.stages.size(); i++) {
            if (!this.stages.get(i).hasBeenSimulated()) {
                this.stages.get(i).simulate(date);
                break;
            }
        }
    }
}
