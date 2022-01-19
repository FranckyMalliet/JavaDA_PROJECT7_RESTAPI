package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;

import java.util.List;

public interface IBidListService {

    public BidList addNewBidToDatabase(BidList bid);
    public List<BidList> findAll();
    public BidList findById(Integer id);
    public void updateBid(BidList bid);
    public void deleteBidById(Integer id);
}
