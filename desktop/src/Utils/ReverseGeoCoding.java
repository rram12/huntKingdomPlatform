/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author mosla
 */
public class ReverseGeoCoding {
 
//    public String getGouvernorat(String latitude, String longitude) {
//        String chaine=jsonGetRequest("https://maps.googleapis.com/maps/api/geocode/json?latlng="+latitude+","+longitude+"&key=AIzaSyDkOnW67jYoKtBRu3utiogSoXzDQems9Lg");
//       return chaine;
//  }
//
//  private static String streamToString(InputStream inputStream) {
//    String text = new Scanner(inputStream, "UTF-8").useDelimiter("\\Z").next();
//    return text;
//  }
//
//  public static String jsonGetRequest(String urlQueryString) {
//    String json = null;
//    try {
//      URL url = new URL(urlQueryString);
//      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//      connection.setDoOutput(true);
//      connection.setInstanceFollowRedirects(false);
//      connection.setRequestMethod("GET");
//      connection.setRequestProperty("Content-Type", "application/json");
//      connection.setRequestProperty("charset", "utf-8");
//      connection.connect();
//      InputStream inStream = connection.getInputStream();
//      json = streamToString(inStream); // input stream to string
//    } catch (IOException ex) {
//      ex.printStackTrace();
//    }
//    return json;
//  }
    
    
    public String getGouvernorat(String latitude, String longitude) throws Exception {

        String place_name = "";
        String url = "https://api.mapbox.com/geocoding/v5/mapbox.places/" + longitude + "," + latitude + ".json?access_token=pk.eyJ1IjoidG91cmFiaSIsImEiOiJjazh0NWYwdHkwMmM5M2hvYnRyMGF4eHF6In0.g51jFys1t-3ZPuLnVHto1Q";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        con.connect();
        int responsecode = con.getResponseCode();

        if (responsecode != 200) {
            System.out.println("failed : " + responsecode);
        } else {

            System.out.println("ok int: " + responsecode);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JSONObject myResponse = new JSONObject(response.toString());
            // System.out.println("Response : \n" + myResponse.getString("place_name"));
            //String pageName = myResponse.getJSONObject("pageInfo").getString("pageName");
            JSONArray arr = myResponse.getJSONArray("features");
            //for (int i = 0; i < arr.length(); i++) {
            place_name = arr.getJSONObject(0).getString("place_name");
            System.out.println(place_name);
            // }
        }
        return place_name;
    }
    
    
}
