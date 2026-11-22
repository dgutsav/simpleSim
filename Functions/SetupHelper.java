package Functions;

public class SetupHelper {

    private static int getData(String nation_name, String parameter) {
        if (nation_name.equalsIgnoreCase(CatalogueHelper.england)) {
            if (parameter.equalsIgnoreCase("num_divisions")) {
                return 4;
            } else if (parameter.equalsIgnoreCase("league_size")) {
                return 20;
            } else if (parameter.equalsIgnoreCase("league_ranking")) {
                return 1;
            } else if (parameter.equalsIgnoreCase("nation_ranking")) {
                return 1;
            } else if (parameter.equalsIgnoreCase("league_rating")) {
                return 80;
            } else if (parameter.equalsIgnoreCase("youth_rating")) {
                return 158;
            }
        } else if (nation_name.equalsIgnoreCase(CatalogueHelper.spain)) {
            if (parameter.equalsIgnoreCase("num_divisions")) {
                return 3;
            } else if (parameter.equalsIgnoreCase("league_size")) {
                return 20;
            } else if (parameter.equalsIgnoreCase("league_ranking")) {
                return 2;
            } else if (parameter.equalsIgnoreCase("nation_ranking")) {
                return 2;
            } else if (parameter.equalsIgnoreCase("league_rating")) {
                return 78;
            } else if (parameter.equalsIgnoreCase("youth_rating")) {
                return 160;
            }
        }
        return 0;
    }

    private static String getStringData(String nation_name, String parameter) {
        if (nation_name.equalsIgnoreCase(CatalogueHelper.england)) {
            if (parameter.equalsIgnoreCase("code")) {
                return "ENG";
            }
        } else if (nation_name.equalsIgnoreCase(CatalogueHelper.spain)) {
            if (parameter.equalsIgnoreCase("code")) {
                return "ESP";
            }
        }
        return "XYZ";
    }

    public static int getNumberOfDivisions(String nation_name) {
        return getData(nation_name, "num_divisions");
    }

    public static int getLeagueSize(String nation_name) {
        return getData(nation_name, "league_size");
    }

    public static int getLeagueRanking(String nation_name) {
        return getData(nation_name, "league_ranking");
    }

    public static int getNationRanking(String nation_name) {
        return getData(nation_name, "nation_ranking");
    }

    public static int getLeagueRating(String nation_name) {
        return getData(nation_name, "league_rating");
    }

    public static int getYouthRating(String nation_name) {
        return getData(nation_name, "youth_rating");
    }

    public static String getCode(String nation_name) {
        return getStringData(nation_name, "code");
    }

    public static String[] getNationsList() {
        String[] nations = { CatalogueHelper.england, CatalogueHelper.spain };
        return nations;
    }
}
