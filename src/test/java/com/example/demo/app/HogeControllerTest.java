package com.example.demo.app;


import com.example.demo.app.service.OreoreService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({SpringExtension.class})
@WebMvcTest(controllers = HogeController.class)
@WithMockUser
class HogeControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    OreoreService oreoreService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void test() throws Exception {
        doReturn("オレオレ").when(oreoreService).getMessage();

        mvc.perform(get("/hoge").contentType("text/html"))
                .andExpect(view().name("hoge"))
                .andExpect(content().string(containsString("HOGE")))
                .andExpect(content().string(containsString("オレオレ")))
                .andExpect(status().isOk());
    }

    @AfterEach
    void tearDown() {
        MockitoAnnotations.initMocks(this);
    }
}
