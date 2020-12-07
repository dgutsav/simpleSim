public class CommonFunctions {
    private CommonFunctions() {
        
    }
    public static Team getTeam(float rating, String nation_name) {
        return new Team(CommonFunctions.getTeamNameForCountry(nation_name), rating);
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

    public static int[] getDistribution(int size, int average, int variation) {
        int starting_value, starting_value2;
        int[] array = new int[size];
        for (int i = 0; i < array.length; i++) {
            array[i] = average;
        }
        if (variation == 1) {
            // single horse with n-nearby
            starting_value = CommonFunctions.getRandomNumber((int) (0.1f * average)) + 6;
            for (int i = 0; i < size; i++) {
                if (i < size / 5) {
                    array[i] += starting_value;
                    starting_value /= 2;
                } else {
                    array[i] += CommonFunctions.getRandomInt(1);
                }
            }
        } else if (variation == 2) {
            // two-horse with m-nearby
            starting_value = CommonFunctions.getRandomNumber((int) (0.15f * average)) + 6;
            starting_value2 = CommonFunctions.getRandomNumber((int) (0.12f * average)) + 6;
            for (int i = 0; i < size; i++) {
                if (i < size / 2) {
                    if (i % 2 == 0) {
                        array[i] += starting_value;
                        starting_value /= 2;
                    } else {
                        array[i] += starting_value2;
                        starting_value2 /= 2;
                    }
                } else {
                    array[i] += CommonFunctions.getRandomInt(1);
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                array[i] += CommonFunctions.getRandomInt(1);
            }
        }
        return array;
    }

    public static League createLeague(int league_rating, int size, int variance, String nation_name) {
        int[] team_ratings = CommonFunctions.getDistribution(size, league_rating, variance);
        League league = getLeague();
        for (int i = 0; i < team_ratings.length; i++) {
            Team team = getTeam((float) team_ratings[i], nation_name);
            String team_id = Sim.addTeam(team);
            league.addTeam(team_id);
        }
        return league;
    }

    public static String getTeamNameForCountry(String nation_name) {
        int year = CommonFunctions.getRandomNumber(100) + 1863;
        String addedendum = String.valueOf(year);
        String[] prefix = { "FC", "SC", "FK", "RB", "AFC", "Union", "", "PFC", "NK", "AC", "SG", "VfB", "Real", "RC" };
        String[] suffix = { addedendum, "United", "City", "FC", "Town", "Lions", "Eagles" };
        String team_name = Cities.getCity(nation_name);
        if (CommonFunctions.getRandomNumber(2) == 0) {
            team_name = prefix[CommonFunctions.getRandomNumber(prefix.length)] + " " + team_name;
        } else {
            team_name += " " + suffix[CommonFunctions.getRandomNumber(suffix.length)];
        }
        return team_name.trim();
    }

    public static int[] shuffle(int[] array) {
        int r;
        int temp;
        int[] copy = array;
        for (int i = 0; i < array.length; i++) {
            r = (int) (Math.random() * (array.length - i));
            temp = copy[r];
            copy[r] = copy[array.length - i - 1];
            copy[array.length - i - 1] = temp;
        }
        return copy;
    }

    public static String[] shuffle(String[] array) {
        int r;
        String temp;
        String[] copy = array;
        for (int i = 0; i < array.length; i++) {
            r = (int) (Math.random() * (array.length - i));
            temp = copy[r];
            copy[r] = copy[array.length - i - 1];
            copy[array.length - i - 1] = temp;
        }
        return copy;
    }
}
