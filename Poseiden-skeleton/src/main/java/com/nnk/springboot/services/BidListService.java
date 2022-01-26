package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class BidListService implements IBidListService {

    private final static Logger logger = LoggerFactory.getLogger(BidListService.class);
    private final BidListRepository bidListRepository;

    public BidListService(BidListRepository bidListRepository){
        this.bidListRepository =  bidListRepository;
    }

    /**
     * Method using crudRepository, saving Bid to the database
     * Before saving a Bid, add timestamp to it
     * @param bid
     * @return a Bid in the database
     */

    public BidList addNewBidToDatabase(BidList bid){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        bid.setBidListDate(timestamp.from(Instant.now()));
        bid.setCreationDate(timestamp.from(Instant.now()));

        logger.info("Adding a new BidList to the database");
        return bidListRepository.save(bid);
    }

    /**
     * @return all Bids from database
     */

    public List<BidList> findAll(){
        logger.info("Retrieving all Bid from database");
        return bidListRepository.findAll();
    }

    /**
     * @param id
     * @return a Bid by his id
     */

    public BidList findById(Integer id){
        logger.info("Retrieving Bid with id number " + id + " from database");
        return bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid bid Id " + id));
    }

    /**
     * Method using crudRepository, updating a Bid in the database
     * Before updating a Bid, set a timestamp to it
     * @param bid
     */

    public void update(BidList bid){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        bid.setRevisionDate(timestamp.from(Instant.now()));

        logger.info("Updating Bid with id number " + bid.getBidListId());
        bidListRepository.save(bid);
    }

    /**
     * Method using crudRepository, deleting a Bid by his id in the database
     * @param id
     */

    public void deleteById(Integer id){
        logger.info("Deleting Bid with id number " + id);
        bidListRepository.deleteById(id);
    }
}
