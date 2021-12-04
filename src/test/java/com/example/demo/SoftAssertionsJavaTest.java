package com.example.demo;

import com.example.demo.app.dto.BookDto;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(SoftAssertionsExtension.class)
class SoftAssertionsJavaTest {
    @Test
    void testFailed() {
        var list = List.of(new BookDto("俺の本", 20000.0), new BookDto("君の本", 10.0));
        assertThat(list).hasSize(2);
        assertThat(list.get(0))
                .hasFieldOrPropertyWithValue("name", "俺の本に決まってんだろボケ")
                .hasFieldOrPropertyWithValue("price", 20000.0);
        assertThat(list.get(1))
                .hasFieldOrPropertyWithValue("name", "おめえの本じゃねえんだよカス")
                .hasFieldOrPropertyWithValue("price", 10.0);
    }

    @Test
    void testSoftlyFailed() {
        var list = List.of(new BookDto("俺の本", 20000.0), new BookDto("君の本", 10.0));
        assertSoftly(
                softly -> {
                    softly.assertThat(list).hasSize(2);
                    softly.assertThat(list.get(0))
                            .hasFieldOrPropertyWithValue("name", "俺の本ではない")
                            .hasFieldOrPropertyWithValue("price", 20000.0);
                    softly.assertThat(list.get(1))
                            .hasFieldOrPropertyWithValue("name", "君の本でもない")
                            .hasFieldOrPropertyWithValue("price", 10.0);
                }
        );
    }

    @Test
    void testSoftlyExtensionFailed(SoftAssertions softly) {
        var list = List.of(new BookDto("俺の本", 20000.0), new BookDto("君の本", 10.0));
        softly.assertThat(list).hasSize(2);
        softly.assertThat(list.get(0))
                .hasFieldOrPropertyWithValue("name", "俺の本ではない")
                .hasFieldOrPropertyWithValue("price", 20000.0);
        softly.assertThat(list.get(1))
                .hasFieldOrPropertyWithValue("name", "君の本でもない")
                .hasFieldOrPropertyWithValue("price", 10.0);
    }

    @Test
    void testAllFailed() {
        var list = List.of(new BookDto("俺の本", 20000.0), new BookDto("君の本", 10.0));
        assertAll(
                () -> assertThat(list).hasSize(2),
                () -> assertThat(list.get(0))
                        .hasFieldOrPropertyWithValue("name", "俺の本ではない")
                        .hasFieldOrPropertyWithValue("price", 20000.0),
                () -> assertThat(list.get(1))
                        .hasFieldOrPropertyWithValue("name", "君の本でもない")
                        .hasFieldOrPropertyWithValue("price", 10.0)
        );
    }

    @Test
    void testZipSatisfyFailed() {
        var list = List.of(new BookDto("俺の本", 20000.0), new BookDto("君の本", 10.0));
        assertThat(list)
                .hasSize(2)
                .zipSatisfy(upTo(1), (actual, index) -> {
                    if (index == 0)
                        assertThat(actual)
                                .hasFieldOrPropertyWithValue("name", "俺の本だ")
                                .hasFieldOrPropertyWithValue("price", 20000.0);
                    if (index == 1)
                        assertThat(actual)
                                .hasFieldOrPropertyWithValue("name", "君の本ではない")
                                .hasFieldOrPropertyWithValue("price", 10.0);
                });
    }

    static Iterable<Long> upTo(long stop) {
        return () -> LongStream.iterate(0, i -> i + 1).limit(stop + 1).iterator();
    }
}
