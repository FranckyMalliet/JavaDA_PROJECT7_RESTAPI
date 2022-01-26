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

    /**
     * Method using crudRepository, saving RuleName to the database
     * @param ruleName
     * @return a RuleName in the database
     */

    public RuleName addNewRuleNameToDatabase(RuleName ruleName){
        logger.info("Adding a new ruleName to database");
        return ruleNameRepository.save(ruleName);
    }

    /**
     * @return all RuleNames from database
     */

    public List<RuleName> findAll(){
        logger.info("Retrieving all RuleNames from database");
        return ruleNameRepository.findAll();
    }

    /**
     * @param id
     * @return a RuleName by his id
     */

    public RuleName findById(Integer id){
        logger.info("Retrieving RuleName with id number " + id + " from database");
        return ruleNameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id " + id));
    }

    /**
     * Method using crudRepository, updating a RuleName in the database
     * @param ruleName
     */

    public void update(RuleName ruleName){
        logger.info("Updating ruleName number " + ruleName.getId());
        ruleNameRepository.save(ruleName);
    }

    /**
     * Method using crudRepository, deleting a RuleName by his id in the database
     * @param id
     */

    public void deleteById(Integer id){
        logger.info("Deleting ruleName number " + id);
        ruleNameRepository.deleteById(id);
    }
}
