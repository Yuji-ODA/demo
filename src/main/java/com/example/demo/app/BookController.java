package com.example.demo.app;

import com.example.demo.app.form.BookForm;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping(path = "/book")
public class BookController {

    @ModelAttribute
    public BookForm bookForm() {
        BookForm bookForm = new BookForm();
        bookForm.setName("新規図書");
        bookForm.setPrice(0.1);

        return bookForm;
    }

    @GetMapping(path = "")
    public String book(Principal principal, BookForm bookForm, Model model) {
        return "book";
    }

    @PostMapping(path = "")
    public ModelAndView createNewBook(Principal principal, @Validated BookForm bookForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("book", HttpStatus.BAD_REQUEST);
        }

        return new ModelAndView("book");
    }

}
