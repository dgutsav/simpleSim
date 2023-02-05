public class CCType {
    public enum Continent {
        EUROPE,
        SOUTH_AMERICA
    }

    public enum CompType {
        CHAMPIONS_LEAGUE(1, "Champions League", "CL", Continent.EUROPE),
        EUROPA_LEAGUE(2, "Europa League", "EL", Continent.EUROPE),
        CONFERENCE_LEAGUE(3, "Europa Conference League", "ECL", Continent.EUROPE);

        private final int level;
        private final String title;
        private final String short_name;
        private final Continent continent;

        CompType(int level, String title, String short_name, Continent continent) {
            this.level = level;
            this.title = title;
            this.short_name = short_name;
            this.continent = continent;
        }

        public int level() {
            return this.level;
        }

        public String title() {
            return this.title;
        }

        public String short_name() {
            return this.short_name;
        }

        public Continent continent() {
            return this.continent;
        }
    }

    public enum StageType {
        KNOCKOUT,
        LEAGUE,
        GROUPS,
        TWO_LEGGED_KNOCKOUT
    }
}
