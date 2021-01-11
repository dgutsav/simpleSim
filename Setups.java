import java.util.ArrayList;
import java.util.List;

public class Setups {
    public static List<Nation_Setup> getNormalSetup() {
        List<Nation_Setup> setups = new ArrayList<>();
        // Europe
        setups.add(new Nation_Setup("England", "ENG", 4, 1, 4, 20, 80, 157, "Europe"));
        setups.add(new Nation_Setup("Spain", "ESP", 6, 2, 2, 20, 78, 163, "Europe"));
        setups.add(new Nation_Setup("Germany", "GER", 13, 3, 3, 18, 79, 162, "Europe"));
        setups.add(new Nation_Setup("Italy", "ITA", 10, 4, 2, 20, 78, 156, "Europe"));
        setups.add(new Nation_Setup("France", "FRA", 2, 5, 2, 20, 76, 165, "Europe"));
        setups.add(new Nation_Setup("Russia", "RUS", 39, 6, 3, 16, 74, 139, "Europe"));
        setups.add(new Nation_Setup("Portugal", "POR", 5, 7, 2, 18, 74, 159, "Europe"));
        setups.add(new Nation_Setup("Belgium", "BEL", 1, 8, 2, 16, 74, 160, "Europe"));
        setups.add(new Nation_Setup("Ukraine", "UKR", 24, 9, 2, 10, 72, 141, "Europe"));
        setups.add(new Nation_Setup("Turkey", "TUR", 32, 10, 3, 16, 73, 140, "Europe"));
        setups.add(new Nation_Setup("Netherlands", "NED", 14, 11, 2, 18, 74, 159, "Europe"));
        setups.add(new Nation_Setup("Austria", "AUT", 23, 12, 2, 12, 69, 144, "Europe"));
        setups.add(new Nation_Setup("Czechia", "CZE", 42, 13, 2, 14, 72, 145, "Europe"));
        setups.add(new Nation_Setup("Greece", "GRE", 53, 14, 3, 18, 73, 135, "Europe"));
        setups.add(new Nation_Setup("Croatia", "CRO", 11, 15, 2, 10, 68, 150, "Europe"));
        setups.add(new Nation_Setup("Denmark", "DEN", 12, 16, 2, 14, 68, 130, "Europe"));
        setups.add(new Nation_Setup("Switzerland", "SUI", 16, 17, 3, 12, 73, 137, "Europe"));
        setups.add(new Nation_Setup("Cyprus", "CYP", 100, 18, 2, 10, 66, 127, "Europe"));
        setups.add(new Nation_Setup("Serbia", "SRB", 30, 19, 3, 16, 68, 148, "Europe"));
        setups.add(new Nation_Setup("Scotland", "SCO", 48, 20, 2, 12, 69, 135, "Europe"));
        setups.add(new Nation_Setup("Norway", "NOR", 44, 21, 3, 14, 67, 134, "Europe"));
        setups.add(new Nation_Setup("Poland", "POL", 19, 22, 3, 18, 68, 136, "Europe"));
        setups.add(new Nation_Setup("Israel", "ISR", 87, 23, 3, 12, 68, 127, "Europe"));
        setups.add(new Nation_Setup("Romania", "ROU", 37, 24, 3, 16, 69, 143, "Europe"));
        setups.add(new Nation_Setup("Hungary", "HUN", 40, 25, 3, 14, 68, 134, "Europe"));
        setups.add(new Nation_Setup("Belarus", "BLR", 88, 26, 2, 10, 65, 125, "Europe"));
        setups.add(new Nation_Setup("Sweden", "SWE", 20, 27, 2, 10, 66, 135, "Europe"));
        setups.add(new Nation_Setup("Slovakia", "SVK", 33, 28, 2, 10, 66, 138, "Europe"));
        setups.add(new Nation_Setup("Slovenia", "SVN", 62, 29, 2, 10, 64, 129, "Europe"));
        setups.add(new Nation_Setup("Bulgaria", "BUL", 68, 30, 2, 10, 64, 129, "Europe"));
        setups.add(new Nation_Setup("Albania", "ALB", 66, 31, 2, 10, 64, 129, "Europe"));
        setups.add(new Nation_Setup("Finland", "FIN", 54, 32, 2, 10, 64, 129, "Europe"));
        setups.add(new Nation_Setup("Ireland", "IRL", 42, 33, 2, 10, 64, 130, "Europe"));
        setups.add(new Nation_Setup("Bosnia", "BIH", 55, 34, 2, 10, 64, 133, "Europe"));
        setups.add(new Nation_Setup("Wales", "WAL", 18, 35, 2, 10, 64, 139, "Europe"));
        setups.add(new Nation_Setup("Iceland", "ISL", 46, 36, 2, 10, 64, 131, "Europe"));
        // SouthAmerica
        setups.add(new Nation_Setup("Argentina", "ARG", 7, 1, 3, 22, 74, 162, "South America"));
        setups.add(new Nation_Setup("Brazil", "BRA", 3, 2, 3, 20, 75, 163, "South America"));
        setups.add(new Nation_Setup("Colombia", "COL", 15, 3, 3, 20, 71, 154, "South America"));
        setups.add(new Nation_Setup("Chile", "CHI", 17, 4, 2, 18, 70, 149, "South America"));
        setups.add(new Nation_Setup("Uruguay", "URU", 8, 5, 2, 16, 69, 150, "South America"));
        setups.add(new Nation_Setup("Paraguay", "PAR", 34, 6, 3, 12, 69, 140, "South America"));
        setups.add(new Nation_Setup("Ecuador", "ECU", 56, 7, 3, 16, 68, 141, "South America"));
        setups.add(new Nation_Setup("Peru", "PER", 25, 8, 2, 20, 69, 145, "South America"));
        setups.add(new Nation_Setup("Venezuela", "VEN", 28, 9, 2, 20, 67, 137, "South America"));
        setups.add(new Nation_Setup("Bolivia", "BOL", 79, 10, 2, 14, 66, 130, "South America"));
        // North America
        setups.add(new Nation_Setup("Mexico", "MEX", 9, 1, 2, 18, 70, 145, "North America"));
        setups.add(new Nation_Setup("United States", "USA", 22, 2, 1, 24, 68, 136, "North America"));
        setups.add(new Nation_Setup("Canada", "CAN", 72, 3, 1, 10, 64, 135, "North America"));
        setups.add(new Nation_Setup("Costa Rica", "CRC", 51, 4, 2, 18, 66, 135, "North America"));
        setups.add(new Nation_Setup("West Indies", "WIN", 47, 5, 1, 16, 59, 120, "North America"));
        setups.add(new Nation_Setup("Honduras", "HON", 64, 6, 2, 14, 62, 126, "North America"));
        // Asia
        setups.add(new Nation_Setup("Japan", "JPN", 27, 1, 1, 18, 69, 138, "Asia"));
        setups.add(new Nation_Setup("China", "CHN", 74, 2, 1, 18, 69, 125, "Asia"));
        setups.add(new Nation_Setup("Korea", "KOR", 38, 3, 1, 18, 69, 139, "Asia"));
        setups.add(new Nation_Setup("Uzbekistan", "UZB", 85, 4, 1, 18, 69, 130, "Asia"));
        setups.add(new Nation_Setup("Saudi Arabia", "SAU", 67, 5, 1, 18, 69, 127, "Asia"));
        setups.add(new Nation_Setup("Iran", "IRN", 29, 6, 1, 18, 69, 133, "Asia"));
        setups.add(new Nation_Setup("Qatar", "QAT", 58, 7, 1, 18, 69, 129, "Asia"));
        setups.add(new Nation_Setup("United Arab Emirates", "UAE", 74, 8, 1, 18, 69, 127, "Asia"));
        setups.add(new Nation_Setup("Australia", "AUS", 41, 9, 1, 18, 69, 136, "Asia"));
        setups.add(new Nation_Setup("Thailand", "THA", 111, 10, 1, 18, 69, 115, "Asia"));
        setups.add(new Nation_Setup("Syria", "SYR", 76, 11, 1, 18, 69, 121, "Asia"));
        setups.add(new Nation_Setup("Iraq", "IRQ", 69, 12, 1, 18, 69, 122, "Asia"));
        // Africa
        setups.add(new Nation_Setup("Egypt", "EGY", 49, 1, 1, 18, 59, 133, "Africa"));
        setups.add(new Nation_Setup("Tunisia", "TUN", 26, 2, 1, 18, 59, 130, "Africa"));
        setups.add(new Nation_Setup("Algeria", "ALG", 31, 3, 1, 18, 59, 131, "Africa"));
        setups.add(new Nation_Setup("Morocco", "MOR", 35, 4, 1, 18, 59, 131, "Africa"));
        setups.add(new Nation_Setup("Senegal", "SEN", 20, 5, 1, 18, 57, 130, "Africa"));
        setups.add(new Nation_Setup("Ivory Coast", "CIV", 61, 6, 1, 18, 57, 128, "Africa"));
        setups.add(new Nation_Setup("Ghana", "GHA", 52, 7, 1, 18, 58, 128, "Africa"));
        setups.add(new Nation_Setup("Nigeria", "NIG", 35, 8, 1, 18, 62, 135, "Africa"));
        return setups;
    }

    public static List<Nation_Setup> getTestSetup() {
        List<Nation_Setup> setups = new ArrayList<>();
        // Europe
        setups.add(new Nation_Setup("England", "ENG", 4, 1, 1, 20, 80, 157, "Europe"));
        return setups;
    }
}
