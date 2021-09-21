package com.example.demo.domain.service.command;

import com.example.demo.app.form.BookForm;
import com.example.demo.domain.model.Book;
import com.example.demo.domain.repository.BookRepository;
import com.example.demo.domain.service.BookService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BookCommand implements TransactionCommand<BookService> {

    private final Book book;

    public static BookCommand fromBookForm(BookForm bookForm) {
        return new BookCommand(Book.of(bookForm.getName(), bookForm.getPrice()));
    }

    @Override
    public void excute(BookService service) {
        BookRepository bookRepository = service.getBookRepository();
        bookRepository.save(this.book);
    }
}
