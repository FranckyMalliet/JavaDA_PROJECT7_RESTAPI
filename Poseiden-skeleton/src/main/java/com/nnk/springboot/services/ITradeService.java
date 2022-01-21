package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;

import java.util.List;

public interface ITradeService {

    public Trade addNewTradeToDatabase(Trade trade);
    public List<Trade> findAll();
    public Trade findById(Integer id);
    public void update(Trade trade);
    public void deleteById(Integer id);
}
