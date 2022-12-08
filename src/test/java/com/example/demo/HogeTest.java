package com.example.demo;

import com.example.demo.lib.Huga;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

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

//        new ObjectMapper().writeValue(System.out, Huga.Companion.dead());

        assertThat(1).isEqualTo(1);
    }

    @DisplayName("翌日テスト")
    @ParameterizedTest(name = "{0}の翌日は{1}であること")
    @CsvSource(value = {"2021-10-31, 2021-11-01", "2022-02-28, 2022-03-01"})
    void testLocalDateParameterPlus1Day(LocalDate given, LocalDate expected) {
        System.out.println(given);
        System.out.println(expected);

        // when
        LocalDate actual = given.plusDays(1);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> intListProvider() {
        return Stream.of(
                arguments(Arrays.asList(1, 2, 3)),
                arguments(Arrays.asList(4, 5, 5))
        );
    }

    @ParameterizedTest(name = "引く数が{0}である")
    @MethodSource("com.example.demo.HogeTest#intListProvider")
    void arrayTest(List<Integer> ints) {
        System.out.println(ints);
    }
}
