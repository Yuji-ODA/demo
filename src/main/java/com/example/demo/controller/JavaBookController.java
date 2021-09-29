package com.example.demo.controller;

import com.example.demo.Preference;
import com.example.demo.app.form.BookForm;
import com.example.demo.domain.repository.BookRepository;
import com.example.demo.domain.service.BookService;
import com.example.demo.domain.service.command.BookCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.codelibs.neologd.ipadic.lucene.analysis.ja.JapaneseTokenizer;
import org.codelibs.neologd.ipadic.lucene.analysis.ja.dict.UserDictionary;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping(path = "/java-book")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JavaBookController {

    BookService bookService;

    Preference preference;

    ObjectMapper objectMapper;

    BookRepository repository;

    private String getUsername(WebRequest webRequest) {
        Principal principal = webRequest.getUserPrincipal();
        if (principal != null)
            return webRequest.getUserPrincipal().getName();
        else
            return null;
    }

    @ModelAttribute
    public BookForm bookForm() {
        return new BookForm() {{
            setName("新規図書");
            setPrice(0.5);
        }};
    }

    @Data
    static class Hoge {
        private Float huga;
    }


    @GetMapping
    public String book(Authentication authentication, BookForm bookForm, Model model) {
        try {
            Hoge hoge = objectMapper.readValue("{\"huga\": \"1.25\"}}", Hoge.class);
            System.out.println(hoge);
        } catch (Exception e) {
            System.out.println(e);
        }
        UserDictionary userDict = null;
        JapaneseTokenizer.Mode mode = JapaneseTokenizer.Mode.NORMAL;
        System.out.println(preference.getTopPage().getListLength());
        model.addAttribute("hoge", "<div>DIV</div>");
        return "book";
    }

    @PostMapping
    public ModelAndView createNewBook(WebRequest webRequest, @Validated BookForm bookForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("book", HttpStatus.BAD_REQUEST);
        }

        bookService.run(BookCommand.fromBookForm(bookForm));

        return new ModelAndView("book");
    }

    @GetMapping(path = "list")
    public String bookList(Authentication authentication, Model model) {
        repository.findAll().forEach(System.out::println);
        return "book";
    }
}
