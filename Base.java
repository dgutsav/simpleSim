public class Base {
    public static Team getTeam(float rating, String nation_name) {
        return new Team(Base.getTeamNameForCountry(nation_name), rating);
    }

    public static League getLeague() {
        return new League(getRandomName("league"), 1, 0, 0);
    }

    public static String getRandomName(String type) {
        int size = (int) (Math.random() * 10) + 6;
        String name = "";
        char ch;
        for (int i = 0; i < size; i++) {
            ch = (char) ((Math.random() * 26) + 97);
            name += ch;
        }
        return name + " " + type;
    }

    public static String getRandomId() {
        String id = "";
        long millis = System.nanoTime();
        while (millis != 0) {
            id = ((char) ((millis % 26) + 97)) + id;
            millis /= 26;
        }
        return id;
    }

    public static int getRandomInt(int range) {
        // -range to +range
        int rand = (int) (Math.random() * ((range * 2) + 1));
        rand -= range;
        return rand;
    }

    public static int getRandomNumber(int range) {
        return (int) (Math.random() * range);
    }

    public static League createLeague(int league_rating, int size, int variance, String nation_name) {
        int[] team_ratings = new int[size];
        int sum = 0;
        for (int i = 0; i < size; i++) {
            team_ratings[i] = league_rating + getRandomInt(variance);
            System.out.println(team_ratings[i]);
            sum += team_ratings[i];
        }

        System.out.println(sum + "  " + sum / size);

        League league = getLeague();
        for (int i = 0; i < team_ratings.length; i++) {
            Team team = getTeam((float) team_ratings[i], nation_name);
            String team_id = Sim.addTeam(team);
            league.addTeam(team_id);
        }

        return league;
    }

    public static String getTeamNameForCountry(String nation_name) {
        String team_name = Cities.getCity(nation_name);
        int year = Base.getRandomNumber(100);
        String addedendum = year >= 10 ? year+"" : "0" + year;
        return team_name + " " + addedendum;
    }

    public static void main(String args[]) {
        for (int i = 0; i < 1; i++) {
            // createLeague(80,16,5);
        }
    }
}
