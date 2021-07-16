package com.example.demo;

import com.example.demo.lib.Huga;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class HogeTest {
    @Test
    void hoge() throws Exception {
        Pattern pat = Pattern.compile("hoge(\\w+)");

        Matcher matcher = pat.matcher("hogehuga");

        if (matcher.find()) {
            System.out.println(matcher.group(1));
        }

        Huga huga = new Huga(1, 1F, "");
        System.out.println(huga.getId());

        System.out.println(Huga.Companion.dead());
        System.out.println(Huga.Companion.dead().getBeType());

        new ObjectMapper().writeValue(System.out, Huga.Companion.dead());
    }
}
