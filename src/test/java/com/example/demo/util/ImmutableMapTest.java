package com.example.demo.util;

import com.example.demo.lib.ImmutableMap;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;


public class ImmutableMapTest {

    @Test
    void test() {
        Map<String, String> map = ImmutableMap.of("hoge", "huga");

        assertThat(map.get("hoge")).isEqualTo("huga");
    }

    @Test
    void hoge() {
        System.out.println(LongStream.range(0, 1000000000).parallel().reduce(0, Long::sum));
        System.out.println(LongStream.range(0, 1000000000).reduce(0, Long::sum));
    }
}
