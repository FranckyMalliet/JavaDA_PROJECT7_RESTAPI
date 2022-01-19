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
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

@Service
@Transactional(rollbackFor = Exception.class)
public class BidListService implements IBidListService {

    private final static Logger logger = LoggerFactory.getLogger(BidListService.class);
    private final BidListRepository bidListRepository;

    public BidListService(BidListRepository bidListRepository){
        this.bidListRepository =  bidListRepository;
    }

    public BidList addNewBidToDatabase(BidList bid){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        bid.setBidListDate(timestamp.from(Instant.now()));
        bid.setCreationDate(timestamp.from(Instant.now()));

        logger.info("Adding a new BidList to the database");
        return bidListRepository.save(bid);
    }

    public List<BidList> findAll(){
        logger.info("Retrieving all Bid from database");
        return bidListRepository.findAll();
    }

    public BidList findById(Integer id){
        return bidListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id " +id));
    }

    public void updateBid(BidList bid){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        bid.setRevisionDate(timestamp.from(Instant.now()));
        System.err.println(bid.getRevisionDate());

        logger.info("Updating Bid with id number " + bid.getBidListId());
        bidListRepository.save(bid);
    }

    public void deleteBidById(Integer id){
        logger.info("Deleting Bid with id number " + id);
        bidListRepository.deleteById(id);
    }

    public void validateBidList(BidList bidList, Errors errors){
        ValidationUtils.rejectIfEmpty(errors, "BidListId", "fields.required");
        ValidationUtils.rejectIfEmpty(errors, "account", "fields.required");
        ValidationUtils.rejectIfEmpty(errors, "type", "fields.required");
    }
}
