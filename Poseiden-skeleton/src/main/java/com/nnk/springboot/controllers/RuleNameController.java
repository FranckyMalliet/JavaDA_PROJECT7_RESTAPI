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

    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        model.addAttribute("ruleNames", iRuleNameService.findAll());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {
        return "ruleName/add";
    }

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

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RuleName ruleName = iRuleNameService.findById(id);
        model.addAttribute("ruleName", iRuleNameService.findById(id));
        return "ruleName/update";
    }

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

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        iRuleNameService.deleteById(id);

        logger.info("Deleting ruleName number " + id);
        model.addAttribute("ruleNames", iRuleNameService.findAll());
        return "redirect:/ruleName/list";
    }
}
