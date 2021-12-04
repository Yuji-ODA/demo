package com.example.demo

import com.example.demo.app.dto.BookDto
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.Test

class SoftAssertionsTest {

    @Test
    fun testFailed() {
        val list = listOf(BookDto("俺の本", 20000.0), BookDto("君の本", 10.0))

        assertThat(list).hasSize(2)
        assertThat(list[0])
            .hasFieldOrPropertyWithValue("name", "俺の本")
            .hasFieldOrPropertyWithValue("price", 20001.0)
        assertThat(list[1])
            .hasFieldOrPropertyWithValue("name", "君の本")
            .hasFieldOrPropertyWithValue("price", 10.1)
    }

    @Test
    fun testSoftlyFailed() {
        val list = listOf(BookDto("俺の本", 20000.0), BookDto("君の本", 10.0))

        assertSoftly { it.apply {
            assertThat(list).hasSize(2)
            assertThat(list[0])
                .hasFieldOrPropertyWithValue("name", "俺の本")
                .hasFieldOrPropertyWithValue("price", 20001.0)
            assertThat(list[1])
                .hasFieldOrPropertyWithValue("name", "君の本")
                .hasFieldOrPropertyWithValue("price", 10.1)
        }}
    }
}