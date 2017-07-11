package generic;

import generic.api.APIQuery;
import generic.api.ImdbApiQuery;
import generic.api.SpotifyApiQuery;
import generic.io.ArgumentInteraction;
import generic.io.Interaction;
import generic.util.ConfigProperties;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Integration Tests to query Spotify api
 */
public class SpotifyApiQueryTest {

    private static Interaction i;
    private static APIQuery q;

    @BeforeClass
    public static void setUp() {
        i = new ArgumentInteraction();
        ConfigProperties c = new ConfigProperties("api-test.properties");
        q = new SpotifyApiQuery(c, i);
    }

    @Test
    public void shouldRetrieveFilmInfo() {
        i.writeParameter("music", "roadhouse blues");
        List<String> resultList = q.query();
        assertTrue("retrieval error", resultList.size() > 0);
    }
}
