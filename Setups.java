import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Setups {
    static class Club_Setup {
        public String name;
        public int reputation;
    
        public Club_Setup(String name, int reputation) {
            this.name = name;
            this.reputation = reputation;
        }
    }

    static class Nation_Setup {
        String name;
        String short_name;
        int national_team_ranking;
        int league_ranking;
        int num_leagues;
        int num_team_leagues;
        int league_rating;
        int youth_rating;
        String continent;
    
        public Nation_Setup(String name, String shortString, int nat_rank, int leg_rank, int num_leg, int team_leg,
                int leg_strength, int youth_rating, String continent) {
            this.name = name;
            this.short_name = shortString;
            this.national_team_ranking = nat_rank;
            this.league_ranking = leg_rank;
            this.num_leagues = num_leg;
            this.num_team_leagues = team_leg;
            this.league_rating = leg_strength;
            this.youth_rating = youth_rating;
            this.continent = continent;
        }
    }

    public static List<Nation_Setup> getNormalSetup() {
        List<Nation_Setup> setups = new ArrayList<>();
        setups.addAll(getUEFASetups());
        setups.addAll(getCONMEBOLSetups());
        setups.addAll(getCONCACAFSetups());
        setups.addAll(getAFCSetups());
        setups.addAll(getCAFSetups());
        return setups;
    }

    public static List<Nation_Setup> getTestSetup() {
        List<Nation_Setup> setups = new ArrayList<>();
        // Europe
        setups.add(new Nation_Setup("England", "ENG", 4, 1, 1, 4, 80, 157, "Europe"));
        // setups.add(new Nation_Setup("Spain", "ESP", 4, 2, 2, 20, 80, 157, "Europe"));
        return setups;
    }

    public static Map<String, Double[]> getCoefficientsSetup() {
        Map<String, Double[]> coeff = new HashMap<>();
        Double[] ENG_COEFF = { 20.071, 22.642, 18.571, 24.357, 21.0 };
        Double[] ESP_COEFF = { 19.714, 19.571, 18.928, 19.5, 18.428 };
        Double[] GER_COEFF = { 9.857, 15.214, 18.714, 15.214, 16.214 };
        Double[] ITA_COEFF = { 17.333, 12.642, 14.928, 16.285, 15.714 };
        Double[] FRA_COEFF = { 11.5, 10.583, 11.666, 7.916, 18.416 };
        Double[] NED_COEFF = { 2.9, 8.6, 9.4, 9.2, 19.2 };
        Double[] POR_COEFF = { 9.666, 10.9, 10.3, 9.6, 12.916 };
        Double[] BEL_COEFF = { 2.6, 7.8, 7.6, 6.0, 6.6 };
        Double[] SCO_COEFF = { 4.0, 6.75, 9.75, 8.5, 7.9 };
        Double[] AUT_COEFF = { 9.75, 6.2, 5.8, 6.7, 10.4 };
        Double[] SRB_COEFF = { 6.375, 6.0, 6.0, 5.5, 9.5 };
        Double[] TUR_COEFF = { 6.8, 5.5, 5.0, 3.1, 6.7 };
        Double[] SUI_COEFF = { 6.5, 3.9, 6.4, 5.125, 7.75 };
        Double[] CZE_COEFF = { 5.5, 6.5, 2.5, 6.6, 6.7 };
        Double[] NOR_COEFF = { 4.0, 5.375, 3.75, 6.5, 7.625 };
        Double[] UKR_COEFF = { 8.0, 5.6, 7.2, 6.8, 4.2 };
        Double[] DEN_COEFF = { 5.25, 4.875, 5.125, 4.125, 7.8 };
        Double[] CRO_COEFF = { 5.125, 5.75, 4.375, 5.9, 6.0 };
        Double[] GRE_COEFF = { 5.1, 5.1, 4.9, 5.1, 8.0 };
        Double[] ISR_COEFF = { 5.625, 2.625, 2.375, 7.0, 6.75 };
        Double[] CYP_COEFF = { 7.0, 6.125, 5.125, 4.0, 4.125 };
        Double[] SWE_COEFF = { 5.375, 4.125, 5.75, 2.5, 5.125 };
        Double[] RUS_COEFF = { 12.6, 7.583, 4.666, 4.333, 5.3 };
        Double[] HUN_COEFF = { 1.625, 3.25, 4.5, 4.25, 2.75 };
        Double[] ROU_COEFF = { 2.9, 2.375, 5.875, 3.75, 2.25 };
        Double[] BUL_COEFF = { 4.0, 4.0, 4.125, 4.0, 3.375 };
        Double[] SVK_COEFF = { 1.875, 5.125, 3.0, 1.5, 4.125 };
        Double[] POL_COEFF = { 2.875, 2.25, 2.125, 4.0, 4.625 };
        Double[] AZE_COEFF = { 4.375, 2.375, 3.375, 2.5, 4.375 };
        Double[] KAZ_COEFF = { 4.25, 4.25, 3.375, 1.0, 2.875 };
        Double[] SVN_COEFF = { 4.625, 3.125, 2.0, 2.25, 3.0 };
        Double[] MDA_COEFF = { 2.75, 1.125, 0.75, 1.375, 5.25 };
        Double[] KVX_COEFF = { 0.0, 2.5, 1.5, 1.833, 2.333 };
        Double[] LAT_COEFF = { 0.75, 1.625, 2.25, 1.375, 2.625 };
        Double[] IRL_COEFF = { 1.125, 1.0, 1.25, 1.875, 2.875 };
        Double[] FIN_COEFF = { 1.25, 0.875, 1.625, 1.375, 3.75 };
        Double[] LIT_COEFF = { 2.375, 2.625, 1.625, 1.625, 1.75 };
        Double[] ARM_COEFF = { 0.625, 1.5, 2.75, 1.375, 1.875 };
        Double[] BLR_COEFF = { 3.25, 5.0, 2.5, 1.5, 0.25 };
        Double[] BIH_COEFF = { 1.375, 2.0, 1.5, 2.625, 1.625 };
        Double[] NIR_COEFF = { 0.75, 0.5, 2.375, 2.833, 1.625 };
        Double[] GEO_COEFF = { 0.125, 1.625, 2.25, 1.75, 1.25 };
        Double[] EST_COEFF = { 0.625, 0.5, 0.875, 1.375, 3.666 };
        Double[] ISL_COEFF = { 1.125, 1.5, 0.625, 0.625, 1.5 };
        Double[] ALB_COEFF = { 2.625, 1.0, 0.75, 2.0, 1.625 };
        Double[] WAL_COEFF = { 0.5, 1.0, 1.0, 1.5, 1.5 };
        Double[] MKD_COEFF = { 3.125, 1.0, 0.5, 1.75, 0.625 };
        Double[] MNE_COEFF = { 1.125, 0.375, 1.0, 1.625, 0.75 };
        Double[] LUX_COEFF = { 2.625, 3.0, 1.0, 1.625, 1.25 };
        Double[] FRO_COEFF = { 1.125, 1.125, 2.750, 1.500, 2.250 };
        coeff.put("ENG", ENG_COEFF);
        coeff.put("ESP", ESP_COEFF);
        coeff.put("GER", GER_COEFF);
        coeff.put("ITA", ITA_COEFF);
        coeff.put("FRA", FRA_COEFF);
        coeff.put("NED", NED_COEFF);
        coeff.put("POR", POR_COEFF);
        coeff.put("BEL", BEL_COEFF);
        coeff.put("SCO", SCO_COEFF);
        coeff.put("AUT", AUT_COEFF);
        coeff.put("SRB", SRB_COEFF);
        coeff.put("TUR", TUR_COEFF);
        coeff.put("SUI", SUI_COEFF);
        coeff.put("CZE", CZE_COEFF);
        coeff.put("NOR", NOR_COEFF);
        coeff.put("UKR", UKR_COEFF);
        coeff.put("DEN", DEN_COEFF);
        coeff.put("CRO", CRO_COEFF);
        coeff.put("GRE", GRE_COEFF);
        coeff.put("ISR", ISR_COEFF);
        coeff.put("CYP", CYP_COEFF);
        coeff.put("SWE", SWE_COEFF);
        coeff.put("RUS", RUS_COEFF);
        coeff.put("HUN", HUN_COEFF);
        coeff.put("ROU", ROU_COEFF);
        coeff.put("BUL", BUL_COEFF);
        coeff.put("SVK", SVK_COEFF);
        coeff.put("POL", POL_COEFF);
        coeff.put("AZE", AZE_COEFF);
        coeff.put("KAZ", KAZ_COEFF);
        coeff.put("SVN", SVN_COEFF);
        coeff.put("MDA", MDA_COEFF);
        coeff.put("KVX", KVX_COEFF);
        coeff.put("LAT", LAT_COEFF);
        coeff.put("IRL", IRL_COEFF);
        coeff.put("FIN", FIN_COEFF);
        coeff.put("LIT", LIT_COEFF);
        coeff.put("ARM", ARM_COEFF);
        coeff.put("BLR", BLR_COEFF);
        coeff.put("BIH", BIH_COEFF);
        coeff.put("NIR", NIR_COEFF);
        coeff.put("GEO", GEO_COEFF);
        coeff.put("EST", EST_COEFF);
        coeff.put("ISL", ISL_COEFF);
        coeff.put("ALB", ALB_COEFF);
        coeff.put("WAL", WAL_COEFF);
        coeff.put("MKD", MKD_COEFF);
        coeff.put("MNE", MNE_COEFF);
        coeff.put("LUX", LUX_COEFF);
        coeff.put("FRO", FRO_COEFF);
        return coeff;
    }

    public static List<Nation_Setup> getUEFASetups() {
        List<Nation_Setup> setups = new ArrayList<>();
        setups.add(new Nation_Setup("England", "ENG", 4, 1, 4, 20, 82, 157, "Europe"));
        setups.add(new Nation_Setup("Spain", "ESP", 6, 2, 2, 20, 81, 163, "Europe"));
        setups.add(new Nation_Setup("Italy", "ITA", 10, 4, 2, 20, 80, 156, "Europe"));
        setups.add(new Nation_Setup("Germany", "GER", 13, 3, 3, 18, 81, 162, "Europe"));
        setups.add(new Nation_Setup("France", "FRA", 2, 5, 2, 18, 76, 165, "Europe"));
        setups.add(new Nation_Setup("Portugal", "POR", 5, 7, 2, 18, 71, 159, "Europe"));
        setups.add(new Nation_Setup("Netherlands", "NED", 14, 11, 2, 18, 71, 159, "Europe"));
        setups.add(new Nation_Setup("Austria", "AUT", 23, 12, 2, 12, 67, 144, "Europe"));
        setups.add(new Nation_Setup("Scotland", "SCO", 48, 20, 2, 12, 64, 135, "Europe"));
        setups.add(new Nation_Setup("Russia", "RUS", 39, 6, 3, 16, 68, 139, "Europe"));
        setups.add(new Nation_Setup("Serbia", "SRB", 30, 19, 3, 16, 62, 148, "Europe"));
        setups.add(new Nation_Setup("Ukraine", "UKR", 24, 9, 2, 10, 66, 141, "Europe"));
        setups.add(new Nation_Setup("Belgium", "BEL", 1, 8, 2, 16, 67, 160, "Europe"));
        setups.add(new Nation_Setup("Switzerland", "SUI", 16, 17, 3, 12, 66, 137, "Europe"));
        setups.add(new Nation_Setup("Greece", "GRE", 53, 14, 3, 18, 61, 135, "Europe"));
        setups.add(new Nation_Setup("Croatia", "CRO", 11, 15, 2, 10, 65, 150, "Europe"));
        setups.add(new Nation_Setup("Turkey", "TUR", 32, 10, 3, 16, 67, 140, "Europe"));
        setups.add(new Nation_Setup("Czechia", "CZE", 42, 13, 2, 14, 65, 145, "Europe"));
        setups.add(new Nation_Setup("Denmark", "DEN", 12, 16, 2, 14, 68, 130, "Europe"));
        setups.add(new Nation_Setup("Cyprus", "CYP", 100, 18, 2, 10, 59, 127, "Europe"));
        setups.add(new Nation_Setup("Norway", "NOR", 44, 21, 3, 14, 63, 134, "Europe"));
        setups.add(new Nation_Setup("Poland", "POL", 19, 22, 3, 18, 63, 136, "Europe"));
        setups.add(new Nation_Setup("Israel", "ISR", 87, 23, 3, 12, 63, 127, "Europe"));
        setups.add(new Nation_Setup("Romania", "ROU", 37, 24, 3, 16, 63, 143, "Europe"));
        setups.add(new Nation_Setup("Hungary", "HUN", 40, 25, 3, 14, 63, 134, "Europe"));
        setups.add(new Nation_Setup("Belarus", "BLR", 88, 26, 2, 10, 60, 125, "Europe"));
        setups.add(new Nation_Setup("Sweden", "SWE", 20, 27, 2, 10, 64, 135, "Europe"));
        setups.add(new Nation_Setup("Slovakia", "SVK", 33, 28, 2, 10, 57, 138, "Europe"));
        setups.add(new Nation_Setup("Slovenia", "SVN", 62, 29, 2, 10, 56, 129, "Europe"));
        setups.add(new Nation_Setup("Bulgaria", "BUL", 68, 30, 2, 10, 59, 129, "Europe"));
        setups.add(new Nation_Setup("Albania", "ALB", 66, 31, 2, 10, 53, 129, "Europe"));
        setups.add(new Nation_Setup("Finland", "FIN", 54, 32, 2, 10, 55, 129, "Europe"));
        setups.add(new Nation_Setup("Ireland", "IRL", 42, 33, 2, 10, 52, 130, "Europe"));
        setups.add(new Nation_Setup("Bosnia", "BIH", 55, 34, 2, 10, 52, 133, "Europe"));
        setups.add(new Nation_Setup("Wales", "WAL", 18, 35, 1, 10, 50, 139, "Europe"));
        setups.add(new Nation_Setup("Iceland", "ISL", 46, 36, 2, 10, 54, 131, "Europe"));
        setups.add(new Nation_Setup("Moldova", "MDA", 46, 37, 2, 8, 58, 131, "Europe"));
        setups.add(new Nation_Setup("Kazakhstan", "KAZ", 46, 38, 2, 10, 59, 131, "Europe"));
        setups.add(new Nation_Setup("Georgia", "GEO", 46, 39, 2, 10, 55, 131, "Europe"));
        setups.add(new Nation_Setup("Armenia", "ARM", 46, 40, 2, 10, 55, 131, "Europe"));
        setups.add(new Nation_Setup("Azerbaijan", "AZE", 46, 41, 2, 10, 58, 131, "Europe"));
        setups.add(new Nation_Setup("Latvia", "LAT", 46, 42, 2, 10, 54, 131, "Europe"));
        setups.add(new Nation_Setup("Estonia", "EST", 46, 43, 2, 10, 52, 131, "Europe"));
        setups.add(new Nation_Setup("Lithuania", "LIT", 46, 44, 2, 10, 52, 131, "Europe"));
        setups.add(new Nation_Setup("North Macedonia", "MKD", 46, 45, 1, 10, 54, 131, "Europe"));
        setups.add(new Nation_Setup("Montenegro", "MNE", 46, 46, 1, 10, 52, 131, "Europe"));
        setups.add(new Nation_Setup("Kosovo", "KVX", 46, 47, 1, 10, 52, 131, "Europe"));
        setups.add(new Nation_Setup("Northern Ireland", "NIR", 46, 48, 1, 10, 54, 131, "Europe"));
        setups.add(new Nation_Setup("Luxembourg", "LUX", 46, 49, 1, 10, 56, 131, "Europe"));
        setups.add(new Nation_Setup("Faroe Islands", "FRO", 46, 50, 1, 10, 55, 131, "Europe"));
        return setups;
    }

    public static List<Nation_Setup> getCONMEBOLSetups() {
        List<Nation_Setup> setups = new ArrayList<>();
        setups.add(new Nation_Setup("Argentina", "ARG", 7, 2, 3, 22, 73, 162, "South America"));
        setups.add(new Nation_Setup("Brazil", "BRA", 3, 1, 3, 20, 74, 163, "South America"));
        setups.add(new Nation_Setup("Colombia", "COL", 15, 3, 3, 20, 68, 154, "South America"));
        setups.add(new Nation_Setup("Chile", "CHI", 17, 4, 2, 18, 68, 149, "South America"));
        setups.add(new Nation_Setup("Uruguay", "URU", 8, 5, 2, 16, 64, 150, "South America"));
        setups.add(new Nation_Setup("Paraguay", "PAR", 34, 6, 3, 12, 64, 140, "South America"));
        setups.add(new Nation_Setup("Ecuador", "ECU", 56, 7, 3, 16, 69, 141, "South America"));
        setups.add(new Nation_Setup("Peru", "PER", 25, 8, 2, 20, 64, 145, "South America"));
        setups.add(new Nation_Setup("Venezuela", "VEN", 28, 9, 2, 20, 60, 137, "South America"));
        setups.add(new Nation_Setup("Bolivia", "BOL", 79, 10, 2, 14, 61, 130, "South America"));
        return setups;
    }

    public static List<Nation_Setup> getCONCACAFSetups() {
        List<Nation_Setup> setups = new ArrayList<>();
        setups.add(new Nation_Setup("Mexico", "MEX", 9, 1, 2, 18, 71, 145, "North America"));
        setups.add(new Nation_Setup("United States", "USA", 22, 2, 1, 24, 70, 136, "North America"));
        setups.add(new Nation_Setup("Canada", "CAN", 72, 4, 1, 10, 58, 135, "North America"));
        setups.add(new Nation_Setup("Costa Rica", "CRC", 51, 3, 2, 18, 60, 135, "North America"));
        setups.add(new Nation_Setup("West Indies", "WIN", 47, 6, 1, 16, 56, 120, "North America"));
        setups.add(new Nation_Setup("Honduras", "HON", 64, 5, 2, 14, 61, 126, "North America"));
        return setups;
    }

    public static List<Nation_Setup> getAFCSetups() {
        List<Nation_Setup> setups = new ArrayList<>();
        setups.add(new Nation_Setup("Saudi Arabia", "SAU", 67, 5, 1, 18, 67, 127, "Asia"));
        setups.add(new Nation_Setup("Japan", "JPN", 27, 1, 1, 18, 65, 138, "Asia"));
        setups.add(new Nation_Setup("Korea", "KOR", 38, 3, 1, 18, 62, 139, "Asia"));
        setups.add(new Nation_Setup("Qatar", "QAT", 58, 7, 1, 10, 61, 129, "Asia"));
        setups.add(new Nation_Setup("Australia", "AUS", 41, 9, 1, 8, 60, 136, "Asia"));
        setups.add(new Nation_Setup("Iran", "IRN", 29, 6, 1, 20, 58, 133, "Asia"));
        setups.add(new Nation_Setup("Emirates", "UAE", 74, 8, 1, 10, 57, 127, "Asia"));
        setups.add(new Nation_Setup("China", "CHN", 74, 2, 1, 18, 56, 125, "Asia"));
        setups.add(new Nation_Setup("Uzbekistan", "UZB", 85, 4, 1, 18, 56, 130, "Asia"));
        setups.add(new Nation_Setup("Thailand", "THA", 111, 10, 1, 12, 56, 115, "Asia"));
        setups.add(new Nation_Setup("Jordan", "JOR", 69, 16, 1, 10, 54, 122, "Asia"));
        setups.add(new Nation_Setup("Vietnam", "VIE", 69, 13, 1, 14, 53, 122, "Asia"));
        setups.add(new Nation_Setup("India", "IND", 69, 12, 1, 16, 52, 122, "Asia"));
        setups.add(new Nation_Setup("Malaysia", "MAS", 69, 14, 1, 18, 50, 122, "Asia"));
        setups.add(new Nation_Setup("Iraq", "IRQ", 69, 17, 1, 10, 50, 122, "Asia"));
        setups.add(new Nation_Setup("Indonesia", "IDN", 69, 15, 1, 14, 50, 122, "Asia"));
        setups.add(new Nation_Setup("Syria", "SYR", 76, 18, 1, 10, 49, 121, "Asia"));
        setups.add(new Nation_Setup("New Zealand", "NZL", 69, 16, 1, 8, 47, 122, "Asia"));
        return setups;
    }

    public static List<Nation_Setup> getCAFSetups() {
        List<Nation_Setup> setups = new ArrayList<>();
        setups.add(new Nation_Setup("Morocco", "MAR", 35, 1, 1, 14, 62, 131, "Africa"));
        setups.add(new Nation_Setup("Egypt", "EGY", 49, 2, 1, 14, 60, 133, "Africa"));
        setups.add(new Nation_Setup("Algeria", "ALG", 31, 3, 1, 14, 57, 131, "Africa"));
        setups.add(new Nation_Setup("South Africa", "RSA", 67, 5, 1, 12, 57, 130, "Africa"));
        setups.add(new Nation_Setup("Tunisia", "TUN", 26, 4, 1, 14, 56, 130, "Africa"));
        setups.add(new Nation_Setup("Congo DR", "DRC", 75, 6, 1, 12, 54, 127, "Africa"));
        setups.add(new Nation_Setup("Nigeria", "NIG", 35, 10, 1, 14, 54, 135, "Africa"));
        setups.add(new Nation_Setup("Guinea", "GUI", 75, 9, 1, 12, 53, 124, "Africa"));
        setups.add(new Nation_Setup("Angola", "ANG", 50, 7, 1, 12, 52, 124, "Africa"));
        setups.add(new Nation_Setup("Sudan", "SUD", 67, 8, 1, 12, 52, 124, "Africa"));
        setups.add(new Nation_Setup("Senegal", "SEN", 20, 13, 1, 14, 52, 130, "Africa"));
        setups.add(new Nation_Setup("Ivory Coast", "CIV", 61, 14, 1, 14, 52, 128, "Africa"));
        setups.add(new Nation_Setup("Zambia", "ZAM", 50, 11, 1, 12, 51, 124, "Africa"));
        setups.add(new Nation_Setup("Cameroon", "CAM", 33, 12, 1, 12, 51, 124, "Africa"));
        setups.add(new Nation_Setup("Ghana", "GHA", 52, 15, 1, 14, 51, 128, "Africa"));
        setups.add(new Nation_Setup("Mali", "MLI", 45, 16, 1, 12, 50, 124, "Africa"));
        return setups;
    }

    public static Map<String, Double[]> getWCLCoefficients() {
        Map<String, Double[]> map = new HashMap<String, Double[]>() {
            {
                put("ENG", new Double[] { 20.071, 22.642, 18.571, 24.357, 21.0 });
                put("ESP", new Double[] { 19.714, 19.571, 18.928, 19.5, 18.428 });
                put("GER", new Double[] { 9.857, 15.214, 18.714, 15.214, 16.214 });
                put("ITA", new Double[] { 17.333, 12.642, 14.928, 16.285, 15.714 });
                put("FRA", new Double[] { 11.5, 10.583, 11.666, 7.916, 18.416 });
                put("NED", new Double[] { 2.9, 8.6, 9.4, 9.2, 19.2 });
                put("POR", new Double[] { 9.666, 10.9, 10.3, 9.6, 12.916 });
                put("BEL", new Double[] { 2.6, 7.8, 7.6, 6.0, 6.6 });
                put("SCO", new Double[] { 4.0, 6.75, 9.75, 8.5, 7.9 });
                put("AUT", new Double[] { 9.75, 6.2, 5.8, 6.7, 10.4 });
                put("SRB", new Double[] { 6.375, 6.0, 6.0, 5.5, 9.5 });
                put("TUR", new Double[] { 6.8, 5.5, 5.0, 3.1, 6.7 });
                put("SUI", new Double[] { 6.5, 3.9, 6.4, 5.125, 7.75 });
                put("CZE", new Double[] { 5.5, 6.5, 2.5, 6.6, 6.7 });
                put("NOR", new Double[] { 4.0, 5.375, 3.75, 6.5, 7.625 });
                put("UKR", new Double[] { 8.0, 5.6, 7.2, 6.8, 4.2 });
                put("DEN", new Double[] { 5.25, 4.875, 5.125, 4.125, 7.8 });
                put("CRO", new Double[] { 5.125, 5.75, 4.375, 5.9, 6.0 });
                put("GRE", new Double[] { 5.1, 5.1, 4.9, 5.1, 8.0 });
                put("ISR", new Double[] { 5.625, 2.625, 2.375, 7.0, 6.75 });
                put("CYP", new Double[] { 7.0, 6.125, 5.125, 4.0, 4.125 });
                put("SWE", new Double[] { 5.375, 4.125, 5.75, 2.5, 5.125 });
                put("RUS", new Double[] { 12.6, 7.583, 4.666, 4.333, 5.3 });
                put("HUN", new Double[] { 1.625, 3.25, 4.5, 4.25, 2.75 });
                put("ROU", new Double[] { 2.9, 2.375, 5.875, 3.75, 2.25 });
                put("BUL", new Double[] { 4.0, 4.0, 4.125, 4.0, 3.375 });
                put("SVK", new Double[] { 1.875, 5.125, 3.0, 1.5, 4.125 });
                put("POL", new Double[] { 2.875, 2.25, 2.125, 4.0, 4.625 });
                put("AZE", new Double[] { 4.375, 2.375, 3.375, 2.5, 4.375 });
                put("KAZ", new Double[] { 4.25, 4.25, 3.375, 1.0, 2.875 });
                put("SVN", new Double[] { 4.625, 3.125, 2.0, 2.25, 3.0 });
                put("MDA", new Double[] { 2.75, 1.125, 0.75, 1.375, 5.25 });
                put("KVX", new Double[] { 0.0, 2.5, 1.5, 1.833, 2.333 });
                put("LAT", new Double[] { 0.75, 1.625, 2.25, 1.375, 2.625 });
                put("IRL", new Double[] { 1.125, 1.0, 1.25, 1.875, 2.875 });
                put("FIN", new Double[] { 1.25, 0.875, 1.625, 1.375, 3.75 });
                put("LIT", new Double[] { 2.375, 2.625, 1.625, 1.625, 1.75 });
                put("ARM", new Double[] { 0.625, 1.5, 2.75, 1.375, 1.875 });
                put("BLR", new Double[] { 3.25, 5.0, 2.5, 1.5, 0.25 });
                put("BIH", new Double[] { 1.375, 2.0, 1.5, 2.625, 1.625 });
                put("NIR", new Double[] { 0.75, 0.5, 2.375, 2.833, 1.625 });
                put("GEO", new Double[] { 0.125, 1.625, 2.25, 1.75, 1.25 });
                put("EST", new Double[] { 0.625, 0.5, 0.875, 1.375, 3.666 });
                put("ISL", new Double[] { 1.125, 1.5, 0.625, 0.625, 1.5 });
                put("ALB", new Double[] { 2.625, 1.0, 0.75, 2.0, 1.625 });
                put("WAL", new Double[] { 0.5, 1.0, 1.0, 1.5, 1.5 });
                put("MKD", new Double[] { 3.125, 1.0, 0.5, 1.75, 0.625 });
                put("MNE", new Double[] { 1.125, 0.375, 1.0, 1.625, 0.75 });
                put("LUX", new Double[] { 2.625, 3.0, 1.0, 1.625, 1.25 });
                put("FRO", new Double[] { 1.125, 1.125, 2.750, 1.500, 2.250 });
                put("ARG", new Double[] { 3.6, 3.5, 3.4, 2.7, 8.5 });
                put("BRA", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("COL", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("CHI", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("URU", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("PAR", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("ECU", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("PER", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("VEN", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("BOL", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("MEX", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("USA", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("CAN", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("CRC", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("WIN", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("HON", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("SAU", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("JPN", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("KOR", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("QAT", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("AUS", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("IRN", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("UAE", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("CHN", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("UZB", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("THA", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("JOR", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("VIE", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("IND", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("MAS", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("IRQ", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("IDN", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("SYR", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("NZL", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("MAR", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("EGY", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("ALG", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("RSA", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("TUN", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("DRC", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("NIG", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("GUI", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("ANG", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("SUD", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("SEN", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("CIV", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("ZAM", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("CAM", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("GHA", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
                put("MLI", new Double[] { 0.0, 0.0, 0.0, 0.0, 0.0 });
            }
        };
        return map;
    }

    public static List<Club_Setup> getClubSetups(String nation, int level) {
        List<Club_Setup> clubs = new ArrayList<>();
        switch (nation) {
            case "England":
                if (level == 1) {
                    clubs.add(new Club_Setup("Manchester City", 9400));
                    clubs.add(new Club_Setup("Liverpool FC", 9100));
                    clubs.add(new Club_Setup("Arsenal", 8900));
                    clubs.add(new Club_Setup("Manchester United", 8800));
                    clubs.add(new Club_Setup("Chelsea", 8700));
                    clubs.add(new Club_Setup("Tottenham Hotspur", 8700));
                    clubs.add(new Club_Setup("Aston Villa", 8300));
                    clubs.add(new Club_Setup("Leicester City", 8300));
                    clubs.add(new Club_Setup("West Ham United", 8300));
                    clubs.add(new Club_Setup("Everton", 8300));
                    clubs.add(new Club_Setup("Newcastle United", 8300));
                    clubs.add(new Club_Setup("Brighton & Hove Albion", 8300));
                    clubs.add(new Club_Setup("Leeds United", 7900));
                    clubs.add(new Club_Setup("AFC Sunderland", 7700));
                    clubs.add(new Club_Setup("Wolverhampton Wanderers", 7600));
                    clubs.add(new Club_Setup("Southampton", 7500));
                    clubs.add(new Club_Setup("Nottingham Forest", 7500));
                    clubs.add(new Club_Setup("Fulham", 7400));
                    clubs.add(new Club_Setup("Sheffield United", 7300));
                    clubs.add(new Club_Setup("AFC Bournemouth", 7400));
                }
                break;
            case "Spain":
                if (level == 1) {
                    clubs.add(new Club_Setup("Real Madrid", 9200));
                    clubs.add(new Club_Setup("FC Barcelona", 9100));
                    clubs.add(new Club_Setup("Atletico de Madrid", 8900));
                    clubs.add(new Club_Setup("Valencia", 8600));
                    clubs.add(new Club_Setup("Sevilla", 8700));
                    clubs.add(new Club_Setup("Real Sociedad", 8700));
                    clubs.add(new Club_Setup("Athletic Club de Bilbao", 8700));
                    clubs.add(new Club_Setup("Villarreal", 8600));
                    clubs.add(new Club_Setup("Real Betis", 8200));
                    clubs.add(new Club_Setup("Celta Vigo", 8200));
                    clubs.add(new Club_Setup("Deportivo La Coruna", 7300));
                    clubs.add(new Club_Setup("Mallorca", 7400));
                    clubs.add(new Club_Setup("Osasuna", 8100));
                    clubs.add(new Club_Setup("Real Zaragoza", 8000));
                    clubs.add(new Club_Setup("RCD Espanyol", 7800));
                    clubs.add(new Club_Setup("Girona", 8400));
                    clubs.add(new Club_Setup("Deportivo Alaves", 7800));
                    clubs.add(new Club_Setup("Rayo Vallecano", 7900));
                    clubs.add(new Club_Setup("Getafe", 7700));
                    clubs.add(new Club_Setup("UD Las Palmas", 7500));
                }
                break;
            case "Germany":
                if (level == 1) {
                    clubs.add(new Club_Setup("FC Bayern Munchen", 8900));
                    clubs.add(new Club_Setup("Borussia Dortmund", 8600));
                    clubs.add(new Club_Setup("Bayer Leverkusen", 8700));
                    clubs.add(new Club_Setup("Schalke 04", 7100));
                    clubs.add(new Club_Setup("Hamburg SV", 7200));
                    clubs.add(new Club_Setup("Eintracht Frankfurt", 8500));
                    clubs.add(new Club_Setup("RB Leipzig", 8600));
                    clubs.add(new Club_Setup("Hertha Berlin", 7300));
                    clubs.add(new Club_Setup("SC Freiburg", 7900));
                    clubs.add(new Club_Setup("Monchengladbach", 8000));
                    clubs.add(new Club_Setup("VfB Stuttgart", 8600));
                    clubs.add(new Club_Setup("VfL Wolfsburg", 7800));
                    clubs.add(new Club_Setup("SC Augsburg", 8100));
                    clubs.add(new Club_Setup("1. FSV Mainz 05", 7600));
                    clubs.add(new Club_Setup("TSG 1899 Hoffenheim", 7700));
                    clubs.add(new Club_Setup("1. FC Koln", 7500));
                    clubs.add(new Club_Setup("SV Werder Bremen", 7800));
                    clubs.add(new Club_Setup("Union Berlin", 7600));
                }
                break;
            case "Italy":
                if (level == 1) {
                    clubs.add(new Club_Setup("Juventus FC", 8600));
                    clubs.add(new Club_Setup("Internazionale Milano", 8800));
                    clubs.add(new Club_Setup("AC Milan", 8400));
                    clubs.add(new Club_Setup("SSC Napoli", 8500));
                    clubs.add(new Club_Setup("AS Roma", 8200));
                    clubs.add(new Club_Setup("SS Lazio", 8100));
                    clubs.add(new Club_Setup("Atalanta BC", 7900));
                    clubs.add(new Club_Setup("Bologna FC 1909", 7800));
                    clubs.add(new Club_Setup("Genoa CFC", 7700));
                    clubs.add(new Club_Setup("US Sassuolo", 7600));
                    clubs.add(new Club_Setup("ACF Fiorentina", 7700));
                    clubs.add(new Club_Setup("UC Sampdoria", 7000));
                    clubs.add(new Club_Setup("Hellas Verona", 7500));
                    clubs.add(new Club_Setup("Cagliari Calcio", 7500));
                    clubs.add(new Club_Setup("Udinese Calcio", 7600));
                    clubs.add(new Club_Setup("Parma Calcio", 7100));
                    clubs.add(new Club_Setup("US Palermo", 7000));
                    clubs.add(new Club_Setup("AC Monza", 7600));
                    clubs.add(new Club_Setup("Torino FC", 7600));
                    clubs.add(new Club_Setup("Empoli FC", 7500));
                }
                break;
            case "France":
                if (level == 1) {
                    clubs.add(new Club_Setup("Paris Saint-Germain", 8800));
                    clubs.add(new Club_Setup("AS Monaco FC", 8200));
                    clubs.add(new Club_Setup("Stade Brestois 29", 7900));
                    clubs.add(new Club_Setup("Lille OSC", 8100));
                    clubs.add(new Club_Setup("OGC Nice", 7800));
                    clubs.add(new Club_Setup("RC Lens", 7700));
                    clubs.add(new Club_Setup("Olympique de Marseille", 8000));
                    clubs.add(new Club_Setup("Stade Rennais", 7800));
                    clubs.add(new Club_Setup("Olympique Lyonnais", 7800));
                    clubs.add(new Club_Setup("Toulouse FC", 7400));
                    clubs.add(new Club_Setup("Stade Reims", 7200));
                    clubs.add(new Club_Setup("FC Nantes", 7000));
                    clubs.add(new Club_Setup("Montpellier HSC", 7000));
                    clubs.add(new Club_Setup("RC Strasbourg", 7100));
                    clubs.add(new Club_Setup("AJ Auxerre", 6900));
                    clubs.add(new Club_Setup("Girodins de Bordeaux", 6700));
                    clubs.add(new Club_Setup("AS Saint-Ettiene", 6700));
                    clubs.add(new Club_Setup("AC Ajaccio", 6600));
                }
                break;
            case "Portugal":
                if (level == 1) {
                    clubs.add(new Club_Setup("SL Benfica", 8100));
                    clubs.add(new Club_Setup("FC Porto", 8000));
                    clubs.add(new Club_Setup("Sporting CP", 8000));
                    clubs.add(new Club_Setup("SC Braga", 7800));
                    clubs.add(new Club_Setup("Vitoria de Guimaraes", 7400));
                    clubs.add(new Club_Setup("OS Belenses", 7300));
                    clubs.add(new Club_Setup("Vitoria Setubal", 7200));
                    clubs.add(new Club_Setup("Boavista FC", 7000));
                    clubs.add(new Club_Setup("Academica de Coimbra", 6700));
                    clubs.add(new Club_Setup("CS Maritimo", 6700));
                    clubs.add(new Club_Setup("Rio Ave FC", 6700));
                    clubs.add(new Club_Setup("Estoril de Praia", 6600));
                    clubs.add(new Club_Setup("Farense", 6500));
                    clubs.add(new Club_Setup("Pacos de Ferreira", 6500));
                    clubs.add(new Club_Setup("Beira-Mar", 6400));
                    clubs.add(new Club_Setup("Gil Vicente", 6300));
                    clubs.add(new Club_Setup("CD Nacional", 6300));
                    clubs.add(new Club_Setup("Portimonense", 6200));
                }
                break;
            case "Netherlands":
                if (level == 1) {
                    clubs.add(new Club_Setup("AFC Ajax", 8200));
                    clubs.add(new Club_Setup("PSV Eindhoven", 8100));
                    clubs.add(new Club_Setup("Feyenoord", 8100));
                    clubs.add(new Club_Setup("FC Twente", 7700));
                    clubs.add(new Club_Setup("AZ Alkmaar", 7700));
                    clubs.add(new Club_Setup("Sparta Rotterdam", 7300));
                    clubs.add(new Club_Setup("Roda JC", 7300));
                    clubs.add(new Club_Setup("FC Utrecht", 7200));
                    clubs.add(new Club_Setup("NAC Breda", 7200));
                    clubs.add(new Club_Setup("ADO Den Haag", 6800));
                    clubs.add(new Club_Setup("FC Groningen", 6700));
                    clubs.add(new Club_Setup("Vitesse Arnhem", 6600));
                    clubs.add(new Club_Setup("Willem II", 6600));
                    clubs.add(new Club_Setup("NEC Nijmegen", 6500));
                    clubs.add(new Club_Setup("MVV Maastricht", 6400));
                    clubs.add(new Club_Setup("SC Heerenveen", 6400));
                    clubs.add(new Club_Setup("Go Ahead Eagles", 6300));
                    clubs.add(new Club_Setup("RKC Waalwijk", 6300));
                }
                break;
            case "Scotland":
                if (level == 1) {
                    clubs.add(new Club_Setup("Celtic FC", 7800));
                    clubs.add(new Club_Setup("Rangers FC", 7700));
                    clubs.add(new Club_Setup("Aberdeen", 7200));
                }
                break;
            case "Russia":
                if (level == 1) {
                    clubs.add(new Club_Setup("Zenit St. Petersburg", 7600));
                    clubs.add(new Club_Setup("FC Krasnodar", 7400));
                    clubs.add(new Club_Setup("Lokomotiv Moskva", 7400));
                    clubs.add(new Club_Setup("PFC CSKA Moskva", 7300));
                    clubs.add(new Club_Setup("Spartak Moksva", 7300));
                    clubs.add(new Club_Setup("Dinamo Moskva", 7300));
                }
                break;
            case "Ukraine":
                if (level == 1) {
                    clubs.add(new Club_Setup("Shakhtar Donetsk", 7500));
                    clubs.add(new Club_Setup("Dynamo Kyiv", 7400));
                    clubs.add(new Club_Setup("FC Dnipro", 7100));
                }
                break;
            case "Belgium":
                if (level == 1) {
                    clubs.add(new Club_Setup("Club Brugge", 7800));
                    clubs.add(new Club_Setup("Union Saint-Gilloise", 7700));
                    clubs.add(new Club_Setup("RSC Anderlecht", 7600));
                    clubs.add(new Club_Setup("KAA Gent", 7600));
                    clubs.add(new Club_Setup("KRC Genk", 7600));
                    clubs.add(new Club_Setup("Royal Antwerp", 7500));
                }
                break;
            case "Austria":
                if (level == 1) {
                    clubs.add(new Club_Setup("Red Bull Salzburg", 7700));
                    clubs.add(new Club_Setup("SK Sturm Graz", 7600));
                    clubs.add(new Club_Setup("SK Rapid Vienna", 7400));
                }
                break;
            case "Croatia":
                if (level == 1) {
                    clubs.add(new Club_Setup("GNK Dinamo Zagreb", 7700));
                    clubs.add(new Club_Setup("HNK Hajduk Split", 7600));
                    clubs.add(new Club_Setup("HNK Rijeka", 7500));
                }
                break;
            case "Serbia":
                if (level == 1) {
                    clubs.add(new Club_Setup("FK Crvena Zvezda", 7600));
                    clubs.add(new Club_Setup("FK Partizan", 7000));
                    clubs.add(new Club_Setup("FK TSC Backa Topola", 7000));
                    clubs.add(new Club_Setup("FK Vojvodina", 6900));
                }
                break;
            case "Switzerland":
                if (level == 1) {
                    clubs.add(new Club_Setup("BSC Young Boys", 7400));
                    clubs.add(new Club_Setup("Servette FC", 7400));
                    clubs.add(new Club_Setup("FC Zurich", 7400));
                    clubs.add(new Club_Setup("FC Lugano", 7400));
                }
                break;
            case "Poland":
                if (level == 1) {
                    clubs.add(new Club_Setup("Legia Warszawa", 7400));
                    clubs.add(new Club_Setup("KKS Lech Poznan", 7400));
                    clubs.add(new Club_Setup("Rokow Czestochowa", 7400));
                    clubs.add(new Club_Setup("Jagiellonia Bialystok", 7300));
                    clubs.add(new Club_Setup("Pogon Szczecin", 7300));
                    clubs.add(new Club_Setup("KS Cracovia", 7200));
                }
                break;
            case "Turkey":
                if (level == 1) {
                    clubs.add(new Club_Setup("Fenerbahce", 8000));
                    clubs.add(new Club_Setup("Galatasaray", 8000));
                    clubs.add(new Club_Setup("Besiktas", 7600));
                    clubs.add(new Club_Setup("Istanbul Basaksehir", 7600));
                    clubs.add(new Club_Setup("Trabzonspor", 7500));
                } break;
            case "Greece":
                if (level == 1) {
                    clubs.add(new Club_Setup("Olympiakos", 7800));
                    clubs.add(new Club_Setup("PAOK FC", 7700));
                    clubs.add(new Club_Setup("AEK Athens", 7700));
                    clubs.add(new Club_Setup("Panathinaikos", 7600));
                } break;
            case "Czechia":
                if (level == 1) {
                    clubs.add(new Club_Setup("Slavia Praha", 8000));
                    clubs.add(new Club_Setup("Sparta Praha", 7900));
                    clubs.add(new Club_Setup("Viktoria Plzen", 7700));
                } break;
            case "Denmark":
                if (level == 1) {
                    clubs.add(new Club_Setup("FC Midtjylland", 7800));
                    clubs.add(new Club_Setup("FC Kobenhavn", 7700));
                    clubs.add(new Club_Setup("FC Nordsjaelland", 7600));
                    clubs.add(new Club_Setup("Brondby IF", 7600));
                }
                break;
            case "Norway":
            if (level == 1) {
                clubs.add(new Club_Setup("FK Bodo/Glimt", 7700));
                clubs.add(new Club_Setup("FK Molde", 7500));
            } break;
        }
        return clubs;
    }
}
