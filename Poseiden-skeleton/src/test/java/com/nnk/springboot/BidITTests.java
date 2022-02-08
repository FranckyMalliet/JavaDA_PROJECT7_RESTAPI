package com.nnk.springboot;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.IBidListService;
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
public class BidITTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private static MockMvc mockMvc;

    @Autowired
    private IBidListService iBidListService;

    @BeforeEach
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void givenAnUrl_ReturnPathToAPageWithAllBids() throws Exception {
        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenAnUrl_ReturnPathToAPageAddingBids() throws Exception {
        mockMvc.perform(get("/bidList/add"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenAValidBid_AddItToDatabase_ReturnPathToAPageWithAllBids() throws Exception{
        BidList bid = new BidList();
        bid.setAccount("Account Test");
        bid.setType("Type Test");
        bid.setBidQuantity(10d);

        iBidListService.addNewBidToDatabase(bid);
        System.err.println(iBidListService.findById(bid.getBidListId()).getAccount());
        Assertions.assertNotNull(iBidListService.findById(bid.getBidListId()));
        Assertions.assertEquals(iBidListService.findById(bid.getBidListId()).getBidQuantity(), 10d, 10d);

        mockMvc.perform(post("/bidList/validate"))
                .andExpect(status().isFound());

        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk());

        iBidListService.deleteById(bid.getBidListId());
    }

    @Test
    public void givenAnId_ReturnPathToPageWithASpecificBid() throws Exception{
        BidList bid = new BidList();
        bid.setAccount("Account Test");
        bid.setType("Type Test");
        bid.setBidQuantity(10d);

        iBidListService.addNewBidToDatabase(bid);
        Assert.assertNotNull(iBidListService.findById(bid.getBidListId()));

        mockMvc.perform(get("/bidList/update/{id}", bid.getBidListId()))
                .andExpect(status().isOk());

        iBidListService.deleteById(bid.getBidListId());
    }

    @Test
    public void givenInformationToABid_UpdateItToTheDatabase_ReturnToAPageWithAllBids() throws Exception {
        BidList bid = new BidList();
        bid.setAccount("Account Test");
        bid.setType("Type Test");
        bid.setBidQuantity(10d);

        iBidListService.addNewBidToDatabase(bid);
        Assertions.assertNotNull(iBidListService.findById(bid.getBidListId()));

        bid.setBidQuantity(20d);
        iBidListService.update(bid);
        Assertions.assertEquals(20d, iBidListService.findById(bid.getBidListId()).getBidQuantity());

        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk());

        iBidListService.deleteById(bid.getBidListId());
    }

    @Test
    public void givenAnId_DeleteABid_ReturnToAPageWithAllBids() throws Exception {
        BidList bid = new BidList();
        bid.setAccount("Account Test");
        bid.setType("Type Test");
        bid.setBidQuantity(10d);

        iBidListService.addNewBidToDatabase(bid);
        Assertions.assertNotNull(iBidListService.findById(bid.getBidListId()));

        iBidListService.deleteById(bid.getBidListId());
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> iBidListService.findById(bid.getBidListId()));
        Assert.assertEquals("Invalid bid Id " + bid.getBidListId(), exception.getMessage());

        mockMvc.perform(get("/bidList/list"))
                .andExpect(status().isOk());
    }
}
