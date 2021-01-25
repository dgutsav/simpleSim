package Models;

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

    public static double getPositionSimilarity(Position a, Position b) {
        if (a == b) {
            return 1;
        } else {
            if (a == Position.GOALKEEPER) {
                return 0;
            } else if (a == Position.CENTRE_BACK) {
                if (b == Position.DEFENSIVE_MIDFIELDER || b == Position.LEFT_BACK || b == Position.RIGHT_BACK) {
                    return 0.8;
                } else {
                    return 0;
                }
            } else if (a == Position.RIGHT_BACK) {
                if (b == Position.RIGHT_MIDFIELDER) {
                    return 0.9;
                } else if (b == Position.CENTRE_BACK) {
                    return 0.8;
                } else if (b == Position.LEFT_BACK) {
                    return 0.75;
                } else {
                    return 0;
                }
            } else if (a == Position.LEFT_BACK) {
                if (b == Position.LEFT_MIDFIELDER) {
                    return 0.9;
                } else if (b == Position.CENTRE_BACK) {
                    return 0.8;
                } else if (b == Position.RIGHT_BACK) {
                    return 0.75;
                } else {
                    return 0;
                }
            } else if (a == Position.DEFENSIVE_MIDFIELDER) {
                if (b == Position.CENTRE_BACK) {
                    return 0.8;
                } else if (b == Position.CENTRAL_MIDFIELDER) {
                    return 0.95;
                } else {
                    return 0;
                }
            } else if (a == Position.CENTRAL_MIDFIELDER) {
                if (b == Position.DEFENSIVE_MIDFIELDER) {
                    return 0.95;
                } else if (b == Position.ATTACKING_MIDFIELDER) {
                    return 0.9;
                } else if (b == Position.RIGHT_MIDFIELDER || b == Position.LEFT_MIDFIELDER) {
                    return 0.8;
                } else {
                    return 0;
                }
            } else if (a == Position.ATTACKING_MIDFIELDER) {
                if (b == Position.CENTRAL_MIDFIELDER) {
                    return 0.9;
                } else if (b == Position.STRIKER) {
                    return 0.85;
                } else {
                    return 0;
                }
            } else if (a == Position.STRIKER) {
                if (b == Position.ATTACKING_MIDFIELDER) {
                    return 0.85;
                } else if (b == Position.RIGHT_WINGER || b == Position.LEFT_WINGER) {
                    return 0.85;
                } else {
                    return 0;
                }
            } else if (a == Position.RIGHT_MIDFIELDER) {
                if (b == Position.RIGHT_WINGER) {
                    return 0.95;
                } else if (b == Position.RIGHT_BACK) {
                    return 0.9;
                } else if (b == Position.LEFT_MIDFIELDER) {
                    return 0.85;
                } else if (b == Position.CENTRAL_MIDFIELDER) {
                    return 0.8;
                } else {
                    return 0;
                }
            } else if (a == Position.LEFT_MIDFIELDER) {
                if (b == Position.LEFT_WINGER) {
                    return 0.95;
                } else if (b == Position.LEFT_BACK) {
                    return 0.9;
                } else if (b == Position.RIGHT_MIDFIELDER) {
                    return 0.85;
                } else if (b == Position.CENTRAL_MIDFIELDER) {
                    return 0.8;
                } else {
                    return 0;
                }
            } else if (a == Position.RIGHT_WINGER) {
                if (b == Position.RIGHT_MIDFIELDER) {
                    return 0.95;
                } else if (b == Position.LEFT_WINGER) {
                    return 0.85;
                } else if (b == Position.STRIKER) {
                    return 0.8;
                } else {
                    return 0;
                }
            } else if (a == Position.LEFT_WINGER) {
                if (b == Position.LEFT_MIDFIELDER) {
                    return 0.95;
                } else if (b == Position.RIGHT_WINGER) {
                    return 0.85;
                } else if (b == Position.STRIKER) {
                    return 0.8;
                } else {
                    return 0;
                }
            }
        }
        return 0;
    }
}
