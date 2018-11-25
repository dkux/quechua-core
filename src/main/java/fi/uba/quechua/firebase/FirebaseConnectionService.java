package fi.uba.quechua.firebase;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FirebaseConnectionService {

  private static final Logger log = LoggerFactory.getLogger(FirebaseConnectionService.class);

  private static final String PROJECT_ID = "quechuaapp-ac3ed";
  private static final String BASE_URL = "https://fcm.googleapis.com";
  private static final String FCM_SEND_ENDPOINT = "/v1/projects/" + PROJECT_ID + "/messages:send";

  private static final String MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
  private static final String[] SCOPES = { MESSAGING_SCOPE };

  /**
   * Retrieve a valid access token that can be use to authorize requests to the FCM REST
   * API.
   *
   * @return Access token.
   * @throws IOException
   */
  private static String getAccessToken() throws IOException {
    GoogleCredential googleCredential = GoogleCredential
        .fromStream(new FileInputStream("./src/main/resources/config/firebase.json"))
        .createScoped(Arrays.asList(SCOPES));
    googleCredential.refreshToken();
    return googleCredential.getAccessToken();
  }

  /**
   * Create HttpURLConnection that can be used for both retrieving and publishing.
   *
   * @return Base HttpURLConnection.
   * @throws IOException
   */
  private static HttpURLConnection getConnection() throws IOException {
    URL url = new URL(BASE_URL + FCM_SEND_ENDPOINT);
    log.info("Firebase URL - " + url);
    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
    log.info("Firebase token - " + getAccessToken());
    httpURLConnection.setRequestProperty("Authorization", "Bearer " + getAccessToken());
    httpURLConnection.setRequestProperty("Content-Type", "application/json; UTF-8");
    return httpURLConnection;
  }

  private static String inputstreamToString(InputStream inputStream) throws IOException {
    StringBuilder stringBuilder = new StringBuilder();
    Scanner scanner = new Scanner(inputStream);
    while (scanner.hasNext()) {
      stringBuilder.append(scanner.nextLine());
    }
    return stringBuilder.toString();
  }

  /**
   * Send request to FCM message using HTTP.
   *
   * @param fcmMessage Body of the HTTP request.
   * @throws IOException
   */
  public static void sendMessage(String fcmMessage) throws IOException {
    HttpURLConnection connection = getConnection();
    connection.setDoOutput(true);
    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
    outputStream.writeBytes(fcmMessage);
    outputStream.flush();
    outputStream.close();

    int responseCode = connection.getResponseCode();
    if (responseCode == 200) {
      String response = inputstreamToString(connection.getInputStream());
      System.out.println("Message sent to Firebase for delivery, response:");
      System.out.println(response);
      log.info("Message sent to Firebase for delivery, response: " + response);
    } else {
      System.out.println("Unable to send message to Firebase:");
      String response = inputstreamToString(connection.getErrorStream());
      System.out.println(response);
      log.error("Unable to send message to Firebase: " + response);
    }
  }

/*
{
  "message":{
      "token" : "bk3RNwTe3H0:CI2k_HHwgIpoDKCIZvvDMExUdFQ3P1...",
      "notification" : {
          "body" : "This is an FCM notification message!",
          "title" : "FCM Message"
        }
    }
}
*/
  public static String buildNotificationMessage(String title, String body, String token) {
    JsonObject jNotification = new JsonObject();
    jNotification.addProperty("title", title);
    jNotification.addProperty("body", body);

    JsonObject jMessage = new JsonObject();
    jMessage.addProperty("token", token);
    jMessage.add("notification", new Gson().toJsonTree(jNotification));

    JsonObject jMessageAux = new JsonObject();
    jMessageAux.add("message", new Gson().toJsonTree(jMessage));

    return jMessageAux.toString();
  }

}