package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.IRuleNameService;
import org.junit.Assert;
import org.junit.Before;
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
public class RuleNameControllerITTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private static MockMvc mockMvc;

    @Autowired
    private IRuleNameService iRuleNameService;

    @BeforeEach
    public void setupMockMvc(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void givenAnUrl_ReturnPathToAPageWithAllRules() throws Exception {
        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenAnUrl_ReturnPathToAPageAddingRules() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenAValidRule_AddItToDatabase_ReturnPathToAPageWithAllRules() throws Exception{
        RuleName rule = new RuleName();
        rule.setName("Rule Name");

        iRuleNameService.addNewRuleNameToDatabase(rule);
        Assertions.assertNotNull(iRuleNameService.findById(rule.getId()));
        Assertions.assertEquals(iRuleNameService.findById(rule.getId()).getName(), "Rule Name", "Rule Name");

        mockMvc.perform(post("/ruleName/validate"))
                .andExpect(redirectedUrl("/ruleName/list"))
                .andExpect(status().isFound());

        iRuleNameService.deleteById(rule.getId());
    }

    @Test
    public void givenAnId_ReturnPathToPageWithASpecificRule() throws Exception{
        RuleName rule = new RuleName();
        rule.setName("Rule Name");

        iRuleNameService.addNewRuleNameToDatabase(rule);
        Assert.assertNotNull(iRuleNameService.findById(rule.getId()));

        mockMvc.perform(get("/ruleName/update/{id}", rule.getId()))
                .andExpect(status().isOk());

        iRuleNameService.deleteById(rule.getId());
    }

    @Test
    public void givenInformationToARule_UpdateItToTheDatabase_ReturnToAPageWithAllRules() throws Exception {
        RuleName rule = new RuleName();
        rule.setName("Rule Name");

        iRuleNameService.addNewRuleNameToDatabase(rule);
        Assertions.assertNotNull(iRuleNameService.findById(rule.getId()));

        rule.setName("New Rule Name");
        iRuleNameService.update(rule);
        Assertions.assertEquals("New Rule Name", iRuleNameService.findById(rule.getId()).getName());

        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk());

        iRuleNameService.deleteById(rule.getId());
    }

    @Test
    public void givenAnId_DeleteARule_ReturnToAPageWithAllRules() throws Exception {
        RuleName rule = new RuleName();
        rule.setName("Rule Name");

        iRuleNameService.addNewRuleNameToDatabase(rule);
        Assertions.assertNotNull(iRuleNameService.findById(rule.getId()));

        iRuleNameService.deleteById(rule.getId());
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> iRuleNameService.findById(rule.getId()));
        Assert.assertEquals("Invalid ruleName Id " + rule.getId(), exception.getMessage());

        mockMvc.perform(get("/ruleName/list"))
                .andExpect(status().isOk());
    }
}
