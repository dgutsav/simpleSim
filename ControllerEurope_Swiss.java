import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ControllerEurope_Swiss {
    private enum SeedingType {
        CL_GROUP,
        CL_Q4_CP,
        CL_Q4_MP,
        CL_Q3_CP,
        CL_Q3_MP,
        CL_Q2_CP,
        CL_Q2_MP,
        CL_Q1_CP,
        EL_GROUP,
        EL_Q4_MP,
        EL_Q3_MP,
        EL_Q2_MP,
        EL_Q1_MP,
        ECL_Q4,
        ECL_Q3,
        ECL_Q2,
        ECL_Q1,
        DEFAULT
    }

    private enum Stage {
        CL_FINAL("Champions League Final", CCType.StageType.KNOCKOUT, CCType.CompType.UCL, false, false),
        CL_SF("Champions League Semi-Final", CCType.StageType.TWO_LEGGED_KNOCKOUT,
                CCType.CompType.UCL, false, false),
        CL_QF("Champions League Quarter-Final", CCType.StageType.TWO_LEGGED_KNOCKOUT,
                CCType.CompType.UCL, false, false),
        CL_R16("Champions League Round of 16", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.UCL,
                true, false),
        CL_KO_PO("Champions League Knockout Play-off", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.UCL,
                true, false),
        CL_GROUP("CL League Phase", CCType.StageType.SWISS_LEAGUE, CCType.CompType.UCL, true, false),
        CL_Q4_CP("CL Play-off Champions Path", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.UCL,
                true, true),
        CL_Q4_MP("CL Play-off Main Path", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.UCL, true,
                true),
        CL_Q3_CP("CL Third Qualifying Round Champions Path", CCType.StageType.TWO_LEGGED_KNOCKOUT,
                CCType.CompType.UCL, true, true),
        CL_Q3_MP("CL Third Qualifying Round Main Path", CCType.StageType.TWO_LEGGED_KNOCKOUT,
                CCType.CompType.UCL, true, true),
        CL_Q2_CP("CL Second Qualifying Round Champions Path", CCType.StageType.TWO_LEGGED_KNOCKOUT,
                CCType.CompType.UCL, true, true),
        CL_Q2_MP("CL Second Qualifying Round Main Path", CCType.StageType.TWO_LEGGED_KNOCKOUT,
                CCType.CompType.UCL, true, true),
        CL_Q1_CP("CL First Qualifying Round Champions Path", CCType.StageType.TWO_LEGGED_KNOCKOUT,
                CCType.CompType.UEL, true, true),
        EL_Q1_MP("EL First Qualifying Round Main Path", CCType.StageType.TWO_LEGGED_KNOCKOUT,
                CCType.CompType.UEL, true, true),
        EL_Q2_MP("EL Second Qualifying Round Main Path", CCType.StageType.TWO_LEGGED_KNOCKOUT,
                CCType.CompType.UEL, true, true),
        EL_Q3_MP("EL Third Qualifying Round Main Path", CCType.StageType.TWO_LEGGED_KNOCKOUT,
                CCType.CompType.UEL, true, true),
        EL_Q3_CP("EL Third Qualifying Round Chaampions Path", CCType.StageType.TWO_LEGGED_KNOCKOUT,
                CCType.CompType.UEL, true, true),
        EL_Q4_MP("EL Play-off Round", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.UEL,
                true, true),
        EL_Q4_CP("EL Play-off Round Champions Path", CCType.StageType.TWO_LEGGED_KNOCKOUT,
                CCType.CompType.UEL, true, true),
        EL_GROUP("EL League Phase", CCType.StageType.SWISS_LEAGUE, CCType.CompType.UEL, true, false),
        EL_KO_PO("Europa League Knockout Play-off", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.UEL,
                true, false),
        EL_R16("Europa League Round of 16", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.UEL, true,
                false),
        EL_QUARTER_FINAL("Europa League Quarter-Final", CCType.StageType.TWO_LEGGED_KNOCKOUT,
                CCType.CompType.UEL, false, false),
        EL_SEMI_FINAL("Europa League Semi-Final", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.UEL,
                false, false),
        EL_FINAL("Europa League Final", CCType.StageType.KNOCKOUT, CCType.CompType.UEL, false, false),
        ECL_FINAL("Europa Conference League Final", CCType.StageType.KNOCKOUT, CCType.CompType.UECL, false,
                false),
        ECL_SF("ECL Semi-Final", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.UECL, false,
                false),
        ECL_QF("ECL Quarter-Final", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.UECL,
                false, false),
        ECL_R16("ECL Round of 16", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.UECL, true,
                false),
        ECL_KO_PO("ECL Knockout Play-off", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.UECL,
                true, false),
        ECL_GROUP("ECL League Phase", CCType.StageType.SWISS_LEAGUE, CCType.CompType.UECL, true, false),
        ECL_Q1_MP("ECL First Qualifying Round", CCType.StageType.TWO_LEGGED_KNOCKOUT, CCType.CompType.UECL,
                true, true),
        ECL_Q2_MP("ECL Second Qualifying Round Main Path", CCType.StageType.TWO_LEGGED_KNOCKOUT,
                CCType.CompType.UECL, true, true),
        ECL_Q2_CP("ECL Second Qualifying Round Champions Path", CCType.StageType.TWO_LEGGED_KNOCKOUT,
                CCType.CompType.UECL, true, true),
        ECL_Q3_MP("ECL Third Qualifying Round Main Path", CCType.StageType.TWO_LEGGED_KNOCKOUT,
                CCType.CompType.UECL, true, true),
        ECL_Q3_CP("ECL Third Qualifying Round Champions Path", CCType.StageType.TWO_LEGGED_KNOCKOUT,
                CCType.CompType.UECL, true, true),
        ECL_Q4_MP("ECL Play-off Round Main Path", CCType.StageType.TWO_LEGGED_KNOCKOUT,
                CCType.CompType.UECL, true, true),
        ECL_Q4_CP("ECL Play-off Round Champions Path", CCType.StageType.TWO_LEGGED_KNOCKOUT,
                CCType.CompType.UECL, true, true),
        DEFAULT("Default", CCType.StageType.KNOCKOUT, CCType.CompType.UCL, false, false);

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

    private static Map<Stage, List<TwoLeggedKnockoutTie>> stages = new HashMap<>();
    private static Map<String, SeedingType> seedings = new HashMap<>();
    private static Map<String, Integer> seeding_scores = new HashMap<>();
    private static Map<String, Integer> team_nations = new HashMap<>();

    private static List<String> bye_teams = new ArrayList<>();
    private static List<String> top_dom_champions = new ArrayList<>();

    private static Map<Stage, League> leagues = new HashMap<>();
    private static Map<Stage, League[]> groups = new HashMap<>();

    private static Map<CCType.CompType, String> prev_champions = new HashMap<>();

    private static void setDefaultChampions() {
        int cl_n = 0, el_n = 0, ecl_n = 0;
        Map<Integer, Integer> league_rankings = getLeagueRankingsForSeason(Sim.getSeasonNumber());
        for (Map.Entry<Integer, Integer> entry : league_rankings.entrySet()) {
            if (entry.getValue() == 1)
                cl_n = entry.getKey();
            else if (entry.getValue() == 2)
                el_n = entry.getKey();
            else if (entry.getValue() == 3)
                ecl_n = entry.getKey();

            if (cl_n != 0 && el_n != 0 && ecl_n != 0)
                break;
        }
        //System.out.println("CL N: " + cl_n + " Nation: " + Sim.getNation(cl_n).getLeague(1).getName());
        prev_champions.put(CCType.CompType.UCL,
                Sim.sortByReputation(Sim.getNation(cl_n).getTeamIds(1)).get(0));

        prev_champions.put(CCType.CompType.UEL,
                Sim.sortByReputation(Sim.getNation(el_n).getTeamIds(1)).get(0));

        prev_champions.put(CCType.CompType.UECL,
                Sim.sortByReputation(Sim.getNation(ecl_n).getTeamIds(1)).get(0));
    }

    private static void setPreviousChampions() {
        if (Sim.isFirstSeason()) {
            setDefaultChampions();
        } else {
            int last_season = Sim.getSeasonNumber() - 1;
            prev_champions.put(CCType.CompType.UCL,
                    getCompetitionHistory(CCType.CompType.UCL, last_season).getWinner());
            prev_champions.put(CCType.CompType.UEL,
                    getCompetitionHistory(CCType.CompType.UEL, last_season).getWinner());
            prev_champions.put(CCType.CompType.UECL,
                    getCompetitionHistory(CCType.CompType.UECL, last_season).getWinner());
        }
    }

    private static int getPlaces(CCType.CompType type, int rank) {
        switch (type) {
            case UCL:
                if (rank <= 5)
                    return 4;
                else if (rank <= 6)
                    return 3;
                else if (rank <= 15)
                    return 2;
                else if (rank <= 50)
                    return 1;
                else
                    return 0;
            case UEL:
                if (rank <= 12)
                    return 2;
                else if (rank <= 33)
                    return 1;
                else
                    return 0;
            case UECL:
                if (rank <= 12)
                    return 1;
                else if (rank <= 33)
                    return 2;
                else if (rank <= 50)
                    return 3;
                else
                    return 0;
            default:
                return 0;
        }
    }

    private static Map<CCType.CompType, List<String>> getTeams(Nation nation, int ranking) {
        List<String> teams = new ArrayList<>();
        int cl_places = getPlaces(CCType.CompType.UCL, ranking);
        if (sortedLeaguesFromSeason(Sim.getSeasonNumber() - 1).subList(0, 2).contains(nation.getId()))
            cl_places += 1;
        int el_places = getPlaces(CCType.CompType.UEL, ranking);
        int ecl_places = getPlaces(CCType.CompType.UECL, ranking);
        for (int i = 1; i <= cl_places + el_places + ecl_places; i++) {
            teams.add(nation.getDivision(1).getTeamAtRank(i));
        }
        String cw = nation.getCupWinner();
        if (!teams.contains(cw)) {
            teams.add(cl_places, cw);
            teams.remove(teams.size() - 1);
        }

        Map<CCType.CompType, List<String>> map = new HashMap<>();
        map.put(CCType.CompType.UCL, teams.subList(0, cl_places));
        map.put(CCType.CompType.UEL, teams.subList(cl_places, cl_places + el_places));
        map.put(CCType.CompType.UECL,
                teams.subList(cl_places + el_places, cl_places + el_places + ecl_places));
        return map;
    }

    private static int getBaseSeedingScore(SeedingType type) {
        if (type == SeedingType.CL_GROUP) {
            return 0;
        } else if (type == SeedingType.CL_Q4_CP) {
            return 100;
        } else if (type == SeedingType.CL_Q3_CP) {
            return 200;
        } else if (type == SeedingType.CL_Q2_CP) {
            return 300;
        } else if (type == SeedingType.CL_Q1_CP) {
            return 400;
        } else if (type == SeedingType.CL_Q3_MP) {
            return 500;
        } else if (type == SeedingType.CL_Q2_MP) {
            return 600;
        } else if (type == SeedingType.EL_GROUP) {
            return 1000;
        } else if (type == SeedingType.EL_Q4_MP) {
            return 1100;
        } else if (type == SeedingType.EL_Q3_MP) {
            return 1200;
        } else if (type == SeedingType.EL_Q2_MP) {
            return 1300;
        } else if (type == SeedingType.EL_Q1_MP) {
            return 1400;
        } else if (type == SeedingType.ECL_Q4) {
            return 2100;
        } else if (type == SeedingType.ECL_Q3) {
            return 2200;
        } else if (type == SeedingType.ECL_Q2) {
            return 2300;
        } else if (type == SeedingType.ECL_Q1) {
            return 2400;
        } else {
            return 4000;
        }
    }

    private static int getMaxSeedingScore(SeedingType type) {
        return getBaseSeedingScore(type) + 99;
    }

    private static List<String> getSortedSeedings(boolean ascending, int limit, int min, int max) {
        Map<String, Integer> map = CommonFunctions.sortMap(seeding_scores, ascending, limit, min, max);
        List<String> list = new ArrayList<>(map.keySet());
        return list;
    }

    private static int getSeedingScore(CCType.CompType type, int league_rank, int rank) {
        switch (type) {
            case UCL:
                if (league_rank <= 4 && rank <= 4)
                    return getBaseSeedingScore(SeedingType.CL_GROUP);
                else if (league_rank == 5) {
                    if (rank <= 3)
                        return getBaseSeedingScore(SeedingType.CL_GROUP);
                    else if (rank == 4)
                        return getBaseSeedingScore(SeedingType.CL_Q3_MP) + league_rank;
                } else if (league_rank == 6) {
                    if (rank <= 2)
                        return getBaseSeedingScore(SeedingType.CL_GROUP);
                    else if (rank == 3)
                        return getBaseSeedingScore(SeedingType.CL_Q3_MP) + league_rank;
                } else if (league_rank >= 7 && league_rank <= 9) {
                    if (rank == 1)
                        return getBaseSeedingScore(SeedingType.CL_GROUP);
                    else if (rank == 2)
                        return getBaseSeedingScore(SeedingType.CL_Q3_MP) + league_rank;
                } else if (league_rank == 10) {
                    if (rank == 1)
                        return getBaseSeedingScore(SeedingType.CL_GROUP);
                    else if (rank == 2)
                        return getBaseSeedingScore(SeedingType.CL_Q2_MP) + league_rank;
                } else if (league_rank <= 12) {
                    if (rank == 1) {
                        return getBaseSeedingScore(SeedingType.CL_Q4_CP) + (league_rank - 10);
                    } else if (rank == 2) {
                        return getBaseSeedingScore(SeedingType.CL_Q2_MP) + league_rank;
                    }
                } else if (league_rank <= 15) {
                    if (rank == 1) {
                        return getBaseSeedingScore(SeedingType.CL_Q3_CP) + (league_rank - 12);
                    } else if (rank == 2) {
                        return getBaseSeedingScore(SeedingType.CL_Q2_MP) + league_rank;
                    }
                } else if (league_rank <= 18 && rank == 1) {
                    return getBaseSeedingScore(SeedingType.CL_Q3_CP) + (league_rank - 12);
                } else if (league_rank <= 26 && rank == 1) {
                    return getBaseSeedingScore(SeedingType.CL_Q2_CP) + (league_rank - 18);
                } else if (league_rank <= 50 && rank == 1) {
                    return getBaseSeedingScore(SeedingType.CL_Q1_CP) + (league_rank - 26);
                }
                break;
            case UEL:
                if (league_rank <= 5 && (rank == 1 || rank == 2)) {
                    return getBaseSeedingScore(SeedingType.EL_GROUP);
                } else if (league_rank == 6) {
                    if (rank == 1)
                        return getBaseSeedingScore(SeedingType.EL_GROUP);
                    else if (rank == 2)
                        return getBaseSeedingScore(SeedingType.EL_Q2_MP) + (league_rank);
                } else if (league_rank == 7) {
                    if (rank == 1)
                        return getBaseSeedingScore(SeedingType.EL_GROUP);
                    else if (rank == 2)
                        return getBaseSeedingScore(SeedingType.EL_Q2_MP) + (league_rank);
                } else if (league_rank <= 11) {
                    if (rank == 1) {
                        return getBaseSeedingScore(SeedingType.EL_Q4_MP) + (league_rank - 7);
                    } else if (rank == 2) {
                        return getBaseSeedingScore(SeedingType.EL_Q2_MP) + (league_rank);
                    }
                } else if (league_rank == 12) {
                    if (rank == 1)
                        return getBaseSeedingScore(SeedingType.EL_Q3_MP) + (league_rank - 11);
                    else if (rank == 2) {
                        return getBaseSeedingScore(SeedingType.EL_Q2_MP) + (league_rank);
                    }
                } else if (league_rank == 13 && rank == 1) {
                    return getBaseSeedingScore(SeedingType.EL_Q3_MP) + (league_rank - 11);
                } else if (league_rank <= 15 && rank == 1) {
                    return getBaseSeedingScore(SeedingType.EL_Q2_MP) + (league_rank - 13);
                } else if (league_rank <= 33 && rank == 1) {
                    return getBaseSeedingScore(SeedingType.EL_Q1_MP) + (league_rank - 15);
                }
                break;
            case UECL:
                if (league_rank <= 5 && rank == 1)
                    return getBaseSeedingScore(SeedingType.ECL_Q4) + league_rank;
                else if (league_rank == 6 && rank == 1)
                    return getBaseSeedingScore(SeedingType.ECL_Q4) + league_rank;
                else if (league_rank <= 8 && rank == 1)
                    return getBaseSeedingScore(SeedingType.ECL_Q4) + league_rank;
                else if (league_rank <= 12 && rank == 1)
                    return getBaseSeedingScore(SeedingType.ECL_Q4) + league_rank;
                else if (league_rank <= 15) {
                    if (rank == 1)
                        return getBaseSeedingScore(SeedingType.ECL_Q4) + league_rank;
                    else if (rank == 2)
                        return getBaseSeedingScore(SeedingType.ECL_Q2) + league_rank;
                } else if (league_rank <= 33) {
                    if (rank == 1)
                        return getBaseSeedingScore(SeedingType.ECL_Q4) + league_rank;
                    else if (rank == 2)
                        return getBaseSeedingScore(SeedingType.ECL_Q2) + league_rank;
                } else if (league_rank <= 50) {
                    if (rank == 1)
                        return getBaseSeedingScore(SeedingType.ECL_Q4) + league_rank;
                    else if (rank == 2)
                        return getBaseSeedingScore(SeedingType.ECL_Q2) + league_rank;
                    else if (rank == 3)
                        return getBaseSeedingScore(SeedingType.ECL_Q1) + league_rank;
                }
                break;
        }
        return 10000;
    }

    private static void setSeedings() {
        setPreviousChampions();
        setSeedingScores();
        addPreviousChampions();
        adjustGroupQualifiedTeams();
        calculateAndSelectQualifierFormat();
    }

    private static void setSeedingScores() {
        List<Nation> nations = Sim.getNationFromContinent("Europe");
        Map<Integer, Integer> league_rankings = getLeagueRankingsForSeason(Sim.getSeasonNumber());
        List<Integer> extra_spots = sortedLeaguesFromSeason(Sim.getSeasonNumber() - 1).subList(0, 2);
        int seed_count = 0;
        for (Nation nation : nations) {
            int ranking = league_rankings.get(nation.getId());
            Map<CCType.CompType, List<String>> teams = getTeams(nation, ranking);
            if (extra_spots.contains(nation.getId())) {
                List<String> qTeams = teams.get(CCType.CompType.UCL);
                String team = qTeams.get(0);
                seeding_scores.put(team, getBaseSeedingScore(SeedingType.CL_GROUP));
                team_nations.put(team, nation.getId());
                List<String> newList = qTeams.subList(1, qTeams.size());
                teams.put(CCType.CompType.UCL, newList);
            }
            for (Map.Entry<CCType.CompType, List<String>> entry : teams.entrySet()) {
                CCType.CompType type = entry.getKey();
                List<String> nat_teams = entry.getValue();
                for (int i = 0; i < nat_teams.size(); i++) {
                    int rank = i + 1;
                    int score = getSeedingScore(type, ranking, rank);
                    seeding_scores.put(nat_teams.get(i), score);
                    team_nations.put(nat_teams.get(i), nation.getId());
                    if (score >= getBaseSeedingScore(SeedingType.CL_Q3_MP)
                            && score <= getMaxSeedingScore(SeedingType.CL_Q2_MP))
                        System.out.println((++seed_count) + ": " + Sim.getClub(nat_teams.get(i)).getName() + " "
                                + Sim.getNation(nation.getId()).getCode() + " rank: " + rank + " nation rank: "
                                + ranking + " score: " + score);
                }
            }
        }
    }

    private static void addPreviousChampions() {
        String cl_champion = prev_champions.get(CCType.CompType.UCL);
        seeding_scores.put(cl_champion, getBaseSeedingScore(SeedingType.CL_GROUP));
        team_nations.put(cl_champion, Sim.getClub(cl_champion).getNationId());

        String el_champion = prev_champions.get(CCType.CompType.UEL);
        seeding_scores.put(el_champion, getBaseSeedingScore(SeedingType.CL_GROUP));
        team_nations.put(el_champion, Sim.getClub(el_champion).getNationId());

        String ecl_champion = prev_champions.get(CCType.CompType.UECL);
        if (seeding_scores.containsKey(ecl_champion)) {
            if (seeding_scores.get(ecl_champion) > getBaseSeedingScore(SeedingType.EL_GROUP)) {
                seeding_scores.put(ecl_champion, getBaseSeedingScore(SeedingType.EL_GROUP));
            }
        } else {
            seeding_scores.put(ecl_champion, getBaseSeedingScore(SeedingType.EL_GROUP));
            team_nations.put(ecl_champion, Sim.getClub(ecl_champion).getNationId());
        }
    }

    private static void adjustGroupQualifiedTeams() {
        int CL_DIRECT = 29, cl_direct = 0;
        int EL_DIRECT = 13, el_direct = 0;

        int cl_seeding = getBaseSeedingScore(SeedingType.CL_GROUP),
                el_seeding = getBaseSeedingScore(SeedingType.EL_GROUP);
        for (Map.Entry<String, Integer> entry : seeding_scores.entrySet()) {
            if (entry.getValue() == cl_seeding) {
                cl_direct++;
                seedings.put(entry.getKey(), SeedingType.CL_GROUP);
            } else if (entry.getValue() == el_seeding) {
                el_direct++;
                seedings.put(entry.getKey(), SeedingType.EL_GROUP);
            }
        }

        int cl_diff = CL_DIRECT - cl_direct;
        int el_diff = EL_DIRECT - el_direct;
        System.out.println("CL DIRECT FOUND: " + cl_direct + " NEED MORE: " + cl_diff);
        System.out.println("EL DIRECT FOUND: " + el_direct + " NEED MORE: " + el_diff);

        List<String> cl_balance = getSortedSeedings(true, cl_diff, getBaseSeedingScore(SeedingType.CL_Q4_CP),
                getMaxSeedingScore(SeedingType.CL_Q1_CP));
        List<String> el_balance = getSortedSeedings(true, el_diff, getBaseSeedingScore(SeedingType.EL_Q4_MP),
                getMaxSeedingScore(SeedingType.EL_Q1_MP));

        for (String team : cl_balance) {
            seedings.put(team, SeedingType.CL_GROUP);
            seeding_scores.put(team, getBaseSeedingScore(SeedingType.CL_GROUP));
        }

        for (String team : el_balance) {
            seedings.put(team, SeedingType.EL_GROUP);
            seeding_scores.put(team, getBaseSeedingScore(SeedingType.EL_GROUP));
        }
    }

    private static void calculateAndSelectQualifierFormat() {
        int qcl = getSortedSeedingsList(false, 100, SeedingType.CL_Q4_CP, SeedingType.CL_Q1_CP).size();
        int qmcl = getSortedSeedingsList(false, 100, SeedingType.CL_Q3_MP, SeedingType.CL_Q2_MP).size();
        int qel = getSortedSeedingsList(false, 100, SeedingType.EL_Q4_MP, SeedingType.EL_Q1_MP).size();
        int qecl = getSortedSeedingsList(false, 1000, SeedingType.ECL_Q4, SeedingType.ECL_Q1).size();

        System.out.println("QCL: " + qcl + " QEL: " + qel + " QECL: " + qecl + " QCL(M): " + qmcl);

        if (qcl == 40 && qmcl == 11 && qel == 33 && qecl == 105) {
            setQualifierFormat(24, 8, 6, 2, 6, 5, 0, 18, 9, 2, 4, 48, 49, 8);
        } else if (qcl == 40 && qmcl == 11 && qel == 32 && qecl == 105) {
            setQualifierFormat(24, 8, 6, 2, 6, 5, 0, 18, 9, 0, 5, 46, 50, 9);
        } else if (qcl == 40 && qmcl == 11 && qel == 31 && qecl == 105) {
            setQualifierFormat(24, 8, 6, 2, 6, 5, 0, 16, 8, 3, 4, 48, 48, 9);
        } else if (qcl == 39 && qmcl == 11 && qel == 33 && qecl == 105) {
            setQualifierFormat(24, 8, 4, 3, 6, 5, 0, 18, 7, 3, 5, 50, 46, 9);
        } else if (qcl == 39 && qmcl == 11 && qel == 32 && qecl == 105) {
            setQualifierFormat(24, 8, 4, 3, 6, 5, 0, 16, 8, 3, 5, 48, 48, 9);
        } else if (qcl == 39 && qmcl == 11 && qel == 31 && qecl == 105) {
            setQualifierFormat(24, 8, 4, 3, 6, 5, 0, 16, 8, 1, 6, 46, 49, 10);
        } else if (qcl == 38 && qmcl == 11 && qel == 33 && qecl == 105) {
            setQualifierFormat(24, 8, 2, 4, 6, 5, 0, 16, 8, 3, 6, 48, 48, 9);
        } else if (qcl == 38 && qmcl == 11 && qel == 32 && qecl == 105) {
            setQualifierFormat(24, 8, 2, 4, 6, 5, 0, 16, 8, 1, 7, 46, 49, 10);
        } else if (qcl == 40 && qmcl == 11 && qel == 30 && qecl == 105) {
            setQualifierFormat(24, 8, 6, 2, 8, 2, 1, 14, 9, 0, 7, 48, 49, 8);
        } else if (qcl == 39 && qmcl == 10 && qel == 32 && qecl == 105) {
            setQualifierFormat(24, 8, 4, 3, 6, 3, 1, 14, 9, 1, 8, 48, 49, 8);
        } else if (qcl == 39 && qmcl == 10 && qel == 33 && qecl == 105) {
            setQualifierFormat(24, 8, 4, 3, 6, 3, 1, 14, 9, 3, 7, 50, 48, 7);
        } else if (qcl == 40 && qmcl == 10 && qel == 33 && qecl == 105) {
            setQualifierFormat(24, 8, 6, 2, 6, 3, 1, 18, 5, 4, 6, 50, 48, 7);
        } else if (qcl == 40 && qmcl == 10 && qel == 32 && qecl == 105) {
            setQualifierFormat(24, 8, 6, 2, 6, 3, 1, 14, 9, 3, 6, 50, 48, 7);
        } else if (qcl == 40 && qmcl == 11 && qel == 31 && qecl == 104) {
            setQualifierFormat(24, 8, 6, 2, 8, 2, 1, 14, 9, 2, 6, 48, 49, 7);
        } else if (qcl == 39 && qmcl == 11 && qel == 33 && qecl == 104) {
            setQualifierFormat(24, 8, 4, 3, 6, 5, 0, 16, 8, 5, 4, 48, 48, 8);
        } else if (qcl == 39 && qmcl == 11 && qel == 32 && qecl == 104) {
            setQualifierFormat(24, 8, 4, 3, 6, 5, 0, 16, 8, 3, 5, 46, 49, 9);
        } else if (qcl == 38 && qmcl == 11 && qel == 33 && qecl == 104) {
            setQualifierFormat(24, 8, 2, 4, 6, 5, 0, 16, 8, 3, 6, 46, 49, 9);
        } else if (qcl == 38 && qmcl == 11 && qel == 32 && qecl == 104) {
            setQualifierFormat(24, 8, 2, 4, 6, 5, 0, 16, 6, 4, 6, 48, 46, 10);
        } else if (qcl == 40 && qmcl == 11 && qel == 33 && qecl == 104) {
            setQualifierFormat(24, 8, 6, 2, 6, 5, 0, 16, 12, 1, 4, 48, 48, 8);
        } else if (qcl == 40 && qmcl == 11 && qel == 32 && qecl == 104) {
            setQualifierFormat(24, 8, 6, 2, 6, 5, 0, 16, 8, 5, 3, 48, 48, 8);
        } else if (qcl == 40 && qmcl == 11 && qel == 33 && qecl == 103) {
            setQualifierFormat(24, 8, 6, 2, 8, 2, 1, 16, 8, 4, 5, 50, 47, 6);
        } else if (qcl == 39 && qmcl == 11 && qel == 33 && qecl == 103) {
            setQualifierFormat(24, 8, 4, 3, 8, 2, 1, 16, 8, 2, 7, 48, 48, 7);
        } else if (qcl == 39 && qmcl == 10 && qel == 33 && qecl == 104) {
            setQualifierFormat(24, 8, 4, 3, 6, 3, 1, 14, 9, 3, 7, 48, 49, 7);
        } else {
            int[] sln = EuropeanFormatCalculator.getSolution(qcl, qmcl, qel, qecl);
            if (sln != null) {
                setQualifierFormat(sln[0], sln[1], sln[2], sln[3], sln[4], sln[5], sln[6], sln[7], sln[8], sln[9],
                        sln[10],
                        sln[11], sln[12], sln[13]);
            }
        }
    }

    private static List<String> getSortedSeedingsList(boolean ascending, int limit, SeedingType base, SeedingType max) {
        return getSortedSeedings(ascending, limit, getBaseSeedingScore(base), getMaxSeedingScore(max));
    }

    private static void setCLChampionsQualifierFormat(int c1, int c2, int c3, int c4) {
        List<String> clq = getSortedSeedingsList(false, 100, SeedingType.CL_Q4_CP, SeedingType.CL_Q1_CP);
        int count_cl = 0;
        for (String cl : clq) {
            SeedingType seeding = SeedingType.DEFAULT;
            if (count_cl < c1) {
                seeding = SeedingType.CL_Q1_CP;
            } else if (count_cl < c1 + c2) {
                seeding = SeedingType.CL_Q2_CP;
            } else if (count_cl < c1 + c2 + c3) {
                seeding = SeedingType.CL_Q3_CP;
            } else if (count_cl < c1 + c2 + c3 + c4) {
                seeding = SeedingType.CL_Q4_CP;
            }
            seedings.put(cl, seeding);
            count_cl++;
        }
    }

    private static void setCLLeagueQualifierFormat(int m2, int m3, int m4) {
        List<String> mclq = getSortedSeedingsList(false, 100, SeedingType.CL_Q3_MP, SeedingType.CL_Q2_MP);
        int count_mcl = 0;
        for (String mcl : mclq) {
            SeedingType seeding = SeedingType.DEFAULT;
            if (count_mcl < m2) {
                seeding = SeedingType.CL_Q2_MP;
            } else if (count_mcl < m2 + m3) {
                seeding = SeedingType.CL_Q3_MP;
            } else if (count_mcl < m2 + m3 + m4) {
                seeding = SeedingType.CL_Q4_MP;
            }
            seedings.put(mcl, seeding);
            count_mcl++;
        }
    }

    private static void setELQualifierFormat(int e1, int e2, int e3, int e4) {
        List<String> elq = getSortedSeedingsList(false, 100, SeedingType.EL_Q4_MP, SeedingType.EL_Q1_MP);
        int count_el = 0;
        for (String el : elq) {
            SeedingType seeding = SeedingType.DEFAULT;
            if (count_el < e1) {
                seeding = SeedingType.EL_Q1_MP;
            } else if (count_el < e1 + e2) {
                seeding = SeedingType.EL_Q2_MP;
            } else if (count_el < e1 + e2 + e3) {
                seeding = SeedingType.EL_Q3_MP;
            } else if (count_el < e1 + e2 + e3 + e4) {
                seeding = SeedingType.EL_Q4_MP;
            }
            seedings.put(el, seeding);
            count_el++;
        }
    }

    private static void setECLQualifierFormat(int f1, int f2, int f4) {
        List<String> eclq = getSortedSeedingsList(false, 1000, SeedingType.ECL_Q4, SeedingType.ECL_Q1);
        int count_ecl = 0;
        for (String ecl : eclq) {
            SeedingType seeding = SeedingType.DEFAULT;
            if (count_ecl < f1) {
                seeding = SeedingType.ECL_Q1;
            } else if (count_ecl < f1 + f2) {
                seeding = SeedingType.ECL_Q2;
            } else if (count_ecl < f1 + f2 + f4) {
                seeding = SeedingType.ECL_Q4;
            }
            seedings.put(ecl, seeding);
            count_ecl++;
        }
    }

    private static void setQualifierFormat(int c1, int c2, int c3, int c4, int m2, int m3, int m4, int e1, int e2,
            int e3, int e4, int f1, int f2, int f4) {
        setCLChampionsQualifierFormat(c1, c2, c3, c4);
        setCLLeagueQualifierFormat(m2, m3, m4);
        setELQualifierFormat(e1, e2, e3, e4);
        setECLQualifierFormat(f1, f2, f4);
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
            case EL_Q2_MP:
                stages.add(Stage.EL_Q1_MP);
                break;
            case EL_Q3_MP:
                stages.add(Stage.EL_Q2_MP);
                break;
            case EL_Q4_MP:
                stages.add(Stage.EL_Q3_MP);
                stages.add(Stage.EL_Q3_CP);
                break;
            case EL_Q4_CP:
                break;
            case EL_GROUP:
                // stages.add(Stage.EL_Q4_CP);
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
            case CL_QF:
                stages.add(Stage.CL_R16);
                break;
            case EL_QUARTER_FINAL:
                stages.add(Stage.EL_R16);
                break;
            case ECL_QF:
                stages.add(Stage.ECL_R16);
                break;
            case CL_SF:
                stages.add(Stage.CL_QF);
                break;
            case EL_SEMI_FINAL:
                stages.add(Stage.EL_QUARTER_FINAL);
                break;
            case ECL_SF:
                stages.add(Stage.ECL_QF);
                break;
            case CL_FINAL:
                stages.add(Stage.CL_SF);
                break;
            case EL_FINAL:
                stages.add(Stage.EL_SEMI_FINAL);
                break;
            case ECL_FINAL:
                stages.add(Stage.ECL_SF);
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
            case EL_Q4_MP:
                stages.add(Stage.CL_Q3_CP);
                break;
            case EL_GROUP:
                stages.add(Stage.CL_Q4_CP);
                stages.add(Stage.CL_Q4_MP);
                stages.add(Stage.CL_Q3_MP);
                break;
            case ECL_Q2_MP:
                stages.add(Stage.EL_Q1_MP);
                break;
            case ECL_Q2_CP:
                stages.add(Stage.CL_Q1_CP);
                break;
            case ECL_Q3_MP:
                stages.add(Stage.EL_Q2_MP);
                break;
            case ECL_Q4_CP:
                stages.add(Stage.EL_Q3_CP);
                break;
            case ECL_Q4_MP:
                stages.add(Stage.EL_Q3_MP);
                break;
            case ECL_GROUP:
                // stages.add(Stage.EL_Q4_CP);
                stages.add(Stage.EL_Q4_MP);
                break;
            default:
                break;
        }
        return stages;
    }

    private static SeedingType getSeedingTypeForStage(Stage stage) {
        switch (stage) {
            case CL_GROUP:
                return SeedingType.CL_GROUP;
            case CL_Q4_CP:
                return SeedingType.CL_Q4_CP;
            case CL_Q4_MP:
                return SeedingType.CL_Q4_MP;
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
            case EL_Q1_MP:
                return SeedingType.EL_Q1_MP;
            case EL_Q2_MP:
                return SeedingType.EL_Q2_MP;
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

    private static boolean isStageGroup(Stage stage) {
        return stage.stage_type() == CCType.StageType.GROUPS;
    }

    private static boolean isStageSwissLeague(Stage stage) {
        return stage.stage_type() == CCType.StageType.SWISS_LEAGUE;
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
        int BYE_TEAMS_COUNT = 0;
        for (int i = 0; i < BYE_TEAMS_COUNT; i++) {
            adjusted.add(teams.remove(BYE_TEAMS_COUNT - (i + 1)));
        }
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

    private static String[][] divideIntoPots(List<String> teams, Stage stage) {
        int pc = 0, pn = 0;
        if (stage == Stage.CL_GROUP || stage == Stage.EL_GROUP) {
            pc = 4;
            pn = 9;
        } else if (stage == Stage.ECL_GROUP) {
            pc = 6;
            pn = 6;
        }
        String[][] pots = new String[pc][pn];
        for (int i = 0; i < pc; i++) {
            for (int j = 0; j < pn; j++) {
                pots[i][j] = teams.get((i * pn) + j);
            }
        }
        return pots;
    }

    private static List<String> getWinnersFromStage(Stage stage) {
        List<String> winners = new ArrayList<>();
        List<TwoLeggedKnockoutTie> ties = stages.get(stage);
        for (TwoLeggedKnockoutTie tie : ties) {
            winners.add(tie.getWinner());
        }
        return winners;
    }

    private static List<String> getLosersFromStage(Stage stage) {
        List<String> losers = new ArrayList<>();
        List<TwoLeggedKnockoutTie> ties = stages.get(stage);
        for (TwoLeggedKnockoutTie tie : ties) {
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

        if (isStageSwissLeague(type)) {
            String[][] pots = divideIntoPots(sortByCoefficients(teams), type);
            League league = new League();
            for (int i = 0; i < teams.size(); i++) {
                league.addTeam(teams.get(i));
            }
            EuropeanFixtures f = new EuropeanFixtures();
            List<Match> matches = new ArrayList<>();
            if (type == Stage.CL_GROUP || type == Stage.EL_GROUP) {
                f.initCL(pots, team_nations);
                matches = f.getFixtures();
            } else if (type == Stage.ECL_GROUP) {
                f.initConfLeague(pots, team_nations);
                matches = f.getFixturesConf();
            }
            league.setFixtures(matches);
            league.setupTable();
            leagues.put(type, league);

        } else if (!isStageGroup(type)) {
            if (teams.size() % 2 != 0)
                System.out.println("Irregular no of teams found for stage: " + type + " " + teams.size());
            teams = CommonFunctions.sortByCoefficients(teams);
            int len = teams.size();
            ArrayList<String> seeded = new ArrayList<>();
            seeded.addAll(teams.subList(0, len / 2));
            ArrayList<String> unseeded = new ArrayList<>();
            unseeded.addAll(teams.subList(len / 2, len));
            List<TwoLeggedKnockoutTie> ties = new ArrayList<>();
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
                TwoLeggedKnockoutTie tie = new TwoLeggedKnockoutTie(selected_team, selected_team_2, 1);
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

        List<TwoLeggedKnockoutTie> ties = new ArrayList<>();
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
            TwoLeggedKnockoutTie tie = new TwoLeggedKnockoutTie(selected_team, selected_team_2, 1);
            ties.add(tie);
            seeded = removeFromList(seeded, selected_team);
            unseeded = removeFromList(unseeded, selected_team_2);
        }
        stages.put(stage, ties);
    }

    private static void setQ1Stages() {
        setStage(Stage.CL_Q1_CP);
        setStage(Stage.EL_Q1_MP);
        setStage(Stage.ECL_Q1_MP);
    }

    private static void simulateQ1Stages() {
        simulateStage(Stage.CL_Q1_CP);
        simulateStage(Stage.EL_Q1_MP);
        simulateStage(Stage.ECL_Q1_MP);
    }

    private static void setQ2Stages() {
        setStage(Stage.CL_Q2_CP);
        setStage(Stage.CL_Q2_MP);
        setStage(Stage.EL_Q2_MP);
        setStage(Stage.ECL_Q2_MP);
        setStage(Stage.ECL_Q2_CP);
    }

    private static void simulateQ2Stages() {
        simulateStage(Stage.CL_Q2_CP);
        simulateStage(Stage.CL_Q2_MP);
        simulateStage(Stage.EL_Q2_MP);
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
        // setStage(Stage.EL_Q4_CP);
        setStage(Stage.EL_Q4_MP);
        setStage(Stage.ECL_Q4_CP);
        setStage(Stage.ECL_Q4_MP);
    }

    private static void simulateQ4Stages() {
        simulateStage(Stage.CL_Q4_CP);
        simulateStage(Stage.CL_Q4_MP);
        // simulateStage(Stage.EL_Q4_CP);
        simulateStage(Stage.EL_Q4_MP);
        simulateStage(Stage.ECL_Q4_CP);
        simulateStage(Stage.ECL_Q4_MP);
    }

    private static void setGroups() {
        setStage(Stage.CL_GROUP);
        setStage(Stage.EL_GROUP);
        setStage(Stage.ECL_GROUP);
    }

    private static List<String[][]> getMatchups(String[] pot1, String pot2[]) {
        List<String[][]> matchups = new ArrayList<>();
        for (int point = 0; point < pot1.length; point++) {
            String arr1[] = new String[pot1.length];
            for (int i = 0; i < pot1.length; i++)
                arr1[i] = pot1[i];
            String arr2[] = new String[pot2.length];
            for (int i = 0; i < pot2.length; i++)
                arr2[i] = pot2[i];

            int max = (pot1.length) - point;
            for (int i = 0; i < max; i++) {
                String first2 = arr2[0];
                String last1 = arr1[arr1.length - 1];
                for (int j = arr1.length - 1; j >= point + 1; j--) {
                    arr1[j] = arr1[j - 1];
                }
                arr1[point] = first2;
                for (int j = 1; j < arr2.length; j++) {
                    arr2[j - 1] = arr2[j];
                }
                arr2[arr2.length - 1] = last1;
            }
            boolean flag = true;
            String[][] matchday = new String[pot1.length][pot2.length];
            for (int i = 0; i < pot1.length; i++) {
                if (team_nations.get(arr1[i]).equals(team_nations.get(arr2[i]))) {
                    flag = false;
                    break;
                }
                if (contains(arr1[i], pot1) && contains(arr2[i], pot2)) {
                    matchday[i][0] = arr1[i];
                    matchday[i][1] = arr2[i];
                } else if (contains(arr1[i], pot2) && contains(arr2[i], pot1)) {
                    matchday[i][1] = arr1[i];
                    matchday[i][0] = arr2[i];
                }
            }
            if (flag) {
                matchups.add(matchday);
            }
        }
        return matchups;
    }

    private static boolean contains(String str, String[] arr) {
        for (String s : arr) {
            if (str.equals(s))
                return true;
        }
        return false;
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
            gLeagues[i].getTable().showLongContinental();
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

    private static void simulateLeaguePhaseMatchDay(Stage stage, int matchday) {
        System.out.println("\n" + stage.stage_name() + " Matchday " + matchday);
        leagues.get(stage).simulateMatchDay(matchday);
    }

    private static void simulateLeaguePhase() {
        for (int i = 1; i <= 8; i++) {
            simulateLeaguePhaseMatchDay(Stage.CL_GROUP, i);
            simulateLeaguePhaseMatchDay(Stage.EL_GROUP, i);
            if (i <= 6) {
                simulateLeaguePhaseMatchDay(Stage.ECL_GROUP, i);
            }
        }

        System.out.println("\nCHAMPIONS LEAGUE - LEAGUE PHASE");
        leagues.get(Stage.CL_GROUP).getTable().showLongContinental();
        System.out.println("\nEUROPA LEAGUE - LEAGUE PHASE");
        leagues.get(Stage.EL_GROUP).getTable().showLongContinental();
        System.out.println("\nCONFERENCE LEAGUE - LEAGUE PHASE");
        leagues.get(Stage.ECL_GROUP).getTable().showLongContinental();
    }

    private static List<String> getGroupTeamsAtRank(Stage stage, int rank) {
        League[] leagues = groups.get(stage);
        List<String> teams = new ArrayList<>();
        for (int i = 0; i < leagues.length; i++) {
            teams.add(leagues[i].getTeamAtRank(rank));
        }
        return teams;
    }

    private static void setupKnockouts() {
        setupKnockouts(Stage.CL_KO_PO);
        setupKnockouts(Stage.EL_KO_PO);
        setupKnockouts(Stage.ECL_KO_PO);
    }

    private static void setupKnockouts(Stage stage) {
        League league = leagues.get(stage);
        int[][] ranks = new int[12][2];
        for (int i = 0; i < 12; i++) {
            boolean flag = CommonFunctions.getRandomInt(100) > 50;
            ranks[i][0] = flag ? 2 * (i + 1) - 1 : (2 * (i + 1));
            ranks[i][1] = !flag ? 2 * (i + 1) - 1 : (2 * (i + 1));
        }
        String[][] pairs = new String[16][2];
        pairs[0][0] = league.getTeamAtRank(ranks[10][0]);
        pairs[0][1] = league.getTeamAtRank(ranks[5][0]);
        pairs[1][0] = null;
        pairs[1][1] = league.getTeamAtRank(ranks[2][0]);
        pairs[2][0] = league.getTeamAtRank(ranks[9][0]);
        pairs[2][1] = league.getTeamAtRank(ranks[6][0]);
        pairs[3][0] = null;
        pairs[3][1] = league.getTeamAtRank(ranks[1][0]);
        pairs[4][0] = league.getTeamAtRank(ranks[11][0]);
        pairs[4][1] = league.getTeamAtRank(ranks[4][0]);
        pairs[5][0] = null;
        pairs[5][1] = league.getTeamAtRank(ranks[3][0]);
        pairs[6][0] = league.getTeamAtRank(ranks[8][0]);
        pairs[6][1] = league.getTeamAtRank(ranks[7][0]);
        pairs[7][0] = null;
        pairs[7][1] = league.getTeamAtRank(ranks[0][0]);

        pairs[8][0] = league.getTeamAtRank(ranks[10][1]);
        pairs[8][1] = league.getTeamAtRank(ranks[5][1]);
        pairs[9][0] = null;
        pairs[9][1] = league.getTeamAtRank(ranks[2][1]);
        pairs[10][0] = league.getTeamAtRank(ranks[9][1]);
        pairs[10][1] = league.getTeamAtRank(ranks[6][1]);
        pairs[11][0] = null;
        pairs[11][1] = league.getTeamAtRank(ranks[1][1]);
        pairs[12][0] = league.getTeamAtRank(ranks[11][1]);
        pairs[12][1] = league.getTeamAtRank(ranks[4][1]);
        pairs[13][0] = null;
        pairs[13][1] = league.getTeamAtRank(ranks[3][1]);
        pairs[14][0] = league.getTeamAtRank(ranks[8][1]);
        pairs[14][1] = league.getTeamAtRank(ranks[7][1]);
        pairs[15][0] = null;
        pairs[15][1] = league.getTeamAtRank(ranks[0][1]);

    }

    private static Stage getLeaguePhaseForCompetition(CCType.CompType comp) {
        if (comp == CCType.CompType.UCL)
            return Stage.CL_GROUP;
        else if (comp == CCType.CompType.UEL)
            return Stage.EL_GROUP;
        else if (comp == CCType.CompType.UECL)
            return Stage.ECL_GROUP;
        return Stage.DEFAULT;
    }

    private static Stage getPreviousStage(Stage stage) {
        switch (stage) {
            case CL_FINAL:
                return Stage.CL_SF;
            case CL_SF:
                return Stage.CL_QF;
            case CL_QF:
                return Stage.CL_R16;
            case CL_R16:
                return Stage.CL_KO_PO;
            case EL_FINAL:
                return Stage.EL_SEMI_FINAL;
            case EL_SEMI_FINAL:
                return Stage.EL_QUARTER_FINAL;
            case EL_QUARTER_FINAL:
                return Stage.EL_R16;
            case EL_R16:
                return Stage.EL_KO_PO;
            case ECL_FINAL:
                return Stage.ECL_SF;
            case ECL_SF:
                return Stage.ECL_QF;
            case ECL_QF:
                return Stage.ECL_R16;
            case ECL_R16:
                return Stage.ECL_KO_PO;
            default:
                return Stage.DEFAULT;
        }
    }

    private static void setupKnockoutPlayoff(Stage stage) {
        League league = leagues.get(getLeaguePhaseForCompetition(stage.competition));
        int[][] ranks = new int[8][2];
        for (int i = 5; i <= 12; i++) {
            boolean flag = CommonFunctions.getRandomInt(100) > 50;
            ranks[i - 5][0] = flag ? 2 * (i) - 1 : (2 * (i));
            ranks[i - 5][1] = !flag ? 2 * (i) - 1 : (2 * (i));
        }

        List<TwoLeggedKnockoutTie> ties = new ArrayList<>();
        ties.add(new TwoLeggedKnockoutTie(league.getTeamAtRank(ranks[6][0]), league.getTeamAtRank(ranks[1][0]), 1));
        ties.add(new TwoLeggedKnockoutTie(league.getTeamAtRank(ranks[5][0]), league.getTeamAtRank(ranks[2][0]), 1));
        ties.add(new TwoLeggedKnockoutTie(league.getTeamAtRank(ranks[7][0]), league.getTeamAtRank(ranks[0][0]), 1));
        ties.add(new TwoLeggedKnockoutTie(league.getTeamAtRank(ranks[4][0]), league.getTeamAtRank(ranks[3][0]), 1));
        ties.add(new TwoLeggedKnockoutTie(league.getTeamAtRank(ranks[6][1]), league.getTeamAtRank(ranks[1][1]), 1));
        ties.add(new TwoLeggedKnockoutTie(league.getTeamAtRank(ranks[5][1]), league.getTeamAtRank(ranks[2][1]), 1));
        ties.add(new TwoLeggedKnockoutTie(league.getTeamAtRank(ranks[7][1]), league.getTeamAtRank(ranks[0][1]), 1));
        ties.add(new TwoLeggedKnockoutTie(league.getTeamAtRank(ranks[4][1]), league.getTeamAtRank(ranks[3][1]), 1));
        stages.put(stage, ties);
    }

    private static void setupRound16(Stage stage) {
        League league = leagues.get(getLeaguePhaseForCompetition(stage.competition));
        int[][] ranks = new int[4][2];
        for (int i = 1; i <= 4; i++) {
            boolean flag = CommonFunctions.getRandomInt(100) > 50;
            ranks[i - 1][0] = flag ? 2 * (i) - 1 : (2 * (i));
            ranks[i - 1][1] = !flag ? 2 * (i) - 1 : (2 * (i));
        }
        List<TwoLeggedKnockoutTie> prev_ties = stages.get(getPreviousStage(stage));

        List<TwoLeggedKnockoutTie> ties = new ArrayList<>();
        ties.add(new TwoLeggedKnockoutTie(prev_ties.get(0).getWinner(), league.getTeamAtRank(ranks[2][0]), 1));
        ties.add(new TwoLeggedKnockoutTie(prev_ties.get(1).getWinner(), league.getTeamAtRank(ranks[1][0]), 1));
        ties.add(new TwoLeggedKnockoutTie(prev_ties.get(2).getWinner(), league.getTeamAtRank(ranks[3][0]), 1));
        ties.add(new TwoLeggedKnockoutTie(prev_ties.get(3).getWinner(), league.getTeamAtRank(ranks[0][0]), 1));
        ties.add(new TwoLeggedKnockoutTie(prev_ties.get(4).getWinner(), league.getTeamAtRank(ranks[2][1]), 1));
        ties.add(new TwoLeggedKnockoutTie(prev_ties.get(5).getWinner(), league.getTeamAtRank(ranks[1][1]), 1));
        ties.add(new TwoLeggedKnockoutTie(prev_ties.get(6).getWinner(), league.getTeamAtRank(ranks[3][1]), 1));
        ties.add(new TwoLeggedKnockoutTie(prev_ties.get(7).getWinner(), league.getTeamAtRank(ranks[0][1]), 1));
        stages.put(stage, ties);
    }

    private static void setupKnockoutStage(Stage stage) {
        List<TwoLeggedKnockoutTie> prev_ties = stages.get(getPreviousStage(stage));
        List<TwoLeggedKnockoutTie> ties = new ArrayList<>();
        for (int i = 0; i < prev_ties.size(); i += 2) {
            ties.add(new TwoLeggedKnockoutTie(prev_ties.get(i).getWinner(), prev_ties.get(i + 1).getWinner(), 1));
        }
        stages.put(stage, ties);
    }

    private static void setup() {
        stages = new HashMap<>();
        seeding_scores = new HashMap<>();
        seedings = new HashMap<>();
        team_nations = new HashMap<>();
        bye_teams = new ArrayList<>();
        groups = new HashMap<>();
        leagues = new HashMap<>();
        top_dom_champions = new ArrayList<>();
        prev_champions = new HashMap<>();
        setSeedings();
    }

    private static void setKnockouts1() {
        setupKnockoutPlayoff(Stage.CL_KO_PO);
        setupKnockoutPlayoff(Stage.EL_KO_PO);
        setupKnockoutPlayoff(Stage.ECL_KO_PO);
    }

    private static void setKnockouts2() {
        setupRound16(Stage.CL_R16);
        setupRound16(Stage.EL_R16);
        setupRound16(Stage.ECL_R16);
    }

    private static void simulateKnockouts1() {
        simulateStage(Stage.CL_KO_PO);
        simulateStage(Stage.EL_KO_PO);
        simulateStage(Stage.ECL_KO_PO);
    }

    private static void simulateKnockouts2() {
        simulateStage(Stage.CL_R16);
        simulateStage(Stage.EL_R16);
        simulateStage(Stage.ECL_R16);
    }

    private static void setQuarterFinals() {
        setupKnockoutStage(Stage.CL_QF);
        setupKnockoutStage(Stage.EL_QUARTER_FINAL);
        setupKnockoutStage(Stage.ECL_QF);
    }

    private static void simulateQuarterFinals() {
        simulateStage(Stage.CL_QF);
        simulateStage(Stage.EL_QUARTER_FINAL);
        simulateStage(Stage.ECL_QF);
    }

    private static void setSemiFinals() {
        setupKnockoutStage(Stage.CL_SF);
        setupKnockoutStage(Stage.EL_SEMI_FINAL);
        setupKnockoutStage(Stage.ECL_SF);
    }

    private static void simulateSemiFinals() {
        simulateStage(Stage.CL_SF);
        simulateStage(Stage.EL_SEMI_FINAL);
        simulateStage(Stage.ECL_SF);
    }

    private static void setFinal() {
        setupKnockoutStage(Stage.CL_FINAL);
        setupKnockoutStage(Stage.EL_FINAL);
        setupKnockoutStage(Stage.ECL_FINAL);
    }

    private static void simulateFinal() {
        System.out.println("FINALS " + Sim.getSeasonNumber());
        simulateStage(Stage.ECL_FINAL);
        simulateStage(Stage.EL_FINAL);
        simulateStage(Stage.CL_FINAL);
    }

    public static void simulate() {
        System.out.println("==========EUROPEAN SIMULATION==========");
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
        // simulateGroupStage();
        simulateLeaguePhase();
        // setupKnockouts();
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
        List<TwoLeggedKnockoutTie> ties = stages.get(stage);
        for (int i = 0; i < ties.size(); i++) {
            ties.get(i).simulate();
            System.out.print("Match " + (i + 1) + ": ");
            ties.get(i).showContinental();
        }
        stages.put(stage, ties);
    }

    private static String getTeamName(String t) {
        return Sim.getClub(t).getName() + "(" + Sim.getNation(team_nations.get(t)).getCode() + ")";
    }

    private static String getTeamNameRankings(String t) {
        Club_Team team = Sim.getClub(t);
        return team.getName() + "(" + Sim.getNation(team.getNationId()).getCode() + ")";
    }

    // #region Coefficients
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

    private static double getRoundParticipationBonusPoints(Stage stage) {
        if (stage == Stage.CL_R16 || stage == Stage.CL_QF || stage == Stage.CL_SF || stage == Stage.CL_FINAL)
            return 1.5;
        else if (stage == Stage.EL_R16 || stage == Stage.EL_QUARTER_FINAL || stage == Stage.EL_SEMI_FINAL
                || stage == Stage.EL_FINAL)
            return 1.0;
        else if (stage == Stage.ECL_R16 || stage == Stage.ECL_QF || stage == Stage.ECL_SF || stage == Stage.ECL_FINAL)
            return 0.5;
        return 0.0;
    }

    private static void setStageBonusPoints(Stage stage) {
        List<TwoLeggedKnockoutTie> ties = stages.get(stage);
        double bonus = getRoundParticipationBonusPoints(stage);
        for (TwoLeggedKnockoutTie tie : ties) {
            addCoefficientPoints(tie.getTeam1(), bonus, false);
            addCoefficientPoints(tie.getTeam2(), bonus, false);
        }
    }

    private static void setEliminationPoints(Stage stage, double points) {
        List<TwoLeggedKnockoutTie> ties = stages.get(stage);
        for (TwoLeggedKnockoutTie tie : ties) {
            addCoefficientPointsToClub(tie.getLoser(), points);
        }
    }

    private static void updateCoefficients() {
        // apply match points
        for (Map.Entry<Stage, List<TwoLeggedKnockoutTie>> entry : stages.entrySet()) {
            List<TwoLeggedKnockoutTie> ties = entry.getValue();
            Stage stage = entry.getKey();
            for (TwoLeggedKnockoutTie tie : ties) {
                updateCoefficientsForMatch(tie.getFirstLeg(), stage.qualifying());
                updateCoefficientsForMatch(tie.getSecondLeg(), stage.qualifying());
            }
        }
        for (Map.Entry<Stage, League> entry : leagues.entrySet()) {
            League league = entry.getValue();
            Stage stage = entry.getKey();
            List<Match> matches = league.getFixtures();
            for (Match match : matches) {
                updateCoefficientsForMatch(match, stage.qualifying());
            }
        }
        // apply bonus points
        // Champions League
        League cl_gs = leagues.get(Stage.CL_GROUP);
        List<String> teams = cl_gs.getTeams();
        for (String team : teams) {
            addCoefficientPoints(team, 6.0, false);
        }
        for (int i = 1; i <= 24; i++) {
            addCoefficientPoints(cl_gs.getTeamAtRank(i), ((24 - i) + 1) * 0.25, false);
        }
        // Europa League
        League el_gs = leagues.get(Stage.EL_GROUP);
        for (int i = 1; i <= 24; i++) {
            addCoefficientPoints(el_gs.getTeamAtRank(i), ((24 - i) + 1) * 0.25, false);
        }
        // Europa Conference League
        League ecl_gs = leagues.get(Stage.ECL_GROUP);
        for (int i = 1; i <= 8; i++) {
            addCoefficientPoints(ecl_gs.getTeamAtRank(i), (((8 - i) + 1) * 0.25) + 2, false);
        }
        for (int i = 9; i <= 24; i++) {
            addCoefficientPoints(ecl_gs.getTeamAtRank(i), ((24 - i) + 1) * 0.125, false);
        }
        // Stage Bonus Points
        setStageBonusPoints(Stage.EL_R16);
        setStageBonusPoints(Stage.CL_QF);
        setStageBonusPoints(Stage.EL_QUARTER_FINAL);
        setStageBonusPoints(Stage.CL_SF);
        setStageBonusPoints(Stage.EL_SEMI_FINAL);
        setStageBonusPoints(Stage.ECL_SF);
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
        double MIN_POINTS_EL = 3.0;
        List<String> teams_el = el_gs.getTeams();
        for (String team : teams_el) {
            double cp = club_points.get(team);
            if (cp < MIN_POINTS_EL)
                club_points.put(team, MIN_POINTS_EL);
        }

        double MIN_POINTS_ECL = 2.5;
        List<String> teams_ecl = ecl_gs.getTeams();
        for (String team : teams_ecl) {
            double cp = club_points.get(team);
            if (cp < MIN_POINTS_ECL)
                club_points.put(team, MIN_POINTS_ECL);
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
        updateCompetitionHistory();
        distributePrizeMoney();
    }

    private static Map<Integer, Map<Integer, Double>> nation_coefficients = new HashMap<>();
    private static Map<Integer, Map<String, Double>> club_coefficients = new HashMap<>();

    public static void initCoefficientsCalculations() {
        for (int i = Sim.STARTING_SEASON - 5; i <= Sim.STARTING_SEASON - 1; i++) {
            Map<Integer, Double> p_map = new HashMap<>();
            nation_coefficients.put(Integer.valueOf(i), p_map);
        }

        Map<String, Double[]> initial_points = Setups.getCoefficientsSetup();
        List<Nation> nations = Sim.getNationFromContinent("Europe");
        for (Nation nation : nations) {
            String code = nation.getCode();
            Double[] points = initial_points.get(code);
            for (int i = 0; i < points.length; i++) {
                int year = i + Sim.STARTING_SEASON - 5;
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
        Collections.sort(list,
                (Map.Entry<Nation, Double> o1, Map.Entry<Nation, Double> o2) -> (int) (o2.getValue() - o1.getValue()));
        int count = 1;
        HashMap<Integer, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<Nation, Double> aa : list) {
            temp.put(aa.getKey().getId(), count);
            count++;
        }
        return temp;
    }

    public static void showLeagueRankings() {
        Map<Integer, Map<Integer, Integer>> map = new HashMap<>();
        System.out.print(CommonFunctions.getPaddedString("Country", 25));
        for (int year = Sim.STARTING_SEASON; year <= Sim.getSeasonNumber(); year++) {
            Map<Integer, Integer> ymap = getLeagueRankingsForSeason(year);
            map.put(year, ymap);
            System.out.print(CommonFunctions.getPaddedString(String.valueOf(year), 7));
        }
        System.out.println();
        List<Nation> nations = Sim.getNationFromContinent("Europe");
        for (Nation nation : nations) {
            System.out.print(CommonFunctions.getPaddedString(nation.getName(), 25));
            for (int year = Sim.STARTING_SEASON; year <= Sim.getSeasonNumber(); year++) {
                System.out.print(CommonFunctions.getPaddedString(String.valueOf(map.get(year).get(nation.getId())), 7));
            }
            System.out.println();
        }
    }

    public static void getClubRankingsForSeason(int season) {
        System.out.println("\nClub Rankings");
        int start = season - 5, end = season - 1;
        Map<String, Double> points = new HashMap<>();
        for (int year = start; year <= end; year++) {
            if (!club_coefficients.containsKey(year)) {
                continue;
            }
            Map<String, Double> year_map = club_coefficients.get(year);
            for (Map.Entry<String, Double> entry : year_map.entrySet()) {
                String team = entry.getKey();
                double point = points.containsKey(team) ? points.get(team) : 0.0;
                point += entry.getValue();
                points.put(team, point);
            }
        }
        List<Map.Entry<String, Double>> list = new ArrayList<>(points.entrySet());
        if (list.size() < 2)
            return;
        Collections.sort(list,
                (Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) -> (int) (o2.getValue() - o1.getValue()));
        int count = 1;
        for (Map.Entry<String, Double> aa : list) {
            String name = getTeamNameRankings(aa.getKey());
            System.out.println("Rank: " + count + " " + name + " " + aa.getValue());
            count++;
            if (count > 50)
                break;
        }
    }

    private static double getCoefficientForTeam(String team, int season) {
        int start = season - 5, end = season - 1;
        double points = 0;
        for (int year = start; year <= end; year++) {
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
        Collections.sort(teams_to_sort,
                (String a, String b) -> (int) (getCoefficientForTeam(b, season) - getCoefficientForTeam(a, season)));
        return teams_to_sort;
    }

    private static List<Integer> sortedLeaguesFromSeason(int season) {
        return nation_coefficients.get(season).entrySet()
                .stream()
                .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static double getTeamCoefficient(String team) {
        return getCoefficientForTeam(team, Sim.getSeasonNumber());
    }
    // #endregion Coefficients

    // #region CompetitionHistory
    private static Map<CCType.CompType, List<CompetitionHistory>> history = new HashMap<>();

    private static void updateHistory(CCType.CompType type) {
        Stage stageFinal = type == CCType.CompType.UCL ? Stage.CL_FINAL
                : type == CCType.CompType.UEL ? Stage.EL_FINAL : Stage.ECL_FINAL;
        List<TeamHistory> tHistory = new ArrayList<>();
        TwoLeggedKnockoutTie tie = stages.get(stageFinal).get(0);
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
        updateHistory(CCType.CompType.UCL);
        updateHistory(CCType.CompType.UEL);
        updateHistory(CCType.CompType.UECL);
    }

    private static void showHistory(CCType.CompType type) {
        System.out.println(type.title());
        List<CompetitionHistory> cHist = history.get(type);
        for (CompetitionHistory compHistory : cHist) {
            compHistory.show(2, false);
        }
    }

    public static void showCompetitionHistory() {
        showHistory(CCType.CompType.UCL);
        showHistory(CCType.CompType.UEL);
        showHistory(CCType.CompType.UECL);
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

    // #region PrizeMoney
    private static long getPrizeMoneyForStage(Stage stage) {
        double millions = 0.0;
        switch (stage) {
            case CL_FINAL:
                millions = 90;
                break;
            case CL_SF:
                millions = 75;
                break;
            case CL_QF:
                millions = 60;
                break;
            case CL_R16:
                millions = 45;
                break;
            case EL_FINAL:
                millions = 13.5;
                break;
            case EL_SEMI_FINAL:
                millions = 11.25;
                break;
            case EL_QUARTER_FINAL:
                millions = 9;
                break;
            case EL_R16:
                millions = 6;
                break;
            case EL_KO_PO:
                millions = 5;
                break;
            case ECL_FINAL:
                millions = 9;
                break;
            case ECL_SF:
                millions = 7.5;
                break;
            case ECL_QF:
                millions = 6;
                break;
            case ECL_R16:
                millions = 4.5;
                break;
            case ECL_KO_PO:
                millions = 3.5;
                break;
            case ECL_Q4_CP:
            case ECL_Q4_MP:
                millions = 1;
                break;
            case ECL_Q3_CP:
            case ECL_Q3_MP:
                millions = 0.75;
                break;
            case ECL_Q2_CP:
            case ECL_Q2_MP:
                millions = 0.6;
                break;
            case ECL_Q1_MP:
                millions = 0.5;
                break;
            default:
                millions = 0;
        }

        return (long) (millions * 1000 * 1000);
    }

    private static long getWinnerPrizeMoney(CCType.CompType type) {
        switch (type) {
            case UCL:
                return 100 * 1000 * 1000;
            case UEL:
                return 15 * 1000 * 1000;
            case UECL:
                return 11 * 1000 * 1000;
            default:
                return 0;
        }
    }

    private static long getGroupStageParticipationPrize(CCType.CompType type) {
        switch (type) {
            case UCL:
                return 15 * 1000 * 1000;
            case UEL:
                return 5 * 1000 * 1000;
            case UECL:
                return 3 * 1000 * 1000;
            default:
                return 0;
        }
    }

    private static long getGroupStageWinPrize(CCType.CompType type) {
        switch (type) {
            case UCL:
                return 2 * 1000 * 1000;
            case UEL:
                return 600 * 1000;
            case UECL:
                return 200 * 1000;
            default:
                return 0;
        }
    }

    private static long getGroupStageDrawPrize(CCType.CompType type) {
        switch (type) {
            case UCL:
                return 750 * 1000;
            case UEL:
                return 200 * 1000;
            case UECL:
                return 100 * 1000;
            default:
                return 0;
        }
    }

    private static void distributePrizeMoney() {
        for (Map.Entry<Stage, List<TwoLeggedKnockoutTie>> entry : stages.entrySet()) {
            Stage stage = entry.getKey();
            if (stage == Stage.CL_FINAL || stage == Stage.EL_FINAL || stage == Stage.ECL_FINAL) {
                for (TwoLeggedKnockoutTie tie : entry.getValue()) {
                    Sim.getClub(tie.getWinner()).updateBudget(getWinnerPrizeMoney(stage.competition()));
                }
            }
            for (TwoLeggedKnockoutTie tie : entry.getValue()) {
                long amount = getPrizeMoneyForStage(stage);
                if (amount > 0) {
                    Sim.getClub(tie.getLoser()).updateBudget(amount);
                }
            }
        }
        for (Map.Entry<Stage, League[]> entry : groups.entrySet()) {
            CCType.CompType comp = entry.getKey().competition();
            for (League league : entry.getValue()) {
                for (String team : league.getTeams()) {
                    TeamPerformance perf = league.getTable().getTeamPerformance(team);
                    long amount = getGroupStageParticipationPrize(comp)
                            + (getGroupStageWinPrize(comp) * perf.matchesWon())
                            + (getGroupStageDrawPrize(comp) * perf.matchesDrawn());
                    Sim.getClub(team).updateBudget(amount);
                }
            }
        }
    }
    // #endregion PrizeMoney

    public static void showCoefficients() {
        showClubCoefficients();
        showNationsCoefficients();
    }
}
