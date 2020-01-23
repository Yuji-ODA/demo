package com.example.demo.lib;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

class OptionalUtilTest {

    @Test
    void test() {
        Optional<String> x = Optional.of("X");
//        Optional<String> x = Optional.empty();
//        Optional<String> y = Optional.of("Y");
        Optional<String> y = Optional.empty();
        Optional<String> z = Optional.of("Z");
//        Optional<String> z = Optional.empty();

        val zyExist = x.<Function<String, Function<String, Supplier<String>>>>map(
                a1 -> a2 -> a3 -> () -> String.join(" and ", a1, a2, a3)
        ).orElse(
                a1 -> a2 -> () -> String.join(" and ", a1, a2)
        );

        val z_Exist = x.<Function<String, Supplier<String>>>map(
                a1 -> a2 -> () -> String.join(" and ", a1, a2)
        ).orElse(
                a1 -> () -> a1
        );

        val _yExist = x.<Function<String, Supplier<String>>>map(
                a1 -> a2 -> () -> String.join(" and ", a1, a2)
        ).orElse(
                a1 -> () -> a1
        );

        val __Exist = x.<Supplier<String>>map(
                a1 -> () -> a1
        ).orElse(
                () -> "none"
        );

        String result = z.map(y.map(zyExist).orElse(z_Exist)).orElse(y.map(_yExist).orElse(__Exist)).get();

        System.out.println(result);
    }
}