package Functions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import Models.Player;

public class CommonFunctions {
    public static int getRandomNumber(int x) {
        return getRandomRange(0, x);
    }

    public static int getRandomRange(int min, int max) {
        int diff = max - min + 1;
        int rand = (int) (Math.random() * diff);
        return rand + min;
    }

    public static String getPaddedString(String str, int length) {
        for (int i = str.length(); i <= length; i++) {
            str += " ";
        }
        return str;
    }

    public static int getRandomInt(int range) {
        return getRandomRange(range * -1, range);
    }

    public static String getRandomElement(List<String> list) {
        return list.get(getRandomNumber(list.size() - 1));
    }

    public static int getRandomIntElement(List<Integer> list) {
        return list.get(getRandomNumber(list.size() - 1));
    }

    public static String getRandomElement(String[] list) {
        return list[getRandomNumber(list.length - 1)];
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

    public static List<Player> sortByRating(List<Player> players) {
        Collections.sort(players, (Player o1, Player o2) -> (int) ((o2.getRating()) - (o1.getRating())));
        return players;
    }

    public static int[] shuffle(int[] array) {
        int temp, r;
        for (int i = 0; i < array.length; i++) {
            r = getRandomNumber(array.length - i - 1);
            temp = array[i];
            array[i] = array[r];
            array[r] = temp;
        }
        return array;
    }

    public static String getDate(Calendar cal) {
        String day = cal.get(Calendar.DATE) >= 10 ? String.valueOf(cal.get(Calendar.DATE))
                : "0" + cal.get(Calendar.DATE);
        String month = cal.get(Calendar.MONTH) >= 9 ? String.valueOf(cal.get(Calendar.MONTH) + 1)
                : "0" + (cal.get(Calendar.MONTH) + 1);
        return day + "/" + month + "/" + cal.get(Calendar.YEAR);
    }

    public static Calendar getFirstDayAfterDate(Calendar date, int day_week) {
        while (true) {
            if (date.get(Calendar.DAY_OF_WEEK) == day_week) {
                return date;
            }
            date.add(Calendar.DATE, 1);
        }
    }

    public static Calendar getLastDayBeforeDate(Calendar date, int day_week) {
        while (true) {
            if (date.get(Calendar.DAY_OF_WEEK) == day_week) {
                return date;
            }
            date.add(Calendar.DATE, -1);
        }
    }

    public static int[] getArrayWithValue(int size, int value) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = value;
        }
        return array;
    }
}
