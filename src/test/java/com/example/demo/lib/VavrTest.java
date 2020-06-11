package com.example.demo.lib;

import io.vavr.control.Option;
import org.junit.jupiter.api.Test;

public class VavrTest {

    @Test
    void test() {
        int i = Option.of(1)
                .map(x -> x * 2)
                .transform(o -> o.get() * 2);
        System.out.println(i);
    }
}
