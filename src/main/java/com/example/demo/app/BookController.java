package com.example.demo.app;

import com.example.demo.GenericClass;
import com.example.demo.Preference;
import com.example.demo.app.form.BookForm;
import com.example.demo.domain.service.BookService;
import com.example.demo.domain.service.command.BookCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@RequestMapping(path = "/book")
@RequiredArgsConstructor
@EnableConfigurationProperties(Preference.class)
public class BookController {

    private final BookService bookService;

    private final Preference preference;

    private final GenericClass<String> stringGenericClass;

    private String getUsername(WebRequest webRequest) {
        Principal principal = webRequest.getUserPrincipal();
        if (principal != null)
            return webRequest.getUserPrincipal().getName();
        else
            return null;
    }

    @ModelAttribute
    public BookForm bookForm() {
        BookForm bookForm = new BookForm();
        bookForm.setName("新規図書");
        bookForm.setPrice(0.1);

        return bookForm;
    }

    @GetMapping(path = "")
    public String book(Authentication authentication, BookForm bookForm, Model model) {
        System.out.println(preference.getTopPage().getListLength());
        return "book";
    }

    @PostMapping(path = "")
    public ModelAndView createNewBook(WebRequest webRequest, @Validated BookForm bookForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("book", HttpStatus.BAD_REQUEST);
        }

        bookService.run(BookCommand.fromBookForm(bookForm));

        return new ModelAndView("book");
    }

}
