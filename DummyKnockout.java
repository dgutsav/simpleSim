class DummyKnockout implements KnockoutTie {
    private String team;

    public DummyKnockout(String t) {
        this.team = t;
    }

    public void simulate() {
    }

    public void setWinner() {
    }

    public int getTeam1Goals() {
        return 0;
    }

    public int getTeam2Goals() {
        return 0;
    }

    public String getWinner() {
        return team;
    }

    public String getLoser() {
        return null;
    }

    public String getTeam1() {
        return team;
    }

    public String getTeam2() {
        return null;
    }

    public void show() {
    }

    public void showContinental() {
    }

    public StageType getType() {
        return StageType.KNOCKOUT;
    }
}
