package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class RuleNameService implements IRuleNameService{

    private final static Logger logger = LoggerFactory.getLogger(RuleNameService.class);
    private RuleNameRepository ruleNameRepository;

    public RuleNameService(RuleNameRepository ruleNameRepository){
        this.ruleNameRepository=ruleNameRepository;
    }

    public RuleName addNewRuleNameToDatabase(RuleName ruleName){
        logger.info("Adding a new ruleName to database");
        return ruleNameRepository.save(ruleName);
    }

    public List<RuleName> findAll(){
        return ruleNameRepository.findAll();
    }

    public RuleName findById(Integer id){
        return ruleNameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id " + id));
    }

    public void update(RuleName ruleName){
        logger.info("Updating ruleName number " + ruleName.getId());
        ruleNameRepository.save(ruleName);
    }

    public void deleteById(Integer id){
        logger.info("Deleting ruleName number " + id);
        ruleNameRepository.deleteById(id);
    }
}
