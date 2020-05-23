package com.example.demo.lib;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;

class OptionalUtilTest {

    @Test
    void test() {
        Optional<String> x = Optional.of("X");
//        Optional<String> x = Optional.empty();
//        Optional<String> y = Optional.of("Y");
        Optional<String> y = Optional.empty();
        Optional<String> z = Optional.of("Z");
//        Optional<String> z = Optional.empty();

        Function<String, Function<String, Supplier<String>>> zyExist = x.<Function<String, Function<String, Supplier<String>>>>map(
                a1 -> a2 -> a3 -> () -> String.join(" and ", a1, a2, a3)
        ).orElse(
                a1 -> a2 -> () -> String.join(" and ", a1, a2)
        );

        Function<String, Supplier<String>> z_Exist = x.<Function<String, Supplier<String>>>map(
                a1 -> a2 -> () -> String.join(" and ", a1, a2)
        ).orElse(
                a1 -> () -> a1
        );

        Function<String, Supplier<String>> _yExist = x.<Function<String, Supplier<String>>>map(
                a1 -> a2 -> () -> String.join(" and ", a1, a2)
        ).orElse(
                a1 -> () -> a1
        );

        Supplier<String> __Exist = x.<Supplier<String>>map(
                a1 -> () -> a1
        ).orElse(
                () -> "none"
        );

        String result = z.map(y.map(zyExist).orElse(z_Exist)).orElse(y.map(_yExist).orElse(__Exist)).get();

        System.out.println(result);
    }

    @Test
    void sequence() {

        List<Optional<Integer>> l = Arrays.asList(Optional.of(1), Optional.of(2));

        Optional<List<Integer>> result = OptionalUtil.sequence(l);

        assertThat(result).isNotEmpty();
        assertThat(result.get()).containsExactly(1, 2);
    }

    @Test
    void sequenceWhenContainsEmpty() {

        List<Optional<Integer>> l = Arrays.asList(Optional.of(1), Optional.of(2), Optional.empty());

        Optional<List<Integer>> result = OptionalUtil.sequence(l);

        assertThat(result).isEmpty();
    }

    @Test
    void traverse() {

        List<Optional<Integer>> l = Arrays.asList(Optional.of(1), Optional.of(2));

        Optional<List<Integer>> result = OptionalUtil.traverse(l, x -> Optional.of(x * 2));

        assertThat(result).isNotEmpty();
        assertThat(result.get()).containsExactly(2, 4);
    }

    @Test
    void traverseWhenContainsEmpty() {

        List<Optional<Integer>> l = Arrays.asList(Optional.of(1), Optional.of(2), Optional.empty());

        Optional<List<Integer>> result = OptionalUtil.traverse(l, x -> Optional.of(x * 2));

        assertThat(result).isEmpty();
    }

    @Test
    void liftUnaryOperator() {
        UnaryOperator<Integer> doubleInteger = x -> 2 * x;
        UnaryOperator<Optional<Integer>> doubleOptional = OptionalUtil.lift(doubleInteger);
        Optional<Integer> result = doubleOptional.apply(Optional.of(10));

        assertThat(result).isEqualTo(Optional.of(20));
    }

    @Test
    void liftBinaryOperator() {
        BinaryOperator<Optional<Integer>> sumOptional = OptionalUtil.lift(Integer::sum);
        Optional<Integer> result = sumOptional.apply(Optional.of(1), Optional.of(2));

        assertThat(result).isEqualTo(Optional.of(3));
    }

}
