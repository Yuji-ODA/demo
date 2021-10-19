package com.example.demo.app.dto

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig

@SpringJUnitConfig(classes = [JacksonAutoConfiguration::class])
class BookDtoTest {

    @Autowired
    var builder: Jackson2ObjectMapperBuilder? = null

    var mapper: ObjectMapper? = null

    @BeforeEach
    fun setup() {
        mapper = builder?.build()
    }

    @Test
    fun testSerialize() {
        mapper?.writeValueAsString(BookDto("おらが村の歴史", 12000.0))
            .let(::println)
    }

    @Test
    fun testDeserialize() {
        "{\"name\":\"おらが村の歴史\",\"price\":120000.0}"
            .let(readValue(mapper, BookDto::class.java))
            .let(::println)
    }

    fun <T> readValue(mapper: ObjectMapper?, cls: Class<T>?): (String) -> T? = { src ->
        cls?.let {
            mapper?.readValue(src, it)
        }
    }
}