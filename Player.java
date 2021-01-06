public class Player {
    private String name;
    private String id;
    private Position position;
    private int age;
    private int nation_id;
    private boolean injured;
    private double xp;
    private int rating;
    private Injury currentInjury;
    private boolean retired;

    public static final int MAX_LEVELS = 500;
    public static final int PADDED_LENGTH = 30;

    public Player(String name, Position position, int age, int nation, int rating, double xp) {
        this.name = name;
        this.position = position;
        this.age = age;
        this.nation_id = nation;
        this.injured = false;
        this.xp = xp;
        this.rating = rating;
        this.currentInjury = null;
        this.retired = false;
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Position getPosition() {
        return this.position;
    }

    public int getAge() {
        return this.age;
    }

    public void incrementAge() {
        this.age++;
    }

    public int getNationId() {
        return this.nation_id;
    }

    public boolean isInjured() {
        return this.injured;
    }

    public void healInjury() {
        this.injured = false;
        this.currentInjury = null;
    }

    public void injurePlayer(Injury injury) {
        this.currentInjury = injury;
        this.injured = true;
    }

    public double getXP() {
        return this.xp;
    }

    public void increaseXP(double xp_to_increase) {
        this.xp += xp_to_increase;
    }

    public int getLevel() {
        return Player.getXPLevel(this.xp);
    }

    public int getRating() {
        return this.rating;
    }

    public void updatePlayer() {
        Sim.updatePlayer(this);
    }

    public boolean isRetired() {
        return this.retired;
    }

    public Injury getInjury() {
        return this.currentInjury;
    }

    public static int getXPLevel(double xp) {
        int current_level = 1;
        int level1 = 10;
        int level2 = 10;
        while (xp > (level1 + level2)) {
            current_level++;
            int temp = level1 + level2;
            level1 = level2;
            level2 = temp;
        }
        if (current_level > Player.MAX_LEVELS) {
            current_level = Player.MAX_LEVELS;
        }
        return current_level;
    }

    public static int getRatingIncrease(int level) {
        int current_level = 0;
        int level1 = 0;
        int level2 = 1;
        while (level > (level1 + level2)) {
            current_level++;
            int temp = level1 + level2;
            level1 = level2;
            level2 = temp;
        }
        return current_level;
    }

    public static int adjustRating(int rating) {
        if (rating > 99) {
            return 99;
        } else if (rating < 0) {
            return 0;
        }
        return rating;
    }

    public void show() {
        System.out.println(PositionHelper.getPositionName(this.position) + "\t" + this.name + "\t\t" + this.rating);
    }

    public void seasonEndChanges() {
        int max_growth = 0;
        if (this.age < 18) {
            max_growth = 6;
        } else if (this.age < 20) {
            max_growth = 5;
        } else if (this.age < 22) {
            max_growth = 4;
        } else if (this.age < 24) {
            max_growth = 3;
        } else if (this.age < 26) {
            max_growth = 1;
        } else if (this.age < 30) {
            max_growth = 0;
        } else if (this.age < 32) {
            max_growth = -3;
        } else if (this.age < 35) {
            max_growth = -5;
        }
        if (max_growth > 0) {
            this.rating += CommonFunctions.getRandomNumber(max_growth);
        } else if (max_growth < 0) {
            this.rating -= CommonFunctions.getRandomNumber(max_growth * -1);
        }
        this.rating = Player.adjustRating(this.rating);
        this.incrementAge();
        if (this.age > 36) {
            this.retired = true;
        }
        this.updatePlayer();
    }

    public String getPaddedName() {
        return CommonFunctions.getPaddedString(this.name, Player.PADDED_LENGTH);
    }

    public int getRatingForPosition(Position position) {
        double factored_rating = PositionHelper.getPositionSimilarity(this.position, position) * this.getRating();
        return (int) factored_rating;
    }

    public int getBaseValue() {
        double value = 0;
        int player_rating = this.getRating();
        if (player_rating >= 95) {
            value = 150;
        } else if (player_rating >= 90) {
            value = 75 + (player_rating - 90) * 15;
        } else if (player_rating >= 85) {
            value = 40 + (player_rating - 85) * 7;
        } else if (player_rating >= 80) {
            value = 15 + (player_rating - 80) * 5;
        } else if (player_rating >= 75) {
            value = 5 + (player_rating - 75) * 2;
        } else if (player_rating >= 70) {
            value = 1 + (player_rating - 70) * 0.8;
        } else if (player_rating >= 60) {
            value = 0.1 + (player_rating - 60) * 0.09;
        } else if (player_rating >= 50) {
            value = 0.01 + (player_rating - 50) * 0.009;
        } else {
            value = 0.01;
        }
        int player_age = this.getAge();
        if (player_age <= 17) {
            value *= 2.5;
        } else if (player_age <= 19) {
            value *= 2 + (19 - player_age) * 0.25;
        } else if (player_age <= 21) {
            value *= 1.75 + (21 - player_age) * 0.125;
        } else if (player_age <= 23) {
            value *= 1.55 + (23 - player_age) * 0.1;
        } else if (player_age <= 25) {
            value *= 1.15 + (25 - player_age) * 0.2;
        } else if (player_age <= 30) {
            value *= 1;
        } else if (player_age <= 33) {
            value *= 0.85;
        } else if (player_age <= 35) {
            value *= 0.65;
        } else if (player_age <= 38) {
            value *= 0.5;
        }
        value *= 1000000;
        return (int) Math.round(value);
    }
}
