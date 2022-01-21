package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;

import java.util.List;

public interface IRuleNameService {

    public RuleName addNewRuleNameToDatabase(RuleName ruleName);
    public List<RuleName> findAll();
    public RuleName findById(Integer id);
    public void update(RuleName ruleName);
    public void deleteById(Integer id);
}
