import java.util.ArrayList;
import java.util.List;

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
    private static String[] englandSuffixes = { "FC", "City", "Town", "United", "Wanderers", "Rovers", "Rangers", "",
            "FC" };

    private static String[] spainPrefixes = { "FC", "Atletico", "Athletic", "Real", "RCD", "SD", "CD", "CA", "RC",
            "Deportivo", "Sporting" };
    private static String[] spainSuffixes = { "FC", "CF", "UD" };

    private static String[] germanyPrefixes = { "VfB", "VfL", "FC", "1. FC", "FSV", "SV", "SpVgg", "VfR", "TSG",
            "Eintracht", "SC" };
    private static String[] germanySuffixes = { "FC", "SV" };

    private static String[] italyPrefixes = { "FC", "SSC", "US", "AC" };
    private static String[] italySuffixes = { "FC", "Calcio", "CFC" };

    private static String[] francePrefixes = { "AS", "Olympique", "Stade", "FC", "SC", "RC", "Racing", "ES" };
    private static String[] franceSuffixes = { "FCO", "FC", "Olympique", "SC", "Foot" };

    private static String[] portugalPrefixes = { "GD", "Sporting", "Sport", "OS", "FC", "CD", "Vitoria de", "UD" };
    private static String[] portugalSuffixes = { "FC", "Clube", "SC", "CP" };

    private static String[] belgiumPrefixes = { "Club", "Cercle", "Standard", "RSC", "KSC", "KVC", "KAA", "KRC", "KVV",
            "Koninklijke" };
    private static String[] belgiumSuffixes = { "FC", "SK", "SC", "VV", "SV" };

    private static String[] russiaPrefixes = { "FC", "PFC", "CSKA", "Dinamo", "Spartak", "Lokomotiv", "Torpedo",
            "SKA" };
    private static String[] russiaSuffixes = { "FC", "Zvezda" };

    private static String[] netherlandsPrefixes = { "FC", "ADO", "SC", "SV", "Sparta", "SBV", "SVV", "VV" };
    private static String[] netherlandsSuffixes = { "SV", "FC", "RC", "JC", "Sport" };

    private static String[] croatiaPrefixes = { "NK", "GNK", "HNK", "Dinamo", "Lokomotiva", "Hrvatski", "Slaven" };
    private static String[] croatiaSuffixes = { "NK" };

    private static String[] denmarkPrefixes = { "AC", "FC", "AB", "HB", "HIK" };
    private static String[] denmarkSuffixes = { "IF", "BK", "IF", "fB" };

    private static String[] ukrainePrefixes = { "FC", "PFC", "Shakhtar", "Dynamo", "Spartak", "Arsenal", "Olimpik",
            "Metalist", "Stal", "MFC" };
    private static String[] ukraineSuffixes = { "FC" };

    private static String[] turkeyPrefixes = { "BK", "BB", "" };
    private static String[] turkeySuffixes = { "Spor", "JK", "SK", "FK" };

    private static String[] austriaPrefixes = { "SV", "TSV", "1.", "FC", "ASK", "SC", "SK", "VfB" };
    private static String[] austriaSuffixes = { "SC", "FC", "SV", "TSV", "SK" };

    public static List<String[]> getPrefixOrSuffixData(String nation_name) {
        List<String[]> data = new ArrayList<>();
        if (nation_name.equalsIgnoreCase(Catalogues.england) || nation_name.equalsIgnoreCase(Catalogues.scotland)) {
            data.add(englandPrefixes);
            data.add(englandSuffixes);
            return data;
        } else if (nation_name.equalsIgnoreCase(Catalogues.spain)) {
            data.add(spainPrefixes);
            data.add(spainSuffixes);
            return data;
        } else if (nation_name.equalsIgnoreCase(Catalogues.germany)) {
            data.add(germanyPrefixes);
            data.add(germanySuffixes);
            return data;
        } else if (nation_name.equalsIgnoreCase(Catalogues.france)) {
            data.add(francePrefixes);
            data.add(franceSuffixes);
            return data;
        } else if (nation_name.equalsIgnoreCase(Catalogues.italy)) {
            data.add(italyPrefixes);
            data.add(italySuffixes);
            return data;
        } else if (nation_name.equalsIgnoreCase(Catalogues.portugal)) {
            data.add(portugalPrefixes);
            data.add(portugalSuffixes);
            return data;
        } else if (nation_name.equalsIgnoreCase(Catalogues.belgium)) {
            data.add(belgiumPrefixes);
            data.add(belgiumSuffixes);
            return data;
        } else if (nation_name.equalsIgnoreCase(Catalogues.russia)) {
            data.add(russiaPrefixes);
            data.add(russiaSuffixes);
            return data;
        } else if (nation_name.equalsIgnoreCase(Catalogues.netherlands)) {
            data.add(netherlandsPrefixes);
            data.add(netherlandsSuffixes);
            return data;
        } else if (nation_name.equalsIgnoreCase(Catalogues.croatia)) {
            data.add(croatiaPrefixes);
            data.add(croatiaSuffixes);
            return data;
        } else if (nation_name.equalsIgnoreCase(Catalogues.denmark)) {
            data.add(denmarkPrefixes);
            data.add(denmarkSuffixes);
            return data;
        } else if (nation_name.equalsIgnoreCase(Catalogues.ukraine)) {
            data.add(ukrainePrefixes);
            data.add(ukraineSuffixes);
            return data;
        } else if (nation_name.equalsIgnoreCase(Catalogues.turkey)) {
            data.add(turkeyPrefixes);
            data.add(turkeySuffixes);
            return data;
        } else if (nation_name.equalsIgnoreCase(Catalogues.austria)) {
            data.add(austriaPrefixes);
            data.add(austriaSuffixes);
            return data;
        } else {
            String[] temp = { "FC" };
            data.add(temp);
            data.add(temp);
            return data;
        }

    }

    public static String getPrefix(String nation_name) {
        String[] data = Catalogues.getPrefixOrSuffixData(nation_name).get(0);
        return data[CommonFunctions.getRandomNumber(data.length)];
    }

    public static String getSuffix(String nation_name) {
        String[] data = Catalogues.getPrefixOrSuffixData(nation_name).get(1);
        return data[CommonFunctions.getRandomNumber(data.length)];
    }

    public static int getPrefixChance(String nation_name) {
        List<String[]> data = Catalogues.getPrefixOrSuffixData(nation_name);
        return (data.get(0).length * 100) / (data.get(0).length + data.get(1).length);
    }

    public static int getSuffixChance(String nation_name) {
        List<String[]> data = Catalogues.getPrefixOrSuffixData(nation_name);
        return (data.get(1).length * 100) / (data.get(0).length + data.get(1).length);
    }

    public static String getDenonym(String nation_name) {
        if (nation_name.equalsIgnoreCase(Catalogues.austria)) {
            return "Austrian";
        } else if (nation_name.equalsIgnoreCase(Catalogues.belgium)) {
            return "Belgian";
        } else if (nation_name.equalsIgnoreCase(Catalogues.croatia)) {
            return "Croatian";
        } else if (nation_name.equalsIgnoreCase(Catalogues.czechia)) {
            return "Czech";
        } else if (nation_name.equalsIgnoreCase(Catalogues.denmark)) {
            return "Danish";
        } else if (nation_name.equalsIgnoreCase(Catalogues.england)) {
            return "English";
        } else if (nation_name.equalsIgnoreCase(Catalogues.france)) {
            return "French";
        } else if (nation_name.equalsIgnoreCase(Catalogues.germany)) {
            return "German";
        } else if (nation_name.equalsIgnoreCase(Catalogues.italy)) {
            return "Italian";
        } else if (nation_name.equalsIgnoreCase(Catalogues.netherlands)) {
            return "Dutch";
        } else if (nation_name.equalsIgnoreCase(Catalogues.portugal)) {
            return "Portuguese";
        } else if (nation_name.equalsIgnoreCase(Catalogues.russia)) {
            return "Russian";
        } else if (nation_name.equalsIgnoreCase(Catalogues.scotland)) {
            return "Scottish";
        } else if (nation_name.equalsIgnoreCase(Catalogues.spain)) {
            return "Spanish";
        } else if (nation_name.equalsIgnoreCase(Catalogues.turkey)) {
            return "Turkish";
        } else if (nation_name.equalsIgnoreCase(Catalogues.ukraine)) {
            return "Ukrainian";
        } else {
            return "Edener";
        }
    }
}
