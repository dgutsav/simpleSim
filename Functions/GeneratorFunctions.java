package Functions;

import Models.Player;
import Models.Position;
import Models.PositionHelper;
import Models.Nation;
import Models.Club;
import Models.Competition;

import java.util.ArrayList;
import java.util.List;

public class GeneratorFunctions {
    public static String getRandomId() {
        String id = "";
        long millis = System.nanoTime();
        while (millis != 0) {
            id = ((char) ((millis % 26) + 97)) + id;
            millis /= 26;
        }
        return id;
    }

    public static Player getPlayer(Nation nation, int rating, int age, Position position) {
        String name = CatalogueHelper.getPlayerName(nation.getName());
        return new Player(name, position, age, nation.getId(), rating, 0);
    }

    public static Club getClub(Nation nation, int rating, int league_rating) {
        String name = CatalogueHelper.getClubName(nation.getName());
        int starting_reputation = rating * league_rating;
        Club club = new Club(name, nation.getId(), starting_reputation);
        int[] ratings = GeneratorFunctions.getPlayerDistribution((int) rating);
        Position[] positions = GeneratorFunctions.getPositions(club.getSeniorSquad().getPositions(), ratings.length);
        for (int i = 0; i < ratings.length; i++) {
            int age = GeneratorFunctions.getAgeByRating(ratings[i]);
            Player player = GeneratorFunctions.getPlayer(nation, ratings[i], age, positions[i]);
            String player_id = SimBettered.addPlayer(player);
            club.getSeniorSquad().addPlayer(player_id);
        }
        return club;
    }

    public static Position[] getPositions(Position[] formation, int length) {
        Position[] positions = new Position[length];
        for (int i = 0; i < formation.length; i++) {
            positions[i] = formation[i];
        }
        List<Position> all_positions = PositionHelper.getAllPositions();
        all_positions.addAll(PositionHelper.getAllPositions());
        int count = 0;
        for (Position position : all_positions) {
            positions[11 + count] = position;
            count++;
        }
        int left = length - 34;
        for (int i = 0; i < left; i++) {
            positions[34 + i] = all_positions.get(CommonFunctions.getRandomNumber(all_positions.size()));
        }
        return positions;
    }

    public static int[] getPlayerDistribution(int rating) {
        int totalPlayers = 35 + CommonFunctions.getRandomNumber(10);
        int[] ratings = new int[totalPlayers];
        for (int i = 0; i < totalPlayers; i++) {
            if (i < 11) {
                ratings[i] = rating + CommonFunctions.getRandomInt(5);
            } else {
                ratings[i] = ((int) ((double) rating * 0.75)) + CommonFunctions.getRandomNumber(5);
            }
            ratings[i] = Player.adjustRating(ratings[i]);
        }
        return ratings;
    }

    public static int getAgeByRating(int rating) {
        int age = 25;
        if (rating >= 90) {
            age = CommonFunctions.getRandomNumber(7) + 30;
        } else if (rating >= 86) {
            age = CommonFunctions.getRandomNumber(10) + 28;
        } else if (rating >= 84) {
            age = CommonFunctions.getRandomNumber(12) + 26;
        } else if (rating >= 82) {
            age = CommonFunctions.getRandomNumber(14) + 24;
        } else if (rating >= 80) {
            age = CommonFunctions.getRandomNumber(16) + 22;
        } else if (rating >= 78) {
            age = CommonFunctions.getRandomNumber(18) + 21;
        } else if (rating >= 75) {
            age = CommonFunctions.getRandomNumber(20) + 20;
        } else if (rating >= 70) {
            age = CommonFunctions.getRandomNumber(18) + 18;
        } else {
            age = CommonFunctions.getRandomNumber(20) + 17;
        }
        return age;
    }

    public static int getChangePlaces(int size) {
        if (size > 16) {
            return 3;
        } else if (size > 10) {
            return 2;
        } else {
            return 1;
        }
    }

    public static Competition getDomesticLeague(Nation nation, int level, int rating, int size) {
        int[] distribution;
        if (level == 1) {
            distribution = getTeamsDistribution(size, rating, 1);
        } else {
            distribution = getTeamsDistribution(size, rating, CommonFunctions.getRandomRange(1, 2));
        }
        Competition comp = Competition.createDomesticLeague((rating + 10) * 2, level);
        List<String> teams_id = new ArrayList<>();
        for (int i = 0; i < distribution.length; i++) {
            Club team = getClub(nation, distribution[i], rating);
            teams_id.add(SimBettered.addClub(team));
        }
        comp.setup(teams_id);
        return comp;
    }

    public static int getDecrease() {
        int r = CommonFunctions.getRandomNumber(100);
        if (r > 90) {
            return 2;
        } else if (r > 60) {
            return 1;
        } else {
            return 0;
        }
    }

    public static int[] getStrongVariation(int[] distribution, int majors) {
        int[] diffs = new int[majors];
        for (int i = 0; i < majors; i++) {
            diffs[i] = CommonFunctions.getRandomRange(5, 13);
        }
        for (int i = 0; i < distribution.length; i++) {
            distribution[i] += diffs[i % majors];
            diffs[i % majors] -= getDecrease();
        }
        return distribution;
    }

    public static int[] getStandardVariation(int[] distribution, int variation) {
        for (int i = 0; i < distribution.length; i++) {
            distribution[i] += CommonFunctions.getRandomInt(variation);
        }
        return distribution;
    }

    public static int[] getTeamsDistribution(int size, int rating, int type) {
        int[] distribution = CommonFunctions.getArrayWithValue(size, rating);
        if (type == 1) {
            distribution = getStrongVariation(distribution, CommonFunctions.getRandomRange(1, 4));
        } else if (type == 2) {
            distribution = getStandardVariation(distribution, CommonFunctions.getRandomRange(2, 5));
        }
        return distribution;
    }

    public static Nation getNation(String nation_name, int id) {
        Nation nation = new Nation(id, nation_name, SetupHelper.getCode(nation_name));
        nation.setLeagueRanking(SetupHelper.getLeagueRanking(nation_name));
        int rating = SetupHelper.getLeagueRating(nation_name);
        if (i > 1) {
            double multiplier = (Math.pow(0.9, (double) (i - 1))) * setup.league_rating;
            rating = (int) (multiplier);
        }
        return nation;
    }
}
