package com.example.demo.lib;

import com.example.demo.controller.form.HogeSend;
import com.example.demo.controller.form.MyClass2;
import com.example.demo.controller.form.MyClassSend;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Range;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static com.example.demo.lib.Functions.eachProperty;
import static com.example.demo.lib.Functions.isValidUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.data.domain.Range.Bound.*;

class FunctionsTest {

    @Test
    void testDumpProperties() {
        ByteArrayResource resource = new ByteArrayResource("俺のファイル".getBytes(StandardCharsets.UTF_8));
        HogeSend hoge = new HogeSend("hoge", 100, null, new MyClassSend("kuso", 10, resource),
                Arrays.asList(new MyClass2("aho", 1, null), new MyClass2("baka", 2, null)));

        eachProperty(hoge, (names, value) -> {
            if ("myClasses".equals(names)) {
                List<?> list = (List<Object>)value;
                IntStream.range(0, list.size()).forEach(i -> {
                    Object obj = list.get(i);
                    if (obj instanceof MyClass2) {
                        System.out.println(names + '[' + i + ']' + ": " + value);
                    }
                });

                return;
            }
            System.out.println(names + ": " + value);
        });
    }

    @Test
    void testDumpProperties2() {
        ByteArrayResource resource = new ByteArrayResource("俺のファイル".getBytes(StandardCharsets.UTF_8));
        HogeSend hoge = new HogeSend("hoge", 100, null, new MyClassSend("kuso", 10, resource),
                Arrays.asList(new MyClass2("aho", 1, null), new MyClass2("baka", 2, null)));

        eachProperty(hoge, (name, value) -> {
            System.out.println(name + ": " + value);
        });
    }

    @Test
    void huga() throws Exception {

        BiFunction<Integer, Long, String> bifun = FunctionsTest::fun;
        BiFunction<Cls, Integer, String> bifun2 = Cls::hoge;

        JavaHuga huga = JavaHuga.of(1, 1.56F, "search");
        System.out.println(new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT).writeValueAsString(huga));
    }

    static <T1, T2> String fun(T1 t1, T2 t2) {
        return t1.toString() + t2;
    }

    @Test
    void uri() {
        String uriString = UriComponentsBuilder.fromHttpUrl("http://hoge.org")
                .path("huga#ほげ")
                .encode()
                .build()
                .toUriString();
        System.out.println(uriString);
    }

    private static final Pattern TRIM = Pattern.compile("(?:^\\h+|\\h+$)");

    private static Function<Matcher, String> replaceWith(String replace) {
        return matcher -> matcher.replaceAll(replace);
    }

    private static String trim(String src) {
        return Optional.ofNullable(src)
                .map(TRIM::matcher)
                .map(replaceWith(""))
                .orElse(src);
    }

    @Test
    void matches() {
        assertThat(trim(" ")).isEmpty();
        assertThat(trim("　")).isEmpty();
        assertThat(trim("hoge")).isEqualTo("hoge");
        assertThat(trim(" hoge ")).isEqualTo("hoge");
        assertThat(trim("hoge ")).isEqualTo("hoge");
        assertThat(trim(" hoge")).isEqualTo("hoge");
        assertThat(trim("hoge huga")).isEqualTo("hoge huga");
        assertThat(trim(" hoge huga ")).isEqualTo("hoge huga");
        assertThat(trim("hoge huga ")).isEqualTo("hoge huga");
        assertThat(trim(" hoge huga")).isEqualTo("hoge huga");
        assertThat(trim("ほげ")).isEqualTo("ほげ");
        assertThat(trim("　ほげ　")).isEqualTo("ほげ");
        assertThat(trim("ほげ　")).isEqualTo("ほげ");
        assertThat(trim("　ほげ")).isEqualTo("ほげ");
        assertThat(trim("ほげ　ふが")).isEqualTo("ほげ　ふが");
        assertThat(trim("　ほげ　ふが　")).isEqualTo("ほげ　ふが");
        assertThat(trim("ほげ　ふが　")).isEqualTo("ほげ　ふが");
        assertThat(trim("　ほげ　ふが")).isEqualTo("ほげ　ふが");
    }

    interface If {
        String HOGE = "hoge";
    }

    static class Cls implements If {
        String hoge(Integer t1) {
            return t1.toString();
        }
    }

    @Test
    void testIsValidUUID() {
        assertThat(isValidUUID("3F2504E0-4F89-11D3-9A0C-0305E82C3301")).isTrue();
        assertThat(isValidUUID("hoge")).isFalse();
        assertThat(isValidUUID(null)).isTrue();
    }

    @Test
    void testLocalDate() {
        Range<ChronoLocalDate> range = Range.leftOpen(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));
        assertFalse(range.contains(LocalDate.now().minusDays(2)));
        assertFalse(range.contains(LocalDate.now().minusDays(1)));
        assertTrue(range.contains(LocalDate.now()));
        assertTrue(range.contains(LocalDate.now().plusDays(1)));
        assertFalse(range.contains(LocalDate.now().plusDays(2)));

        Range<ChronoLocalDateTime<?>> range2 = Range.from(inclusive(LocalDateTime.now().minusDays(1)))
                .to(exclusive(LocalDateTime.now()));
        assertFalse(range2.contains(LocalDateTime.now()));

        Range<ChronoLocalDateTime<?>> range3 = Range.from(inclusive(LocalDateTime.now()))
                .to(unbounded());
    }
}
