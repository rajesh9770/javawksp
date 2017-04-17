package com.functional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.TooManyListenersException;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SampleStreams {

    public static void generateRandonInts() {
        Random random = new Random();
        random.ints().limit(10).forEach(System.out::println);

        List<Integer> numbers = Stream.of(1,1, 1, 1, 2, 2, 2,2, 3, 3, 3, 4, 4,4, 4, 5, 5,5, 6, 6,7).collect(Collectors.toList());
        numbers.stream().sorted().distinct().limit(4).forEach(System.out::println);
    }

    public static long countLC(String str) {
        return str.chars().filter(c -> Character.isLowerCase(c)).count();
    }
    
    public static String getStrWithMaxLC(List<String> list) {
        return list.stream().max(Comparator.comparingLong(str -> str.chars().filter(c -> Character.isLowerCase(c)).count())).orElseGet( () -> new String("Not Found"));
    }
    public static void main(String[] args) {
        //generateRandonInts();
        //List<String> beginningWithNumber = Stream.of("a", "a1", "1A", "1D", "ac", "32").filter(str -> Character.isDigit(str.charAt(0))).collect(Collectors.toList());
        //beginningWithNumber.stream().forEach( str -> System.out.println(str));
        System.out.println(getStrWithMaxLC(Arrays.asList("aa", "bbv", "cca")));
        System.out.println(getStrWithMaxLC(new ArrayList<String>()));

    }

}
