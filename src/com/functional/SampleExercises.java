package com.functional;

import com.functional.java8lambdas.Album;
import com.functional.java8lambdas.Artist;
import com.functional.java8lambdas.SampleData;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

/**
 * Created by Rajesh on 7/2/2017.
 */
public class SampleExercises {

    //make a string of artists names
    public static void test1(){
        String result = SampleData.getArtists().map(Artist::getName).collect(Collectors.joining(", ", "[", "]"));
        System.out.println(result);
    }

    //count the number of albums for each artists
    public static void test2(){
        Map<Artist, Long> result = SampleData.albums.collect(Collectors.groupingBy(album -> album.getMainMusician(), counting()));
        result.forEach((k,v) -> System.out.printf("%s : %s\n", k,v));
    }

    public static void test3(){
        Map<Artist, List<String>> result = SampleData.albums.collect(Collectors.groupingBy(album -> album.getMainMusician(), mapping(Album::getName, toList())));
        result.forEach((k,v) -> System.out.printf("%s : %s\n", k, v.stream().collect(Collectors.joining(", ", "[", "]"))));
    }

    public static void test4(){
        String result = SampleData.getArtists().map(Artist::getName).collect(new StringCollector(", ", "[", "]"));
        System.out.println(result);
    }
    public static void main(String[] args) {
        test4();

    }
}
