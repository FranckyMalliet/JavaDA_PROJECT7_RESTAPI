package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.IRatingService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RatingServiceTests {

	@Autowired
	private IRatingService iRatingService;

	@Test
	public void ratingTest() {
		Rating rating = new Rating();
		rating.setOrderNumber(10);

		// Save
		rating = iRatingService.addNewRatingToDatabase(rating);
		Assert.assertNotNull(rating.getId());
		Assert.assertTrue(rating.getOrderNumber() == 10);

		// Update
		rating.setOrderNumber(20);
		iRatingService.update(rating);
		Assert.assertTrue(rating.getOrderNumber() == 20);

		// Find
		List<Rating> listResult = iRatingService.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = rating.getId();
		iRatingService.deleteById(id);

		Exception exception = Assert.assertThrows(IllegalArgumentException.class, () -> iRatingService.findById(id));
		Assert.assertEquals("Invalid rating Id " + id, exception.getMessage());
	}
}
