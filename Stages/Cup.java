package Stages;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Cup implements Stage {
    private boolean simulated;
    private List<String> teams;
    public Cup() {
        /*this.teams = new ArrayList<>();
        this.international = false;
        this.youth = false;
        this.neutral = false;
        this.fixtures = new ArrayList<>();
        this.simulated = false;*/
    }

    @Override
    public void setup(List<String> teams) {

    }

    @Override
    public void simulate(Calendar date) {

    }

    @Override
    public List<String> getTeams() {

        return null;
    }

    @Override
    public List<String> getWinners() {

        return null;
    }

    @Override
    public void setInternational() {

    }

    @Override
    public void setYouth() {

    }

    @Override
    public void setNeutral() {

    }

    @Override
    public Stages getStageType() {

        return null;
    }

    @Override
    public boolean hasBeenSimulated() {
        return false;
    }

}
