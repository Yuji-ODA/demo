package com.example.demo.controller

import com.example.demo.Preference
import com.example.demo.app.form.BookForm
import com.example.demo.domain.repository.BookRepository
import com.example.demo.domain.service.BookService
import com.example.demo.domain.service.command.BookCommand
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("book")
class BookController(private val bookService: BookService,
                     private val preference: Preference,
                     private val objectMapper: ObjectMapper,
                     private var repository: BookRepository) {

    @ModelAttribute
    fun bookForm() = BookForm().apply {
        name = "新規図書"
        price = 0.1
    }

    @GetMapping
    fun book(webRequest: WebRequest?, bookForm: BookForm?, model: Model): String {
        println(webRequest?.userPrincipal?.name)
        return "book"
    }

    @PostMapping
    fun createNewBook(webRequest: WebRequest?, @Validated bookForm: BookForm?,
                      bindingResult: BindingResult): ModelAndView? {
        println(webRequest?.userPrincipal?.name)

        if (bindingResult.hasErrors()) {
            return ModelAndView("book", HttpStatus.BAD_REQUEST)
        }
        bookService.run(BookCommand.fromBookForm(bookForm))
        return ModelAndView("book")
    }

    @GetMapping(path = ["list"])
    fun bookList(webRequest: WebRequest?, model: Model?): String? {
        println(webRequest?.userPrincipal?.name)

        repository.findAll().forEach {
            println(it)
        }
        return "book"
    }
}
