package com.nnk.springboot;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.ICurvePointService;
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
public class CurvePointITTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private static MockMvc mockMvc;

    @Autowired
    private ICurvePointService iCurvePointService;

    @BeforeEach
    public void setupMockMvc(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void givenAnUrl_ReturnPathToAPageWithAllCurvePoints() throws Exception {
        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenAnUrl_ReturnPathToAPageAddingCurvePoints() throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenAValidCurvePoint_AddItToDatabase_ReturnPathToAPageWithAllCurvePoints() throws Exception{
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(10);

        iCurvePointService.addNewCurvePointToDatabase(curvePoint);
        Assertions.assertNotNull(iCurvePointService.findById(curvePoint.getId()));
        Assertions.assertEquals(iCurvePointService.findById(curvePoint.getId()).getCurveId(), 10, 10);

        mockMvc.perform(post("/curvePoint/validate"))
                .andExpect(redirectedUrl("/curvePoint/list"))
                .andExpect(status().isFound());

        iCurvePointService.deleteById(curvePoint.getId());
    }

    @Test
    public void givenAnId_ReturnPathToPageWithASpecificCurvePoint() throws Exception{
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(10);

        iCurvePointService.addNewCurvePointToDatabase(curvePoint);
        Assert.assertNotNull(iCurvePointService.findById(curvePoint.getId()));

        mockMvc.perform(get("/curvePoint/update/{id}", curvePoint.getId()))
                .andExpect(status().isOk());

        iCurvePointService.deleteById(curvePoint.getId());
    }

    @Test
    public void givenInformationToACurvePoint_UpdateItToTheDatabase_ReturnToAPageWithAllCurvePoints() throws Exception {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(10);

        iCurvePointService.addNewCurvePointToDatabase(curvePoint);
        Assertions.assertNotNull(iCurvePointService.findById(curvePoint.getId()));

        curvePoint.setCurveId(20);
        iCurvePointService.update(curvePoint);
        Assertions.assertEquals(20, iCurvePointService.findById(curvePoint.getId()).getCurveId());

        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk());

        iCurvePointService.deleteById(curvePoint.getId());
    }

    @Test
    public void givenAnId_DeleteACurvePoint_ReturnToAPageWithAllCurvePoints() throws Exception {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setCurveId(10);

        iCurvePointService.addNewCurvePointToDatabase(curvePoint);
        Assertions.assertNotNull(iCurvePointService.findById(curvePoint.getId()));

        iCurvePointService.deleteById(curvePoint.getId());
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> iCurvePointService.findById(curvePoint.getId()));
        Assert.assertEquals("Invalid curvePoint Id " + curvePoint.getId(), exception.getMessage());

        mockMvc.perform(get("/curvePoint/list"))
                .andExpect(status().isOk());
    }
}
