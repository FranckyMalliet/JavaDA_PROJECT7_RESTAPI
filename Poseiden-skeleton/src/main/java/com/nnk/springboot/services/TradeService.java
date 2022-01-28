package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class TradeService implements ITradeService{

    private final static Logger logger = LoggerFactory.getLogger(TradeService.class);
    private final TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository){
        this.tradeRepository = tradeRepository;
    }

    /**
     * Method using crudRepository, saving Trade to the database
     * Before saving a Bid, add timestamp to it
     * @param trade
     * @return a Bid in the database
     */

    public Trade addNewTradeToDatabase(Trade trade){
        Timestamp timestamp = new Timestamp((System.currentTimeMillis()));
        trade.setCreationDate(timestamp.from(Instant.now()));
        trade.setTradeDate(timestamp.from(Instant.now()));

        logger.info("Adding a new trade to database");
        return tradeRepository.save(trade);
    }

    /**
     * @return all Trades from database
     */

    public List<Trade> findAll(){
        logger.info("Retrieving all Trades from database");
        return tradeRepository.findAll();
    }

    /**
     * @param id
     * @return a Trade by his id
     */

    public Trade findById(Integer id){
        logger.info("Retrieving Trade with id number " + id + " from database");
        return tradeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trade Id " + id));
    }

    /**
     * Method using crudRepository, updating a Trade in the database
     * @param trade
     */

    public void update(Trade trade){
        logger.info("Updating trade number " + trade.getTradeId());
        tradeRepository.save(trade);
    }

    /**
     * Method using crudRepository, deleting a Trade by his id in the database
     * @param id
     */

    public void deleteById(Integer id){
        logger.info("Deleting trade number " + id);
        tradeRepository.deleteById(id);
    }
}
