package com.example.demo.controller

import com.example.demo.Preference
import com.example.demo.app.form.BookForm
import com.example.demo.domain.service.BookService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.context.request.WebRequest

@Controller
@RequestMapping("book")
class BookController(private val bookService: BookService,
                     private val preference: Preference,
                     private val objectMapper: ObjectMapper) {

    private fun WebRequest.username(): String? = userPrincipal?.name

    private val String.lastChar: Char
        get() = this[length - 1]

    @ModelAttribute
    fun bookForm(): BookForm = BookForm().apply {
        name = "新規図書"
        price = 0.1
    }

}

data class Hoge(val huga: Float)
