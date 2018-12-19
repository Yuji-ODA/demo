package com.example.demo.app;

import com.example.demo.app.form.BookForm;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/book")
public class BookController {
    private String getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated())
            return auth.getName();
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
        System.out.println(authentication == null);
        System.out.println(getUsername());
        return "book";
    }

    @PostMapping(path = "")
    public ModelAndView createNewBook(Authentication authentication, @Validated BookForm bookForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("book", HttpStatus.BAD_REQUEST);
        }

        return new ModelAndView("book");
    }

}
