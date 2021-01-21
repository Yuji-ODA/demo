package com.example.demo.domain.repository;

import com.example.demo.domain.model.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    @Sql(statements = "INSERT INTO books(id, name, price) values(100000, 'Siring-framework step by step', '10')")
    void test() throws Exception {

        Optional<Book> book = bookRepository.findById(100000L);

        assertThat(book.isPresent()).isTrue();

    }
}
