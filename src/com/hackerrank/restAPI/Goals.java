package com.hackerrank.restAPI;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class Goals {

    public static int getTotalGoals(String team, int year) throws UnsupportedEncodingException {
        team = URLEncoder.encode(team, StandardCharsets.UTF_8.toString());
        String urla = "https://jsonmock.hackerrank.com/api/football_matches?year=%d&team1=%s&page=%d";
        String urlb = "https://jsonmock.hackerrank.com/api/football_matches?year=%d&team2=%s&page=%d";
        int total = 0;
        try {

            int page = 0;
            int totalPages;

            do {
                StringBuffer stringBuffer = get(String.format(urla, year, team, ++page));
                int[] team1goals = parse(stringBuffer, "team1goals");
                totalPages = team1goals[1];
                total += team1goals[0];
            }while(page<totalPages);


            page = 0;

            do {
                StringBuffer stringBuffer = get(String.format(urlb, year, team, ++page));
                int[] team2goals = parse(stringBuffer, "team2goals");
                totalPages = team2goals[1];
                total += team2goals[0];
            }while(page<totalPages);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return total;
    }

    public static StringBuffer get(String urlStr) throws IOException {
        long start = System.currentTimeMillis();
        URL url = new URL(urlStr);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        // optional default is GET
        urlConnection.setRequestMethod("GET");
        int responseCode = urlConnection.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        StringBuffer response = new StringBuffer();

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        System.out.println(System.currentTimeMillis() - start);
        return response;
    }

    public static int[] parse(StringBuffer buff, String field) throws ParseException {
        // Parse JSON
        JSONObject jo = (JSONObject) new JSONParser().parse(buff.toString());
        long totalPages = (Long) jo.get("total_pages");
        JSONArray data = (JSONArray) jo.get("data");
        Iterator<JSONObject> it = data.iterator();
        int goals = 0;
        while(it.hasNext()){
            JSONObject next = it.next();
            Object o = next.get(field);
            goals += Integer.parseInt((String) o);
        }
        return new int[]{goals, (int) totalPages};
    }

    public static void main(String[] args) {
        int barcelona = 0;
        try {
            //barcelona = getTotalGoals("Barcelona", 2011);
            int numDraws = getNumDraws(2011);
            System.out.println(numDraws);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(barcelona);
    }

    public static int getNumDraws(int year) {
        String urla = "https://jsonmock.hackerrank.com/api/football_matches?year=%d&page=%d";
        int total = 0;
        try {
            int page = 0;
            int totalPages;
            do {
                StringBuffer stringBuffer = get(String.format(urla, year, ++page));
                int[] draws = parseDraws(stringBuffer);
                totalPages = draws[1];
                total += draws[0];
            } while (page < totalPages);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return total;
    }

    private static int[] parseDraws(StringBuffer buff) throws ParseException {
        JSONObject jo = (JSONObject) new JSONParser().parse(buff.toString());
        long totalPages = (Long) jo.get("total_pages");
        JSONArray data = (JSONArray) jo.get("data");
        Iterator<JSONObject> it = data.iterator();
        int draws = 0;
        while(it.hasNext()){
            JSONObject next = it.next();
            if(Integer.parseInt((String) next.get("team1goals")) == Integer.parseInt((String) next.get("team2goals"))){
                ++draws;
            }
        }
        return new int[]{draws, (int) totalPages};
    }
}
