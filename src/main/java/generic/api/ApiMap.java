package generic.api;

import generic.io.Interaction;
import generic.util.ConfigProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sanver.
 */
public class ApiMap {
    private Map<String, APIQuery> map;

    public ApiMap(ConfigProperties p, Interaction i) {
        map = new HashMap<>();
        map.put("imdb", new ImdbApiQuery(p, i));
        map.put("spotify", new SpotifyApiQuery(p, i));
    }

    public APIQuery getRelevantInterface(String type) {
        return map.get(type);
    }

}
