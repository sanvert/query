package generic.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import generic.http.HttpQuery;
import generic.io.Interaction;
import generic.util.ConfigProperties;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sanver.
 */
public class ImdbApiQuery implements APIQuery {

    private String apiPrefix;
    private String token;
    private Interaction interaction;

    public ImdbApiQuery(ConfigProperties properties, Interaction i) {
        apiPrefix = properties.getValue("imdb.api");
        token = properties.getValue("imdb.api.token");
        interaction = i;
    }

    @Override
    public List<String> query() {
        String phrase = interaction.readByParameter("movie");
        String url;
        try {
            url = apiPrefix + "?api_key=" + token + "&query=" + URLEncoder.encode(phrase, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("Wrong encoding of URL " + e.getMessage());
            return new ArrayList<>();
        }

        return HttpQuery.sendGet(url, new HashMap<>()).map(result -> {
            JsonElement root = new JsonParser().parse(result.getContent());
            JsonArray list = root.getAsJsonObject().get("results").getAsJsonArray();

            final List dataList = new ArrayList<>();
            list.forEach(element -> {
                String data = element.getAsJsonObject().get("title").getAsString() +
                        ", " +
                        element.getAsJsonObject().get("release_date").getAsString();

                dataList.add(data);
            });

            return dataList;
        }).orElse(new ArrayList<>());
}}
