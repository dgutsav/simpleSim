import java.util.ArrayList;
import java.util.List;

public class PositionHelper {

    private PositionHelper() {
    }

    public static final int GOALKEEPER = 1;
    public static final int DEFENDER = 2;
    public static final int MIDFIELDER = 3;
    public static final int ATTACKER = 4;

    public static int getPositionType(Position position) {
        switch (position) {
            case GOALKEEPER:
                return GOALKEEPER;
            case CENTRE_BACK:
            case RIGHT_BACK:
            case LEFT_BACK:
                return DEFENDER;
            case DEFENSIVE_MIDFIELDER:
            case ATTACKING_MIDFIELDER:
            case CENTRAL_MIDFIELDER:
            case RIGHT_MIDFIELDER:
            case LEFT_MIDFIELDER:
                return MIDFIELDER;
            case LEFT_WINGER:
            case RIGHT_WINGER:
            case STRIKER:
                return ATTACKER;
            default:
                return MIDFIELDER;
        }
    }

    public static String getPositionName(Position position) {
        switch (position) {
            case GOALKEEPER:
                return "GK";
            case CENTRE_BACK:
                return "CB";
            case RIGHT_BACK:
                return "RB";
            case LEFT_BACK:
                return "LB";
            case DEFENSIVE_MIDFIELDER:
                return "DM";
            case ATTACKING_MIDFIELDER:
                return "AM";
            case CENTRAL_MIDFIELDER:
                return "CM";
            case RIGHT_MIDFIELDER:
                return "RM";
            case LEFT_MIDFIELDER:
                return "LM";
            case LEFT_WINGER:
                return "LW";
            case RIGHT_WINGER:
                return "RW";
            case STRIKER:
                return "ST";
            default:
                return "CM";
        }
    }

    public static Position getPosition(int position) {
        return Position.values()[position];
    }

    public static List<Position> getAllPositions() {
        List<Position> positions = new ArrayList<>();
        positions.add(Position.ATTACKING_MIDFIELDER);
        positions.add(Position.CENTRAL_MIDFIELDER);
        positions.add(Position.CENTRE_BACK);
        positions.add(Position.DEFENSIVE_MIDFIELDER);
        positions.add(Position.RIGHT_BACK);
        positions.add(Position.RIGHT_MIDFIELDER);
        positions.add(Position.RIGHT_WINGER);
        positions.add(Position.LEFT_BACK);
        positions.add(Position.LEFT_MIDFIELDER);
        positions.add(Position.LEFT_WINGER);
        positions.add(Position.STRIKER);
        positions.add(Position.GOALKEEPER);
        return positions;
    }

    public static void main(String[] args) {
        System.out.println(getPositionName(getPosition(5)));
    }
}
