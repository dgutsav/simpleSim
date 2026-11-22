package Stages;

import java.util.Calendar;
import java.util.List;

public interface Stage {
    public void setup(List<String> teams);

    public void simulate(Calendar date);

    public List<String> getTeams();

    public List<String> getWinners();

    public void setInternational();

    public void setYouth();

    public void setNeutral();

    public Stages getStageType();

    public boolean hasBeenSimulated();
}
