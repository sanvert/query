package generic.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import generic.http.HttpQuery;
import generic.io.Interaction;
import generic.util.ConfigProperties;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Created by sanver.
 */
public class SpotifyApiQuery implements APIQuery {
    private String dataApiPrefix;
    private String apiPrefix;
    private String clientId;
    private String clientSecret;
    private Interaction interaction;

    public SpotifyApiQuery(ConfigProperties properties, Interaction i) {
        dataApiPrefix = properties.getValue("spotify.data.api");
        apiPrefix = properties.getValue("spotify.api");
        clientId = properties.getValue("spotify.api.client.id");
        clientSecret = properties.getValue("spotify.api.client.secret");
        interaction = i;
    }

    /**
     * Retrieves client token from Spotify server in client credentials manner
     * @return String token
     */
    private String generateClientToken() {
        String url = apiPrefix + "/api/token";
        String body = "grant_type=client_credentials";

        // Add headers for post request
        Map<String, String> headers = new HashMap(){{
            put("Authorization", "Basic " + Base64.getEncoder().encodeToString(((clientId + ":" + clientSecret).getBytes())));
            put("Accept", "application/json");
            put("Content-Type", "application/x-www-form-urlencoded");
            put("Content-Length", Integer.toString(body.length()));
        }};

        return HttpQuery.sendPost(url, headers, body).map(result -> {
            JsonElement root = new JsonParser().parse(result.getContent());
            return root.getAsJsonObject().get("access_token").getAsString();
        }).orElse("");
    }

    @Override
    public List<String> query() {
        String phrase = interaction.readByParameter("music");
        String token = "Bearer " + generateClientToken();
        // Add headers for get request
        Map<String, String> headers = new HashMap(){{
            put("Authorization", token);
        }};

        String url;
        try {
            url = dataApiPrefix + "/v1/search" + "?q=" + URLEncoder.encode(phrase, StandardCharsets.UTF_8.name()) + "&type=album";
        } catch (UnsupportedEncodingException e) {
            System.out.println("Wrong encoding of URL " + e.getMessage());
            return new ArrayList<>();
        }

        return HttpQuery.sendGet(url, headers).map(result -> {
            final List dataList = new ArrayList<>();

            JsonElement root = new JsonParser().parse(result.getContent());
            JsonElement albumsElement = root.getAsJsonObject().get("albums");
            JsonArray albumArr = albumsElement.getAsJsonObject().get("items").getAsJsonArray();

            albumArr.forEach(element -> {
                StringBuilder data = new StringBuilder();
                data.append(element.getAsJsonObject().get("name").getAsString())
                        .append(" -> ");
                element.getAsJsonObject().get("artists").getAsJsonArray().forEach(artist -> {
                    data.append(artist.getAsJsonObject().get("name").getAsString())
                            .append(", ");
                });
                dataList.add(data);
            });

            return dataList;
        }).orElse(new ArrayList<>());
    }
}
