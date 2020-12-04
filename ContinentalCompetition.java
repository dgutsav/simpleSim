public class ContinentalCompetition {

    public static int getPlacesForCompetition(int competition_level, int league_ranking) {
        switch(competition_level) {
            case 1:
            if(league_ranking <= 3) {
                return 4;
            } else if (league_ranking <= 5) {
                return 3;
            } else if (league_ranking <= 9) {
                return 2;
            } else {
                return 1;
            }
            case 2:
            if(league_ranking <= 5) {
                return 3;
            } else if (league_ranking <= 8) {
                return 4;
            } else {
                return 3;
            }
            case 3:
            if(league_ranking <= 11) {
                return 3;
            } else {
                return 2;
            }
        }
    }
}