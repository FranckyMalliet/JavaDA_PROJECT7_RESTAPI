package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class TradeService implements ITradeService{

    private final static Logger logger = LoggerFactory.getLogger(TradeService.class);
    private final TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository){
        this.tradeRepository = tradeRepository;
    }

    public Trade addNewTradeToDatabase(Trade trade){
        Timestamp timestamp = new Timestamp((System.currentTimeMillis()));
        trade.setCreationDate(timestamp.from(Instant.now()));
        trade.setTradeDate(timestamp.from(Instant.now()));

        logger.info("Adding a new trade to database");
        return tradeRepository.save(trade);
    }

    public List<Trade> findAll(){
        return tradeRepository.findAll();
    }

    public Trade findById(Integer id){
        return tradeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id " + id));
    }

    public void update(Trade trade){
        logger.info("Updating trade number " + trade.getTradeId());
        tradeRepository.save(trade);
    }

    public void deleteById(Integer id){
        logger.info("Deleting trade number " + id);
        tradeRepository.deleteById(id);
    }
}
