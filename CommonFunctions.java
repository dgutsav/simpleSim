import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Models.Player;
import Models.Position;
import Models.PositionHelper;
import Functions.CatalogueHelper;

public class CommonFunctions {
    private CommonFunctions() {

    }

    public static Club_Team getTeam(float rating, String nation_name, int nation_id, float league_rating) {
        int reputation = Math.round((rating + 10) * (league_rating + 10));
        Club_Team team = new Club_Team(CommonFunctions.getTeamNameForCountry(nation_name), nation_id, reputation);
        int[] ratings = CommonFunctions.getPlayerDistribution((int) rating);
        Position[] positions = CommonFunctions.getPositions(team.getSeniorSquad().getPositions(), ratings.length);
        for (int i = 0; i < ratings.length; i++) {
            int age;
            if (ratings[i] >= 86) {
                age = CommonFunctions.getRandomNumber(10) + 28;
            } else if (ratings[i] >= 84) {
                age = CommonFunctions.getRandomNumber(12) + 26;
            } else if (ratings[i] >= 82) {
                age = CommonFunctions.getRandomNumber(14) + 24;
            } else if (ratings[i] >= 80) {
                age = CommonFunctions.getRandomNumber(16) + 22;
            } else if (ratings[i] >= 78) {
                age = CommonFunctions.getRandomNumber(18) + 21;
            } else if (ratings[i] >= 75) {
                age = CommonFunctions.getRandomNumber(20) + 20;
            } else if (ratings[i] >= 70) {
                age = CommonFunctions.getRandomNumber(18) + 18;
            } else {
                age = CommonFunctions.getRandomNumber(20) + 17;
            }
            Player player = CommonFunctions.getPlayer(nation_name, nation_id, ratings[i], age, positions[i]);
            String player_id = Sim.addPlayer(player);
            team.getSeniorSquad().addPlayer(player_id);
        }
        return team;
    }

    public static Position[] getPositions(Position[] formation, int length) {
        Position[] positions = new Position[length];
        for (int i = 0; i < formation.length; i++) {
            positions[i] = formation[i];
        }
        List<Position> all_positions = PositionHelper.getAllPositions();
        int count = 0;
        for (Position position : all_positions) {
            positions[11 + count] = position;
            count++;
        }
        int assigned = formation.length + all_positions.size();
        for (int i = 0; i < length - assigned; i++) {
            positions[assigned + i] = all_positions.get(CommonFunctions.getRandomNumber(all_positions.size()));
        }
        return positions;
    }

    public static League getLeague(String nation_name, int division, int promotion, int relegation, int nation_id,
            int rating) {
        String name = CatalogueHelper.getDenonym(nation_name) + " League " + division;
        return new League(name, division, promotion, relegation, nation_id, rating);
    }

    public static Player getPlayer(String nation_name, int nation_id, int rating, int age, Position position) {
        String name = CatalogueHelper.getPlayerName(nation_name);
        return new Player(name, position, age, nation_id, rating, 0);
    }

    public static int[] getPlayerDistribution(int rating) {
        int totalPlayers = 25 + CommonFunctions.getRandomNumber(5);
        int[] ratings = new int[totalPlayers];
        for (int i = 0; i < totalPlayers; i++) {
            if (i < 11) {
                ratings[i] = rating + CommonFunctions.getRandomInt(3);
            } else if (i < 20){
                ratings[i] = ((int) ((double) rating * 0.75)) + CommonFunctions.getRandomNumber(5);
            } else {
                ratings[i] = ((int) ((double) rating * 0.6)) + CommonFunctions.getRandomNumber(7);
            }
            ratings[i] = Player.adjustRating(ratings[i]);
        }
        return ratings;
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
            starting_value = CommonFunctions.getRandomNumber((int) (0.1f * average)) + 4;
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
            starting_value = CommonFunctions.getRandomNumber((int) (0.15f * average)) + 4;
            starting_value2 = CommonFunctions.getRandomNumber((int) (0.12f * average)) + 4;
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

    public static League createLeague(int league_rating, int size, int nation_id, int level, int promotion,
            int relegation, String nation_name) {
        int variance = 0;
        if (level == 1) {
            variance = CommonFunctions.getRandomNumber(2);
        } else {
            variance = CommonFunctions.getRandomNumber(3);
        }
        int[] team_ratings = CommonFunctions.getDistribution(size, league_rating, variance);
        League league = getLeague(nation_name, level, promotion, relegation, nation_id, league_rating);
        for (int i = 0; i < team_ratings.length; i++) {
            Club_Team team = getTeam((float) team_ratings[i], nation_name, nation_id, league_rating);
            String team_id = Sim.addClub(team);
            league.addTeam(team_id);
        }
        return league;
    }

    public static String getTeamNameForCountry(String nation_name) {
        int year = CommonFunctions.getRandomNumber(110) + 1890;
        String addedendum = String.valueOf(year);
        String team_name = CatalogueHelper.getCity(nation_name);
        if (CommonFunctions.getRandomNumber(100) < CatalogueHelper.getPrefixChance(nation_name)) {
            team_name = CatalogueHelper.getPrefix(nation_name) + " " + team_name;
            if (CommonFunctions.getRandomNumber(100) > 90) {
                team_name += " " + addedendum;
            }
        } else {
            team_name = team_name + " " + CatalogueHelper.getSuffix(nation_name);
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

    public static String getRandomElement(List<String> list) {
        return list.get(getRandomNumber(list.size()));
    }

    public static int getRandomIntElement(List<Integer> list) {
        return list.get(getRandomNumber(list.size()));
    }

    public static String getRandomElement(String[] list) {
        return list[getRandomNumber(list.length)];
    }

    public static void display2DArray(String[][] array, int a, int b) {
        System.out.println();
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {
                System.out.print(array[i][j] + "   ");
            }
            System.out.println();
        }
    }

    public static List<Club_Team> getTeams(List<String> teams_to_find) {
        List<Club_Team> teams = new ArrayList<>();
        for (String team_id : teams_to_find) {
            teams.add(Sim.getClub(team_id));
        }
        return teams;
    }

    public static List<String> sortByCoefficients(List<String> teams_to_sort) {
        List<String> sorted = new ArrayList<>();
        List<Club_Team> teams = CommonFunctions.getTeams(teams_to_sort);
        Collections.sort(teams, (Club_Team a, Club_Team b) -> (int) (b.getCoefficient() - a.getCoefficient()));
        for (Club_Team team : teams) {
            sorted.add(team.getId());
        }
        return sorted;
    }

    public static List<String> sortByReputation(List<String> teams_to_sort) {
        List<String> sorted = new ArrayList<>();
        List<Club_Team> teams = CommonFunctions.getTeams(teams_to_sort);
        Collections.sort(teams, (Club_Team a, Club_Team b) -> (int) (b.getReputation() - a.getReputation()));
        for (Club_Team team : teams) {
            sorted.add(team.getId());
        }
        return sorted;
    }

    public static String formatMoney(long amount) {
        String money = String.valueOf(amount);
        List<Integer> parts = new ArrayList<>();
        while (amount > 0) {
            int part = (int) (amount % 1000);
            parts.add(0, part);
            amount /= 1000;
        }
        money = "";
        for (int i : parts) {
            String temp = String.valueOf(i);
            if (temp.length() != 3) {
                for (int x = temp.length(); x < 3; x++) {
                    temp = "0" + temp;
                }
            }
            money = money + "," + temp;
        }
        while (true) {
            if (money.charAt(0) == ',' || money.charAt(0) == '0') {
                money = money.substring(1);
            } else {
                break;
            }
        }
        return money;
    }

    public static String getPaddedString(String x, int len) {
        String padded = x;
        for (int i = padded.length(); i < len; i++) {
            padded += " ";
        }
        return padded;

    }
}
