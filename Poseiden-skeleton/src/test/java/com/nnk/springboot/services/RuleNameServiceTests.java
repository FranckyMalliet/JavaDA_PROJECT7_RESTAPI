package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.IRuleNameService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RuleNameServiceTests {

	@Autowired
	private IRuleNameService iRuleNameService;

	@Test
	public void ruleTest() {
		RuleName rule = new RuleName();
		rule.setName("Rule Name");

		// Save
		rule = iRuleNameService.addNewRuleNameToDatabase(rule);
		Assert.assertNotNull(rule.getId());
		Assert.assertTrue(rule.getName().equals("Rule Name"));

		// Update
		rule.setName("Rule Name Update");
		iRuleNameService.update(rule);
		Assert.assertTrue(rule.getName().equals("Rule Name Update"));

		// Find
		List<RuleName> listResult = iRuleNameService.findAll();
		Assert.assertTrue(listResult.size() > 0);

		// Delete
		Integer id = rule.getId();
		iRuleNameService.deleteById(id);

		Exception exception = Assert.assertThrows(IllegalArgumentException.class, () -> iRuleNameService.findById(id));
		Assert.assertEquals("Invalid ruleName Id " + id, exception.getMessage());
	}
}
