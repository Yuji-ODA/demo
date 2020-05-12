package com.example.demo;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class HogeTest {
    @Test
    void hoge() {
        Pattern pat = Pattern.compile("hoge(\\w+)");

        Matcher matcher = pat.matcher("hogehuga");

        if (matcher.find()) {
            System.out.println(matcher.group(1));
        }
    }
}
