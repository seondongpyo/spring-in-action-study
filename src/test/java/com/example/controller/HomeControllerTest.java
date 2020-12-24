package com.example.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc; // MockMvc 주입

    @Test
    public void testHomepage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/")) // HTTP GET '/' 요청
                .andExpect(MockMvcResultMatchers.status().isOk()) // HTTP 200
                .andExpect(MockMvcResultMatchers.view().name("home")) // 뷰의 이름은 home
                .andExpect(MockMvcResultMatchers.content().string(
                        Matchers.containsString("Welcome to Taco Cloud") // 뷰 콘텐츠에 'Welcome To Taco Cloud' 포함
                ));
    }
}