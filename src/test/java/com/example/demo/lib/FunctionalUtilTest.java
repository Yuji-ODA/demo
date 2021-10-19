package com.example.demo.lib;

import lombok.Value;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.example.demo.lib.FunctionalUtil.isIdentical;
import static java.util.function.Predicate.isEqual;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FunctionalUtilTest {

    List<String> list;
    Set<String> set;

    @BeforeEach
    void setUpEach() {
        list = Arrays.asList("I", "am", "john");
        set = new HashSet<>(list);
    }

    @Test
    void foldLeft() {
        String sum = FunctionalUtil.foldLeft(list, "", (a, b) -> a + " " + b);

        assertThat(sum).isEqualTo(" I am john");
    }

    @Test
    void foldRight() {

        String sum = FunctionalUtil.foldRight(list, "", (b, a) -> a + " " + b);

        assertThat(sum).isEqualTo(" john am I");

    }

    @Test
    void foldRightForSet() {

        Set<Integer> values = IntStream.range(0, 100).boxed().collect(Collectors.toSet());

        int sum = FunctionalUtil.foldRight(values, 0, Integer::sum);

        assertThat(sum).isEqualTo(99 * 50);
    }

    @Test
    void foldRightForSetResultsINStackOverflow() {

        Set<Integer> values = IntStream.range(0, 1000000).boxed().collect(Collectors.toSet());

        assertThatThrownBy(() -> FunctionalUtil.foldRight(values, 0, Integer::sum))
                .isInstanceOf(StackOverflowError.class);
    }

    @Test
    void testEqualTo() {
        Hoge hoge = new Hoge("hoge");
        Hoge huga1 = new Hoge("huga");
        Hoge huga2 = new Hoge("huga");

        assertThat(Stream.of(hoge, huga1).filter(isEqual(huga2)).findFirst())
                .isPresent()
                .contains(huga1);

        assertThat(Stream.of(hoge, huga1).filter(isIdentical(huga2)).findFirst())
                .isEmpty();
    }

    @Value
    static class Hoge {
        String huga;
    }
}