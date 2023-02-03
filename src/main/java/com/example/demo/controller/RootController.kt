package com.example.demo.controller

import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RootController {
    @GetMapping("/")
    fun root(authentication: Authentication?) = "Welcome ${authentication?.name}!!"
}