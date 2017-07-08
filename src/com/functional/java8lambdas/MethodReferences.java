package com.functional.java8lambdas;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.nio.charset.Charset.defaultCharset;
import static java.util.stream.Collectors.groupingBy;
import static com.functional.java8lambdas.Utilities.countWords;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class MethodReferences {

    public Map<Artist, List<Album>> albumsByArtist(Stream<Album> albums) {
        return albums.collect(groupingBy(Album::getMainMusician));
    }

    private static final Pattern SPACES = Pattern.compile("\\w+");

    public static Map<String, Long> countWordsIn(Path path) throws IOException {
        Stream<String> words = Files.readAllLines(path, defaultCharset())
                                    .stream()
                                    .flatMap(SPACES::splitAsStream);

        return countWords(words);
    }

    public static void main(String[] args) {
        SampleData.getThreeArtists();

    }

}
