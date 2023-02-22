public enum NationEnum {
    ENGLAND("England", "ENG", 135, 80, 20, 4, "Europe", "Britain"),
    SPAIN("Spain", "ESP", 153, 80, 20, 3, "Europe", "Europe South"),
    FRANCE("France", "FRA", 163, 80, 18, 2, "Europe", "Europe West"),
    ITALY("Italy", "ITA", 140, 80, 20, 3, "Europe", "Europe South"),
    GERMANY("Germany", "GER", 155, 80, 18, 3, "Europe", "Europe West");

    private final String name;
    private final int youth_rating;
    private final String code;
    private final int league_strength;
    private final int pyramid_size;
    private final int league_size;
    private final String continent;
    private final String region;

    NationEnum(String name, String code, int youth_rating, int league_strength, int league_size, int pyramid_size, String continent,
            String region) {
        this.name = name;
        this.youth_rating = youth_rating;
        this.code = code;
        this.league_strength = league_strength;
        this.pyramid_size = pyramid_size;
        this.continent = continent;
        this.region = region;
        this.league_size = league_size;
    }

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }

    public int getYouthRating() {
        return this.youth_rating;
    }

    public int getLeagueStrength() {
        return this.league_strength;
    }

    public int getPyramidSize() {
        return this.pyramid_size;
    }

    public String getContinent() {
        return this.continent;
    }

    public String getRegion() {
        return this.region;
    }

    public int getLeagueSize() {
        return this.league_size;
    }
}
