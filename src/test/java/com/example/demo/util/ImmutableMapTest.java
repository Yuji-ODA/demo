package com.example.demo.util;

import org.junit.jupiter.api.Test;

import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;


public class ImmutableMapTest {

    @Test
    void test() {
        Map<String, String> map = ImmutableMap.of("hoge", "huga");

        assertThat(map.get("hoge")).isEqualTo("huga");
    }
}
