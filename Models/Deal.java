package Models;

public class Deal {
    public String player_name;
    public String buyer_name;
    public String seller_name;
    public int day;
    public int year;
    public long amount;
    public int rating;
    public String position;
    public int nation;
    public int buyer_nation;
    public int seller_nation;

    public String date() { return day+"/"+year; }
}
