public class Catalogues {
    // Nations
    private static String england = "England";
    private static String spain = "Spain";
    private static String germany = "Germany";
    private static String france = "France";
    private static String italy = "Italy";
    private static String portugal = "Portugal";
    private static String russia = "Russia";
    private static String ukraine = "Ukraine";
    private static String netherlands = "Netherlands";
    private static String belgium = "Belgium";
    private static String denmark = "Denmark";
    private static String croatia = "Croatia";
    private static String scotland = "Scotland";
    private static String turkey = "Turkey";
    private static String austria = "Austria";
    private static String czechia = "Czechia";

    private static String[] englandCities = { "London", "Liverpool", "Manchester", "Birmingham", "Sheffield",
            "Southampton", "Newcastle", "Leeds", "Bradford", "Bristol", "Leicester", "Wakefield", "Coventry",
            "Nottingham", "Sunderland", "Brighton", "Hull", "Plymouth", "Wolverhampton", "Stoke", "Derby", "Salford",
            "Westminster", "Portsmouth", "York", "Oxford", "Cambridge" };
    private static String[] spainCities = { "Madrid", "Barcelona", "Valencia", "Seville", "Bilbao", "Malaga", "Oviedo",
            "Gijon", "Elche", "Las Palmas", "Zaragoza", "Murcia", "Tenerife", "Granada", "Palma", "Vigo", "Cartagena",
            "Cadiz", "San Sebastian", "Coruna", "Valladolid", "Pamplona", "Santander", "Lleida", "Toledo", "Compostela",
            "Huelva", "Salamanca", "Xerez" };
    private static String[] germanyCities = { "Berlin", "Hamburg", "Munchen", "Koln", "Frankfurt", "Stuttgart",
            "Dusseldorf", "Dortmund", "Essen", "Leipzig", "Bremen", "Dresden", "Hannover", "Nurnberg", "Duisburg",
            "Bochum", "Wuppertal", "Bielefeld", "Bonn", "Munster", "Karlsruhe", "Mannheim", "Augsburg", "Wiesbaden",
            "Gelsenkirchen", "Potsdam", "Rostock", "Mainz", "Saarbrucken", "Halle", "Magdeburg", "Kiel", "Erfurt" };
    private static String[] franceCities = { "Lyon", "Saint-Etienne", "Grenoble", "Dijon", "Rennes", "Brest", "Tours",
            "Ajaccio", "Strasbourg", "Reims", "Metz", "Nancy", "Lille", "Amiens", "Paris", "Saint Denis", "Bordeaux",
            "Limoges", "Le Havre", "Rouen", "Caen", "Toulouse", "Montpellier", "Nimes", "Nantes", "Angers", "Le Mans",
            "Marseille", "Nice", "Toulon", "Villerbaunne" };
    private static String[] italyCities = { "Roma", "Milano", "Napoli", "Genova", "Bari", "Palermo", "Firenze",
            "Torino", "Bologna", "Catania", "Verona", "Venezia", "Messina", "Padova", "Trieste", "Brescia", "Parma",
            "Prato", "Taranto", "Modena", "Perugia", "Ravenna", "Livorno", "Cagliari", "Foggia", "Monza", "Trento",
            "Udine", "Pescara", "Calabria", "Bergamo", "Bolzano" };
    private static String[] portugalCities = { "Lisboa", "Porto", "Vila Nova", "Amadora", "Braga", "Coimbra", "Funchal",
            "Almada", "Setubal", "Agualva-Cacem", "Queluz", "Aveiro", "Guimaraes", "Viseu", "Rio Tinto", "Odilevas",
            "Leiria", "Azores" };
    private static String[] belgiumCities = { "Brussels", "Antwerp", "Gent", "Leuven", "Hasselt", "Brugge", "Mons",
            "Liege", "Arlon", "Namur", "Wavre", "Charleroi", "Mechelen", "Genk", "Mouscron", "Kortrijk", "Aalst",
            "La Louviere", "Oostende" };
    private static String[] netherlandsCities = { "Assen", "Emmen", "Lelystad", "Almere", "Leeuwarden", "Arnhem",
            "Nijmegen", "Groningen", "Maastricht", "Eindhoven", "Hertogenbosch", "Haarlem", "Amsterdam", "Zwolle",
            "Enschede", "Hague", "Rotterdam", "Utrecht", "Middelburg", "Breda", "Apeldoorn", "Tilburg", "Dordrecht",
            "Leiden", "Amersfoort", "Hoofddorp", "Delft" };
    private static String[] russiaCities = { "Moskva", "Sankt-Peterburg", "Novosibirsk", "Ekaterinburg", "Kazan",
            "Nizhniy Novgorod", "Chelyabinsk", "Samara", "Omsk", "Rostov", "Ufa", "Krasnoyarsk", "Voronezh", "Perm",
            "Volgograd", "Krasnodar", "Saratov", "Tyumen", "Izhevsk", "Barnaul", "Irkutsk", "Groznyy", "Vladivostok",
            "Makhachkala", "Orenburg", "Sochi" };
    private static String[] ukraineCities = { "Kyiv", "Kharkiv", "Odesa", "Dnipro", "Donetsk", "Zaporizhzhia", "Lviv",
            "Kryvyi Rih", "Mykolaiv", "Mariupol", "Luhansk", "Sevastopol", "Vinnytsia", "Makiivka", "Simferopol",
            "Kherson", "Poltava", "Chernigiv", "Cherkasy" };
    private static String[] austriaCities = { "Vienna", "Graz", "Linz", "Salzburg", "Innsbruck", "Klagenfurt",
            "Villach", "Wels", "Sankt Polten", "Wiener Neustadt", "Bregenz", "Feldkirch" };
    private static String[] scotlandCities = { "Glasgow", "Edinburgh", "Aberdeen", "Dundee", "Paisley", "Livingston",
            "Hamilton", "Dunfermline", "Ayr", "Kilmarnock" };
    private static String[] turkeyCities = { "Istanbul", "Ankara", "Izmir", "Bursa", "Adana", "Gaziantep", "Konya",
            "Antalya", "Keyseri", "Mersin", "Eskisehir", "Diyarbakir", "Samsun", "Trabzon", "Denizli" };
    private static String[] croatiaCities = { "Zagreb", "Split", "Rijeka", "Osijek", "Zadar", "Velika Gorica",
            "Slavonski Brod", "Pula", "Karlovac", "Sisak", "Varazdin", "Sibenik", "Dubrovnik", "Bjelovar", "Kastela",
            "Samobor", "Vinkovci", "Koprivnica", "Dakovo", "Vukovar", "Cakovec", "Pozega", "Zapresic", "Sinj",
            "Petrinja" };
    private static String[] denmarkCities = { "Kobenhavn", "Aarhus", "Odense", "Aalborg", "Esbjerg", "Randers",
            "Kolding", "Horsens", "Vejle", "Roskilde", "Herning", "Horsholm", "Elsinore", "Silkeborg", "Naestved",
            "Fredericia", "Viborg", "Koge", "Holstebro", "Taastrup" };

    private Catalogues() {
    }

    public static String getCity(String nation_name) {
        if (nation_name.equalsIgnoreCase(Catalogues.england)) {
            return englandCities[CommonFunctions.getRandomNumber(englandCities.length)];
        } else if (nation_name.equalsIgnoreCase(Catalogues.spain)) {
            return spainCities[CommonFunctions.getRandomNumber(spainCities.length)];
        } else if (nation_name.equalsIgnoreCase(Catalogues.germany)) {
            return germanyCities[CommonFunctions.getRandomNumber(germanyCities.length)];
        } else if (nation_name.equalsIgnoreCase(Catalogues.france)) {
            return franceCities[CommonFunctions.getRandomNumber(franceCities.length)];
        } else if (nation_name.equalsIgnoreCase(Catalogues.italy)) {
            return italyCities[CommonFunctions.getRandomNumber(italyCities.length)];
        } else if (nation_name.equalsIgnoreCase(Catalogues.portugal)) {
            return portugalCities[CommonFunctions.getRandomNumber(portugalCities.length)];
        } else if (nation_name.equalsIgnoreCase(Catalogues.belgium)) {
            return belgiumCities[CommonFunctions.getRandomNumber(belgiumCities.length)];
        } else if (nation_name.equalsIgnoreCase(Catalogues.netherlands)) {
            return netherlandsCities[CommonFunctions.getRandomNumber(netherlandsCities.length)];
        } else if (nation_name.equalsIgnoreCase(Catalogues.russia)) {
            return russiaCities[CommonFunctions.getRandomNumber(russiaCities.length)];
        } else if (nation_name.equalsIgnoreCase(Catalogues.ukraine)) {
            return ukraineCities[CommonFunctions.getRandomNumber(ukraineCities.length)];
        } else if (nation_name.equalsIgnoreCase(Catalogues.austria)) {
            return austriaCities[CommonFunctions.getRandomNumber(austriaCities.length)];
        } else if (nation_name.equalsIgnoreCase(Catalogues.scotland)) {
            return scotlandCities[CommonFunctions.getRandomNumber(scotlandCities.length)];
        } else if (nation_name.equalsIgnoreCase(Catalogues.turkey)) {
            return turkeyCities[CommonFunctions.getRandomNumber(turkeyCities.length)];
        } else if (nation_name.equalsIgnoreCase(Catalogues.croatia)) {
            return croatiaCities[CommonFunctions.getRandomNumber(croatiaCities.length)];
        } else if (nation_name.equalsIgnoreCase(Catalogues.denmark)) {
            return denmarkCities[CommonFunctions.getRandomNumber(denmarkCities.length)];
        }
        return "Eden";
    }

    private static String[] englandPrefixes = { "AFC", "FC" };
    private static String[] englandSuffixes = { "FC", "City", "Town", "United", "Wanderers", "Rovers", "Rangers",
            "Park" };

    private static String[] spainPrefixes = { "FC", "Atletico", "Athletic", "Real", "RCD", "SD", "CD", "CA", "RC",
            "Deportivo", "Sporting" };
    private static String[] spainSuffixes = { "FC", "CF", "UD" };

    private static String[] germanyPrefixes = { "VfB", "VfL", "FC", "1.", "FSV", "SV", "SpVgg", "VfR", "TSG",
            "Eintracht", "SC" };
    private static String[] germanySuffixes = { "FC", "SV" };

    private static String[] italyPrefixes = { "FC", "SSC", "US", "AC" };
    private static String[] italySuffixes = { "FC", "Calcio", "CFC" };

    private static String[] francePrefixes = { "AS", "Olympique", "Stade", "FC", "SC", "RC", "Racing", "ES" };
    private static String[] franceSuffixes = { "FCO", "FC", "Olympique", "SC", "Foot" };

    private static String[] portugalPrefixes = { "GD", "Sporting", "Sport", "OS", "FC", "CD", "Vitoria de ", "UD" };
    private static String[] portugalSuffixes = { "FC", "Clube", "SC", "CP" };

    private static String[] belgiumPrefixes = { "Club", "Cercle", "Standard", "RSC", "KSC", "KVC", "KAA", "KRC", "KVV", "Koninklijke" };
    private static String[] belgiumSuffixes = { "FC", "SK", "SC", "VV", "SV" };

    public static String getPrefix(String nation_name) {
        if (nation_name.equalsIgnoreCase(Catalogues.england)) {
            return englandPrefixes[CommonFunctions.getRandomNumber(englandPrefixes.length)];
        } else if (nation_name.equalsIgnoreCase(Catalogues.spain)) {
            return spainPrefixes[CommonFunctions.getRandomNumber(spainPrefixes.length)];
        } else if (nation_name.equalsIgnoreCase(Catalogues.germany)) {
            return germanyPrefixes[CommonFunctions.getRandomNumber(germanyPrefixes.length)];
        } else if (nation_name.equalsIgnoreCase(Catalogues.france)) {
            return francePrefixes[CommonFunctions.getRandomNumber(francePrefixes.length)];
        } else if (nation_name.equalsIgnoreCase(Catalogues.italy)) {
            return italyPrefixes[CommonFunctions.getRandomNumber(italyPrefixes.length)];
        } else if (nation_name.equalsIgnoreCase(Catalogues.portugal)) {
            return portugalPrefixes[CommonFunctions.getRandomNumber(portugalPrefixes.length)];
        } else if (nation_name.equalsIgnoreCase(Catalogues.belgium)) {
            return belgiumPrefixes[CommonFunctions.getRandomNumber(belgiumPrefixes.length)];
        } else {
            return "FC";
        }
    }

    public static String getSuffix(String nation_name) {
        if (nation_name.equalsIgnoreCase(Catalogues.england)) {
            return englandSuffixes[CommonFunctions.getRandomNumber(englandSuffixes.length)];
        } else if (nation_name.equalsIgnoreCase(Catalogues.spain)) {
            return spainSuffixes[CommonFunctions.getRandomNumber(spainSuffixes.length)];
        } else if (nation_name.equalsIgnoreCase(Catalogues.germany)) {
            return germanySuffixes[CommonFunctions.getRandomNumber(germanySuffixes.length)];
        } else if (nation_name.equalsIgnoreCase(Catalogues.france)) {
            return franceSuffixes[CommonFunctions.getRandomNumber(franceSuffixes.length)];
        } else if (nation_name.equalsIgnoreCase(Catalogues.italy)) {
            return italySuffixes[CommonFunctions.getRandomNumber(italySuffixes.length)];
        } else if (nation_name.equalsIgnoreCase(Catalogues.portugal)) {
            return portugalSuffixes[CommonFunctions.getRandomNumber(portugalSuffixes.length)];
        } else if (nation_name.equalsIgnoreCase(Catalogues.belgium)) {
            return belgiumSuffixes[CommonFunctions.getRandomNumber(belgiumSuffixes.length)];
        } else {
            return "FC";
        }
    }

    public static int getPrefixChance(String nation_name) {
        int num = 0, den = 0;
        if (nation_name.equalsIgnoreCase(Catalogues.england)) {
            num = englandPrefixes.length;
            den = englandPrefixes.length + englandSuffixes.length;
        } else if (nation_name.equalsIgnoreCase(Catalogues.spain)) {
            num = spainPrefixes.length;
            den = spainPrefixes.length + spainSuffixes.length;
        } else if (nation_name.equalsIgnoreCase(Catalogues.germany)) {
            num = germanyPrefixes.length;
            den = germanyPrefixes.length + germanySuffixes.length;
        } else if (nation_name.equalsIgnoreCase(Catalogues.france)) {
            num = francePrefixes.length;
            den = francePrefixes.length + franceSuffixes.length;
        } else if (nation_name.equalsIgnoreCase(Catalogues.italy)) {
            num = italyPrefixes.length;
            den = italyPrefixes.length + italySuffixes.length;
        } else if (nation_name.equalsIgnoreCase(Catalogues.portugal)) {
            num = portugalPrefixes.length;
            den = portugalPrefixes.length + portugalSuffixes.length;
        } else if (nation_name.equalsIgnoreCase(Catalogues.belgium)) {
            num = belgiumPrefixes.length;
            den = belgiumPrefixes.length + belgiumSuffixes.length;
        } else {
            num = 1;
            den = 2;
        }
        return (num * 100) / den;
    }

    public static int getSuffixChance(String nation_name) {
        int num = 0, den = 0;
        if (nation_name.equalsIgnoreCase(Catalogues.england)) {
            num = englandSuffixes.length;
            den = englandPrefixes.length + englandSuffixes.length;
        } else if (nation_name.equalsIgnoreCase(Catalogues.spain)) {
            num = spainSuffixes.length;
            den = spainPrefixes.length + spainSuffixes.length;
        } else if (nation_name.equalsIgnoreCase(Catalogues.germany)) {
            num = germanySuffixes.length;
            den = germanyPrefixes.length + germanySuffixes.length;
        } else if (nation_name.equalsIgnoreCase(Catalogues.france)) {
            num = franceSuffixes.length;
            den = francePrefixes.length + franceSuffixes.length;
        } else if (nation_name.equalsIgnoreCase(Catalogues.italy)) {
            num = italySuffixes.length;
            den = italyPrefixes.length + italySuffixes.length;
        } else if (nation_name.equalsIgnoreCase(Catalogues.portugal)) {
            num = portugalSuffixes.length;
            den = portugalPrefixes.length + portugalSuffixes.length;
        } else if (nation_name.equalsIgnoreCase(Catalogues.belgium)) {
            num = belgiumSuffixes.length;
            den = belgiumPrefixes.length + belgiumSuffixes.length;
        } else {
            num = 1;
            den = 2;
        }
        return (num * 100) / den;
    }

    public static String getDenonym(String nation_name) {
        if(nation_name.equalsIgnoreCase(Catalogues.austria)) {
            return "Austrian";
        } else if(nation_name.equalsIgnoreCase(Catalogues.belgium)) {
            return "Belgian";
        } else if(nation_name.equalsIgnoreCase(Catalogues.croatia)) {
            return "Croatian";
        } else if(nation_name.equalsIgnoreCase(Catalogues.czechia)) {
            return "Czech";
        } else if(nation_name.equalsIgnoreCase(Catalogues.denmark)) {
            return "Danish";
        } else if(nation_name.equalsIgnoreCase(Catalogues.england)) {
            return "English";
        } else if(nation_name.equalsIgnoreCase(Catalogues.france)) {
            return "French";
        } else if(nation_name.equalsIgnoreCase(Catalogues.germany)) {
            return "German";
        } else if(nation_name.equalsIgnoreCase(Catalogues.italy)) {
            return "Italian";
        } else if(nation_name.equalsIgnoreCase(Catalogues.netherlands)) {
            return "Dutch";
        } else if(nation_name.equalsIgnoreCase(Catalogues.portugal)) {
            return "Portuguese";
        } else if(nation_name.equalsIgnoreCase(Catalogues.russia)) {
            return "Russian";
        } else if(nation_name.equalsIgnoreCase(Catalogues.scotland)) {
            return "Scottish";
        } else if(nation_name.equalsIgnoreCase(Catalogues.spain)) {
            return "Spanish";
        } else if(nation_name.equalsIgnoreCase(Catalogues.turkey)) {
            return "Turkish";
        } else if(nation_name.equalsIgnoreCase(Catalogues.ukraine)) {
            return "Ukrainian";
        } else {
            return "Edener";
        }
    }
}
