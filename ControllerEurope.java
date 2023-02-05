import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ControllerEurope {
    private enum SeedingType {
        CL_GROUP,
        CL_Q4_CP,
        CL_Q3_CP,
        CL_Q3_MP,
        CL_Q2_CP,
        CL_Q2_MP,
        CL_Q1_CP,
        EL_GROUP,
        EL_Q4_MP,
        EL_Q3_MP,
        ECL_Q4,
        ECL_Q3,
        ECL_Q2,
        ECL_Q1,
        DEFAULT
    }

    private enum Stage {
        CL_FINAL("Champions League Final", CCType.StageType.KNOCKOUT, CCType.CompType.CHAMPIONS_LEAGUE, false, false),
        CL_SEMI_FINAL("Champions League Semi-Final", CCType.StageType.TWO_LEGGED_KNOCKOUT,
                CCType.CompType.CHAMPIONS_LEAGUE, false, false),
        CL_QUARTER_FINAL("Champions League Quarter-Final", CCType.StageType.TWO_LEGGED_KNOCKOUT,
                CCType.CompType.CHAMPIONS_LEAGUE, false, false),
        CL_R16("Champions League Round of 16", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.CHAMPIONS_LEAGUE,
                true, false),
        CL_GROUP("CL Group Stage", CCType.StageType.GROUPS, CCType.CompType.CHAMPIONS_LEAGUE, true, false),
        CL_Q4_CP("CL Play-off Champions Path", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.CHAMPIONS_LEAGUE,
                true, true),
        CL_Q4_MP("CL Play-off Main Path", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.CHAMPIONS_LEAGUE, true,
                true),
        CL_Q3_CP("CL Third Qualifying Round Champions Path", CCType.StageType.TWO_LEGGED_KNOCKOUT,
                CCType.CompType.CHAMPIONS_LEAGUE, true, true),
        CL_Q3_MP("CL Third Qualifying Round Main Path", CCType.StageType.TWO_LEGGED_KNOCKOUT,
                CCType.CompType.CHAMPIONS_LEAGUE, true, true),
        CL_Q2_CP("CL Second Qualifying Round Champions Path", CCType.StageType.TWO_LEGGED_KNOCKOUT,
                CCType.CompType.CHAMPIONS_LEAGUE, true, true),
        CL_Q2_MP("CL Second Qualifying Round Main Path", CCType.StageType.TWO_LEGGED_KNOCKOUT,
                CCType.CompType.CHAMPIONS_LEAGUE, true, true),
        CL_Q1_CP("CL First Qualifying Round Champions Path", CCType.StageType.TWO_LEGGED_KNOCKOUT,
                CCType.CompType.EUROPA_LEAGUE, true, true),
        EL_Q3_MP("EL Third Qualifying Round Main Path", CCType.StageType.TWO_LEGGED_KNOCKOUT,
                CCType.CompType.EUROPA_LEAGUE, true, true),
        EL_Q3_CP("EL Third Qualifying Round Chaampions Path", CCType.StageType.TWO_LEGGED_KNOCKOUT,
                CCType.CompType.EUROPA_LEAGUE, true, true),
        EL_Q4_MP("EL Play-off Round Main Path", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.EUROPA_LEAGUE,
                true, true),
        EL_Q4_CP("EL Play-off Round Champions Path", CCType.StageType.TWO_LEGGED_KNOCKOUT,
                CCType.CompType.EUROPA_LEAGUE, true, true),
        EL_GROUP("EL Group Stage", CCType.StageType.GROUPS, CCType.CompType.EUROPA_LEAGUE, true, false),
        EL_KO_PO("Europa League Knockout Play-off", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.EUROPA_LEAGUE,
                true, false),
        EL_R16("Europa League Round of 16", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.EUROPA_LEAGUE, true,
                false),
        EL_QUARTER_FINAL("Europa League Quarter-Final", CCType.StageType.TWO_LEGGED_KNOCKOUT,
                CCType.CompType.EUROPA_LEAGUE, false, false),
        EL_SEMI_FINAL("Europa League Semi-Final", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.EUROPA_LEAGUE,
                false, false),
        EL_FINAL("Europa League Final", CCType.StageType.KNOCKOUT, CCType.CompType.EUROPA_LEAGUE, false, false),
        ECL_FINAL("Europa Conference League Final", CCType.StageType.KNOCKOUT, CCType.CompType.CONFERENCE_LEAGUE, false,
                false),
        ECL_SEMI_FINAL("ECL Semi-Final", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.CONFERENCE_LEAGUE, false,
                false),
        ECL_QUARTER_FINAL("ECL Quarter-Final", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.CONFERENCE_LEAGUE,
                false, false),
        ECL_R16("ECL Round of 16", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.CONFERENCE_LEAGUE, true,
                false),
        ECL_KO_PO("ECL Knockout Play-off", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.CONFERENCE_LEAGUE,
                true, false),
        ECL_GROUP("ECL Group Stage", CCType.StageType.GROUPS, CCType.CompType.CONFERENCE_LEAGUE, true, false),
        ECL_Q1_MP("ECL First Qualifying Round", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.CONFERENCE_LEAGUE,
                true, true),
        ECL_Q2_MP("ECL Second Qualifying Round Main Path", CCType.StageType.TWO_LEGGED_KNOCKOUT,
                CCType.CompType.CONFERENCE_LEAGUE, true, true),
        ECL_Q2_CP("ECL Second Qualifying Round Champions Path", CCType.StageType.TWO_LEGGED_KNOCKOUT,
                CCType.CompType.CONFERENCE_LEAGUE, true, true),
        ECL_Q3_MP("ECL Third Qualifying Round Main Path", CCType.StageType.TWO_LEGGED_KNOCKOUT,
                CCType.CompType.CONFERENCE_LEAGUE, true, true),
        ECL_Q3_CP("ECL Third Qualifying Round Champions Path", CCType.StageType.TWO_LEGGED_KNOCKOUT,
                CCType.CompType.CONFERENCE_LEAGUE, true, true),
        ECL_Q4_MP("ECL Play-off Round Main Path", CCType.StageType.TWO_LEGGED_KNOCKOUT,
                CCType.CompType.CONFERENCE_LEAGUE, true, true),
        ECL_Q4_CP("ECL Play-off Round Champions Path", CCType.StageType.TWO_LEGGED_KNOCKOUT,
                CCType.CompType.CONFERENCE_LEAGUE, true, true),
        DEFAULT("Default", CCType.StageType.KNOCKOUT, CCType.CompType.CHAMPIONS_LEAGUE, false, false);

        private final String stage_name;
        private final CCType.StageType stage_type;
        private final CCType.CompType competition;
        private final boolean nation_protected;
        private final boolean qualifying;

        Stage(String stage_name, CCType.StageType stage_type, CCType.CompType competition, boolean nation_protected,
                boolean qualifying) {
            this.stage_name = stage_name;
            this.stage_type = stage_type;
            this.competition = competition;
            this.nation_protected = nation_protected;
            this.qualifying = qualifying;
        }

        public String stage_name() {
            return this.stage_name;
        }

        public CCType.StageType stage_type() {
            return this.stage_type;
        }

        public CCType.CompType competition() {
            return this.competition;
        }

        public boolean nation_protected() {
            return this.nation_protected;
        }

        public boolean qualifying() {
            return this.qualifying;
        }
    }

    private static Map<Stage, List<KnockoutTie>> stages = new HashMap<>();
    private static Map<String, SeedingType> seedings = new HashMap<>();
    private static Map<String, Integer> team_nations = new HashMap<>();

    private static List<String> bye_teams = new ArrayList<>();
    private static List<String> top_dom_champions = new ArrayList<>();

    private static Map<Stage, League[]> groups = new HashMap<>();

    private static int getPlaces(CCType.CompType type, int rank) {
        switch (type) {
            case CHAMPIONS_LEAGUE:
                if (rank <= 4)
                    return 4;
                else if (rank <= 6)
                    return 3;
                else if (rank <= 15)
                    return 2;
                else
                    return 1;
            case EUROPA_LEAGUE:
                if (rank <= 5)
                    return 2;
                else if (rank <= 15)
                    return 1;
                else
                    return 0;
            case CONFERENCE_LEAGUE:
                if (rank <= 5)
                    return 1;
                else if (rank <= 15)
                    return 2;
                else
                    return 3;
        }
        return 0;
    }

    private static Map<CCType.CompType, List<String>> getTeams(Nation nation, int ranking) {
        List<String> teams = new ArrayList<>();
        int cl_places = getPlaces(CCType.CompType.CHAMPIONS_LEAGUE, ranking);
        int el_places = getPlaces(CCType.CompType.EUROPA_LEAGUE, ranking);
        int ecl_places = getPlaces(CCType.CompType.CONFERENCE_LEAGUE, ranking);
        for (int i = 1; i <= cl_places + el_places + ecl_places; i++) {
            teams.add(nation.getLeague(1).getTeamAtRank(i));
        }
        String cw = nation.getCupWinner();
        if (!teams.contains(cw)) {
            teams.add(cl_places, cw);
            teams.remove(teams.size() - 1);
        }

        Map<CCType.CompType, List<String>> map = new HashMap<>();
        map.put(CCType.CompType.CHAMPIONS_LEAGUE, teams.subList(0, cl_places));
        map.put(CCType.CompType.EUROPA_LEAGUE, teams.subList(cl_places, cl_places + el_places));
        map.put(CCType.CompType.CONFERENCE_LEAGUE,
                teams.subList(cl_places + el_places, cl_places + el_places + ecl_places));
        return map;
    }

    private static SeedingType getSeeding(CCType.CompType type, int league_rank, int rank) {
        switch (type) {
            case CHAMPIONS_LEAGUE:
                if (league_rank <= 4 && rank <= 4)
                    return SeedingType.CL_GROUP;
                else if (league_rank <= 6) {
                    if (rank <= 2)
                        return SeedingType.CL_GROUP;
                    else if (rank <= 3)
                        return SeedingType.CL_Q3_MP;
                } else if (league_rank <= 9) {
                    if (rank <= 1)
                        return SeedingType.CL_GROUP;
                    else if (rank <= 2)
                        return SeedingType.CL_Q3_MP;
                } else if (league_rank <= 12) {
                    if (rank <= 1)
                        return SeedingType.CL_GROUP;
                    else if (rank <= 2)
                        return SeedingType.CL_Q2_MP;
                } else if (league_rank <= 14) {
                    if (rank <= 1)
                        return SeedingType.CL_Q4_CP;
                    else if (rank <= 2)
                        return SeedingType.CL_Q2_MP;
                } else if (league_rank <= 15) {
                    if (rank <= 1)
                        return SeedingType.CL_Q3_CP;
                    else if (rank <= 2)
                        return SeedingType.CL_Q2_MP;
                } else if (league_rank <= 18 && rank <= 1)
                    return SeedingType.CL_Q3_CP;
                else if (league_rank <= 20 && rank <= 1)
                    return SeedingType.CL_Q2_CP;
                else if (league_rank <= 48 && rank <= 1)
                    return SeedingType.CL_Q1_CP;
            case EUROPA_LEAGUE:
                if (league_rank <= 9)
                    return SeedingType.EL_GROUP;
                else if (league_rank <= 12)
                    return SeedingType.EL_Q4_MP;
                else if (league_rank <= 15)
                    return SeedingType.EL_Q3_MP;
            case CONFERENCE_LEAGUE:
                if (league_rank <= 5)
                    return SeedingType.ECL_Q4;
                else if (league_rank <= 10 && rank <= 1)
                    return SeedingType.ECL_Q4;
                else if (league_rank <= 10)
                    return SeedingType.ECL_Q3;
                else if (league_rank <= 15 && rank <= 1)
                    return SeedingType.ECL_Q3;
                else if (league_rank <= 27 && rank <= 1)
                    return SeedingType.ECL_Q3;
                else if (league_rank <= 15)
                    return SeedingType.ECL_Q2;
                else if (league_rank <= 25 && rank <= 2)
                    return SeedingType.ECL_Q2;
                else if (rank <= 1)
                    return SeedingType.ECL_Q2;
                else
                    return SeedingType.ECL_Q1;
        }
        return SeedingType.DEFAULT;
    }

    private static void setSeedings() {
        List<Nation> nations = Sim.getNationFromContinent("Europe");
        Map<Integer, Integer> league_rankings = getLeagueRankingsForSeason(Sim.getSeasonNumber());
        for (Nation nation : nations) {
            int ranking = league_rankings.get(nation.getId());
            Map<CCType.CompType, List<String>> teams = getTeams(nation, ranking);
            for (Map.Entry<CCType.CompType, List<String>> entry : teams.entrySet()) {
                CCType.CompType type = entry.getKey();
                List<String> nat_teams = entry.getValue();
                for (int i = 0; i < nat_teams.size(); i++) {
                    int rank = i + 1;
                    SeedingType seeding = getSeeding(type, ranking, rank);
                    seedings.put(nat_teams.get(i), seeding);
                    team_nations.put(nat_teams.get(i), nation.getId());
                    if (rank == 1 && ranking <= 8 && type == CCType.CompType.CHAMPIONS_LEAGUE) {
                        top_dom_champions.add(nat_teams.get(i));
                    }
                }
            }
        }
    }

    private static List<String> getTeamsWithSeeding(SeedingType seedingType) {
        List<String> teams_list = new ArrayList<>();
        for (Map.Entry<String, SeedingType> entry : seedings.entrySet()) {
            if (entry.getValue() == seedingType)
                teams_list.add(entry.getKey());
        }
        return teams_list;
    }

    private static List<String> getOtherTeamsFromNation(String team) {
        List<String> teams = new ArrayList<>();
        int nation_id = team_nations.get(team);
        for (Map.Entry<String, Integer> entry : team_nations.entrySet()) {
            if (entry.getValue() == nation_id)
                teams.add(entry.getKey());
        }
        return teams;
    }

    private static List<Stage> getPreviousWinnerStages(Stage stage) {
        List<Stage> stages = new ArrayList<>();
        switch (stage) {
            case CL_GROUP:
                stages.add(Stage.CL_Q4_CP);
                stages.add(Stage.CL_Q4_MP);
                break;
            case CL_Q4_CP:
                stages.add(Stage.CL_Q3_CP);
                break;
            case CL_Q3_CP:
                stages.add(Stage.CL_Q2_CP);
                break;
            case CL_Q2_CP:
                stages.add(Stage.CL_Q1_CP);
                break;
            case CL_Q4_MP:
                stages.add(Stage.CL_Q3_MP);
                break;
            case CL_Q3_MP:
                stages.add(Stage.CL_Q2_MP);
                break;
            case EL_Q4_MP:
                stages.add(Stage.EL_Q3_MP);
                break;
            case EL_Q4_CP:
                stages.add(Stage.EL_Q3_CP);
                break;
            case EL_GROUP:
                stages.add(Stage.EL_Q4_CP);
                stages.add(Stage.EL_Q4_MP);
                break;
            case ECL_Q4_CP:
                stages.add(Stage.ECL_Q3_CP);
                break;
            case ECL_Q4_MP:
                stages.add(Stage.ECL_Q3_MP);
                break;
            case ECL_Q3_CP:
                stages.add(Stage.ECL_Q2_CP);
                break;
            case ECL_Q3_MP:
                stages.add(Stage.ECL_Q2_MP);
                break;
            case ECL_Q2_MP:
                stages.add(Stage.ECL_Q1_MP);
                break;
            case ECL_GROUP:
                stages.add(Stage.ECL_Q4_CP);
                stages.add(Stage.ECL_Q4_MP);
                break;
            case CL_QUARTER_FINAL:
                stages.add(Stage.CL_R16);
                break;
            case EL_QUARTER_FINAL:
                stages.add(Stage.EL_R16);
                break;
            case ECL_QUARTER_FINAL:
                stages.add(Stage.ECL_R16);
                break;
            case CL_SEMI_FINAL:
                stages.add(Stage.CL_QUARTER_FINAL);
                break;
            case EL_SEMI_FINAL:
                stages.add(Stage.EL_QUARTER_FINAL);
                break;
            case ECL_SEMI_FINAL:
                stages.add(Stage.ECL_QUARTER_FINAL);
                break;
            case CL_FINAL:
                stages.add(Stage.CL_SEMI_FINAL);
                break;
            case EL_FINAL:
                stages.add(Stage.EL_SEMI_FINAL);
                break;
            case ECL_FINAL:
                stages.add(Stage.ECL_SEMI_FINAL);
                break;
            default:
                break;
        }
        return stages;
    }

    private static List<Stage> getPreviousLoserStages(Stage stage) {
        List<Stage> stages = new ArrayList<>();
        switch (stage) {
            case EL_Q3_CP:
                stages.add(Stage.CL_Q2_CP);
                break;
            case EL_Q3_MP:
                stages.add(Stage.CL_Q2_MP);
                break;
            case EL_Q4_CP:
                stages.add(Stage.CL_Q3_CP);
                break;
            case EL_GROUP:
                stages.add(Stage.CL_Q4_CP);
                stages.add(Stage.CL_Q4_MP);
                stages.add(Stage.CL_Q3_MP);
                break;
            case ECL_Q2_CP:
                stages.add(Stage.CL_Q1_CP);
                break;
            case ECL_Q4_CP:
                stages.add(Stage.EL_Q3_CP);
                break;
            case ECL_Q4_MP:
                stages.add(Stage.EL_Q3_MP);
                break;
            case ECL_GROUP:
                stages.add(Stage.EL_Q4_CP);
                stages.add(Stage.EL_Q4_MP);
                break;
            default:
                break;
        }
        return stages;
    }

    private static Stage getNextStage(Stage type, boolean winner) {
        switch (type) {
            case CL_FINAL:
                return Stage.DEFAULT;
            case CL_SEMI_FINAL:
                return winner ? Stage.CL_FINAL : Stage.DEFAULT;
            case CL_QUARTER_FINAL:
                return winner ? Stage.CL_SEMI_FINAL : Stage.DEFAULT;
            case CL_R16:
                return winner ? Stage.CL_QUARTER_FINAL : Stage.DEFAULT;
            case CL_GROUP:
                return winner ? Stage.CL_R16 : Stage.DEFAULT;
            case CL_Q4_CP:
                return winner ? Stage.CL_GROUP : Stage.EL_GROUP;
            case CL_Q4_MP:
                return winner ? Stage.CL_GROUP : Stage.EL_GROUP;
            case CL_Q3_CP:
                return winner ? Stage.CL_Q4_CP : Stage.EL_Q4_CP;
            case CL_Q3_MP:
                return winner ? Stage.CL_Q4_MP : Stage.EL_GROUP;
            case CL_Q2_CP:
                return winner ? Stage.CL_Q3_CP : Stage.EL_Q3_CP;
            case CL_Q2_MP:
                return winner ? Stage.CL_Q3_MP : Stage.EL_Q3_MP;
            case CL_Q1_CP:
                return winner ? Stage.CL_Q2_CP : Stage.ECL_Q2_CP; // 2 will be given byes
            case EL_Q3_MP:
                return winner ? Stage.EL_Q4_MP : Stage.ECL_Q4_MP;
            case EL_Q3_CP:
                return winner ? Stage.EL_Q4_CP : Stage.ECL_Q4_CP;
            case EL_Q4_MP:
                return winner ? Stage.EL_GROUP : Stage.ECL_GROUP;
            case EL_Q4_CP:
                return winner ? Stage.EL_GROUP : Stage.ECL_GROUP;
            case EL_GROUP:
                return winner ? Stage.EL_KO_PO : Stage.DEFAULT;
            case EL_KO_PO:
                return winner ? Stage.EL_R16 : Stage.DEFAULT;
            case EL_R16:
                return winner ? Stage.EL_QUARTER_FINAL : Stage.DEFAULT;
            case EL_QUARTER_FINAL:
                return winner ? Stage.EL_SEMI_FINAL : Stage.DEFAULT;
            case EL_SEMI_FINAL:
                return winner ? Stage.EL_FINAL : Stage.DEFAULT;
            case EL_FINAL:
                return Stage.DEFAULT;
            case ECL_FINAL:
                return Stage.DEFAULT;
            case ECL_SEMI_FINAL:
                return winner ? Stage.ECL_FINAL : Stage.DEFAULT;
            case ECL_QUARTER_FINAL:
                return winner ? Stage.ECL_SEMI_FINAL : Stage.DEFAULT;
            case ECL_R16:
                return winner ? Stage.ECL_QUARTER_FINAL : Stage.DEFAULT;
            case ECL_KO_PO:
                return winner ? Stage.ECL_R16 : Stage.DEFAULT;
            case ECL_GROUP:
                return winner ? Stage.ECL_KO_PO : Stage.DEFAULT;
            case ECL_Q1_MP:
                return winner ? Stage.ECL_Q2_MP : Stage.DEFAULT;
            case ECL_Q2_MP:
                return winner ? Stage.ECL_Q3_MP : Stage.DEFAULT;
            case ECL_Q2_CP:
                return winner ? Stage.ECL_Q3_CP : Stage.DEFAULT;
            case ECL_Q3_MP:
                return winner ? Stage.ECL_Q4_MP : Stage.DEFAULT;
            case ECL_Q3_CP:
                return winner ? Stage.ECL_Q4_CP : Stage.DEFAULT;
            case ECL_Q4_MP:
                return winner ? Stage.ECL_GROUP : Stage.DEFAULT;
            case ECL_Q4_CP:
                return winner ? Stage.ECL_GROUP : Stage.DEFAULT;
            case DEFAULT:
                return Stage.DEFAULT;
        }
        return Stage.DEFAULT;
    }

    private static SeedingType getSeedingTypeForStage(Stage stage) {
        switch (stage) {
            case CL_GROUP:
                return SeedingType.CL_GROUP;
            case CL_Q4_CP:
                return SeedingType.CL_Q4_CP;
            case CL_Q3_CP:
                return SeedingType.CL_Q3_CP;
            case CL_Q3_MP:
                return SeedingType.CL_Q3_MP;
            case CL_Q2_CP:
                return SeedingType.CL_Q2_CP;
            case CL_Q2_MP:
                return SeedingType.CL_Q2_MP;
            case CL_Q1_CP:
                return SeedingType.CL_Q1_CP;
            case EL_Q3_MP:
                return SeedingType.EL_Q3_MP;
            case EL_Q4_MP:
                return SeedingType.EL_Q4_MP;
            case ECL_Q1_MP:
                return SeedingType.ECL_Q1;
            case ECL_Q2_MP:
                return SeedingType.ECL_Q2;
            case ECL_Q3_MP:
                return SeedingType.ECL_Q3;
            case ECL_Q4_MP:
                return SeedingType.ECL_Q4;
            case EL_GROUP:
                return SeedingType.EL_GROUP;
            default:
                break;
        }
        return SeedingType.DEFAULT;
    }

    private static boolean isStageKOPO(Stage stage) {
        return (stage == Stage.EL_KO_PO || stage == Stage.ECL_KO_PO);
    }

    private static boolean isStageR16(Stage stage) {
        return (stage == Stage.EL_R16 || stage == Stage.ECL_R16 || stage == Stage.CL_R16);
    }

    private static boolean isStageGroup(Stage stage) {
        return stage.stage_type() == CCType.StageType.GROUPS;
    }

    private static boolean isStageNationProtected(Stage stage) {
        return stage.nation_protected();
    }

    private static List<String> filterTeamsFromNation(String team, List<String> teams) {
        List<String> same_nat = getOtherTeamsFromNation(team);
        List<String> filtered = new ArrayList<>();
        for (String fTeam : teams) {
            if (!same_nat.contains(fTeam))
                filtered.add(fTeam);
        }
        return filtered;
    }

    private static String preventZeroChoices(List<String> pot1, List<String> pot2) {
        String min_team = null;
        int min_options = 1000;
        for (String team : pot1) {
            List<String> filtered = filterTeamsFromNation(team, pot2);
            if (min_options > filtered.size()) {
                min_options = filtered.size();
                min_team = team;
            }
        }
        return min_team;
    }

    private static String preventZeroGroups(List<String> pot, int pot_number, String[][] groups) {
        String min_team = null;
        int min_options = 1000;
        for (String team : pot) {
            List<Integer> options = getGroupOptions(team, pot_number, groups);
            if (options.size() < min_options) {
                min_options = options.size();
                min_team = team;
            }
        }
        return min_team;
    }

    private static boolean fromSameGroup(String a, String b) {
        for (Map.Entry<Stage, League[]> entry : groups.entrySet()) {
            League[] leagues = entry.getValue();
            for (League league : leagues) {
                List<String> teams = league.getTeams();
                if (teams.contains(a) && teams.contains(b))
                    return true;
            }
        }
        return false;
    }

    private static boolean fromSameNation(String a, String b) {
        return team_nations.get(a) == team_nations.get(b);
    }

    private static String preventZeroGroupsNations(List<String> pot1, List<String> pot2) {
        int min_options = 1000;
        String min_team = null;
        for (String team : pot1) {
            int options = 0;
            for (String oTeam : pot2) {
                if (!fromSameNation(team, oTeam) && !fromSameGroup(team, oTeam))
                    options++;
            }
            if (options < min_options) {
                min_options = options;
                min_team = team;
            }
        }
        return min_team;
    }

    private static List<String> filterNationGroup(String team, List<String> pot) {
        List<String> filtered = new ArrayList<>();
        for (String altTeam : pot) {
            if (!fromSameNation(team, altTeam) && !fromSameGroup(team, altTeam))
                filtered.add(altTeam);
        }
        return filtered;
    }

    private static List<Integer> getGroupOptions(String team, int pot_number, String[][] groups) {
        List<Integer> options = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            boolean has_same_nation = false;
            for (int j = 0; j < pot_number; j++) {
                if (team_nations.get(team) == team_nations.get(groups[i][j])) {
                    has_same_nation = true;
                }
            }
            if (!has_same_nation && groups[i][pot_number] == null) {
                options.add(i);
            }
        }
        return options;
    }

    private static ArrayList<String> removeFromList(ArrayList<String> list, String to_remove) {
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equalsIgnoreCase(to_remove)) {
                index = i;
                break;
            }
        }
        list.remove(index);
        return list;
    }

    private static List<String> adjustECLQ2CH(List<String> teams) {
        teams = CommonFunctions.sortByCoefficients(teams);
        List<String> adjusted = new ArrayList<>();
        adjusted.add(teams.remove(1));
        adjusted.add(teams.remove(0));
        bye_teams = adjusted;
        return teams;
    }

    private static List<String> adjustForCLGroups(List<String> teams) {
        List<String> ranked = new ArrayList<>();
        ranked.addAll(top_dom_champions);
        for (String rank : ranked) {
            teams.remove(rank);
        }
        ranked.addAll(teams);
        return ranked;
    }

    private static String[][] divideIntoPots(List<String> teams) {
        String[][] pots = new String[4][8];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                pots[i][j] = teams.get((i * 8) + j);
            }
        }
        return pots;
    }

    private static List<String> getWinnersFromStage(Stage stage) {
        List<String> winners = new ArrayList<>();
        List<KnockoutTie> ties = stages.get(stage);
        for (KnockoutTie tie : ties) {
            winners.add(tie.getWinner());
        }
        return winners;
    }

    private static List<String> getLosersFromStage(Stage stage) {
        List<String> losers = new ArrayList<>();
        List<KnockoutTie> ties = stages.get(stage);
        for (KnockoutTie tie : ties) {
            losers.add(tie.getLoser());
        }
        return losers;
    }

    private static void setStage(Stage type) {
        List<String> teams = new ArrayList<>();
        SeedingType seeding = getSeedingTypeForStage(type);
        if (seeding != SeedingType.DEFAULT) {
            teams.addAll(getTeamsWithSeeding(seeding));
        }

        List<Stage> win_stages = getPreviousWinnerStages(type);
        for (Stage stage : win_stages) {
            teams.addAll(getWinnersFromStage(stage));
        }

        List<Stage> loss_stages = getPreviousLoserStages(type);
        for (Stage stage : loss_stages) {
            teams.addAll(getLosersFromStage(stage));
        }

        if (type == Stage.ECL_Q2_CP) {
            teams = adjustECLQ2CH(teams);
        }

        if (type == Stage.ECL_Q3_CP) {
            teams.addAll(bye_teams);
        }

        if (!isStageGroup(type)) {
            if (teams.size() % 2 != 0)
                System.out.println("Irregular no of teams found for stage: " + type);
            teams = CommonFunctions.sortByCoefficients(teams);
            int len = teams.size();
            ArrayList<String> seeded = new ArrayList<>();
            seeded.addAll(teams.subList(0, len / 2));
            ArrayList<String> unseeded = new ArrayList<>();
            unseeded.addAll(teams.subList(len / 2, len));
            List<KnockoutTie> ties = new ArrayList<>();
            int ties_size = teams.size() / 2;
            for (int i = 0; i < ties_size; i++) {
                String selected_team = CommonFunctions.getRandomElement(seeded);
                if (isStageNationProtected(type)) {
                    String zero_choice = preventZeroChoices(seeded, unseeded);
                    if (zero_choice != null)
                        selected_team = zero_choice;
                }
                List<String> filtered = unseeded;
                if (isStageNationProtected(type)) {
                    filtered = filterTeamsFromNation(selected_team, unseeded);
                }
                if (filtered.size() == 0)
                    System.out.println("No team found in stage: " + type);
                String selected_team_2 = CommonFunctions.getRandomElement(filtered);
                KnockoutTie tie = new KnockoutTie(selected_team, selected_team_2, 1);
                ties.add(tie);
                seeded = removeFromList(seeded, selected_team);
                unseeded = removeFromList(unseeded, selected_team_2);
            }
            stages.put(type, ties);
        } else {
            teams = CommonFunctions.sortByCoefficients(teams);
            if (type == Stage.CL_GROUP) {
                teams = adjustForCLGroups(teams);
            }
            String[][] pots = divideIntoPots(teams);
            String[][] groups_matrix = drawGroups(pots);
            League[] stage_groups = new League[8];
            for (int i = 0; i < 8; i++) {
                League league = new League();
                for (int j = 0; j < 4; j++) {
                    league.addTeam(groups_matrix[i][j]);
                }
                league.setFixtures();
                league.setupTable();
                stage_groups[i] = league;
            }
            groups.put(type, stage_groups);
        }
    }

    private static void setKOPO_R16Stage(Stage stage) {
        ArrayList<String> seeded = new ArrayList<>();
        ArrayList<String> unseeded = new ArrayList<>();

        if (stage == Stage.CL_R16) {
            seeded.addAll(getGroupTeamsAtRank(Stage.CL_GROUP, 1));
            unseeded.addAll(getGroupTeamsAtRank(Stage.CL_GROUP, 2));
        } else if (stage == Stage.EL_KO_PO) {
            seeded.addAll(getGroupTeamsAtRank(Stage.EL_GROUP, 2));
            unseeded.addAll(getGroupTeamsAtRank(Stage.CL_GROUP, 3));
        } else if (stage == Stage.ECL_KO_PO) {
            seeded.addAll(getGroupTeamsAtRank(Stage.ECL_GROUP, 2));
            unseeded.addAll(getGroupTeamsAtRank(Stage.EL_GROUP, 3));
        } else if (stage == Stage.EL_R16) {
            unseeded.addAll(getWinnersFromStage(Stage.EL_KO_PO));
            seeded.addAll(getGroupTeamsAtRank(Stage.EL_GROUP, 1));
        } else if (stage == Stage.ECL_R16) {
            unseeded.addAll(getWinnersFromStage(Stage.ECL_KO_PO));
            seeded.addAll(getGroupTeamsAtRank(Stage.ECL_GROUP, 1));
        }

        List<KnockoutTie> ties = new ArrayList<>();
        int ties_size = seeded.size();
        for (int i = 0; i < ties_size; i++) {
            String selected_team = CommonFunctions.getRandomElement(seeded);
            String zero_choice = preventZeroGroupsNations(seeded, unseeded);
            if (zero_choice != null)
                selected_team = zero_choice;
            List<String> filtered = filterNationGroup(selected_team, unseeded);
            if (filtered.size() == 0)
                System.out.println("No team found in stage: " + stage);
            String selected_team_2 = CommonFunctions.getRandomElement(filtered);
            KnockoutTie tie = new KnockoutTie(selected_team, selected_team_2, 1);
            ties.add(tie);
            seeded = removeFromList(seeded, selected_team);
            unseeded = removeFromList(unseeded, selected_team_2);
        }
        stages.put(stage, ties);
    }

    private static void setQ1Stages() {
        setStage(Stage.CL_Q1_CP);
        setStage(Stage.ECL_Q1_MP);
    }

    private static void simulateQ1Stages() {
        simulateStage(Stage.CL_Q1_CP);
        simulateStage(Stage.ECL_Q1_MP);
    }

    private static void setQ2Stages() {
        setStage(Stage.CL_Q2_CP);
        setStage(Stage.CL_Q2_MP);
        setStage(Stage.ECL_Q2_MP);
        setStage(Stage.ECL_Q2_CP);
    }

    private static void simulateQ2Stages() {
        simulateStage(Stage.CL_Q2_CP);
        simulateStage(Stage.CL_Q2_MP);
        simulateStage(Stage.ECL_Q2_MP);
        simulateStage(Stage.ECL_Q2_CP);
    }

    private static void setQ3Stages() {
        setStage(Stage.CL_Q3_CP);
        setStage(Stage.CL_Q3_MP);
        setStage(Stage.EL_Q3_CP);
        setStage(Stage.EL_Q3_MP);
        setStage(Stage.ECL_Q3_CP);
        setStage(Stage.ECL_Q3_MP);
    }

    private static void simulateQ3Stages() {
        simulateStage(Stage.CL_Q3_CP);
        simulateStage(Stage.CL_Q3_MP);
        simulateStage(Stage.EL_Q3_CP);
        simulateStage(Stage.EL_Q3_MP);
        simulateStage(Stage.ECL_Q3_CP);
        simulateStage(Stage.ECL_Q3_MP);
    }

    private static void setQ4Stages() {
        setStage(Stage.CL_Q4_CP);
        setStage(Stage.CL_Q4_MP);
        setStage(Stage.EL_Q4_CP);
        setStage(Stage.EL_Q4_MP);
        setStage(Stage.ECL_Q4_CP);
        setStage(Stage.ECL_Q4_MP);
    }

    private static void simulateQ4Stages() {
        simulateStage(Stage.CL_Q4_CP);
        simulateStage(Stage.CL_Q4_MP);
        simulateStage(Stage.EL_Q4_CP);
        simulateStage(Stage.EL_Q4_MP);
        simulateStage(Stage.ECL_Q4_CP);
        simulateStage(Stage.ECL_Q4_MP);
    }

    private static void setGroups() {
        setStage(Stage.CL_GROUP);
        setStage(Stage.EL_GROUP);
        setStage(Stage.ECL_GROUP);
    }

    private static String[][] drawGroups(String[][] pots) {
        String[][] groups = new String[8][4];
        for (int i = 0; i < 8; i++) {
            groups[i][0] = pots[0][i];
        }
        for (int i = 1; i < 4; i++) {
            ArrayList<String> pot_teams = new ArrayList<>();
            pot_teams.addAll(Arrays.asList(pots[i]));
            while (!pot_teams.isEmpty()) {
                String selected_team = CommonFunctions.getRandomElement(pot_teams);
                String zero_team = preventZeroGroups(pot_teams, i, groups);
                if (zero_team != null)
                    selected_team = zero_team;
                List<Integer> options = getGroupOptions(selected_team, i, groups);
                int group = CommonFunctions.getRandomIntElement(options);
                groups[group][i] = selected_team;
                pot_teams = removeFromList(pot_teams, selected_team);
            }
        }
        return groups;
    }

    private static void simulateGroupsMatchDay(Stage stage, int matchday) {
        League[] gLeagues = groups.get(stage);
        for (int i = 0; i < gLeagues.length; i++) {
            char ch = (char) (65 + i);
            System.out.println(stage.stage_name() + ": Group " + ch + " Matchday " + matchday);
            gLeagues[i].simulateMatchDay(matchday);
        }
    }

    private static void dispGroupTables(Stage stage) {
        System.out.println("\n" + stage.stage_name());
        League[] gLeagues = groups.get(stage);
        for (int i = 0; i < gLeagues.length; i++) {
            char ch = (char) (65 + i);
            System.out.println("Group " + ch);
            gLeagues[i].getTable().showLong();
        }
    }

    private static void simulateGroupStage() {
        for (int i = 1; i <= 6; i++) {
            simulateGroupsMatchDay(Stage.CL_GROUP, i);
            simulateGroupsMatchDay(Stage.EL_GROUP, i);
            simulateGroupsMatchDay(Stage.ECL_GROUP, i);
        }
        // Show Tables
        System.out.println("\nStandings");
        dispGroupTables(Stage.CL_GROUP);
        dispGroupTables(Stage.EL_GROUP);
        dispGroupTables(Stage.ECL_GROUP);
    }

    private static List<String> getGroupTeamsAtRank(Stage stage, int rank) {
        League[] leagues = groups.get(stage);
        List<String> teams = new ArrayList<>();
        for (int i = 0; i < leagues.length; i++) {
            teams.add(leagues[i].getTeamAtRank(rank));
        }
        return teams;
    }

    private static void setup() {
        stages = new HashMap<>();
        seedings = new HashMap<>();
        team_nations = new HashMap<>();
        bye_teams = new ArrayList<>();
        groups = new HashMap<>();
        top_dom_champions = new ArrayList<>();
        setSeedings();
    }

    // private static void dispStages() {
    // for (Map.Entry<Stage, List<KnockoutTie>> entry : stages.entrySet()) {
    // List<KnockoutTie> ties = entry.getValue();
    // int same_count = 0;
    // for (KnockoutTie tie : ties) {
    // System.out.println(getTeamName(tie.getTeam1()) + " v " +
    // getTeamName(tie.getTeam2()));
    // if (team_nations.get(tie.getTeam1()) == team_nations.get(tie.getTeam2())) {
    // System.out.println("Same nation");
    // same_count++;
    // }
    // }
    // System.out.println("Same count for stage: " + same_count);
    // }
    // }

    private static void setKnockouts1() {
        setKOPO_R16Stage(Stage.ECL_KO_PO);
        setKOPO_R16Stage(Stage.EL_KO_PO);
    }

    private static void setKnockouts2() {
        setKOPO_R16Stage(Stage.CL_R16);
        setKOPO_R16Stage(Stage.EL_R16);
        setKOPO_R16Stage(Stage.ECL_R16);
    }

    private static void simulateKnockouts1() {
        simulateStage(Stage.ECL_KO_PO);
        simulateStage(Stage.EL_KO_PO);
    }

    private static void simulateKnockouts2() {
        simulateStage(Stage.CL_R16);
        simulateStage(Stage.EL_R16);
        simulateStage(Stage.ECL_R16);
    }

    private static void setQuarterFinals() {
        setStage(Stage.CL_QUARTER_FINAL);
        setStage(Stage.EL_QUARTER_FINAL);
        setStage(Stage.ECL_QUARTER_FINAL);
    }

    private static void simulateQuarterFinals() {
        simulateStage(Stage.CL_QUARTER_FINAL);
        simulateStage(Stage.EL_QUARTER_FINAL);
        simulateStage(Stage.ECL_QUARTER_FINAL);
    }

    private static void setSemiFinals() {
        setStage(Stage.CL_SEMI_FINAL);
        setStage(Stage.EL_SEMI_FINAL);
        setStage(Stage.ECL_SEMI_FINAL);
    }

    private static void simulateSemiFinals() {
        simulateStage(Stage.CL_SEMI_FINAL);
        simulateStage(Stage.EL_SEMI_FINAL);
        simulateStage(Stage.ECL_SEMI_FINAL);
    }

    private static void setFinal() {
        setStage(Stage.CL_FINAL);
        setStage(Stage.EL_FINAL);
        setStage(Stage.ECL_FINAL);
    }

    private static void simulateFinal() {
        simulateStage(Stage.CL_FINAL);
        simulateStage(Stage.EL_FINAL);
        simulateStage(Stage.ECL_FINAL);
    }

    public static void simulate() {
        setup();
        setQ1Stages();
        simulateQ1Stages();
        setQ2Stages();
        simulateQ2Stages();
        setQ3Stages();
        simulateQ3Stages();
        setQ4Stages();
        simulateQ4Stages();
        setGroups();
        simulateGroupStage();
        setKnockouts1();
        simulateKnockouts1();
        setKnockouts2();
        simulateKnockouts2();
        setQuarterFinals();
        simulateQuarterFinals();
        setSemiFinals();
        simulateSemiFinals();
        setFinal();
        simulateFinal();
        postSeasonChanges();
    }

    private static void simulateStage(Stage stage) {
        System.out.println("\n" + stage.stage_name);
        List<KnockoutTie> ties = stages.get(stage);
        for (int i = 0; i < ties.size(); i++) {
            ties.get(i).simulate();
            System.out.print("Match " + (i + 1) + ": ");
            ties.get(i).show();
        }
        stages.put(stage, ties);
    }

    private static String getTeamName(String t) {
        return Sim.getClub(t).getName() + "(" + Sim.getNation(team_nations.get(t)).getCode() + ")";
    }

    private static Map<String, Double> club_points = new HashMap<>();
    private static Map<Integer, Double> nation_points = new HashMap<>();

    private static final double WIN = 2.0;
    private static final double DRAW = 1.0;

    private static void initCoefficients() {
        club_points = new HashMap<>();
        nation_points = new HashMap<>();
        List<Nation> nations = Sim.getNationFromContinent("Europe");
        for (Nation nation : nations) {
            nation_points.put(nation.getId(), 0.0);
        }
        Set<String> teams = seedings.keySet();
        for (String team : teams)
            club_points.put(team, 0.0);
    }

    private static void addCoefficientPointsToClub(String team, double points) {
        double club_point = club_points.get(team);
        club_point += points;
        club_points.put(team, club_point);
    }

    private static void addCoefficientPoints(String team, double points, boolean qualifying) {
        if (qualifying)
            points = points / 2;
        int nation_id = team_nations.get(team);
        double nat_points = nation_points.get(nation_id);
        nat_points += points;
        nation_points.put(nation_id, nat_points);
        if (!qualifying) {
            addCoefficientPointsToClub(team, points);
        }
    }

    private static void updateCoefficientsForMatch(Match match, boolean qualifying) {
        if (match.home_team_goals > match.away_team_goals) {
            addCoefficientPoints(match.home_id, WIN, qualifying);
        } else if (match.home_team_goals == match.away_team_goals) {
            addCoefficientPoints(match.away_id, WIN, qualifying);
        } else {
            addCoefficientPoints(match.home_id, DRAW, qualifying);
            addCoefficientPoints(match.away_id, DRAW, qualifying);
        }
    }

    private static void setStageBonusPoints(Stage stage) {
        List<KnockoutTie> ties = stages.get(stage);
        for (KnockoutTie tie : ties) {
            addCoefficientPoints(tie.getTeam1(), 1.0, false);
            addCoefficientPoints(tie.getTeam2(), 1.0, false);
        }
    }

    private static void setEliminationPoints(Stage stage, double points) {
        List<KnockoutTie> ties = stages.get(stage);
        for (KnockoutTie tie : ties) {
            addCoefficientPointsToClub(tie.getLoser(), points);
        }
    }

    private static void updateCoefficients() {
        // apply match points
        for (Map.Entry<Stage, List<KnockoutTie>> entry : stages.entrySet()) {
            List<KnockoutTie> ties = entry.getValue();
            Stage stage = entry.getKey();
            for (KnockoutTie tie : ties) {
                updateCoefficientsForMatch(tie.getFirstLeg(), stage.qualifying());
                updateCoefficientsForMatch(tie.getSecondLeg(), stage.qualifying());
            }
        }
        for (Map.Entry<Stage, League[]> entry : groups.entrySet()) {
            League[] leagues = entry.getValue();
            Stage stage = entry.getKey();
            for (League league : leagues) {
                List<Match> matches = league.getFixtures();
                for (Match match : matches) {
                    updateCoefficientsForMatch(match, stage.qualifying());
                }
            }
        }
        // apply bonus points
        // Champions League
        League[] cl_gs = groups.get(Stage.CL_GROUP);
        for (League league : cl_gs) {
            List<String> teams = league.getTeams();
            for (String team : teams) {
                addCoefficientPoints(team, 4.0, false);
            }
            addCoefficientPoints(league.getTeamAtRank(1), 5.0, false);
            addCoefficientPoints(league.getTeamAtRank(2), 5.0, false);
        }
        // Europa League
        League[] el_gs = groups.get(Stage.EL_GROUP);
        for (League league : el_gs) {
            addCoefficientPoints(league.getTeamAtRank(1), 4.0, false);
            addCoefficientPoints(league.getTeamAtRank(2), 2.0, false);
        }
        // Europa Conference League
        League[] ecl_gs = groups.get(Stage.ECL_GROUP);
        for (League league : ecl_gs) {
            addCoefficientPoints(league.getTeamAtRank(1), 2.0, false);
            addCoefficientPoints(league.getTeamAtRank(2), 1.0, false);
        }
        // Stage Bonus Points
        setStageBonusPoints(Stage.EL_R16);
        setStageBonusPoints(Stage.CL_QUARTER_FINAL);
        setStageBonusPoints(Stage.EL_QUARTER_FINAL);
        setStageBonusPoints(Stage.CL_SEMI_FINAL);
        setStageBonusPoints(Stage.EL_SEMI_FINAL);
        setStageBonusPoints(Stage.ECL_SEMI_FINAL);
        setStageBonusPoints(Stage.CL_FINAL);
        setStageBonusPoints(Stage.EL_FINAL);
        setStageBonusPoints(Stage.ECL_FINAL);

        // Set Participation Points
        setEliminationPoints(Stage.ECL_Q1_MP, 1.0);
        setEliminationPoints(Stage.ECL_Q2_CP, 1.5);
        setEliminationPoints(Stage.ECL_Q2_MP, 1.5);
        setEliminationPoints(Stage.ECL_Q3_MP, 2.0);
        setEliminationPoints(Stage.ECL_Q3_CP, 2.0);
        setEliminationPoints(Stage.ECL_Q4_MP, 2.5);
        setEliminationPoints(Stage.ECL_Q4_CP, 2.5);

        // Set Compensation Points
        for (League league : el_gs) {
            double MIN_POINTS = 3.0;
            List<String> teams = league.getTeams();
            for (String team : teams) {
                double cp = club_points.get(team);
                if (cp < MIN_POINTS)
                    club_points.put(team, MIN_POINTS);
            }
        }

        for (League league : ecl_gs) {
            double MIN_POINTS = 2.5;
            List<String> teams = league.getTeams();
            for (String team : teams) {
                double cp = club_points.get(team);
                if (cp < MIN_POINTS)
                    club_points.put(team, MIN_POINTS);
            }
        }
        club_coefficients.put(Sim.getSeasonNumber(), club_points);
    }

    private static void normalizeNationCoefficients() {
        Collection<Integer> nations_coll = team_nations.values();
        ArrayList<Integer> nations = new ArrayList<>(nations_coll);
        Map<Integer, Integer> nations_map = new HashMap<>();
        for (int i : nations) {
            int val = 0;
            if (nations_map.containsKey(i)) {
                val = nations_map.get(i);
            }
            val++;
            nations_map.put(i, val);
        }
        for (Map.Entry<Integer, Integer> entry : nations_map.entrySet()) {
            int key = entry.getKey(), val = entry.getValue();
            double points = nation_points.get(key);
            points = points / val;
            nation_points.put(key, points);
        }
        nation_coefficients.put(Sim.getSeasonNumber(), nation_points);
    }

    private static void showNationsCoefficients() {
        int count = 1;
        System.out.println("League Rankings");
        List<Map.Entry<Integer, Double>> list = new ArrayList<>(nation_points.entrySet());
        Collections.sort(list, (Map.Entry<Integer, Double> o1,
                Map.Entry<Integer, Double> o2) -> (int) (o2.getValue() - o1.getValue()));
        for (Map.Entry<Integer, Double> entry : list) {
            int nation_id = entry.getKey();
            String name = Sim.getNation(nation_id).getName();
            System.out.println(count + "\t" + name + "\t\t" + entry.getValue());
            count++;
        }
    }

    private static void showClubCoefficients() {
        int count = 1;
        System.out.println("\nClub Rankings");
        List<Map.Entry<String, Double>> list = new ArrayList<>(club_points.entrySet());
        Collections.sort(list, (Map.Entry<String, Double> o1,
                Map.Entry<String, Double> o2) -> (int) (o2.getValue() - o1.getValue()));
        for (Map.Entry<String, Double> entry : list) {
            String name = getTeamName(entry.getKey());
            System.out.println(count + "\t" + name + "\t\t" + entry.getValue());
            count++;
            if (count > 50)
                break;
        }
    }

    private static void postSeasonChanges() {
        initCoefficients();
        updateCoefficients();
        normalizeNationCoefficients();
        //showNationsCoefficients();
        //showClubCoefficients();
    }

    private static Map<Integer, Map<Integer, Double>> nation_coefficients = new HashMap<>();
    private static Map<Integer, Map<String, Double>> club_coefficients = new HashMap<>();

    public static void initCoefficientsCalculations() {
        for (int i = 2015; i <= 2019; i++) {
            Map<Integer, Double> p_map = new HashMap<>();
            nation_coefficients.put(Integer.valueOf(i), p_map);
        }

        Map<String, Double[]> initial_points = Setups.getCoefficientsSetup();
        List<Nation> nations = Sim.getNationFromContinent("Europe");
        for (Nation nation : nations) {
            String code = nation.getCode();
            Double[] points = initial_points.get(code);
            for (int i = 0; i < points.length; i++) {
                int year = i + 2015;
                Map<Integer, Double> year_map = nation_coefficients.get(year);
                year_map.put(nation.getId(), points[i]);
            }
        }
    }

    public static Map<Integer, Integer> getLeagueRankingsForSeason(int season) {
        int start = season - 5, end = season - 1;
        Map<Nation, Double> points = new HashMap<>();
        List<Nation> nations = Sim.getNationFromContinent("Europe");
        for (Nation nation : nations) {
            double point = 0.0;
            for (int year = start; year <= end; year++) {
                Map<Integer, Double> year_map = nation_coefficients.get(year);
                point += year_map.get(nation.getId());
            }
            points.put(nation, point);
        }
        List<Map.Entry<Nation, Double>> list = new ArrayList<>(points.entrySet());
        Collections.sort(list, (Map.Entry<Nation, Double> o1, Map.Entry<Nation, Double> o2) -> (int) (o2.getValue() - o1.getValue()));
        int count = 1;
        HashMap<Integer, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<Nation, Double> aa : list) {
            temp.put(aa.getKey().getId(), count);
            System.out.println("Rank: " + count + " " + aa.getKey().getName() + " " + aa.getValue());
            count++;
        }
        return temp;
    }

    private static double getCoefficientForTeam(String team, int season) {
        int start = season - 5, end = season - 1;
        double points = 0;
        for(int year = start; year <= end; year++) {
            if (club_coefficients.containsKey(year)) {
                Map<String, Double> map = club_coefficients.get(year);
                if (map.containsKey(team)) {
                    points += map.get(team);
                }
            }
        }
        return points;
    }

    public static List<String> sortByCoefficients(List<String> teams_to_sort) {
        int season = Sim.getSeasonNumber();
        Collections.sort(teams_to_sort, (String a, String b) -> (int) (getCoefficientForTeam(b, season) -getCoefficientForTeam(b, season) ));
        return teams_to_sort;
    }
}
