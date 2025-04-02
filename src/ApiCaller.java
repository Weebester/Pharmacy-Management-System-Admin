
import org.apache.http.HttpHeaders;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApiCaller {
    static String serverAddress = "http://192.168.0.105:8000";
    static String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJBZG1pbklEIjoiS2FycmFyIiwiTWVkQWNjZXNzIjoiWWVzIiwiUGhhcm1hQWNjZXNzIjoiWWVzIiwiZXhwIjoxNzQzNjAyNzc2fQ.4i2-S28DKl47mYV5e_YdePwd7v-VhTLSu6qTXH-2W7M";
    static Boolean medAccess = false;
    static Boolean pharmaAccess = false;

    public static void Login(String adminID, String password) throws Exception {

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            JSONObject json = new JSONObject();
            json.put("AdminID", adminID);
            json.put("AdminPass", password);

            HttpPost request = new HttpPost(serverAddress + "/LoginAdmin");
            request.setHeader("Content-Type", "application/json");
            request.setEntity(new StringEntity(json.toString()));

            ResponseHandler<String> responseHandler = new BasicResponseHandler();

            String responseBody = client.execute(request, responseHandler);

            System.out.println("Response Body: " + responseBody);
            String[] parts = responseBody.split("\\.");
            if (parts.length < 2) {
                throw new IOException("Invalid Token");
            }

            //String token = responseBody.substring(1,responseBody.length()-1);
            String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
            JSONObject payload = new JSONObject(payloadJson);


            boolean medAccess = payload.optString("MedAccess", "No").equals("Yes");
            boolean pharmaAccess = payload.optString("PharmaAccess", "No").equals("Yes");

            Main.MainW.SetTabsState(medAccess, pharmaAccess);

            System.out.println("Token: " + token);
            System.out.println("Med Access: " + medAccess);
            System.out.println("Pharma Access: " + pharmaAccess);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }


    }

    public static void Logout() {
        token = "";
        medAccess = false;
        pharmaAccess = false;
        Main.MainW.SetTab(0);
        Main.MainW.SetTabsState(false, false);
    }


    public static CompletableFuture<String> GetRequest(String route) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        return CompletableFuture.supplyAsync(() -> {
            try (CloseableHttpClient client = HttpClients.createDefault()) {

                HttpGet request = new HttpGet(serverAddress + route);

                request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

                ResponseHandler<String> responseHandler = new BasicResponseHandler();


                String responseBody = client.execute(request, responseHandler);
                if (responseBody == null) {
                    throw new Exception("Received an empty response from the server.");
                }

                return responseBody;

            } catch (Exception e) {
                throw new RuntimeException("Error during GET request: " + e.getMessage(), e);
            }
        }, executorService);
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
