package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TradeRepository extends JpaRepository<Trade, Integer>, CrudRepository<Trade, Integer> {

    public List<Trade> findAll();
    public Optional<Trade> findById(Integer id);
}
