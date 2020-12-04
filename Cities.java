public class Cities {
    private static String[] englandCities = { "London", "Liverpool", "Manchester", "Birmingham", "Sheffield",
            "Southampton", "Newcastle", "Leeds", "Bradford", "Bristol", "Leicester", "Wakefield", "Coventry",
            "Nottingham", "Sunderland", "Brighton", "Hull", "Plymouth", "Wolverhampton", "Stoke", "Derby", "Salford",
            "Westminster", "Portsmouth", "York", "Oxford", "Cambridge" };
    private static String[] spainCities = { "Madrid", "Barcelona", "Valencia", "Seville", "Bilbao", "Malaga", "Oviedo",
            "Gijon", "Elche", "Las Palmas", "Zaragoza", "Murcia", "Tenerife", "Granada", "Palma", "Vigo", "Cartagena",
            "Cadiz", "San Sebastian", "Coruna", "Valladolid", "Pamplona" };
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
            "Prato", "Taranto", "Modena", "Perugia", "Ravenna", "Livorno", "Cagliari", "FOggia", "Monza", "Trento",
            "Udine", "Pescara", "Calabria", "Bergamo", "Bolzano" };
    private static String[] portugalCities = { "Lisboa", "Porto", "Vila Nova", "Amadora", "Braga", "Coimbra", "Funchal",
            "Almada", "Setubal", "Agualva-Cacem", "Queluz", "Aveiro", "Guimaraes", "Viseu", "Rio Tinto", "Odilevas",
            "Leiria", "Azores" };
    private static String[] belgiumCities = { "Brussels", "Antwerp", "Gent", "Leuven", "Hasselt", "Brugge", "Mons",
            "Liege", "Arlon", "Namur", "Wavre", "Charleroi", "Mechelen", "Genk", "Mouscron", "Kortrijk", "Aalst",
            "La Louviere", "Oostende" };
    private static String[] netherlandsCities = { "Assen", "Emmen", "Lelystad", "Almere", "Leeuwarden", "Arnhem",
            "Nijmegen", "Groningen", "Maastricht", "Eindhoven", "Hertogenbosch", "Haarlem", "Amsterdam", "Zwolle",
            "Enschede", "den Haag", "Rotterdam", "Utrecht", "Middelburg", "Breda", "Apeldoorn", "Tilburg", "Dordrecht",
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

    public static String getCity(String nation_name) {
        if (nation_name.equalsIgnoreCase("England")) {
            return englandCities[Base.getRandomNumber(englandCities.length)];
        } else if (nation_name.equalsIgnoreCase("Spain")) {
            return spainCities[Base.getRandomNumber(spainCities.length)];
        } else if (nation_name.equalsIgnoreCase("Germany")) {
            return germanyCities[Base.getRandomNumber(germanyCities.length)];
        } else if (nation_name.equalsIgnoreCase("France")) {
            return franceCities[Base.getRandomNumber(franceCities.length)];
        } else if (nation_name.equalsIgnoreCase("Italy")) {
            return italyCities[Base.getRandomNumber(italyCities.length)];
        } else if (nation_name.equalsIgnoreCase("Portugal")) {
            return portugalCities[Base.getRandomNumber(portugalCities.length)];
        } else if (nation_name.equalsIgnoreCase("belgium")) {
            return belgiumCities[Base.getRandomNumber(belgiumCities.length)];
        } else if (nation_name.equalsIgnoreCase("netherlands")) {
            return netherlandsCities[Base.getRandomNumber(netherlandsCities.length)];
        } else if (nation_name.equalsIgnoreCase("russia")) {
            return russiaCities[Base.getRandomNumber(russiaCities.length)];
        } else if (nation_name.equalsIgnoreCase("ukraine")) {
            return ukraineCities[Base.getRandomNumber(ukraineCities.length)];
        } else if (nation_name.equalsIgnoreCase("austria")) {
            return austriaCities[Base.getRandomNumber(austriaCities.length)];
        } else if (nation_name.equalsIgnoreCase("scotland")) {
            return scotlandCities[Base.getRandomNumber(scotlandCities.length)];
        } else if (nation_name.equalsIgnoreCase("turkey")) {
            return turkeyCities[Base.getRandomNumber(turkeyCities.length)];
        } else if (nation_name.equalsIgnoreCase("croatia")) {
            return croatiaCities[Base.getRandomNumber(croatiaCities.length)];
        } else if (nation_name.equalsIgnoreCase("denmark")) {
            return denmarkCities[Base.getRandomNumber(denmarkCities.length)];
        }
        return "Eden";
    }
}
