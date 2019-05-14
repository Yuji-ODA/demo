package com.example.demo.app;


import com.example.demo.Preference;
import com.example.demo.domain.repository.BookRepository;
import com.example.demo.domain.service.BookService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({SpringExtension.class})
@WebMvcTest(controllers = BookController.class)
@WithMockUser
class BookControllerTest {

    @Autowired
    MockMvc mvc;

    Preference preference;

    @MockBean
    BookRepository bookRepository;

    BookService bookService;

//    @Autowired
//    Preference preference;

    @BeforeEach
    void setUp() {
        bookService = new BookService(bookRepository);
    }

    @Test
    void test() throws Exception {
        mvc.perform(get("/book").contentType("text/html"))
                .andExpect(view().name("book"))
                .andExpect(content().string(containsString("新規図書")))
                .andExpect(content().string(containsString("0.1")))
                .andExpect(status().isOk());
    }

    @Test
    void testPost() throws Exception {
        mvc.perform(post("/book").contentType("text/html")
                    .param("name","ABookName")
                    .param("price","10.12")
                )
                .andExpect(view().name("book"))
                .andExpect(status().isOk());

    }

    @AfterEach
    void tearDown() {
    }

    @TestConfiguration
    @EnableConfigurationProperties(Preference.class)
    public static class TestConfig {
        @Autowired
        Preference preference;

        @Autowired
        BookRepository bookRepository;

        @Bean
        public BookService bookService() {
            return new BookService(bookRepository);
        }

        @Bean
        public Preference preference() {
            return preference;
        }


    }
}
