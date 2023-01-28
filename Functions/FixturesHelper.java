package Functions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import Models.CompetitionType;
import Models.Match;

public class FixturesHelper {
    public static List<Match> scheduleMatches(List<String> teams, CompetitionType type) {
        List<Match> matches = new ArrayList<>();
        int total_matchdays = getTotalMatchdays(teams.size(), 2);
        Calendar start = Calendar.getInstance();
        start.set(SimBettered.getSeasonNumber(), Calendar.FEBRUARY, 10);
        Calendar end = Calendar.getInstance();
        end.set(SimBettered.getSeasonNumber(), Calendar.NOVEMBER, 25);
        List<Calendar> matchdays = getMatchDates(total_matchdays, start, end);
        List<Integer[]> matches_list = generateMatches(teams.size());
        for (Integer[] array : matches_list) {
            Match match = new Match(teams.get(array[0]), teams.get(array[1]), matchdays.get(array[2] - 1));
            matches.add(match);
        }
        return FixturesHelper.sortMatches(matches);
    }

    public static int getTotalMatchdays(int teams, int rounds) {
        if (teams % 2 == 1) {
            teams += 1;
        }
        return (teams - 1) * rounds;
    }

    public static List<Integer[]> generateMatches(int count) {
        List<Integer[]> gen = new ArrayList<>();
        int[] ids = new int[count];
        for (int i = 0; i < count; i++) {
            ids[i] = i;
        }
        ids = CommonFunctions.shuffle(ids);
        int[] home = new int[count / 2];
        int[] away = new int[count / 2];
        for (int i = 0; i < count / 2; i++) {
            home[i] = ids[i];
            away[i] = ids[i + (count / 2)];
        }

        int[] away_days = new int[count - 1];
        for (int i = 0; i < count - 1; i++) {
            away_days[i] = count + i;
        }

        for (int i = 1; i <= (count - 1); i++) {
            // select away day
            int away_index = CommonFunctions.getRandomNumber(away_days.length - i);
            int away_day = away_days[away_index];
            away_days[away_index] = away_days[away_days.length - i];
            away_days[away_days.length - i] = away_day;

            for (int j = 0; j < home.length; j++) {
                Integer[] arr_home = new Integer[3];
                Integer[] arr_away = new Integer[3];
                if (i % 2 == 0) {
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
            int add_to_home = away[0];
            int add_to_away = home[home.length - 1];
            for (int j = home.length - 1; j > 1; j--) {
                home[j] = home[j - 1];
            }
            home[1] = add_to_home;
            for (int j = 0; j < away.length - 1; j++) {
                away[j] = away[j + 1];
            }
            away[away.length - 1] = add_to_away;
        }
        return gen;
    }

    public static List<Calendar> getMatchDates(int size, Calendar start, Calendar end) {
        start = CommonFunctions.getFirstDayAfterDate(start, Calendar.SATURDAY);
        end = CommonFunctions.getLastDayBeforeDate(end, Calendar.SATURDAY);
        List<Calendar> dates = new ArrayList<>();
        int days = 0;
        int start_day = start.get(Calendar.DATE);
        int start_month = start.get(Calendar.MONTH);
        int start_year = start.get(Calendar.YEAR);
        while (true) {
            Calendar date = Calendar.getInstance();
            date.set(start_year, start_month, start_day);
            date.add(Calendar.DATE, days);
            days += 7;
            if (date.after(end)) {
                break;
            }
            dates.add(date);
        }
        if (size > dates.size()) {
            int diff = size - dates.size();
            int[] dates_set = new int[(dates.size() - 2) / 2];
            for (int i = 1; i < dates.size() - 2; i += 2) {
                dates_set[(i - 1) / 2] = i;
            }
            dates_set = CommonFunctions.shuffle(dates_set);
            List<Calendar> extra_dates = new ArrayList<>();
            for (int i = 0; i < diff; i++) {
                Calendar cal = Calendar.getInstance();
                int day = dates.get(dates_set[i]).get(Calendar.DATE);
                int month = dates.get(dates_set[i]).get(Calendar.MONTH);
                int year = dates.get(dates_set[i]).get(Calendar.YEAR);
                cal.set(year, month, day);
                cal.add(Calendar.DATE, 4);
                extra_dates.add(cal);
            }
            dates.addAll(extra_dates);
        }
        return dates;
    }

    public static List<Match> setInternationalMatches(List<Match> matches) {
        for (int i = 0; i < matches.size(); i++) {
            matches.get(i).setInternationalMatch();
        }
        return matches;
    }

    public static List<Match> setYouthMatches(List<Match> matches) {
        for (int i = 0; i < matches.size(); i++) {
            matches.get(i).setYouthMatch();
        }
        return matches;
    }

    public static List<Match> setNeutralVenues(List<Match> matches) {
        for (int i = 0; i < matches.size(); i++) {
            matches.get(i).setNeutralVenue();
        }
        return matches;
    }

    public static void main(String[] args) {
        List<String> teams = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            teams.add(GeneratorFunctions.getRandomId());
        }
        List<Match> fixtures = scheduleMatches(teams, CompetitionType.DOMESTIC_LEAGUE);
        for (int i = 0; i < fixtures.size(); i += 10) {
            System.out.println(fixtures.get(i).getHomeId() + " " + fixtures.get(i).getAwayId());
        }
        System.out.println(fixtures.size());
    }

    public static List<Match> sortMatches(List<Match> matches) {
        Collections.sort(matches, (Match m1, Match m2) -> {
            if (m1.getDate().after(m2.getDate())) {
                return 1;
            } else if (m1.getDate().before(m2.getDate())) {
                return -1;
            } else {
                return 0;
            }
        });
        return matches;
    }

    public static List<int[]> getLeagueDates(int year, int num_dates) {
        int base_year = 2018;
        int[][] league_dates = { { 4, 8, 2018 }, { 11, 8, 2018 }, { 18, 8, 2018 }, { 22, 8, 2018 }, { 25, 8, 2018 },
                { 1, 9, 2018 }, { 15, 9, 2018 }, { 18, 9, 2018 }, { 22, 9, 2018 }, { 29, 9, 2018 }, { 2, 10, 2018 },
                { 6, 10, 2018 }, { 20, 10, 2018 }, { 23, 10, 2018 }, { 27, 10, 2018 }, { 3, 11, 2018 },
                { 10, 11, 2018 }, { 24, 11, 2018 }, { 28, 11, 2018 }, { 1, 12, 2018 }, { 8, 12, 2018 },
                { 15, 12, 2018 }, { 22, 12, 2018 }, { 26, 12, 2018 }, { 29, 12, 2018 }, { 1, 1, 2019 }, { 12, 1, 2019 },
                { 19, 1, 2019 }, { 26, 1, 2019 }, { 2, 2, 2019 }, { 9, 2, 2019 }, { 13, 2, 2019 }, { 16, 2, 2019 },
                { 23, 2, 2019 }, { 2, 3, 2019 }, { 9, 3, 2019 }, { 13, 3, 2019 }, { 16, 3, 2019 }, { 30, 3, 2019 },
                { 6, 4, 2019 }, { 9, 4, 2019 }, { 13, 4, 2019 }, { 19, 4, 2019 }, { 22, 4, 2019 }, { 27, 4, 2019 },
                { 5, 5, 2019 }, { 12, 5, 2019 }, { 18, 5, 2019 } };
        List<int[]> dates = new ArrayList<>();
        for (int[] date : league_dates) {
            date[2] += (year - base_year);
            dates.add(date);
        }
        return dates;
    }
}
