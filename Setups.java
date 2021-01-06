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
        return setups;
    }

    public static List<Nation_Setup> getTestSetup() {
        List<Nation_Setup> setups = new ArrayList<>();
        // Europe
        setups.add(new Nation_Setup("England", "ENG", 4, 1, 1, 20, 80, 157, "Europe"));
        return setups;
    }
}
