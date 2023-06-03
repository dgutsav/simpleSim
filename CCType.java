public class CCType {
    public enum Continent {
        EUROPE,
        SOUTH_AMERICA,
        ASIA,
        AFRICA,
        NORTH_AMERICA,
        WORLD,
        DEFAULT
    }

    public enum CompType {
        UCL(1, "Champions League", "CL", Continent.EUROPE),
        UEL(2, "Europa League", "EL", Continent.EUROPE),
        UECL(3, "Europa Conference League", "ECL", Continent.EUROPE),

        LIB(1, "Copa Libertadores", "LIB", Continent.SOUTH_AMERICA),
        SUD(1, "Copa Sudamericana", "SUD", Continent.SOUTH_AMERICA),

        ACL(1, "Asian Champions League", "ACL", Continent.ASIA),

        NACL(1, "North American Champions League", "NACL", Continent.NORTH_AMERICA),

        ACC(1, "African Champions Cup", "ACC", Continent.AFRICA),

        CWC(1, "Club World Championship", "CWC", Continent.WORLD),

        DEFAULT(1, "DEFAULT", "DEF", Continent.DEFAULT);

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
