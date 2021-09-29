package com.example.demo.controller

import com.example.demo.lib.Huga
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestOperations
import java.util.*

@RestController
@RequestMapping("api")
internal final class ApiController(private val restOperations: RestOperations) {
    init {
        System.err.println(restOperations)
    }
    @GetMapping("hoge", "huga")
    fun hoge() = "hoge".run {
        val doubleInt: (Int) -> Int = { it * 2 }
        val doubleLong: (Long) -> Long = { it * 2 }
        val sumInt: (Int, Int) -> Int = { x, y -> x + y }
        val i: Optional<Int> = Optional.of(1)
            .map(doubleInt)
        val u: Unit = Unit
        val p: Pair<Unit, String> = u to "hoge"
        val n: Pair<Unit, String> = p ?: throw Exception()
        val i2: Int = if (null === null) 1
        else 2
        reversed().uppercase()
    }

    @GetMapping("baka")
    fun baka() = jacksonObjectMapper().writeValueAsString(Huga.dead())
}
