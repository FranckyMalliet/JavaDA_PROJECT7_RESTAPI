package com.nnk.springboot.services;

import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeService implements ITradeService{

    @Autowired
    private TradeRepository tradeRepository;
}