import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class ApiCaller {
    static String serverAddress = "http://192.168.0.105:8000";
    static String token = "";
    static Boolean medAccess = false;
    static Boolean pharmaAccess = false;

    public static void Login(String adminID, String password) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        JSONObject json = new JSONObject();
        json.put("AdminID", adminID);
        json.put("AdminPass", password);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverAddress + "/LoginAdmin"))
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("HTTP Error: " + response.statusCode() + " - " + response.body());
        }

        String[] parts = response.body().split("\\.");

        if (parts.length < 2) {
            throw new IOException("Invalid Token");

        }
        token = response.body();

        String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
        JSONObject payload = new JSONObject(payloadJson);

        medAccess = payload.optString("MedAccess", "No").equals("Yes");
        pharmaAccess = payload.optString("PharmaAccess", "No").equals("Yes");

        Main.MainW.SetTabsState(medAccess, pharmaAccess);

        //System.out.println(token+"\n"+medAccess+"\n"+pharmaAccess);
    }

    public static void Logout() {
        token = "";
        medAccess = false;
        pharmaAccess = false;
        Main.MainW.SetTab(0);
        Main.MainW.SetTabsState(false, false);
    }

    public static String GetRequest(String route) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverAddress + route))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new IOException("HTTP Error: " + response.statusCode() + " - " + response.body());
        }

        return response.body();
    }

    /*String jsonResponse = ApiCaller.GetRequest("/test");
                JSONObject jsonObject = new JSONObject(jsonResponse);

                String text = jsonObject.getString("text");
                int num1 = jsonObject.getInt("num1");
                int num2 = jsonObject.getInt("num2");

                SwingUtilities.invokeLater(() -> {
                    textField.setText(text);
                    textField2.setText(String.valueOf(num1 + num2));
                });*/

}
