import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller_CC {
    private enum Continent {
        EUROPE("Europe"),
        ASIA("ASIA"),
        AFRICA("AFRICA"),
        S_AMERICA("South America"),
        N_AMERICA("North America"),
        WORLD("World"),
        DEFAULT("Default");

        private String name;

        private Continent(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

    private enum Competition {
        UCL("Champions League", "UCL", 1, Continent.EUROPE),
        UEL("Europa League", "UEL", 2, Continent.EUROPE),
        UECL("Europa Conference League", "UECL", 3, Continent.EUROPE),
        LIB("Copa Libertadores", "LIB", 1, Continent.S_AMERICA),
        SUD("Copa Sudamericana", "SUD", 2, Continent.S_AMERICA),
        ACL("Asian Champions League", "ACL", 1, Continent.ASIA),
        ACC("African Champions Cup", "ACC", 1, Continent.AFRICA),
        NACL("North American Champions League", "NACL", 1, Continent.N_AMERICA),
        DEFAULT("DEFAULT", "DEF", 1, Continent.DEFAULT);

        private String comp_name;
        private String short_name;
        private int level;
        private Continent continent;

        private Competition(String comp_name, String short_name, int level, Continent continent) {
            this.comp_name = comp_name;
            this.short_name = short_name;
            this.level = level;
            this.continent = continent;
        }

        public String comp_name() {
            return this.comp_name;
        }

        public String short_name() {
            return this.short_name;
        }

        public int level() {
            return this.level;
        }

        public Continent continent() {
            return this.continent;
        }
    }

    private enum Stage {
        CL_FINAL("Final", StageType.KNOCKOUT, Competition.UCL, 5, false, false, true),
        CL_SEMI("Semi Final", StageType.TWO_LEGGED_KNOCKOUT, Competition.UCL, 4, false, false, false),
        CL_QUARTER("Quarter Final", StageType.TWO_LEGGED_KNOCKOUT, Competition.UCL, 3, false, false, false),
        CL_R16("Round of 16", StageType.TWO_LEGGED_KNOCKOUT, Competition.UCL, 2, true, false, false),
        CL_GROUP("Group Stage", StageType.GROUPS, Competition.UCL, 1, true, false, false),
        CL_Q4_CP("Q4 Champions Path", StageType.TWO_LEGGED_KNOCKOUT, Competition.UCL, -1, true, true, false),
        CL_Q4_MP("Q4 Main Path", StageType.TWO_LEGGED_KNOCKOUT, Competition.UCL, -1, true, true, false),
        CL_Q3_CP("Q3 Champions Path", StageType.TWO_LEGGED_KNOCKOUT, Competition.UCL, -2, true, true, false),
        CL_Q3_MP("Q3 Main Path", StageType.TWO_LEGGED_KNOCKOUT, Competition.UCL, -2, true, true, false),
        CL_Q2_CP("Q2 Champions Path", StageType.TWO_LEGGED_KNOCKOUT, Competition.UCL, -3, true, true, false),
        CL_Q2_MP("Q2 Main Path", StageType.TWO_LEGGED_KNOCKOUT, Competition.UCL, -3, true, true, false),
        CL_Q1_CP("Q1 Champions Path", StageType.TWO_LEGGED_KNOCKOUT, Competition.UCL, -4, true, true, false),
        DEFAULT("Default", StageType.KNOCKOUT, Competition.DEFAULT, 0, false, false, false);

        private String stage_name;
        private StageType type;
        private Competition competition;
        private int level;
        private boolean is_nation_protected;
        private boolean qualifying;
        private boolean is_final;

        private Stage(String stage_name, StageType type, Competition competition, int level,
                boolean is_nation_protected, boolean qualifying, boolean is_final) {
            this.stage_name = stage_name;
            this.type = type;
            this.competition = competition;
            this.level = level;
            this.is_nation_protected = is_nation_protected;
            this.qualifying = qualifying;
            this.is_final = is_final;
        }

        public String stage_name() {
            return this.stage_name;
        }

        public StageType type() {
            return this.type;
        }

        public Competition competition() {
            return this.competition;
        }

        public int level() {
            return this.level;
        }

        public boolean is_nation_protected() {
            return this.is_nation_protected;
        }

        public boolean qualifying() {
            return this.qualifying;
        }

        public boolean is_final() {
            return this.is_final;
        }
    }

    private enum GroupConfig {
        UCL_GROUP(Stage.CL_GROUP, 8, 4, 2),
        DEFAULT(Stage.DEFAULT, 0, 0, 0);

        private int num_groups;
        private int num_teams;
        private int rounds;
        private Stage stage;

        private GroupConfig(Stage stage, int num_groups, int num_teams, int rounds) {
            this.num_groups = num_groups;
            this.num_teams = num_teams;
            this.rounds = rounds;
            this.stage = stage;
        }

        public Stage stage() {
            return this.stage;
        }

        public int num_groups() {
            return this.num_groups;
        }

        public int num_teams() {
            return this.num_teams;
        }

        public int rounds() {
            return this.rounds;
        }
    }

    private enum Seeding {
        CL_GROUP,
        CL_Q4_CP,
        CL_Q3_CP,
        CL_Q3_MP,
        CL_Q2_CP,
        CL_Q2_MP,
        CL_Q1_CP
    }

    private static Map<Stage, List<KnockoutTie>> stages = new HashMap<>();

    
}
