package generic;

import generic.api.ApiMap;
import generic.io.ArgumentInteraction;
import generic.io.Interaction;
import generic.util.ConfigProperties;

/**
 * Query API to query movies and films!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println("Query API started!");
        Interaction argInteraction = new ArgumentInteraction();
        ApiMap map = new ApiMap(new ConfigProperties("api.properties"), argInteraction);

        String api = argInteraction.readByParameter("api");

        map.getRelevantInterface(api)
                .query()
                .stream()
                .forEach(System.out::println);
    }
}
