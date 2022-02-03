package com.example.demo.app.dto

import com.example.demo.ModelMapperConfig
import com.example.demo.domain.model.Book
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig

@SpringJUnitConfig(classes = [JacksonAutoConfiguration::class, ModelMapperConfig::class])
class BookDtoTest {

    @Autowired
    lateinit var builder: Jackson2ObjectMapperBuilder

    @Autowired
    lateinit var modelMapper: ModelMapper

    lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setup() {
        objectMapper = builder.build()
    }

    @Test
    fun testSerialize() {
        objectMapper?.writeValueAsString(BookDto("おらが村の歴史", 12000.0, ""))
            .let(::println)
    }

    @Test
    fun testDeserialize() {
        "{\"name\":\"おらが村の歴史\",\"price\":120000.0,\"isbn_code\":\"dkjflasdlkdsj\"}"
            .let(readValue(objectMapper, BookDto::class.java))
            .let(::println)
    }

    fun <T> readValue(mapper: ObjectMapper?, cls: Class<T>?): (String) -> T? = { src ->
        cls?.let {
            mapper?.readValue(src, it)
        }
    }

    @Test
    fun modelMapperTest() {
        val bookDto = objectMapper.readValue("{\"name\":\"おらが村の歴史\",\"price\":120000.0,\"isbn_code\":\"978-4309289052\"}", BookDto::class.java)

        println(bookDto)

        val bookEntity = Book()

        modelMapper.map(bookDto, bookEntity)
        println(bookEntity)

        println(modelMapper.map(bookDto, Book::class.java))
    }
}
