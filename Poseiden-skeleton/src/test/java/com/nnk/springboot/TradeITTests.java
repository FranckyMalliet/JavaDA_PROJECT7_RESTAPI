package com.nnk.springboot;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.ITradeService;
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
public class TradeITTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private static MockMvc mockMvc;

    @Autowired
    private ITradeService iTradeService;

    @BeforeEach
    public void setupMockMvc(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void givenAnUrl_ReturnPathToAPageWithAllTrades() throws Exception {
        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenAnUrl_ReturnPathToAPageAddingTrades() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenAValidTrade_AddItToDatabase_ReturnPathToAPageWithAllTrades() throws Exception{
        Trade trade = new Trade();
        trade.setAccount("Trade Account");
        trade.setType("Type");

        iTradeService.addNewTradeToDatabase(trade);
        Assertions.assertNotNull(iTradeService.findById(trade.getTradeId()));
        Assertions.assertEquals(iTradeService.findById(trade.getTradeId()).getAccount(), "Trade Account", "Trade Account");

        mockMvc.perform(post("/trade/validate"))
                .andExpect(status().isFound());

        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk());

        iTradeService.deleteById(trade.getTradeId());
    }

    @Test
    public void givenAnId_ReturnPathToPageWithASpecificTrade() throws Exception{
        Trade trade = new Trade();
        trade.setAccount("Trade Account");
        trade.setType("Type");

        iTradeService.addNewTradeToDatabase(trade);
        Assert.assertNotNull(iTradeService.findById(trade.getTradeId()));

        mockMvc.perform(get("/trade/update/{id}", trade.getTradeId()))
                .andExpect(status().isOk());

        iTradeService.deleteById(trade.getTradeId());
    }

    @Test
    public void givenInformationToATrade_UpdateItToTheDatabase_ReturnToAPageWithAllTrades() throws Exception {
        Trade trade = new Trade();
        trade.setAccount("Trade Account");
        trade.setType("Type");

        iTradeService.addNewTradeToDatabase(trade);
        Assertions.assertNotNull(iTradeService.findById(trade.getTradeId()));

        trade.setAccount("New Trade Account");
        iTradeService.update(trade);
        Assertions.assertEquals("New Trade Account", iTradeService.findById(trade.getTradeId()).getAccount());

        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk());

        iTradeService.deleteById(trade.getTradeId());
    }

    @Test
    public void givenAnId_DeleteATrade_ReturnToAPageWithAllTrades() throws Exception {
        Trade trade = new Trade();
        trade.setAccount("Trade Account");
        trade.setType("Type");

        iTradeService.addNewTradeToDatabase(trade);
        Assertions.assertNotNull(iTradeService.findById(trade.getTradeId()));

        iTradeService.deleteById(trade.getTradeId());
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> iTradeService.findById(trade.getTradeId()));
        Assert.assertEquals("Invalid trade Id " + trade.getTradeId(), exception.getMessage());

        mockMvc.perform(get("/trade/list"))
                .andExpect(status().isOk());
    }
}
