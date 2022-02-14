package com.nnk.springboot.controllers;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ViewControllerITTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private static MockMvc mockMvc;

    @BeforeEach
    public void setupMockMvc(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @WithMockUser(username = "Paul", password = "Arrakis55!!", roles = "ADMIN")
    public void givenNoDistinctUrl_ReturnLoginPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(username = "Paul", password = "Arrakis55!!", roles = "ADMIN")
    public void givenAnUrl_ReturnLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "Paul", password = "Arrakis55!!", roles = "ADMIN")
    public void givenAnUrlThatLogOutAUser_ReturnLoginPage() throws Exception {
        mockMvc.perform(get("/logout"))
                .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(username = "Paul", password = "Arrakis55!!", roles = "ADMIN")
    public void givenAnUrl_ReturnHomePage() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "Paul", password = "Arrakis55!!", roles = "ADMIN")
    public void givenAnUrlWhenAErrorOccur_ReturnAModelWith403() throws Exception {
        mockMvc.perform(get("/error"))
                .andExpect(status().isOk());
    }
}
