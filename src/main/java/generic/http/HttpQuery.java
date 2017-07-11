package generic.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.of;

/**
 * Created by sanver on 6/30/2017.
 */
public class HttpQuery {

    /**
     * Get request method
     * @param url
     * @param headers
     * @return Optional HttpResult
     */
    public static Optional<HttpResult> sendGet(String url, Map<String, String> headers) {
        HttpResult result = null;
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            //add request headers
            headers.forEach((k, v) -> {
                con.setRequestProperty(k, v);
            });
            int responseCode = con.getResponseCode();

            String response;
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))){
                response = in.lines().collect(Collectors.joining("\n"));
            }

            result = new HttpResult(responseCode, response);

        } catch (MalformedURLException e) {
            System.out.println("Malformed URL " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO Exception " + e.getMessage());
        }

        return of(result);
    }

    /**
     * Post request method
     * @param url
     * @param headers
     * @param body
     * @return Optional HttpResult
     */
    public static Optional<HttpResult> sendPost(String url, Map<String, String> headers, String body) {
        HttpResult result = null;
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            //add request headers
            headers.forEach((k, v) -> {
                con.setRequestProperty(k, v);
            });

            // Send post request
            con.setDoOutput(true);
            try(DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.writeBytes(body);
                wr.flush();
            }

            int responseCode = con.getResponseCode();

            String response;
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))){
                response = in.lines().collect(Collectors.joining("\n"));
            }

            result = new HttpResult(responseCode, response);

        } catch (MalformedURLException e) {
            System.out.println("Malformed URL " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO Exception " + e.getMessage());
        }

        return of(result);
    }
}
