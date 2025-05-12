import org.apache.http.HttpHeaders;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApiCaller {
    static String serverAddress = "http://192.168.0.105:8000";
    static String token = "";
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

            JSONObject jsonResponse = new JSONObject(responseBody);

            token = jsonResponse.getString("token");

            System.out.println("Response Body: " + responseBody);
            String[] parts = token.split("\\.");
            if (parts.length < 2) {
                throw new Exception("Invalid Token");
            }

            String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
            JSONObject payload = new JSONObject(payloadJson);
            boolean medAccess = payload.optString("MedAccess", "No").equals("Yes");
            boolean pharmaAccess = payload.optString("PharmaAccess", "No").equals("Yes");

            Main.MainW.SetTabsState(medAccess, pharmaAccess);
            Main.MainW.TicketT.load_next_Page();

            System.out.println("Token: " + token);
            System.out.println("Med Access: " + medAccess);
            System.out.println("Pharma Access: " + pharmaAccess);

        } catch (HttpResponseException e) {
            if (e.getStatusCode() == 456)
                throw new Exception("HTTP ERROR: " + e.getStatusCode() + " - Admin Not Found");
            else if (e.getStatusCode() == 403) {
                throw new Exception("HTTP ERROR: " + e.getStatusCode() + " - Invalid Password");
            } else {
                throw new Exception("HTTP Error:" + e.getStatusCode() + " - " + e.getMessage());
            }
        } catch (Exception e) {
            throw new Exception("UnExpected Error: " + e.getMessage(), e);
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

            } catch (HttpResponseException e) {
                if (e.getStatusCode() == 401) {
                    Logout();
                    throw new RuntimeException("HTTP ERROR: " + e.getStatusCode() + " - Session Ended due to invalid or expired token");
                } else if (e.getStatusCode() == 456)
                    throw new RuntimeException("HTTP ERROR: " + e.getStatusCode() + " - DataEntity Not Found");
                else {
                    throw new RuntimeException("HTTP Error:" + e.getStatusCode() + " - " + e.getMessage());
                }
            } catch (Exception e) {
                throw new RuntimeException("UnExpected Error: " + e.getMessage(), e);
            }
        }, executorService);
    }

    public static CompletableFuture<String> PostRequest(String route, JSONObject body) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        return CompletableFuture.supplyAsync(() -> {
            try (CloseableHttpClient client = HttpClients.createDefault()) {

                HttpPost request = new HttpPost(serverAddress + route);

                request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
                request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
                request.setEntity(new StringEntity(body.toString(), "UTF-8"));

                ResponseHandler<String> responseHandler = new BasicResponseHandler();

                String responseBody = client.execute(request, responseHandler);
                if (responseBody == null) {
                    throw new Exception("Received an empty response from the server.");
                }

                return responseBody;

            } catch (HttpResponseException e) {
                if (e.getStatusCode() == 401) {
                    Logout();
                    throw new RuntimeException("HTTP ERROR: " + e.getStatusCode() + " - Session Ended due to invalid or expired token");
                } else if (e.getStatusCode() == 456)
                    throw new RuntimeException("HTTP ERROR: " + e.getStatusCode() + " - DataEntity Not Found");
                else if (e.getStatusCode() == 409)
                    throw new RuntimeException("HTTP ERROR: " + e.getStatusCode() + " - failed to complete operation");
                else {
                    throw new RuntimeException("HTTP Error:" + e.getStatusCode() + " - " + e.getMessage());
                }
            } catch (Exception e) {
                throw new RuntimeException("UnExpected Error: " + e.getMessage(), e);
            }
        }, executorService);
    }

    public static CompletableFuture<String> DeleteRequest(String route) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        return CompletableFuture.supplyAsync(() -> {
            try (CloseableHttpClient client = HttpClients.createDefault()) {

                HttpDelete request = new HttpDelete(serverAddress + route);

                request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

                ResponseHandler<String> responseHandler = new BasicResponseHandler();

                String responseBody = client.execute(request, responseHandler);
                if (responseBody == null) {
                    throw new Exception("Received an empty response from the server.");
                }

                return responseBody;

            } catch (HttpResponseException e) {
                if (e.getStatusCode() == 401) {
                    Logout();
                    throw new RuntimeException("HTTP ERROR: " + e.getStatusCode() + " - Session Ended due to invalid or expired token");
                } else if (e.getStatusCode() == 456)
                    throw new RuntimeException("HTTP ERROR: " + e.getStatusCode() + " - DataEntity Not Found");
                else if (e.getStatusCode() == 409)
                    throw new RuntimeException("HTTP ERROR: " + e.getStatusCode() + " - Failed to complete  operation");
                else {
                    throw new RuntimeException("HTTP Error:" + e.getStatusCode() + " - " + e.getMessage());
                }
            } catch (Exception e) {
                throw new RuntimeException("UnExpected Error: " + e.getMessage(), e);
            }
        }, executorService);
    }

}
