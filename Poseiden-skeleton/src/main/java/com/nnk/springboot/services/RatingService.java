package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class RatingService implements IRatingService {

    private final static Logger logger = LoggerFactory.getLogger(RatingService.class);
    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository){
        this.ratingRepository = ratingRepository;
    }

    public Rating addNewRatingToDatabase(Rating rating){
        logger.info("Adding a new Rating to database");
        return ratingRepository.save(rating);
    }

    public List<Rating> findAll(){
        return ratingRepository.findAll();
    }

    public Rating findById(Integer id){
        return ratingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid rating Id number " + id));
    }

    public void update(Rating rating){
        logger.info("Updating Rating with id number " + rating.getId());
        ratingRepository.save(rating);
    }

    public void deleteById(Integer id){
        logger.info("Deleting rating with id number " + id);
        ratingRepository.deleteById(id);
    }
}
