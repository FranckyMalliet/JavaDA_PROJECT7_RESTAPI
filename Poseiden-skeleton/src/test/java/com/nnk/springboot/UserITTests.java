package com.nnk.springboot;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.IUserService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class UserITTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private static MockMvc mockMvc;

    @Autowired
    private IUserService iUserService;

    @BeforeEach
    public void setupMockMvc(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @WithMockUser(username = "Paul", password = "Arrakis55!!", roles="ADMIN")
    public void givenAnUrl_ReturnPathToAPageWithAllUsers() throws Exception {
        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "Paul", password = "Arrakis55!!", roles="ADMIN")
    public void givenAnUrl_ReturnPathToAPageAddingUsers() throws Exception {
        mockMvc.perform(get("/user/add"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "Paul", password = "Arrakis55!!", roles="ADMIN")
    public void givenAValidUser_AddItToDatabase_ReturnPathToAPageWithAllUsers() throws Exception{
        User user = new User();
        user.setUsername("Paul");
        user.setFullname("Atreides");
        user.setRole("user");
        user.setPassword("Arrakis55!!");
        user.setEnabled(false);

        iUserService.addNewUserToDatabase(user);
        Assertions.assertNotNull(iUserService.findById(user.getId()));
        Assertions.assertEquals(iUserService.findById(user.getId()).getUsername(), "Paul", "Paul");

        mockMvc.perform(post("/user/validate"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk());

        iUserService.deleteById(user.getId());
    }

    @Test
    @WithMockUser(username = "Paul", password = "Arrakis55!!", roles="ADMIN")
    public void givenAnId_ReturnPathToPageWithASpecificUser() throws Exception{
        User user = new User();
        user.setUsername("Paul");
        user.setFullname("Atreides");
        user.setRole("user");
        user.setPassword("Arrakis55!!");
        user.setEnabled(false);

        iUserService.addNewUserToDatabase(user);
        Assert.assertNotNull(iUserService.findById(user.getId()));

        mockMvc.perform(get("/user/update/{id}", user.getId()))
                .andExpect(status().isOk());

        iUserService.deleteById(user.getId());
    }

    @Test
    @WithMockUser(username = "Paul", password = "Arrakis55!!", roles="ADMIN")
    public void givenInformationToAUser_UpdateItToTheDatabase_ReturnToAPageWithAllUsers() throws Exception {
        User user = new User();
        user.setUsername("Paul");
        user.setFullname("Atreides");
        user.setRole("user");
        user.setPassword("Arrakis55!!");
        user.setEnabled(false);

        iUserService.addNewUserToDatabase(user);
        Assertions.assertNotNull(iUserService.findById(user.getId()));

        user.setUsername("Duke");
        iUserService.update(user);
        Assertions.assertEquals("Duke", iUserService.findById(user.getId()).getUsername());

        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk());

        iUserService.deleteById(user.getId());
    }

    @Test
    @WithMockUser(username = "Paul", password = "Arrakis55!!", roles="ADMIN")
    public void givenAnId_DeleteAUser_ReturnToAPageWithAllUsers() throws Exception {
        User user = new User();
        user.setUsername("Paul");
        user.setFullname("Atreides");
        user.setRole("user");
        user.setPassword("Arrakis55!!");
        user.setEnabled(false);

        iUserService.addNewUserToDatabase(user);
        Assertions.assertNotNull(iUserService.findById(user.getId()));

        iUserService.deleteById(user.getId());
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> iUserService.findById(user.getId()));
        Assert.assertEquals("Invalid user Id " + user.getId(), exception.getMessage());

        mockMvc.perform(get("/user/list"))
                .andExpect(status().isOk());
    }
}
