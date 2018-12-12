package com.example.demo.app;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({SpringExtension.class})
@WebMvcTest(controllers = BookController.class, secure = false)
class BookControllerTest {

    @Autowired
    MockMvc mvc;

    @BeforeEach
    void setUp() {
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
}
