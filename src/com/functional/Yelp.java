package com.functional;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Yelp {
	  private List<Restaurant> restaurants;
	  private List<Rating> ratings;

	  public Yelp(List<Restaurant> restaurants, List<Rating> ratings) {
	    this.restaurants = restaurants;
	    this.ratings = ratings;
	  }

	  /*
	   * Returns list of Restaurant within radius.
	   *
	   *  latitude: double
	   *  longitude: double
	   *  radius: kilometer in int
	   *  diningHour: If null, find any restaurant in radius.
	   *              Otherwise return list of open restaurants at specified hour.
	   *  sortByRating: If true, sort result in descending order,
	   *                highest rated first.
	   */
	  public List<Restaurant> find(double latitude, double longitude, double radius,
	      int diningHour, boolean sortByRating) {
		  
		  Stream<Restaurant> filter = restaurants.stream().filter(restaurant -> {return distance(restaurant.latitude, latitude, restaurant.longitude, longitude) < radius;});
		  if (sortByRating){
			  List<Restaurant> collect = filter.sorted((r1, r2) -> 
			  Double.compare(r2.rating, r1.rating)).collect(Collectors.toList());
			  return collect;
		  }else{
			  List<Restaurant> collect = filter.sorted((r1, r2) -> 
			  Double.compare(distance(r1.latitude, latitude, r1.longitude, longitude), distance(r2.latitude, latitude, r2.longitude, longitude))).collect(Collectors.toList());
			  return collect;
		  }
	  }

	  public static double distance(double lat1, double lat2, double lon1,
		        double lon2) {

		    final int R = 6371; // Radius of the earth

		    Double latDistance = Math.toRadians(lat2 - lat1);
		    Double lonDistance = Math.toRadians(lon2 - lon1);
		    Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
		            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
		            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		    Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		    double distance = R * c * 1000; // convert to meters

		    distance = Math.pow(distance, 2);

		    return Math.sqrt(distance);
		}
	  
	  @Override
	public String toString() {	
		restaurants.forEach(restaurants -> {System.out.println(restaurants.name);});
		return "";
	}
	  public static class Restaurant {
	    private double latitude;
	    private double longitude;
	    private String id;
	    private String name;
	    private int openHour;   /* in [0-23] */
	    private int closeHour;  /* in [0-23] */
		private double rating;

	    public Restaurant(String name, String id, double latitude, double logitute, double rating) 
	    { 
	    	this.latitude = latitude;
	    	this.longitude = logitute;
	    	this.id = id;
	    	this.name = name;	    	
	    	this.rating = rating;
	    }  
	  }

	  public static class Rating {
	    private int id;
	    private int rating;      /* in [1-5] */

	    public Rating(int id, int rating) { 
	    	this.id = id;
	    	this.rating = rating;
	    			
	    }      
	  }

	  public static String [] parse(String csLine){
		  return csLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");  
	  }
	  
	  public static void main(String[] args) {
		List<Restaurant> restaurants = null;
	    List<Rating> ratings = new ArrayList<Rating>();
	    
	    File inputF= new File("C:\\Users\\Rajesh\\Documents\\output.csv");
	    InputStream inputFS;
		try {
			inputFS = new FileInputStream(inputF);
			BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
		    restaurants = br.lines().skip(1).map(line -> {
		    		String [] tokens = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
		    		Restaurant ret  = new Restaurant(tokens[5].substring(0, Math.min(10, tokens[5].length())), tokens[0], Double.parseDouble(tokens[3]), Double.parseDouble(tokens[4]), Double.parseDouble(tokens[7]));
		    		return ret;
		    }).collect(Collectors.toList());
		    br.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    Yelp y = new Yelp(restaurants, ratings);
	    
	    double latitude = 40.3983526;
	    double longitute = -80.0824517;
	    List<Restaurant> find = y.find(latitude, longitute, 105, 0, true);
	    find.stream().forEach(r -> {System.out.printf("%-15s\t%.5f\t%.5f\t%.3f\t%.2f\n", r.name, r.latitude, r.longitude, 
	    		distance(r.latitude, latitude, r.longitude, longitute),
	    		r.rating );});
	    
	  }
	}