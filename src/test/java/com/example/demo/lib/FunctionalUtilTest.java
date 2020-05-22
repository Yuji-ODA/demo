package com.example.demo.lib;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FunctionalUtilTest {

    List<String> list;

    @BeforeEach
    void setUpEach() {
        list = Arrays.asList("I", "am", "john");
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
}