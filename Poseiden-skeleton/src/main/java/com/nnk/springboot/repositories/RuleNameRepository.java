package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.RuleName;
import org.h2.bnf.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RuleNameRepository extends JpaRepository<RuleName, Integer>, CrudRepository<RuleName, Integer> {

    List<RuleName> findAll();
    Optional<RuleName> findById(Integer id);
}
