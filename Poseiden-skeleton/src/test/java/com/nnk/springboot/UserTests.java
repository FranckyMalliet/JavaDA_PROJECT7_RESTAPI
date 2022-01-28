package com.nnk.springboot;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.IUserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTests {

    @Autowired
    private IUserService iUserService;

    @Test
    public void userTest() {
        User user = new User();
        user.setUsername("Paul");
        user.setFullname("Atreides");
        user.setRole("user");
        user.setPassword("Arrakis55!!");
        user.setEnabled(false);

        // Save
        user = iUserService.addNewUserToDatabase(user);
        Assert.assertNotNull(user.getId());
        Assert.assertTrue(user.getUsername().equals("Paul"));
        Assert.assertTrue(user.getFullname().equals("Atreides"));
        Assert.assertTrue(user.getRole().equals("user"));

        // Update
        user.setFullname("Harkonnen");
        iUserService.update(user);
        Assert.assertTrue(user.getFullname().equals("Harkonnen"));

        // Find
        List<User> listResult = iUserService.findAll();
        Assert.assertTrue(listResult.size() > 0);

        // Delete
        Integer id = user.getId();
        iUserService.deleteById(id);

        Exception exception = Assert.assertThrows(IllegalArgumentException.class, () -> iUserService.findById(id));
        Assert.assertEquals("Invalid user Id " + id, exception.getMessage());
    }
}
