import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MajorLeague implements Division {
    private enum Conference {
        EAST, WEST
    };

    private String name;
    private int comp_level;
    private int promotion_places;
    private int relegation_places;
    private String id;
    private List<CompetitionHistory> competitionHistory;
    private int nation_id;
    private int reputation;
    private List<String> teams;

    private List<Match> fixtures;

    private int NUM_TEAMS = 30;
    private int MATCHDAYS = 36;
    private Map<Conference, List<String>> confTeams;
    private Map<Conference, Table> confTable;
    private Table aggregateTable;
    private Map<Conference, KnockoutTree> tree;
    private SingleKnockoutTie cup_final;

    private void initMajorLeague() {
        name = "Major League Soccer";
        reputation = 160;
        confTeams = new HashMap<>();
        confTeams.put(Conference.EAST, new ArrayList<>());
        confTeams.put(Conference.WEST, new ArrayList<>());
        for (String t : this.getTeams()) {
            String name = Sim.getClub(t).getName();
            confTeams.get(getConferenceType(name)).add(t);
        }
    }

    public void setId(String id) { this.id = id; }

    public String getId() { return this.id; }

    public String getName() { return this.name; }

    public int getLevel() { return this.comp_level; }
    
    public int getPromotionPlaces() { return this.promotion_places; }
    
    public int getRelegationPlaces() { return this.relegation_places; }

    public int getNation() { return this.nation_id; }

    public int getReputation() { return this.reputation; }

    public void setTeams(List<String> teams) { this.teams = teams; }

    private Conference getConferenceType(String name) {
        switch (name) {
            case "Los Angeles FC":
            case "Seattle Sounders":
            case "Houston Dynamo":
            case "LA Galaxy FC":
            case "Austin FC":
            case "St. Louis City":
            case "Dallas FC":
            case "Colorado Rapids":
            case "Sporting Kansas City":
            case "San Jose Earthquakes":
            case "San Diego FC":
            case "Real Salt Lake":
            case "Minnesota United":
            case "Portland Timbers":
            case "Vancouver Whitecaps FC":
                return Conference.WEST;
            case "Columbus Crew":
            case "Inter Miami CF":
            case "Orlando City FC":
            case "FC Cincinnati":
            case "Philadelphia Union":
            case "Charlotte FC":
            case "New York City FC":
            case "New York Red Bulls":
            case "Atlanta United":
            case "CF Montreal Impact":
            case "DC United":
            case "Nashville SC":
            case "New England Revolution":
            case "Toronto FC":
            case "Chicago Fire":
                return Conference.EAST;
        }
        return Conference.EAST;
    }

    private List<String> getConferenceTeams(Conference conf) {
        return confTeams.get(conf);
    }

    private String getConfName(Conference conf) {
        if (conf == Conference.WEST) return "Western Conference";
        else return "Eastern Conference";
    }

    private Conference getConference(String id) {
        if (confTeams.get(Conference.EAST).contains(id))
            return Conference.EAST;
        else if (confTeams.get(Conference.WEST).contains(id))
            return Conference.WEST;
        else
            return Conference.WEST;
    }

    private void setSchedule() {
        List<Match> matches = new ArrayList<>();
        matches.addAll(FixtureScheduler.scheduleFixtures(getConferenceTeams(Conference.EAST), false));
        matches.addAll(FixtureScheduler.scheduleFixtures(getConferenceTeams(Conference.WEST), false));
        matches.addAll(scheduleInterConfMatches());
        this.fixtures = matches;
    }

    // region INTER-CONFERENCE MATCHES
    private Map<String, List<String>> oppList = new HashMap<>();
    private int INTER_CONFERENCE_MATCHES = 6;

    private List<Match> scheduleInterConfMatches() {
        for (String x: getTeams()) oppList.put(x, new ArrayList<>());
        int matchday = NUM_TEAMS;
        List<Match> m = new ArrayList<>();
        for (int i = 0; i < INTER_CONFERENCE_MATCHES; i += 2) {
            List<String[]> x1 = drawMatches(getConferenceTeams(Conference.EAST), getConferenceTeams(Conference.WEST));
            List<String[]> x2 = drawMatches(getConferenceTeams(Conference.EAST), getConferenceTeams(Conference.WEST));
            List<List<String[]>> x = orderPots(x1, x2);
            for (List<String[]> s : x) {
                matchday++;
                for (String[] a : s) {
                    Match match = new Match();
                    match.home_team = Sim.getClub(a[0]).getName();
                    match.away_team = Sim.getClub(a[1]).getName();
                    match.home_id = a[0];
                    match.away_id = a[1];
                    match.matchday = matchday;
                    m.add(match);
                }
            }
        }
        return m;
    }

    private List<String[]> drawMatches(List<String> east, List<String> west) {
        int size = east.size();
        List<String[]> matches = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            List<String> options = new ArrayList<>();
            String team = null;
            int count = Integer.MAX_VALUE;
            for (String x : east) {
                List<String> o = new ArrayList<>();
                for (String y : west) {
                    if (!oppList.get(x).contains(y))
                        o.add(y);
                }
                //System.out.println(x + ": " + o + " (" + o.size() + ")");
                if (count > o.size()) {
                    options = o;
                    count = o.size();
                    team = x;
                }
            }
            for (String x : west) {
                List<String> o = new ArrayList<>();
                for (String y : east) {
                    if (!oppList.get(x).contains(y))
                        o.add(y);
                }
                
                //System.out.println(x + ": " + o + " (" + o.size() + ")");
                if (count > o.size()) {
                    options = o;
                    count = o.size();
                    team = x;
                }
            }
            //System.out.println("MIN: " + team + ": " + options + " " + options.size());
            String opp = CommonFunctions.getRandomElement(options);
            //System.out.println(team + " v " + opp);
            matches.add(new String[] { team, opp });
            if (east.contains(team))
                east = CommonFunctions.removeElement(team, east);
            else if (west.contains(team))
                west = CommonFunctions.removeElement(team, west);

            if (east.contains(opp))
                east = CommonFunctions.removeElement(opp, east);
            else if (west.contains(opp))
                west = CommonFunctions.removeElement(opp, west);

            oppList.get(team).add(opp);
            oppList.get(opp).add(team);
        }
        return matches;
    }

    private List<List<String[]>> orderPots(List<String[]> matches1, List<String[]> matches2) {
        Map<String, Integer> homeGames = new HashMap<>(), awayGames = new HashMap<>();
        for (String[] pair : matches1) {
            for (String p : pair) {
                homeGames.put(p, 0);
                awayGames.put(p, 0);
            }
        }
        List<String[]> matches = new ArrayList<>();
        matches.addAll(matches1);
        matches.addAll(matches2);
        if (recursePairs(matches, homeGames, awayGames, 0)) {
            List<List<String[]>> m = new ArrayList<>();
            for (int i = 0; i < 2; i++)
                m.add(new ArrayList<>());
            for (int i = 0; i < matches.size(); i++) {
                m.get(i < matches.size() / 2 ? 0 : 1).add(matches.get(i));
            }
            return m;
        } else {
            System.out.println("No SOLUTION ");
            return new ArrayList<>();
        }
    }

    private boolean recursePairs(List<String[]> matches, Map<String, Integer> home, Map<String, Integer> away,
            int index) {
        if (index == matches.size())
            return true;
        String hTeam = matches.get(index)[0];
        String aTeam = matches.get(index)[1];

        if (home.get(hTeam) < 1 && away.get(aTeam) < 1) {
            home.put(hTeam, home.get(hTeam) + 1);
            away.put(aTeam, away.get(aTeam) + 1);
            if (recursePairs(matches, home, away, index + 1))
                return true;
            home.put(hTeam, home.get(hTeam) - 1);
            away.put(aTeam, away.get(aTeam) - 1);
        }

        if (home.get(aTeam) < 1 && away.get(hTeam) < 1) {
            matches.set(index, new String[] { aTeam, hTeam });
            home.put(aTeam, home.get(aTeam) + 1);
            away.put(hTeam, away.get(hTeam) + 1);
            if (recursePairs(matches, home, away, index + 1))
                return true;
            home.put(aTeam, home.get(aTeam) - 1);
            away.put(hTeam, away.get(hTeam) - 1);
            matches.set(index, new String[] { hTeam, aTeam });
        }
        return false;
    }
    // endregion INTER-CONFERENCE MATCHES

    public void setupTable() {
        this.aggregateTable = new Table(this.getTeams().size(), false);
        this.aggregateTable.startNewSeason(this.getTeams());
        this.confTable = new HashMap<>();
        this.confTable.put(Conference.EAST, new Table(this.getConferenceTeams(Conference.EAST).size(), false));
        this.confTable.get(Conference.EAST).startNewSeason(getConferenceTeams(Conference.EAST));
        this.confTable.put(Conference.WEST, new Table(this.getConferenceTeams(Conference.WEST).size(), false));
        this.confTable.get(Conference.WEST).startNewSeason(getConferenceTeams(Conference.WEST));
    }

    public void startSeason() {
        this.initMajorLeague();
        this.setSchedule();
        this.setupTable();
    }

    public void simulateMatchDay(int matchday) {
        List<Match> matches = new ArrayList<>();
        for (Match match : fixtures) {
            if (match.matchday == matchday) {
                matches.add(match);
            }
        }
        for (Match match : matches) {
            match.simulate();
            if (!Sim.TEST) {
                match.display();
            }
            if (getConference(match.home_id) == Conference.EAST) {
                confTable.get(Conference.EAST).updateTeamPerformance(match.home_id, match.home_team_goals,
                        match.away_team_goals);
            } else if (getConference(match.home_id) == Conference.WEST) {
                confTable.get(Conference.WEST).updateTeamPerformance(match.home_id, match.home_team_goals,
                        match.away_team_goals);
            }
            if (getConference(match.away_id) == Conference.EAST) {
                confTable.get(Conference.EAST).updateTeamPerformance(match.away_id, match.away_team_goals,
                        match.home_team_goals);
            } else if (getConference(match.away_id) == Conference.WEST) {
                confTable.get(Conference.WEST).updateTeamPerformance(match.away_id, match.away_team_goals,
                        match.home_team_goals);
            }
            this.aggregateTable.updateTeamPerformance(match.home_id, match.home_team_goals, match.away_team_goals);
            this.aggregateTable.updateTeamPerformance(match.away_id, match.away_team_goals, match.home_team_goals);
        }
    }

    private void setupConferencePlayoff(Conference conf) {
        List<String[]> pairs = new ArrayList<>();
        String bye = "bye";
        pairs.add(new String[] { confTable.get(conf).getTeamAtRank(1), bye });
        pairs.add(new String[] { confTable.get(conf).getTeamAtRank(8), confTable.get(conf).getTeamAtRank(9) });
        pairs.add(new String[] { confTable.get(conf).getTeamAtRank(4), bye });
        pairs.add(new String[] { confTable.get(conf).getTeamAtRank(5), bye });
        pairs.add(new String[] { confTable.get(conf).getTeamAtRank(3), bye });
        pairs.add(new String[] { confTable.get(conf).getTeamAtRank(6), bye });
        pairs.add(new String[] { confTable.get(conf).getTeamAtRank(2), bye });
        pairs.add(new String[] { confTable.get(conf).getTeamAtRank(7), bye });
        String[] stage_names = { getConfName(conf) + " Wildcard", getConfName(conf) + " Round One", getConfName(conf) + " Semifinals", getConfName(conf) + " Final"};
        KnockoutTree t = new KnockoutTree();
        t.setup(pairs, Arrays.asList(stage_names));
        tree.put(conf, t);
    }

    private void setupMLS_Cup() {
        String east = tree.get(Conference.EAST).getWinner();
        String west = tree.get(Conference.WEST).getWinner();
        if (aggregateTable.getRankOfTeam(east) < aggregateTable.getRankOfTeam(west)) {
            cup_final = new SingleKnockoutTie(east, west);
        } else {
            cup_final = new SingleKnockoutTie(west, east);
        }
    }

    private void simulateMLS_Cup() {
        cup_final.simulate();
        cup_final.show();
    }

    public void simulate() {
        for (int i = 1; i <= MATCHDAYS; i++) {
            System.out.println("Matchday " + i);
            simulateMatchDay(i);
        }
        System.out.println("\nMajor League Soccer Eastern Conference");
        this.confTable.get(Conference.EAST).showLong();

        System.out.println("\nMajor League Soccer Western Conference");
        this.confTable.get(Conference.WEST).showLong();
        System.out.println("\nMajor League Soccer Aggregate Table");
        this.aggregateTable.showLong();
        tree = new HashMap<>();
        System.out.println();
        setupConferencePlayoff(Conference.EAST);
        setupConferencePlayoff(Conference.WEST);
        tree.get(Conference.EAST).simulate();
        tree.get(Conference.WEST).simulate();
        setupMLS_Cup();
        simulateMLS_Cup();
        System.out.println(cup_final.getWinner());
    }

    public void endSeason() {
        int reputation_steps = (this.getReputation() / NUM_TEAMS) * 10;
        List<String> sorted_teams = CommonFunctions.sortByReputation(this.getTeams());
        long prize_money = this.getReputation() * 50000 * (long)(Math.pow(1.01, (this.getReputation() - 200)));
        for (int i = 0; i < this.getTeams().size(); i++) {
            String team = sorted_teams.get(i);
            int rank = this.aggregateTable.getRankOfTeam(team);
            int diff = rank - (i + 1);
            if (diff < 3 || diff > -3) {
                continue;
            } else if (diff > 0) {
                Sim.getClub(team).addReputation(reputation_steps * (diff - 3) * -1);
            } else {
                Sim.getClub(team).addReputation(reputation_steps * ((diff * -1) + 3));
            }
            Sim.getClub(aggregateTable.getTeamAtRank(i)).updateBudget((long) (prize_money / NUM_TEAMS));
        }
    }

    public List<String> getTeams() {
        return this.teams;
    }

    public boolean isTeamInLeague(String team_id) {
        return getTeams().contains(team_id);
    }

    public List<String> getPromotedTeams() {
        return new ArrayList<>();
    }

    public List<String> getRelegatedTeams() {
        return new ArrayList<>();
    }

    public void showCompetitionHistory() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showCompetitionHistory'");
    }

    @Override
    public String getTeamAtRank(int rank) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTeamAtRank'");
    }
}