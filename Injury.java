public class Injury {
    public float effect;
    public InjuryType injury_type;
    private int games_missed = 0;

    public Injury (InjuryType type, float f) {
        this.effect = f;
        this.injury_type = type;
        this.games_missed = 0;
    }

    public int getGames() {
        switch(this.injury_type) {
            case CRUCIATE_LIGAMENT:
                return 30 - this.games_missed;
            case CALF:
                return 3 - this.games_missed;
            case HAMSTRING:
                return 5 - this.games_missed;
            case CONCUSSION:
                return 2 - this.games_missed;
            case FRACTURE:
                return 10 - this.games_missed;
            case SICKNESS:
                return 1 - this.games_missed;
            default:
                return 0;
        }
    }

    public void gameMissed() {
        this.games_missed++;
    }

    public static InjuryType getInjury(int id) {
        switch(id) {
            case 0:
                return InjuryType.CALF;
            case 1:
                return InjuryType.HAMSTRING;
            case 2:
                return InjuryType.CONCUSSION;
            case 3:
                return InjuryType.FRACTURE;
            case 4:
                return InjuryType.CRUCIATE_LIGAMENT;
            case 5:
                return InjuryType.SICKNESS;
            default:
                return InjuryType.SICKNESS;
        }
    }

    public float getEffect() {
        return this.effect;
    }
}
