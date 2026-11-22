import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerAsia {
    private enum Zone {
        EAST,
        WEST,
        DEFAULT
    }

    private enum SeedingType {
        ACLE_WEST_LEAGUE,
        ACLE_WEST_QUALIFIER,
        ACLE_EAST_LEAGUE,
        ACLE_EAST_QUALIFIER,
        ACL2_EAST_QUALIFIER,
        ACL2_WEST_QUALIFIER,
        ACL2_WEST_GROUP,
        ACL2_EAST_GROUP,
        ACGL_EAST_GROUP,
        ACGL_WEST_GROUP,
        ACGL_EAST_QUALIFIER,
        ACGL_WEST_QUALIFIER,
        ACLE_WEST_PREQUALIFIER,
        ACLE_EAST_PREQUALIFIER,
        ACL2_EAST_PREQUALIFIER,
        ACL2_WEST_PREQUALIFIER,
        ACGL_EAST_PREQUALIFIER,
        ACGL_WEST_PREQUALIFIER,
        DEFAULT
    }

    private enum Stage {
        ACLE_PQ_EAST("ACL East Zone PreQualifying Round", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.ACLE,
                true),
        ACLE_PQ_WEST("ACL West Zone PreQualifying Round", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.ACLE,
                true),
        ACLE_Q_EAST("ACL East Zone Qualifying Round", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.ACLE, true),
        ACLE_Q_WEST("ACL West Zone Qualifying Round", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.ACLE, true),
        ACLE_LEAGUE_EAST("ACL East Zone League Phase", CCType.StageType.SWISS_LEAGUE, CCType.CompType.ACLE, false),
        ACLE_LEAGUE_WEST("ACL West Zone League Phase", CCType.StageType.SWISS_LEAGUE, CCType.CompType.ACLE, false),
        ACLE_R16_EAST("ACL East Zone Round of 16", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.ACLE, false),
        ACLE_R16_WEST("ACL West Zone Round of 16", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.ACLE, false),
        ACLE_QUARTER_FINAL("ACL Quarter Final", CCType.StageType.KNOCKOUT, CCType.CompType.ACLE, false),
        ACLE_SEMI_FINAL("ACL Semi Final", CCType.StageType.KNOCKOUT, CCType.CompType.ACLE, false),
        ACLE_FINAL("Asian Champions League Elite Final", CCType.StageType.KNOCKOUT, CCType.CompType.ACLE, false),

        ACL2_PQ_EAST("ACL2 East Zone PreQualifying Round", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.ACL2,
                true),
        ACL2_PQ_WEST("ACL2 West Zone PreQualifying Round", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.ACL2,
                true),
        ACL2_Q_EAST("ACL2 East Zone Qualifying Round", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.ACL2,
                true),
        ACL2_Q_WEST("ACL2 West Zone Qualifying Round", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.ACL2,
                true),
        ACL2_GROUP_EAST("ACL2 East Zone Group Stage", CCType.StageType.GROUPS, CCType.CompType.ACL2, false),
        ACL2_GROUP_WEST("ACL2 West Zone Group Stage", CCType.StageType.GROUPS, CCType.CompType.ACL2, false),
        ACL2_R16_EAST("ACL2 East Zone Round of 16", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.ACL2, false),
        ACL2_R16_WEST("ACL2 West Zone Round of 16", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.ACL2, false),
        ACL2_QF_EAST("ACL2 Quarter Final East", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.ACL2, false),
        ACL2_QF_WEST("ACL2 Quarter Final West", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.ACL2, false),
        ACL2_SF_EAST("ACL2 Semi Final East", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.ACL2, false),
        ACL2_SF_WEST("ACL2 Semi Final West", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.ACL2, false),
        ACL2_FINAL("Asian Champions League Two Final", CCType.StageType.KNOCKOUT, CCType.CompType.ACL2, false),

        ACGL_PQ_EAST("ACGL East Zone PreQualifying Round", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.ACGL,
                true),
        ACGL_PQ_WEST("ACGL West Zone PreQualifying Round", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.ACGL,
                true),
        ACGL_Q_EAST("ACGL East Zone Qualifying Round", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.ACGL,
                true),
        ACGL_Q_WEST("ACGL West Zone Qualifying Round", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.ACGL,
                true),
        ACGL_GROUP_EAST("ACGL East Zone Group Stage", CCType.StageType.GROUPS, CCType.CompType.ACGL, false),
        ACGL_GROUP_WEST("ACGL West Zone Group Stage", CCType.StageType.GROUPS, CCType.CompType.ACGL, false),
        ACGL_QF_EAST("ACGL Quarter Final East", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.ACGL, false),
        ACGL_QF_WEST("ACGL Quarter Final West", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.ACGL, false),
        ACGL_SF_EAST("ACGL Semi Final East", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.ACGL, false),
        ACGL_SF_WEST("ACGL Semi Final West", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.ACGL, false),
        ACGL_FINAL("Asian Challenge League Final", CCType.StageType.KNOCKOUT, CCType.CompType.ACGL, false);

        private final String stage_name;
        private final CCType.StageType stage_type;

        Stage(String stage_name, CCType.StageType stage_type, CCType.CompType competition, boolean qualifying) {
            this.stage_name = stage_name;
            this.stage_type = stage_type;
        }

        public String stage_name() {
            return this.stage_name;
        }

        public CCType.StageType stage_type() {
            return this.stage_type;
        }
    }

    private static Map<Integer, Integer> zonal_rankings = new HashMap<>();
    private static Map<String, Integer> team_nations = new HashMap<>();

    private static Map<String, SeedingType> seedings = new HashMap<>();
    private static Map<String, Integer> seeding_scores = new HashMap<>();
    private static Map<Stage, List<KnockoutTie>> stages = new HashMap<>();
    private static Map<Stage, League[]> groups = new HashMap<>();
    private static Map<Stage, League> acl_leagues = new HashMap<>();
    private static Map<String, RankingInfo> ranking_info = new HashMap<>();

    private static String acle_champion = null;
    private static String acl2_champion = null;
    private static String acgl_champion = null;
    private static String acgl_runnerup = null;

    static class RankingInfo {
        int zone_rank;
        int league_rank;

        public RankingInfo(int zone, int league) {
            this.zone_rank = zone;
            this.league_rank = league;
        }
    }

    private static Zone getZone(Nation nation) {
        switch (nation.getName()) {
            case "Saudi Arabia":
            case "Iran":
            case "Iraq":
            case "Syria":
            case "Uzbekistan":
            case "Emirates":
            case "Qatar":
            case "India":
            case "Jordan":
            case "Kuwait":
            case "Bahrain":
            case "Oman":
            case "Turkmenistan":
            case "Tajikistan":
            case "Kyrgyzstan":
            case "Afghanistan":
            case "Bangladesh":
            case "Lebanon":
            case "Yemen":
            case "Palestine":
                return Zone.WEST;
            case "South Korea":
            case "Japan":
            case "China":
            case "Thailand":
            case "Australia":
            case "New Zealand":
            case "Malaysia":
            case "Vietnam":
            case "Indonesia":
            case "Singapore":
            case "Philippines":
            case "Cambodia":
            case "Laos":
            case "Brunei":
            case "Taiwan":
            case "Hong Kong":
            case "North Korea":
            case "Timor Leste":
            case "Myanmar":
            case "Mongolia":
                return Zone.EAST;
        }
        return Zone.DEFAULT;
    }

    private static void setZonalRankings() {
        List<Nation> nations = Sim.getNationFromContinent("Asia");
        Collections.sort(nations, Comparator.comparing(Nation::getLeagueRanking));
        int east_rank = 1, west_rank = 1;
        for (Nation nation : nations) {
            if (getZone(nation) == Zone.EAST) {
                zonal_rankings.put(nation.getId(), east_rank);
                east_rank++;
            } else if (getZone(nation) == Zone.WEST) {
                zonal_rankings.put(nation.getId(), west_rank);
                west_rank++;
            }
        }
    }

    private static int getZonalRanking(Nation nation) {
        return zonal_rankings.get(nation.getId());
    }

    private static int getTotalPlaces(Nation nation) {
        return getPlaces(nation, CCType.CompType.ACLE) + getPlaces(nation, CCType.CompType.ACL2)
                + getPlaces(nation, CCType.CompType.ACGL);
    }

    private static int getPlaces(Nation nation, CCType.CompType comp) {
        int ranking = getZonalRanking(nation);
        switch (comp) {
            case ACLE:
                if (ranking == 1)
                    return 5;
                else if (ranking == 2)
                    return 4;
                else if (ranking == 3)
                    return 3;
                else if (ranking <= 5)
                    return 2;
                else if (ranking <= 7)
                    return 1;
                else
                    return 0;
            case ACL2:
                if (ranking <= 7)
                    return 1;
                else if (ranking <= 10)
                    return 2;
                else if (ranking <= 12)
                    return 1;
                else
                    return 0;
            case ACGL:
                if (ranking <= 10)
                    return 0;
                else if (ranking <= 12)
                    return 1;
                else if (ranking <= 17)
                    return 2;
                else if (ranking <= 20)
                    return 1;
                else
                    return 0;
            default:
                return 0;
        }
    }

    private static int getBaseSeedingScore(SeedingType seeding) {
        switch (seeding) {
            case ACLE_WEST_LEAGUE:
            case ACLE_EAST_LEAGUE:
                return 0;
            case ACLE_WEST_QUALIFIER:
            case ACLE_EAST_QUALIFIER:
                return 100;
            case ACL2_WEST_GROUP:
            case ACL2_EAST_GROUP:
                return 1000;
            case ACL2_EAST_QUALIFIER:
            case ACL2_WEST_QUALIFIER:
                return 1100;
            case ACGL_EAST_GROUP:
            case ACGL_WEST_GROUP:
                return 2000;
            case ACGL_EAST_QUALIFIER:
            case ACGL_WEST_QUALIFIER:
                return 2100;
            case DEFAULT:
                return 10000;
            default:
                return 10000;
        }
    }

    private static int getSeedingScore(Nation nation, int rank) {
        SeedingType base_seeding_type = getSeeding(nation, rank);
        int base_seeding_score = getBaseSeedingScore(base_seeding_type);
        if (base_seeding_score == 0 || base_seeding_score == 1000 || base_seeding_score == 2000)
            return base_seeding_score;
        else
            return base_seeding_score + getZonalRanking(nation) + rank;
    }

    private static int getSeeding(int zone_rank, int rank) {
        if (rank == 1) {
            if (zone_rank <= 6)
                return 1;
            else if (zone_rank == 7)
                return 2;
            else if (zone_rank <= 10)
                return 3;
            else if (zone_rank <= 12)
                return 4;
            else if (zone_rank <= 17)
                return 5;
            else if (zone_rank <= 20)
                return 6;
        } else if (rank == 2) {
            if (zone_rank <= 4)
                return 1;
            else if (zone_rank == 5)
                return 2;
            else if (zone_rank <= 7)
                return 3;
            else if (zone_rank <= 10)
                return 4;
            else if (zone_rank <= 12)
                return 5;
            else if (zone_rank <= 17)
                return 6;
        } else if (rank == 3) {
            if (zone_rank <= 3)
                return 1;
            else if (zone_rank <= 5)
                return 3;
        } else if (rank == 4) {
            if (zone_rank <= 2)
                return 2;
            else if (zone_rank == 3)
                return 3;
        } else if (rank == 5) {
            if (zone_rank == 1)
                return 2;
            else if (zone_rank == 2)
                return 3;
        } else if (rank == 6) {
            if (zone_rank == 1)
                return 3;
        }
        return 100;
    }

    private static SeedingType getSeedingTypeForTierAndZone(int tier, int level, Zone zone) {
        if (zone == Zone.EAST) {
            if (tier == 1) {
                return level == 1 ? SeedingType.ACLE_EAST_LEAGUE
                        : level == 2 ? SeedingType.ACLE_EAST_QUALIFIER
                                : level == 3 ? SeedingType.ACLE_EAST_PREQUALIFIER : SeedingType.DEFAULT;
            } else if (tier == 2) {
                return level == 1 ? SeedingType.ACL2_EAST_GROUP
                        : level == 2 ? SeedingType.ACL2_EAST_QUALIFIER
                                : level == 3 ? SeedingType.ACL2_EAST_PREQUALIFIER : SeedingType.DEFAULT;
            } else if (tier == 3) {
                return level == 1 ? SeedingType.ACGL_EAST_GROUP
                        : level == 2 ? SeedingType.ACGL_EAST_QUALIFIER
                                : level == 3 ? SeedingType.ACGL_EAST_PREQUALIFIER : SeedingType.DEFAULT;
            }
        } else if (zone == Zone.WEST) {
            if (tier == 1) {
                return level == 1 ? SeedingType.ACLE_WEST_LEAGUE
                        : level == 2 ? SeedingType.ACLE_WEST_QUALIFIER
                                : level == 3 ? SeedingType.ACLE_WEST_PREQUALIFIER : SeedingType.DEFAULT;
            } else if (tier == 2) {
                return level == 1 ? SeedingType.ACL2_WEST_GROUP
                        : level == 2 ? SeedingType.ACL2_WEST_QUALIFIER
                                : level == 3 ? SeedingType.ACL2_WEST_PREQUALIFIER : SeedingType.DEFAULT;
            } else if (tier == 3) {
                return level == 1 ? SeedingType.ACGL_WEST_GROUP
                        : level == 2 ? SeedingType.ACGL_WEST_QUALIFIER
                                : level == 3 ? SeedingType.ACGL_WEST_PREQUALIFIER : SeedingType.DEFAULT;
            }
        }
        return SeedingType.DEFAULT;
    }

    private static SeedingType getSeeding(Nation nation, int rank) {
        Zone zone = getZone(nation);
        int zone_rank = getZonalRanking(nation);
        int seed = getSeeding(zone_rank, rank);

        int level = -1, tier = -1;
        if (seed == 1) {
            level = 1;
            tier = 1;
        } else if (seed == 2) {
            tier = 1;
            level = 2;
        } else if (seed == 3) {
            tier = 2;
            level = 1;
        } else if (seed == 4) {
            tier = 2;
            level = 2;
        } else if (seed == 5) {
            tier = 3;
            level = 1;
        } else if (seed == 6) {
            tier = 3;
            level = 2;
        }

        return getSeedingTypeForTierAndZone(tier, level, zone);
    }

    private static void setSeedingScores() {
        List<Nation> nations = Sim.getNationFromContinent("Asia");
        int acle_champion_nation = acle_champion != null ? Sim.getClub(acle_champion).getNationId() : 0;
        int acl2_champion_nation = acl2_champion != null ? Sim.getClub(acl2_champion).getNationId() : 0;
        for (Nation nation : nations) {
            int zone_rank = getZonalRanking(nation);
            List<String> teams = getTeams(nation);

            if (acle_champion != null && acl2_champion != null) {
                int acle_places = getPlaces(nation, CCType.CompType.ACLE);
                if (acle_champion_nation == nation.getId()) {
                    if (teams.contains(acle_champion)) {
                        if (!teams.get(0).equals(acle_champion)) {
                            teams.remove(acle_champion);
                            teams.add(0, acle_champion);
                        }
                    } else if (zone_rank <= 4) {
                        teams.remove(teams.size() - 1);
                        teams.add(0, acle_champion);
                    }
                }

                if (acl2_champion_nation == nation.getId()) {
                    if (teams.contains(acl2_champion)) {
                        if (!teams.subList(0, acle_places).contains(acl2_champion) && acle_places > 0) {
                            teams.remove(acl2_champion);
                            teams.add(acle_places - 1, acl2_champion);
                        }
                    } else if (acle_places > 1) {
                        teams.remove(teams.size() - 1);
                        teams.add(acle_places - 1, acl2_champion);
                    }
                }
            }

            for (int i = 0; i < teams.size(); i++) {
                int rank = i + 1;
                SeedingType seedingType = getSeeding(nation, rank);
                seedings.put(teams.get(i), seedingType);
                seeding_scores.put(teams.get(i), getSeedingScore(nation, rank));
                team_nations.put(teams.get(i), nation.getId());
                RankingInfo ri = new ControllerAsia.RankingInfo(zone_rank, rank);
                ranking_info.put(teams.get(i), ri);
                if (seedingType == SeedingType.ACGL_EAST_GROUP || seedingType == SeedingType.ACGL_EAST_QUALIFIER)
                    System.out
                            .println(Sim.getClubContinentalName(teams.get(i)) + " " + seedingType.toString() + " zone: "
                                    + ri.zone_rank + " league: " + rank + " score: " + getSeedingScore(nation, rank));
            }
        }
    }

    private static List<String> getTeams(Nation nation) {
        Division league = nation.getDivision(1);
        int places = getTotalPlaces(nation);
        List<String> teams_list = new ArrayList<>();
        for (int i = 1; i <= places; i++) {
            teams_list.add(league.getTeamAtRank(i));
        }
        return teams_list;
    }

    private static SeedingType getSeedingTypeForStage(Stage stage) {
        switch (stage) {
            case ACLE_LEAGUE_EAST:
                return SeedingType.ACLE_EAST_LEAGUE;
            case ACLE_Q_EAST:
                return SeedingType.ACLE_EAST_QUALIFIER;
            case ACLE_LEAGUE_WEST:
                return SeedingType.ACLE_WEST_LEAGUE;
            case ACLE_Q_WEST:
                return SeedingType.ACLE_WEST_QUALIFIER;
            case ACL2_GROUP_EAST:
                return SeedingType.ACL2_EAST_GROUP;
            case ACL2_GROUP_WEST:
                return SeedingType.ACL2_WEST_GROUP;
            case ACL2_Q_EAST:
                return SeedingType.ACL2_EAST_QUALIFIER;
            case ACL2_Q_WEST:
                return SeedingType.ACL2_WEST_QUALIFIER;
            case ACGL_GROUP_EAST:
                return SeedingType.ACGL_EAST_GROUP;
            case ACGL_GROUP_WEST:
                return SeedingType.ACGL_WEST_GROUP;
            case ACGL_Q_EAST:
                return SeedingType.ACGL_EAST_QUALIFIER;
            case ACGL_Q_WEST:
                return SeedingType.ACGL_WEST_QUALIFIER;
            case ACLE_PQ_EAST:
                return SeedingType.ACLE_EAST_PREQUALIFIER;
            case ACLE_PQ_WEST:
                return SeedingType.ACLE_WEST_PREQUALIFIER;
            case ACL2_PQ_EAST:
                return SeedingType.ACL2_EAST_PREQUALIFIER;
            case ACL2_PQ_WEST:
                return SeedingType.ACL2_WEST_PREQUALIFIER;
            case ACGL_PQ_EAST:
                return SeedingType.ACGL_EAST_PREQUALIFIER;
            case ACGL_PQ_WEST:
                return SeedingType.ACGL_WEST_PREQUALIFIER;
            default:
                break;
        }
        return SeedingType.DEFAULT;
    }

    private static List<String> getTeamsWithSeeding(SeedingType seedingType) {
        List<String> teams_list = new ArrayList<>();
        for (Map.Entry<String, SeedingType> entry : seedings.entrySet()) {
            if (entry.getValue() == seedingType)
                teams_list.add(entry.getKey());
        }
        return teams_list;
    }

    private static List<Stage> getPreviousWinnerStages(Stage stage) {
        List<Stage> stages = new ArrayList<>();
        switch (stage) {
            case ACLE_FINAL:
                stages.add(Stage.ACLE_SEMI_FINAL);
                break;
            case ACLE_SEMI_FINAL:
                stages.add(Stage.ACLE_QUARTER_FINAL);
                break;
            case ACLE_QUARTER_FINAL:
                stages.add(Stage.ACLE_R16_EAST);
                stages.add(Stage.ACLE_R16_WEST);
                break;
            case ACLE_R16_EAST:
                stages.add(Stage.ACLE_LEAGUE_EAST);
                break;
            case ACLE_R16_WEST:
                stages.add(Stage.ACLE_LEAGUE_WEST);
                break;
            case ACLE_LEAGUE_EAST:
                stages.add(Stage.ACLE_Q_EAST);
                break;
            case ACLE_LEAGUE_WEST:
                stages.add(Stage.ACLE_Q_WEST);
                break;
            case ACLE_Q_EAST:
                stages.add(Stage.ACLE_PQ_EAST);
                break;
            case ACLE_Q_WEST:
                stages.add(Stage.ACLE_PQ_WEST);
                break;

            case ACL2_FINAL:
                stages.add(Stage.ACL2_SF_EAST);
                stages.add(Stage.ACL2_SF_WEST);
                break;
            case ACL2_SF_EAST:
                stages.add(Stage.ACL2_QF_EAST);
                break;
            case ACL2_SF_WEST:
                stages.add(Stage.ACL2_QF_WEST);
                break;
            case ACL2_QF_EAST:
                stages.add(Stage.ACL2_R16_EAST);
                break;
            case ACL2_QF_WEST:
                stages.add(Stage.ACL2_R16_WEST);
                break;
            case ACL2_R16_EAST:
                stages.add(Stage.ACL2_GROUP_EAST);
                break;
            case ACL2_R16_WEST:
                stages.add(Stage.ACL2_GROUP_WEST);
                break;
            case ACL2_GROUP_EAST:
                stages.add(Stage.ACL2_Q_EAST);
                break;
            case ACL2_GROUP_WEST:
                stages.add(Stage.ACL2_Q_WEST);
                break;
            case ACL2_Q_EAST:
                stages.add(Stage.ACL2_PQ_EAST);
                break;
            case ACL2_Q_WEST:
                stages.add(Stage.ACL2_PQ_WEST);
                break;

            case ACGL_FINAL:
                stages.add(Stage.ACGL_SF_EAST);
                stages.add(Stage.ACGL_SF_WEST);
                break;
            case ACGL_SF_EAST:
                stages.add(Stage.ACGL_QF_EAST);
                break;
            case ACGL_SF_WEST:
                stages.add(Stage.ACGL_QF_WEST);
                break;
            case ACGL_QF_EAST:
                stages.add(Stage.ACGL_GROUP_EAST);
                break;
            case ACGL_QF_WEST:
                stages.add(Stage.ACGL_GROUP_WEST);
                break;
            case ACGL_GROUP_EAST:
                stages.add(Stage.ACGL_Q_EAST);
                break;
            case ACGL_GROUP_WEST:
                stages.add(Stage.ACGL_Q_WEST);
                break;
            case ACGL_Q_EAST:
                stages.add(Stage.ACGL_PQ_EAST);
                break;
            case ACGL_Q_WEST:
                stages.add(Stage.ACGL_PQ_WEST);
                break;

            default:
                break;
        }
        return stages;
    }

    private static List<Stage> getPreviousLoserStages(Stage stage) {
        List<Stage> stages = new ArrayList<>();
        switch (stage) {
            case ACL2_GROUP_EAST:
                stages.add(Stage.ACLE_Q_EAST);
                stages.add(Stage.ACLE_PQ_EAST);
                break;
            case ACL2_GROUP_WEST:
                stages.add(Stage.ACLE_Q_WEST);
                stages.add(Stage.ACLE_PQ_WEST);
                break;
            case ACGL_GROUP_EAST:
                stages.add(Stage.ACL2_Q_EAST);
                stages.add(Stage.ACL2_PQ_EAST);
                break;
            case ACGL_GROUP_WEST:
                stages.add(Stage.ACL2_Q_WEST);
                stages.add(Stage.ACL2_PQ_WEST);
                break;
            default:
                break;
        }
        return stages;
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

    private static boolean isStageGroup(Stage stage) {
        return stage.stage_type() == CCType.StageType.GROUPS;
    }

    private static boolean isStageSwissLeague(Stage stage) {
        return stage.stage_type() == CCType.StageType.SWISS_LEAGUE;
    }

    private static void setDefaultChampions() {
        List<String> arr = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : seeding_scores.entrySet()) {
            if (entry.getValue() < 100) {
                arr.add(entry.getKey());
            }
        }
        acle_champion = arr.get(0);
        acl2_champion = arr.get(1);
        acgl_champion = arr.get(2);
        acgl_runnerup = arr.get(3);
    }

    private static void setPreviousChampions() {
        if (Sim.isFirstSeason()) {
            setDefaultChampions();
        } else {
            int last_season = Sim.getSeasonNumber() - 1;
            acle_champion = getCompetitionHistory(CCType.CompType.ACLE, last_season).getWinner();
            acl2_champion = getCompetitionHistory(CCType.CompType.ACL2, last_season).getWinner();
            acgl_champion = getCompetitionHistory(CCType.CompType.ACGL, last_season).getWinner();
            acgl_runnerup = getCompetitionHistory(CCType.CompType.ACGL, last_season).getRunnerUp();
        }

        System.out.println("ACLE CHAMPION: " + Sim.getClubContinentalName(acle_champion));
        System.out.println("ACL2 CHAMPION: " + Sim.getClubContinentalName(acl2_champion));
        System.out.println("ACGL CHAMPION: " + Sim.getClubContinentalName(acgl_champion));
        System.out.println("ACGL RUNNER UP: " + Sim.getClubContinentalName(acgl_runnerup));
    }

    private static void addPreviousChampions() {
        if (seeding_scores.containsKey(acle_champion)) {
            if (seeding_scores.get(acle_champion) == getBaseSeedingScore(SeedingType.ACLE_EAST_QUALIFIER)) {
                seeding_scores.put(acle_champion, getBaseSeedingScore(SeedingType.ACLE_EAST_LEAGUE));
            }
        }

        int base_seeding_acl2_ch = getBaseSeedingScore(SeedingType.ACLE_EAST_QUALIFIER);
        if (seeding_scores.containsKey(acl2_champion)) {
            if (seeding_scores.get(acl2_champion) > base_seeding_acl2_ch)
                seeding_scores.put(acl2_champion, base_seeding_acl2_ch);
        } else {
            seeding_scores.put(acl2_champion, base_seeding_acl2_ch);
            team_nations.put(acl2_champion, Sim.getClub(acl2_champion).getNationId());
        }

        int base_seeding_acgl_ch = getBaseSeedingScore(SeedingType.ACL2_EAST_GROUP);
        if (seeding_scores.containsKey(acgl_champion)) {
            if (seeding_scores.get(acgl_champion) > base_seeding_acgl_ch)
                seeding_scores.put(acgl_champion, base_seeding_acgl_ch);
        } else {
            seeding_scores.put(acgl_champion, base_seeding_acgl_ch);
            team_nations.put(acgl_champion, Sim.getClub(acgl_champion).getNationId());
        }

        int base_seeding_acgl_ru = getBaseSeedingScore(SeedingType.ACL2_EAST_QUALIFIER);
        if (seeding_scores.containsKey(acgl_runnerup)) {
            if (seeding_scores.get(acgl_runnerup) > base_seeding_acgl_ru)
                seeding_scores.put(acgl_runnerup, base_seeding_acgl_ru);
        } else {
            seeding_scores.put(acgl_runnerup, base_seeding_acgl_ru);
            team_nations.put(acgl_runnerup, Sim.getClub(acgl_runnerup).getNationId());
        }
    }

    private static Zone getZone(String team) {
        int nation = team_nations.getOrDefault(team, -1);
        if (nation == -1) {
            System.out.println("Failed nation parse: " + Sim.getClubContinentalName(team));
            team_nations.put(team, Sim.getClub(team).getNationId());
            nation = team_nations.get(team);
        }
        Nation n = Sim.getNation(nation);
        return getZone(n);
    }

    private static List<String> getSortedSeedings(boolean ascending, int limit, int min, int max) {
        Map<String, Integer> map = CommonFunctions.sortMap(seeding_scores, ascending, limit, min, max);
        List<String> list = new ArrayList<>(map.keySet());
        return list;
    }

    private static int getMaxSeedingScore(SeedingType type) {
        return getBaseSeedingScore(type) + 99;
    }

    private static List<String> getSortedSeedingsList(boolean ascending, int limit, SeedingType base, SeedingType max) {
        return getSortedSeedings(ascending, limit, getBaseSeedingScore(base), getMaxSeedingScore(max));
    }

    private static List<String> filterbyZone(List<String> list, Zone zone, int limit) {
        int count = 0;
        List<String> s = new ArrayList<>();
        if (limit == 0)
            return s;
        for (String x : list) {
            if (zone == getZone(x)) {
                s.add(x);
                count++;
                if (count == limit)
                    break;
            }
        }
        return s;
    }

    private static void applySeedings(List<String> list, SeedingType seeding) {
        for (String x : list) {
            seedings.put(x, seeding);
        }
    }

    private static void applySeedingsAccordingToFormat(List<String> l, int a, int b, int c, SeedingType sa,
            SeedingType sb, SeedingType sc) {
        applySeedings(l.subList(0, c), sc);
        applySeedings(l.subList(c, c + b), sb);
        applySeedings(l.subList(c + b, c + b + a), sa);
    }

    private static void applySeedingsForFormat(List<String> l, int a, int b, int c, int tier, Zone zone) {
        applySeedingsAccordingToFormat(l, a, b, c, getSeedingTypeForTierAndZone(tier, 3, zone),
                getSeedingTypeForTierAndZone(tier, 2, zone), getSeedingTypeForTierAndZone(tier, 1, zone));
    }

    private static void setQualifierFormat(List<Integer> sol, Zone zone) {
        int e1 = sol.get(0), e2 = sol.get(1), e3 = sol.get(2);
        int c1 = sol.get(3), c2 = sol.get(4), c3 = sol.get(5);
        int g1 = sol.get(6), g2 = sol.get(7), g3 = sol.get(8);

        SeedingType qseed1 = getSeedingTypeForTierAndZone(1, 2, zone);
        SeedingType qseed2 = getSeedingTypeForTierAndZone(2, 2, zone);
        SeedingType qseed3 = getSeedingTypeForTierAndZone(3, 2, zone);

        List<String> acle = filterbyZone(getSortedSeedingsList(true, 100, qseed1, qseed1), zone, e3 + e2 + e1);
        List<String> acl2 = filterbyZone(getSortedSeedingsList(true, 100, qseed2, qseed2), zone, c3 + c2 + c1);
        List<String> acgl = filterbyZone(getSortedSeedingsList(true, 100, qseed3, qseed3), zone, g3 + g2 + g1);

        applySeedingsForFormat(acle, e1, e2, e3, 1, zone);
        applySeedingsForFormat(acl2, c1, c2, c3, 2, zone);
        applySeedingsForFormat(acgl, g1, g2, g3, 3, zone);

        int e = e1 + e2 + e3, c = c1 + c2 + c3, g = g1 + g2 + g3;
        System.out.println("e1: " + e1 + " e2: " + e2 + " e3: " + e3 + " c1: " + c1 + " c2: " + c2 + " c3: " + c3
                + " g1: " + g1 + " g2: " + g2 + " g3: " + g3);
        System.out.println(zone + " acle: " + e + "/" + acle.size() +
                " acl2: " + c + "/" + acl2.size() + " acgl: " + g + "/" + acgl.size());
    }

    private static Map<String, List<Integer>> formats = new HashMap<>();

    private static String getFormatsKey(String acle, String tacle, String acl2, String tacl2, String acgl,
            String tacgl) {
        return acle + "::" + tacle + "::" + acl2 + "::" + tacl2 + "::" + acgl + "::" + tacgl;
    }

    private static String getFormatsKey(int a, int b, int c, int d, int e, int f) {
        return getFormatsKey(String.valueOf(a), String.valueOf(b), String.valueOf(c), String.valueOf(d),
                String.valueOf(e), String.valueOf(f));
    }

    private static void loadAsianFormats() {
        String file_name = "formats/asia.csv";
        List<String[]> data = FileOperations.readCSV(file_name);
        for (String[] x : data) {
            if (x.length != 15)
                continue;
            String key = getFormatsKey(x[0], x[1], x[2], x[3], x[4], x[5]);
            List<Integer> l = new ArrayList<>();
            for (int i = 6; i < 15; i++) {
                try {
                    l.add(Integer.parseInt(x[i]));
                } catch (Exception e) {
                    return;
                }
            }
            formats.put(key, l);
        }
    }

    private static void selectQualifierFormats() {
        int acle_east = 0, acle_west = 0, acl2_east = 0, acl2_west = 0, acgl_east = 0, acgl_west = 0;
        int acle_east_q = 0, acle_west_q = 0, acl2_east_q = 0, acl2_west_q = 0, acgl_east_q = 0, acgl_west_q = 0;

        int acle_seeding = getBaseSeedingScore(SeedingType.ACLE_EAST_LEAGUE);
        int acl2_seeding = getBaseSeedingScore(SeedingType.ACL2_EAST_GROUP);
        int acgl_seeding = getBaseSeedingScore(SeedingType.ACGL_EAST_GROUP);

        for (Map.Entry<String, Integer> entry : seeding_scores.entrySet()) {
            int val = entry.getValue();
            String k = entry.getKey();
            Zone zone = getZone(k);
            if (val == acle_seeding) {
                if (zone == Zone.EAST) {
                    acle_east++;
                    seedings.put(k, SeedingType.ACLE_EAST_LEAGUE);
                } else if (zone == Zone.WEST) {
                    acle_west++;
                    seedings.put(k, SeedingType.ACLE_WEST_LEAGUE);
                }
            } else if (val == acl2_seeding) {
                if (zone == Zone.EAST) {
                    acl2_east++;
                    seedings.put(k, SeedingType.ACL2_EAST_GROUP);
                } else if (zone == Zone.WEST) {
                    acl2_west++;
                    seedings.put(k, SeedingType.ACL2_WEST_GROUP);
                }
            } else if (val == acgl_seeding) {
                if (zone == Zone.EAST) {
                    acgl_east++;
                    seedings.put(k, SeedingType.ACGL_EAST_GROUP);
                } else if (zone == Zone.WEST) {
                    acgl_west++;
                    seedings.put(k, SeedingType.ACGL_WEST_GROUP);
                }
            } else if (val > acle_seeding && val < acl2_seeding) {
                if (zone == Zone.EAST)
                    acle_east_q++;
                else if (zone == Zone.WEST) {
                    acle_west_q++;
                }
            } else if (val > acl2_seeding && val < acgl_seeding) {
                if (zone == Zone.EAST)
                    acl2_east_q++;
                else if (zone == Zone.WEST) {
                    acl2_west_q++;
                }
            } else {
                if (zone == Zone.EAST)
                    acgl_east_q++;
                else if (zone == Zone.WEST) {
                    acgl_west_q++;
                }
            }
        }

        int ACLE_TEAMS = 16;
        int ACL2_TEAMS = 16;
        int ACGL_TEAMS = 12;

        System.out.println(" ACLE: E: " + acle_east + " W: " + acle_west + " ACL2: E: " + acl2_east + " W: " + acl2_west
                + " ACGL: E: " + acgl_east + " W: " + acgl_west);
        System.out.println(
                " ACLE: E: " + acle_east_q + " W: " + acle_west_q + " ACL2: E: " + acl2_east_q + " W: " + acl2_west_q
                        + " ACGL: E: " + acgl_east_q + " W: " + acgl_west_q);

        String key_east = getFormatsKey(acle_east_q, ACLE_TEAMS - acle_east, acl2_east_q,
                ACL2_TEAMS - acl2_east, acgl_east_q, ACGL_TEAMS - acgl_east);
        String key_west = getFormatsKey(acle_west_q, ACLE_TEAMS - acle_west, acl2_west_q,
                ACL2_TEAMS - acl2_west, acgl_west_q, ACGL_TEAMS - acgl_west);

        if (formats.size() == 0)
            loadAsianFormats();

        if (formats.containsKey(key_east)) {
            setQualifierFormat(formats.get(key_east), Zone.EAST);
        } else {
            List<Integer> soln_east = AsianFormatCalculator.getSolution(acle_east_q, ACLE_TEAMS - acle_east,
                    acl2_east_q,
                    ACL2_TEAMS - acl2_east, acgl_east_q, ACGL_TEAMS - acgl_east);
            if (soln_east != null && soln_east.size() > 0) {
                setQualifierFormat(soln_east, Zone.EAST);
                formats.put(key_east, soln_east);
            }
        }

        if (formats.containsKey(key_west)) {
            setQualifierFormat(formats.get(key_west), Zone.WEST);
        } else {
            List<Integer> soln_west = AsianFormatCalculator.getSolution(acle_west_q, ACLE_TEAMS - acle_west,
                    acl2_west_q,
                    ACL2_TEAMS - acl2_west, acgl_west_q, ACGL_TEAMS - acgl_west);
            if (soln_west != null && soln_west.size() > 0) {
                setQualifierFormat(soln_west, Zone.WEST);
                formats.put(key_west, soln_west);
            }
        }
    }

    private static void setSeedings() {
        System.out.println("Seedings setting: ");
        if (Sim.isFirstSeason()) {
            setSeedingScores();
            setPreviousChampions();
        } else {
            setPreviousChampions();
            setSeedingScores();
        }
        addPreviousChampions();
        selectQualifierFormats();
    }

    private static int NUM_POTS = 4;
    private static int NUM_GROUPS = 4;
    private static int NUM_TEAMS = 16;

    private static String[][] divideIntoPots(List<String> teams, int num_pots, int num_teams_per_pot) {
        int pot = 0;
        int pos = 0;
        List<String> teams_added = new ArrayList<>();
        String[][] pots = new String[num_pots][num_teams_per_pot];
        for (int lr = 1; lr <= 10; lr++) {
            for (int zr = 1; zr <= 20; zr++) {
                for (String x : ranking_info.keySet()) {
                    RankingInfo ri = ranking_info.get(x);
                    if (ri.zone_rank == zr && ri.league_rank == lr && teams.contains(x)) {
                        pots[pot][pos] = x;
                        teams_added.add(x);
                        pos++;
                        if (pos == num_teams_per_pot) {
                            pos = 0;
                            pot++;
                        }
                    }
                }
            }
        }
        for (String x : teams) {
            if (teams_added.contains(x))
                continue;
            pots[pot][pos] = x;
            pos++;
            if (pos == num_teams_per_pot) {
                pos = 0;
                pot++;
            }
        }

        return pots;
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

        List<Stage> l_stages = getPreviousLoserStages(type);
        for (Stage stage : l_stages) {
            teams.addAll(getLosersFromStage(stage));
        }

        if (isStageSwissLeague(type)) {
            teams = Sim.sortByReputation(teams);
            NUM_POTS = 4;
            NUM_TEAMS = 16;
            String[][] pots = divideIntoPots(teams, NUM_POTS, NUM_TEAMS / NUM_POTS);
            League league = new League();
            for (int i = 0; i < teams.size(); i++) {
                league.addTeam(teams.get(i));
            }
            List<Match> matches = new AsianFixtures(pots, team_nations).getFixtures();
            league.setFixtures(matches);
            league.setupTable();
            acl_leagues.put(type, league);
        } else if (isStageGroup(type)) {
            if (type == Stage.ACL2_GROUP_EAST || type == Stage.ACL2_GROUP_WEST) {
                NUM_GROUPS = 4;
                NUM_POTS = 4;
                NUM_TEAMS = 16;
            } else if (type == Stage.ACGL_GROUP_EAST || type == Stage.ACGL_GROUP_WEST) {
                NUM_GROUPS = 3;
                NUM_POTS = 4;
                NUM_TEAMS = 12;
            }
            teams = Sim.sortByReputation(teams);
            System.out.println("size: " + teams.size() + " " + type.stage_name);
            String[][] pots = divideIntoPots(teams, NUM_POTS, NUM_TEAMS / NUM_POTS);
            // CommonFunctions.display2DArrayTeams(pots, NUM_POTS, NUM_GROUPS);
            String[][] groups_matrix = CCDrawHelper.drawGroups(pots, NUM_GROUPS, NUM_POTS, team_nations);
            League[] stage_groups = new League[NUM_GROUPS];
            for (int i = 0; i < NUM_GROUPS; i++) {
                League league = new League();
                for (int j = 0; j < NUM_POTS; j++) {
                    league.addTeam(groups_matrix[i][j]);
                }
                league.setFixtures();
                league.setupTable();
                stage_groups[i] = league;
            }
            groups.put(type, stage_groups);
        } else {
            if (teams.size() % 2 != 0)
                System.out.println("Irregular no of teams found for stage: " + type);
            teams = Sim.sortByReputation(teams);
            int len = teams.size();
            List<String> seeded = new ArrayList<>();
            seeded.addAll(teams.subList(0, len / 2));
            List<String> unseeded = new ArrayList<>();
            unseeded.addAll(teams.subList(len / 2, len));
            List<KnockoutTie> ties = new ArrayList<>();
            int ties_size = teams.size() / 2;
            for (int i = 0; i < ties_size; i++) {
                String selected_team = CommonFunctions.getRandomElement(seeded);
                List<String> filtered = unseeded;
                if (filtered.size() == 0)
                    System.out.println("No team found in stage: " + type);
                String selected_team_2 = CommonFunctions.getRandomElement(filtered);
                if (type.stage_type() == CCType.StageType.TWO_LEGGED_KNOCKOUT) {
                    ties.add(new TwoLeggedKnockoutTie(selected_team, selected_team_2, 1));
                } else if (type.stage_type() == CCType.StageType.KNOCKOUT) {
                    ties.add(new SingleKnockoutTie(selected_team, selected_team_2, 1));
                }
                seeded.remove(selected_team);
                unseeded.remove(selected_team_2);
            }
            stages.put(type, ties);
        }

    }

    private static void simulateStage(Stage stage) {
        System.out.println("\n" + stage.stage_name);
        List<KnockoutTie> ties = stages.get(stage);
        for (int i = 0; i < ties.size(); i++) {
            ties.get(i).simulate();
            System.out.print("Match " + (i + 1) + ": ");
            ties.get(i).showContinental();
        }
        stages.put(stage, ties);
    }

    private static void simulateLeaguePhaseMatchDay(Stage stage, int matchday) {
        System.out.println("\n" + stage.stage_name() + " Matchday " + matchday);
        acl_leagues.get(stage).simulateMatchDay(matchday);
    }

    private static void simulateLeaguePhase() {
        for (int i = 1; i <= 8; i++) {
            simulateLeaguePhaseMatchDay(Stage.ACLE_LEAGUE_EAST, i);
            simulateLeaguePhaseMatchDay(Stage.ACLE_LEAGUE_WEST, i);
        }

        System.out.println("\nACL EAST ZONE - LEAGUE PHASE");
        acl_leagues.get(Stage.ACLE_LEAGUE_EAST).getTable().showLongContinental();
        System.out.println("\nACL WEST ZONE - LEAGUE PHASE");
        acl_leagues.get(Stage.ACLE_LEAGUE_WEST).getTable().showLongContinental();
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
            gLeagues[i].getTable().showLongContinental();
        }
    }

    private static void simulateGroupStage() {
        for (int i = 1; i <= 6; i++) {
            simulateGroupsMatchDay(Stage.ACL2_GROUP_EAST, i);
            simulateGroupsMatchDay(Stage.ACL2_GROUP_WEST, i);

            simulateGroupsMatchDay(Stage.ACGL_GROUP_EAST, i);
            simulateGroupsMatchDay(Stage.ACGL_GROUP_WEST, i);
        }
        // Show Tables
        System.out.println("\nStandings");
        dispGroupTables(Stage.ACL2_GROUP_EAST);
        dispGroupTables(Stage.ACL2_GROUP_WEST);

        dispGroupTables(Stage.ACGL_GROUP_EAST);
        dispGroupTables(Stage.ACGL_GROUP_WEST);
    }

    private static List<String> getGroupTeamsAtRank(Stage stage, int rank) {
        League[] leagues = groups.get(stage);
        List<String> teams = new ArrayList<>();
        for (int i = 0; i < leagues.length; i++) {
            teams.add(leagues[i].getTeamAtRank(rank));
        }
        return teams;
    }

    private static List<String> getTeamsAtRank(League league, int lower, int upper) {
        List<String> t = new ArrayList<>();
        for (int i = lower; i <= upper; i++) {
            t.add(league.getTeamAtRank(i));
        }
        return t;
    }

    private static void setR16Stage(Stage stage) {
        List<String> seeded = new ArrayList<>();
        List<String> unseeded = new ArrayList<>();

        if (stage == Stage.ACLE_R16_EAST) {
            seeded.addAll(getTeamsAtRank(acl_leagues.get(Stage.ACLE_LEAGUE_EAST), 1, 4));
            unseeded.addAll(getTeamsAtRank(acl_leagues.get(Stage.ACLE_LEAGUE_EAST), 5, 8));
        } else if (stage == Stage.ACLE_R16_WEST) {
            seeded.addAll(getTeamsAtRank(acl_leagues.get(Stage.ACLE_LEAGUE_WEST), 1, 4));
            unseeded.addAll(getTeamsAtRank(acl_leagues.get(Stage.ACLE_LEAGUE_WEST), 5, 8));
        } else if (stage == Stage.ACL2_R16_EAST) {
            seeded.addAll(getGroupTeamsAtRank(Stage.ACL2_GROUP_EAST, 1));
            unseeded.addAll(getGroupTeamsAtRank(Stage.ACL2_GROUP_EAST, 2));
        } else if (stage == Stage.ACL2_R16_WEST) {
            seeded.addAll(getGroupTeamsAtRank(Stage.ACL2_GROUP_WEST, 1));
            unseeded.addAll(getGroupTeamsAtRank(Stage.ACL2_GROUP_WEST, 2));
        }

        List<KnockoutTie> ties = new ArrayList<>();
        int ties_size = seeded.size();
        for (int i = 0; i < ties_size; i++) {
            String selected_team = CommonFunctions.getRandomElement(seeded);
            String selected_team_2 = CommonFunctions.getRandomElement(unseeded);
            TwoLeggedKnockoutTie tie = new TwoLeggedKnockoutTie(selected_team, selected_team_2, 1);
            ties.add(tie);
            seeded.remove(selected_team);
            unseeded.remove(selected_team_2);
        }
        stages.put(stage, ties);
    }

    private static void setACLE_QFStage() {
        List<String> seeded = new ArrayList<>();
        List<String> unseeded = new ArrayList<>();
        seeded = getWinnersFromStage(Stage.ACLE_R16_EAST);
        unseeded = getWinnersFromStage(Stage.ACLE_R16_WEST);

        List<KnockoutTie> ties = new ArrayList<>();
        int size = seeded.size();
        for (int i = 0; i < size; i++) {
            String selected_team = CommonFunctions.getRandomElement(seeded);
            String selected_team_2 = CommonFunctions.getRandomElement(unseeded);
            SingleKnockoutTie tie = new SingleKnockoutTie(selected_team, selected_team_2, 1);
            ties.add(tie);
            seeded.remove(selected_team);
            unseeded.remove(selected_team_2);
        }
        stages.put(Stage.ACLE_QUARTER_FINAL, ties);
    }

    private static void setACGL_QFStage(Stage stage) {
        Stage gs = Stage.ACGL_GROUP_EAST;
        if (stage == Stage.ACGL_QF_EAST)
            gs = Stage.ACGL_GROUP_EAST;
        else if (stage == Stage.ACGL_QF_WEST)
            gs = Stage.ACGL_GROUP_WEST;

        List<String> teams_prog = getGroupTeamsAtRank(gs, 1);
        List<String> seconds = getGroupTeamsAtRank(gs, 2);

        TeamPerformance[] perfs = new TeamPerformance[seconds.size()];
        League[] lg = groups.get(gs);
        for (int i = 0; i < seconds.size(); i++) {
            for (League l : lg) {
                if (l.getTeams().contains(seconds.get(i)))
                    perfs[i] = l.getTable().getTeamPerformance(seconds.get(i));
            }
        }
        Table table = new Table(perfs.length, false);
        table.performances = perfs;
        table.sortTable();
        teams_prog.add(table.getTeamAtRank(1));

        List<KnockoutTie> ties = new ArrayList<>();
        int size = teams_prog.size();
        for (int i = 0; i < size; i += 2) {
            String selected_team = CommonFunctions.getRandomElement(teams_prog);
            teams_prog.remove(selected_team);
            String selected_team_2 = CommonFunctions.getRandomElement(teams_prog);
            teams_prog.remove(selected_team_2);
            TwoLeggedKnockoutTie tie = new TwoLeggedKnockoutTie(selected_team, selected_team_2, 1);
            ties.add(tie);
        }
        stages.put(stage, ties);
    }

    private static void qualifiers() {
        setStage(Stage.ACLE_PQ_WEST);
        setStage(Stage.ACLE_PQ_EAST);
        setStage(Stage.ACL2_PQ_EAST);
        setStage(Stage.ACL2_PQ_WEST);
        setStage(Stage.ACGL_PQ_EAST);
        setStage(Stage.ACGL_PQ_WEST);

        simulateStage(Stage.ACLE_PQ_WEST);
        simulateStage(Stage.ACLE_PQ_EAST);
        simulateStage(Stage.ACL2_PQ_WEST);
        simulateStage(Stage.ACL2_PQ_EAST);
        simulateStage(Stage.ACGL_PQ_WEST);
        simulateStage(Stage.ACGL_PQ_EAST);

        setStage(Stage.ACLE_Q_WEST);
        setStage(Stage.ACLE_Q_EAST);
        setStage(Stage.ACL2_Q_EAST);
        setStage(Stage.ACL2_Q_WEST);
        setStage(Stage.ACGL_Q_EAST);
        setStage(Stage.ACGL_Q_WEST);

        simulateStage(Stage.ACLE_Q_WEST);
        simulateStage(Stage.ACLE_Q_EAST);
        simulateStage(Stage.ACL2_Q_WEST);
        simulateStage(Stage.ACL2_Q_EAST);
        simulateStage(Stage.ACGL_Q_WEST);
        simulateStage(Stage.ACGL_Q_EAST);
    }

    private static void setGroups() {
        setStage(Stage.ACLE_LEAGUE_EAST);
        setStage(Stage.ACLE_LEAGUE_WEST);

        setStage(Stage.ACL2_GROUP_EAST);
        setStage(Stage.ACL2_GROUP_WEST);

        setStage(Stage.ACGL_GROUP_EAST);
        setStage(Stage.ACGL_GROUP_WEST);
    }

    private static void setR16Stage() {
        setR16Stage(Stage.ACLE_R16_EAST);
        setR16Stage(Stage.ACLE_R16_WEST);

        setR16Stage(Stage.ACL2_R16_EAST);
        setR16Stage(Stage.ACL2_R16_WEST);
    }

    private static void simulateR16Stage() {
        simulateStage(Stage.ACLE_R16_EAST);
        simulateStage(Stage.ACLE_R16_WEST);

        simulateStage(Stage.ACL2_R16_EAST);
        simulateStage(Stage.ACL2_R16_WEST);
    }

    private static void setQFStage() {
        setACLE_QFStage();

        setStage(Stage.ACL2_QF_EAST);
        setStage(Stage.ACL2_QF_WEST);

        setACGL_QFStage(Stage.ACGL_QF_EAST);
        setACGL_QFStage(Stage.ACGL_QF_WEST);
    }

    private static void simulateQFStage() {
        simulateStage(Stage.ACLE_QUARTER_FINAL);

        simulateStage(Stage.ACL2_QF_EAST);
        simulateStage(Stage.ACL2_QF_WEST);

        simulateStage(Stage.ACGL_QF_EAST);
        simulateStage(Stage.ACGL_QF_WEST);
    }

    private static void setSFStage() {
        setStage(Stage.ACLE_SEMI_FINAL);

        setStage(Stage.ACL2_SF_EAST);
        setStage(Stage.ACL2_SF_WEST);

        setStage(Stage.ACGL_SF_EAST);
        setStage(Stage.ACGL_SF_WEST);
    }

    private static void simulateSFStage() {
        simulateStage(Stage.ACLE_SEMI_FINAL);

        simulateStage(Stage.ACL2_SF_EAST);
        simulateStage(Stage.ACL2_SF_WEST);

        simulateStage(Stage.ACGL_SF_EAST);
        simulateStage(Stage.ACGL_SF_WEST);
    }

    private static void setFinal() {
        setStage(Stage.ACGL_FINAL);
        setStage(Stage.ACL2_FINAL);
        setStage(Stage.ACLE_FINAL);
    }

    private static void simulateFinal() {
        simulateStage(Stage.ACGL_FINAL);
        simulateStage(Stage.ACL2_FINAL);
        simulateStage(Stage.ACLE_FINAL);
    }

    public static void simulate() {
        seedings = new HashMap<>();
        stages = new HashMap<>();
        groups = new HashMap<>();
        zonal_rankings = new HashMap<>();
        team_nations = new HashMap<>();
        seeding_scores = new HashMap<>();
        ranking_info = new HashMap<>();

        setZonalRankings();
        setSeedings();
        qualifiers();
        setGroups();
        simulateLeaguePhase();
        simulateGroupStage();
        setR16Stage();
        simulateR16Stage();
        setQFStage();
        simulateQFStage();
        setSFStage();
        simulateSFStage();
        setFinal();
        simulateFinal();
        updateCompetitionHistory();
    }

    // #region CompetitionHistory
    private static Map<CCType.CompType, List<CompetitionHistory>> history = new HashMap<>();

    private static void updateHistory(CCType.CompType type) {
        Stage stageFinal = type == CCType.CompType.ACLE ? Stage.ACLE_FINAL
                : type == CCType.CompType.ACL2 ? Stage.ACL2_FINAL : Stage.ACGL_FINAL;
        List<TeamHistory> tHistory = new ArrayList<>();
        KnockoutTie tie = stages.get(stageFinal).get(0);
        tHistory.add(new TeamHistory(tie.getWinner(), 1, Sim.getClub(tie.getWinner()).getBestSquadRating()));
        tHistory.add(new TeamHistory(tie.getLoser(), 2, Sim.getClub(tie.getLoser()).getBestSquadRating()));
        List<CompetitionHistory> chistory;
        if (history.containsKey(type)) {
            chistory = history.get(type);
        } else {
            chistory = new ArrayList<>();
        }
        CompetitionHistory cHist = new CompetitionHistory(Sim.getSeasonNumber(), tHistory);
        chistory.add(cHist);
        history.put(type, chistory);
    }

    private static void updateCompetitionHistory() {
        updateHistory(CCType.CompType.ACLE);
        updateHistory(CCType.CompType.ACL2);
        updateHistory(CCType.CompType.ACGL);
    }

    private static void showHistory(CCType.CompType type) {
        System.out.println(type.title());
        List<CompetitionHistory> cHist = history.get(type);
        for (CompetitionHistory compHistory : cHist) {
            compHistory.show(2, false);
        }
    }

    public static void showCompetitionHistory() {
        showHistory(CCType.CompType.ACLE);
    }

    public static Map<CCType.CompType, List<CompetitionHistory>> getHistory() {
        return history;
    }

    private static CompetitionHistory getCompetitionHistory(CCType.CompType type, int season) {
        List<CompetitionHistory> compHistory = history.get(type);
        for (CompetitionHistory c : compHistory) {
            if (c.getSeason() == season)
                return c;
        }
        return null;
    }
    // #endregion CompetitionHistory

}
