package com.functional;

/**
 * Created by Rajesh on 7/2/2017.
 */
public class StringCombiner {
    private StringBuilder builder;
    private String delimiter;
    private String prefix;
    private String suffix;

    public StringCombiner(String delimiter, String prefix, String suffix){
        builder = new StringBuilder();
        this.delimiter = delimiter;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public StringCombiner add(String element){
        if(builder.length() == 0){
            builder.append(prefix);
        }else{
            builder.append(delimiter);
        }
        builder.append(element);
        return this;
    }

    public StringCombiner merge(StringCombiner other){
        System.out.println("this" + builder.toString() + " " + other.builder.toString());
        builder.append(other.builder);
        return this;
    }

    @Override
    public String toString() {
        return builder + suffix;
    }
}
