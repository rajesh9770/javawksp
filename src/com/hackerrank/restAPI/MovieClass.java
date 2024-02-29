package com.hackerrank.restAPI;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class MovieClass {
    private static final String USER_AGENT = "Mozilla/5.0";
    public static void main(String args[]) {
        List<String> sortedTitles = getMovieTitles("Spiderman");

        System.out.println(sortedTitles);
    }

    public static List<String> getMovieTitles(String title) {
        String url = "https://jsonmock.hackerrank.com/api/movies/search/?Title=%s";
        url = String.format(url, title);

        List<String> result = new ArrayList<String>();

        URL obj;
        try {
            obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuffer response = new StringBuffer();

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response);
            result = getTitlesArray(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        Collections.sort(result);

        return result;
    }

    public static List<String> getTitlesArray(String str) {

        List<String> result = new ArrayList<String>();

        try {
            // Parse JSON
            Object obj1 = new JSONParser().parse(str);
            JSONObject jo = (JSONObject) obj1;
            JSONArray data = (JSONArray) jo.get("data");

            Iterator<JSONObject> it = data.iterator();

            while(it.hasNext()) {
                JSONObject next = it.next();
                String theTitle = (String) next.get("Title");
                result.add(theTitle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Collections.sort(result);

        return result;

    }

}
