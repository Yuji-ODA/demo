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
@RequestMapping("books")
class BookController(private val bookService: BookService,
                     private val preference: Preference,
                     private val objectMapper: ObjectMapper,
                     private var repository: BookRepository) {

    fun defaultBookForm() = BookForm("新規図書", 0.1, "なんか知らん")

    @GetMapping
    fun book(webRequest: WebRequest?, model: Model): String {
        println(webRequest?.userPrincipal?.name)
        model.addAttribute("books", repository.findAll())
        model.addAttribute("bookForm", defaultBookForm())
        return "books"
    }

    @PostMapping
    fun createNewBook(webRequest: WebRequest?, @ModelAttribute @Validated bookForm: BookForm?,
                      bindingResult: BindingResult): ModelAndView {
        println(webRequest?.userPrincipal?.name)

        return if (bindingResult.hasErrors()) {
            ModelAndView("books", mapOf("books" to repository.findAll()) , HttpStatus.BAD_REQUEST)
        } else {
            bookService.run(BookCommand.fromBookForm(bookForm))
            ModelAndView("redirect:/books")
        }
    }

    @GetMapping(path = ["list"])
    fun bookList(webRequest: WebRequest?, model: Model?): String {
        println(webRequest?.userPrincipal?.name)

        repository.findAll().forEach {
            println(it)
        }
        return "books"
    }
}
