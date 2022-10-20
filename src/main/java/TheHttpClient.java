import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TheHttpClient {
    private final String baseUrl;
    private final HttpClient client;
    private final String token;

    public TheHttpClient(String baseUrl) {
        this.baseUrl = baseUrl;
        client = HttpClient.newHttpClient();
        token = "Bearer " + System.getenv().get("token");
    }

    public HttpResponse<String> post(String userJson) {
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(baseUrl))
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", token)
                    .POST(HttpRequest.BodyPublishers.ofString(userJson))
                    .build();
        } catch (URISyntaxException e) {
            System.out.println("Cannot create request. Massage: " + e.getMessage());
            System.exit(0);
        }

        return sendAndVerifyResponse(request);
    }

    public HttpResponse<String> delete(int id) {
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(baseUrl + "/" + id))
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", token)
                    .DELETE()
                    .build();
        } catch (URISyntaxException e) {
            System.out.println("Cannot create request. Massage: " + e.getMessage());
            System.exit(0);
        }

        return sendAndVerifyResponse(request);
    }

    public HttpResponse<String> get() {
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(baseUrl))
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization", token)
                    .GET()
                    .build();
        } catch (URISyntaxException e) {
            System.out.println("Cannot create request. Massage: " + e.getMessage());
            System.exit(0);
        }

        return sendAndVerifyResponse(request);
    }

    private HttpResponse<String> sendAndVerifyResponse(HttpRequest request) {
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.out.println("Cannot send a request. Message: " + e.getMessage());
            System.exit(0);
        }

        if (response.statusCode() >= 300 || response.statusCode() < 200) {
            System.out.println("Request error. Status code: " + response.statusCode());
            System.exit(0);
        }

        return response;
    }
}

