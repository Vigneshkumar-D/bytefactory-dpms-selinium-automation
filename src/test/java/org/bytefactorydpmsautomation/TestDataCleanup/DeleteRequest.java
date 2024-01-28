package org.bytefactorydpmsautomation.TestDataCleanup;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DeleteRequest {
    final String username = "Administrator";
    final String password = "123456";
    public void makeApiCall(URL url){
         try {
             HttpURLConnection connection = (HttpURLConnection) url.openConnection();
             connection.setRequestMethod("GET");
             String credentials = username + ":" + password;
             String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
             connection.setRequestProperty("Authorization", "Basic " + encodedCredentials);
             int responseCode = connection.getResponseCode();
             if (responseCode == HttpURLConnection.HTTP_OK) {
                 BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                 StringBuilder response = new StringBuilder();
                 String line;
                 while ((line = reader.readLine()) != null) {
                     response.append(line);
                 }
                 reader.close();
                 System.out.println("Response: " + response);
                 System.out.println("Response code: " + responseCode);
             } else {
                 System.out.println("Failed to get data. Response code: " + responseCode);
             }
             connection.disconnect();
         } catch (Exception e) {
             System.err.println("Error: " + e.getMessage());
         }
    }
    public void deleteRole(int id) throws MalformedURLException {
        String baseUrl = "http://192.168.0.189:4000/api/role/";
        URL url = new URL(baseUrl+id);
        makeApiCall(url);
    }
    public void deleteUser(int id) throws MalformedURLException {
        String baseUrl = "http://192.168.0.189:4000/api/user/";
        URL url = new URL(baseUrl+id);
        makeApiCall(url);
    }
    public void deleteUserGroup(int id) throws MalformedURLException {
        String baseUrl = "http://192.168.0.189:4000/api/user-groups/";
        URL url = new URL(baseUrl+id);
        makeApiCall(url);
    }
    public void deleteChecks(int id) throws MalformedURLException {
        String baseUrl = "http://192.168.0.189:4000/api/check/";
        URL url = new URL(baseUrl+id);
        makeApiCall(url);
    }
    public void deleteCheckTypes( int id) throws MalformedURLException {
        String baseUrl = "http://192.168.0.189:4000/api/check-type/";
        URL url = new URL(baseUrl+id);
        makeApiCall(url);
    }
    public void deleteCheckList( int id) throws MalformedURLException {
        String baseUrl = "http://192.168.0.189:4000/api/check-list/";
        URL url = new URL(baseUrl+id);
        makeApiCall(url);
    }
    public void deleteAsset( int id) throws MalformedURLException {
        String baseUrl = "http://192.168.0.189:4000/api/asset/";
        URL url = new URL(baseUrl+id);
        makeApiCall(url);
    }
    public void deleteScheduler( int id) throws MalformedURLException {
        String baseUrl = "http://192.168.0.189:4000/api/scheduler/";
        URL url = new URL(baseUrl+id);
        makeApiCall(url);
    }
    public void deleteResolutionWorkOrder(int id) throws MalformedURLException {
        String baseUrl = "http://192.168.0.189:4000/api/resolution-work-order/";
        URL url = new URL(baseUrl+id);
        makeApiCall(url);
    }
}

