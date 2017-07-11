package generic.http;

/**
 * Created by sanver on 6/30/2017.
 */
public class HttpResult {
    private int statusCode;
    private String content;

    public HttpResult(int statusCode, String content) {
        this.statusCode = statusCode;
        this.content = content;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getContent() {
        return content;
    }
}
