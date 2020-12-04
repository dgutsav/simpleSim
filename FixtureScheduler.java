import java.util.ArrayList;
import java.util.List;

public class FixtureScheduler {
    public static List<Match> scheduleFixtures(List<String> teams_list) {
        Team[] teams = Sim.getTeamArray(teams_list);
        List<Match> matches = new ArrayList<>();
        List<Integer[]> generate = generateMatches(teams.length);
        for(Integer[] array : generate) {
            Match match = new Match();
            match.home_team = teams[array[0]].name;
            match.away_team = teams[array[1]].name;
            match.home_id = teams[array[0]].id;
            match.away_id = teams[array[1]].id;
            match.matchday = array[2];
            matches.add(match);
        }
        return matches;
    }

    public static int[] shuffle (int[] array) {
        int r; 
        int temp;
        int[] copy = array;
        for(int i=0;i<array.length;i++) {
            r = (int)(Math.random()*(array.length - i));
            temp = copy[r];
            copy[r] = copy[array.length - i - 1];
            copy[array.length - i - 1] = temp;
        }
        return copy;
    }

    public static List<Integer[]> generateMatches (int count) {
        List<Integer[]> gen = new ArrayList<>();
        
        int[] ids = new int[count];
        for(int i= 0; i<count; i++) {
            ids[i] = i;
        }
        ids = shuffle(ids);
        int[] home = new int [count/2];
        int[] away = new int [count/2];
        for(int i = 0; i<count/2;i++){
            home[i] = ids[i];
            away[i] = ids[i+(count/2)];
        }

        int[] away_days = new int[count-1];
        for(int i=0; i<count-1; i++) {
            away_days[i] = count + i;
        }

        for(int i = 1; i<=(count-1);i++) {  
            //select away day
            int away_index = (int)(Math.random()*(away_days.length - i + 1));
            int away_day = away_days[away_index];
            away_days[away_index] = away_days[away_days.length - i];
            away_days[away_days.length - i] = away_day;

            for(int j=0;j<home.length;j++) {
                Integer[] arr_home = new Integer[3];
                Integer[] arr_away = new Integer[3];
                if (i%2 == 0) {
                    arr_home[0] = Integer.valueOf(home[j]);
                    arr_home[1] = Integer.valueOf(away[j]);
                    arr_away[0] = Integer.valueOf(away[j]);
                    arr_away[1] = Integer.valueOf(home[j]);
                } else {
                    arr_home[0] = Integer.valueOf(away[j]);
                    arr_home[1] = Integer.valueOf(home[j]);
                    arr_away[0] = Integer.valueOf(home[j]);
                    arr_away[1] = Integer.valueOf(away[j]);
                }
                arr_home[2] = Integer.valueOf(i);
                arr_away[2] = Integer.valueOf(away_day);
                gen.add(arr_home);
                gen.add(arr_away);
            }
            //swaps
            int add_to_home = away[0];
            int add_to_away = home[home.length - 1];
            for(int j = home.length - 1; j>1;j--)
                home[j] = home[j-1];
            home[1] = add_to_home;
            for(int j=0;j<away.length-1;j++)
                away[j] = away[j+1];
            away[away.length - 1] = add_to_away;
        }
        return gen;
    }

    public static void main(String args[]) {
        generateMatches(20);
    }

}
