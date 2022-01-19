package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


public interface BidListRepository extends JpaRepository<BidList, Integer>, CrudRepository<BidList, Integer> {

    List<BidList> findAll();
    Optional<BidList> findById(Integer id);

    @Query(value="FROM BidList bid WHERE bid_list_id = :id")
    BidList findBidById(Integer id);

    @Modifying
    @Query(value="UPDATE BidList bid WHERE bid.bid_list_id =:id", nativeQuery = true)
    void updateBidList(Integer id);
}
