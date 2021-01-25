package Stages;

import java.util.ArrayList;
import java.util.List;

import Stages.Helpers.Table;

public class RoundRobin implements Stage {
    private List<String> teams;
    private Table table;
    private boolean international;

    public RoundRobin() {
        this.teams = new ArrayList<>();
        this.international = false;
    }

    @Override
    public void simulate() {

    }

    @Override
    public List<String> getTeams() {
        return this.teams;
    }

    @Override
    public List<String> getWinners() {
        return null;
    }

    @Override
    public void addTeam(String team_id) {
        this.teams.add(team_id);
    }

    @Override
    public void setup() {
        int size = this.teams.size();
        this.table = new Table(size);
    }

    @Override
    public void setInternational() {
        this.international = true;
    }

}
