package com.example.demo.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestOperations

@RestController
@RequestMapping("api")
internal final class ApiController(private val restOperations: RestOperations) {
    init {
        System.err.println(restOperations)
    }
    @GetMapping("hoge", "huga")
    fun hoge() = "hoge".run {
        reversed().uppercase()
    }
}
