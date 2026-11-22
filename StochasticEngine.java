public class StochasticEngine {
    private static int MATCH_DURATION = 90;

    private static int zone = 5;
    private static int in_posession = 1;
    private static int goals_a = 0, goals_b = 0;

    private static void init() {
        zone = 5;
        in_posession = 1;
    }

    private static void toss() {
        in_posession = (int)(Math.random() * 2) % 2;
    }

    private static int passToZone() {
        return (int)(Math.random() * 9) + 1;
    }

    private static float passSuccessRate(int zoneTo) {
        double passing_probabilities[][] = {
            // A   |   B   |   C   |   D   |   E   |   F   |   G   |   H   |   I
            {0.70, 0.10, 0.05, 0.05, 0.03, 0.02, 0.02, 0.02, 0.01},  // A
            {0.10, 0.70, 0.10, 0.03, 0.02, 0.01, 0.01, 0.01, 0.01},  // B
            {0.05, 0.10, 0.70, 0.02, 0.03, 0.02, 0.01, 0.01, 0.01},  // C
            {0.05, 0.03, 0.02, 0.70, 0.10, 0.05, 0.02, 0.02, 0.01},  // D
            {0.03, 0.02, 0.03, 0.10, 0.70, 0.10, 0.02, 0.02, 0.01},  // E
            {0.02, 0.01, 0.02, 0.05, 0.10, 0.70, 0.01, 0.02, 0.01},  // F
            {0.02, 0.01, 0.01, 0.02, 0.02, 0.01, 0.70, 0.10, 0.05},  // G
            {0.02, 0.01, 0.01, 0.02, 0.02, 0.02, 0.10, 0.70, 0.10},  // H
            {0.01, 0.01, 0.01, 0.01, 0.01, 0.01, 0.05, 0.10, 0.70}   // I
        };

        return (float)(passing_probabilities[zone - 1][zoneTo - 1]);
    }


    private static void print(String str) {
        System.out.println(str);
    }
    
    private static void turnoverPossession() {
        in_posession = in_posession == 1 ? 2 : 1;
        print("Possession turned over to " + in_posession);
    }

    private static void pass() {
        int zonePass = passToZone();
        boolean chance = Math.random() > passSuccessRate(zonePass);
        if (chance) {
            System.out.println("pass to zone " + zonePass);
            zone = zonePass;
        } else {
            turnoverPossession();
        }
    }

    private static float dribbleSuccessRate() {
        return 0.5f;
    }

    private static int dribbleToZone() {
        return (int)(Math.random() * 9) + 1;
    } 


    private static void dribble() {
        int zoneDribble = dribbleToZone();
        boolean success = Math.random() > dribbleSuccessRate();
        if (success) {
            print("dribble to zone " + zoneDribble);
            zone = zoneDribble;
        } else {
            turnoverPossession();
        }
    }

    private static float shootSuccessRate() {
        if (in_posession == 1) {
            if (zone == 8) return 0.5f;
            else if (zone == 7 || zone == 9) return 0.15f;
            
        } else {
            if (zone == 2) return 0.5f;
            else if (zone == 1 || zone == 3) return 0.15f;
        }
        return 0;
    }

    private static void shoot() {
        boolean success = Math.random() > shootSuccessRate();
        if (success) {
            if (in_posession == 1){ 
                goals_a++;
                print("Goal for Team A");
            }
            else {
                goals_b++;
                print("Goal for Team B");
            }
        }
        zone = 5;
        turnoverPossession();
    }

    private static void action() {
        double random = Math.random();
        if (in_posession == 1) {
            if (zone == 1 || zone == 2 || zone == 3) pass();
            else if (zone == 4 || zone == 5 || zone == 6) {
                if (random > 0.6) pass();
                else dribble();
            } else if (zone == 7 || zone == 8 || zone == 9) {
                if (random > 0.45) pass();
                else if (random > 0.8) dribble();
                else shoot();
            }
        }
        else if (in_posession == 2) {
            if (zone == 7 || zone == 8 || zone == 9) pass();
            else if (zone == 4 || zone == 5 || zone == 6) {
                if (random > 0.6) pass();
                else dribble();
            } else if (zone == 1 || zone == 2 || zone == 3) {
                if (random > 0.45) pass();
                else if (random > 0.8) dribble();
                else shoot();
            }
        }
    }

    public static void main (String args[]) {
        init();
        toss();
        for(int i = 0; i< MATCH_DURATION; i++) {
            action();
        }
        print("Team A " + goals_a +" - " + goals_b + " Team B");
    }
}
