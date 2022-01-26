package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.IRuleNameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class RuleNameController {

    private final static Logger logger = LoggerFactory.getLogger(RuleNameController.class);
    private final IRuleNameService iRuleNameService;

    public RuleNameController (IRuleNameService iRuleNameService){
        this.iRuleNameService = iRuleNameService;
    }

    /**
     * Method calling a html page displaying all RuleNames entities
     * from a database
     * @param model
     * @return a String of a html page with all RuleNames
     */

    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        model.addAttribute("ruleNames", iRuleNameService.findAll());
        return "ruleName/list";
    }

    /**
     * Method calling a html page with a form that can add a new RuleName
     * to the database
     * @return a String of a html page
     */

    @GetMapping("/ruleName/add")
    public String addRuleForm() {
        return "ruleName/add";
    }

    /**
     * Method validating or not a new RuleName. If it's validated,
     * add it to the database
     * @param ruleName
     * @param result
     * @param model
     * @return a String of a html page with all RuleNames after adding
     * a new RuleName to the database
     */

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        if(result.hasErrors()){
            logger.info("RuleName incorrect");
            return "ruleName/add";
        }

        iRuleNameService.addNewRuleNameToDatabase(ruleName);

        logger.info("Adding a new ruleName to database");
        model.addAttribute("ruleNames", iRuleNameService.findAll());
        return "redirect:/ruleName/list";
    }

    /**
     * Given a RuleName found by his ID, this method call a html form with
     * the RuleName data already written, ready to be updated
     * @param id
     * @param model
     * @return a String of a html page
     */

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RuleName ruleName = iRuleNameService.findById(id);
        model.addAttribute("ruleName", iRuleNameService.findById(id));
        return "ruleName/update";
    }

    /**
     * Method validating or not a RuleName. If it's validated, update it
     * to the database
     * @param id
     * @param ruleName
     * @param result
     * @param model
     * @return a String of a html page with all RuleNames after updating a Bid
     * in the database
     */

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        if(result.hasErrors()){
            logger.info("RuleName not found or incorrect");
            return "ruleName/update";
        }

        ruleName.setId(id);
        iRuleNameService.update(ruleName);

        logger.info("Updating ruleName number " + id);
        model.addAttribute("ruleNames", iRuleNameService.findAll());
        return "redirect:/ruleName/list";
    }

    /**
     * Method used to delete RuleNames entities in the database
     * @param id
     * @param model
     * @return a String of a html page with all RuleNames after deleting a RuleName
     * in the database
     */

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        iRuleNameService.deleteById(id);

        logger.info("Deleting ruleName number " + id);
        model.addAttribute("ruleNames", iRuleNameService.findAll());
        return "redirect:/ruleName/list";
    }
}
