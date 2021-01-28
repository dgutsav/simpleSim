package Stages;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Functions.FixturesHelper;
import Models.CompetitionType;
import Models.Match;
import Stages.Helpers.Table;

public class RoundRobin implements Stage {
    private List<String> teams;
    private Table table;
    private boolean international;
    private boolean youth;
    private boolean neutral;
    private List<Match> fixtures;
    private static Stages stage_type = Stages.LEAGUE;
    private int matches_simulated = 0;
    private boolean simulated;

    public RoundRobin() {
        this.teams = new ArrayList<>();
        this.international = false;
        this.youth = false;
        this.neutral = false;
        this.fixtures = new ArrayList<>();
        this.simulated = false;
    }

    @Override
    public void simulate(Calendar date) {
        for (int i = matches_simulated; i < this.fixtures.size(); i++) {
            if (this.fixtures.get(i).getDate().before(date)) {
                this.fixtures.get(i).simulate();
                this.matches_simulated++;
                if (this.matches_simulated == this.fixtures.size()) {
                    this.simulated = true;
                }
            } else {
                break;
            }
        }
    }

    @Override
    public List<String> getTeams() {
        return this.teams;
    }

    @Override
    public List<String> getWinners() {
        List<String> ranks = new ArrayList<>();
        for (int i = 1; i <= this.teams.size(); i++) {
            ranks.add(this.table.getTeamAtRank(i));
        }
        return ranks;
    }

    @Override
    public void setup(List<String> teams) {
        this.teams = teams;
        this.table = new Table(this.teams.size());
        this.fixtures = FixturesHelper.scheduleMatches(teams, CompetitionType.DOMESTIC_LEAGUE);
        if (this.international) {
            this.fixtures = FixturesHelper.setInternationalMatches(this.fixtures);
        }
        if (this.youth) {
            this.fixtures = FixturesHelper.setYouthMatches(this.fixtures);
        }
        if (this.neutral) {
            this.fixtures = FixturesHelper.setNeutralVenues(this.fixtures);
        }
    }

    @Override
    public void setInternational() {
        this.international = true;
    }

    @Override
    public Stages getStageType() {
        return RoundRobin.stage_type;
    }

    @Override
    public void setYouth() {
        this.youth = true;
    }

    @Override
    public void setNeutral() {
        this.neutral = true;
    }

    @Override
    public boolean hasBeenSimulated() {
        return this.simulated;
    }
}
