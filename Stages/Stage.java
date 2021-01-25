package Stages;

import java.util.List;

public interface Stage {
    public void addTeam(String team_id);

    public void setup();

    public void simulate();

    public List<String> getTeams();

    public List<String> getWinners();

    public void setInternational();
}
