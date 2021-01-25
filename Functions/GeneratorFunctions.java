package Functions;

import Models.Player;
import Models.Position;

public class GeneratorFunctions {
    public static String getRandomId() {
        String id = "";
        long millis = System.nanoTime();
        while (millis != 0) {
            id = ((char) ((millis % 26) + 97)) + id;
            millis /= 26;
        }
        return id;
    }

    public static Player getPlayer(String nation_name, int nation_id, int rating, int age, Position position) {
        String name = CatalogueHelper.getPlayerName(nation_name);
        return new Player(name, position, age, nation_id, rating, 0);
    }
}
