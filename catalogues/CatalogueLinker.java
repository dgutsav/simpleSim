package catalogues;

import java.util.*;

class Nation {
    public String id, name, denonym, region;
    public List<String> cities = new ArrayList<>();
    public List<String> favourableNations = new ArrayList<>();
    public List<NameSource> nameSources = new ArrayList<>();
    public List<WeightedProfile> clubNaming = new ArrayList<>(); // single string handled by deserializer or just always
                                                                 // use list
}

class NameSource {
    public int weight = 100;
    public String names; // language id
    public String surnames; // language id
}

class WeightedProfile {
    public int weight = 100;
    public String profile;
}

class NamePool {
    public String id;
    public List<String> names = new ArrayList<>();
    public List<String> surnames = new ArrayList<>();
}

class ClubNaming {
    public String id;
    public List<String> prefixes = List.of("FC");
    public List<String> suffixes = List.of("FC");
}

public class CatalogueLinker {

}
