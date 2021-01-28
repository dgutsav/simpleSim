package Models;

import java.util.Calendar;

import Functions.MatchSimulator;
import Functions.SimBettered;

public class Match {
    private String home_team_id;
    private String away_team_id;
    private int home_goals;
    private int away_goals;
    private Calendar date;
    private boolean youth_match;
    private boolean international_match;
    private boolean simulated;
    private boolean neutral_venue;
    private int level;

    public Match(String home, String away, Calendar d) {
        this.home_team_id = home;
        this.away_team_id = away;
        this.date = d;
        this.home_goals = 0;
        this.away_goals = 0;
        this.youth_match = false;
        this.international_match = false;
        this.simulated = false;
        this.level = 0;
    }

    public String getHomeId() {
        return this.home_team_id;
    }

    public String getAwayId() {
        return this.away_team_id;
    }

    public void setYouthMatch() {
        this.youth_match = true;
    }

    public void setInternationalMatch() {
        this.international_match = true;
    }

    public void setNeutralVenue() {
        this.neutral_venue = true;
    }

    public boolean isNeutralVenue() {
        return this.neutral_venue;
    }

    public boolean isSimulated() {
        return this.simulated;
    }

    public boolean isYouth() {
        return this.youth_match;
    }

    public boolean isInternational() {
        return this.international_match;
    }

    public int getHomeGoals() {
        return this.home_goals;
    }

    public int getAwayGoals() {
        return this.away_goals;
    }

    public int getResult() {
        if (this.home_goals > this.away_goals) {
            return 1;
        } else if (this.home_goals < this.away_goals) {
            return 2;
        } else {
            return 3;
        }
    }

    public void display() {
        String home_name = SimBettered.getTeamName(this.home_team_id);
        String away_name = SimBettered.getTeamName(this.away_team_id);
        if (isSimulated()) {
            System.out.println(home_name + " " + home_goals + "-" + away_goals + " " + away_name);
        } else {
            System.out.println(home_name + " vs " + away_name);
        }
    }

    public Calendar getDate() {
        return this.date;
    }

    public void simulate() {
        if (!isSimulated()) {
            Squad[] squads = SimBettered.getSquads(this);
            if (MatchSimulator.simulateMatch(this, squads[0], squads[1])) {
                this.home_goals = MatchSimulator.getHomeGoals();
                this.away_goals = MatchSimulator.getAwayGoals();
                this.simulated = true;
            }
        }
    }
}
