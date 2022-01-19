package com.nnk.springboot.services;

import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuleNameService implements IRuleNameService{

    @Autowired
    private RuleNameRepository ruleNameRepository;
}
