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
