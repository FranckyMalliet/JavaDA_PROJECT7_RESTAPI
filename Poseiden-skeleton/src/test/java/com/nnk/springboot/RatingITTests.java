package com.nnk.springboot;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.IRatingService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class RatingITTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private static MockMvc mockMvc;

    @Autowired
    private IRatingService iRatingService;

    @BeforeEach
    private void setupMockMvc(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void givenAnUrl_ReturnPathToAPageWithAllRatings() throws Exception {
        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenAnUrl_ReturnPathToAPageAddingRatings() throws Exception {
        mockMvc.perform(get("/rating/add"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenAValidRating_AddItToDatabase_ReturnPathToAPageWithAllRatings() throws Exception{
        Rating rating = new Rating();
        rating.setOrderNumber(10);

        iRatingService.addNewRatingToDatabase(rating);
        Assertions.assertNotNull(iRatingService.findById(rating.getId()));
        Assertions.assertEquals(iRatingService.findById(rating.getId()).getOrderNumber(), 10, 10);

        mockMvc.perform(post("/rating/validate"))
                .andExpect(redirectedUrl("/rating/list"))
                .andExpect(status().isFound());

        iRatingService.deleteById(rating.getId());
    }

    @Test
    public void givenAnId_ReturnPathToPageWithASpecificRating() throws Exception{
        Rating rating = new Rating();
        rating.setOrderNumber(10);

        iRatingService.addNewRatingToDatabase(rating);
        Assert.assertNotNull(iRatingService.findById(rating.getId()));

        mockMvc.perform(get("/rating/update/{id}", rating.getId()))
                .andExpect(status().isOk());

        iRatingService.deleteById(rating.getId());
    }

    @Test
    public void givenInformationToARating_UpdateItToTheDatabase_ReturnToAPageWithAllRatings() throws Exception {
        Rating rating = new Rating();
        rating.setOrderNumber(10);

        iRatingService.addNewRatingToDatabase(rating);
        Assertions.assertNotNull(iRatingService.findById(rating.getId()));

        rating.setOrderNumber(20);
        iRatingService.update(rating);
        Assertions.assertEquals(20, iRatingService.findById(rating.getId()).getOrderNumber());

        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk());

        iRatingService.deleteById(rating.getId());
    }

    @Test
    public void givenAnId_DeleteARating_ReturnToAPageWithAllRatings() throws Exception {
        Rating rating = new Rating();
        rating.setOrderNumber(10);

        iRatingService.addNewRatingToDatabase(rating);
        Assertions.assertNotNull(iRatingService.findById(rating.getId()));

        iRatingService.deleteById(rating.getId());
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> iRatingService.findById(rating.getId()));
        Assert.assertEquals("Invalid rating Id " + rating.getId(), exception.getMessage());

        mockMvc.perform(get("/rating/list"))
                .andExpect(status().isOk());
    }
}
