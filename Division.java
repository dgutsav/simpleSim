import java.util.List;

interface Division {
    public void setId(String id);

    public String getId();

    public String getName();

    public int getLevel();
    
    public int getPromotionPlaces();
    
    public int getRelegationPlaces();

    public int getNation();

    public int getReputation();

    public void setTeams(List<String> teams);

    public void startSeason();

    public void simulate();

    public List<String> getTeams();

    public boolean isTeamInLeague(String team_id);

    public List<String> getPromotedTeams();

    public List<String> getRelegatedTeams();

    public void showCompetitionHistory();

    public void endSeason();

    public String getTeamAtRank(int rank);
}