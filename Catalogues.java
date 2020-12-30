import java.util.ArrayList;
import java.util.Arrays;
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
    private static String switzerland = "Switzerland";
    private static String greece = "Greece";
    private static String serbia = "Serbia";
    private static String cyprus = "Cyprus";
    private static String norway = "Norway";
    private static String hungary = "Hungary";
    private static String israel = "Israel";
    private static String poland = "Poland";
    private static String romania = "Romania";

    private static String belarus = "Belarus";
    private static String slovakia = "Slovakia";
    private static String slovenia = "Slovenia";
    private static String bulgaria = "Bulgaria";
    private static String sweden = "Sweden";
    private static String albania = "Albania";
    private static String finland = "Finland";
    private static String ireland = "Ireland";

    private static String argentina = "Argentina";
    private static String bolivia = "Bolivia";
    private static String brazil = "Brazil";
    private static String chile = "Chile";
    private static String colombia = "Colombia";
    private static String ecuador = "Ecuador";
    private static String paraguay = "Paraguay";
    private static String peru = "Peru";
    private static String uruguay = "Uruguay";
    private static String venezuela = "Venezuela";

    public static String getDenonym(String nation_name) {
        if (nation_name.equalsIgnoreCase(Catalogues.albania)) {
            return "Albanian";
        } else if (nation_name.equalsIgnoreCase(Catalogues.argentina)) {
            return "Argentine";
        } else if (nation_name.equalsIgnoreCase(Catalogues.austria)) {
            return "Austrian";
        } else if (nation_name.equalsIgnoreCase(Catalogues.belarus)) {
            return "Belarussian";
        } else if (nation_name.equalsIgnoreCase(Catalogues.belgium)) {
            return "Belgian";
        } else if (nation_name.equalsIgnoreCase(Catalogues.bolivia)) {
            return "Bolivian";
        } else if (nation_name.equalsIgnoreCase(Catalogues.brazil)) {
            return "Brazilian";
        } else if (nation_name.equalsIgnoreCase(Catalogues.bulgaria)) {
            return "Bulgarian";
        } else if (nation_name.equalsIgnoreCase(Catalogues.chile)) {
            return "Chilean";
        } else if (nation_name.equalsIgnoreCase(Catalogues.colombia)) {
            return "Colombian";
        } else if (nation_name.equalsIgnoreCase(Catalogues.croatia)) {
            return "Croatian";
        } else if (nation_name.equalsIgnoreCase(Catalogues.cyprus)) {
            return "Cypriot";
        } else if (nation_name.equalsIgnoreCase(Catalogues.czechia)) {
            return "Czech";
        } else if (nation_name.equalsIgnoreCase(Catalogues.denmark)) {
            return "Danish";
        } else if (nation_name.equalsIgnoreCase(Catalogues.ecuador)) {
            return "Ecuadorian";
        } else if (nation_name.equalsIgnoreCase(Catalogues.england)) {
            return "English";
        } else if (nation_name.equalsIgnoreCase(Catalogues.finland)) {
            return "Finnish";
        } else if (nation_name.equalsIgnoreCase(Catalogues.france)) {
            return "French";
        } else if (nation_name.equalsIgnoreCase(Catalogues.germany)) {
            return "German";
        } else if (nation_name.equalsIgnoreCase(Catalogues.greece)) {
            return "Greek";
        } else if (nation_name.equalsIgnoreCase(Catalogues.hungary)) {
            return "Hungarian";
        } else if (nation_name.equalsIgnoreCase(Catalogues.ireland)) {
            return "Irish";
        } else if (nation_name.equalsIgnoreCase(Catalogues.israel)) {
            return "Israeli";
        } else if (nation_name.equalsIgnoreCase(Catalogues.italy)) {
            return "Italian";
        } else if (nation_name.equalsIgnoreCase(Catalogues.netherlands)) {
            return "Dutch";
        } else if (nation_name.equalsIgnoreCase(Catalogues.norway)) {
            return "Norwegian";
        } else if (nation_name.equalsIgnoreCase(Catalogues.paraguay)) {
            return "Paraguayan";
        } else if (nation_name.equalsIgnoreCase(Catalogues.peru)) {
            return "Peruvian";
        } else if (nation_name.equalsIgnoreCase(Catalogues.poland)) {
            return "Polish";
        } else if (nation_name.equalsIgnoreCase(Catalogues.portugal)) {
            return "Portuguese";
        } else if (nation_name.equalsIgnoreCase(Catalogues.romania)) {
            return "Romanian";
        } else if (nation_name.equalsIgnoreCase(Catalogues.russia)) {
            return "Russian";
        } else if (nation_name.equalsIgnoreCase(Catalogues.scotland)) {
            return "Scottish";
        } else if (nation_name.equalsIgnoreCase(Catalogues.serbia)) {
            return "Serbian";
        } else if (nation_name.equalsIgnoreCase(Catalogues.slovakia)) {
            return "Slovakian";
        } else if (nation_name.equalsIgnoreCase(Catalogues.slovenia)) {
            return "Slovenian";
        } else if (nation_name.equalsIgnoreCase(Catalogues.spain)) {
            return "Spanish";
        } else if (nation_name.equalsIgnoreCase(Catalogues.sweden)) {
            return "Swedish";
        } else if (nation_name.equalsIgnoreCase(Catalogues.switzerland)) {
            return "Swiss";
        } else if (nation_name.equalsIgnoreCase(Catalogues.turkey)) {
            return "Turkish";
        } else if (nation_name.equalsIgnoreCase(Catalogues.ukraine)) {
            return "Ukrainian";
        } else if (nation_name.equalsIgnoreCase(Catalogues.uruguay)) {
            return "Uruguayan";
        } else if (nation_name.equalsIgnoreCase(Catalogues.venezuela)) {
            return "Venezuelan";
        } else {
            return "Edener";
        }
    }

    // Cities
    private static String[] englandCities = { "London", "Liverpool", "Manchester", "Birmingham", "Sheffield",
            "Southampton", "Newcastle", "Leeds", "Bradford", "Bristol", "Leicester", "Wakefield", "Coventry",
            "Nottingham", "Sunderland", "Brighton", "Hull", "Plymouth", "Wolverhampton", "Stoke", "Derby", "Salford",
            "Westminster", "Portsmouth", "York", "Oxford", "Cambridge", "Cornwall", "Buckingham", "Durham",
            "Bournemouth", "Croydon", "Ealing", "Enfield", "Stockport", "Dorset", "Chesire" };
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
    private static String[] czechiaCities = { "Praha", "Brno", "Ostrava", "Plzen", "Olomouc", "Usti nad Labem",
            "Hradec Kralove", "Ceske Budejovice", "Pardubice", "Havirov", "Zlin", "Kladno", "Most", "Karvina",
            "Frydek-Mistek", "Opava", "Karlovy Vary", "Teplice", "Decin", "Jihlava", "Chomutov", "Prerov",
            "Mlada Boleslav", "Prostejov", "Tachov", "Morava" };
    private static String[] greeceCities = { "Athina", "Thessaloniki", "Patras", "Piraeus", "Larissa", "Iraklis",
            "Peristeri", "Kallithea", "Acharnes", "Kalamaria", "Nikaia", "Glyfada", "Volos", "Ilio", "Ilioupoli",
            "Keratsini", "Evosmos", "Chalandri", "Nea Smyrni", "Marousi", "Agios Dimitrios", "Zografou", "Egaleo",
            "Nea Ionia", "Ioannina", "Palaio Faliro", "Korydallos", "Trikala", "Vyronas", "Agia Paraskevi", "Galatsi",
            "Agrinio", "Chalcis", "Petroupoli", "Serres", "Alexandroupoli", "Lamia" };
    private static String[] switzerlandCities = { "Zurich", "Geneva", "Basel", "Lausanne", "Bern", "Winterthur",
            "Lucerne", "St. Gallen", "Lugano", "Biel/Bienne", "Thun", "Bellinzona", "Koniz", "Fribourg",
            "La Chaux-de-Fonds", "Schaffhausen", "Chur", "Vernier", "Uster", "Sion" };
    private static String[] serbiaCities = { "Belgrade", "Novi Sad", "Nis", "Kragujevac", "Leskovac", "Subotica",
            "Krusevac", "Kraljevo", "Pancevo", "Zrenjanin", "Sabac", "Cacak", "Smederevo", "Novi Pazar", "Valjevo",
            "Sombor", "Vranje", "Sremska Mitrovica", "Loznica", "Uzice", "Pozarevac", "Jagodina", "Stara Pazova",
            "Zajecar", "Kikinda", "Pirot", "Backa Palanka", "Ruma", "Paracin", "Vrsac", "Aleksinac",
            "Smederevska Palanka", "Bor", "Indija", "Arandelovac", "Prokuplje", "Gornji Milanovac", "Bujanovac", "Kula",
            "Trstenik" };
    private static String[] cyprusCities = { "Famagusta", "Nicosia", "Pafos", "Larnaka", "Limassol", "Kyrenia",
            "Strovolos", "Lakatamia", "Kato Polemidhia", "Aglandjia", "Lefkosa", "Gonyeli", "Degirmenlik", "Gazimagusa",
            "Sehitler", "Gecitkale", "Tatlisu", "Akdogan", "Girne", "Lapta", "Alsancak", "Guzelyurt", "Iskele",
            "Lefke" };
    private static String[] norwayCities = { "Oslo", "Bergen", "Stavanger", "Trondheim", "Drammen", "Sarpsborg",
            "Skien", "Kristiansund", "Alesund", "Tonsberg", "Moss", "Haugesund", "Sandefjord", "Arendal", "Bodo",
            "Tromso", "Hamar", "Halden", "Larvik", "Askoy" };
    private static String[] israelCities = { "Jerusalem", "Tel Aviv", "Haifa", "Rishon LeZion", "Petah Tikva", "Ashdod",
            "Netanya", "Beersheba", "Bnei Brak", "Holon", "Ramat Gan", "Rehvovot", "Ashkelon", "Bat Yam",
            "Beit Shemesh", "Kfar Saba", "Herzliya", "Hadera", "Nazareth" };
    private static String[] polandCities = { "Warszawa", "Krakow", "Lodz", "Wroclaw", "Poznan", "Gdansk", "Szczesin",
            "Bydgoszcz", "Lunlin", "Bialystok", "Katowice", "Gdynia", "Czestochowa", "Radom", "Torun", "Sosnowiec",
            "Rzeszow", "Kielce", "Gliwice", "Olsztyn", "Zabrze", "Bytom", "Zielona Gora", "Opole", "Gorzow", "Tarnow",
            "Chorzow", "Tychy" };
    private static String[] hungaryCities = { "Budapest", "Debrecen", "Szeged", "Miskolc", "Pecs", "Gyor",
            "Nyiregyhaza", "Kecskemet", "Szekesfehervar", "Szombathely", "Szolnok", "Erd", "Tatabanya", "Sopron",
            "Kaposvar", "Veszprem", "Bekescsaba", "Zalaegerszeg", "Eger", "Cegled" };
    private static String[] romaniaCities = { "Bucuresti", "Cluj", "Timisoara", "Iasi", "Constanta", "Craiova",
            "Brasov", "Galati", "Ploiesti", "Oradea", "Braila", "Arad", "Sibiu", "Pitesti", "Bacau", "Targu Mures",
            "Baia Mare", "Buzau", "Botosani", "Ramnicu Valcea", "Severin", "Suceava", "Neamt", "Targu Jiu",
            "Targoviste", "Focsani", "Bistrita", "Tulcea", "Restita", "Slatina", "Giurgiu" };
    private static String[] belarusCities = { "Minsk", "Brest", "Gomel", "Grodno", "Mogilev", "Vitebsk", "Baranavichi",
            "Pinsk", "Kobryn", "Mazyr", "Zhlobin", "Lida", "Bobriusk", "Borisov", "Salihorsk", "Orsha", "Polotsk" };

    private static String[] argentinaCities = { "Buenos Aires", "Cordoba", "Rosario", "La Plata", "Mar del Plata",
            "Salta", "Tucuman", "San Juan", "Resistencia", "Santiago del Estero", "Corrientes", "Mendoza", "Posadas",
            "Jujuy", "Bahia Blanca", "Formosa", "Neuquen", "Parana" };
    private static String[] brazilCities = { "Sao Paulo", "Belo Horizonte", "Manaus", "Curitiba", "Rio de Janeiro",
            "Salvador", "Brasilia", "Fortaleza", "Recife", "Porto Alegre", "Belem", "Goiania", "Guarulhos", "Campinas",
            "Sao Luis", "Sao Gonncalo", "Maceio", "Caxias", "Natal", "Teresina", "Ignacu" };
    private static String[] chileCities = { "Santiago", "Puente Alto", "Iquique", "Valparaiso", "Antofagasta",
            "Copiapo", "La Serena", "Arica", "Rancagua", "Talca", "Chilan", "Concepcion", "Temuco", "Valdivia",
            "Puerto Montt", "Coyhaique" };
    private static String[] colombiaCities = { "Bogota", "Medellin", "Cali", "Barranquilla", "Cartagena", "Cucuta",
            "Soledad", "Ibague", "Bucaramanga", "Soacha", "Villavicencio", "Santa Marta", "Valledupar", "Bello",
            "Pereira", "Monteria", "Pasto", "Buenaventura", "Manizales", "Neiva", "Palmira", "Armenia", "Riohacha",
            "Sincelejo", "Popayán", "Itagüí" };
    private static String[] ecuadorCities = { "Guayaquil", "Quito", "Cuenca", "Santo Domingo", "Machala", "Duran",
            "Manta", "Portoviejo", "Ibarra", "Quevedo", "Loja", "Ambato", "Esmeraldas", "Riobamba", "Milagro",
            "Latacunga", "La Libertad", "Babahoyo" };
    private static String[] boliviaCities = { "Santa Cruz", "El Alto", "La Paz", "Cochabamba", "Oruro", "Sucre",
            "Tarija", "Potosí", "Sacaba", "Quillacollo", "Montero", "Trinidad", "Riberalta", "Warnes", "La Guardia",
            "Villazón" };
    private static String[] peruCities = { "Lima", "Callao", "Arequiba", "Trujillo", "Chiclayo", "Huancayo", "Iquitos",
            "Piura", "Cuzco", "Chimbote", "Tacna", "Juliaca", "Ica", "Cajamarca", "Pucallpa", "Sullana", "Ayacucho",
            "Chincha Alta", "Huanuco", "Tarapoto", "Puno" };
    private static String[] paraguayCities = { "Asuncion", "Ciudad del Este", "San Lorenzo", "Luque", "Capiata",
            "Lambare", "Limpio", "Nemby", "Encarnacion", "Villa Elisa", "Caaguazu", "Coronel Oviedo", "Hernandarias",
            "Itaugua", "Concepcion", "Villarrica", "San Antonio", "Pilar", "Caacupe", "Ita" };
    private static String[] uruguayCities = { "Montevideo", "Salto", "Paysandu", "Las Piedras", "Rivera", "Maldonado",
            "Tacuarembo", "Melo", "Mercedes", "Artigas", "Minas", "San Jose", "Durazno", "Florida", "Barros Blancos",
            "San Carlos", "Colonia Sacramento", "Pando" };
    private static String[] venezuelaCities = { "Caracas", "Maracaibo", "Barquisimeto", "Valencia", "Ciudad Guayana",
            "Maturín", "Barcelona", "Cumaná", "Petare", "Turmero", "Mérida", "Ciudad Bolívar", "Barinas", "Los Teques",
            "Punto Fijo", "La Guaira", "El Vigía", "San Cristóbal" };

    private Catalogues() {
    }

    public static String getCity(String nation_name) {
        if (nation_name.equalsIgnoreCase(Catalogues.england)) {
            return CommonFunctions.getRandomElement(englandCities);
        } else if (nation_name.equalsIgnoreCase(Catalogues.spain)) {
            return CommonFunctions.getRandomElement(spainCities);
        } else if (nation_name.equalsIgnoreCase(Catalogues.germany)) {
            return CommonFunctions.getRandomElement(germanyCities);
        } else if (nation_name.equalsIgnoreCase(Catalogues.france)) {
            return CommonFunctions.getRandomElement(franceCities);
        } else if (nation_name.equalsIgnoreCase(Catalogues.italy)) {
            return CommonFunctions.getRandomElement(italyCities);
        } else if (nation_name.equalsIgnoreCase(Catalogues.portugal)) {
            return CommonFunctions.getRandomElement(portugalCities);
        } else if (nation_name.equalsIgnoreCase(Catalogues.belgium)) {
            return CommonFunctions.getRandomElement(belgiumCities);
        } else if (nation_name.equalsIgnoreCase(Catalogues.netherlands)) {
            return CommonFunctions.getRandomElement(netherlandsCities);
        } else if (nation_name.equalsIgnoreCase(Catalogues.russia)) {
            return CommonFunctions.getRandomElement(russiaCities);
        } else if (nation_name.equalsIgnoreCase(Catalogues.ukraine)) {
            return CommonFunctions.getRandomElement(ukraineCities);
        } else if (nation_name.equalsIgnoreCase(Catalogues.austria)) {
            return CommonFunctions.getRandomElement(austriaCities);
        } else if (nation_name.equalsIgnoreCase(Catalogues.scotland)) {
            return CommonFunctions.getRandomElement(scotlandCities);
        } else if (nation_name.equalsIgnoreCase(Catalogues.turkey)) {
            return CommonFunctions.getRandomElement(turkeyCities);
        } else if (nation_name.equalsIgnoreCase(Catalogues.croatia)) {
            return CommonFunctions.getRandomElement(croatiaCities);
        } else if (nation_name.equalsIgnoreCase(Catalogues.denmark)) {
            return CommonFunctions.getRandomElement(denmarkCities);
        } else if (nation_name.equalsIgnoreCase(Catalogues.czechia)) {
            return CommonFunctions.getRandomElement(czechiaCities);
        } else if (nation_name.equalsIgnoreCase(Catalogues.greece)) {
            return CommonFunctions.getRandomElement(greeceCities);
        } else if (nation_name.equalsIgnoreCase(Catalogues.switzerland)) {
            return CommonFunctions.getRandomElement(switzerlandCities);
        } else if (nation_name.equalsIgnoreCase(Catalogues.cyprus)) {
            return CommonFunctions.getRandomElement(cyprusCities);
        } else if (nation_name.equalsIgnoreCase(Catalogues.serbia)) {
            return CommonFunctions.getRandomElement(serbiaCities);
        } else if (nation_name.equalsIgnoreCase(Catalogues.norway)) {
            return CommonFunctions.getRandomElement(norwayCities);
        } else if (nation_name.equalsIgnoreCase(Catalogues.romania)) {
            return CommonFunctions.getRandomElement(romaniaCities);
        } else if (nation_name.equalsIgnoreCase(Catalogues.israel)) {
            return CommonFunctions.getRandomElement(israelCities);
        } else if (nation_name.equalsIgnoreCase(Catalogues.poland)) {
            return CommonFunctions.getRandomElement(polandCities);
        } else if (nation_name.equalsIgnoreCase(Catalogues.hungary)) {
            return CommonFunctions.getRandomElement(hungaryCities);
        } else if (nation_name.equalsIgnoreCase(Catalogues.argentina)) {
            return CommonFunctions.getRandomElement(argentinaCities);
        } else if (nation_name.equalsIgnoreCase(Catalogues.brazil)) {
            return CommonFunctions.getRandomElement(brazilCities);
        } else if (nation_name.equalsIgnoreCase(Catalogues.chile)) {
            return CommonFunctions.getRandomElement(chileCities);
        } else if (nation_name.equalsIgnoreCase(Catalogues.colombia)) {
            return CommonFunctions.getRandomElement(colombiaCities);
        } else if (nation_name.equalsIgnoreCase(Catalogues.venezuela)) {
            return CommonFunctions.getRandomElement(venezuelaCities);
        } else if (nation_name.equalsIgnoreCase(Catalogues.bolivia)) {
            return CommonFunctions.getRandomElement(boliviaCities);
        } else if (nation_name.equalsIgnoreCase(Catalogues.paraguay)) {
            return CommonFunctions.getRandomElement(paraguayCities);
        } else if (nation_name.equalsIgnoreCase(Catalogues.uruguay)) {
            return CommonFunctions.getRandomElement(uruguayCities);
        } else if (nation_name.equalsIgnoreCase(Catalogues.ecuador)) {
            return CommonFunctions.getRandomElement(ecuadorCities);
        } else if (nation_name.equalsIgnoreCase(Catalogues.peru)) {
            return CommonFunctions.getRandomElement(peruCities);
        }
        return "Eden";
    }

    // Prefixes and Suffixes
    private static String[] englandPrefixes = { "AFC", "FC" };
    private static String[] englandSuffixes = { "FC", "City", "Town", "United", "Wanderers", "Rovers", "Rangers", "",
            "FC", "County" };

    private static String[] spainPrefixes = { "FC", "Atletico", "Athletic", "Real", "RCD", "SD", "CD", "CA", "RC",
            "Deportivo", "Sporting" };
    private static String[] spainSuffixes = { "FC", "CF", "UD" };

    private static String[] germanyPrefixes = { "VfB", "VfL", "FC", "1. FC", "FSV", "SV", "SpVgg", "VfR", "TSG",
            "Eintracht", "SC" };
    private static String[] germanySuffixes = { "FC", "SV" };

    private static String[] italyPrefixes = { "FC", "SSC", "US", "AC", "AS" };
    private static String[] italySuffixes = { "FC", "Calcio", "CFC" };

    private static String[] francePrefixes = { "AS", "Olympique", "Stade", "FC", "SC", "RC", "Racing", "ES" };
    private static String[] franceSuffixes = { "FCO", "FC", "Olympique", "SC", "Foot" };

    private static String[] portugalPrefixes = { "GD", "Sporting", "Sport", "OS", "FC", "CD", "Vitoria de", "UD" };
    private static String[] portugalSuffixes = { "FC", "Clube", "SC", "CP" };

    private static String[] belgiumPrefixes = { "Club", "Cercle", "Standard", "RSC", "KSC", "KVC", "KAA", "KRC", "KVV",
            "Koninklijke" };
    private static String[] belgiumSuffixes = { "FC", "SK", "SC", "VV", "SV" };

    private static String[] russiaPrefixes = { "FC", "PFC", "CSKA", "Dinamo", "Spartak", "Lokomotiv", "Torpedo", "SKA",
            "Zenit" };
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

    private static String[] czechiaPrefixes = { "AC", "FK", "SK", "MFK", "1. FC", "FC", "1. FK", "Sparta", "Dynamo",
            "SFC", "Slovan", "Slavia", "Viktoria", "1. SK" };
    private static String[] czechiaSuffixes = { "FC" };

    private static String[] switzerlandPrefixes = { "FC", "SC", "Grasshopper", "US", "Team", "Sport", "Stade" };
    private static String[] greecePrefixes = { "AEK", "AE", "Asteras", "Apollon", "FC", "OFI" };

    private static String[] serbiaPrefixes = { "OFK", "FK", "Sloboda", "Radnicki", "Spartak", "Metalac", "Borac",
            "Mladost" };
    private static String[] serbiaSuffixes = { "FK" };

    private static String[] norwayPrefixes = { "FK", "SK", "IK", "IF", "IL" };
    private static String[] norwaySuffixes = { "FK", "BK", "Fotball", "FF", "TF" };

    private static String[] israelPrefixes = { "FC", "Tzafirim", "Maccabi", "Hapoel", "Bnei", "Beitar", "Ironi" };
    private static String[] israelSuffixes = { "FC" };

    private static String[] romaniaPrefixes = { "FC", "Dinamo", "Rapid", "Steaua", "Universitea", "Petrolul", "Astra",
            "Unirea", "Viitorul", "Club", "CS", "Politechnica", "UTA" };
    private static String[] romaniaSuffixes = { "FC" };

    private static String[] hungaryPrefixes = { "FC", "TC", "MTK", "SC" };
    private static String[] hungarySuffixes = { "FC", "Honved", "ETO", "TC", "SC" };

    private static String[] polandPrefixes = { "FC", "Lech", "Legia", "Stal", "Slask", "Wisla", "Lechia" };
    private static String[] polandSuffixes = { "KS", "KKS", "SA", "KSSA" };

    private static String[] brazilPrefixes = { "AC", "SC", "FC", "Atletico", "Sport", "Nacional" };
    private static String[] brazilSuffixes = { "FC", "Clube" };

    private static String[] latinPrefixes = { "FC", "Atletico", "Athletic", "RCD", "SD", "CD", "CA", "RC", "Deportivo",
            "Sporting", "Union" };
    private static String[] latinSuffixes = { "FC", "CF", "UD", "Juniors" };

    public static List<String[]> getPrefixOrSuffixData(String nation_name) {
        String[] latin_countries = { argentina, bolivia, chile, colombia, ecuador, peru, paraguay, uruguay, venezuela };
        List<String[]> data = new ArrayList<>();
        if (nation_name.equalsIgnoreCase(Catalogues.england) || nation_name.equalsIgnoreCase(Catalogues.scotland)) {
            data.add(englandPrefixes);
            data.add(englandSuffixes);
        } else if (nation_name.equalsIgnoreCase(Catalogues.spain)) {
            data.add(spainPrefixes);
            data.add(spainSuffixes);
        } else if (nation_name.equalsIgnoreCase(Catalogues.germany)) {
            data.add(germanyPrefixes);
            data.add(germanySuffixes);
        } else if (nation_name.equalsIgnoreCase(Catalogues.france)) {
            data.add(francePrefixes);
            data.add(franceSuffixes);
        } else if (nation_name.equalsIgnoreCase(Catalogues.italy)) {
            data.add(italyPrefixes);
            data.add(italySuffixes);
        } else if (nation_name.equalsIgnoreCase(Catalogues.portugal)) {
            data.add(portugalPrefixes);
            data.add(portugalSuffixes);
        } else if (nation_name.equalsIgnoreCase(Catalogues.belgium)) {
            data.add(belgiumPrefixes);
            data.add(belgiumSuffixes);
        } else if (nation_name.equalsIgnoreCase(Catalogues.russia)) {
            data.add(russiaPrefixes);
            data.add(russiaSuffixes);
        } else if (nation_name.equalsIgnoreCase(Catalogues.netherlands)) {
            data.add(netherlandsPrefixes);
            data.add(netherlandsSuffixes);
        } else if (nation_name.equalsIgnoreCase(Catalogues.croatia)) {
            data.add(croatiaPrefixes);
            data.add(croatiaSuffixes);
        } else if (nation_name.equalsIgnoreCase(Catalogues.denmark)) {
            data.add(denmarkPrefixes);
            data.add(denmarkSuffixes);
        } else if (nation_name.equalsIgnoreCase(Catalogues.ukraine)) {
            data.add(ukrainePrefixes);
            data.add(ukraineSuffixes);
        } else if (nation_name.equalsIgnoreCase(Catalogues.turkey)) {
            data.add(turkeyPrefixes);
            data.add(turkeySuffixes);
        } else if (nation_name.equalsIgnoreCase(Catalogues.austria)) {
            data.add(austriaPrefixes);
            data.add(austriaSuffixes);
        } else if (nation_name.equalsIgnoreCase(Catalogues.czechia)) {
            data.add(czechiaPrefixes);
            data.add(czechiaSuffixes);
        } else if (nation_name.equalsIgnoreCase(Catalogues.switzerland)) {
            data.add(switzerlandPrefixes);
            data.add(ukraineSuffixes);
        } else if (nation_name.equalsIgnoreCase(Catalogues.greece) || nation_name.equalsIgnoreCase(Catalogues.cyprus)) {
            data.add(greecePrefixes);
            data.add(czechiaSuffixes);
        } else if (nation_name.equalsIgnoreCase(Catalogues.serbia)) {
            data.add(serbiaPrefixes);
            data.add(serbiaSuffixes);
        } else if (nation_name.equalsIgnoreCase(Catalogues.norway)) {
            data.add(norwayPrefixes);
            data.add(norwaySuffixes);
        } else if (nation_name.equalsIgnoreCase(Catalogues.israel)) {
            data.add(israelPrefixes);
            data.add(israelSuffixes);
        } else if (nation_name.equalsIgnoreCase(Catalogues.romania)) {
            data.add(romaniaPrefixes);
            data.add(romaniaSuffixes);
        } else if (nation_name.equalsIgnoreCase(Catalogues.poland)) {
            data.add(polandPrefixes);
            data.add(polandSuffixes);
        } else if (nation_name.equalsIgnoreCase(Catalogues.hungary)) {
            data.add(hungaryPrefixes);
            data.add(hungarySuffixes);
        } else if (nation_name.equalsIgnoreCase(Catalogues.brazil)) {
            data.add(brazilPrefixes);
            data.add(brazilSuffixes);
        } else if (Arrays.asList(latin_countries).contains(nation_name)) {
            data.add(latinPrefixes);
            data.add(latinSuffixes);
        } else {
            String[] temp = { "FC" };
            data.add(temp);
            data.add(temp);
        }
        return data;
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

    // Formations
    private static Position[][] formations = {
            { Position.GOALKEEPER, Position.RIGHT_BACK, Position.CENTRE_BACK, Position.CENTRE_BACK, Position.LEFT_BACK,
                    Position.CENTRAL_MIDFIELDER, Position.CENTRAL_MIDFIELDER, Position.RIGHT_MIDFIELDER,
                    Position.LEFT_MIDFIELDER, Position.STRIKER, Position.STRIKER },
            { Position.GOALKEEPER, Position.RIGHT_BACK, Position.CENTRE_BACK, Position.CENTRE_BACK, Position.LEFT_BACK,
                    Position.DEFENSIVE_MIDFIELDER, Position.CENTRAL_MIDFIELDER, Position.CENTRAL_MIDFIELDER,
                    Position.RIGHT_WINGER, Position.STRIKER, Position.LEFT_WINGER },
            { Position.GOALKEEPER, Position.RIGHT_BACK, Position.CENTRE_BACK, Position.CENTRE_BACK, Position.LEFT_BACK,
                    Position.DEFENSIVE_MIDFIELDER, Position.DEFENSIVE_MIDFIELDER, Position.RIGHT_MIDFIELDER,
                    Position.LEFT_MIDFIELDER, Position.ATTACKING_MIDFIELDER, Position.STRIKER },
            { Position.GOALKEEPER, Position.CENTRE_BACK, Position.CENTRE_BACK, Position.CENTRE_BACK,
                    Position.RIGHT_MIDFIELDER, Position.CENTRAL_MIDFIELDER, Position.CENTRAL_MIDFIELDER,
                    Position.LEFT_MIDFIELDER, Position.RIGHT_WINGER, Position.STRIKER, Position.LEFT_WINGER },
            { Position.GOALKEEPER, Position.RIGHT_BACK, Position.CENTRE_BACK, Position.CENTRE_BACK, Position.LEFT_BACK,
                    Position.DEFENSIVE_MIDFIELDER, Position.DEFENSIVE_MIDFIELDER, Position.ATTACKING_MIDFIELDER,
                    Position.ATTACKING_MIDFIELDER, Position.ATTACKING_MIDFIELDER, Position.STRIKER },
            { Position.GOALKEEPER, Position.RIGHT_BACK, Position.CENTRE_BACK, Position.CENTRE_BACK,
                    Position.CENTRE_BACK, Position.LEFT_BACK, Position.CENTRAL_MIDFIELDER, Position.CENTRAL_MIDFIELDER,
                    Position.RIGHT_WINGER, Position.STRIKER, Position.LEFT_WINGER },
            { Position.GOALKEEPER, Position.CENTRE_BACK, Position.CENTRE_BACK, Position.CENTRE_BACK,
                    Position.RIGHT_MIDFIELDER, Position.CENTRAL_MIDFIELDER, Position.CENTRAL_MIDFIELDER,
                    Position.CENTRAL_MIDFIELDER, Position.LEFT_MIDFIELDER, Position.STRIKER, Position.STRIKER },
            { Position.GOALKEEPER, Position.CENTRE_BACK, Position.CENTRE_BACK, Position.RIGHT_BACK, Position.LEFT_BACK,
                    Position.DEFENSIVE_MIDFIELDER, Position.RIGHT_MIDFIELDER, Position.CENTRAL_MIDFIELDER,
                    Position.CENTRAL_MIDFIELDER, Position.LEFT_MIDFIELDER, Position.STRIKER } };

    private static String[] formationNames = { "4-4-2", "4-3-3", "4-2-3-1 Wide", "3-4-3", "4-2-3-1", "5-2-3", "3-5-2",
            "4-1-4-1" };

    public static Position[] getFormation(int formation_id) {
        return formations[formation_id];
    }

    public static String getFormationName(int formation_id) {
        return formationNames[formation_id];
    }

    public static int numFormations() {
        return Catalogues.formationNames.length;
    }

    // Names
    private static String[] englishNames = { "Adam", "Andrew", "Alexander", "Matthew", "Sam", "Robin", "Arthur",
            "Douglas", "Michael", "Gareth", "Alfred", "Simon", "Henry", "Robert", "Jack", "Jacob", "Jake", "George",
            "Lewis", "Peter", "Lando", "Kevin", "Luke", "Oliver", "Harry", "Leo", "Lionel", "Oscar", "Jim", "Jeremy",
            "Boris", "Denis", "John", "Fred", "Kieran", "Mark", "Christopher", "William", "Will", "James", "Steven",
            "Frank", "David", "Bradley", "Marcus", "Jordan", "Jamie", "Craig", "Sean", "Brendan", "Brandon" };
    private static String[] englishSurnames = { "Charlton", "Kane", "Winks", "Trippier", "Armstrong", "Russell",
            "Hamilton", "Wood", "Adams", "Jones", "Sharp", "Bale", "Hart", "Stokes", "Strauss", "Paul", "Gerrard",
            "Lampard", "Hutchinson", "Sterling", "Morgan", "Derbyshire", "London", "Palmer", "Cook", "Lucas", "Beckham",
            "Anderson", "Ferdinand", "Neville", "Southall", "Southgate", "Hodgson", "Shaw", "Collins", "Maddison",
            "Henderson", "Maguire", "Burley", "Dyche", "Rodgers", "Williams" };

    private static String[] germanNames = { "Toni", "Marco", "Jurgen", "Timo", "Marcel", "Manuel", "Bastian", "Kai",
            "Klaus", "Steffen", "Karl", "Heinz", "Oliver", "Stefan", "Maximillian", "Leon", "Serge", "Leroy", "Thomas",
            "Jonas", "Lukas", "Michael", "Fritz", "Uwe", "Hans", "Nico", "Christian", "Moritz", "Patrick", "Florian",
            "Daniel", "Dennis", "Phillip", "Helmut", "Andreas", "Julian", "Mario", "Tobias", "Felix", "Mats", "Marius",
            "Andre", "Marvin", "Peter", "Wolfgang", "Gerhard", "Gunter", "Dieter", "Horst", "Josef", "Frank", "Bernd",
            "Franz", "Heinrich", "Rolf", "Ralf", "Joachim", "Rainer", "Siegfried", "Norbert", "Wilhelm", "Jorg", "Sven",
            "Lars", "Ulrich", "Willi", "Erich", "Paul", "Otto", "Erwin", "Johannes", "Lothar", "Ernst", "Gerd",
            "Volker", "Jens", "Holger", "Dietmar", "Axel", "Torsten" };
    private static String[] germanSurnames = { "Muller", "Schmidt", "Schneider", "Fischer", "Weber", "Meyer", "Wagner",
            "Becker", "Schulz", "Hoffmann", "Schafer", "Koch", "Bauer", "Richter", "Klein", "Wolf", "Schroder",
            "Neumann", "Schwarz", "Zimmermann", "Braun", "Kruger", "Hofmann", "Hartmann", "Lange", "Schmitt", "Werner",
            "Schmitz", "Krause", "Meier", "Lehmann", "Schmid", "Schulze", "Maier", "Kohler", "Herrmann", "Konig",
            "Walter", "Mayer", "Huber", "Kaiser", "Fuchs", "Peters", "Lang", "Scholz", "Moller", "Weiss", "Jung",
            "Hahn", "Schubert", "Vogel", "Friedrich", "Keller", "Gunther", "Frank", "Berger", "Winkler", "Roth", "Beck",
            "Lorenz", "Baumann", "Franke", "Albrecht", "Schuster", "Simon", "Ludwig", "Bohm", "Winter", "Kraus",
            "Martin", "Schumacher", "Kramer", "Vogt", "Stein", "Jager", "Otto", "Sommer", "Gross", "Seidel", "Heinrich",
            "Brandt", "Haas", "Schreiber", "Graf", "Schulte", "Dietrich", "Ziegler", "Kuhn", "Kahn", "Pohl", "Engel",
            "Horn", "Busch", "Bergmann", "Thomas", "Voigt", "Sauer", "Arnold", "Wolff", "Pfeiffer" };

    private static String[] spanishNames = { "Diego", "Leo", "Sergio", "Jaime", "Carlos", "Andres", "Iker", "Victor",
            "Gonzalo", "Javier", "Luis", "Fernando", "Alejandro", "Mateo", "Antonio", "Jorge", "Emilio", "Jose",
            "Enrique", "Leonanrdo", "Juan", "Emiliano", "Manuel", "Lorenzo", "Miguel", "Maximiliano", "Matias",
            "Cristian", "Santiago", "Francisco", "Rafael", "Raul", "Pedro", "Marco", "Ricardo", "Armando", "Cesar",
            "Ezequiel", "Sergi", "Roberto", "Rodrigo", "Pablo", "Mario", "Esteban", "Joaquin", "Alberto", "Aitor",
            "Joselu", "Ruben", "Ivan", "Ramon", "Roman", "Eduardo", "Santi", "Marcos", "Alfonso", "Julio", "Roque",
            "Marc", "Ignacio", "Mikel", "Carles", "Gerard", "Arturo", "Hector", "Angel", "Facundo", "Sebastian",
            "Federico", "Fede", "Enrico", "Dario", "Alvaro", "Xavi", "Xabi", "Julen" };
    private static String[] spanishSurnames = { "Cuenca", "de la Fuente", "Mesa", "Hurtado", "Trujillo", "Santamaria",
            "Iniesta", "Izquierdo", "Camacho", "Merino", "Pardo", "Llorente", "Luque", "Ruidiaz", "Roca", "Salazar",
            "Munoz", "Puyol", "Manzano", "Bentancur", "Rocha", "de Los Santos", "de Leon", "Silvera", "Machado",
            "Serrano", "Giraldo", "Quintero", "Sanz", "Fuentes", "Espinoza", "Sepulveda", "Rubio", "Villa", "Delgano",
            "Moralez", "Marin", "Iglesias", "Sainz", "Gonzalez", "Rodriguez", "Gomez", "Fernandez", "Lopez", "Diaz",
            "Martinez", "Perez", "Garcia", "Sanchez", "Romero", "Sosa", "Torres", "Alvarez", "Ruiz", "Ramirez",
            "Flores", "Benitez", "Acosta", "Medina", "Herrera", "Suarez", "Aguirre", "Gimenez", "Gutierrez", "Pereyra",
            "Rojas", "Molina", "Castro", "Ortiz", "Silva", "Nuñez", "Luna", "Juarez", "Cabrera", "Rios", "Morales",
            "Godoy", "Moreno", "Ferreyra", "Dominguez", "Carrizo", "Peralta", "Castillo", "Ledesma", "Quiroga", "Vega",
            "Vera", "Muñoz", "Ojeda", "Ponce", "Villalba", "Cardozo", "Navarro", "Coronel", "Vazquez", "Ramos",
            "Vargas", "Caceres", "Arias", "Figueroa", "Cordoba", "Correa", "Maldonado", "Paz", "Rivero", "Miranda",
            "Mansilla", "Farias", "Roldan", "Mendez", "Guzman", "Aguero", "Hernandez", "Lucero", "Cruz", "Paez",
            "Escobar", "Mendoza", "Barrios", "Bustos", "Avila", "Ayala", "Blanco", "Soria", "Maidana", "Acuña", "Leiva",
            "Duarte", "Moyano", "Campos", "Soto", "Martin", "Valdez", "Bravo", "Chavez", "Velazquez", "Olivera",
            "Toledo", "Franco", "Ibañez", "Leguizamon", "Montenegro", "Delgado", "Arce", "Ibarra", "Gallardo",
            "Santillan", "Acevedo", "Aguilar", "Vallejos", "Contreras", "Alegre", "Galvan", "Oviedo", "Aranda",
            "Albornoz", "Baez", "Sandoval", "Barrionuevo", "Veron", "Gauna", "Zarate", "Heredia", "Mercado", "Monzon",
            "Marquez", "Zalazar", "Mamani", "Coria", "Segovia", "Romano", "Jimenez", "Salinas", "Quinteros", "Barrera",
            "Ortega", "Cabral", "Palacios", "Cejas", "Quintana", "Zapata", "Rosales", "Altamirano", "Nieva", "Bazan",
            "Alonso", "Burgos", "Bustamante", "Varela", "Lescano", "Aguilera", "Paredes", "Avalos", "Cuello", "Aquino",
            "Orellana", "Caballero", "Reynoso", "Reyes", "Villarreal", "Alarcon", "Pacheco", "Tapia", "Galarza",
            "Ocampo", "Meza", "Guerrero", "Salas", "Frias", "Videla", "Miño", "Jara", "Garay", "Rossi", "Lezcano",
            "Valenzuela", "Oliva", "Fuentes", "Robledo", "Espindola", "Nieto", "Pereira", "Brizuela", "Andrada",
            "Maciel", "Funes", "Robles", "Sotelo", "Cortez", "Almiron", "Rivas", "Gil", "Villegas", "Calderon",
            "Vergara", "Carabajal", "Ceballos", "Gallo", "Palavecino" };

    private static String[] frenchNames = { "Remy", "Charles", "Romain", "Florian", "Rene", "Julien", "Antoine",
            "Olivier", "Andre", "Quincy", "Marcellus", "Remi", "Eric", "Hugo", "Jules", "Lucas", "Raphael", "Gerard",
            "Matt", "Jacques", "Pierre", "Jean", "Jeanne", "Fabian", "Maurice", "Gustav", "Guy", "Emanuelle",
            "Francois", "Brett", "Xavier", "Yves", "Thierry", "Henry", "Robert", "Marcel", "Christophe", "Louis",
            "Philippe", "Edgar", "Claude", "Albert", "Auguste", "Alexandre", "Alain", "Benoit", "Chapin", "Edouard",
            "Fabrice", "Mathieu", "Franck", "Franc", "Didier", "Patrice" };
    private static String[] frenchSurnames = { "Martin", "Bernard", "Thomas", "Petit", "Richard", "Durand", "Dubois",
            "Moreau", "Laurent", "Gasly", "Grosjean", "Gignac", "Prost", "Valbuena", "Berger", "Bisset", "Boucher",
            "Brodeur", "Caron", "Charpentier", "Chevrolet", "Couture", "Fabron", "Faucheux", "Gagne", "Gnagnon",
            "Granger", "Lefebvre", "Marchand", "Mercier", "Mullins", "Paquet", "Page", "Pelletier", "Segal", "Legrand",
            "Leblanc", "Brun", "Beaulieu", "Beaumont", "Chastain", "Comtois", "Deschamps", "Dupont", "Desmarais",
            "Dupuis", "Linville", "Ribery" };

    private static String[] italianNames = { "Alberto", "Alessio", "Alessandro", "Angelo", "Anselmo", "Antonio",
            "Bruno", "Bernardo", "Carlo", "Cristiano", "Claudio", "Damiano", "Dario", "Domenico", "Enrico", "Enzo",
            "Eugenio", "Ezio", "Fabrizio", "Filippo", "Federico", "Francesco", "Franco", "Flavio", "Gaetano", "Gennaro",
            "Giorgio", "Giuliano", "Gregorio", "Lorenzo", "Marco", "Mario", "Martino", "Massimo", "Matteo", "Maurizio",
            "Mauro", "Nevio", "Norberto", "Ottaviano", "Paolo", "Pietro", "Riccardo", "Roberto", "Silvio", "Stefano",
            "Tommaso", "Umberto", "Valentino", "Vincenzo", "Vittorio", "Achille", "Aimone", "Alceste", "Alcide",
            "Amilcare", "Amintore", "Annibale", "Apollone", "Aristotele", "Astore", "Baldassarre", "Beppe", "Carmine",
            "Cesare", "Clemente", "Daniele", "Dante", "Davide", "Emanuele", "Ercole", "Ettore", "Felice", "Gabriele",
            "Gaspare", "Gastone", "Gentile", "Giacobbe", "Giosue", "Giuseppe", "Leone", "Marte", "Melchiorre",
            "Michele", "Nataniele", "Oddone", "Ottone", "Pasquale", "Raffaele", "Salomone", "Salvatore", "Samuele",
            "Sante", "Scipione", "Simone", "Ulisse", "Vitale", "Vittore", "Dionigi", "Gianni", "Giovanni", "Luigi",
            "Nanni", "Ranieri", "Andrea", "Battista", "Elia", "Enea", "Evangelista", "Luca", "Mattia", "Nicola" };
    private static String[] italianSurnames = { "Rossi", "Russo", "Ferrari", "Esposito", "Bianchi", "Romano", "Regio",
            "Ricci", "Marino", "Lupo", "Lastra", "Bruno", "Gallo", "Conti", "De Luca", "Mancini", "Costa", "Giordano",
            "Rizzo", "Lombardi", "Moretti", "Tarantino", "Cantu", "Milanesi", "Lucchese", "Greco", "Jilani",
            "Napolitano", "Cremonesi", "Calabrese", "Romano", "Pesci", "Giuliani", "Bruni", "Rivera", "Immobile",
            "Caputo", "De Rossi", "Totti", "Bonetti", "Armani", "Pacino", "Di Maggio", "Capone", "Gollini", "Pirlo",
            "Vialli", "Marcotti", "Maggio", "De Niro", "Barone", "Acerbi", "Sportiello", "Como", "Argento",
            "Berlusconi", "Pellegri", "Pochettino", "Di Francesco", "Berardi", "Pellegrini", "Barella", "Leto" };

    private static String[] dutchNames = { "Abraham", "Bram", "Adrianus", "Aart", "Janus", "Antonie", "Anthonie",
            "Antony", "Anthony", "Arend", "Berend", "Christiaan", "Cornelis", "Kees", "Nelis", "Derk", "Dirk", "Evert",
            "Gerrit", "Garrit", "Geert", "Harmen", "Herman", "Hendrik", "Jacob", "Jakob", "Coepe", "Ceupe", "Koepe",
            "Keupe", "Jan", "Karel", "Klaas", "Nicolaas", "Leendert", "Manus", "Marinus", "Matthias", "Matthijs",
            "Mattijs", "Thijs", "Meindert", "Machiel", "Michiel", "Peter", "Petrus", "Pieter", "Roelof", "Rudolf",
            "Steven", "Stephanus", "Teube", "Tobias", "Teunis", "Tonnis", "Theodorus", "Dorus", "Doris", "Wichart",
            "Willem", "Johan", "Max", "Martijn", "Marten", "Arjen", "Ruud", "Frenkie", "Jos", "Kevin", "Nick", "Theo",
            "Luuk", "Siem", "Rafael", "Dries", "Toby", "Erik", "Robin", "Wout", "Bas" };
    private static String[] dutchSurnames = { "de Jong", "Jansen", "de Vries", "van der Berg", "van den Berg",
            "van de Berg", "van Dijk", "Bakker", "Janssen", "Visser", "Smit", "Meijer", "de Boer", "Mulder", "de Groot",
            "Bos", "Vos", "Peters", "Hendriks", "Dekker", "van Leeuwen", "Brouwer", "Verstappen", "van der Vaart",
            "Krol", "van Nistelrooy", "Robben", "Heitinga", "Vossen", "Mertens", "de Bruyne", "de Wit", "Dijkstra",
            "Smits", "de Graaf", "van der Meer", "Willems", "Gossens", "de Wout", "Thyssen", "Teuling", "de Ligt",
            "de Konig", "Bouwman", "Huntelaar", "Promes", "Depay", "Wijnaldum", "de Bruin", "van der Westhuizen",
            "Weghorst", "Dost", "Alderweireld" };

    private static String[] portugueseNames = { "Bruno", "Eder", "Pepe", "Bernardo", "Cristiano", "Joao", "Ruben",
            "Jose", "Henrique", "Hernanes", "Gedson", "David", "Antonio", "Felix", "Pedro", "Guilherme", "Joaquim",
            "Jorge", "Felipe", "Aurelio", "Diogo", "Eduardo", "Fernando", "Luis", "Eusebio", "Miguel", "Nelson",
            "Gelson", "Marcelo", "Carlos", "Zeca", "Rui", "Sebastiao", "Ricardo", "Nuno", "Tiago", "Renato", "Manuel",
            "Paulo", "Otavio", "Goncalo", "Gabriel", "Eugenio", "Augusto", "Amancio", "Afonso", "Alexandre", "Claudio",
            "Fabiano", "Tomas", "Martim", "Lucas" };
    private static String[] portugueseSurnames = { "Silva", "Santos", "Ferreira", "Pereira", "Oliveira", "Costa",
            "Rodrigues", "Martins", "Jesus", "Sousa", "Fernandes", "Goncalves", "Gomes", "Lopes", "Marques", "Alves",
            "Almeida", "Ribeiro", "Pinto", "Carvalho", "Castelinho", "Teixeira", "Moreira", "Correia", "Mendes",
            "Nunes", "Soares", "Vieira", "Monteiro", "Cardoso", "Rocha", "Neves", "Coelho", "Cruz", "Cunha", "Pires",
            "Ramos", "Reis", "Simoes", "Antunes", "Matos", "Fonseca", "Machado", "Araujo", "Fonseca", "Barbosa",
            "Tavares", "Pimentel", "Nascimento", "Lima", "Moutinho", "Ronaldo", "Mourinho" };

    private static String[] turkishNames = { "Ahmet", "Ayaz", "Eymen", "Mehmet", "Mirac", "Muhammed", "Mustafa", "Omer",
            "Yusuf", "Aagha", "Aaghaa", "Abay", "Abi", "Adalar", "Adlee", "Afridi", "Afshin", "Agha", "Agin", "Aimal",
            "Ajdin", "Ajnur", "Akara", "Akay", "Alibek", "Alp", "Altan", "Anarbek", "Arystanbek", "Asen", "Asker",
            "Aslan", "Asuman", "Asur", "Ata", "Ataa", "Atil", "Aydar", "Aydin", "Azizbek", "Azzat", "Bahadir", "Baki",
            "Bakytbek", "Balian", "Baris", "Bata", "Bayar", "Bechir", "Beg", "Behrem", "Beibut", "Bekarys", "Berkan",
            "Buqat", "Burak", "Cahil", "Can", "Candana", "Candanin", "Candon", "Cenk", "Cetin", "Ceyhun", "Demir",
            "Deniz", "Duman", "Ediz", "Edric", "Efe", "Eldar", "Emin", "Emir", "Emmad", "Emre", "Emree", "Emry",
            "Enayat", "Encarna", "Ender", "Ensar", "Enver", "Eren", "Ergin", "Erol", "Eshaq", "Esmaeel", "Esmail",
            "Etemad", "Galip", "Guney", "Gyula", "Hakan", "Halil", "Hamit", "Hasad", "Hidir", "Hosmunt", "Kaan",
            "Kahraman", "Karawan", "Kiral", "Latafat", "Mart", "Mert", "Mesich", "Mesut", "Metin", "Murat", "Neval",
            "Nijaz", "Oguz", "Okan", "Olcay", "Onur", "Orhan", "Osmanek", "Osmen", "Otar", "Ozan", "Rifat", "Roshan",
            "Safet", "Sukru", "Suleyman", "Tengiz", "Tuncay", "Yusuf", "Ilkay", "Recep", "Cengiz", "Caglar" };
    private static String[] turkishSurnames = { "Aksoy", "Arap", "Aslan", "Avci", "Badem", "Balik", "Bardakci", "Baris",
            "Binici", "Burakgazi", "Degirmenci", "Demir", "Demirci", "Ekmekci", "Karga", "Kartal", "Katirci", "Koc",
            "Kucuk", "Makar", "Marangoz", "Mataraci", "Nacar", "Sadik", "Solak", "Teke", "Terzi", "Tilki", "Tiryaki",
            "Uzun", "Yilmaz", "Topal", "Belozoglu", "Erdogan", "Under", "Calhanoglu", "Senturk", "Inler", "Derdiyok",
            "Tufan", "Sukur", "Kahveci", "Recber", "Kaya", "Aga" };

    private static String[] russianNames = { "Ivan", "Ilya", "Nikolai", "Boris", "Vladimir", "Pyotr", "Andrei",
            "Aleksandr", "Filip", "Dmitrij", "Sergei", "Leonid", "Viktor", "Georgi", "Pavel", "Konstantin", "Kirill",
            "Vasili", "Roman", "Vladislav", "Vyacheslav", "Matvei", "Mikhail", "Oleg", "Igor", "Maksim", "Sasha",
            "Anatoly", "Gennady", "Grigory", "Grisha", "Yevgeniy", "Lev", "Misha", "Stanislav", "Vadim", "Valentin",
            "Valery", "Yaroslav", "Yuri", "Artem", "Bogdan", "Radoslav", "Daler", "Daniil", "Nikita", "Aleksei",
            "Stepan", "Denis" };
    private static String[] russianSurnames = { "Smirnov", "Ivanov", "Kuznetsov", "Popov", "Sokolov", "Lebedev",
            "Kozlov", "Novikov", "Morozov", "Petrov", "Volkov", "Solovyov", "Vasilyev", "Zaytsev", "Pavlov", "Semyonov",
            "Golubev", "Vinogradov", "Bogdanov", "Vorobyov", "Semak", "Zhirkov", "Stepanov", "Arshavin", "Yeltsin",
            "Gorbachev", "Yusupov", "Abramovich", "Akinfeev", "Aliyev", "Andropov", "Anisimov", "Antonov", "Mazepin",
            "Kvyat", "Berezutskiy", "Brezhnev", "Brusilov", "Bobrov", "Voronin", "Putin", "Vasin", "Gusev", "Golovin",
            "Goncharev", "Ignasevich", "Kerzhakov", "Fomin", "Chalov", "Miranchuk", "Cheryshev", "Sharapov" };

    private static String[] ukrainianNames = { "Anatoliy", "Andriy", "Anton", "Artem", "Artur", "Bohdan", "Ivan",
            "Yevhen", "Viktor", "Olexandr", "Olexiy", "Borys", "Kliment", "Konstantyn", "Danylo", "Serhiy", "Yuriy",
            "Mykola", "Heorhiy", "Denys", "Ihor", "Dmytro", "Mykyta", "Eduard", "Yevhenii", "Vitaliy", "Roman", "Marko",
            "Krystiyan", "Mykhailo", "Petro", "Pylyp", "Symon", "Taras", "Volodymyr", "Yosyp", "" };
    private static String[] ukrainianSurnames = { "Shevchenko", "Kovalenko", "Bondarenko", "Tkachenko", "Boyko",
            "Kravchenko", "Melnik", "Koval", "Rudenko", "Savchenko", "Marchenko", "Kovalchuk", "Klimenko", "Petrenko",
            "Bondar", "Kuzmenko", "Sidorenko", "Pavlenko", "Vasilenko", "Moroz", "Shevchuk", "Ponomarenko", "Levchenko",
            "Kharchenko", "Litvinenko", "Kolesnik", "Karpenko", "Oleynik", "Yurchenko", "Dyachenko", "Khomenko",
            "Panchenko", "Upravl", "Ivanov", "Nazarenko", "Pomeschenie", "Goncharenko", "Romanenko", "Prikhodko",
            "Lysenko", "Kirichenko", "Kostenko", "Tischenko", "Tereschenko", "Fedorenko", "Popov", "Makarenko",
            "Demchenko", "Tarasenko", "Nesterenko", "Tkachuk", "Kollektivnyy", "Pilipenko", "Timoshenko", "Shvets",
            "Vlasenko", "Kulik", "Kravchuk", "Gerasimenko", "Polischuk", "Litvin", "Petrova", "Moskalenko", "Kovtun",
            "Radchenko", "Miroshnichenko", "Babenko", "Volkova", "Kravets", "Gostinitsa", "Melnichenko", "Smirnova",
            "Prokopenko", "Grischenko", "Denisenko", "Naumenko", "Maksimenko", "Stepanenko", "Onischenko", "Ostapenko",
            "Scherbina", "Shulga", "Doroshenko", "Parkhomenko", "Babich", "Borisenko", "Upravlenie", "Sokolova",
            "Serdyuk", "Mischenko", "Mikhaylenko", "Lutsenko", "Vovk", "Dmitrenko", "Zavod", "Omelchenko", "Novikova",
            "Yarmolenko", "Konoplyanka", "Rotan", "Akhmetov", "Zinchenko" };

    private static String[] croatianNames = { "Berislav", "Berivoj", "Blago", "Bogdan", "Bogumil", "Bogoljub",
            "Bogomil", "Bojan", "Boris", "Borislav", "Borna", "Bozetjeh", "Bozidar", "Bozo", "Bratislav", "Budimir",
            "Branimir", "Brajko", "Branko", "Braslav", "Bratoljub", "Cvitko", "Cvjetko", "Caslav", "Castimir",
            "Cedomir", "Dalibor", "Damir", "Darko", "Davor", "Desimir", "Dobroslav", "Dobrovit", "Domagoj", "Dragan",
            "Drago", "Dragoslav", "Dragutin", "Dragomir", "Drazen", "Drziha", "Drzislav", "Godemir", "Gojko",
            "Gojislav", "Gojslav", "Goran", "Grubisa", "Hrvatin", "Hrvoj", "Hrvoje", "Hrvoslav", "Kazimir", "Kazimir",
            "Jasenko", "Klonimir", "Kresimir", "Krsevan", "Lavoslav", "Ljubomir", "Ljudevit", "Milan", "Mile",
            "Milivoj", "Milovan", "Miljenko", "Mirko", "Miroslav", "Miros", "Mislav", "Mladen", "Mojmir", "Mutimir",
            "Nediljko", "Nedjeljko", "Nenad", "Ognjen", "Ostoja", "Ozren", "Predrag", "Pribislav", "Prvan", "Prvoslav",
            "Prvos", "Radimir", "Radomir", "Rados", "Rajko", "Ranko", "Ratimir", "Ratko", "Rato", "Radovan", "Radoslav",
            "Slaven", "Slavisa", "Slavoljub", "Slavomir", "Smiljan", "Spomenko", "Srebrenko", "Srecko", "Stanislav",
            "Stanko", "Strahimir", "Svetoslav", "Tihomil", "Tihomir", "Tjesimir", "Tomislav", "Tomo", "Tvrtko",
            "Trpimir", "Vatroslav", "Veceslav", "Vedran", "Velimir", "Veselko", "Vidoslav", "Vjekoslav", "Vjenceslav",
            "Viseslav", "Vitomir", "Vjeran", "Vladimir", "Vlado", "Vlatko", "Vojmil", "Vojnomir", "Vuk", "Zdenko",
            "Zdeslav", "Zdravko", "Zorislav", "Zoran", "Zrinko", "Zrinoslav", "Zlatko", "Zvonimir", "Zvonko", "Zarko",
            "Zelimir", "Zeljko", "Zivko", "Aleksandar", "Ante", "Antija", "Danijel", "Dominik", "Edvard", "Filip",
            "Franjo", "Fridrik", "Ivan", "Josip", "Juraj", "Karlo", "Luka", "Marko", "Matej", "Nicola", "Niko", "Petar",
            "Stipe", "Lovro", "Andrej" };
    private static String[] croatianSurnames = { "Modric", "Petkovic", "Rakitic", "Perisic", "Kovac", "Prosinecki",
            "Horvat", "Kovacevic", "Babic", "Maric", "Juric", "Novak", "Kovacic", "Knezevic", "Vukovic", "Markovic",
            "Duvnjak", "Simunic", "Olic", "Bosnjak", "Blazevic", "Bozic", "Bilic", "Filipovic", "Grgic", "Ivanovic",
            "Kralj", "Loncar", "Lovric", "Majer", "Mandic", "Pavlovic", "Petrovic", "Tomic", "Simunovic", "Vukovic",
            "Zoric", "Antic", "Kalinic", "Kramaric", "Bogdanic", "Brankovic", "Hadzic", "Broz", "Pletikosa", "Srna" };

    private static String[] scotlandNames = { "Jack", "Oliver", "James", "Lewis", "Alexander", "Charlie", "Lucas",
            "Logan", "Harris", "Daniel", "Finlay", "Jacob", "Leo", "Mason", "Noah", "Harry", "Alfie", "Max", "Callum",
            "Aaron", "Adam", "Thomas", "Ethan", "Rory", "Cameron", "Archie", "Oscar", "Matthew", "Nathan", "Joshua",
            "Brodie", "William", "Liam", "Ryan", "Jamie", "Harrison", "Joseph", "Dylan", "Samuel", "Riley", "David",
            "Ollie", "Andrew", "Connor", "Luke", "Jaxon", "Kyle", "Benjamin", "Michael", "Caleb", "Jackson", "George",
            "Finn", "Leon", "Fraser", "Murray", "Jake", "John", "Arran", "Angus", "Cole", "Robert", "Cooper", "Isaac",
            "Jayden", "Aiden", "Kai", "Theo", "Jude", "Ben", "Tyler", "Owen", "Blake", "Freddie", "Euan", "Josh",
            "Blair", "Robbie", "Hamish", "Kian", "Sam", "Aidan", "Jay", "Christopher", "Reuben", "Cody", "Luca",
            "Lachlan", "Elliot", "Evan", "Sonny", "Calum", "Henry", "Rhys", "Carson", "Harvey", "Calvin", "Callan" };
    private static String[] scotlandSurnames = { "Aitken", "Alexander", "Allan", "Anderson", "Bell", "Black", "Boyle",
            "Brown", "Bruce", "Burns", "Cameron", "Campbell", "Christie", "Clark", "Craig", "Crawford", "Cunningham",
            "Davidson", "Dickson", "Dixon", "Docherty", "Donaldson", "Douglas", "Duncan", "Ferguson", "Findlay",
            "Fleming", "Forbes", "Fraser", "Gibson", "Gordon", "Graham", "Grant", "Gray", "Hamilton", "Henderson",
            "Hill", "Hughes", "Hunter", "Jamieson", "Johnstone", "Jones", "Kelly", "Kennedy", "Kerr", "King",
            "Macdonald", "Mackay", "Mackenzie", "Maclean", "Macleod", "Marshall", "Martin", "McDonald", "McGregor",
            "McIntosh", "McIntyre", "McKay", "McKenzie", "McLean", "McMillan", "Miller", "Millar", "Milne", "Mitchell",
            "Moore", "Morrison", "Muir", "Murphy", "Munro", "Murray", "Paterson", "Reid", "Reilly", "Ritchie",
            "Robertson", "Ross", "Russell", "Scott", "Shaw", "Simpson", "Sinclair", "Smith", "Stevenson", "Stewart",
            "Sutherland", "Taylor", "Thompson", "Thomson", "Walker", "Wallace", "Watson", "White", "Watt", "Williams",
            "Williamson", "Wilson", "Wood", "Wright", "Young" };

    private static String[] danishNames = { "William", "Noah", "Oscar", "Lucas", "Victor", "Malthe", "Oliver", "Alfred",
            "Carl", "Valdemar", "Emil", "Elias", "August", "Aksel", "Magnus", "Frederik", "Arthur", "Felix", "Anton",
            "Elliot", "Kevin", "Jan", "Christian", "Peter", "Michael", "Brian", "Peter", "Markus" };
    private static String[] danishSurnames = { "Nielsen", "Jensen", "Hansen", "Pedersen", "Andersen", "Christensen",
            "Larsen", "Sorensen", "Rasmussen", "Jorgensen", "Petersen", "Madsen", "Kristensen", "Olsen", "Thomsen",
            "Christiansen", "Poulsen", "Johansen", "Moller", "Mortensen", "Magnnussen", "Knudsen", "Lund", "Mikkelsen",
            "Jacobsen", "Eriksen", "Henriksen", "Clausen", "Holm", "Simonsen", "Dahl", "Iverson", "Kjaer", "Jepsen",
            "Skov", "Laudrup", "Nygaard" };

    private static String[] czechNames = { "Jakub", "Jan", "Tomas", "Adam", "Matyas", "Martin", "Michal", "Ondra",
            "Daniel", "Marek", "Petr", "Patrik", "Henry", "Filip", "Vojtech", "Lukas", "Karel", "Dominik", "Michael",
            "Radek", "David", "Josef", "Honza", "Matej", "Vaclav", "Krystof", "Jiri", "Milan", "Ondrej", "Zdenek",
            "Vladimir", "Jirka", "Ales" };
    private static String[] czechSurnames = { "Novak", "Svoboda", "Novotny", "Dvorak", "Cerny", "Prochazka", "Kucera",
            "Vesely", "Horak", "Nemec", "Maly", "Zelezny", "Kolar", "Sediak", "Koci", "Liska", "Zajic", "Jelinek",
            "Jezek", "Sokol", "Holub", "Cap", "Javor", "Ruzicka", "Jahoda", "Cibulka", "Hruska", "Makovec", "Oliva",
            "Slezak", "Moravec", "Rosicky", "Nepomucky", "Musil", "Pospisil", "Hrabal", "Zdrazil", "Svacina" };

    private static String[] greekNames = { "Andreas", "Alexios", "Daniel", "David", "Elias", "Angelos", "Christos",
            "Christoforos", "Evangelos", "Dimitrios", "Georgios", "Pantelis", "Sergios", "Stavros", "Theofilos",
            "Zacharias", "Stefanos", "Sotiris", "Thomas", "Kostas", "Petros", "Pavlos", "Ioannis", "Giannis", "Yiannis",
            "Markos", "Loukas", "Eleftherios", "Emmanouil", "Anastasios", "Athanasios", "Vassileios", "Christianos",
            "Konstantinos", "Gabriel", "Panagiotis", "Nicolaos", "Niketas", "Solomon", "Leo" };
    private static String[] greekSurnames = { "Mitsotakis", "Theodorakis", "Doukakis", "Xarhakos", "Kyrgiakos",
            "Katsioulis", "Giannoulis", "Melas", "Dimas", "Zorbas", "Lekkas", "Moustakas", "Zappas", "Pappas", "Doukas",
            "Georgatos", "Cosmatos", "Manatos", "Charisteas", "Georgeas", "Katselis", "Papadelis", "Georgallis",
            "Kanellis", "Stavridis", "Koutoufides", "Angelidis", "Georgiadis", "Stamatelopoulos", "Papadopoulos",
            "Gianopoulos", "Anagnostopoulos", "Theodorakopoulos", "Tsolakoglou", "Ardizoglou", "Patsatzoglou",
            "Afxentiou", "Economou", "Konstantinou", "Christoforou", "Gregoriou", "Politis", "Chiotis", "Hatzipanagis",
            "Devetzi", "Kouyioumtzis", "Samaras", "Papoutsis", "Kritikos", "Aivaliotis", "Kontos", "Mytaras", "Koufos",
            "Manolas" };

    private static String[] serbianNames = { "Miroslav", "Vladimir", "Zoran", "Ljubomir", "Milorad", "Dragan", "Milan",
            "Goran", "Radomir", "Vukasin", "Miomir", "Branimir", "Budimir", "Nikola", "Ivan", "Jovan", "Mihailo",
            "Aleksandar", "Andrej", "Djordje", "Stefan", "Petar", "Vasilije", "Todor", "Marko", "Antonije", "Pavle",
            "Srdjan", "Igor", "Dejan", "Vuk", "Nenad", "Prodan", "Stanisa", "Lazar", "Sasa", "Bojan", "Branislav",
            "Novak", "Zivko", "Zdravko", "Nemanja", "Andrija", "Predrag" };
    private static String[] serbianSurnames = { "Jovanovic", "Petrovic", "Nikolic", "Markovic", "Djordjevic",
            "Stojanovic", "Ilic", "Stankovic", "Pavlovic", "Milosevic", "Milankovic", "Petric", "Katic", "Sindjelic",
            "Nedic", "Maric", "Visnjic", "Janjic", "Saric", "Milicic", "Milenic", "Natalic", "Zoric", "Smiljic",
            "Andjelic", "Tosic", "Kolarov" };

    private static String[] hebrewNames = { "David", "Benjamin", "Nir", "Ariel", "Noam", "Lavi", "Joseph", "Uri",
            "Ethan", "Daniel", "Yehuda", "Moshe", "Raphael", "Aaron", "Itai", "Yehonathan", "Israel", "Abraham",
            "Samuel", "Omer", "Michael", "Itamar", "Yaakov", "Eliya", "Yair", "Yitzhak", "Yonathan", "Ido", "Ari",
            "Shimon", "Chaim", "Yanai", "Solomon", "Manor", "Harel", "Amit", "Nathaniel", "Mordechai" };
    private static String[] hebrewSurnames = { "Hoffman", "Pereira", "Abrams", "Haddad", "Goldmann", "Levi", "Levy",
            "Blau", "Friedman", "Fridman", "Friedmann", "Horowitz", "Abulafia", "Blum", "Kantor", "Cantor", "Fingerhut",
            "Leib", "Leiberman", "Leibermann", "Cardoso", "Bensaïd", "Cohen", "Efron", "Abutbul", "Schechter",
            "Abecassis", "Bernstein", "Geller", "Nazarian", "Bensoussan", "Melamed", "Weinberg", "Stern", "Benveniste",
            "Obadia", "Becker", "Chason", "Chazen", "Ackerman", "Rubin", "Altman", "Pasternak", "Nahum", "Navarro",
            "Naftali", "Nisim", "Aarons", "Abate", "Babel", "Bach", "Camhi", "Ancel", "Arenberg", "Alterman", "Abadi",
            "Abeles", "Netanyahu", "Biton", "Mizrachi", "Ohayon", "Katz" };

    private static String[] romanianNames = { "Andrei", "Constantin", "Cristian", "Daniel", "Dan", "Gheroghe",
            "Grigore", "Ilie", "Ion", "Ioan", "Iacob", "Laurentiu", "Luca", "Marcu", "Matei", "Mihail", "Mihai",
            "Nicolae", "Niculaie", "Pavel", "Petrul", "Petre", "Stefan", "Vasile", "Traian", "Titus", "Marius",
            "Ovidiu", "Cornel", "Codrut", "Sorin", "Florin", "Bogdan", "Dragos", "Mircea", "Radu", "Tihomir", "Vlad",
            "Vladislav", "Vladimir", "Miroslav", "Casimir", "Adrian", "Cosmin", "Alexandru", "Ionut", "Gabriel",
            "Darius", "Ciprian", "Razvan", "Ianis" };
    private static String[] romanianSurnames = { "Ionescu", "Mutu", "Petrescu", "Ardeleanu", "Moldoveanu", "Muresanu",
            "Popa", "Popescu", "Hagi", "Pop", "Radu", "Dumitru", "Stan", "Stoica", "Ciobanu", "Rusu", "Patrascu",
            "Maxim", "Rat", "Racovita", "Ionita", "Andone", "Contra", "Raducioiu", "Lucescu", "Ghanea", "Dobrin",
            "Marica", "Chivu", "Tatarusanu", "Nedelcearu", "Marin", "Tanase", "Keseru", "Man", "Nastase" };

    private static String[] hungarianNames = { "Andras", "Attila", "Bela", "Csabo", "Geza", "Gyorgy", "Istvan", "Imre",
            "Janos", "Jozsef", "Karoly", "Lajos", "Laszlo", "Mark", "Marton", "Matyas", "Sandor", "Tamas", "Zoltan",
            "Zsigmond", "Adam", "Ferenc", "Viktor", "Arpad", "Gabor", "Gustav", "Jeno", "Krisztian", "Erno", "Zsolt" };
    private static String[] hungarianSurnames = { "Nagy", "Kovacs", "Toth", "Szabo", "Horvath", "Varga", "Kiss",
            "Molnar", "Nemeth", "Farkas", "Balogh", "Papp", "Lakatos", "Takacs", "Juhasz", "Meszaros", "Olah", "Simon",
            "Racz", "Fekete", "Szilagyi", "Torok", "Feher", "Balazs", "Gal", "Orban", "Dzudszak", "Halasz", "Kalmar" };

    private static String[] polishNames = { "Robert", "Arkadiusz", "Antoni", "Jakub", "Jan", "Szymon", "Franciszek",
            "Filip", "Aleksander", "Mikolaj", "Wojciech", "Kacper", "Stanislaw", "Kazimierz", "Piotr", "Lech",
            "Mieszko", "Krzysztof", "Andrzej" };
    private static String[] polishSurnames = { "Lewandowski", "Milik", "Blaszczykowski", "Szczesny", "Nowak",
            "Kowalski", "Wisniewski", "Wojcik", "Kowalczyk", "Kaminski", "Zielinksi", "Szymanski", "Wozniak",
            "Dabrowski", "Kozlowski", "Jankowski", "Mazur", "Kwiatkowski", "Wojciechowski", "Krawczyk", "Kaczmarek",
            "Piotrowski", "Grabowski", "Trochowski", "Deyna", "Kuchar" };

    private static String[] norwegianNames = { "Jan", "Per", "Bjorn", "Kjell", "Ole", "Lars", "Arne", "Svein", "Knut",
            "Hans", "Geir", "Odd", "Tor", "Torje", "Morten", "John", "Rune", "Trond", "Thomas", "Erik", "Tore", "Tom",
            "John", "Gunnar", "Leif", "Olav", "Helge", "Frode", "Dag", "Stig", "Martin", "Iver", "Erling", "Thor",
            "Egil", "Gamst" };
    private static String[] norwegianSurnames = { "Hansen", "Johansen", "Olsen", "Larsen", "Andersen", "Nilsen",
            "Pedersen", "Jensen", "Kristiansen", "Karlsen", "Johnsen", "Pettersen", "Eriksen", "Berg", "Haugen",
            "Hagen", "Johannessen", "Jacobsen", "Andreassen", "Dahl", "Halvorsen", "Lund", "Henriksen", "Jorgensen",
            "Jakobsen", "Gundersen", "Moen", "Sorensen", "Iversen", "Svendsen", "Strand", "Solberg", "Martinsen",
            "Paulsen", "Knutsen", "Gulbrandsen", "Strom", "Fossum", "Odegaard", "Larsson", "Berger", "Haaland",
            "Moller" };

    public static String getPlayerName(String nation_name) {
        String[] spanish_countries = { spain, argentina, bolivia, chile, colombia, peru, paraguay, ecuador, uruguay,
                venezuela };
        String[] portuguese_countries = { brazil, portugal };
        String[] german_countries = { germany, austria };
        if (nation_name.equalsIgnoreCase(england)) {
            return getName(englishNames, englishSurnames);
        } else if (Arrays.asList(german_countries).contains(nation_name)) {
            return getName(germanNames, germanSurnames);
        } else if (Arrays.asList(spanish_countries).contains(nation_name)) {
            return getName(spanishNames, spanishSurnames);
        } else if (nation_name.equalsIgnoreCase(france)) {
            return getName(frenchNames, frenchSurnames);
        } else if (nation_name.equalsIgnoreCase(italy)) {
            return getName(italianNames, italianSurnames);
        } else if (nation_name.equalsIgnoreCase(netherlands)) {
            return getName(dutchNames, dutchSurnames);
        } else if (Arrays.asList(portuguese_countries).contains(nation_name)) {
            return getName(portugueseNames, portugueseSurnames);
        } else if (nation_name.equalsIgnoreCase(turkey)) {
            return getName(turkishNames, turkishSurnames);
        } else if (nation_name.equalsIgnoreCase(russia)) {
            return getName(russianNames, russianSurnames);
        } else if (nation_name.equalsIgnoreCase(ukraine)) {
            return getName(ukrainianNames, ukrainianSurnames);
        } else if (nation_name.equalsIgnoreCase(croatia)) {
            return getName(croatianNames, croatianSurnames);
        } else if (nation_name.equalsIgnoreCase(scotland)) {
            return getName(scotlandNames, scotlandSurnames);
        } else if (nation_name.equalsIgnoreCase(denmark)) {
            return getName(danishNames, danishSurnames);
        } else if (nation_name.equalsIgnoreCase(belgium)) {
            int prob = CommonFunctions.getRandomNumber(100);
            if (prob < 59) {
                return getName(dutchNames, dutchSurnames);
            } else {
                return getName(frenchNames, frenchSurnames);
            }
        } else if (nation_name.equalsIgnoreCase(czechia)) {
            return getName(czechNames, czechSurnames);
        } else if (nation_name.equalsIgnoreCase(greece)) {
            return getName(greekNames, greekSurnames);
        } else if (nation_name.equalsIgnoreCase(serbia)) {
            return getName(serbianNames, serbianSurnames);
        } else if (nation_name.equalsIgnoreCase(israel)) {
            return getName(hebrewNames, hebrewSurnames);
        } else if (nation_name.equalsIgnoreCase(hungary)) {
            return getName(hungarianNames, hungarianSurnames);
        } else if (nation_name.equalsIgnoreCase(poland)) {
            return getName(polishNames, polishSurnames);
        } else if (nation_name.equalsIgnoreCase(norway)) {
            return getName(norwegianNames, norwegianSurnames);
        } else if (nation_name.equalsIgnoreCase(switzerland)) {
            int prob = CommonFunctions.getRandomNumber(100);
            if (prob < 65) {
                return getName(germanNames, germanSurnames);
            } else if (prob < 90) {
                return getName(frenchNames, frenchSurnames);
            } else {
                return getName(italianNames, italianSurnames);
            }
        } else if (nation_name.equalsIgnoreCase(cyprus)) {
            int prob = CommonFunctions.getRandomNumber(100);
            if (prob < 75) {
                return getName(greekNames, greekSurnames);
            } else {
                return getName(turkishNames, turkishSurnames);
            }
        } else if (nation_name.equalsIgnoreCase(romania)) {
            int prob = CommonFunctions.getRandomNumber(100);
            if (prob < 88) {
                return getName(romanianNames, romanianSurnames);
            } else {
                return getName(hungarianNames, hungarianSurnames);
            }
        } else {
            return "Player Player";
        }
    }

    public static String getName(String[] names, String[] surnames) {
        return CommonFunctions.getRandomElement(names) + " " + CommonFunctions.getRandomElement(surnames);
    }
}
